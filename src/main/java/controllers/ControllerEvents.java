package controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.ClientService;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;

@Controller
public class ControllerEvents {
    final static Logger logger = Logger.getLogger(ControllerEvents.class);

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String installedOrDeleted(HttpServletRequest request) {
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
        return "index";
    }
}
