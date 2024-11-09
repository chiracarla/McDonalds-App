package Controller;

import Enums.Allergens;
import Enums.DishSize;
import Enums.DrinkVolume;
import Model.MainDish;
import Model.Product;
import Service.ProductService;

public class ProductController {
    private final ProductService productService; //nu are repos asociate

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public void createMainDish(String productName, int productPrice, int calories, DishSize size){
        productService.create_main_dish(productName, productPrice, calories, size);
    }

    public void createSideDish(String productName, int productPrice, DishSize size){
        productService.create_side_dish(productName, productPrice, size);
    }

    public void createDessert(String productName, int productPrice, Allergens allergens){
        productService.create_dessert(productName, productPrice, allergens);
    }

    public void createDrink(String productName, int productPrice, DrinkVolume volume){
        productService.create_drink(productName, productPrice, volume);
    }

    public Product getProduct(String productName){
        return productService.getProduct(productName);
    }
}
