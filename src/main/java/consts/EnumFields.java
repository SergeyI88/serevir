package consts;

public enum EnumFields {
    NAME("имя", null, true, null) //[ 1 .. 100 ] characters
    , GROUP("группа", null, true, null)
    , TYPE("тип", new String[]{"alcohol_not_marked", "normal", "alcohol_marked", "service"}, true, null)
    , QUANTITY("количество", null, true, true)
    , MEASURE_NAME("название меры"
            , new String[]{"", "шт", "кг", "л", "м", "км", "м2", "м3", "компл", "упак", "ед", "дроб", "штука"}
            , true, true)
    , CODE("код", null, true, null) //[ 0 .. 10 ] characters
    , BAR_CODES("штрих-коды", null, true, true)
    , PRICE("цена", null, true, true) //[ 0 .. 9999999.99 ] double
    , COST_PRICE("цена закупки", null, false, true) //[ 0 .. 9999999.99 ] double
    , TAX("налог", new String[]{"NO_VAT", "VAT_10", "VAT_18", "VAT_0", "VAT_18_118", "VAT_10_110"}, true, true)
    , ALLOW_TO_SELL("разрешено к продаже", null, true, true)
    , DESCRIPTION("описание", null, true, true)
    , ARTICLE_NUMBER("артикул", null, false, true) //[ 0 .. 20 ] characters
    , PARENT_UUID("код группы", null, false, false)
    , ALCOHOL_BY_VOLUME("объем алкогольной тары", null, false, true) //[ 0 .. 99.999 ] double
    , ALCOHOL_PRODUCT_KIND_CODE("код алкоголя", null, false, true) //от 1 до 999. int
    , TARE_VOLUME("объем тары", null, false, true)//[ 0 .. 999.999 ] double
    , UUID("uuid", null, true, false)
    , ALCO_CODES("алко-коды", null, false, true);

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
