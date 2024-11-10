package store.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO
//  - [x] 유효성 체크
//      - 개수
//  - [x] 주문
//      - 주문된 Product를 던진다.
//  - [x] 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 필요한 수량을 추가로 가져오면 혜택을 받을 수 있음을 안내한다.
//  - [x] 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제하게 됨을 안내한다.

public class Inventory {
    private final List<Item> items;

    public Inventory(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public OrderedInventory order(String name, Integer quantity) {
        validate(name, quantity);
        List<Item> targetItems = findByName(name);

        List<OrderedItem> orderedItems = new ArrayList<OrderedItem>();

        for (Item targetItem : targetItems) {
            OrderedItem orderedItem = targetItem.order(quantity);
            quantity -= orderedItem.getProductQuantity();
            orderedItems.add(orderedItem);
            if (quantity == 0) {
                break;
            }

        }
        return new OrderedInventory(orderedItems);
    }

    //ordered method
    public boolean checkPromotionApplicableQuantity(String name, Integer quantity) {
        Item promotionItem = findPromotionItemByName(name);
        if (promotionItem == null) {
            return false;
        }
        return promotionItem.checkPromotionApplicableQuantity(quantity);
    }

    public Integer getPromotionOutOfStock(String name, Integer quantity) {
        Item promotionItem = findPromotionItemByName(name);
        if (promotionItem == null) {
            return 0;
        }
        return promotionItem.getPromotionOutOfStock(quantity);
    }

    public void validate(String name, Integer quantity) {
        List<Item> targetItems = findByName(name);
        validateName(targetItems);
        validateQuantity(targetItems, quantity);
    }

    private void validateName(List<Item> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }
    }

    private void validateQuantity(List<Item> items, Integer quantity) {
        items.stream()
                .map(Item::getProductQuantity)
                .reduce(Integer::sum)
                .filter(sum -> sum >= quantity)
                .orElseThrow(() -> new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."));
    }

    private List<Item> findByName(String name) {
        return items.stream()
                .filter(item -> item.getProductName().equals(name))
                .toList();
    }

    private Item findPromotionItemByName(String name) {
        return findByName(name).stream()
                .filter(item -> item.getPromotionName() != null)
                .findAny()
                .orElse(null);
    }

    private Item findNonePromotionItemByName(String name) {
        return findByName(name).stream()
                .filter(item -> item.getPromotionName() == null)
                .findAny()
                .orElse(null);
    }

}
