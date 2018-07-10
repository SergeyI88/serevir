package controllers;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import controllers.json.Document;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.Header;
import service.ShopService;
import validators.TransactionHandler;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DocumentController {
    private static Logger logger = Logger.getLogger(DocumentController.class);

    @Autowired
    TransactionHandler transactionHandler;
    @Autowired
    ShopService shopService;

    @RequestMapping(value = "/api/v1/inventories/stores/{storeUuid}/documents", method = RequestMethod.PUT)
    @ResponseBody
    public String getDocuments(@PathVariable("storeUuid") String shop, @Header("Authorization") String a, @RequestBody String body) {
        String authorization = shopService.getTokenByStoreUuid(shop);
        Gson gson = new Gson();
        List<LinkedTreeMap> list = gson.fromJson(body, List.class);
        List<Document> documents = new ArrayList<>();

        for (LinkedTreeMap map : list) {
            Document document = new Document();
            document.setType((String) map.get("type"));
            document.setTransactions(new ArrayList<>());
            for(LinkedTreeMap t: (ArrayList<LinkedTreeMap>) map.get("transactions")) {
                Document.Transaction transaction =  document.new Transaction();
                transaction.setCommodityUuid((String) t.get("commodityUuid"));
                transaction.setQuantity((Double) t.get("quantity"));
                transaction.setType((String) t.get("type"));
                document.getTransactions().add(transaction);
            }
            documents.add(document);

        }
        Document document = documents.get(documents.size() - 1);
        transactionHandler.getGoods(document.getType(), document.getTransactions(), shop, authorization);
        return "{success:true}";
    }
}
