package store;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.PromotionType;
import store.model.Inventory;
import store.model.Item;
import store.model.OrderedInventory;
import store.model.OrderedItem;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InventoryTest {
    @DisplayName("프로모션 물품이 먼저 주문된다.")
    @Test
    void order() {
        Inventory inventory = getInventory();
        OrderedInventory orderedInventory = inventory.order("콜라",10);
        assertThat(orderedInventory.getQuantity()).isEqualTo(10);
        assertThat(orderedInventory.getDiscountQuantity()).isEqualTo(3);
    }

    @DisplayName("프로모션 적용받지 못하는 개수를 전달한다.")
    @ParameterizedTest
    @CsvSource(value = {"10,0", "11,2", "15,6", "20,11"})
    void order_stocks_not_exist(int quantity, int nonDiscountQuantity) {
        Inventory inventory = getInventory();
        assertThat(inventory.getPromotionOutOfStock("콜라",quantity)).isEqualTo(nonDiscountQuantity);
    }

    @DisplayName("[예외]: 재고수량 초과 구매 ")
    @ParameterizedTest()
    @ValueSource(ints={21,22,23})
    void validate_order_size(int quantity){
        assertThatThrownBy(() -> getInventory().order("콜라", quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
    }

    @DisplayName("[예외]: 없는 물품 구매")
    @Test
    void validate_order_not_exist(){
        Inventory inventory = getInventory();
        assertThatThrownBy(()->inventory.order("사이다",21))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 상품입니다. 다시 입력해 주세요.");
    }


    private Inventory getInventory() {
        Inventory stocks = new Inventory(List.of(
                new Item(
                        new Product("콜라", 1000, 10, "null"),
                        null
                ),
                new Item(
                        new Product("콜라", 1000, 10, "2+1"),
                        new Promotion("2+1", PromotionType.TWO_PLUS_ONE, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-31"))
                )

        ));
        return stocks;
    }
}