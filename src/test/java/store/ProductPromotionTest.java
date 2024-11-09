package store;

import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.PromotionType;
import store.model.ProductPromotion;

import java.text.DecimalFormat;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ProductPromotionTest {


    @Test
    void getAppliedPromotionQuantity() {
        ProductPromotion productPromotion = new ProductPromotion(
                new Product("콜라",1000,10,"2+1"),
                new Promotion("2+1", PromotionType.TWO_PLUS_ONE, LocalDate.parse("2024-01-01"),LocalDate.parse("2024-12-31"))
        );
        assertThat(productPromotion.order(7).getAppliedPromotionQuantity()).isEqualTo(2);
        assertThat(productPromotion.order(3).getAppliedPromotionQuantity()).isEqualTo(1);
        //재고 소진

        assertThat(productPromotion.order(10).getAppliedPromotionQuantity()).isEqualTo(0);
    }

    @Test
    void getAppliedPromotionPrice() {
        ProductPromotion productPromotion = new ProductPromotion(
                new Product("콜라",1000,10,"2+1"),
                new Promotion("2+1", PromotionType.TWO_PLUS_ONE, LocalDate.parse("2024-01-01"),LocalDate.parse("2024-12-31"))
        );
        assertThat(productPromotion.order(7).getAppliedPromotionPrice()).isEqualTo(2000);
        assertThat(productPromotion.order(3).getAppliedPromotionPrice()).isEqualTo(1000);
        //재고 소진
        assertThat(productPromotion.order(10).getAppliedPromotionPrice()).isEqualTo(0);
    }
}