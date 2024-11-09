package store;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PromotionTest {
    public static Promotion promotionTPO = new Promotion(
            "탄산2+1",
            PromotionType.TWO_PLUS_ONE,
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 12, 31)
    );
    public static Promotion promotionOPO = new Promotion(
            "MD추천상품",
            PromotionType.ONE_PLUS_ONE,
            LocalDate.of(2024,01,01),
            LocalDate.of(2024,12,31)
    );
    public static Promotion expiresDeadline = new Promotion(
            "MD추천상품",
            PromotionType.ONE_PLUS_ONE,
            LocalDate.of(2023,01,01),
            LocalDate.of(2023,12,31)
    );

    @DisplayName("유효기간 체크")
    @Test
    void checkDateTime() {
        assertThat(expiresDeadline.checkDateTime(LocalDateTime.now())).isFalse();
        assertThat(promotionOPO.checkDateTime(LocalDateTime.of(2024,12,31,23,59,59))).isTrue();
    }

    @DisplayName("프로모션 적용가능한 개수 체크")
    @Test
    void checkPromotionApplicable() {
        assertThat(promotionOPO.checkQuantity(1)).isTrue();
        assertThat(promotionOPO.checkQuantity(3)).isTrue();
        assertThat(promotionOPO.checkQuantity(2)).isFalse();
        assertThat(promotionOPO.checkQuantity(4)).isFalse();

        assertThat(promotionTPO.checkQuantity(2)).isTrue();
        assertThat(promotionTPO.checkQuantity(5)).isTrue();
        assertThat(promotionTPO.checkQuantity(4)).isFalse();
    }
}