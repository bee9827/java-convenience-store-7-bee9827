package store;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {
    public static Product productTPO = new Product("콜라",1000,10, "2+1");
    public static Product productOPO = new Product("감자칩",1500,5, "1+1");
    public static Product product = new Product("콜라",1000,10, null);

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