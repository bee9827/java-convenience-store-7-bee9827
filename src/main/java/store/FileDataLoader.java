package store;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class FileDataLoader<T> implements DataLoader<T> {
    private static final String productFilePath = "src/main/resources/products.md";
    private static final String promotionFilePath = "src/main/resources/promotions.md";

    public List<Promotion> createPromotions() throws FileNotFoundException {
        DataLoader<Promotion> loader = new FileDataLoader<>();
        return loader.loadData(promotionFilePath, this::makePromotion);
    }

    public List<Product> createProducts() throws FileNotFoundException {
        DataLoader<Product> loader = new FileDataLoader<>();
        return loader.loadData(productFilePath, this::makeProduct);
    }

    public List<T> loadData(String filePath, Function<String[], T> creator) throws FileNotFoundException {
        List<T> list = new ArrayList<>();
        Scanner sc = new Scanner(new File(filePath));
        sc.nextLine();  //헤더
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] tokens = line.split(",");
            list.add(creator.apply(tokens));
        }
        sc.close(); // Scanner 닫기
        return list;
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
