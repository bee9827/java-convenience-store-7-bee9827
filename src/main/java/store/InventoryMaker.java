package store;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InventoryMaker {
    private static final String productFilePath = "src/main/resources/products.md";
    private static final String promotionFilePath = "src/main/resources/promotions.md";
    private FileDataLoader fileDataLoader;

    public Inventory makeInventory(FileDataLoader fileDataLoader) {
        try {
            return new Inventory(createProducts(), createPromotions());
        }catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Promotion> createPromotions() throws FileNotFoundException {
        DataLoader<Promotion> loader = new FileDataLoader<>();
        return loader.loadData(promotionFilePath, this::makePromotion);
    }

    public List<Product> createProducts() throws FileNotFoundException {
        DataLoader<Product> loader = new FileDataLoader<>();
        return loader.loadData(productFilePath, this::makeProduct);
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
