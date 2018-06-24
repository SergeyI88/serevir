package controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;

@Controller
public class WelcomeController {

    private static Logger logger = Logger.getLogger(WelcomeController.class);


//    @RequestMapping("/")
//    public String send() {
//        return "index";
//    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/user/create", method = RequestMethod.POST)
    public String reg(HttpServletRequest request) {
        System.out.println(request);
        return "{\n" +
                "userId\": \"01-000000000000001\",\n" +
                "\"token\": \"toaWaep4chou7ahkoogiu9Iusaht9ima\"\n" +
                "}";
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String token(HttpServletRequest request) {
        logger.info(request);
        return "{\n" +
                "userId\": \"01-000000000000001\",\n" +
                "\"token\": \"toaWaep4chou7ahkoogiu9Iusaht9ima\"\n" +
                "}";
    }

    @ResponseBody
    @RequestMapping(value = "/api/v2/installation/event", method = RequestMethod.POST)
    public String instal(HttpServletRequest request) {
        return "{succes: true}";
    }
}
