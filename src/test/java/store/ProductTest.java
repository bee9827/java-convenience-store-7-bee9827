package store;

import org.junit.jupiter.api.Test;
import store.domain.Product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    public
    @Test
    void order() {
        Product product = new Product("콜라",1000,10, "탄산 2+1");
        Product ordered = product.order(10);
        assertThat(product.getQuantity()).isEqualTo(0);
        assertThat(ordered.getQuantity()).isEqualTo(10);
    }

}