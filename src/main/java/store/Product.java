package store;

import camp.nextstep.edu.missionutils.DateTimes;

public class Product {
    private final String name;
    private final Integer price;
    private Integer quantity;
    private final String promotionName;


    public Product(String name, Integer price, Integer quantity, String promotionName) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotionName = promotionName;
    }

    public Product order(Integer orderQuantity) {
        if (orderQuantity > quantity) {
            orderQuantity = quantity;
        }

        quantity -= orderQuantity;
        return new Product(name, price, orderQuantity, promotionName);
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getPromotionName() {
        return promotionName;
    }

}
