package Service;

import Enums.Allergens;
import Enums.DishSize;
import Enums.DrinkVolume;
import Model.*;
import Repository.IRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 *
 */
public class ProductService {
    private final IRepository<Product> productRepo;;
    private final IRepository<MainDish> mainDishRepo;
    private final IRepository<SideDish> sideDishRepo;
    private final IRepository<Desserts> dessertRepo;
    private final IRepository<Drinks> drinkRepo;

    /**
     *
     * @param productRepo
     * @param mainDishRepo
     * @param sideDishRepo
     * @param dessertRepo
     * @param drinkRepo
     */
    public ProductService(IRepository<Product> productRepo, IRepository<MainDish> mainDishRepo, IRepository<SideDish> sideDishRepo, IRepository<Desserts> dessertRepo, IRepository<Drinks> drinkRepo) {
        this.productRepo = productRepo;
        this.mainDishRepo = mainDishRepo;
        this.sideDishRepo = sideDishRepo;
        this.dessertRepo = dessertRepo;
        this.drinkRepo = drinkRepo;
    }
    //TODO: parca trebuia o clasa abstracta

    /**
     *
     * @param productName
     * @param productPrice
     * @param calories
     * @param size
     */
    public void createMainDish(String productName, int productPrice, int calories, DishSize size) {
        Product mainDish = new MainDish(productName, productPrice, calories, size, generateNewOfferID());

        mainDishRepo.create((MainDish) mainDish);
        productRepo.create((Product) mainDish);

        System.out.println("Main Dish created: " + productName + " with price: " + productPrice + ", calories: " + calories + ", size: " + size);
    }

    /**
     *
     * @param productName
     * @param productPrice
     * @param size
     */
    public void createSideDish(String productName, int productPrice, DishSize size){
        Product sideDish = new SideDish(productName, productPrice, size, generateNewOfferID());

        sideDishRepo.create((SideDish) sideDish);
        productRepo.create((Product) sideDish);

        System.out.println("Side Dish created: " + productName + " with price: " + productPrice + ", size: " + size);
    }

    /**
     *
     * @param productName
     * @param productPrice
     * @param allergens
     */
    public void createDessert(String productName, int productPrice, Allergens allergens){
        Desserts desserts = new Desserts(productName, productPrice, allergens,generateNewOfferID());

        dessertRepo.create((Desserts) desserts);
        productRepo.create((Product) desserts);

        System.out.println("Dessert created: " + productName + " with price: " + productPrice + ", allergens: " + allergens);
    }

    /**
     *
     * @param productName
     * @param productPrice
     * @param volume
     */
    public void createDrink(String productName, int productPrice, DrinkVolume volume) {
        Drinks drinks = new Drinks(productName, productPrice, volume, generateNewOfferID());

        drinkRepo.create((Drinks) drinks);
        productRepo.create((Product) drinks);

        System.out.println("Drink created: " + productName + " with price: " + productPrice + ", volume: " + volume);
    }

    /**
     *
     * @param productName
     * @return
     */
    public Product getProduct(String productName){
        Optional<Product> existingProduct = productRepo.getAll().stream()
                .filter(product -> product.getProductName().equals(productName))
                .findFirst();
        if(existingProduct.isPresent()){
            return existingProduct.get();
        }
        System.out.println("No product found with name: " + productName);
        return null;
    }
    //problema posibila: produse cu acelasi nume dar marimi diferite: solutie- selectarea marimii
    // cu cresterea marimii se adauga 2 lei?
    //delete+update + read

    //TODO: verify - product sorting
    /**
     *this function sorts the products by price
     * functionality: user can choose products according to their budget
     * @return
     */
    public List<Product> sortProductsByPrice(){
        List<Product> products = productRepo.getAll();
        products.sort(Comparator.comparingInt(Product::getProductPrice));
        return products;
    }

    private int generateNewOfferID() {
        return productRepo.getAll().stream()
                .mapToInt(Product::getProdId)
                .max().orElse(0)+1;
    }

    //TODO: allergens filter
    /**
     * this function filters the products by allergens
     * functionality: user can choose products according to their allergies
     * @param allergen
     * @return
     */
    public List<Product> filterProductsByAllergens(Allergens allergen){
        List<Product> products = productRepo.getAll();
        products.removeIf(product -> product.getAllergens() != null && product.getAllergens().equals(allergen));
        return products;
    }
}
