import Controller.OfferController;
import Controller.OrderController;
import Controller.ProductController;
import Controller.UserController;
import Enums.DishSize;
import Enums.DrinkVolume;
import Enums.Locations;
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

public class ConsoleApp {
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

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Select an option:");
            System.out.println("1. Sign Up Manager");
            System.out.println("2. Sign Up Client");
            System.out.println("3. Sign In");
            System.out.println("4. Show All Managers");
            System.out.println("5. Create Location");
            System.out.println("6. Create Main Dish");
            System.out.println("7. Create Side Dish");
            System.out.println("8. Create Drink");
            System.out.println("9. Analyze Most Ordered");
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Enter email:");
                    String managerEmail = scanner.nextLine();
                    System.out.println("Enter name:");
                    String managerName = scanner.nextLine();
                    System.out.println("Enter password:");
                    String managerPassword = scanner.nextLine();
                    System.out.println("Enter role:");
                    String managerRole = scanner.nextLine();
                    userController.sign_Up_Manager(managerEmail, managerName, managerPassword, managerRole);
                    break;
                case 2:
                    System.out.println("Enter email:");
                    String clientEmail = scanner.nextLine();
                    System.out.println("Enter name:");
                    String clientName = scanner.nextLine();
                    System.out.println("Enter password:");
                    String clientPassword = scanner.nextLine();
                    userController.sign_Up_Client(clientEmail, clientName, clientPassword);
                    break;
                case 3:
                    System.out.println("Enter email:");
                    String email = scanner.nextLine();
                    System.out.println("Enter password:");
                    String password = scanner.nextLine();
                    userController.sign_In(email, password);
                    break;
                case 4:
                    userController.showAllManagers();
                    break;
                case 5:
                    System.out.println("Enter location:");
                    String locationName = scanner.nextLine();
                    System.out.println("Enter manager email:");
                    String locManagerEmail = scanner.nextLine();
                    Manager manager = userService.getAllManagers().stream()
                            .filter(m -> m.getEmail().equals(locManagerEmail))
                            .findFirst()
                            .orElse(null);
                    if (manager != null) {
                        Locations location = Locations.valueOf(locationName);
                        orderController.createLocation(location, manager);
                    } else {
                        System.out.println("Manager not found.");
                    }
                    break;
                case 6:
                    System.out.println("Enter main dish name:");
                    String mainDishName = scanner.nextLine();
                    System.out.println("Enter price:");
                    int mainDishPrice = scanner.nextInt();
                    System.out.println("Enter ID:");
                    int mainDishId = scanner.nextInt();
                    System.out.println("Enter size (SMALL, MEDIUM, LARGE):");
                    String mainDishSize = scanner.next();
                    System.out.println("Enter stock:");
                    int mainDishStock = scanner.nextInt();
                    productController.createMainDish(mainDishName, mainDishPrice, mainDishId, DishSize.valueOf(mainDishSize), mainDishStock);
                    break;
                case 7:
                    System.out.println("Enter side dish name:");
                    String sideDishName = scanner.nextLine();
                    System.out.println("Enter price:");
                    int sideDishPrice = scanner.nextInt();
                    System.out.println("Enter size (SMALL, MEDIUM, LARGE):");
                    String sideDishSize = scanner.next();
                    System.out.println("Enter ID:");
                    int sideDishId = scanner.nextInt();
                    productController.createSideDish(sideDishName, sideDishPrice, DishSize.valueOf(sideDishSize), sideDishId);
                    break;
                case 8:
                    System.out.println("Enter drink name:");
                    String drinkName = scanner.nextLine();
                    System.out.println("Enter price:");
                    int drinkPrice = scanner.nextInt();
                    System.out.println("Enter volume (ML_200, ML_300, ML_500):");
                    String drinkVolume = scanner.next();
                    System.out.println("Enter ID:");
                    int drinkId = scanner.nextInt();
                    productController.createDrink(drinkName, drinkPrice, DrinkVolume.valueOf(drinkVolume), drinkId);
                    break;
                case 9:
                    orderController.analyzeMostOrdered();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }
}