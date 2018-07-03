package controllers;

import db.entity.Shop;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.ClientService;
import service.ShopService;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


@Controller
@SessionAttributes(value = {"shops", "token"})
public class MainController {

    final static Logger logger = Logger.getLogger(MainController.class);

    @Autowired
    private ClientService clientService;
    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "/")

    public ModelAndView open(HttpServletRequest request, @RequestParam String uid, @RequestParam String token) {
        request.getSession().setAttribute("token", token);
        List<Shop> list = shopService.getShops(uid, (String) request.getSession().getAttribute("token"));
        System.out.println(list);
        request.getSession().setAttribute("shops", list);
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/user/token", method = RequestMethod.POST)
    public String install(HttpServletRequest request) {
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


    @RequestMapping("/error")
    public String error() {
        return "error";
    }
}
