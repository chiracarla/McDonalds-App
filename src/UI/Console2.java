package UI;
import Controller.OfferController;
import Controller.OrderController;
import Controller.ProductController;
import Controller.UserController;
import Enums.DishSize;
import Enums.DrinkVolume;
import Enums.Locations;
import Enums.ManagerRank;
import Model.*;
import Repository.IRepository;
import Repository.InMemoryRepository;
import Service.OfferService;
import Service.OrderService;
import Service.ProductService;
import Service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
public class Console2 {
    public static void main(String[] args) {
        IRepository<User> userRepo = new InMemoryRepository<>();
        IRepository<Client> clientRepo = new InMemoryRepository<>();
        IRepository<Manager> managerRepo = new InMemoryRepository<>();
        IRepository<Employee> employeeRepo = new InMemoryRepository<>();
        IRepository<Order> orderRepo = new InMemoryRepository<>();
        IRepository<Product> productRepo = new InMemoryRepository<>();
        IRepository<MainDish> mainDRepo = new InMemoryRepository<>();
        IRepository<SideDish> sideDRepo = new InMemoryRepository<>();
        IRepository<Drinks> drinkRepo = new InMemoryRepository<>();
        IRepository<Desserts> dessertRepo = new InMemoryRepository<>();
        IRepository<Location> locationRepo = new InMemoryRepository<>();
        IRepository<Offer> offerRepo = new InMemoryRepository<>();
        UserService userService = new UserService(userRepo, clientRepo, managerRepo, employeeRepo);
        UserController userController = new UserController(userService);

        OrderService orderService = new OrderService(orderRepo, locationRepo);
        OrderController orderController = new OrderController(orderService);

        ProductService productService = new ProductService(productRepo, mainDRepo, sideDRepo, dessertRepo, drinkRepo);
        ProductController productController = new ProductController(productService);

        OfferService offerService = new OfferService(offerRepo);
        OfferController offerController = new OfferController(offerService);

        productController.createMainDish("Hamburger", 12, 1307, DishSize.MEDIUM, 1);
        productController.createMainDish("Cheeseburger", 13, 1350, DishSize.MEDIUM, 2);
        productController.createMainDish("Big Mac", 15, 1500, DishSize.LARGE, 3);

        productController.createSideDish("French Fries", 5, DishSize.MEDIUM, 6);
        productController.createSideDish("Chicken McNuggets", 8, DishSize.MEDIUM, 7);

        productController.createDrink("Sprite", 3, DrinkVolume._300ML, 9);
        productController.createDrink("Lipton", 3, DrinkVolume._200ML, 7);
        List<Product> offerList = new ArrayList<>();
        offerList.add(productController.getProduct("Cheeseburger"));
        offerController.add(3, offerList);
        userController.signUpManager("klara.orban@yahoo.com", "Orban Klara", "1234", ManagerRank.Senior);
        userController.signUpClient("chira.carla@gmail.com", "Chira Carla", "5678");
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        int signedInID;
        String userType;
        System.out.println("Select an option:");
        System.out.println("1. Sign Up Manager");
        System.out.println("2. Sign Up Employee");
        System.out.println("3. Sign Up Client");
        System.out.println("4. Sign In");
        System.out.println("0. Exit");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
//begin session as employee/client/ manager
        switch (choice) {
            case 1:
                System.out.println("Enter email:");
                String managerEmail = scanner.nextLine();
                System.out.println("Enter name:");
                String managerName = scanner.nextLine();
                System.out.println("Enter password:");
                String managerPassword = scanner.nextLine();
                System.out.println("Enter role:");
                String managerRank = scanner.nextLine();
                userController.signUpManager(managerEmail, managerName, managerPassword, ManagerRank.valueOf(managerRank));
                break;
            case 2:
                System.out.println("Enter email:");
                String employeeEmail = scanner.nextLine();
                System.out.println("Enter name:");
                String employeeName = scanner.nextLine();
                System.out.println("Enter password:");
                String employeePassword = scanner.nextLine();
                System.out.println("Enter manager ID:");
                int manID = scanner.nextInt();
                Manager employeeManager = userController.readManager(manID);
                userController.signUpEmployee(employeeEmail, employeeName, employeePassword,employeeManager);
                break;
            case 3:
                System.out.println("Enter email:");
                String clientEmail = scanner.nextLine();
                System.out.println("Enter name:");
                String clientName = scanner.nextLine();
                System.out.println("Enter password:");
                String clientPassword = scanner.nextLine();
                userController.signUpClient(clientEmail, clientName, clientPassword);
                break;
            case 4:
                System.out.println("Enter email:");
                String email = scanner.nextLine();
                System.out.println("Enter password:");
                String password = scanner.nextLine();
                userController.signIn(email, password);
                break;
        }
    }

}
