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
    // TODO
    //  - [x] 유효성 체크
    //  - 개수
    //  - [x] 주문
    //  - 주문된 Product를 던진다.
    //  - [x] 각 멤버의 게터함수
    //  - [x] 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 필요한 수량을 추가로 가져오면 혜택을 받을 수 있음을 안내한다.
    //  - [ ] 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제하게 됨을 안내한다.

    public Product order(Integer orderQuantity) {
        validateQuantity(orderQuantity);
        this.quantity -= orderQuantity;

        return new Product(name, price, orderQuantity, promotion, ProductStatus.ORDERED);
    }

    public boolean checkPromotionApplicable(int quantity) {
        validateQuantity(quantity);
        validatePromotion();
        return quantity % (promotion.getType().getBuy() + promotion.getType().getGet()) == promotion.getType().getBuy();
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

    private void validatePromotion() {
        if(promotion == null) throw new IllegalStateException("잘못된 입력입니다. 다시 입력해 주세요.");
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
