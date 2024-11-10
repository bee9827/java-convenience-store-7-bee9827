package store;

import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.PromotionType;
import store.model.Inventory;
import store.model.Item;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InventoryTest {
    @Test
    void order() {
        Inventory stocks = new Inventory(List.of(
                new Item(
                        new Product("콜라", 1000, 10, "2+1"),
                        new Promotion("2+1", PromotionType.TWO_PLUS_ONE, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"))
                )
        ));


        assertThat(stocks.order("콜라", 10).getQuantity()).isEqualTo(10);
        assertThatThrownBy(() -> stocks.order("콜라", 16))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
    }

}