package controllers;

import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.generic.NEW;
import controllers.json.NewClient;
import db.entity.Shop;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
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
        List<Shop> list = shopService.getShops(uid);
        System.out.println(list);
        request.getSession().setAttribute("shops", list);
        return new ModelAndView("index");
    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/user/token", method = RequestMethod.POST)
    public String install(@RequestBody String body) {
        Gson gson = new Gson();
        NewClient newClient = gson.fromJson(body, NewClient.class);
        logger.info("new client" + newClient.getUserId());
        clientService.createClient(newClient.getUserId(), newClient.getToken(), "");
        return "{succes: true}";
    }

    @RequestMapping("/error")
    public String error() {
        return "error";
    }
}
