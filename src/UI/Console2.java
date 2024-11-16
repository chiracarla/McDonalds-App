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

/**
 * Manager can:
 *      view all managers, employees, clients
 *      add new products
 *      add and assign new offers
 * Employees and Clients can:
 *      make an order
 *      view offers
 *      delete/edit account
 */
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

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to McDonald's App!");
            System.out.println("1. Sign Up");
            System.out.println("2. Sign In");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    handleSignUp(scanner, userController);
                    break;
                case 2:
                    handleSignIn(scanner, userController, orderController, productController);
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
    private static void handleSignUp(Scanner scanner, UserController userController) {
        System.out.println("Select user type to sign up:");
        System.out.println("1. Client");
        System.out.println("2. Employee");
        System.out.println("3. Manager");
        System.out.print("Choice: ");
        int type = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        switch (type) {
            case 1:
                userController.signUpClient(name, email, password);
                break;
            case 2:
                System.out.print("Enter manager ID: ");
                int manID = scanner.nextInt();
                Manager employeeManager = userController.readManager(manID);
                userController.signUpEmployee(name, email, password, employeeManager);
                break;
            case 3:
                System.out.println("Enter rank (Senior, Junior):");
                String managerRank = scanner.nextLine();
                userController.signUpManager(name, email, password, ManagerRank.valueOf(managerRank));
                break;
            default:
                System.out.println("Invalid user type.");
        }
        System.out.println("Sign-up successful!");
    }

    private static void handleSignIn(Scanner scanner, UserController userController, OrderController orderController, ProductController productController) {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        userController.signIn(email, password).ifPresentOrElse(
                        user -> showUserMenu(user, orderController, productController),
                        () -> System.out.println("Invalid email or password."));
    }

    private static void showUserMenu(User user, OrderController orderController, ProductController productController) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nWelcome, " + user.getClass().getSimpleName() + ": " + user.getName());
//            user.displayOptions();
            //TODO
            System.out.print("Select an option (or 0 to logout): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Enter manager email:");
                    String locManEmail = scanner.next();
                    Location location = orderController.getLocations().stream()
                            .filter(l -> l.getStoreManager().getEmail().equals(locManEmail))
                            .findFirst()
                            .orElse(null);
                    //TODO:de schimbat cu numele locatiei
                    //TODO:valabil si pt employee
                    List<Product> productList = new ArrayList<>();
                    System.out.println("Enter product names (comma separated):");
                    scanner.nextLine();
                    String[] productNames = scanner.nextLine().split(",");
                    for (String productName : productNames) {
                        Product product = productController.getProduct(productName.trim());
                        if (product != null) {
                            productList.add(product);
                        }
                    }
                    System.out.println("Apply offer? (yes/no):");
                    String applyOffer = scanner.next();
                    Optional<Offer> offer = Optional.empty();
                    if (applyOffer.equalsIgnoreCase("yes")) {
                        System.out.println("Enter offer ID:");
                        int offerId = scanner.nextInt();
                        offer = Optional.ofNullable(user.getOffers().get(offerId));
                    }
                    System.out.println("Pay with points? (true/false):");
                    boolean payWithPoints = scanner.nextBoolean();
                    orderController.createOrder((Client)user, location, productList, offer, payWithPoints);
                    break;
                case 2:
                    for(Offer o : user.getOffers()) {
                        System.out.println(o.toString());
                    }
                case 3:
                    System.out.println("Option 3 logic...");
                case 4:
                {
                    if (user instanceof Manager) {
                        System.out.println("Manager-specific logic...");
                    } else {
                        System.out.println("Invalid option!");
                    }
                }
                case 0 :
                    running = false;
                default :
                    System.out.println("Invalid option!");
            }
        }
    }
}
