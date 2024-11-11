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

    public void createMainDish(String productName, int productPrice, int calories, DishSize size, int id){
        productService.create_main_dish(productName, productPrice, calories, size, id);
    }

    public void createSideDish(String productName, int productPrice, DishSize size, int id){
        productService.create_side_dish(productName, productPrice, size,id);
    }

    public void createDessert(String productName, int productPrice, Allergens allergens, int id){
        productService.create_dessert(productName, productPrice, allergens, id);
    }

    public void createDrink(String productName, int productPrice, DrinkVolume volume, int id){
        productService.create_drink(productName, productPrice, volume, id);
    }

    public Product getProduct(String productName){
        return productService.getProduct(productName);
    }
}
