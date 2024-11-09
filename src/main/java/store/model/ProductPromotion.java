package store.model;

import camp.nextstep.edu.missionutils.DateTimes;
import store.domain.Product;
import store.domain.Promotion;

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

    public String getPromotionName() {
        return product.getPromotionName();
    }

    public String getProductName() {
        return product.getName();
    }

    public Long getProductPrice() {
        return (long)product.getPrice() * product.getQuantity();
    }

    public Integer getProductQuantity() {
        return product.getQuantity();
    }

    public Long getAppliedPromotionPrice() {
        if(promotion == null) return 0L;
        return getAppliedPromotionQuantity() * (long)product.getPrice();
    }

    public Integer getAppliedPromotionQuantity() {
        if(promotion == null) return 0;
        return product.getQuantity() / (promotion.getType().getGet() + promotion.getType().getBuy());
    }

}
