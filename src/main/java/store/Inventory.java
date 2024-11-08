package store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

// TODO
//  - [x] 유효성 체크
//      - 개수
//  - [x] 주문
//      - 주문된 Product를 던진다.
//  - [x] 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 필요한 수량을 추가로 가져오면 혜택을 받을 수 있음을 안내한다.
//  - [ ] 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제하게 됨을 안내한다.

public class Inventory {
    private final List<Product> products;

    public Inventory(List<Product> products) {
        this.products = products;
    }

    public Inventory order(String name, Integer quantity) {
        List<Product> stocks = findByName(name);
        validate(stocks, quantity);

        List<Product> ordered = new ArrayList<Product>();
        for (Product productStock : stocks) {
            Product orderedProduct = productStock.order(quantity);
            quantity -= orderedProduct.getQuantity();
            ordered.add(orderedProduct);
        }
        return new Inventory(ordered);
    }

    public Integer getQuantity(String name) {
        final List<Product> products = findByName(name);
        validateName(products);

        return products.stream()
                .map(Product::getQuantity)
                .reduce(Integer::sum)
                .get();
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    private void validate(List<Product> stocks, Integer quantity) {
        validateName(stocks);
        validateQuantity(stocks, quantity);
    }

    private void validateName(List<Product> stocks) {
        if (stocks == null || stocks.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }
    }

    private void validateQuantity(List<Product> stocks, Integer quantity) {
        stocks.stream()
                .map(Product::getQuantity)
                .reduce(Integer::sum)
                .filter(sum -> sum >= quantity)
                .orElseThrow(() -> new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."));
    }

    private List<Product> findByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .toList();
    }
}
