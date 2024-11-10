package store.converter;

import store.file.DataLoader;
import store.file.FileDataLoader;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.PromotionType;
import store.model.Item;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductPromotionMaker {
    private static final String productFilePath = "src/main/resources/products.md";
    private static final String promotionFilePath = "src/main/resources/promotions.md";
    private final List<Promotion> promotions;
    private final List<Product> products;

    public ProductPromotionMaker(){
        //한번만 실행된다.
        this.promotions = createPromotions();
        validatePromotion();
        this.products = createProducts();
    }

    public List<Item> makeProdcutPromotions() {
        List<Item> items = new ArrayList<>();
        for (Product p : products) {
            items.add(makeProductPromotion(p));
        }

        return items;
    }

    private Item makeProductPromotion(Product product) {
        if (product.getPromotionName() == null)
            return new Item(product, null);

        return new Item(product, findPromotionByName(product.getPromotionName()));
    }

    private void validatePromotion() {
        Set<String> promotionsName = promotions.stream()
                .map(Promotion::getName)
                .collect(Collectors.toSet());

        if (promotionsName.size() != promotions.size()) {
            throw new IllegalArgumentException("동일한 이름의 프로모션이 2개이상 존재합니다.");
        }
    }

    private Promotion findPromotionByName(String promotionName) {
        if(Objects.equals(promotionName, "null")) return null;
        return promotions.stream()
                .filter(promotion -> promotion.getName().equals(promotionName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(STR."\{promotionName} 프로모션을 찾을수 없습니다."));
    }

    private List<Promotion> createPromotions() {
        DataLoader<Promotion> loader = new FileDataLoader<>();
        try {
            return loader.loadData(promotionFilePath, this::makePromotion);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Product> createProducts() {
        DataLoader<Product> loader = new FileDataLoader<>();
        try {
            return loader.loadData(productFilePath, this::makeProduct);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Product makeProduct(String[] tokens) {
        String name = tokens[0];
        Integer price = Integer.parseInt(tokens[1]);
        Integer quantity = Integer.parseInt(tokens[2]);
        String promotionName = tokens[3];

        return new Product(name, price, quantity, promotionName);
    }

    private Promotion makePromotion(String[] tokens) {
        String name = tokens[0];
        PromotionType type = PromotionType.of(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
        LocalDate startDate = LocalDate.parse(tokens[3]);
        LocalDate endDate = LocalDate.parse(tokens[4]);

        return new Promotion(name, type, startDate, endDate);
    }
}
