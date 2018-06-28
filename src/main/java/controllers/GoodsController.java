package controllers;

import consts.Const;
import http.SendGoods;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import service.ShopService;
import validators.FileHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class GoodsController {
    private final FileHandler fileHandler;

    final static Logger logger = Logger.getLogger(GoodsController.class);
    @Autowired
    ShopService shopService;

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



    public File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
