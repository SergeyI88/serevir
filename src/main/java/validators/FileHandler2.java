package validators;

import http.entity.Good;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import service.ShopService;
import utils.MapperToEnumField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

public class FileHandler2 {
    @Autowired
    private ShopService shopService;
    @Autowired
    private MapperToEnumField mapperToEnumField;


    public HashMap<String, List> getResult(Workbook workbook, String storeUuid, String auth) {
        Sheet sheet = workbook.getSheetAt(0);
        List<Good> listGood = new ArrayList<>();
        Queue<String> defaultSequence = mapperToEnumField.SEQUENCE;
        sheet.removeRow(sheet.getRow(0));
        sheet.forEach(row -> {

        });
//        sheet.getLastRowNum()
        return new HashMap<>();
    }
}
