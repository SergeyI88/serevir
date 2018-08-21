package controllers;

import com.google.gson.Gson;
import controllers.json.Document;
import db.DAO.impl.ShopDaoImpl;
import db.entity.Shop;
import http.impl.DeleteGoodsImpl;
import http.GetGoods;
import http.impl.GetDocumentsImpl;
import http.impl.SendGoodsImpl;
import http.entity.Good;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import service.ShopService;
import utils.CreateFileSellsFromEvotor;
import utils.CreateXlsxFromEvotor;
import validators.FileHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Controller
public class GoodsController {
    private final FileHandler fileHandler;

    final static Logger logger = Logger.getLogger(GoodsController.class);

    private final ShopDaoImpl shopDao;

    private final CreateXlsxFromEvotor createXlsxFromEvotor;

    private final CreateFileSellsFromEvotor createFileSellsFromEvotor;

    @Autowired
    public GoodsController(FileHandler fileHandler, CreateFileSellsFromEvotor createFileSellsFromEvotor, CreateXlsxFromEvotor createXlsxFromEvotor, ShopDaoImpl shopDao) {
        this.fileHandler = fileHandler;
        this.createFileSellsFromEvotor = createFileSellsFromEvotor;
        this.createXlsxFromEvotor = createXlsxFromEvotor;
        this.shopDao = shopDao;
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ModelAndView uploadFile(HttpServletRequest request, @RequestParam("file") MultipartFile file, @RequestParam("shop") String storeUuid) {
        ModelAndView modelAndView = new ModelAndView("index");
        if (file.isEmpty()) {
            return new ModelAndView("index");
        }
        List<String> list;
        try (Workbook workbook = WorkbookFactory.create(convert(file))) {
            Map<String, List> map = fileHandler.getResult(workbook, storeUuid, (String) request.getSession().getAttribute("token"));
            list = map.get("errors");
            int result = 0;
            if (list.isEmpty()) {
                if (!map.get("forDelete").isEmpty()) {
                    DeleteGoodsImpl deleteGoodsImpl = new DeleteGoodsImpl();
                    deleteGoodsImpl.execute(storeUuid, (String) request.getSession().getAttribute("token"), map.get("forDelete"));
                }
                SendGoodsImpl sendGoodsImpl = new SendGoodsImpl();
                result = sendGoodsImpl.send(map.get("goods"), storeUuid, (String) request.getSession().getAttribute("token"));
            }
            modelAndView.addObject("list", !list.isEmpty() ? list : result == 200 ? new ArrayList(Arrays.asList("Все товары загружены")) : new ArrayList(Arrays.asList("Сервер ответил отказом, попробуйте позже")));
        } catch (InvalidFormatException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }


    @GetMapping("/downloadGoods")
    public void downloadFile3(HttpServletResponse response,
                              @RequestParam("store") String storeUuid,
                              HttpServletRequest request) throws Exception {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://umorili.herokuapp.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        String token = (String) request.getSession().getAttribute("token");
        List<Good> goods = retrofit.create(GetGoods.class).getData(storeUuid, token).execute().body();
        Shop shop = shopDao.getShopByUuidStore(storeUuid);
        Workbook workbook = createXlsxFromEvotor.getWorkbook(goods, shop);

        BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/json");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "goods" + ".xlsx");
        workbook.write(outStream);
        outStream.close();
        workbook.close();
    }

    @RequestMapping(value = "/removeAll", method = RequestMethod.POST)
    public ModelAndView removeAll(HttpServletRequest request, @RequestParam("store") String storeUuid) {
        DeleteGoodsImpl deleteGoodsImpl = new DeleteGoodsImpl();
        deleteGoodsImpl.execute(storeUuid, (String) request.getSession().getAttribute("token"), null);
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("list", Collections.singletonList("Все товары удалены"));
        return modelAndView;
    }

    @RequestMapping(value = "/sells", method = RequestMethod.POST)
    public ModelAndView getSells(HttpServletResponse response,
                                 HttpServletRequest request,
                                 @RequestParam("store") String storeUuid,
                                 @RequestParam("dateFrom") String from,
                                 @RequestParam("dateTo") String to) throws IOException {
        ModelAndView modelAndView = new ModelAndView("index");
        if ("".equals(from) || from == null) {
            modelAndView.addObject("list", Collections.singletonList("Неверный формат даты"));
            return modelAndView;
        }
        LocalDateTime localDateTimeFrom;
        LocalDateTime localDateTimeTo;

        try {
            localDateTimeFrom = LocalDateTime.of(LocalDate.parse(from, DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalTime.of(0, 0, 0, 0));
            if ("".equals(to) || to == null) {
                localDateTimeTo = LocalDateTime.now();
            } else {
                localDateTimeTo = LocalDateTime.of(LocalDate.parse(to, DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalTime.now());
            }
        } catch (DateTimeParseException e) {
            modelAndView.addObject("list", Collections.singletonList("Неверный формат даты"));
            return modelAndView;
        }
        if (localDateTimeTo.isBefore(localDateTimeFrom)) {
            modelAndView.addObject("list", Collections.singletonList("Дата от не может быть позже сегодняшней даты и даты до не может быть раньше сегодняшней даты"));
            return modelAndView;
        }

        if (localDateTimeFrom.isBefore(LocalDateTime.now().minusDays(30))) {
            modelAndView.addObject("list", Collections.singletonList("Доступная история продаж - 30 дней"));
            return modelAndView;
        }


        GetDocumentsImpl getDocuments = new GetDocumentsImpl();
        System.out.println(localDateTimeTo.toString());
        System.out.println(localDateTimeFrom.toString());

        List<Document> documents = getDocuments.get(storeUuid, (String) request.getSession().getAttribute("token"), localDateTimeTo.toString() + "+0000", localDateTimeFrom.toString() + ":00.000+0000 ");
        if (documents == null) {
            modelAndView.addObject("list", Collections.singletonList("Ошибка на сервере, пожалуйста попробуйте позже"));
            return modelAndView;
        }

        Shop shop = shopDao.getShopByUuidStore(storeUuid);
        Workbook workbook = createFileSellsFromEvotor.convertFromDocToGood(documents, shop.getName());

        BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/json");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + LocalDate.of(localDateTimeFrom.getYear(), localDateTimeFrom.getMonth(), localDateTimeFrom.getDayOfMonth()) + "_" + LocalDate.of(localDateTimeTo.getYear(), localDateTimeTo.getMonth(), localDateTimeTo.getDayOfMonth()) + ".xlsx");
        workbook.write(outStream);
        outStream.close();
        workbook.close();
        return null;
    }

    private File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

}
