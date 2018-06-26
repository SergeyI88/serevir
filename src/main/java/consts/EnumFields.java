package consts;

public enum EnumFields {
    NAME("name", null, true, null) //[ 1 .. 100 ] characters
    , GROUP("group", null, true, null)
    , TYPE("type", new String[]{"ALCOHOL_NOT_MARKED", "normal", "alcohol_marked", "service"}, true, null)
    , QUANTITY("quantity", null, true, true), MEASURE_NAME("measureName"
            , new String[]{"", "шт", "кг", "л", "м", "км", "м2", "м3", "компл", "упак", "ед", "дроб"}
            , true, true)
    , CODE("code", null, true, null) //[ 0 .. 10 ] characters
    , BAR_CODES("barCodes", null, true, true)
    , PRICE("price", null, true, true) //[ 0 .. 9999999.99 ] double
    , COST_PRICE("costPrice", null, false, true) //[ 0 .. 9999999.99 ] double
    , TAX("tax", new String[]{"NO_VAT", "VAT_10", "VAT_18", "VAT_0", "VAT_18_118", "VAT_10_110"}, true, true)
    , ALLOW_TO_SELL("allowToSell", null, true, true)
    , DESCRIPTION("description", null, true, true)
    , ARTICLE_NUMBER("articleNumber", null, false, true) //[ 0 .. 20 ] characters
    , PARENT_UUID("parentCode", null, false, false)
    , ALCOHOL_BY_VOLUME("alcoholByVolume", null, false, true) //[ 0 .. 99.999 ] double
    , ALCOHOL_PRODUCT_KIND_CODE("alcoholProductKindCode", null, false, true) //от 1 до 999. int
    , TARE_VOLUME("tareVolume", null, false, true); //[ 0 .. 999.999 ] double

    public String name;
    public String[] value;
    public boolean isRequired;
    public Boolean isSendIfGroupFalse;


    EnumFields(String name, String[] value, boolean isRequired, Boolean isRequiredIfGroupFalse) {
        this.name = name;
        this.value = value;
        this.isRequired = isRequired;
        this.isSendIfGroupFalse = isRequiredIfGroupFalse;
    }
}
