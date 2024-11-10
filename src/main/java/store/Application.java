package store;

import store.controller.ProductController;
import store.view.InputView;
import store.view.OutputView;

import java.io.FileNotFoundException;

public class Application {
    public static void main(String[] args) {
        ProductController productController = new ProductController(new InputView(), new OutputView());
        productController.run();
    }
}
