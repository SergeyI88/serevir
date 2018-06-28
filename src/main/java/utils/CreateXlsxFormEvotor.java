package utils;

import http.entity.Good;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreateXlsxFormEvotor {


    public Workbook getWorkbook (List<Good> goods, String shopName){
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet(shopName);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);
        String[] columns = {"uuid", "code", "barCodes", "alcoCodes", "name", "price", "quantity", "costPrice", "measureName",
                "tax", "allowToSell", "description", "articleNumber", "parentCode",
                "group", "type", "alcoholByVolume", "alcoholProductKindCode", "tareVolume"};
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowNum = 1;
        for(Good good: goods) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(good.getUuid() != null ? good.getUuid() : "");
            row.createCell(1).setCellValue(good.getCode() != null ? good.getCode() : "");
            StringBuilder barCodes = new StringBuilder();
            if (good.getBarCodes() != null) {
                for (Object obj : good.getBarCodes()) {
                    String str = (String)obj;
                    barCodes.append(str);
                }
                row.createCell(2).setCellValue(barCodes.toString());
            }
            StringBuilder AlcoCodes = new StringBuilder();
            if (good.getAlcoCodes() != null) {
                for (Object obj : good.getAlcoCodes()) {
                    String str = (String)obj;
                    AlcoCodes.append(str);
                }
                row.createCell(3).setCellValue(AlcoCodes.toString());
            }
            row.createCell(4).setCellValue(good.getName() != null ? good.getName() : "");
            row.createCell(5).setCellValue(good.getPrice() != null ? good.getPrice().toString() : "");
            row.createCell(6).setCellValue(good.getQuantity() != null ? good.getQuantity().toString() : "");
            row.createCell(7).setCellValue(good.getCostPrice() != null ? good.getCostPrice().toString() : "");
            row.createCell(8).setCellValue(good.getMeasureName() != null ? good.getMeasureName() : "");
            row.createCell(9).setCellValue(good.getTax() != null ? good.getTax() : "");
            row.createCell(10).setCellValue(good.getAllowToSell() != null ? good.getAllowToSell() ? "1" : "0" : "");
            row.createCell(11).setCellValue(good.getDescription() != null ? good.getDescription() : "");
            row.createCell(12).setCellValue(good.getArticleNumber() != null ? good.getArticleNumber() : "");
            row.createCell(13).setCellValue(good.getParentUuid() != null ? good.getParentUuid() : "");
            row.createCell(14).setCellValue(good.getGroup() != null ? good.getGroup() ? "1" : "0" : "");
            row.createCell(15).setCellValue(good.getType() != null ? good.getType() : "");
            row.createCell(16).setCellValue(good.getAlcoholByVolume() != null ? good.getAlcoholByVolume().toString() : "");
            row.createCell(17).setCellValue(good.getAlcoholProductKindCode() != null ? good.getAlcoholProductKindCode().toString() :"");
            row.createCell(18).setCellValue(good.getTareVolume() != null ? good.getTareVolume().toString() : "");
        }
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        return workbook;
    }
}