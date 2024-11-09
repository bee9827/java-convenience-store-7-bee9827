package store;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FileDataLoaderTest {
    public FileDataLoader fileDataLoader = new FileDataLoader();

    @Test
    void createPromotions(){
        List<Promotion> promotions = fileDataLoader.createPromotions();
        assertThat(promotions.getFirst()).isInstanceOf(Promotion.class);
    }

    @Test
    void createProducts(){
        List<Product> products = fileDataLoader.createProducts();
        assertThat(products.getFirst()).isInstanceOf(Product.class);
    }
}