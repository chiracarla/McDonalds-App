package Service;

import Enums.Allergens;
import Enums.DishSize;
import Enums.DrinkVolume;
import Model.*;
import Repository.IRepository;

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
     * @param id
     */
    public void createMainDish(String productName, int productPrice, int calories, DishSize size, int id) {
        Product mainDish = new MainDish(productName, productPrice, calories, size, id);

        mainDishRepo.create((MainDish) mainDish);
        productRepo.create((Product) mainDish);

        System.out.println("Main Dish created: " + productName + " with price: " + productPrice + ", calories: " + calories + ", size: " + size);
    }

    /**
     *
     * @param productName
     * @param productPrice
     * @param size
     * @param id
     */
    public void createSideDish(String productName, int productPrice, DishSize size , int id){
        Product sideDish = new SideDish(productName, productPrice, size, id);

        sideDishRepo.create((SideDish) sideDish);
        productRepo.create((Product) sideDish);

        System.out.println("Side Dish created: " + productName + " with price: " + productPrice + ", size: " + size);
    }

    /**
     *
     * @param productName
     * @param productPrice
     * @param allergens
     * @param id
     */
    public void createDessert(String productName, int productPrice, Allergens allergens, int id){
        Desserts desserts = new Desserts(productName, productPrice, allergens, id);

        dessertRepo.create((Desserts) desserts);
        productRepo.create((Product) desserts);

        System.out.println("Dessert created: " + productName + " with price: " + productPrice + ", allergens: " + allergens);
    }

    /**
     *
     * @param productName
     * @param productPrice
     * @param volume
     * @param id
     */
    public void createDrink(String productName, int productPrice, DrinkVolume volume, int id) {
        Drinks drinks = new Drinks(productName, productPrice, volume, id);

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

}
