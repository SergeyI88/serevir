package validators;

import consts.EnumFields;
import http.entity.Good;
import http.entity.Group;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import utils.ThirdFunction;
import utils.MapperToEnumField;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Component
@Scope("prototype")
public class FileHandler<T extends Workbook> {
    @Autowired
    MapperToEnumField mapperToEnumField;

    public FileHandler checkValues(ThirdFunction<Cell, EnumFields, List<String>, Good, Good> func
            , Cell cell
            , EnumFields field
            , List<String> errors
            , Queue<String> queue
            , List<Good> goods
            , Good good) {
        queue.offer(queue.poll());
        func.apply(cell, field, errors, good);
        return this;
    }

    private Good groupOrNo(Good apply, List<String> listErrors) {
        if (apply.getGroup() != null) {
            if (apply.getGroup()) {
                for (int i = 0; i < listErrors.size();) {
                    if(listErrors.get(i++).startsWith(apply.getId() + "")) {
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

    public HashMap<String, List> getResult(Workbook workbook) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        boolean match = false;
        HashMap map = new HashMap();
        List<String> listErrors = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(0);
        Queue<String> sequence = new ArrayDeque<>();

        Row row = sheet.getRow(0);
        for (EnumFields e : EnumFields.values()) {
            for (Iterator<Cell> it = row.cellIterator(); it.hasNext(); ) {
                Cell c = it.next();
                if (e.name.toLowerCase().equals(c.toString().toLowerCase())) {
                    match = true;
                    break;
                }
            }
            if (!match) {
                listErrors.add("Колонка: " + e.name + " отсутсвует");
            }
            match = false;
        }
        if(!listErrors.isEmpty()) {
            map.put("errors", listErrors);
            return map;
        }
        int[] numberColumns = {0};
        for (Iterator<Cell> it = row.cellIterator(); it.hasNext(); ) {
            sequence.offer(it.next().toString());
            numberColumns[0]++;
        }
        Integer[] integers = {1};
        sheet.removeRow(row);
        System.out.println(sheet.getRow(2).getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK ).getAddress());
        sequence.forEach(System.out::println);

        List<Good> goodList = new ArrayList<>();
        sheet.forEach(r -> {
            Good good = new Good(++integers[0]);
            int i = 0;
            Cell c = r.getCell (i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            for (; i < numberColumns[0];) {
                checkValues(mapperToEnumField.mapFunc.get(sequence.peek()), c, mapperToEnumField.mapNames.get(sequence.peek()), listErrors, sequence, goodList, good);
                c = r.getCell(++i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            };
            goodList.add(groupOrNo(good, listErrors));
        });
        isMatch(goodList, listErrors);
        map.put("errors", listErrors);
        map.put("goods", goodList);
        return map;
    }

    private void isMatch(List<Good> goods, List<String> listErrors){
        Map<String,String> mapUuidOrCodeWithGoodId = new HashMap<>();
        List<String> matchUuid = new ArrayList<>();
        List<String> matchCode = new ArrayList<>();
        Map<String, String> codeWithUuidForSwap = new HashMap<>();
            for (Good good : goods) {
                codeWithUuidForSwap.put(good.getCode(), good.getUuid());
                System.out.println("Uuid" + " " + good.getUuid() + " " + good.getId());
                String uuid = good.getUuid();
                String code = good.getCode();
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
                    mapUuidOrCodeWithGoodId.put(code, String.valueOf(good.getId()));
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
        for (Good good : goods) {
            String parrentCode = good.getParentUuid();
            if (codeWithUuidForSwap.containsKey(parrentCode)){
                String uuid = codeWithUuidForSwap.get(parrentCode);
                good.setParentUuid(uuid);
            }
        }
    }
}
