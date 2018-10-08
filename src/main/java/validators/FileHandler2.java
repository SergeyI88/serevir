package validators;

import consts.EnumFields;
import http.entity.Good;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import service.ShopService;
import utils.MapperToEnumField;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class FileHandler2 {
    private final static Logger log = Logger.getLogger(FileHandler2.class);
    @Autowired
    private ShopService shopService;
    GoodsHandler goodsHandler;

    @Autowired
    private MapperToEnumField mapperToEnumField;


    public HashMap<String, List> getResult(Workbook workbook, String token, String storeUuid) {
        int capacityRow = 50;
        HashMap map = new HashMap();
        Sheet sheet = workbook.getSheetAt(0);
        Row firstRow = sheet.getRow(0);
        Set<Cell> firstCells = getFirstRowWithCellsName(firstRow);
        List<String> listErrors = new ArrayList<>();
        requiredColumnsIsNotPresented(firstCells, listErrors);
        if (!listErrors.isEmpty()) {
            map.put("errors", listErrors);
            return map;
        }
        List<Good> listGood = new ArrayList<>();
        Map<Integer, String> mapIndexValueNameColumn = firstCells.stream().collect(Collectors.toMap((Cell::getColumnIndex), (c -> c.toString().trim().toLowerCase())));
        Queue<Integer> firstCellsTheirIndexes = firstCells.stream().map(Cell::getColumnIndex).collect(Collectors.toCollection(PriorityQueue::new));
//        if (firstCells.size() >= 19) {
//            writeToDataBase(firstCells, storeUuid);
//        }
        sheet.removeRow(firstRow);
        for (int i = 1; i < capacityRow; i++) {
            Good good = new Good(i + 1);
            if (sheet.getRow(i) != null) {
                good = setAllFields(sheet.getRow(i), firstCellsTheirIndexes, mapIndexValueNameColumn, good, listErrors);
            }
            if (IsGood(good)) {
                capacityRow = i + 50;
                listGood.add(good);
            } else {
                Good finalGood = good;
                listErrors.removeIf(s -> s.startsWith(finalGood.getId() + "") && s.contains("Предупреждение"));
            }
        }
        goodsHandler = new GoodsHandler(listErrors, listGood, token, storeUuid);
        goodsHandler.handleList();
        Map<Short, List<String>> mapErrorAndWarning = goodsHandler
                .getErrors()
                .stream()
                .collect(Collectors.groupingBy((String s) -> {
                    return Short.valueOf(s.substring(s.length() - 2).trim());
                }, Collectors.mapping((String s1) -> s1.replaceAll("[^0-9]+", " ").split(" ")[0], Collectors.toList())));
        List<String> resultErrors = new ArrayList<>();
        List<String> resultWarning = new ArrayList<>();
        mapErrorAndWarning.entrySet().stream().forEach((e) -> {
            switch (e.getKey()) {
                case 1:
                    resultErrors.add("Сервер не отвечает, попробуйте позже");
                    break;
                case 2:
                    resultErrors.add("Не указано \"имя\" в строках: " + e.getValue().stream().collect(Collectors.joining(",", "", "")));
                    break;
                case 3:
                    resultErrors.add("Оставьте поле UUID пустым, в строках одинаковый UUID (Уже существующей группы в облаке или файлк): " + e.getValue().stream().collect(Collectors.joining(",", "", "")));
                    break;
                case 12:
                    resultWarning.add("У алкогольных товаров не заполнена колонка \"алко-коды\" в строках: " + e.getValue().stream().collect(Collectors.joining(",", "", "")) + "совет заполняйте алкогольную продукцию через терминал");
                    break;
                case 4:
                    resultWarning.add("Некорректное поле \"цена\" в строках: " + e.getValue().stream().collect(Collectors.joining(",", "", "")) + " выставлено 0");
                    break;
                case 5:
                    resultWarning.add("Некорректное поле \"название меры\" должно быть " + Arrays.toString(EnumFields.MEASURE_NAME.value) + " в строках: " + e.getValue().stream().collect(Collectors.joining(",", "", "")) + " выставлено шт.");
                    break;
                case 6:
                    resultWarning.add("Не найдена группа с таким кодом, как в колонке \"код группы\" в строках: " + e.getValue().stream().collect(Collectors.joining(",", "", "")) + " выставлено \"пусто\"");
                    break;
                case 7:
                    resultWarning.add("Некорректное поле \"количество\" в строках: " + e.getValue().stream().collect(Collectors.joining(",", "", "")) + " выставлено 0");
                    break;
                case 8:
                    resultWarning.add("Некорректное поле \"группа\" в строках: " + e.getValue().stream().collect(Collectors.joining(",", "", "")) + " выставлено 0");
                    break;
                case 9:
                    resultWarning.add("Некорректное поле \"тип\" должно быть " + Arrays.toString(EnumFields.TYPE.value) + " в строках: " + e.getValue().stream().collect(Collectors.joining(",", "", "")) + " выставлено \"normal\" ");
                    break;
                case 10:
                    resultWarning.add("Некорректное поле \"объем алкогольной тары\" должно быть число [ 0 .. 99.999 ] в строках: " + e.getValue().stream().collect(Collectors.joining(",", "", "")) + " выставлено 0");
                    break;
                case 11:
                    resultWarning.add("Некорректное поле \"код алкоголя\" Код вида алкогольной продукции ФСРАР [ 0 .. 999 ] в строках: " + e.getValue().stream().collect(Collectors.joining(",", "", "")) + " выставлено 0");
                    break;
                case 13:
                    resultWarning.add("Некорректное поле \"цена закупки\" в строках: " + e.getValue().stream().collect(Collectors.joining(",", "", "")) + " выставлено 0");
                    break;
                case 14:
                    resultWarning.add("Некорректное поле \"описание\" в строках: " + e.getValue().stream().collect(Collectors.joining(",", "", "")) + " выставлено пусто");
                    break;
                case 15:
                    resultWarning.add("Некорректное поле \"налог\" должно быть " + Arrays.toString(EnumFields.TAX.value) + " в строках: " + e.getValue().stream().collect(Collectors.joining(",", "", "")) + " выставлено NO_VAT");
                    break;
                case 16:
                    resultWarning.add("Некорректное поле \"uuid\" в строках: " + e.getValue().stream().collect(Collectors.joining(",", "", "")) + " сгенерировалось автоматически");
                    break;
            }
        });
        System.out.println(mapErrorAndWarning);


//        List<String> errors = mapErrorAndWarning.entrySet()
//                .stream()
//                .filter(e -> e.getKey() == 1 || e.getKey() == 2 || e.getKey() == 3)
//                .flatMap(e -> e.getValue().stream())
//                .collect(Collectors.toList());
        HashMap<String, List> hashMap = new HashMap<>();
        hashMap.put("goods", goodsHandler.getGoods());
        hashMap.put("errors", resultErrors);
        hashMap.put("warnings", resultWarning);
        hashMap.put("forDelete", goodsHandler.getRemoveList());


        return hashMap;
    }

    private Good setAllFields(Row row, Queue<Integer> firstCellsTheirIndexes, Map<Integer, String> mapIndexValueNameColumn, Good good, List<String> listErrors) {
        for (Integer indexColumn : firstCellsTheirIndexes) {
            Cell c = row.getCell(indexColumn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            String nameColumn = mapIndexValueNameColumn.get(indexColumn);
            setOneFieldByNameColumn(c, good, nameColumn, listErrors);
        }
        return good;
    }

    private void setOneFieldByNameColumn(Cell c, Good good, String nameColumn, List<String> listErrors) {
        for (EnumFields e : EnumFields.values()) {
            if (e.nameColumn.equals(nameColumn)) {
                try {
                    Field f = Good.class.getDeclaredField(e.nameFieldForReflection);
                    f.setAccessible(Boolean.TRUE);
                    f.set(good, mapperToEnumField.mapFunc.get(nameColumn).apply(c, listErrors, good.getId(), nameColumn));
                } catch (NoSuchFieldException e1) {
                    log.info(nameColumn + " поле не системное, но присутствует в EnumFields");
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }

            }
        }
        /*
        ToDo Далее здесь должно быть реализовано засетирование других полей
         */
        System.out.println(good);
    }

    /**
     * Обязательные колонки не представлены
     */
    private void requiredColumnsIsNotPresented(Set<Cell> set, List<String> listErrors) {
        boolean match = false;
        for (EnumFields e : Arrays.stream(EnumFields.values()).filter(e -> e.isRequired).collect(Collectors.toList())) {
            for (Cell c : set) {
                if (e.nameColumn.toLowerCase().equals(c.toString().toLowerCase().trim())) {
                    match = true;
                    break;
                }
            }
            if (!match) {
                listErrors.add("Колонка: " + e.nameColumn + " отсутсвует");
            }
            match = false;
        }
    }

    /**
     * Добавляем в SET значение всех клеток. Даже null.
     * capaciry это значение на которое мы увеличиваем каждый раз при клетке не null.
     * Нужно чтобы учеть случай,когда между столбцами со значением есть пустые столбцы.
     * Использовать только для первой строки(строки с названием колонок)
     */
    private Set<Cell> getFirstRowWithCellsName(Row r) {
        Set<Cell> columns = new LinkedHashSet<>();
        Cell c;
        short capacity = 20;
        for (int i = 0; i < capacity; i++) {
            c = r.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            if (c != null && !c.toString().equals("")) {
                columns.add(c);
                capacity = (short) (20 + i);
            }
        }
        return columns;
    }

    /*
    * Пишем новую последовательность в базу данных.
     */
    private void writeToDataBase(Set<Cell> cells, String storeUuid) {
        StringBuilder builder = new StringBuilder();
        for (Cell cell : cells) {
            String column = cell.toString().toLowerCase().trim();
            builder.append(column).append(";");
        }
        shopService.writeSequenceColumns(builder.toString(), storeUuid);
    }

    private boolean IsGood(Good g) {
        return g.getName() != null;
    }
}
