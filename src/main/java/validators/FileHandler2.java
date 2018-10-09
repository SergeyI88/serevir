//package validators;
//
//import consts.EnumFields;
//import http.entity.Good;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.springframework.beans.factory.annotation.Autowired;
//import service.ShopService;
//import utils.MapperToEnumField;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//public class FileHandler2 {
//    @Autowired
//    private ShopService shopService;
//    @Autowired
//    private MapperToEnumField mapperToEnumField;
//
//
//    public HashMap<String, List> getResult(Workbook workbook, String storeUuid) {
//        HashMap map = new HashMap();
//        Sheet sheet = workbook.getSheetAt(0);
//        Row row = sheet.getRow(0);
//        Set<Cell> firstCells = getFirstRowWithCellsName(row);
//        List<String> listErrors = new ArrayList<>();
//        requiredColumnsIsNotPresented(firstCells, listErrors);
//        if (!listErrors.isEmpty()) {
//            map.put("errors", listErrors);
//            return map;
//        }
//
//
//
//
//
//
//        List<Good> listGood = new ArrayList<>();
//        Queue<String> defaultSequence = mapperToEnumField.SEQUENCE;
//        sheet.removeRow(sheet.getRow(0));
//        sheet.forEach(row -> {
//
//        });
////        sheet.getLastRowNum()
//        return new HashMap<>();
//    }
//
//    /**
//     * Обязательные колонки не представлены
//     */
//    private void requiredColumnsIsNotPresented(Set<Cell> set, List<String> listErrors) {
//        boolean match = false;
//        for (EnumFields e : Arrays.stream(EnumFields.values()).filter(e -> e.isRequired).collect(Collectors.toList())) {
//            for (Cell c : set) {
//                if (e.name.toLowerCase().equals(c.toString().toLowerCase().trim())) {
//                    match = true;
//                    break;
//                }
//            }
//            if (!match) {
//                listErrors.add("Колонка: " + e.name + " отсутсвует");
//            }
//            match = false;
//        }
//    }
//    private List<Good> createListGood() {
//        List<Good> goods = new ArrayList<>();
//
//        return goods;
//    }
//    /**
//    * Добавляем в SET значение всех клеток. Даже null.
//    * capaciry это значение на которое мы увеличиваем каждый раз при клетке не null.
//    * Нужно чтобы учеть случай,когда между столбцами со значением есть пустые столбцы.
//    * Использовать только для первой строки(строки с названием колонок)
//    */
//    private Set<Cell> getFirstRowWithCellsName(Row r) {
//        Set<Cell> columns = new HashSet<>();
//        Cell c;
//        short capacity = 20;
//        for (int i = 0; i < capacity; i++) {
//            c = r.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
//            columns.add(c);
//            if (c != null) {
//                capacity = (short) (capacity + i);
//            }
//        }
//        columns.removeIf(Objects::isNull);
//        return columns;
//    }
//}
