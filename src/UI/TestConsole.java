package UI;
import Controller.OfferController;
import Controller.OrderController;
import Controller.ProductController;
import Controller.UserController;
import Enums.*;
import Model.*;
import Repository.CompositeRepository;
import Repository.FileRepository;
import Repository.*;
import Repository.InMemoryRepository;
import Service.OfferService;
import Service.OrderService;
import Service.ProductService;
import Service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
public class TestConsole {
    public static void main(String[] args) {
//        IRepository<User> userRepo = new FileRepository<>("src\\Files\\users.txt");
//        IRepository<Client> clientRepo =  new FileRepository<>("src\\Files\\clients.txt");
//        IRepository<Manager> managerRepo = new FileRepository<>("src\\Files\\managers.txt");
//        IRepository<Employee> employeeRepo = new FileRepository<>("src\\Files\\employees.txt");
//        IRepository<Order> orderRepo = new CompositeRepository<>(new InMemoryRepository<>(), new FileRepository<>("src\\Files\\orders.txt"));
//        IRepository<Product> productRepo = new CompositeRepository<>(new InMemoryRepository<>(), new FileRepository<>("src\\Files\\products.txt"));
//        IRepository<MainDish> mainDRepo = new CompositeRepository<>(new InMemoryRepository<>(), new FileRepository<>("src\\Files\\mainDishes.txt"));
//        IRepository<SideDish> sideDRepo = new CompositeRepository<>(new InMemoryRepository<>(), new FileRepository<>("src\\Files\\sideDishes.txt"));
//        IRepository<Drinks> drinkRepo = new CompositeRepository<>(new InMemoryRepository<>(), new FileRepository<>("src\\Files\\drinks.txt"));
//        IRepository<Desserts> dessertRepo = new CompositeRepository<>(new InMemoryRepository<>(), new FileRepository<>("src\\Files\\desserts.txt"));
//        IRepository<Location> locationRepo = new CompositeRepository<>(new InMemoryRepository<>(), new FileRepository<>("src\\Files\\locations.txt"));
//        IRepository<Offer> offerRepo = new CompositeRepository<>(new InMemoryRepository<>(), new FileRepository<>("src\\Files\\offers.txt"));
        IRepository<Client> clientRepo = new ClientFileRepository("clients.txt");
        IRepository<Manager> managerRepo = new ManagerFileRepository("managers.txt");
        IRepository<User> userRepo = new UserFileRepository("users.txt");
        IRepository<Employee> employeeRepo = new EmployeeFileRepository("employee.txt");
        IRepository<Desserts> dessertRepo = new DessertFileRepository("desserts.txt");
        IRepository<Drinks> drinkRepo = new DrinkFileRepository("drinks.txt");
        IRepository<MainDish> mainsRepo = new MainsFileRepository("mains.txt");
        IRepository<SideDish> sidesRepo = new SidesFileRepository("sides.txt");
        IRepository<Product> prodsRepo = new ProductFileRepository("prods.txt");

        UserService userService = new UserService(userRepo, clientRepo, managerRepo, employeeRepo);
        UserController userController = new UserController(userService);
//        UserService userService = new UserService(clientRepo);

        // Example usage
//        Manager manager = new Manager("manager@example.com", "John Doe", 1, "password", ManagerRank.Senior);
//        managerRepo.create(manager);
//        managerRepo.getAll().forEach(System.out::println);

//
//        OrderService orderService = new OrderService(orderRepo, locationRepo);
//        OrderController orderController = new OrderController(orderService);

        ProductService productService = new ProductService(prodsRepo, mainsRepo, sidesRepo, dessertRepo, drinkRepo);
        ProductController productController = new ProductController(productService);
//
//        OfferService offerService = new OfferService(offerRepo);
//        OfferController offerController = new OfferController(offerService);

//        productController.createMainDish("Hamburger", 12, 1307, DishSize.MEDIUM);
//        productController.createMainDish("Cheeseburger", 13, 1350, DishSize.MEDIUM);
//        productController.createMainDish("Big Mac", 15, 1500, DishSize.LARGE);
//
//        productController.createSideDish("French Fries", 5, DishSize.MEDIUM);
//        productController.createSideDish("Chicken McNuggets", 8, DishSize.MEDIUM);
//
//        productController.createDrink("Sprite", 3, DrinkVolume._300ML);
//        productController.createDrink("Lipton", 3, DrinkVolume._200ML);
//        List<Product> offerList = new ArrayList<>();
//        offerList.add(productController.getProduct("Cheeseburger"));
//        offerController.add(3, offerList);
        userController.signUpManager("klara.orban@yahoo.com", "Orban Klara", "1234", ManagerRank.Senior);
        userController.signUpClient("chira.carla@gmail.com", "Chira Carla", "5678");
//        orderController.createLocation(Locations.Bucuresti, userService.readManager(1));

        managerRepo.getAll().forEach(System.out::println);

        System.out.println(userRepo.read(1));

    }
}
