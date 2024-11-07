package store;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    @DisplayName("재고 수량을 초과하여 구매하면 실패한다.")
    @Test
    void order() {
        Promotion promotion = new Promotion(
                "탄산2+1",
                PromotionType.TWO_PLUS_ONE,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 12, 31)
        );
        Product product = new Product("콜라",1000,10, promotion);

        assertThatThrownBy(()-> product.order(11))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
    }

}