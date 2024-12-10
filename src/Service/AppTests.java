package Service;

import Controller.OfferController;
import Controller.OrderController;
import Controller.ProductController;
import Controller.UserController;
import Enums.*;
import Enums.ManagerRank;
import Model.*;
import Repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class AppTests {

    private UserService userService;
    private UserController userController;
    private IRepository<Client> clientRepo;
    private IRepository<Manager> managerRepo;
    private IRepository<User> userRepo;
    private IRepository<Employee> employeeRepo;
    private IRepository<Product> productRepo;
    private IRepository<MainDish> mainDishRepo;
    private IRepository<SideDish> sideDishRepo;
    private IRepository<Desserts> dessertRepo;
    private IRepository<Drinks> drinkRepo;
    private IRepository<Offer> offerRepo;
    private IRepository<Order> orderRepo;
    private IRepository<Location> locationRepo;

    private OfferService offerService;
    private OfferController offerController;
    private OrderService orderService;
    private OrderController orderController;
    private ProductService productService;
    private ProductController productController;

    @BeforeEach
    void setUp() {
        userRepo = new InMemoryRepository<>();
        clientRepo = new InMemoryRepository<>();
        managerRepo = new InMemoryRepository<>();
        employeeRepo = new InMemoryRepository<>();
        orderRepo = new InMemoryRepository<>();
        productRepo = new InMemoryRepository<>();
        mainDishRepo = new InMemoryRepository<>();
        sideDishRepo = new InMemoryRepository<>();
        drinkRepo = new InMemoryRepository<>();
        dessertRepo = new InMemoryRepository<>();
        locationRepo = new InMemoryRepository<>();
        offerRepo = new InMemoryRepository<>();

        userService = new UserService(userRepo, clientRepo, managerRepo, employeeRepo);
        userController = new UserController(userService);

        productService = new ProductService(productRepo, mainDishRepo, sideDishRepo, dessertRepo, drinkRepo);
        productController = new ProductController(productService);

        orderService = new OrderService(orderRepo, locationRepo);
        orderController = new OrderController(orderService);

        offerService = new OfferService(offerRepo);
        OfferController offerController = new OfferController(offerService);
    }

    @Test
    void crudOperationsUsers() {
        // Create
        User newClient = new Client("jack.wolf@example.com", "Jack Wolf", 15, "password15");
        userRepo.create(newClient);
        clientRepo.create((Client) newClient);
        User client = clientRepo.read(15);
        assertNotNull(client);
        assertEquals("Jack Wolf", client.getName());

        // Read
        User readClient = userRepo.read(15);
        assertNotNull(readClient);
        assertEquals("Jack Wolf", readClient.getName());

        // Update
        client.setName("Updated Jack Wolf");
        userRepo.update(client);
        User updatedClient = userRepo.read(15);
        assertNotNull(updatedClient);
        assertEquals("Updated Jack Wolf", updatedClient.getName());

        // Delete
        userRepo.delete(15);
        User deletedClient = userRepo.read(15);
        assertNull(deletedClient);
    }

    @Test
    void crudOperationsProducts() {
        // Main Dish CRUD
        MainDish newMainDish = new MainDish( "Hamburger", 12, 1307,DishSize.MEDIUM, 11);
        mainDishRepo.create(newMainDish);
        MainDish mainDish = mainDishRepo.read(11);
        assertNotNull(mainDish);
        assertEquals("Hamburger", mainDish.getProductName());

        mainDish.setProductName("Updated Hamburger");
        mainDishRepo.update(mainDish);
        MainDish updatedMainDish = mainDishRepo.read(11);
        assertNotNull(updatedMainDish);
        assertEquals("Updated Hamburger", updatedMainDish.getProductName());

        mainDishRepo.delete(11);
        MainDish deletedMainDish = mainDishRepo.read(11);
        assertNull(deletedMainDish);

        // Side Dish CRUD
        SideDish newSideDish = new SideDish( "French Fries", 5, DishSize.MEDIUM, 20);
        sideDishRepo.create(newSideDish);
        SideDish sideDish = sideDishRepo.read(20);
        assertNotNull(sideDish);
        assertEquals("French Fries", sideDish.getProductName());

        sideDish.setProductName("Updated French Fries");
        sideDishRepo.update(sideDish);
        SideDish updatedSideDish = sideDishRepo.read(20);
        assertNotNull(updatedSideDish);
        assertEquals("Updated French Fries", updatedSideDish.getProductName());

        sideDishRepo.delete(20);
        SideDish deletedSideDish = sideDishRepo.read(20);
        assertNull(deletedSideDish);

        // Drink CRUD
        Drinks newDrink = new Drinks( "Sprite", 7, DrinkVolume._300ML, 30);
        drinkRepo.create(newDrink);
        Drinks drink = drinkRepo.read(30);
        assertNotNull(drink);
        assertEquals("Sprite", drink.getProductName());

        drink.setProductName("Updated Sprite");
        drinkRepo.update(drink);
        Drinks updatedDrink = drinkRepo.read(30);
        assertNotNull(updatedDrink);
        assertEquals("Updated Sprite", updatedDrink.getProductName());

        drinkRepo.delete(30);
        Drinks deletedDrink = drinkRepo.read(30);
        assertNull(deletedDrink);

        // Dessert CRUD
        Desserts newDessert = new Desserts( "Pie", 10, Allergens.gluten, 13);
        dessertRepo.create(newDessert);
        Desserts dessert = dessertRepo.read(13);
        assertNotNull(dessert);
        assertEquals("Pie", dessert.getProductName());

        dessert.setProductName("Updated Pie");
        dessertRepo.update(dessert);
        Desserts updatedDessert = dessertRepo.read(13);
        assertNotNull(updatedDessert);
        assertEquals("Updated Pie", updatedDessert.getProductName());

        dessertRepo.delete(13);
        Desserts deletedDessert = dessertRepo.read(13);
        assertNull(deletedDessert);
    }

    @Test
    void crudOperationsOrder() {
        // Create products
        MainDish hamburger = new MainDish("Hamburger", 12, 1307, DishSize.MEDIUM, 14);
        mainDishRepo.create(hamburger);
        assertNotNull(mainDishRepo.read(14), "Hamburger was not created");

        SideDish frenchFries = new SideDish("French Fries", 5, DishSize.MEDIUM, 21);
        sideDishRepo.create(frenchFries);
        assertNotNull(sideDishRepo.read(21), "French Fries were not created");

        Drinks sprite = new Drinks("Sprite", 3, DrinkVolume._300ML, 34);
        drinkRepo.create(sprite);
        assertNotNull(drinkRepo.read(34), "Sprite was not created");

        // Create user and manager
        User newClient = new Client("jack.wolf@example.com", "Jack Wolf", 15, "password15");
        userRepo.create(newClient);
        assertNotNull(userRepo.read(15), "Client was not created");

        Manager manager = new Manager("klara.orban@yahoo.com", "Orban Klara", 14, "1234", ManagerRank.Senior);
        managerRepo.create(manager);
        assertNotNull(managerRepo.read(14), "Manager was not created");

        // Create location
        Location newLocation = new Location(Locations.Brasov, manager, 11);
        locationRepo.create(newLocation);
        assertNotNull(locationRepo.read(11), "Location was not created");

        // Create order with 3 elements
        Order newOrder = new Order(List.of(hamburger, frenchFries, sprite), newLocation, newClient, 13);
        orderRepo.create(newOrder);
        Order order = orderRepo.read(13);
        assertNotNull(order, "Order was not created");
        assertEquals(3, order.getProducts().size());

        // Read
        Order readOrder = orderRepo.read(13);
        assertNotNull(readOrder, "Order was not read");
        assertEquals(3, readOrder.getProducts().size());

        // Update
        order.setOrderID(14);
        orderRepo.update(order);
        Order updatedOrder = orderRepo.read(13);
        assertNotNull(updatedOrder, "Order was not updated");
        assertEquals(14, updatedOrder.getOrderID());

        // Delete
        orderRepo.delete(14);
        Order deletedOrder = orderRepo.read(14);
        assertNull(deletedOrder, "Order was not deleted");

        mainDishRepo.delete(14);
        sideDishRepo.delete(21);
        drinkRepo.delete(34);
        userRepo.delete(15);
        managerRepo.delete(14);
        locationRepo.delete(11);
    }

    @Test
    void filterFunctionOffer() {
        // Initialize the list of IDs
        List<Integer> ids = List.of(1, 2, 3);

        // Create some products
        Product product1 = new MainDish("Product 1", 15, 1300, DishSize.MEDIUM, 1);
        Product product2 = new SideDish("Product 2", 16, DishSize.MEDIUM,2);
        Product product3 = new SideDish("Product 3", 17, DishSize.LARGE,3);

        // Create some offers and add them to the repository
        Offer offer1 = new Offer(45, 35, List.of(product1), 1);
        Offer offer2 = new Offer(50, 43, List.of(product2), 2);
        Offer offer3 = new Offer(27, 22, List.of(product3), 3);
        offerRepo.create(offer1);
        offerRepo.create(offer2);
        offerRepo.create(offer3);

        // Filter offers by IDs
        List<Offer> offers = offerService.getAllOffers().stream()
                .filter(offer -> ids.contains(offer.getOfferId()))
                .collect(Collectors.toList());

        // Assert that the filtered offers match the expected IDs
        assertEquals(3, offers.size());
        assertTrue(offers.stream().allMatch(offer -> ids.contains(offer.getOfferId())));
    }

    @Test
    void filterProductsByAllergens() {
        // Create some products with different allergens
        Product product1 = new Desserts("Cake", 10, Allergens.gluten, 1);
        Product product2 = new MainDish("Salad", 8, 200, DishSize.SMALL, 2);
        Product product3 = new SideDish("Fries", 5, DishSize.MEDIUM, 3);
        Product product4 = new Desserts("Ice Cream", 7, Allergens.dairy, 4);

        // Add products to the repository
        productRepo.create(product1);
        productRepo.create(product2);
        productRepo.create(product3);
        productRepo.create(product4);

        // Filter products by gluten allergen
        List<Product> filteredProducts = productService.filterProductsByAllergens(Allergens.dairy);

        // Assert that the filtered products do not contain products with gluten allergen
        assertEquals(3, filteredProducts.size());
        assertTrue(filteredProducts.stream().noneMatch(product -> Allergens.dairy.equals(product.getAllergens())));
    }

    @Test
    void employeesSortByName() {
        // Create some employees with different names
        Employee employee1 = new Employee("john.doe@example.com", "John Doe", 1, "password1", new Manager("manager1@example.com", "Manager One", 10, "password10", ManagerRank.Senior));
        Employee employee2 = new Employee("jane.smith@example.com", "Jane Smith", 2, "password2", new Manager("manager2@example.com", "Manager Two", 11, "password11", ManagerRank.Junior));
        Employee employee3 = new Employee("alice.jones@example.com", "Alice Jones", 3, "password3", new Manager("manager3@example.com", "Manager Three", 12, "password12", ManagerRank.Junior));

        // Add employees to the repository
        employeeRepo.create(employee1);
        employeeRepo.create(employee2);
        employeeRepo.create(employee3);

        // Sort employees by name
        List<Employee> sortedEmployees = userService.employeesSortByName();

        // Assert that the employees are sorted by name
        assertEquals(3, sortedEmployees.size());
        assertEquals("Alice Jones", sortedEmployees.get(0).getName());
        assertEquals("Jane Smith", sortedEmployees.get(1).getName());
        assertEquals("John Doe", sortedEmployees.get(2).getName());
    }

    @Test
    void sortProductsByPrice() {
        // Create some products with different prices
        Product product1 = new MainDish("Burger", 10, 500, DishSize.MEDIUM, 1);
        Product product2 = new SideDish("Fries", 5, DishSize.SMALL, 2);
        Product product3 = new Drinks("Soda", 3, DrinkVolume._300ML, 3);
        Product product4 = new Desserts("Cake", 7, Allergens.gluten, 4);

        // Add products to the repository
        productRepo.create(product1);
        productRepo.create(product2);
        productRepo.create(product3);
        productRepo.create(product4);

        // Sort products by price
        List<Product> sortedProducts = productService.sortProductsByPrice();

        // Assert that the products are sorted by price
        assertEquals(4, sortedProducts.size());
        assertEquals("Soda", sortedProducts.get(0).getProductName());
        assertEquals("Fries", sortedProducts.get(1).getProductName());
        assertEquals("Cake", sortedProducts.get(2).getProductName());
        assertEquals("Burger", sortedProducts.get(3).getProductName());
    }
}