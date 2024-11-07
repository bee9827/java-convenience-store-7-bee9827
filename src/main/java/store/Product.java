package store;

import camp.nextstep.edu.missionutils.DateTimes;

import java.time.LocalDateTime;

public class Product {
    private final String name;
    private final Integer price;
    private Integer quantity;
    private final Promotion promotion;


    public Product(String name, Integer price, Integer quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
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

    public Promotion getPromotion() {
        return promotion;
    }

    public Product order(Integer orderQuantity) {
        validateQuantity(orderQuantity);
        this.quantity -= orderQuantity;

        return new Product(name, price, orderQuantity, promotion);
    }

    private void validateQuantity(Integer quantity) {
        if (quantity > this.quantity) {
            throw new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }

    private boolean checkExpirationDate() {
        if (promotion == null) {
            return false;
        }
        return promotion.validateDateTime(DateTimes.now());
    }
}
