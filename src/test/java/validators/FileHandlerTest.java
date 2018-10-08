package validators;

import config.Config;
import http.entity.Good;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class})
//@Ignore
public class FileHandlerTest {
    @Autowired
    FileHandler2 handler;

    public void checkValues() throws Exception {
    }

    @Test
    public void getResult() throws Exception {

        Map<String, List> map = handler
                .getResult(WorkbookFactory.create(new File("src/test/resources/petr.xlsx"))
                        , "3f52850f-aeae-4784-8006-1029d537a8d8"
                        , "20180618-1AAC-4017-807E-33B65740B3CB");
        List<Good> goods = map.get("goods");
        List<String> errors = map.get("errors");
        assertEquals(14, goods.size());
        assertEquals(14, errors.size());
    }

}
