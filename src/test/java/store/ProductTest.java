package store;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {
    public Promotion promotionTPO = new Promotion(
            "탄산2+1",
            PromotionType.TWO_PLUS_ONE,
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 12, 31)
    );
    public Promotion promotionOPO = new Promotion(
            "MD추천상품",
            PromotionType.ONE_PLUS_ONE,
            LocalDate.of(2024,01,01),
            LocalDate.of(2024,12,31)
    );
    Product productTPO = new Product("콜라",1000,10, promotionTPO, ProductStatus.STOCK);
    Product productOPO = new Product("감자칩",1500,5, promotionOPO, ProductStatus.STOCK);

    public
    @DisplayName("재고 수량을 초과하여 구매하면 실패한다.")
    @Test
    void order() {
        assertThatThrownBy(()-> productTPO.order(11))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
    }

    @DisplayName("프로모션 적용이 가능한 상품 수량인지 체크")
    @Test
    void checkPromotionApplicable() {
        assertThat(productTPO.checkPromotionApplicable(2)).isTrue();
        assertThat(productTPO.checkPromotionApplicable(3)).isFalse();
        assertThat(productOPO.checkPromotionApplicable(4)).isFalse();
        assertThat(productTPO.checkPromotionApplicable(5)).isTrue();
    }
}