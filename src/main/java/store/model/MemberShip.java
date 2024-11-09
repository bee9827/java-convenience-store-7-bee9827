package store.model;

import java.math.BigDecimal;

public class MemberShip {
    public static BigDecimal DISCOUNT = new BigDecimal("0.3");
    private boolean isActive;
    private Long price;

    public MemberShip(boolean isActive, Long price) {
        this.isActive = isActive;
        this.price = price;
    }

    public Long getAfterDiscountPrice() {
        return price - getDiscountPrice();
    }

    public Long getDiscountPrice() {
        if(!isActive) { return 0L;}
        long discountPrice = new BigDecimal(price).multiply(DISCOUNT).longValue();
        if (discountPrice > 8000) discountPrice = 8000;
        return discountPrice;
    }

    //멤버십 회원은 프로모션 미적용 금액의 30%를 할인받는다.
    //프로모션 적용 후 남은 금액에 대해 멤버십 할인을 적용한다.
    //멤버십 할인의 최대 한도는 8,000원이다.
}
