package validators;

import config.Config;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import java.io.File;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class})
public class FileHandlerTest {
    @Autowired
    FileHandler<Workbook> handler;
    @Test
    public void checkValues() throws Exception {
    }

    @Test
    public void getResult() throws Exception {

        List<String> list = handler.getResult(WorkbookFactory.create(new File("src/test/resources/products.xlsx")));
       list.forEach(System.out::println);
//        Assert.assertEquals(0, list.size());
    }

}
