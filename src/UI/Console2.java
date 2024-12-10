package UI;
import Controller.OfferController;
import Controller.OrderController;
import Controller.ProductController;
import Controller.UserController;
import Enums.*;
import Model.*;
import Repository.*;
import Repository.DbRepository.*;
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

//create location, deleete prods/offers, assign employees to orders, transform to menu/combo, view menu
    //inn products.txt se salveaza doar drinks aparent
    //fix toStrings
public class Console2 {
    public static void main(String[] args) {

        IRepository<User> userRepo;
        IRepository<Client> clientRepo;
        IRepository<Manager> managerRepo;
        IRepository<Employee> employeeRepo;
        IRepository<Order> orderRepo;
        IRepository<Product> prodsRepo;
        IRepository<MainDish> mainsRepo;
        IRepository<SideDish> sidesRepo;
        IRepository<Drinks> drinkRepo;
        IRepository<Desserts> dessertRepo;
        IRepository<Location> locationRepo;
        IRepository<Offer> offerRepo;
        System.out.println("1. In Memory");
        System.out.println("2. File");
        System.out.println("3. Database");
        System.out.print("Select an option: ");
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        scanner.nextLine();

        if (option == 1) {
            userRepo = new InMemoryRepository<>();
            clientRepo = new InMemoryRepository<>();
            managerRepo = new InMemoryRepository<>();
            employeeRepo = new InMemoryRepository<>();
            orderRepo = new InMemoryRepository<>();
            prodsRepo = new InMemoryRepository<>();
            mainsRepo = new InMemoryRepository<>();
            sidesRepo = new InMemoryRepository<>();
            drinkRepo = new InMemoryRepository<>();
            dessertRepo = new InMemoryRepository<>();
            locationRepo = new InMemoryRepository<>();
            offerRepo = new InMemoryRepository<>();
        } else if (option == 2) {
            userRepo = new UserFileRepository("users.txt");
            clientRepo = new ClientFileRepository("clients.txt");
            managerRepo = new ManagerFileRepository("managers.txt");
            employeeRepo = new EmployeeFileRepository("employees.txt");
            prodsRepo = new ProductFileRepository("prods.txt");
            locationRepo = new LocationFileRepository("locations.txt");
            orderRepo = new OrderFileRepository("orders.txt", (UserFileRepository) userRepo, (ProductFileRepository) prodsRepo, (LocationFileRepository) locationRepo);
            mainsRepo = new MainsFileRepository("mains.txt");
            sidesRepo = new SidesFileRepository("sides.txt");
            drinkRepo = new DrinkFileRepository("drinks.txt");
            dessertRepo = new DessertFileRepository("desserts.txt");
            offerRepo = new OfferFileRepository("offers.txt", (ProductFileRepository) prodsRepo);
        } else if (option == 3) {
            String dbUrl = "jdbc:sqlserver://localhost:1433;databaseName=McDonalds;encrypt=false";
//            String dbUrl = "jdbc:sqlserver://192.168.4.213:1433;databaseName=McDonalds;encrypt=false";
            String dbUser = "sa";
            String dbPassword = "m@pMcDonalds1";
            managerRepo = new ManagersDBRepository(dbUrl, dbUser, dbPassword);
            clientRepo = new ClientsDBRepository(dbUrl, dbUser, dbPassword);
            employeeRepo = new EmployeesDBRepository(dbUrl, dbUser, dbPassword);
            dessertRepo = new DessertsDBRepository(dbUrl, dbUser, dbPassword);
            drinkRepo = new DrinksDBRepository(dbUrl, dbUser, dbPassword);
            mainsRepo = new MainDishDBRepository(dbUrl, dbUser, dbPassword);
            sidesRepo = new SideDishesDBRepository(dbUrl, dbUser, dbPassword);
            prodsRepo = new ConcreteProductsDBRepository(dbUrl, dbUser, dbPassword);
            orderRepo = new OrderDBRepository(dbUrl, dbUser, dbPassword);
            offerRepo = new OffersDBRepository(dbUrl, dbUser, dbPassword);
            userRepo = new InMemoryRepository<>();
//            userRepo = new UserDBRepository(dbUrl, dbUser, dbPassword);
            locationRepo = new LocationDBRepository(dbUrl, dbUser, dbPassword);
        }else {
            System.out.println("Invalid option selected.");
            return;
        }
        UserService userService = new UserService(userRepo, clientRepo, managerRepo, employeeRepo);
        UserController userController = new UserController(userService);

        OrderService orderService = new OrderService(orderRepo, locationRepo);
        OrderController orderController = new OrderController(orderService);

        ProductService productService = new ProductService(prodsRepo, mainsRepo, sidesRepo, dessertRepo, drinkRepo);
        ProductController productController = new ProductController(productService);

        OfferService offerService = new OfferService(offerRepo);
        OfferController offerController = new OfferController(offerService);

//        productController.createMainDish("Hamburger", 12, 1307, DishSize.MEDIUM);
//        productController.createMainDish("Cheeseburger", 13, 1350, DishSize.MEDIUM);
//        productController.createMainDish("Big Mac", 15, 1500, DishSize.LARGE);
//
//        productController.createSideDish("French Fries", 5, DishSize.MEDIUM);
//        productController.createSideDish("Chicken McNuggets", 8, DishSize.MEDIUM);
//
//        productController.createDrink("Sprite", 3, DrinkVolume._300ML);
//        productController.createDrink("Lipton", 3, DrinkVolume._200ML);
//        productController.createDessert("Pie", 6, Allergens.gluten);
//        productController.createDessert("Ice cream", 5, Allergens.dairy);
//        List<Product> offerList = new ArrayList<>();
//        offerList.add(productController.getProduct("Cheeseburger"));
//        offerController.add(3, offerList);
//        userController.signUpManager("klara.orban@yahoo.com", "Orban Klara", "1234", ManagerRank.Senior);
//        userController.signUpClient("chira.carla@gmail.com", "Chira Carla", "5678");
//        userController.signIn("chira.carla@gmail.com", "5678").addOffer(offerRepo.getAll().get(0));
//        orderController.createLocation(Locations.Bucuresti, userService.readManager(1));

        scanner = new Scanner(System.in);
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
                    handleSignIn(scanner, userController, orderController, productController, offerController);
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
                userController.signUpClient(email, name, password);
                break;
            case 2:
                System.out.print("Enter manager ID: ");
                int manID = scanner.nextInt();
                Manager employeeManager = userController.readManager(manID);
                userController.signUpEmployee(email, name, password, employeeManager);
                break;
            case 3:
                System.out.println("Enter rank (Senior, Junior):");
                String managerRank = scanner.nextLine();
                userController.signUpManager(email, name, password, ManagerRank.valueOf(managerRank));
                break;
            default:
                System.out.println("Invalid user type.");
        }
        System.out.println("Sign-up successful!");
    }

    private static void handleSignIn(Scanner scanner, UserController userController, OrderController orderController, ProductController productController, OfferController offerController) {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userController.signIn(email, password);
        if (user != null) {
            showUserMenu(user, orderController, productController, userController, offerController);
        } else {
            System.out.println("Invalid email or password.");
        }

//        userController.signIn(email, password).ifPresentOrElse(
//                        user -> showUserMenu(user, orderController, productController, userController, offerController),
//                        () -> System.out.println("Invalid email or password."));
    }

    private static void showUserMenu(User user, OrderController orderController, ProductController productController, UserController userController, OfferController offerController) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nWelcome, " + user.getClass().getSimpleName() + ": " + user.getName());
            user.displayOptions();
            System.out.print("Select an option (or 0 to logout): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

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
                        for(Offer o : user.getOffers()) {
                            System.out.println(o.toString());
                        }
                        System.out.println("Enter offer ID:");
                        int offerId = scanner.nextInt();
                        offer = Optional.ofNullable(user.getOffers().get(offerId));
                    }
                    System.out.println("Pay with points? (true/false):");
                    boolean payWithPoints = scanner.nextBoolean();
                    orderController.createOrder((Client)user, location, productList, offer, payWithPoints);
                    break;
                case 2:
                    System.out.println("1. View all offers");
                    System.out.println("2. View offers that contain one specific product");
                    int opt = scanner.nextInt();
                    if(opt == 1) {
                        for(Offer o : user.getOffers()) {
                            System.out.println(o.toString());
                        } //TODO: implementare la tostring
                    }
                    else if(opt == 2) {
                        System.out.println("Enter product: ");
                        scanner.nextLine();
                        String productName = scanner.nextLine();
                        for(Offer o : offerController.filterOffersByProduct(user.getOffers(), productController.getProduct(productName))) {
                            System.out.println(o.toString());
                        }
                    }
                    break;
                case 3:
                    userController.deleteAccount(user.getEmail(), user.getPassword());
                    break;
                case 4:
                    System.out.println("1. View entire menu:"); //va tb sa il facem sortat dupa fel maybe
                    System.out.println("2. View menu sorted by price");
                    System.out.println("3. View menu items that dont contain an allergen");
                    System.out.print("Choice: ");
                    int type = scanner.nextInt();
                    scanner.nextLine();
                    switch (type) {
                        case 1:
                            for(Product p : productController.getAllProducts()) {
                                System.out.println(p.toString());
                            }
                            break;
                        case 2:
                            for(Product p : productController.sortProductsByPrice()) {
                                System.out.println(p.toString());
                            }
                            break;
                        case 3:
                            System.out.println("Enter allergen: ");
                            String allergen = scanner.nextLine();
                            for(Product p : productController.filterProductsByAllergen(Allergens.valueOf(allergen))) {
                                System.out.println(p.toString());
                            }
                            break;
                    }
                case 5:
                    if (user instanceof Manager) {
                        System.out.println("Select Product type to add:");
                        System.out.println("1. Main Dish");
                        System.out.println("2. Side Dish");
                        System.out.println("3. Drink");
                        System.out.println("4. Dessert");
                        System.out.print("Choice: ");
                        int foodType = scanner.nextInt();
                        scanner.nextLine();
                        switch (foodType){
                            case 1:
                                System.out.println("Enter main dish name:");
                                String mainDishName = scanner.nextLine();
                                System.out.println("Enter price:");
                                int mainDishPrice = scanner.nextInt();
                                System.out.println("Enter size (SMALL, MEDIUM, LARGE):");
                                String mainDishSize = scanner.next();
                                System.out.println("Enter stock:");
                                int mainDishStock = scanner.nextInt();
                                System.out.println("Enter calories:");
                                int mainDishCalories = scanner.nextInt();
                                productController.createMainDish(mainDishName, mainDishPrice, mainDishCalories, DishSize.valueOf(mainDishSize));
                                break;
                            case 2:
                                System.out.println("Enter side dish name:");
                                String sideDishName = scanner.nextLine();
                                System.out.println("Enter price:");
                                int sideDishPrice = scanner.nextInt();
                                System.out.println("Enter size (SMALL, MEDIUM, LARGE):");
                                String sideDishSize = scanner.next();
                                productController.createSideDish(sideDishName, sideDishPrice, DishSize.valueOf(sideDishSize));
                                break;
                            case 3:
                                System.out.println("Enter drink name:");
                                String drinkName = scanner.nextLine();
                                System.out.println("Enter price:");
                                int drinkPrice = scanner.nextInt();
                                System.out.println("Enter volume (ML_200, ML_300, ML_500):");
                                String drinkVolume = scanner.next();
                                productController.createDrink(drinkName, drinkPrice, DrinkVolume.valueOf(drinkVolume));
                                break;
                            case 4:
                                System.out.println("Enter drink name:");
                                String dessertName = scanner.nextLine();
                                System.out.println("Enter price:");
                                int dessertPrice = scanner.nextInt();
                                System.out.println("Enter allergens (nuts, egg, meat, fish, gluten, dairy, soy):");
                                String dessertAllergen = scanner.next();
                                productController.createDessert(dessertName, dessertPrice, Allergens.valueOf(dessertAllergen));
                                break;
                            default:
                                System.out.println("Invalid option!");

                        }
                    } else {
                        System.out.println("Invalid option!");
                    }
                    break;
                case 6:
                    if (user instanceof Manager) {
                        List<Product> offerList = new ArrayList<>();
                        System.out.println("Enter product names (comma separated):");
                        scanner.nextLine();
                        String[] offerProductNames = scanner.nextLine().split(",");
                        for (String productName : offerProductNames) {
                            Product product = productController.getProduct(productName.trim());
                            if (product != null) {
                                offerList.add(product);
                            }
                        }
                        System.out.println("Enter new price:");
                        int newPrice = scanner.nextInt();
                        offerController.add(newPrice, offerList);
                        //TODO assign offer to user
                    }
                    else {
                        System.out.println("Invalid option!");}
                    break;
                case 7:
                    if (user instanceof Manager) {
                        System.out.println("View: ");
                        System.out.println("1. Clients");
                        System.out.println("2. Most active client");
                        System.out.println("3. Employees");
                        System.out.println("4. Managers");
                        System.out.print("Choice: ");
                        int userType = scanner.nextInt();
                        scanner.nextLine();
                        switch (userType){
                            case 1:
                                userController.showAllClients();
                            case 2:
                                System.out.println(orderController.getMostActiveClientByLocation(orderController.getLocations().stream()
                                        .filter(l -> l.getStoreManager().getEmail().equals(user.getEmail()))
                                        .findFirst()
                                        .orElse(null)));
                            case 3:
                                userController.employeeSortByName();
                            case 4:
                                userController.showAllManagers();
//                            default:
//                                System.out.println("Invalid option!");
                        }
                    } else {
                        System.out.println("Invalid option!");
                    }
                    break;
                case 0 :
                    running = false;
                default :
                    System.out.println("Invalid option!");
            }
        }
    }
}
//        IRepository<User> userRepo = new CompositeRepository<>(new InMemoryRepository<>(), new FileRepository<>("src\\Files\\users.txt"));
//        IRepository<Client> clientRepo = new CompositeRepository<>(new InMemoryRepository<>(), new FileRepository<>("src\\Files\\clients.txt"));
//        IRepository<Manager> managerRepo = new CompositeRepository<>(new InMemoryRepository<>(), new FileRepository<>("src\\Files\\managers.txt"));
//        IRepository<Employee> employeeRepo = new CompositeRepository<>(new InMemoryRepository<>(), new FileRepository<>("src\\Files\\employees.txt"));
//        IRepository<Order> orderRepo = new CompositeRepository<>(new InMemoryRepository<>(), new FileRepository<>("src\\Files\\orders.txt"));
//        IRepository<Product> productRepo = new CompositeRepository<>(new InMemoryRepository<>(), new FileRepository<>("src\\Files\\products.txt"));
//        IRepository<MainDish> mainDRepo = new CompositeRepository<>(new InMemoryRepository<>(), new FileRepository<>("src\\Files\\mainDishes.txt"));
//        IRepository<SideDish> sideDRepo = new CompositeRepository<>(new InMemoryRepository<>(), new FileRepository<>("src\\Files\\sideDishes.txt"));
//        IRepository<Drinks> drinkRepo = new CompositeRepository<>(new InMemoryRepository<>(), new FileRepository<>("src\\Files\\drinks.txt"));
//        IRepository<Desserts> dessertRepo = new CompositeRepository<>(new InMemoryRepository<>(), new FileRepository<>("src\\Files\\desserts.txt"));
//        IRepository<Location> locationRepo = new CompositeRepository<>(new InMemoryRepository<>(), new FileRepository<>("src\\Files\\locations.txt"));
//        IRepository<Offer> offerRepo = new CompositeRepository<>(new InMemoryRepository<>(), new FileRepository<>("src\\Files\\offers.txt"));

