package validators;

import http.GetGoodsImpl;
import http.SendGoods;
import http.entity.Good;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import controllers.json.Document.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionHandler {
    Logger logger = Logger.getLogger(TransactionHandler.class);

    public void getGoods(String type, List<Transaction> transactions, String storeUuid, String authorization) {
        GetGoodsImpl getGoods = new GetGoodsImpl();
        List<Good> fromEvotor = null;
        try {
            fromEvotor = getGoods.get(storeUuid, authorization);
            logger.info("get goods from the evotor");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (type.equals("INVENTORY")) {
            executeInventory(transactions, storeUuid, authorization, fromEvotor);
            return;
        }
        List<Good> listFromTerminal = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getType().equals("REGISTER_POSITION")) {
                Good good = new Good();
                good.setQuantity(t.getQuantity());
                good.setUuid(t.getCommodityUuid());
                listFromTerminal.add(good);
            }
        }

        logger.info("get goods from the terminal");
        execute(listFromTerminal, fromEvotor, storeUuid, authorization, type);

    }

    private void execute(List<Good> listFromTerminal, List<Good> fromEvotor, String storeUuid, String authorization, String type) {
        if (type.equals("SELL") || type.equals("WRITE_OFF") || type.equals("RETURN")) {
            decrement(listFromTerminal, fromEvotor, storeUuid, authorization);
        } else if (type.equals("ACCEPT") || type.equals("PAYBACK")) {
            increment(listFromTerminal, fromEvotor, storeUuid, authorization);
        }
    }

    private void increment(List<Good> listFromTerminal, List<Good> fromEvotor, String storeUuid, String authorization) {
        for (Good withFields : fromEvotor) {
            for (Good without : listFromTerminal) {
                if (withFields.getUuid().equals(without.getUuid())) {
                    withFields.setQuantity(withFields.getQuantity() + without.getQuantity());
                }
            }
        }
        logger.info("got union list in increment");
        SendGoods sendGoods = new SendGoods();
        try {
            int code = sendGoods.send(fromEvotor, storeUuid, authorization);
            logger.info(code + " sent in evotor after increment");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void decrement(List<Good> listFromTerminal, List<Good> fromEvotor, String storeUuid, String authorization) {
        for (Good withFields : fromEvotor) {
            for (Good without : listFromTerminal) {
                if (withFields.getUuid().equals(without.getUuid())) {
                    withFields.setQuantity(withFields.getQuantity() - without.getQuantity());
                }
            }
        }
        logger.info("got union list in decrement");
        SendGoods sendGoods = new SendGoods();
        try {
            int code = sendGoods.send(fromEvotor, storeUuid, authorization);
            logger.info(code + " sent in evotor after decrement");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void executeInventory(List<Transaction> list, String shop, String authorization, List<Good> fromEvotor) {
        List<Good> listFromTerminal = list.stream()
                .filter(t -> t.getType().equals("INVENTORY"))
                .map(t -> {
                    Good good = new Good();
                    good.setQuantity(t.getQuantity());
                    good.setUuid(t.getCommodityUuid());
                    return good;
                }).collect(Collectors.toList());
        logger.info("method executeInventory got list from terminal");
        for (Good withFields : fromEvotor) {
            for (Good without : listFromTerminal) {
                if (withFields.getUuid().equals(without.getUuid())) {
                    withFields.setQuantity(without.getQuantity());
                }
            }
        }
        logger.info("got union list");
        SendGoods sendGoods = new SendGoods();
        try {
            int code = sendGoods.send(fromEvotor, shop, authorization);
            logger.info(code + " sent in evotor after inventory");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
