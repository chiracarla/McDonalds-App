package Controller;

import Enums.Allergens;
import Enums.DishSize;
import Enums.DrinkVolume;
import Model.Product;
import Service.ProductService;

import java.util.List;

/**
 * controller for managing products using product service
 */
public class ProductController {
    private final ProductService productService; //nu are repos asociate

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * creates new main dish
     * @param productName
     * @param productPrice
     * @param calories
     * @param size
     * @param id
     */
    public void createMainDish(String productName, int productPrice, int calories, DishSize size, int id){
        productService.createMainDish(productName, productPrice, calories, size, id);
    }

    /**
     * creates new side dish
     * @param productName
     * @param productPrice
     * @param size
     * @param id
     */
    public void createSideDish(String productName, int productPrice, DishSize size, int id){
        productService.createSideDish(productName, productPrice, size,id);
    }

    /**
     * creates new dessert
     * @param productName
     * @param productPrice
     * @param allergens
     * @param id
     */
    public void createDessert(String productName, int productPrice, Allergens allergens, int id){
        productService.createDessert(productName, productPrice, allergens, id);
    }

    /**
     * creates new drink
     * @param productName
     * @param productPrice
     * @param volume
     * @param id
     */
    public void createDrink(String productName, int productPrice, DrinkVolume volume, int id){
        productService.createDrink(productName, productPrice, volume, id);
    }

    /**
     * finds a product by name
     * @param productName
     * @return
     */
    public Product getProduct(String productName){
        return productService.getProduct(productName);
    }

    public List<Product> sortProductsByPrice(){
        return productService.sortProductsByPrice();
    }

    public List<Product> filterProductsByAllergen(Allergens allergen){
        return productService.filterProductsByAllergens(allergen);
    }
}
