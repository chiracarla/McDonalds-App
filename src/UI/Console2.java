package UI;
import Controller.OfferController;
import Controller.OrderController;
import Controller.ProductController;
import Controller.UserController;
import Enums.*;
import Model.*;
import Repository.*;
import Repository.DbRepository.*;
import Repository.FRepository.*;
import Service.OfferService;
import Service.OrderService;
import Service.ProductService;
import Service.UserService;

import java.util.*;

import Exceptions.*;

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
            userRepo = new UserFileRepository("src/Files/users.txt");
            clientRepo = new ClientFileRepository("src/Files/clients.txt");
            managerRepo = new ManagerFileRepository("src/Files/managers.txt");
            employeeRepo = new EmployeeFileRepository("src/Files/employees.txt");
            prodsRepo = new ProductFileRepository("src/Files/prods.txt");
            locationRepo = new LocationFileRepository("src/Files/locations.txt");
            orderRepo = new OrderFileRepository("src/Files/orders.txt", (UserFileRepository) userRepo, (ProductFileRepository) prodsRepo, (LocationFileRepository) locationRepo);
            mainsRepo = new MainsFileRepository("src/Files/mains.txt");
            sidesRepo = new SidesFileRepository("src/Files/sides.txt");
            drinkRepo = new DrinkFileRepository("src/Files/drinks.txt");
            dessertRepo = new DessertFileRepository("src/Files/desserts.txt");
            offerRepo = new OfferFileRepository("src/Files/offers.txt", (ProductFileRepository) prodsRepo);
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
//            userRepo = new InMemoryRepository<>();
//            userRepo = new ConcreteUserDBRepository(dbUrl, dbUser, dbPassword);
            userRepo = new ConcreteUserDBRepository(dbUrl, dbUser, dbPassword);
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

        if( option == 1) {
            productController.createMainDish("Hamburger", 12, 1307, DishSize.MEDIUM);
            productController.createMainDish("Cheeseburger", 13, 1350, DishSize.MEDIUM);
            productController.createMainDish("Big Mac", 15, 1500, DishSize.LARGE);

            productController.createSideDish("French Fries", 5, DishSize.MEDIUM);
            productController.createSideDish("Chicken McNuggets", 8, DishSize.MEDIUM);

            productController.createDrink("Sprite", 3, DrinkVolume._300ML);
            productController.createDrink("Lipton", 3, DrinkVolume._200ML);
            productController.createDessert("Pie", 6, Allergens.gluten);
            productController.createDessert("Ice cream", 5, Allergens.dairy);
            List<Product> offerList = new ArrayList<>();
            offerList.add(productController.getProduct("Cheeseburger"));
            offerController.add(3, offerList);
            userController.signUpManager("klara.orban@yahoo.com", "Orban Klara", "1234", ManagerRank.Senior);
            userController.signUpClient("chira.carla@gmail.com", "Chira Carla", "5678");
            userController.signIn("chira.carla@gmail.com", "5678").addOffer(offerRepo.getAll().get(0));
            orderController.createLocation(Locations.Bucuresti, userService.readManager(1));
        }
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

        String name;
        try {
            System.out.print("Enter name: ");
            name = scanner.nextLine();
            name.trim();
            validateName(name);
        } catch (ValidationException e) {
            try {
                System.out.println(e.getMessage());
                System.out.print("Please re-enter name: ");
                name = scanner.nextLine();
                name.trim();
                validateName(name);
            } catch (ValidationException x) {
                return;
            }
        }
        String email;
        try {
            System.out.print("Enter email: ");
            email = scanner.nextLine();
            validateEmail(email);
        } catch (ValidationException e) {
            try {
                System.out.println(e.getMessage());
                System.out.print("Please re-enter email: ");
                email = scanner.nextLine();
                validateEmail(email);
            } catch (ValidationException x) {
                return;
            }
        }

        String password;
        try {
            System.out.print("Enter password: ");
            password = scanner.nextLine();
            validatePassword(password);
        } catch (ValidationException e) {
            try {
                System.out.println(e.getMessage());
                System.out.print("Please re-enter password: ");
                password = scanner.nextLine();
                validatePassword(password);
            } catch (ValidationException x) {
                return;
            }
        }

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
                String managerRank;
                try {
                    System.out.print("Enter rank (Senior, Junior): ");
                    managerRank = scanner.nextLine();
                    if(!Arrays.asList("Junior", "Senior").contains(managerRank)) {
                        throw new ValidationException("Invalid rank");
                    }
                } catch (ValidationException e) {
                    System.out.println(e.getMessage());
                    System.out.print("Please re-enter name: ");
                    managerRank = scanner.nextLine();
                    if(!Arrays.asList("Junior", "Senior").contains(managerRank)) {
                        throw new ValidationException("Invalid rank");
                    }
                }
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
                    System.out.println("Enter location (Bucuresti, Brasov, Sighisoara):");
                    String loc = scanner.nextLine();
                    Location location = orderController.getLocations().stream()
                            .filter(l -> l.getStoreLocation().equals(Locations.valueOf(loc)))
                            .findFirst()
                            .orElse(null);
                    List<Product> productList = new ArrayList<>();
                    System.out.println("Enter product names (comma separated) for menu simply add one Main Dish, one Side Dish and one Drink:");
                    String[] productNames = scanner.nextLine().split(",");
                    for (String productName : productNames) {
                        Product product = productController.getProduct(productName.trim());
                        if (product != null) {
                            productList.add(product);
                        }
                    }
                    System.out.println("Apply offer? (yes/no):");
                    String applyOffer;
                    try {
                        applyOffer = scanner.nextLine();
                        if(!Arrays.asList("yes", "no").contains(applyOffer)) {
                            throw new ValidationException("Invalid option");
                        }
                    } catch (ValidationException e) {
                        System.out.println(e.getMessage());
                        System.out.print("Please re-enter option(yes/no): ");
                        applyOffer = scanner.nextLine();
                        if(!Arrays.asList("yes", "no").contains(applyOffer)) {
                            return;
                        }
                    }
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
                        }
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
                                String mainDishSize;
                                try {
                                    mainDishSize = scanner.next();
                                    if(!Arrays.asList("SMALL", "MEDIUM", "LARGE").contains(mainDishSize)) {
                                        throw new ValidationException("Invalid size");
                                    }
                                } catch (ValidationException e) {
                                    System.out.println(e.getMessage());
                                    System.out.print("Please re-enter size: ");
                                    mainDishSize = scanner.nextLine();
                                    if(!Arrays.asList("SMALL", "MEDIUM", "LARGE").contains(mainDishSize)) {
                                        throw new ValidationException("Invalid size");
                                    }
                                }
//                                System.out.println("Enter stock:");
//                                int mainDishStock = scanner.nextInt();
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
                                String sideDishSize;
                                try {
                                    sideDishSize = scanner.next();
                                    if(!Arrays.asList("SMALL", "MEDIUM", "LARGE").contains(sideDishSize)) {
                                        throw new ValidationException("Invalid size");
                                    }
                                } catch (ValidationException e) {
                                    System.out.println(e.getMessage());
                                    System.out.print("Please re-enter size: ");
                                    sideDishSize = scanner.nextLine();
                                    if(!Arrays.asList("SMALL", "MEDIUM", "LARGE").contains(sideDishSize)) {
                                        throw new ValidationException("Invalid size");
                                    }
                                }
                                productController.createSideDish(sideDishName, sideDishPrice, DishSize.valueOf(sideDishSize));
                                break;
                            case 3:
                                System.out.println("Enter drink name:");
                                String drinkName = scanner.nextLine();
                                System.out.println("Enter price:");
                                int drinkPrice = scanner.nextInt();
                                System.out.println("Enter volume (ML_200, ML_300, ML_500):");
                                String drinkVolume;
                                try {
                                    drinkVolume = scanner.next();
                                    if(!Arrays.asList("ML_200", "ML_300", "ML_500").contains(drinkVolume)) {
                                        throw new ValidationException("Invalid volume");
                                    }
                                } catch (ValidationException e) {
                                    System.out.println(e.getMessage());
                                    System.out.print("Please re-enter volume: ");
                                    drinkVolume = scanner.nextLine();
                                    if(!Arrays.asList("ML_200", "ML_300", "ML_500").contains(drinkVolume)) {
                                        throw new ValidationException("Invalid volume");
                                    }
                                }
                                productController.createDrink(drinkName, drinkPrice, DrinkVolume.valueOf(drinkVolume));
                                break;
                            case 4:
                                System.out.println("Enter drink name:");
                                String dessertName = scanner.nextLine();
                                System.out.println("Enter price:");
                                int dessertPrice = scanner.nextInt();
                                System.out.println("Enter allergens (nuts, egg, meat, fish, gluten, dairy, soy):");
                                String dessertAllergen;
                                try {
                                    dessertAllergen = scanner.next();
                                    if(!Arrays.asList("nuts", "egg", "meat", "fish", "gluten", "dairy", "soy").contains(dessertAllergen)) {
                                        throw new ValidationException("Invalid allergen");
                                    }
                                } catch (ValidationException e) {
                                    System.out.println(e.getMessage());
                                    System.out.print("Please re-enter allergen: ");
                                    dessertAllergen = scanner.nextLine();
                                    if(!Arrays.asList("nuts", "egg", "meat", "fish", "gluten", "dairy", "soy").contains(dessertAllergen)) {
                                        throw new ValidationException("Invalid allergen");
                                    }
                                }
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
//                        scanner.nextLine();
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
                        System.out.println("Add offer to Clients:");
                        userController.showAllClients();
                        System.out.println("Enter client ids separated by comma:");
                        scanner.nextLine();
                        String[] clients = scanner.nextLine().split(",");
                        for (String clientID : clients) {
                            Client cl = userController.readClient(Integer.valueOf(clientID));
                            if (cl != null){
                                cl.addOffer(offerController.lastOffer());
                            }
                        }
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
    private static void validateName(String name) {
        String[] parts = name.trim().split("\\s+");
        if (parts.length < 2) {
            throw new ValidationException("Invalid name format (at least 2 names)");
        }
        for (String part : parts) {
            if (!part.matches("[A-Z].*")){
                throw new ValidationException("Invalid name format (names should start with upper case letters)");
            }
        }
    }

    private static void validateEmail(String email){
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        if(!email.matches(emailRegex)){
            throw new ValidationException("Invalid email format");
        }
    }

    private static void validatePassword(String password) {
        if(!password.matches("^(?=.*[A-Za-z])(?=.*\\d).+$")){
            throw new ValidationException("Invalid password format (should contain at least one number and one letter");
        }
    }
}