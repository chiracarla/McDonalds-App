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
     */
    public void createMainDish(String productName, int productPrice, int calories, DishSize size){
        productService.createMainDish(productName, productPrice, calories, size);
    }

    public Product readProduct(int id){
        return  productService.readProduct(id);
    }

    /**
     * creates new side dish
     * @param productName
     * @param productPrice
     * @param size
     */
    public void createSideDish(String productName, int productPrice, DishSize size){
        productService.createSideDish(productName, productPrice, size);
    }

    /**
     * creates new dessert
     * @param productName
     * @param productPrice
     * @param allergens
     */
    public void createDessert(String productName, int productPrice, Allergens allergens){
        productService.createDessert(productName, productPrice, allergens);
    }

    /**
     * creates new drink
     * @param productName
     * @param productPrice
     * @param volume
     */
    public void createDrink(String productName, int productPrice, DrinkVolume volume){
        productService.createDrink(productName, productPrice, volume);
    }

    /**
     * finds a product by name
     * @param productName
     * @return
     */
    public Product getProduct(String productName){
        return productService.getProduct(productName);
    }

    public List<Product> getAllProducts(){
        return productService.getAll();
    }

    public List<Product> sortProductsByPrice(){
        return productService.sortProductsByPrice();
    }

    /**
     * filters products by allergen
     * @param allergen
     * @return
     */
    public List<Product> filterProductsByAllergen(Allergens allergen){
        return productService.filterProductsByAllergens(allergen);
    }
}
