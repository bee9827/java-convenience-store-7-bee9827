package store;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductPromotionTest {


    @Test
    void getAppliedPromotionPrice() {
        ProductPromotion productPromotion = new ProductPromotion(ProductTest.product, PromotionTest.promotionTPO);
        assertThat(productPromotion.order(7).getAppliedPromotionQuantity()).isEqualTo(2);
        assertThat(productPromotion.order(3).getAppliedPromotionQuantity()).isEqualTo(1);
        //재고 소진

        assertThat(productPromotion.order(10).getAppliedPromotionQuantity()).isEqualTo(0);
    }

    @Test
    void getAppliedPromotionQuantity() {
        ProductPromotion productPromotion = new ProductPromotion(ProductTest.product, PromotionTest.promotionTPO);
        assertThat(productPromotion.order(7).getAppliedPromotionPrice()).isEqualTo(2 * productPromotion.getProductPrice());
        assertThat(productPromotion.order(3).getAppliedPromotionPrice()).isEqualTo(1 * productPromotion.getProductPrice());
        //재고 소진
        assertThat(productPromotion.order(10).getAppliedPromotionPrice()).isEqualTo(0 * productPromotion.getProductPrice());
    }
}