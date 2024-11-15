package store.view;

import store.model.Inventory;
import store.model.Item;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class StockList {
    private final DecimalFormat decimalFormat = new DecimalFormat("###,###");
    private final Inventory inventory;

    public StockList(Inventory inventory) {
        this.inventory = inventory;
    }

    public List<String> getStocks() {
        List<String> stocks = new ArrayList<String>();
        for (Item item : inventory.getItems()) {
            stocks.add(makeInfo(item));
        }
        return stocks;
    }

    private String makeInfo(Item item) {
        return getName(item.getProductName()) + getPrice(item.getProductPrice())
                + getQuantity(item.getProductQuantity()) + getPromotionName(item.getPromotionName());
    }

    private String getName(String name) {
        return name + " ";
    }

    private String getPrice(Long price) {
        return decimalFormat.format(price) + "원 ";
    }

    private String getQuantity(Integer quantity) {
        if (quantity == 0) return "재고 없음 ";
        return decimalFormat.format(quantity) + "개 ";
    }

        private String getPromotionName (String promotionName){
            if (promotionName.equals("null")) return "";
            return promotionName;
        }
    }
