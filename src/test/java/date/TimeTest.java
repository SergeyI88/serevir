package date;

import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Ignore
public class TimeTest {
@Test
    public void testTime() {
        String time = "2018-06-30T18:46:57.000+0000";
        time = time.substring(0, 10);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(time, dateTimeFormatter);
        System.out.println(localDate.equals(LocalDate.now()));
    }
}
