package consts;

public enum EnumFields {
    NAME("имя", null, true, null, "name", null) //[ 1 .. 100 ] characters
    , GROUP("группа", null, false, null, "group", Boolean.FALSE)
    , TYPE("тип", new String[]{"alcohol_not_marked", "normal", "alcohol_marked", "service"}, false, null, "type", "normal")
    , QUANTITY("количество", null, false, true, "quantity", 0.0)
    , MEASURE_NAME("название меры"
            , new String[]{"", "шт", "кг", "л", "м", "км", "м2", "м3", "компл", "упак", "ед", "дроб", "штука"}
            , false, true, "measureName", "шт")
    , CODE("код", null, false, null, "code", null) //[ 0 .. 10 ] characters
    , BAR_CODES("штрих-коды", null, false, true, "barCodes", null)
    , PRICE("цена", null, true, true, "price", 0.0) //[ 0 .. 9999999.99 ] double
    , COST_PRICE("цена закупки", null, false, true, "costPrice", 0.0) //[ 0 .. 9999999.99 ] double
    , TAX("налог", new String[]{"NO_VAT", "VAT_10", "VAT_18", "VAT_0", "VAT_18_118", "VAT_10_110"}, false, true, "tax", "NO_VAT")
    , ALLOW_TO_SELL("разрешено к продаже", null, false, true, "allowToSell", Boolean.TRUE)
    , DESCRIPTION("описание", null, false, true, "description", "")
    , ARTICLE_NUMBER("артикул", null, false, true, "articleNumber", "") //[ 0 .. 20 ] characters
    , PARENT_UUID("код группы", null, false, false, "parentUuid", null)
    , ALCOHOL_BY_VOLUME("объем алкогольной тары", null, false, true, "alcoholByVolume", 0.0) //[ 0 .. 99.999 ] double
    , ALCOHOL_PRODUCT_KIND_CODE("код алкоголя", null, false, true, "alcoholProductKindCode", 1) //от 1 до 999. int
    , TARE_VOLUME("объем тары", null, false, true, "tareVolume", 0.0)//[ 0 .. 999.999 ] double
    , UUID("uuid", null, false, false, "uuid", null)
    , ALCO_CODES("алко-коды", null, false, false, "alcoCodes", null);

    public String nameColumn;
    public String[] value;
    public boolean isRequired;
    public Boolean isSendIfGroupFalse;
    public String nameFieldForReflection;
    public Object defaultValue;


    EnumFields(String nameColumn, String[] value, boolean isRequired
            , Boolean isRequiredIfGroupFalse, String nameFieldForReflection
            , Object defaultValue) {
        this.nameColumn = nameColumn;
        this.value = value;
        this.isRequired = isRequired;
        this.isSendIfGroupFalse = isRequiredIfGroupFalse;
        this.nameFieldForReflection = nameFieldForReflection;
        this.defaultValue = defaultValue;
    }
}
