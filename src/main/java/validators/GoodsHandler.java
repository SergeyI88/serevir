package validators;

import com.fasterxml.uuid.Generators;
import consts.ConstantsErrorWarning;
import consts.EnumFields;
import http.entity.Good;
import http.impl.GetGoodsImpl;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GoodsHandler {

    private List<String> errors;
    private List<Good> goods;
    private static final List<String> CONST_ALCO = Arrays.asList("alcohol_not_marked", "alcohol_marked");

    public List<Good> getGoods() {
        return goods;
    }

    public List<Good> getRemoveList() {
        return removeList;
    }

    private List<Good> removeList = new ArrayList<>();
    private String token;
    private String storeUuid;
    private int countAttempts = 0;
    private Double maxCode;
    private GetGoodsImpl getGoods = new GetGoodsImpl();

    public GoodsHandler(List<String> errors, List<Good> goods, String token,
                        String storeUuid) {
        this.errors = errors;
        this.goods = goods;
        this.token = token;
        this.storeUuid = storeUuid;

    }

    public void handleList() {
        List<Good> fromEvotorGoods = null;
        try {
            fromEvotorGoods = getGoods.get(storeUuid, token);
            if (fromEvotorGoods == null) {
                throw new IOException();
            }
        } catch (IOException e) {
            if (countAttempts++ > 2) {
                handleList();
            } else {
                errors.add("Нет ответа от сервера, пожалуйста попробуйте позже " + ConstantsErrorWarning.SERVER_NOT_ANSWER);
                e.printStackTrace();
            }
        }

        setDefaultAddInRemove(fromEvotorGoods);
        unionGoods(fromEvotorGoods);
        replaceCodeAndParentUuidOrRemoveThisSequenceGroup();
        checkUniqueCodeUuidAndWriteThemIfIsNotPresent(fromEvotorGoods);
    }


    private void checkUniqueCodeUuidAndWriteThemIfIsNotPresent(List<Good> fromEvotorGoods) {
        if (fromEvotorGoods == null) {
            return;
        }
        fromEvotorGoods.addAll(goods);
        List<String> alreadyExists = new ArrayList<>();
        for (int i = 0; i < fromEvotorGoods.size(); i++) {
            Good out = fromEvotorGoods.get(i);
            if (out == null) {
                continue;
            }
            for (Good in : goods) {
                if (in == null) {
                    continue;
                }
                // если товар не один и тот же объект и равен uuid
                if (!out.equals(in) && out.getUuid().equals(in.getUuid()) && !alreadyExists.contains("" + in.getId() + out.getId())) {
                    if (out.getGroup() && !in.getGroup()) {
                        in.setUuid(Generators.timeBasedGenerator().generate().toString());
                    } else if (!out.getGroup() && in.getGroup()) {
                        out.setUuid(Generators.timeBasedGenerator().generate().toString());
                    } else if (!out.getGroup() && !in.getGroup()) {
                        in.setUuid(Generators.timeBasedGenerator().generate().toString());
                    } else {
                        alreadyExists.add("" + out.getId() + in.getId());
                        errors.add(in.getId() + " строка - Возник конфликт, есть две группы с одинаковым uuid оставьте его пустым " + ConstantsErrorWarning.CONFLICT_TWO_GROUPS);
                        continue;
                    }
                }
                if (!out.equals(in) && out.getCode().equals(in.getCode())) {
                    if (out.getGroup()) {
                        in.setCode((maxCode++).toString());
                    } else {
                        out.setCode((maxCode++).toString());
                    }
                }
            }
        }
    }

    private void unionGoods(List<Good> goodsEvotor) {
        if (goodsEvotor == null || goodsEvotor.isEmpty()) {
            return;
        }
        goods = goods
                .stream()
                .map(g -> goodsEvotor.contains(g) ? mergeOfGoods(g, goodsEvotor.get(goodsEvotor.indexOf(g))) : g)
                .collect(Collectors.toList());
    }

    private Good mergeOfGoods(Good file, Good evotor) {
        file.setUuid(evotor.getUuid());
        return file;
    }

    private void setDefaultAddInRemove(List<Good> fromEvotorGoods) {
        if (fromEvotorGoods == null && fromEvotorGoods.isEmpty()) {
            return;
        }
        fromEvotorGoods.addAll(goods);
        maxCode = fromEvotorGoods
                .stream()
                .filter(g -> g.getCode() != null && g.getCode()
                        .matches("[0-9.]+"))
                .map(g -> Double.valueOf(g.getCode()))
                .max(Comparator.naturalOrder()).orElse(1.0);
        for (int i = 0; i < goods.size(); i++) {
            Good g = goods.get(i);
            if (g.getName().equals("-")) {
                removeList.add(g);
                goods.remove(i--);
                continue;
            }
            if (g.getCode() == null) {
                g.setCode((maxCode++).toString());
            }
            if (g.getPrice() == null) {
                g.setPrice((Double) EnumFields.PRICE.defaultValue);
            }
            if (g.getGroup() == null) {
                g.setGroup((Boolean) EnumFields.GROUP.defaultValue);
            }
            if (g.getParentUuid() == null) {
                g.setParentUuid((String) EnumFields.PARENT_UUID.defaultValue);
            }
            if (g.getUuid() == null) {
                g.setUuid(Generators.timeBasedGenerator().generate().toString());
            }
            if (g.getQuantity() == null) {
                g.setQuantity((Double) EnumFields.QUANTITY.defaultValue);
            }
            if (g.getAllowToSell() == null) {
                g.setAllowToSell((Boolean) EnumFields.ALLOW_TO_SELL.defaultValue);
            }
            if (g.getTax() == null) {
                g.setTax((String) EnumFields.TAX.defaultValue);
            }
            if (g.getAlcoCodes() == null) {
                g.setAlcoCodes((List<Object>) EnumFields.ALCO_CODES.defaultValue);
            }
            if (g.getBarCodes() == null) {
                g.setBarCodes((List<Object>) EnumFields.BAR_CODES.defaultValue);
            }
            if (g.getAlcoholByVolume() == null) {
                g.setAlcoholByVolume((Double) EnumFields.ALCOHOL_BY_VOLUME.defaultValue);
            }
            if (g.getQuantity() == null) {
                g.setQuantity((Double) EnumFields.ALCOHOL_PRODUCT_KIND_CODE.defaultValue);
            }
            if (g.getArticleNumber() == null) {
                g.setArticleNumber((String) EnumFields.ARTICLE_NUMBER.defaultValue);
            }
            if (g.getCostPrice() == null) {
                g.setCostPrice((Double) EnumFields.COST_PRICE.defaultValue);
            }
            if (g.getTareVolume() == null) {
                g.setTareVolume((Double) EnumFields.TARE_VOLUME.defaultValue);
            }
            if (g.getMeasureName() == null) {
                g.setMeasureName((String) EnumFields.MEASURE_NAME.defaultValue);
            }
            if (g.getDescription() == null) {
                g.setDescription((String) EnumFields.DESCRIPTION.defaultValue);
            }
            if (g.getType() == null) {
                g.setType((String) EnumFields.TYPE.defaultValue);
            }
            if (CONST_ALCO.contains(g.getType().trim().toLowerCase()) && g.getAlcoCodes() == null) {
                errors.add(g.getId() + " " + ConstantsErrorWarning.MISSING_CODE_ALCO_OF_ALCOHOL);
            }
        }

    }

    private void replaceCodeAndParentUuidOrRemoveThisSequenceGroup() {
        Map<String, Good> mapCodeAndHimGood = goods.stream()
                .filter(Good::getGroup)
                .collect(Collectors.toMap(Good::getCode, g -> g, (good, good2) -> good));
        for (Good g : goods) {
            if (mapCodeAndHimGood.containsKey(g.getParentUuid())) {
                g.setParentUuid(mapCodeAndHimGood.get(g.getParentUuid()).getUuid());
            } else if (g.getParentUuid() != null) {
                g.setParentUuid(null);
                errors.add(g.getId() + " строка - товар был загружен - Предупреждение: такой группы не существует " + ConstantsErrorWarning.NOT_FOUND_GROUP);
            }
        }
    }

    public List<String> getErrors() {
        return errors;
    }
}
