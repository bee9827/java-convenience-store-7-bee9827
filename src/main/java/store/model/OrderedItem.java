package store.model;

import camp.nextstep.edu.missionutils.DateTimes;
import store.domain.Product;
import store.domain.Promotion;

public class OrderedItem {
    private final Product product;
    private final Promotion promotion;

    public OrderedItem(Product product, Promotion promotion) {
        this.product = product;
        this.promotion = promotion;
    }

    public String getProductName() {
        return product.getName();
    }

    public Long getProductPrice() {
        return (long) product.getPrice() * product.getQuantity();
    }

    public Integer getProductQuantity() {
        return product.getQuantity();
    }

    public Long getAppliedPromotionPrice() {
        if (!checkValidPromotion()) return 0L;
        return getAppliedPromotionQuantity() * (long) product.getPrice();
    }

    public Integer getAppliedPromotionQuantity() {
        if (!checkValidPromotion()) return 0;
        return product.getQuantity() / (promotion.getType().getGet() + promotion.getType().getBuy());
    }

    private boolean checkValidPromotion() {
        if (promotion == null) return false;
        return promotion.checkDateTime(DateTimes.now());
    }
}
