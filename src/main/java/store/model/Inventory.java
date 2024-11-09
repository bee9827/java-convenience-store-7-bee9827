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
    private final List<ProductPromotion> stocks;

    public Inventory(List<ProductPromotion> stocks) {
        this.stocks = stocks;
    }

    public Inventory order(String name, Integer quantity) {
        List<ProductPromotion> targetStocks = findByName(name);
        validate(targetStocks, quantity);

        List<ProductPromotion> orderedStocks = new ArrayList<ProductPromotion>();

        for (ProductPromotion targetStock : targetStocks) {
            orderedStocks.add(targetStock.order(quantity));
        }
        return new Inventory(orderedStocks);
    }

    public Integer getQuantity(String name) {
        final List<ProductPromotion> products = findByName(name);
        validateName(products);

        return products.stream()
                .map(ProductPromotion::getProductQuantity)
                .reduce(Integer::sum)
                .get();
    }

    public List<ProductPromotion> getProductPromotions() {
        return Collections.unmodifiableList(stocks);
    }

    private void validate(List<ProductPromotion> stocks, Integer quantity) {
        validateName(stocks);
        validateQuantity(stocks, quantity);
    }

    private void validateName(List<ProductPromotion> stocks) {
        if (stocks == null || stocks.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }
    }

    private void validateQuantity(List<ProductPromotion> stocks, Integer quantity) {
        stocks.stream()
                .map(ProductPromotion::getProductQuantity)
                .reduce(Integer::sum)
                .filter(sum -> sum >= quantity)
                .orElseThrow(() -> new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."));
    }

    private List<ProductPromotion> findByName(String name) {
        return stocks.stream()
                .filter(stock -> stock.getProductName().equals(name))
                .toList();
    }
}
