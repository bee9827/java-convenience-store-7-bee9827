package store;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {
    public static Product product = new Product("콜라",1000,10, null);

    public
    @Test
    void order() {
        Product ordered = product.order(10);
        assertThat(product.getQuantity()).isEqualTo(0);
        assertThat(ordered.getQuantity()).isEqualTo(10);
    }

}