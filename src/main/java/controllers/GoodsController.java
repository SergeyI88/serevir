package controllers;

import consts.Const;
import db.DAO.impl.ShopDaoImpl;
import db.entity.Shop;
import http.GetGoods;
import db.DAO.impl.ShopDaoImpl;
import db.entity.Shop;
import http.SendGoods;
import http.entity.Good;
import http.entity.Good;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import service.ShopService;
import validators.FileHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GoodsController {
    private final FileHandler fileHandler;

    final static Logger logger = Logger.getLogger(GoodsController.class);
    @Autowired
    ShopService shopService;

    @Autowired
    ShopDaoImpl shopDao;

    @Autowired
    public GoodsController(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("shop") String storeUuid) {
        ModelAndView modelAndView = new ModelAndView("index");

        List<String> list = null;
        try (Workbook workbook = WorkbookFactory.create(convert(file))) {
            list = fileHandler.getResult(workbook);

            modelAndView.addObject("errors", list.isEmpty() ? new ArrayList(Arrays.asList("Товары загружены")): list);
            SendGoods sendGoods = new SendGoods();
//            sendGoods.send(list, shopService.getShop(storeUuid));
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }


    @GetMapping("/downloadGoods")
    public void downloadFile3(HttpServletResponse resonse,
                              @RequestParam String storeUuid,
                              @RequestParam String token) throws Exception {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://umorili.herokuapp.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        List<Good> goods = retrofit.create(GetGoods.class).getData(storeUuid, token).execute().body();
        List<String> listForFile = goods.stream().map(g -> g.toString()).collect(Collectors.toList());

        XSSFWorkbook workbook = new XSSFWorkbook();
        Shop shop = shopDao.getShopByUuidStore(storeUuid);
        XSSFSheet sheet = workbook.createSheet(shop.getName());

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);
        String[] columns = {"uuid", "code", "barCodes", "alcoCodes", "name", "price", "quantity", "costPrice", "measureName",
                            "tax", "allowToSell", "description", "articleNumber", "parentUuid",
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
                row.createCell(3).setCellValue(barCodes.toString());
            }
            StringBuilder AlcoCodes = new StringBuilder();
            if (good.getAlcoCodes() != null) {
                for (Object obj : good.getAlcoCodes()) {
                    String str = (String)obj;
                    AlcoCodes.append(str);
                }
                row.createCell(4).setCellValue(AlcoCodes.toString());
            }
            row.createCell(5).setCellValue(good.getName() != null ? good.getName() : "");
            row.createCell(6).setCellValue(good.getPrice() != null ? good.getPrice().toString() : "");
            row.createCell(7).setCellValue(good.getQuantity() != null ? good.getQuantity().toString() : "");
            row.createCell(8).setCellValue(good.getCostPrice() != null ? good.getCostPrice().toString() : "");
            row.createCell(9).setCellValue(good.getMeasureName() != null ? good.getMeasureName() : "");
            row.createCell(10).setCellValue(good.getTax() != null ? good.getTax() : "");
            row.createCell(11).setCellValue(good.getAllowToSell() != null ? good.getAllowToSell() ? "1" : "0" : "");
            row.createCell(12).setCellValue(good.getDescription() != null ? good.getDescription() : "");
            row.createCell(13).setCellValue(good.getArticleNumber() != null ? good.getArticleNumber() : "");
            row.createCell(14).setCellValue(good.getParentUuid() != null ? good.getParentUuid() : "");
            row.createCell(15).setCellValue(good.getGroup() != null ? good.getGroup() ? "1" : "0" : "");
            row.createCell(16).setCellValue(good.getType() != null ? good.getType() : "");
            row.createCell(17).setCellValue(good.getAlcoholByVolume() != null ? good.getAlcoholByVolume().toString() : "");
            row.createCell(18).setCellValue(good.getAlcoholProductKindCode() != null ? good.getAlcoholProductKindCode().toString() :"");
            row.createCell(19).setCellValue(good.getTareVolume() != null ? good.getTareVolume().toString() : "");
        }


        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        BufferedOutputStream outStream = new BufferedOutputStream(resonse.getOutputStream());
        resonse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "products.xlsx");
        workbook.write(outStream);
        outStream.close();
        workbook.close();
    }



    public File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
