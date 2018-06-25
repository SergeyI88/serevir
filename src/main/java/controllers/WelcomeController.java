package controllers;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;


@Controller
public class WelcomeController {

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
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        List<Good> list = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(convert(file))) {
            Sheet sheet = workbook.getSheetAt(0);
            sheet.forEach(row -> {
                row.forEach(cell -> {
                    System.out.print(cell.toString() +"\t");
                });
                System.out.println();
            });
//            SendGoods sendGoods = new SendGoods();
//            sendGoods.send(list);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
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
