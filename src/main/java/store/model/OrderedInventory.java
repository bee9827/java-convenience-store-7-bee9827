package store.model;

import java.util.List;

public class OrderedInventory {
    private final List<OrderedItem> orderedItems;

    public OrderedInventory(List<OrderedItem> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public String getName(){
        return orderedItems.getFirst().getProductName();
    }

    public Integer getQuantity() {
        return orderedItems.stream()
                .map(OrderedItem::getProductQuantity)
                .reduce(Integer::sum)
                .get();
    }

    public Long getPrice() {
        return orderedItems.stream()
                .map(OrderedItem::getProductPrice)
                .reduce(Long::sum)
                .get();
    }

    public Integer getDiscountQuantity() {
        return orderedItems.stream()
                .map(OrderedItem::getAppliedPromotionQuantity)
                .reduce(Integer::sum)
                .get();
    }

    public Long getDiscountPrice() {
        return orderedItems.stream()
                .map(OrderedItem::getAppliedPromotionPrice)
                .reduce(Long::sum)
                .get();
    }
}
