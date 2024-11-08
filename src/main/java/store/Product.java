package store;

import camp.nextstep.edu.missionutils.DateTimes;

public class Product {
    private final String name;
    private final Integer price;
    private Integer quantity;
    private final Promotion promotion;
    private final ProductStatus productStatus;


    public Product(String name, Integer price, Integer quantity, Promotion promotion, ProductStatus productStatus) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
        this.productStatus = productStatus;
    }

    public Product order(Integer orderQuantity) {
        if (orderQuantity > quantity) {
            orderQuantity = quantity;
        }

        quantity -= orderQuantity;
        return new Product(name, price, orderQuantity, promotion, ProductStatus.ORDERED);
    }

    public boolean checkPromotionApplicable(int quantity) {
        if (checkExpirationDate()) {
            return quantity % (promotion.getType().getBuy() + promotion.getType().getGet()) == promotion.getType().getBuy();
        }
        return false;
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

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    private boolean checkExpirationDate() {
        if (promotion == null) {
            return false;
        }
        return promotion.validateDateTime(DateTimes.now());
    }

}
