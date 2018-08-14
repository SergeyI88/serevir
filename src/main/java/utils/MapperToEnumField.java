package utils;

import com.fasterxml.uuid.Generators;
import consts.EnumFields;
import http.entity.Good;
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

        mapFunc.put("объем алкогольной тары", (cell, field, list, good) -> {
            if (!cell.toString().trim().isEmpty()) {
                Double d = null;
                try {
                    d = Double.valueOf(cell.toString());
                } catch (NumberFormatException e) {
                    list.add(good.getId() + " " + cell.toString() + "объем алкогольной тары - должно быть число [ 0 .. 99.999 ] столбец " + field.name);
                }
                good.setAlcoholByVolume(d);
            }
            return good;
        });

        mapFunc.put("код алкоголя", (cell, field, list, good) -> {
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

        mapFunc.put("артикул", (cell, field, list, good) -> {
            if (!cell.toString().trim().isEmpty()) {
                good.setArticleNumber(cell.toString());
            }
            return good;
        });

        mapFunc.put("штрих-коды", (cell, field, list, good) -> {
            if (!cell.toString().trim().isEmpty()) {
                good.setBarCodes(new ArrayList<>(Arrays.asList(cell.toString().split(" "))));
            }
            return good;
        });
        mapFunc.put("код", (cell, field, list, good) -> {
            if (!cell.toString().trim().isEmpty()) {
                good.setCode(cell.toString());
            }
            return good;
        });

        mapFunc.put("цена закупки", (cell, field, list, good) -> {
            Double d = null;
            if (!cell.toString().trim().isEmpty()) {
                if (field.isRequired) {
                    try {
                        d = Double.valueOf(cell.toString().replace(",", "."));
                    } catch (NumberFormatException e) {
                        list.add(good.getId() + " " + cell.toString() + "цена закупки - должн быть число [ 0 .. 9999999.999 ] столбец " + field.name);
                    }
                } else {
                    try {
                        d = Double.valueOf(cell.toString().replace(",", "."));
                    } catch (NumberFormatException e) {
                        list.add(good.getId() + " " + cell.toString() + "цена закупки - должно быть число [ 0 .. 9999999.999 ] столбец " + field.name);
                    }

                }
            }
            good.setCostPrice(d);
            return good;
        });
        mapFunc.put("цена", (cell, field, list, good) -> {
            Double d = null;
            if (field.isRequired) {
                if(cell.toString() == null || cell.toString().isEmpty()) {
                    list.add(good.getId() + " " + cell.toString() + "цена обязателен к заполнению [ 0 .. 9999999.999 ] столбец " + field.name);
                } else if (!cell.toString().trim().isEmpty()) {
                    try {
                        d = Double.valueOf(cell.toString().replace(",", "."));
                    } catch (NumberFormatException e) {
                        list.add(good.getId() + " " + cell.toString() + "цена должно быть число [ 0 .. 9999999.999 ] столбец " + field.name);
                    }
                } else {
                    list.add(good.getId() + " " + cell.toString() + "цена обязателен к заполнению [ 0 .. 9999999.999 ] столбец " + field.name);
                }
            }
            good.setPrice(d);
            return good;
        });
        mapFunc.put("описание", (cell, field, list, good) -> {
            if (field.isRequired) {
                if (!cell.toString().trim().isEmpty()) {
                    good.setDescription(cell.toString());
                } else {
                    good.setDescription("");
                }
            }
            return good;
        });
        mapFunc.put("группа", (cell, field, list, good) -> {
            if (field.isRequired) {
                if (!cell.toString().trim().isEmpty()) {
                    if (cell.toString().trim().equals("1.0") || cell.toString().trim().equals("0.0") || cell.toString().trim().equals("1") || cell.toString().trim().equals("0")) {
                        good.setGroup(cell.toString().equals("1.0") || cell.toString().equals("1"));
                    } else {
                        list.add(cell.toString() + " группа должна быть 0 если не является и 1 если является группой" + field.name);
                    }
                } else {
                    good.setGroup(false);
                }
            }
            return good;
        });

        mapFunc.put("название меры", (cell, field, list, good) -> {
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

        mapFunc.put("имя", (cell, field, list, good) -> {
            if (field.isRequired) {
                if (cell.toString() == null || cell.toString().isEmpty()) {
                    list.add(good.getId() + " Поле имя не может быть пустым");
                } else if (!cell.toString().trim().isEmpty()) {
                    good.setName(cell.toString().trim());
                } else if (good.getName().trim().toLowerCase().equals("-")) {
                    good.setName("-");
                } else {
                    list.add(good.getId() + " Поле имя не может быть пустым");
                }
            }
            return good;
        });

        mapFunc.put("код группы", (cell, field, list, good) -> {
            if (!cell.toString().trim().isEmpty()) {
                good.setParentUuid(cell.toString().trim());
            }
            return good;
        });

        mapFunc.put("количество", (cell, field, list, good) -> {
            Double d = null;
            if (cell.toString() == null || cell.toString().isEmpty()) {
                list.add(good.getId() + " " + "Поле количество не может быть пустым");
            } else if (!cell.toString().trim().isEmpty()) {
                try {
                    d = Double.valueOf(cell.toString().replace(",", "."));
                } catch (NumberFormatException e) {
                    list.add(good.getId() + " " + cell.toString() + "количество должно быть число [ 0 .. 9999999.999 ] столбец " + field.name);
                }
            } else {
                if (field.isRequired) {
                    list.add(good.getId() + " " + "Поле количество не может быть пустым");
                }
            }
            good.setQuantity(d);
            return good;
        });

        mapFunc.put("объем тары", (cell, field, list, good) -> {
            Double d = null;
            if (!cell.toString().trim().isEmpty()) {
                try {
                    d = Double.valueOf(cell.toString());
                } catch (NumberFormatException e) {
                    list.add(good.getId() + " " + cell.toString() + "объем тары должно быть число [ 0 .. 999.999 ] столбец " + field.name);
                }
            } else {
                if (field.isRequired) {
                    list.add(good.getId() + " " + "Поле объем тары не может быть пустым");
                }
            }
            good.setTareVolume(d);
            return good;
        });

        mapFunc.put("налог", (cell, field, list, good) -> {
            if (field.isRequired) {
                if (!cell.toString().trim().isEmpty()) {
                    if (Arrays.asList(field.value).contains(cell.toString().trim())) {
                        good.setTax(cell.toString().trim());
                    } else {
                        list.add(good.getId() + " " + cell.toString() + " система налогооблажения дожна быть представлена как " + Arrays.asList(field.value) + " если пусто ставится упрощенная система");
                    }
                } else {
                    good.setTax("NO_VAT");
                }
            }
            return good;
        });

        mapFunc.put("тип", (cell, field, list, good) -> {
            if (field.isRequired) {
                if (!cell.toString().trim().isEmpty()) {
                    if (Arrays.asList(field.value).contains(cell.toString().toLowerCase().trim())) {
                        good.setType(cell.toString().trim());
                    } else {
                        list.add(good.getId() + " " + cell.toString() + " дожно быть представлено одно из " + Arrays.asList(field.value) + " если пусто ставится NORMAL");
                    }
                } else {
                    good.setType("NORMAL");
                }
            }
            return good;
        });

        mapFunc.put("разрешено к продаже", (cell, field, list, good) -> {

            if (!cell.toString().trim().isEmpty()) {
                if (cell.toString().trim().equals("1.0") || cell.toString().trim().equals("1")) {
                    good.setAllowToSell(true);
                } else if (cell.toString().trim().equals("0") || cell.toString().trim().equals("0.0")) {
                    good.setAllowToSell(false);
                } else {
                    list.add(good.getId() + " " + cell.toString() + " allowToSell должна быть 0 если товар нельзя добавлять в чек и 1 если можно" + field.name);
                }
            } else {
                good.setAllowToSell(true);
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

        mapFunc.put("алко-коды", (cell, field, list, good) -> {
            if (!cell.toString().trim().isEmpty()) {
                good.setAlcoCodes(Arrays.asList(cell.toString().split(" ")));
            }
            return good;
        });

    }

    public final HashMap<String, ThirdFunction<Cell, EnumFields, List<String>, Good, Good>> mapFunc = new HashMap<>();
}
