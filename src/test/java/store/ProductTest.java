package store;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.Product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    @DisplayName("초과해서 구매해도 최대 개수만큼만 주문한다.")
    @ParameterizedTest
    @CsvSource(value = {"1,9","5,5","10,0","20,0","100,0"})
    void order(int orderQuantity, int stockQuantity) {
        Product product = new Product("콜라",1000,10, "탄산 2+1");
        product.order(orderQuantity);
        assertThat(product.getQuantity()).isEqualTo(stockQuantity);
    }

}