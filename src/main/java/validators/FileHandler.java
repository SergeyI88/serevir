package validators;

import consts.EnumFields;
import http.DeleteGoods;
import http.entity.Good;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import service.ShopService;
import utils.ThirdFunction;
import utils.MapperToEnumField;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Component
@Scope("prototype")
public class FileHandler<T extends Workbook> {
    @Autowired
    private ShopService shopService;
    @Autowired
    private MapperToEnumField mapperToEnumField;

    private final List<String> columns = Arrays.asList("uuid", "код", "штрих-коды", "алко-коды", "имя", "цена", "количество", "цена закупки", "название меры",
            "налог", "разрешено к продаже", "описание", "артикул", "код группы",
            "группа", "тип", "объем алкогольной тары", "код алкоголя", "объем тары");

    public FileHandler checkValues(ThirdFunction<Cell, EnumFields, List<String>, Good, Good> func
            , Cell cell
            , EnumFields field
            , List<String> errors
            , Queue<String> queue
            , Good good
            , List<Good> forDelete) {
        queue.offer(queue.poll());

        func.apply(cell, field, errors, good);
        return this;
    }

    private Good groupOrNo(Good apply, List<String> listErrors) {
        if (apply.getGroup() != null) {
            if (apply.getGroup()) {
                for (int i = 0; i < listErrors.size(); ) {
                    if (listErrors.get(i++).startsWith(apply.getId() + "")) {
                        listErrors.remove(--i);
                    }
                }
                Good good = new Good();
                good.setCode(apply.getCode());
                good.setName(apply.getName());
                good.setGroup(apply.getGroup());
                good.setParentUuid(apply.getParentUuid());
                good.setUuid(apply.getUuid());
                return good;
            }
        }
        return apply;
    }

    public HashMap<String, List> getResult(Workbook workbook, String storeUuid, String auth) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        boolean match = false;
        HashMap map = new HashMap();
        List<String> listErrors = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);
        List<Cell> list = getFirstColumns(row);
        for (EnumFields e : EnumFields.values()) {
            for (Cell c : list) {
                if (e.name.toLowerCase().equals(c.toString().toLowerCase().trim())) {
                    match = true;
                    break;
                }
            }
            if (!match) {
                listErrors.add("Колонка: " + e.name + " отсутсвует");
            }
            match = false;
        }
        if (!listErrors.isEmpty()) {
            map.put("errors", listErrors);
            return map;
        }

        Queue<String> sequence = getSequenceAndWriteToDataBase(row, storeUuid);

        Integer[] integers = {1};
        sheet.removeRow(row);

        List<Good> goodList = new ArrayList<>();
        List<Good> forDelete = new ArrayList<>();
        sheet.forEach(r -> {
            Good good = new Good(++integers[0]);
            int i = 0;
            Cell c = r.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            for (; i < sequence.size(); ) {
                if (columns.stream().anyMatch(s -> s.equals(sequence.peek().trim().toLowerCase()))) {
                    checkValues(mapperToEnumField.mapFunc.get(sequence.peek().trim().toLowerCase())
                            , c
                            , mapperToEnumField.mapNames.get(sequence.peek().trim().toLowerCase())
                            , listErrors
                            , sequence
                            , good
                            , forDelete);
                } else {
                    sequence.offer(sequence.poll());
                }
                c = r.getCell(++i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            }
            Good temp = isEnd(good, listErrors);
            if (temp != null && temp.getName().equals("-")) {
                forDelete.add(temp);
            }

            if (temp != null && temp.getName() != null && temp.getUuid() != null) {
                goodList.add(groupOrNo(good, listErrors));
            }
        });
        isMatch(goodList, listErrors);
        isDelete(storeUuid, auth, forDelete);
        map.put("errors", listErrors);
        map.put("goods", goodList);
        return map;
    }

    private List<Cell> getFirstColumns(Row r) {
        List<Cell> columns = new ArrayList<>();
        Cell c = null;
        for (int i = 0; i < r.getLastCellNum(); i++) {
            c = r.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            columns.add(c);
        }
        return columns;
    }

    private Queue<String> getSequenceAndWriteToDataBase(Row row, String storeUuid) {
        Queue<String> sequence = new ArrayDeque<>();
        StringBuilder builder = new StringBuilder();
        for (Cell cell : row) {
            String column = cell.toString().toLowerCase().trim();
            sequence.offer(column);
            if (columns.contains(column)) {
                builder.append(column).append(";");
            }
        }
        shopService.writeSequenceColumns(builder.toString(), storeUuid);
        return sequence;
    }

    private void isDelete(String storeUuid, String auth, List<Good> temp) {
        DeleteGoods deleteGoods = new DeleteGoods();
        deleteGoods.execute(storeUuid, auth, temp);

    }

    private Good isEnd(Good good, List<String> listErrors) {
        if (good.getQuantity() == null && good.getName() == null && good.getPrice() == null) {
            for (int i = 0; i < listErrors.size(); ) {
                if (listErrors.get(i++).startsWith(good.getId() + "")) {
                    listErrors.remove(--i);
                }
            }
            return null;
        }
        return good;
    }

    private void isMatch(List<Good> goods, List<String> listErrors) {
        Map<String, String> mapUuidOrCodeWithGoodId = new HashMap<>();
        List<String> matchUuid = new ArrayList<>();
        List<String> matchCode = new ArrayList<>();
        Map<String, String> codeWithUuidForSwap = new HashMap<>();
        for (Good good : goods) {
            if (good.getCode() != null) {
                codeWithUuidForSwap.put(good.getCode(), good.getUuid());
            }
            System.out.println("Uuid" + " " + good.getUuid() + " " + good.getId());
            String uuid = good.getUuid();
            String code = good.getCode();
            code = code != null ? Double.valueOf(code).toString() : null;
            if (mapUuidOrCodeWithGoodId.containsKey(uuid)) {
                if (!matchUuid.contains(uuid)) {
                    matchUuid.add(uuid);
                }
                mapUuidOrCodeWithGoodId.replace(uuid, (mapUuidOrCodeWithGoodId.get(uuid) + ", " + good.getId()));
            } else {
                mapUuidOrCodeWithGoodId.put(uuid, String.valueOf(good.getId()));
            }
            if (mapUuidOrCodeWithGoodId.containsKey(code)) {
                if (!matchCode.contains(code)) {
                    matchCode.add(code);
                }
                mapUuidOrCodeWithGoodId.replace(code, mapUuidOrCodeWithGoodId.get(code) + " " + String.valueOf(good.getId()));
            } else {
                if (code != null) {
                    mapUuidOrCodeWithGoodId.put(code, String.valueOf(good.getId()));
                }
            }
        }
        if (!matchUuid.isEmpty()) {
            for (String str : matchUuid) {
                String errorString = "В строках " + mapUuidOrCodeWithGoodId.get(str) + " одинаковое поле Uuid";
                listErrors.add(errorString);
            }
        }
        if (!matchCode.isEmpty()) {
            for (String str : matchCode) {
                String errorString = "В строках " + mapUuidOrCodeWithGoodId.get(str) + " одинаковое поле Code";
                listErrors.add(errorString);
            }
        }
        if (listErrors.isEmpty()) {
            for (Good good : goods) {
                String parrentCode = good.getParentUuid();
                if (parrentCode != null && codeWithUuidForSwap.containsKey(parrentCode)) {
                    String uuid = codeWithUuidForSwap.get(parrentCode);
                    good.setParentUuid(uuid);
                }
            }
        }
    }
}
