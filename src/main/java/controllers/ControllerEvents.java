package controllers;

import com.google.gson.Gson;
import controllers.json.EventInstallOrUninstall;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.ClientService;

@Controller
public class ControllerEvents {
    final static Logger logger = Logger.getLogger(ControllerEvents.class);

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = {"application/json"})
    public String installedOrDeleted(@RequestBody String body) {
        Gson gson = new Gson();
        EventInstallOrUninstall event = gson.fromJson(body, EventInstallOrUninstall.class);
        logger.info(event);

//        Enumeration<String> enumerPar = request.getParameterNames();
//        while (enumerPar.hasMoreElements()) {
//            String param = enumerPar.nextElement();
//            logger.info(param);
//            logger.info(request.getParameter(param));
//        }
//
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
//            String string = reader.readLine();
//            while (string != null) {
//                logger.info(string + " зашли");
//                string = reader.readLine();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Enumeration<String> enumer = request.getHeaderNames();
//        while (enumer.hasMoreElements()) {
//            String header = enumer.nextElement();
//            logger.info(header);
//            logger.info(request.getHeader(header));
//        }
        return "index";
    }
}
