package store.model;

import camp.nextstep.edu.missionutils.DateTimes;
import store.domain.Product;
import store.domain.Promotion;

public class Item {
    private final Product product;
    private final Promotion promotion;

    public Item(Product product, Promotion promotion) {
        this.product = product;
        this.promotion = promotion;
    }

    public OrderedItem order(Integer quantity) {
        Product ordered = product.order(quantity);
        return new OrderedItem(ordered, promotion);
    }

    //최대개수이상 이면서 나누어떨어지지 않으면 재고부족.
    public Integer getPromotionOutOfStock(Integer quantity) {
        if(!checkValidPromotion()) return 0;
        if (quantity > product.getQuantity()) {
            return (quantity - product.getQuantity()) + product.getQuantity() % (promotion.getType().getBuy() + 1);
        }
        if (quantity == product.getQuantity()) {
            return product.getQuantity() % (promotion.getType().getGet() + 1);
        }
        return 0;
    }

    //나누어 떨어지는수 ex) 2+1에서 3개 시키면 true else false;
    public boolean checkPromotionApplicableQuantity(Integer quantity) {
        return checkValidPromotion() && promotion.checkQuantity(quantity);
    }

    public String getPromotionName() {
        return product.getPromotionName();
    }

    public String getProductName() {
        return product.getName();
    }

    public Integer getProductQuantity() {
        return product.getQuantity();
    }

    public Long getProductPrice() {
        return (long) product.getPrice();
    }

    private boolean checkValidPromotion() {
        if (promotion == null) return false;
        return promotion.checkDateTime(DateTimes.now());
    }
}
