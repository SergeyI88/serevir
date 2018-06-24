package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.http.HttpServletRequest;

@Controller
public class WelcomeController {


    @RequestMapping("/")
    public String send() {
        return "index";
    }

    @RequestMapping(value = "/api/v1/user/create", method = RequestMethod.POST)
    public String reg(HttpServletRequest request) {
        System.out.println(request);
        return "{\n" +
                "\"userId\": \"01-000000000000001\",\n" +
                "\"token\": \"toaWaep4chou7ahkoogiu9Iusaht9ima\"\n" +
                "}";
    }
}
