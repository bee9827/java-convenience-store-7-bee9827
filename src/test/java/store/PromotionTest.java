package store;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PromotionTest {

    @Test
    void validateDateTime() {
        Promotion promotion = new Promotion(
                "탄산2+1",
                PromotionType.TWO_PLUS_ONE,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 12, 31)
        );
        assertThat(promotion.validateDateTime(LocalDateTime.of(2024,1,1,0,0,1))).isTrue();
        assertThat(promotion.validateDateTime(LocalDateTime.of(2024,12,31,23,59,59))).isTrue();
    }
}