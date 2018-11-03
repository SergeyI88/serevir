package utils;

import consts.ConstantsErrorWarning;
import consts.EnumFields;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
@PropertySource("classpath:sequence.properties")
public class MapperToEnumField {
    @Autowired
    private Environment env;

    public final HashMap<String, EnumFields> mapNames = new HashMap<>();
    public static final Queue<String> SEQUENCE = new PriorityQueue<>();

    @PostConstruct
    public void init() {
        mapNames.put("объем алкогольной тары", EnumFields.ALCOHOL_BY_VOLUME);
        mapNames.put("код алкоголя", EnumFields.ALCOHOL_PRODUCT_KIND_CODE);
        mapNames.put("артикул", EnumFields.ARTICLE_NUMBER);
        mapNames.put("штрих-коды", EnumFields.BAR_CODES);
        mapNames.put("код", EnumFields.CODE);
        mapNames.put("цена закупки", EnumFields.COST_PRICE);
        mapNames.put("описание", EnumFields.DESCRIPTION);
        mapNames.put("группа", EnumFields.GROUP);
        mapNames.put("название меры", EnumFields.MEASURE_NAME);
        mapNames.put("имя", EnumFields.NAME);
        mapNames.put("код группы", EnumFields.PARENT_UUID);
        mapNames.put("цена", EnumFields.PRICE);
        mapNames.put("количество", EnumFields.QUANTITY);
        mapNames.put("объем тары", EnumFields.TARE_VOLUME);
        mapNames.put("налог", EnumFields.TAX);
        mapNames.put("тип", EnumFields.TYPE);
        mapNames.put("разрешено к продаже", EnumFields.ALLOW_TO_SELL);
        mapNames.put("uuid", EnumFields.UUID);
        mapNames.put("алко-коды", EnumFields.ALCO_CODES);

        SEQUENCE.addAll(Arrays.asList(env.getProperty("sequence").split(";")));

        mapFunc.put("объем алкогольной тары", (cell, list, id, nameColumn) -> {
            if (!cell.toString().trim().isEmpty()) {
                Double d = null;
                try {
                    d = Double.valueOf(cell.toString());
                } catch (NumberFormatException e) {
                    list.add(id + " строка -  Товар был загружен - Предупреждение: должно быть число [ 0 .. 99.999 ] столбец " + nameColumn + " выставлено - 0" + " " + ConstantsErrorWarning.NOT_ALCOHOL_BY_VOLUME);
            }
                return d;
            }
            return null;
        });

        mapFunc.put("код алкоголя", (cell, list, id, nameColumn) -> {
            if (!cell.toString().trim().isEmpty()) {
                Double d = null;
                try {
                    d = Double.parseDouble(cell.toString());
                } catch (NumberFormatException e) {
                    list.add(id + " строка - Товар был загружен - Предупреждение: должно быть число [1 ... 999] столбец " + nameColumn + " выставлено - пусто" + " " + ConstantsErrorWarning.NOT_ALCOHOL_PRODUCT_KIND_CODE);
                }
                return d;
            }
            return null;
        });

        mapFunc.put("артикул", (cell, list, id, nameColumn) -> {
            if (!cell.toString().trim().isEmpty()) {
                // позвонили попросили выгружать артикул без .0
                if (cell.toString().trim().endsWith(".0")) {
                    return cell.toString().trim().substring(0, cell.toString().length() - 2);
                }
                return cell.toString().trim();
            }
            return null;
        });

        mapFunc.put("штрих-коды", (cell, list, id, nameColumn) -> {
            if (!cell.toString().trim().isEmpty()) {
                return new ArrayList<>(Arrays.asList(cell.toString().split(" ")));
            }
            return null;
        });
        mapFunc.put("код", (cell, field, id, nameColumn) -> {
            if (!cell.toString().trim().isEmpty()) {
                return cell.toString().trim();
            }
            return null;
        });

        mapFunc.put("цена закупки", (cell, list, id, nameColumn) -> {
            Double d = null;
            if (!cell.toString().trim().isEmpty()) {
                try {
                    d = Double.valueOf(cell.toString().replace(",", "."));
                } catch (NumberFormatException e) {
                    list.add(id + " строка - Товар был загружен - Предупреждение: должно быть число [ 0 .. 9999999.99 ] столбец " + nameColumn + " выставлено - 0" + " " + ConstantsErrorWarning.NOT_COST_PRICE);
                }
            }
            return d;
        });
        mapFunc.put("цена", (cell, list, id, nameColumn) -> {
            Double d = null;
            if (cell.toString().isEmpty()) {
                list.add(id + " строка - Товар был загружен - Предупреждение: должно быть число [ 0 .. 9999999.99 ] столбец " + nameColumn + " выставлено - 0" + " " + ConstantsErrorWarning.NOT_PRICE);
            } else if (!cell.toString().trim().isEmpty()) {
                try {
                    d = Double.valueOf(cell.toString().replace(",", "."));
                } catch (NumberFormatException e) {
                    list.add(id + " строка - Товар был загружен - Предупреждение: должно быть число [ 0 .. 9999999.999 ] столбец " + nameColumn + " выставлено - 0" + " " + ConstantsErrorWarning.NOT_PRICE);
                }
            } else {
                list.add(id + " строка - Товар был загружен - Предупреждение: должно быть число [ 0 .. 9999999.99 ] столбец " + nameColumn + " выставлено - 0" + " " + ConstantsErrorWarning.NOT_PRICE);
            }
            return d;
        });
        mapFunc.put("описание", (cell, list, id, nameColumn) -> {
            if (!cell.toString().isEmpty()) {
                return cell.toString().trim();
            }
            return null;
        });
        mapFunc.put("группа", (cell, list, id, nameColumn) -> {
            if (!cell.toString().isEmpty()) {
                if (cell.toString().trim().equals("1.0") || cell.toString().trim().equals("0.0") || cell.toString().trim().equals("1") || cell.toString().trim().equals("0")) {
                    return cell.toString().equals("1.0") || cell.toString().equals("1");
                } else {
                    list.add(id + " строка - Товар был загружен - Предупреждение: 0 если товар не является группой и 1 если является " + nameColumn + " выставлено - 0" + " " + ConstantsErrorWarning.NOT_GROUP);
                }
            }
            return null;
        });

        mapFunc.put("название меры", (cell, list, id, nameColumn) -> {
            if (!cell.toString().isEmpty()) {
                if (Arrays.asList(EnumFields.MEASURE_NAME.value).contains(cell.toString().trim())) {
                    return cell.toString().trim();
                } else {
                    list.add(id + " строка - Товар был загружен - Предупреждение: система подсчета остатков товара должна содержать одно из " + Arrays.asList(EnumFields.MEASURE_NAME.value) + " выставлено - шт." + " " + ConstantsErrorWarning.NOT_MEASURE_NAME);
                }
            }
            return null;
        });

        mapFunc.put("имя", (cell, list, id, nameColumn) -> {
            if (cell.toString().isEmpty()) {
                list.add(id + " строка - Поле имя не может быть пустым " + ConstantsErrorWarning.NOT_NAME);
            } else {
                return cell.toString().trim();
            }
            return null;
        });

        mapFunc.put("код группы", (cell, list, id, nameColumn) -> {
            if (!cell.toString().isEmpty()) {
                return cell.toString().trim();
            }
            return null;
        });

        mapFunc.put("количество", (cell, list, id, nameColumn) -> {
            if (cell.toString().isEmpty()) {
                return null;
            } else if (!cell.toString().isEmpty()) {
                try {
                    return Double.valueOf(cell.toString().trim().replace(",", "."));
                } catch (NumberFormatException e) {
                    list.add(id + " строка -  Товар был загружен - Предупреждение: должно быть число [ 0 .. 9999999.999 ] столбец " + nameColumn + " выставлено - 0.0 " + ConstantsErrorWarning.NOT_QUANTITY);
                }
            }
            return null;
        });

        mapFunc.put("объем тары", (cell, list, id, nameColumn) -> {
            if (!cell.toString().isEmpty()) {
                try {
                    return Double.valueOf(cell.toString().trim());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return null;
        });

        mapFunc.put("налог", (cell, list, id, nameColumn) -> {
            if (!cell.toString().trim().isEmpty()) {
                if (Arrays.asList(EnumFields.TAX.value).contains(cell.toString().trim())) {
                    return cell.toString().trim().toUpperCase();
                } else {
                    list.add(id + " строка - Товар был загружен - Предупреждение: должен иметь значения " + Arrays.toString(EnumFields.TAX.value) + " выставлено - NO_VAT " + ConstantsErrorWarning.NOT_TAX);
                }
            }
            return null;
        });

        mapFunc.put("тип", (cell, list, id, nameColumn) ->

        {
            if (!cell.toString().isEmpty()) {
                if (Arrays.asList(EnumFields.TYPE.value).contains(cell.toString().toLowerCase().trim())) {
                    return cell.toString().trim().toUpperCase();
                } else {
                    list.add(id + " строка - Товар был загружен - Предупреждение: должен иметь значения " + Arrays.toString(EnumFields.TYPE.value) + " выставлено - NORMAL " + ConstantsErrorWarning.NOT_TYPE);
                }
            }
            return null;
        });

        mapFunc.put("разрешено к продаже", (cell, list, id, nameColumn) -> {
            if (!cell.toString().isEmpty()) {
                if (cell.toString().trim().equals("1.0") || cell.toString().trim().equals("1")) {
                    return true;
                } else if (cell.toString().trim().equals("0") || cell.toString().trim().equals("0.0")) {
                    return false;
                }
            }
            return null;
        });

        mapFunc.put("uuid", (cell, list, id, nameColumn) -> {
            if (!cell.toString().isEmpty()) {
                if (cell.toString().trim().matches("[0-9A-f]{8}-[0-9A-f]{4}-[0-9A-f]{4}-[0-9A-f]{4}-[0-9A-f]{12}")) {
                    return cell.toString().trim();
                } else {
                    list.add(id + " строка - Товар был загружен - Предупреждение: программма генерирует автоматически, оставляйте пустым при первой загрузке товара" + nameColumn + " " + ConstantsErrorWarning.NOT_UUID);
                }
            }
            return null;
        });

        mapFunc.put("алко-коды", (cell, list, id, nameColumn) -> {
            if (!cell.toString().isEmpty()) {
                return Arrays.asList(cell.toString().trim().split(" "));
            }
            return null;
        });

    }

    public final HashMap<String, ThirdFunction<Cell, List<String>, Object>> mapFunc = new HashMap<>();
}
