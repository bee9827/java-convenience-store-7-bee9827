package store;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    public static Inventory stocks = new Inventory(List.of(ProductTest.productTPO,ProductTest.product));

    @Test
    void order() {
        assertThat(stocks.order("콜라",10).getQuantity("콜라")).isEqualTo(10);
        assertThatThrownBy(() -> stocks.order("콜라",16))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
    }
}