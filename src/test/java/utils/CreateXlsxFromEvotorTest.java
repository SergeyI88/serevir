package utils;

import config.Config;
import http.entity.Good;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import validators.FileHandler;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class})
public class CreateXlsxFromEvotorTest {

    @Autowired
    CreateXlsxFromEvotor createXlsxFromEvotor;

    @Autowired
    FileHandler<Workbook> handler;

    @Test
    public void sortListGood() throws IOException, InvalidFormatException, NoSuchAlgorithmException {
        Map<String, List> list = handler.getResult(WorkbookFactory.create(new File("src/test/resources/products.xlsx")));
        List<Good> goods = list.get("goods");
        for (Good good : goods) {
            System.out.println(good.toString());
        }
        System.out.println("--------------------------------------------------------------");
        createXlsxFromEvotor.sortListGood(goods);
        for (Good good : goods) {
            System.out.println(good.toString());
        }
    }


}
