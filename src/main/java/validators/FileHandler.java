package validators;

import consts.EnumFields;
import controllers.Good;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import utils.MapperToEnumField;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Consumer;

@Component
@Scope("prototype")
public class FileHandler<T extends Workbook> {
    @Autowired
    MapperToEnumField mapperToEnumField;






    public FileHandler checkValues(Consumer<Cell> func, Cell cell, EnumFields field, List<String> errors) {
        func.accept(cell);
        errors.add("Ошибка в строке");
        return this;
    }

    public List<String> getResult(Workbook workbook) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        boolean match = false;
        List<String> listErrors = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(0);
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        Queue<String> sequence = new ArrayDeque<>();


        Row row = sheet.getRow(0);
        for (EnumFields e : EnumFields.values()) {
            for (Iterator<Cell> it = row.cellIterator(); it.hasNext(); ) {
                Cell c = it.next();
                if (e.name.toLowerCase().equals(c.toString().toLowerCase())) {
                    map.put(c.toString(), new ArrayList<>());
                    sequence.offer(c.toString());
                    match = true;
                    break;
                }
            }
            sheet.removeRow(row);
            if (!match) {
                listErrors.add("Колонка: " + e.name + " отсутсвует");
            }
            match = false;
        }
        Integer index = 0;
        sheet.forEach(r -> {
            r.forEach(c -> {
//                checkValues(, c, mapperToEnumField.map.get(sequence.peek()), listErrors);
            });
        });
        sequence.offer(sequence.poll());


        UUID uuid = UUID.randomUUID();
        Good good = new Good();

        return listErrors;
    }


}
