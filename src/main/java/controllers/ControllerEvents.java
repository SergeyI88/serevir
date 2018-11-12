package controllers;

import com.google.gson.Gson;
import controllers.json.EventInstallOrUninstall;
import controllers.json.EventSubscription;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.ClientService;

@Controller
public class ControllerEvents {
    final static Logger logger = Logger.getLogger(ControllerEvents.class);

    private final ClientService clientService;

    @Autowired
    public ControllerEvents(ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping(value = "/events/install", method = RequestMethod.POST)
    @ResponseBody
    public String installedOrDeleted(@RequestBody String body) {
        Gson gson = new Gson();
        EventInstallOrUninstall event = gson.fromJson(body, EventInstallOrUninstall.class);
        if(event.getType().equals("ApplicationUninstalled")) {
            clientService.removeClient(event.getData().getUserId());
        }
        return "{success:true}";
    }

    @RequestMapping(value = "/events/subscription", method = RequestMethod.POST)
    @ResponseBody
    public String subscription(@RequestBody String body) {
        Gson gson = new Gson();
        EventSubscription event = gson.fromJson(body, EventSubscription.class);
        if(event.getType().equals("SubscriptionCreated")
                || event.getType().equals("SubscriptionActivated")
                || event.getType().equals("SubscriptionRenewed")) {
            clientService.setSubscription(event.getUserId(), true);
        } else if (event.getType().equals("SubscriptionTerminated ")) {
            clientService.setSubscription(event.getUserId(), false);
        }
        return "{success:true}";
    }
}
