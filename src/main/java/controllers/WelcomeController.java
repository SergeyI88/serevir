package controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;

@Controller
public class WelcomeController {

    final static Logger logger = Logger.getLogger(WelcomeController.class);


//    @RequestMapping("/")
//    public String send() {
//        return "index";
//    }

    @ResponseBody
    @RequestMapping(value = "/api/v1/user/create", method = RequestMethod.POST)
    public String reg(HttpServletRequest request) {
        return "{\n" +
                "userId\": \"01-000000000000001\",\n" +
                "\"token\": \"toaWaep4chou7ahkoogiu9Iusaht9ima\"\n" +
                "}";
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String token(HttpServletRequest request, @RequestBody String body) {
        logger.info(body);
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

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            while (reader.readLine() != null) {
                logger.info(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "{\"userId\":\"01-000000000973924\",\"token\":\"93e44dfa-26c7-441a-8e41-3b433228f96e\"}";
    }

    @ResponseBody
    @RequestMapping(value = "/api/v2/installation/event", method = RequestMethod.POST)
    public String instal(HttpServletRequest request) {
        return "{succes: true}";
    }
}
