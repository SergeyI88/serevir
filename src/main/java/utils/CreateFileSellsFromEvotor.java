package utils;

import controllers.json.Document;
import db.entity.Shop;
import http.entity.Good;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CreateFileSellsFromEvotor {

    public Workbook convertFromDocToGood(List<Document> documents, String shopName){
        Map<String, Good> map = new HashMap<>();

        for (Document document : documents) {
            if (document.getType().equals("SELL") || document.getType().equals("PAYBACK")) {
                List<Document.Transaction> transactions = document.getTransactions();
                for (Document.Transaction transaction : transactions) {
                    if (transaction.equals("REGISTER_POSITION")) {
                        if (document.getType().equals("SELL")) {
                            if (map.containsKey(transaction.getCommodityUuid())){
                                Good good = map.get(transaction.getCommodityUuid());
                                Double quanity = good.getQuantity();
                                quanity += transaction.getQuantity();
                                good.setQuantity(quanity);
                                map.replace(transaction.getCommodityUuid(), good);
                            } else {
                                Good good = new Good();
                                good.setUuid(transaction.getCommodityUuid());
                                good.setName(transaction.getCommodityName());
                                good.setQuantity(transaction.getQuantity());
                                good.setPrice(transaction.getPrice().doubleValue());
                                good.setCode(transaction.getCommodityCode());
                                map.put(transaction.getCommodityUuid(), good);
                            }
                        } else {
                            if (map.containsKey(transaction.getCommodityUuid())){
                                Good good = map.get(transaction.getCommodityUuid());
                                Double quanity = good.getQuantity();
                                quanity -= transaction.getQuantity();
                                good.setQuantity(quanity);
                                map.replace(transaction.getCommodityUuid(), good);
                            } else {
                                Good good = new Good();
                                good.setUuid(transaction.getCommodityUuid());
                                good.setName(transaction.getCommodityName());
                                good.setQuantity(-transaction.getQuantity());
                                good.setPrice(transaction.getPrice().doubleValue());
                                good.setCode(transaction.getCommodityCode());
                                map.put(transaction.getCommodityUuid(), good);
                            }
                        }
                    }
                }
            } else {
                continue;
            }
        }

        List<Good> goods = new ArrayList<>();
        for (Map.Entry<String, Good> entry : map.entrySet()) {
            goods.add(entry.getValue());
        }
        return getWorkbook(goods, shopName);
    }


    public Workbook getWorkbook (List<Good> goods, String shopName) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(shopName);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);
        List<String> columnList = Arrays.asList("Код", "Имя", "Количество", "Цена");

        for(int i = 0; i < columnList.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnList.get(i));
            cell.setCellStyle(headerCellStyle);
        }

        int rowNum = 1;
        if (goods == null) {
            for(int i = 0; i < columnList.size(); i++) {
                sheet.autoSizeColumn(i);
            }
            return workbook;
        }

        for(Good good: goods) {

            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(good.getCode() != null ? good.getCode() : "");
            row.createCell(1).setCellValue(good.getName() != null ? good.getName() : "");
            row.createCell(2).setCellValue(good.getQuantity() != null ? good.getQuantity() : 0);
            row.createCell(3).setCellValue(good.getPrice() != null ? good.getPrice() : 0);
        }

        for(int i = 0; i < columnList.size(); i++) {
            sheet.autoSizeColumn(i);
        }

        return workbook;
    }

}
