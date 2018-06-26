package validators;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Assert;
import org.junit.Test;
import java.util.*;

import java.io.File;

public class FileHandlerTest {
    @Test
    public void checkValues() throws Exception {
    }

    @Test
    public void getResult() throws Exception {
        FileHandler<Workbook> handler = new FileHandler<>(WorkbookFactory.create(new File("C:\\projects\\Test\\src\\test\\resources\\products.xlsx")));
        List<String> list = handler.getResult();
        System.out.println(list);
        Assert.assertEquals(0, list.size());
    }

}
