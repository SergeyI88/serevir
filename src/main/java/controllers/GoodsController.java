package controllers;

import db.DAO.impl.ShopDaoImpl;
import db.entity.Shop;
import http.GetGoods;
import http.SendGoods;
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
import utils.CreateXlsxFromEvotor;
import validators.FileHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Controller
public class GoodsController {
    private final FileHandler fileHandler;

    final static Logger logger = Logger.getLogger(GoodsController.class);
    @Autowired
    ShopService shopService;

    @Autowired
    ShopDaoImpl shopDao;

    @Autowired
    CreateXlsxFromEvotor createXlsxFromEvotor;

    @Autowired
    public GoodsController(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ModelAndView uploadFile(HttpServletRequest request, @RequestParam("file") MultipartFile file, @RequestParam("shop") String storeUuid) {
        ModelAndView modelAndView = new ModelAndView("index");
        if (file.isEmpty()) {
            return new ModelAndView("index");
        }
        List<String> list = null;
        try (Workbook workbook = WorkbookFactory.create(convert(file))) {
            Map<String, List> map = fileHandler.getResult(workbook);
            list = map.get("errors");
            SendGoods sendGoods = new SendGoods();
            System.out.println();
            System.out.println(storeUuid);
            System.out.println();
            int result = sendGoods.send(map.get("goods"), storeUuid, (String) request.getSession().getAttribute("token"));
            modelAndView.addObject("list", !list.isEmpty() ? list : result == 200 ? new ArrayList(Arrays.asList("Все товары загружены")) : new ArrayList(Arrays.asList("Сервер ответил отказом, попробуйте позже")));
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
                              HttpServletRequest request) throws Exception {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://umorili.herokuapp.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        String token = (String) request.getSession().getAttribute("token");
        List<Good> goods = retrofit.create(GetGoods.class).getData(storeUuid, token).execute().body();
        Shop shop = shopDao.getShopByUuidStore(storeUuid);

        Workbook workbook = createXlsxFromEvotor.getWorkbook(goods, shop.getName());

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
