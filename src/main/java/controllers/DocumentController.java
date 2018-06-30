package controllers;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import controllers.json.Document;
import http.SendGoods;
import http.entity.Good;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.Header;
import service.ShopService;
import validators.TransactionHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class DocumentController {
    private static Logger logger = Logger.getLogger(DocumentController.class);

    @Autowired
    TransactionHandler transactionHandler;
    @Autowired
    ShopService shopService;

    @RequestMapping(value = "/api/v1/inventories/stores/{storeUuid}/documents", method = RequestMethod.PUT)
    public String getDocuments(@PathVariable("storeUuid") String shop, @Header("Authorization") String a, @RequestBody String body) {
        logger.info(a);
        String authorization = shopService.getTokenByStoreUuid(shop);
        Gson gson = new Gson();
        List<LinkedTreeMap> list = gson.fromJson(body, List.class);
        logger.info(list);
//        list = list.stream()
//                .filter(d -> {
//                    LocalDate lcd = LocalDate.parse(d.getOpenDate().substring(0, 10));
//                    return lcd.equals(LocalDate.now());
//                });
//        Document document = list.get(list.size() - 1);
//        transactionHandler.getGoods(document.getType(), document.getTransactions(), shop, authorization);
        return "index";
    }
}
