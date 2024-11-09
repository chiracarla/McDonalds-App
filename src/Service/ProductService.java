package Service;

import Enums.Allergens;
import Enums.DishSize;
import Enums.DrinkVolume;
import Model.*;
import Repository.IRepository;

import java.util.Optional;

public class ProductService {
    private final IRepository<Product> productRepo;;
    private final IRepository<MainDish> mainDishRepo;
    private final IRepository<SideDish> sideDishRepo;
    private final IRepository<Desserts> dessertRepo;
    private final IRepository<Drinks> drinkRepo;

    public ProductService(IRepository<Product> productRepo, IRepository<MainDish> mainDishRepo, IRepository<SideDish> sideDishRepo, IRepository<Desserts> dessertRepo, IRepository<Drinks> drinkRepo) {
        this.productRepo = productRepo;
        this.mainDishRepo = mainDishRepo;
        this.sideDishRepo = sideDishRepo;
        this.dessertRepo = dessertRepo;
        this.drinkRepo = drinkRepo;
    }
//astea nu vrem oare sa le punem la products? + nu putem instantia clase abstracte nush care ar fi tho
    //maii tb cv si la offers
    public void create_main_dish(String productName, int productPrice, int calories, DishSize size) {
        Product mainDish = new MainDish(productName, productPrice, calories, size);

        mainDishRepo.create((MainDish) mainDish);
        productRepo.create((Product) mainDish);

        System.out.println("Main Dish created: " + productName + " with price: " + productPrice + ", calories: " + calories + ", size: " + size);
    }

    public void create_side_dish(String productName, int productPrice, DishSize size){
        Product sideDish = new SideDish(productName, productPrice, size);

        sideDishRepo.create((SideDish) sideDish);
        productRepo.create((Product) sideDish);

        System.out.println("Side Dish created: " + productName + " with price: " + productPrice + ", size: " + size);
    }

    public void create_dessert(String productName, int productPrice, Allergens allergens){
        Desserts desserts = new Desserts(productName, productPrice, allergens);

        dessertRepo.create((Desserts) desserts);
        productRepo.create((Product) desserts);

        System.out.println("Dessert created: " + productName + " with price: " + productPrice + ", allergens: " + allergens);
    }

    public void create_drink(String productName, int productPrice, DrinkVolume volume) {
        Drinks drinks = new Drinks(productName, productPrice, volume);

        drinkRepo.create((Drinks) drinks);
        productRepo.create((Product) drinks);

        System.out.println("Drink created: " + productName + " with price: " + productPrice + ", volume: " + volume);
    }

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
