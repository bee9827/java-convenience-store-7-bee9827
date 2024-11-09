package store;

import camp.nextstep.edu.missionutils.DateTimes;

public class ProductPromotion {
    private final Product product;
    private final Promotion promotion;

    public ProductPromotion(Product product, Promotion promotion) {
        this.product = product;
        this.promotion = promotion;
    }

    public ProductPromotion order(Integer quantity) {
        Product ordered = product.order(quantity);
        return new ProductPromotion(ordered, promotion);
    }

    public boolean checkValidPromotion() {
        if (promotion == null) return false;
        return promotion.checkDateTime(DateTimes.now());
    }

    public boolean checkPromotionOutOfStock(Integer quantity) {
        return product.getQuantity() <= quantity;
    }

    public boolean checkPromotionApplicableQuantity(Integer quantity) {
        return promotion.checkQuantity(quantity);
    }

    public String getProductName() {
        return product.getName();
    }

    public Integer getProductPrice() {
        return product.getPrice() * product.getQuantity();
    }

    public Integer getProductQuantity() {
        return product.getQuantity();
    }

    public Integer getAppliedPromotionPrice() {
        return getAppliedPromotionQuantity() * getProductPrice();
    }

    public Integer getAppliedPromotionQuantity() {
        return getProductQuantity() / (promotion.getType().getGet() + promotion.getType().getBuy());
    }
}
