package utils;

import com.fasterxml.uuid.Generators;
import consts.EnumFields;
import controllers.Good;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.HashMap;

@Component
public class MapperToEnumField {
    public final HashMap<String, EnumFields> mapNames = new HashMap<>();

    @PostConstruct
    public void init() {
        mapNames.put("alcoholByVolume", EnumFields.ALCOHOL_BY_VOLUME);
        mapNames.put("alcoholProductKindCode", EnumFields.ALCOHOL_PRODUCT_KIND_CODE);
        mapNames.put("articleNumber", EnumFields.ARTICLE_NUMBER);
        mapNames.put("barCodes", EnumFields.BAR_CODES);
        mapNames.put("code", EnumFields.CODE);
        mapNames.put("costPrice", EnumFields.COST_PRICE);
        mapNames.put("description", EnumFields.DESCRIPTION);
        mapNames.put("group", EnumFields.GROUP);
        mapNames.put("measureName", EnumFields.MEASURE_NAME);
        mapNames.put("name", EnumFields.NAME);
        mapNames.put("parentCode", EnumFields.PARENT_UUID);
        mapNames.put("price", EnumFields.PRICE);
        mapNames.put("quantity", EnumFields.QUANTITY);
        mapNames.put("tareVolume", EnumFields.TARE_VOLUME);
        mapNames.put("tax", EnumFields.TAX);
        mapNames.put("type", EnumFields.TYPE);
        mapNames.put("allowToSell", EnumFields.ALLOW_TO_SELL);
        mapNames.put("uuid", EnumFields.UUID);
        mapNames.put("alcoCodes", EnumFields.ALCO_CODES);

        mapFunc.put("alcoholByVolume", (cell, field, list, good) -> {
            if (!cell.toString().trim().isEmpty()) {
                Double d = null;
                try {
                    d = Double.valueOf(cell.toString());
                } catch (NumberFormatException e) {
                    list.add(good.getId() + " " + cell.toString() + "alcoholByVolume должно быть число [ 0 .. 99.999 ] столбец " + field.name);
                }
                good.setAlcoholByVolume(d);
            }
            return good;
        });

        mapFunc.put("alcoholProductKindCode", (cell, field, list, good) -> {
            if (!cell.toString().trim().isEmpty()) {
                Double d = null;
                try {
                    d = Double.parseDouble(cell.toString());
                } catch (NumberFormatException e) {
                    list.add(good.getId() + " " + cell.toString() + " не число [1 ... 999] столбец " + field.name);
                }
                good.setAlcoholProductKindCode(d);
            }
            return good;
        });

        mapFunc.put("articleNumber", (cell, field, list, good) -> {
            if (!cell.toString().trim().isEmpty()) {
                good.setArticleNumber(cell.toString());
            }
            return good;
        });

        mapFunc.put("barCodes", (cell, field, list, good) -> {
            if (field.isRequired) {
                if (!cell.toString().trim().isEmpty()) {
                    good.setBarCodes(new ArrayList<>(Arrays.asList(cell.toString().split(" "))));
                } else {
                    list.add(good.getId() + " " + cell.toString() + " обязательно к заполнению " + field.name);
                }
            }
            return good;
        });
        mapFunc.put("code", (cell, field, list, good) -> {
            if (field.isRequired) {
                if (!cell.toString().trim().isEmpty()) {
                    good.setCode(cell.toString());
                } else {
                    list.add("Строка " + good.getId() + " " + cell.toString() + " обязателен к заполнению [0 ... 10] " + field.name);
                }
            }
            return good;
        });

        mapFunc.put("costPrice", (cell, field, list, good) -> {
            Double d = null;
            if (!cell.toString().trim().isEmpty()) {
                if (field.isRequired) {
                    try {
                        d = Double.valueOf(cell.toString());
                    } catch (NumberFormatException e) {
                        list.add(good.getId() + " " + cell.toString() + "costPrice должно быть число [ 0 .. 9999999.999 ] столбец " + field.name);
                    }
                } else {
                    try {
                        d = Double.valueOf(cell.toString());
                    } catch (NumberFormatException e) {
                        list.add(good.getId() + " " + cell.toString() + "costPrice должно быть число [ 0 .. 9999999.999 ] столбец " + field.name);
                    }

                }
            }
            good.setCostPrice(d);
            return good;
        });
        mapFunc.put("price", (cell, field, list, good) -> {
            Double d = null;
            if (field.isRequired) {
                if (!cell.toString().trim().isEmpty()) {
                    try {
                        d = Double.valueOf(cell.toString());
                    } catch (NumberFormatException e) {
                        list.add(good.getId() + " " + cell.toString() + "costPrice должно быть число [ 0 .. 9999999.999 ] столбец " + field.name);
                    }
                } else {
                    list.add(good.getId() + " " + cell.toString() + "price обязателен к заполнению [ 0 .. 9999999.999 ] столбец " + field.name);
                }
            }
            good.setPrice(d);
            return good;
        });
        mapFunc.put("description", (cell, field, list, good) -> {
            if (field.isRequired) {
                if (!cell.toString().trim().isEmpty()) {
                    good.setDescription(cell.toString());
                } else {
                    good.setDescription("");
                }
            }
            return good;
        });
        mapFunc.put("group", (cell, field, list, good) -> {
            if (field.isRequired) {
                if (!cell.toString().trim().isEmpty()) {
                    if (cell.toString().equals("1.0") || cell.toString().equals("0.0")) {
                        good.setGroup(cell.toString().equals("1.0"));
                    } else {
                        list.add(cell.toString() + " group должна быть 0 если не является и 1 если является группой" + field.name);
                    }
                } else {
                    good.setGroup(false);
                }
            }
            return good;
        });

        mapFunc.put("measureName", (cell, field, list, good) -> {
            if (field.isRequired) {
                if (!cell.toString().trim().isEmpty()) {
                    if (Arrays.asList(field.value).contains(cell.toString().trim())) {
                        good.setMeasureName(cell.toString().trim());
                    } else {
                        list.add(good.getId() + " " + cell.toString() + " система подсчета остатков товара должна содержать одно из " + Arrays.asList(field.value) + " если пуста ставится шт");
                    }
                } else {
                    good.setMeasureName("шт");
                }
            }
            return good;
        });

        mapFunc.put("name", (cell, field, list, good) -> {
            if (field.isRequired) {
                if (!cell.toString().trim().isEmpty()) {
                    good.setName(cell.toString().trim());
                } else {
                    list.add("Поле name не может быть пустым");
                }
            }
            return good;
        });

        mapFunc.put("parentCode", (cell, field, list, good) -> {
            if (!cell.toString().trim().isEmpty()) {
                good.setParentUuid(cell.toString().trim());
            }
            return good;
        });

        mapFunc.put("quantity", (cell, field, list, good) -> {
            Double d = null;
            if (!cell.toString().trim().isEmpty()) {
                try {
                    d = Double.valueOf(cell.toString());
                } catch (NumberFormatException e) {
                    list.add(good.getId() + " " + cell.toString() + "quantity должно быть число [ 0 .. 9999999.999 ] столбец " + field.name);
                }
            } else {
                if (field.isRequired) {
                    list.add(good.getId() + " " + "Поле quantity не может быть пустым");
                }
            }
            good.setQuantity(d);
            return good;
        });

        mapFunc.put("tareVolume", (cell, field, list, good) -> {
            Double d = null;
            if (!cell.toString().trim().isEmpty()) {
                try {
                    d = Double.valueOf(cell.toString());
                } catch (NumberFormatException e) {
                    list.add(good.getId() + " " + cell.toString() + "tareVolume должно быть число [ 0 .. 999.999 ] столбец " + field.name);
                }
            } else {
                if (field.isRequired) {
                    list.add(good.getId() + " " + "Поле tareVolume не может быть пустым");
                }
            }
            good.setTareVolume(d);
            return good;
        });

        mapFunc.put("tax", (cell, field, list, good) -> {
            if (field.isRequired) {
                if (!cell.toString().trim().isEmpty()) {
                    if (Arrays.asList(field.value).contains(cell.toString().trim())) {
                        good.setMeasureName(cell.toString().trim());
                    } else {
                        list.add(good.getId() + " " + cell.toString() + " система налогооблажения дожна быть представлена как " + Arrays.asList(field.value) + " если пусто ставится упрощенная система");
                    }
                } else {
                    good.setTax("NO_VAT");
                }
            }
            return good;
        });

        mapFunc.put("type", (cell, field, list, good) -> {
            if (field.isRequired) {
                if (!cell.toString().trim().isEmpty()) {
                    if (Arrays.asList(field.value).contains(cell.toString().toLowerCase().trim())) {
                        good.setMeasureName(cell.toString().trim());
                    } else {
                        list.add(good.getId() + " " + cell.toString() + " дожно быть представлено одно из " + Arrays.asList(field.value) + " если пусто ставится NORMAL");
                    }
                } else {
                    good.setTax("NORMAL");
                }
            }
            return good;
        });

        mapFunc.put("allowToSell", (cell, field, list, good) -> {
            if (field.isRequired) {
                if (!cell.toString().trim().isEmpty()) {
                    if (cell.toString().equals("1.0") || cell.toString().equals("0.0")) {
                        good.setAllowToSell(cell.toString().equals("1"));
                    } else {
                        list.add(good.getId() + " " + cell.toString() + " allowToSell должна быть 0 если товар нельзя добавлять в чек и 1 если можно" + field.name);
                    }
                } else {
                    good.setAllowToSell(false);
                }
            }
            return good;
        });

        mapFunc.put("uuid", (cell, field, list, good) -> {
            if (!cell.toString().trim().isEmpty()) {
                if (field.isRequired) {
                    if (cell.toString().trim().matches("[0-9A-f]{8}-[0-9A-f]{4}-[0-9A-f]{4}-[0-9A-f]{4}-[0-9A-f]{12}")) {
                        good.setUuid(cell.toString().trim());
                    } else {
                        list.add(cell.toString() + " неверный формат uuid4, можете оставить пустым программа сама его заполнит" + field.name);
                    }
                }
            } else {
                good.setUuid(Generators.timeBasedGenerator().generate().toString());
            }
            return good;
        });

        mapFunc.put("alcoCodes", (cell, field, list, good) -> {
            if (!cell.toString().trim().isEmpty()) {
               good.setAlcoCodes(Arrays.asList(cell.toString().split(" ")));
            }
            return good;
        });

    }

    public final HashMap<String, ThirdFunction<Cell, EnumFields, List<String>, Good, Good>> mapFunc = new HashMap<>();
}
