package Controller;

import Enums.Allergens;
import Enums.DishSize;
import Enums.DrinkVolume;
import Model.MainDish;
import Service.ProductService;

public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public MainDish createMainDish(String productName, int productPrice, int calories, DishSize size){
        productService.create_main_dish(productName, productPrice, calories, size);
        return null;
    }

    public void createSideDis(String productName, int productPrice, DishSize size){
        productService.create_side_dish(productName, productPrice, size);
    }

    public void createDessert(String productName, int productPrice, Allergens allergens){
        productService.create_dessert(productName, productPrice, allergens);
    }

    public void createDrink(String productName, int productPrice, DrinkVolume volume){
        productService.create_drink(productName, productPrice, volume);
    }
}
