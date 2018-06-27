package controllers;

import consts.Const;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import validators.FileHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;


@Controller
public class WelcomeController {

    @Autowired
    FileHandler fileHandler;

    final static Logger logger = Logger.getLogger(WelcomeController.class);

    @RequestMapping(value = "/")
    public String reg(HttpServletRequest request) {
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String token(HttpServletRequest request) {
        Enumeration<String> enumerPar = request.getParameterNames();
        while (enumerPar.hasMoreElements()) {
            String param = enumerPar.nextElement();
            logger.info(param);
            logger.info(request.getParameter(param));
        }
        Enumeration<String> enumer = request.getHeaderNames();
        while (enumer.hasMoreElements()) {
            String header = enumer.nextElement();
            logger.info(header);
            logger.info(request.getHeader(header));
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            while (reader.readLine() != null) {
                logger.info(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "{\"userId\":\"01-000000000973924\",\"token\":\"93e44dfa-26c7-441a-8e41-3b433228f96e01\"}";
    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/user/token", method = RequestMethod.POST)
    public String instal(HttpServletRequest request) {
        Enumeration<String> enumerPar = request.getParameterNames();
        while (enumerPar.hasMoreElements()) {
            String param = enumerPar.nextElement();
            logger.info(param);
            logger.info(request.getParameter(param));
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            String string = reader.readLine();
            while (string != null) {
                logger.info(string + " зашли");
                string = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Enumeration<String> enumer = request.getHeaderNames();
        while (enumer.hasMoreElements()) {
            String header = enumer.nextElement();
            logger.info(header);
            logger.info(request.getHeader(header));
        }
        return "{succes: true}";
    }


    private static Connection getConnection() throws URISyntaxException, SQLException {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        return DriverManager.getConnection(dbUrl);
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)

    public ModelAndView uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("a") String shop) {
        ModelAndView modelAndView = new ModelAndView("/index");
        Const con = shop.equals("1") ? Const.STORE_R : Const.STORE_P;
        List<String> list = null;



        try (Workbook workbook = WorkbookFactory.create(convert(file))) {
             list = fileHandler.getResult(workbook);
             modelAndView.addObject("list", list);

            Sheet sheet = workbook.getSheetAt(0);
//            SendGoods sendGoods = new SendGoods();
//            sendGoods.send(list, con);
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
