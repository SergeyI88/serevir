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

    @RequestMapping(value = "/events/subscription", method = RequestMethod.POST)
    public String installedOrDeleted(@RequestBody String body) {
        Gson gson = new Gson();
        EventInstallOrUninstall event = gson.fromJson(body, EventInstallOrUninstall.class);
        if(event.getType().equals("ApplicationUninstalled")) {
            clientService.removeClient(event.getData().getUserId());
        }
        return "index";
    }
}
