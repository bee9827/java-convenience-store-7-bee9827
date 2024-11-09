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
//  - [ ] 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제하게 됨을 안내한다.

public class Inventory {
    private final List<ProductPromotion> items;

    public Inventory(List<ProductPromotion> items) {
        this.items = items;
    }

    public List<ProductPromotion> getItems() {
        return Collections.unmodifiableList(items);
    }

    public List<ProductPromotion> order(String name, Integer quantity) {
        List<ProductPromotion> targetItems = findByName(name);
        validate(targetItems, quantity);

        List<ProductPromotion> orderedItems = new ArrayList<ProductPromotion>();

        for (ProductPromotion targetItem : targetItems) {
            orderedItems.add(targetItem.order(quantity));
        }
        return orderedItems;
    }

    public Integer getQuantity(String name) {
        final List<ProductPromotion> products = findByName(name);
        validateName(products);

        return products.stream()
                .map(ProductPromotion::getProductQuantity)
                .reduce(Integer::sum)
                .get();
    }

    public Long getPrice(String name) {
        final List<ProductPromotion> products = findByName(name);
        validateName(products);

        return products.stream()
                .map(ProductPromotion::getProductPrice)
                .reduce(Long::sum)
                .get();
    }

    public Integer getDiscountQuantity(String name) {
        final List<ProductPromotion> products = findByName(name);
        validateName(products);

        return products.stream()
                .map(ProductPromotion::getAppliedPromotionQuantity)
                .reduce(Integer::sum)
                .get();
    }

    public Long getDiscountPrice(String name) {
        final List<ProductPromotion> products = findByName(name);
        validateName(products);

        return products.stream()
                .map(ProductPromotion::getAppliedPromotionPrice)
                .reduce(Long::sum)
                .get();
    }

    private void validate(List<ProductPromotion> items, Integer quantity) {
        validateName(items);
        validateQuantity(items, quantity);
    }

    private void validateName(List<ProductPromotion> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }
    }

    private void validateQuantity(List<ProductPromotion> items, Integer quantity) {
        items.stream()
                .map(ProductPromotion::getProductQuantity)
                .reduce(Integer::sum)
                .filter(sum -> sum >= quantity)
                .orElseThrow(() -> new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."));
    }

    private List<ProductPromotion> findByName(String name) {
        return items.stream()
                .filter(item -> item.getProductName().equals(name))
                .toList();
    }
}
