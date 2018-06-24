package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class WelcomeController {


    @RequestMapping("/")
    public String send() {
        return "index";
    }

    @RequestMapping("/api/v1/user/create")
    public String reg(HttpServletRequest request) {
        System.out.println(request);
        return "index";
    }
}
