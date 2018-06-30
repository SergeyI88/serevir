package utils;

import http.entity.Good;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CreateXlsxFromEvotor {

    private class  Node{
        public Node(Good good) {
            this.good = good;
        }

        public Boolean getBoss() {
            return boss;
        }

        public void setBoss(Boolean boss) {
            this.boss = boss;
        }

        Boolean boss;

        private Good good;

        public Good getGood() {
            return good;
        }

        public void setGood(Good good) {
            this.good = good;
        }

        public List<Node> getNodes() {
            return nodes;
        }

        public void setNodes(List<Node> nodes) {
            this.nodes = nodes;
        }

        private List<Node> nodes;




    }


    public void sortListGood(List<Good> goods){
        Map<String, Node> sortMap = new HashMap<>();
        for (Good good : goods) {
            String parentUuid = good.getParentUuid();
            if (parentUuid == null || parentUuid.equals("")) {
                if (sortMap.containsKey(good.getUuid())) {
                    Node node = sortMap.get(good.getUuid());
                    node.setGood(good);
                    sortMap.put(good.getUuid(), node);
                } else {
                    Node node = new Node(good);
                    node.setBoss(true);
                    sortMap.put(good.getUuid(), node);
                }
            } else {
                if (sortMap.containsKey(parentUuid)) {
                    Node nodeParent = sortMap.get(parentUuid);
                    Node node = null;
                    if (sortMap.containsKey(good.getUuid())) {
                        node = sortMap.get(good.getUuid());
                        node.setGood(good);
                    } else {
                        node = new Node(good);
                    }
                    node.setBoss(false);
                    sortMap.put(good.getUuid(), node);
                    List<Node> nodes = null;
                    if (nodeParent.getNodes() != null) {
                        nodes = nodeParent.getNodes();
                        nodes.add(node);
                    } else{
                        nodes = new ArrayList<>();
                        nodes.add(node);
                    }
                    nodeParent.setNodes(nodes);
                    sortMap.put(parentUuid, nodeParent);

                } else {
                    Node node = null;
                    if (sortMap.containsKey(good.getUuid())) {
                        node = sortMap.get(good.getUuid());
                        node.setGood(good);
                    } else {
                        node = new Node(good);
                    }
                    node.setBoss(false);
                    Node nodeParent = new Node(null);
                    List<Node> list = new ArrayList<>();
                    list.add(node);
                    node.setBoss(false);
                    nodeParent.setBoss(true);
                    nodeParent.setNodes(list);
                    sortMap.put(parentUuid, nodeParent);
                    sortMap.put(good.getUuid(), node);
                }
            }
        }
        goods.clear();
        for (Map.Entry<String, Node> entry : sortMap.entrySet()) {
            if (entry.getValue().getBoss()) {
                Node node = entry.getValue();
                putIntoList(goods, node);

            }
        }
    }

    private void putIntoList(List<Good> goods, Node node){
        if (node.getNodes() == null) {
            goods.add(node.getGood());
            return;
        } else {

            for (Node innerNode : node.getNodes()) {
                putIntoList(goods, innerNode);
            }
        }
    }



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
        String[] columns = {"uuid", "код", "штрих-коды", "алко-коды", "имя", "цена", "количество", "цена закупки", "название меры",
                "налог", "разрешено к продаже", "описание", "артикул", "код группы",
                "группа", "тип", "объем алкогольной тары", "код алкоголя", "объем тары"};
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowNum = 1;
        if (goods == null) {
            for(int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            return workbook;
        }
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
