package Service;

import Enums.Allergens;
import Enums.DishSize;
import Enums.DrinkVolume;
import Model.*;
import Repository.IRepository;

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

    public void create_main_dish(String productName, int productPrice, int calories, DishSize size) {
        MainDish mainDish = new MainDish(productName, productPrice, calories, size);

        mainDishRepo.create(mainDish);

        System.out.println("Main Dish created: " + productName + " with price: " + productPrice + ", calories: " + calories + ", size: " + size);
    }

    public void create_side_dish(String productName, int productPrice, DishSize size){
        SideDish sideDish = new SideDish(productName, productPrice, size);

        sideDishRepo.create(sideDish);

        System.out.println("Side Dish created: " + productName + " with price: " + productPrice + ", size: " + size);
    }

    public void create_dessert(String productName, int productPrice, Allergens allergens){
        Desserts desserts = new Desserts(productName, productPrice, allergens);

        dessertRepo.create(desserts);

        System.out.println("Dessert created: " + productName + " with price: " + productPrice + ", allergens: " + allergens);
    }

    public void create_drink(String productName, int productPrice, DrinkVolume volume){
        Drinks drinks = new Drinks(productName, productPrice, volume);

        drinkRepo.create(drinks);

        System.out.println("Drink created: " + productName + " with price: " + productPrice + ", volume: " + volume);
    }

}
