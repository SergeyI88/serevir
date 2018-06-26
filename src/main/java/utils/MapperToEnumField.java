package utils;

import consts.EnumFields;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
public class MapperToEnumField {
    public final HashMap<String, EnumFields> map = new HashMap<>();
    @PostConstruct
    public void init() {
        map.put("alcoholByVolume", EnumFields.ALCOHOL_BY_VOLUME);
        map.put("alcoholProductKindCode", EnumFields.ALCOHOL_PRODUCT_KIND_CODE);
        map.put("articleNumber", EnumFields.ARTICLE_NUMBER);
        map.put("barCodes", EnumFields.BAR_CODES);
        map.put("code", EnumFields.CODE);
        map.put("costPrice", EnumFields.COST_PRICE);
        map.put("description", EnumFields.DESCRIPTION);
        map.put("group", EnumFields.GROUP);
        map.put("measureName", EnumFields.MEASURE_NAME);
        map.put("name", EnumFields.NAME);
        map.put("parentCode", EnumFields.PARENT_UUID);
        map.put("price", EnumFields.PRICE);
        map.put("quantity", EnumFields.QUANTITY);
        map.put("tareVolume", EnumFields.TARE_VOLUME);
        map.put("tax", EnumFields.TAX);
        map.put("type", EnumFields.TYPE);
    }
}
