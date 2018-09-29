package utils;

import consts.EnumFields;
import db.entity.Shop;
import http.entity.Good;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.ShopService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public class CreateXlsxFromEvotor {

    @Autowired
    ShopService shopService;

//    private class Node {
//        public Node(Good good) {
//            this.good = good;
//        }
//
//        public Node(Good good, int i) {
//            index = i;
//            this.good = good;
//        }
//
//        private int index;
//
//        public int getIndex() {
//            return index;
//        }
//
//        public void setIndex(int index) {
//            this.index = index;
//        }
//
//        public Boolean getBoss() {
//            return boss;
//        }
//
//        public void setBoss(Boolean boss) {
//            this.boss = boss;
//        }
//
//        Boolean boss;
//
//        private Good good;
//
//        public Good getGood() {
//            return good;
//        }
//
//        public void setGood(Good good) {
//            this.good = good;
//        }
//
//        public List<Node> getNodes() {
//            return nodes;
//        }
//
//        public void setNodes(List<Node> nodes) {
//            this.nodes = nodes;
//        }
//
//        private List<Node> nodes;
//    }

//    public String sortListGood(List<Good> goods, Map<String, String> uuidWithCodeForSwap) {
//        Map<String, Node> sortMap = new HashMap<>();
//        for (Good good : goods) {
//            String parentUuid = good.getParentUuid();
//            if (parentUuid == null || parentUuid.equals("")) {
//                if (sortMap.containsKey(good.getUuid())) {
//                    Node node = sortMap.get(good.getUuid());
//                    node.setGood(good);
//                    sortMap.replace(good.getUuid(), node);
//                } else {
//                    Node node = new Node(good);
//                    node.setBoss(true);
//                    sortMap.put(good.getUuid(), node);
//                }
//            } else {
//                if (sortMap.containsKey(parentUuid)) {
//                    Node nodeParent = sortMap.get(parentUuid);
//                    Node node = null;
//                    if (sortMap.containsKey(good.getUuid())) {
//                        node = sortMap.get(good.getUuid());
//                        node.setGood(good);
//                    } else {
//                        node = new Node(good);
//                    }
//                    node.setBoss(false);
//                    sortMap.put(good.getUuid(), node);
//                    List<Node> nodes = null;
//                    if (nodeParent.getNodes() != null) {
//                        nodes = nodeParent.getNodes();
//                        nodes.add(node);
//                    } else {
//                        nodes = new ArrayList<>();
//                        nodes.add(node);
//                    }
//                    nodeParent.setNodes(nodes);
//                    sortMap.replace(parentUuid, nodeParent);
//
//                } else {
//                    Node node = null;
//                    if (sortMap.containsKey(good.getUuid())) {
//                        node = sortMap.get(good.getUuid());
//                        node.setGood(good);
//                    } else {
//                        node = new Node(good);
//                    }
//                    node.setBoss(false);
//                    Node nodeParent = new Node(null);
//                    List<Node> list = new ArrayList<>();
//                    list.add(node);
//                    nodeParent.setBoss(true);
//                    nodeParent.setNodes(list);
//                    sortMap.put(parentUuid, nodeParent);
//                    sortMap.put(good.getUuid(), node);
//                }
//            }
//        }
//        goods.clear();
//        List<Node> justGood = new ArrayList<>();
//        for (Map.Entry<String, Node> entry : sortMap.entrySet()) {
//            if (entry.getValue().getBoss()) {
//                if (entry.getValue().getNodes() == null && !entry.getValue().getGood().getGroup()) {
//                    justGood.add(entry.getValue());
//                } else {
//                    Node node = entry.getValue();
//                    putIntoList(goods, node);
//                }
//            }
//        }
//        String uuidFirstGood = null;
//        if (!justGood.isEmpty()) {
//            uuidFirstGood = justGood.get(0).getGood().getUuid();
//        }
//        for (Node node : justGood) {
//            putIntoList(goods, node);
//        }
//        return uuidFirstGood;
//    }
//
//    private void putIntoList(List<Good> goods, Node node) {
//        if (node.getNodes() == null) {
//            goods.add(node.getGood());
//            return;
//        } else {
//            goods.add(node.getGood());
//            List<Node> groups = new ArrayList<>();
//            for (Node innerNode : node.getNodes()) {
//                if (innerNode.getNodes() == null && !innerNode.getGood().getGroup()) {
//                    putIntoList(goods, innerNode);
//                } else {
//                    groups.add(innerNode);
//                }
//            }
//            for (Node group : groups) {
//                putIntoList(goods, group);
//            }
//        }
//    }


    public Workbook getWorkbook(List<Good> goods, Shop shop) {
        byte indexColumnNameGood = 0;
//        Map<String, String> uuidWithCodeForSwap = new HashMap<>();
//        for (Good good : goods) {
//            uuidWithCodeForSwap.put(good.getUuid(), good.getCode());
//        }

        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet(shop.getName());

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

//        Font groupFont = workbook.createFont();
//        Font podGroupFont = workbook.createFont();

//        groupFont.setColor(IndexedColors.RED1.getIndex());
//        podGroupFont.setColor(IndexedColors.YELLOW1.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        CellStyle groupCellStyle = workbook.createCellStyle();
        groupCellStyle.setFillForegroundColor(IndexedColors.RED1.getIndex());
        groupCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        groupCellStyle.setFont(groupFont);
        CellStyle podGroupCellStyle = workbook.createCellStyle();
        podGroupCellStyle.setFillForegroundColor(IndexedColors.YELLOW1.getIndex());
        podGroupCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        podGroupCellStyle.setFont(podGroupFont);
        CellStyle firstGoodCellStyle = workbook.createCellStyle();
        firstGoodCellStyle.setBorderTop(BorderStyle.MEDIUM);

        Row headerRow = sheet.createRow(0);
        List<String> columnList = Arrays.asList(shopService.getSequance(shop.getUuid()).trim().split(";"));
        for (int i = 0; i < columnList.size(); i++) {
            Cell cell = headerRow.createCell(i);
            if (columnList.get(i).equals(EnumFields.NAME.name)) {
                indexColumnNameGood = (byte) i;
            }
            cell.setCellValue(columnList.get(i));
            cell.setCellStyle(headerCellStyle);
        }

        int rowNum = 1;
        if (goods == null) {
            for (int i = 0; i < columnList.size(); i++) {
                sheet.autoSizeColumn(i);
            }
            return workbook;
        }

//        String uuidFirstGood = sortListGood(goods, uuidWithCodeForSwap);
        replaceCodeAndParentUuid(goods);
        for (Good good : myAlgho(goods)) {
            CellStyle justCellStyle = workbook.createCellStyle();
//                String parentUuid = good.getParentUuid();
//                if (parentUuid != null && uuidWithCodeForSwap.containsKey(parentUuid)) {
//                    String code = uuidWithCodeForSwap.get(parentUuid);
//                    good.setParentUuid(code);
//                }
            Row row = sheet.createRow(rowNum++);

            if (good.getParentUuid() == null && good.getGroup()) {
                justCellStyle = groupCellStyle;
            } else if (good.getParentUuid() != null && good.getGroup()) {
                justCellStyle = podGroupCellStyle;
            }
//                if (uuidFirstGood != null) {
//                    if (good.getUuid().equals(uuidFirstGood)) {
//                        justCellStyle = firstGoodCellStyle;
//                    }
//                }

            Cell cell1 = row.createCell(columnList.indexOf("uuid"));
            cell1.setCellValue(good.getUuid() != null ? good.getUuid() : "");
            cell1.setCellStyle(justCellStyle);

            Cell cell2 = row.createCell(columnList.indexOf("код"));
            cell2.setCellValue(good.getCode() != null ? good.getCode() : "");
            cell2.setCellStyle(justCellStyle);

            StringBuilder barCodes = new StringBuilder();
            if (good.getBarCodes() != null || good.getGroup()) {
                if (good.getBarCodes() != null) {
                    for (Object obj : good.getBarCodes()) {
                        String str = (String) obj;
                        barCodes.append(str);
                    }
                }
                Cell cell3 = row.createCell(columnList.indexOf("штрих-коды"));
                cell3.setCellValue(barCodes.toString());
                cell3.setCellStyle(justCellStyle);
            }

            StringBuilder AlcoCodes = new StringBuilder();
            if (good.getAlcoCodes() != null || good.getGroup()) {
                if (good.getAlcoCodes() != null) {
                    for (Object obj : good.getAlcoCodes()) {
                        String str = (String) obj;
                        AlcoCodes.append(str);
                    }
                }
                Cell cell4 = row.createCell(columnList.indexOf("алко-коды"));
                cell4.setCellValue(AlcoCodes.toString());
                cell4.setCellStyle(justCellStyle);
            }

            Cell cell5 = row.createCell(columnList.indexOf("имя"));
            cell5.setCellValue(good.getName() != null ? good.getName() : "");
            cell5.setCellStyle(justCellStyle);

            Cell cell6 = row.createCell(columnList.indexOf("цена"));
            cell6.setCellValue(good.getPrice() != null ? good.getPrice() : 0);
            cell6.setCellStyle(justCellStyle);

            Cell cell7 = row.createCell(columnList.indexOf("количество"));
            cell7.setCellValue(good.getQuantity() != null ? good.getQuantity() : 0);
            cell7.setCellStyle(justCellStyle);

            Cell cell8 = row.createCell(columnList.indexOf("цена закупки"));
            cell8.setCellValue(good.getCostPrice() != null ? good.getCostPrice() : 0);
            cell8.setCellStyle(justCellStyle);

            Cell cell9 = row.createCell(columnList.indexOf("название меры"));
            cell9.setCellValue(good.getMeasureName() != null ? good.getMeasureName() : "");
            cell9.setCellStyle(justCellStyle);

            Cell cell10 = row.createCell(columnList.indexOf("налог"));
            cell10.setCellValue(good.getTax() != null ? good.getTax() : "");
            cell10.setCellStyle(justCellStyle);

            Cell cell11 = row.createCell(columnList.indexOf("разрешено к продаже"));
            cell11.setCellValue(good.getAllowToSell() != null ? good.getAllowToSell() ? "1" : "0" : "");
            cell11.setCellStyle(justCellStyle);

            Cell cell12 = row.createCell(columnList.indexOf("описание"));
            cell12.setCellValue(good.getDescription() != null ? good.getDescription() : "");
            cell12.setCellStyle(justCellStyle);

            Cell cell13 = row.createCell(columnList.indexOf("артикул"));
            cell13.setCellValue(good.getArticleNumber() != null ? good.getArticleNumber() : "");
            cell13.setCellStyle(justCellStyle);

            Cell cell14 = row.createCell(columnList.indexOf("код группы"));
            cell14.setCellValue(good.getParentUuid() != null ? good.getParentUuid() : "");
            cell14.setCellStyle(justCellStyle);

            Cell cell15 = row.createCell(columnList.indexOf("группа"));
            cell15.setCellValue(good.getGroup() != null ? good.getGroup() ? "1" : "0" : "");
            cell15.setCellStyle(justCellStyle);

            Cell cell16 = row.createCell(columnList.indexOf("тип"));
            cell16.setCellValue(good.getType() != null ? good.getType() : "");
            cell16.setCellStyle(justCellStyle);

            Cell cell17 = row.createCell(columnList.indexOf("объем алкогольной тары"));
            cell17.setCellValue(good.getAlcoholByVolume() != null ? good.getAlcoholByVolume().toString() : "");
            cell17.setCellStyle(justCellStyle);

            Cell cell18 = row.createCell(columnList.indexOf("код алкоголя"));
            cell18.setCellValue(good.getAlcoholProductKindCode() != null ? good.getAlcoholProductKindCode().toString() : "");
            cell18.setCellStyle(justCellStyle);

            Cell cell19 = row.createCell(columnList.indexOf("объем тары"));
            cell19.setCellValue(good.getTareVolume() != null ? good.getTareVolume().toString() : "");
            cell19.setCellStyle(justCellStyle);
        }
        for (int i = 0; i < columnList.size(); i++) {
            sheet.setColumnWidth(i, 2560);
        }
        sheet.setColumnWidth(indexColumnNameGood, 7680);

        return workbook;
    }

    private List<Good> myAlgho(List<Good> goods) {
        List<Good> goodList = new ArrayList<>();
        for (Good g : goods) {
            if (g.getGroup() && g.getParentUuid() == null) {
                goodList.add(g);
                goodList.addAll(getAll(goods, g.getCode()));
            }
        }
        for (Good g : goods) {
            if (g.getParentUuid() == null && !g.getGroup()) {
                goodList.add(g);
            }
        }
        return goodList;
    }

    private List<Good> getAll(List<Good> goods, String code) {
        List<Good> commonList = new ArrayList<>();
        for (Good g : goods) {
            if (g.getParentUuid() != null && g.getParentUuid().equals(code) && !g.getGroup()) {
                commonList.add(g);
            }
        }
        for (Good g : goods) {
            if (g.getParentUuid() != null && g.getParentUuid().equals(code) && g.getGroup()) {
                commonList.add(g);
                commonList.addAll(getAllGoodsUnder(goods, g.getCode()));
            }
        }
        return commonList;
    }

    private List<Good> getAllGoodsUnder(List<Good> goods, String code) {
        List<Good> onlyGoods = new ArrayList<>();
        for (Good g : goods) {
            if (g.getParentUuid() != null && g.getParentUuid().equals(code)) {
                onlyGoods.add(g);
            }
        }
        return onlyGoods;
    }


    private void replaceCodeAndParentUuid(List<Good> goods) {
        HashMap<String, Good> hashMap = new HashMap<>();
        for (Good g : goods) {
            hashMap.put(g.getUuid(), g);
        }
        for (Good g : goods) {
            if (hashMap.containsKey(g.getParentUuid())) {
                g.setParentUuid(hashMap.get(g.getParentUuid()).getCode());
            }
        }
    }

}



