import Controller.OrderController;
import Controller.ProductController;
import Controller.UserController;
import Enums.DishSize;
import Enums.Locations;
import Model.*;
import Repository.IRepository;
import Repository.InMemoryRepository;
import Service.OrderService;
import Service.ProductService;
import Service.UserService;

import java.util.ArrayList;
import java.util.List;

public class Main {
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
        UserService userService = new UserService(userRepo, clientRepo, managerRepo, employeeRepo);
        UserController userController = new UserController(userService); //mai simplu: apelat mai putine clase

        OrderService orderService = new OrderService(orderRepo, locationRepo);
        OrderController orderController = new OrderController(orderService);

        ProductService productService = new ProductService(productRepo, mainDRepo, sideDRepo, dessertRepo, drinkRepo);
        ProductController productController = new ProductController(productService);
        Manager manager1 = userController.sign_Up_Manager("klara.orban@yahoo.com", "Orban Klara", "1234", "Top manager" );
        userController.sign_Up_Client("chira.carla@gmail.com", "Chira Carla", "5678");

        userController.sign_In("klara.orban@yahoo.com", "1234");
        Client client1 = userController.sign_Up_Client("example.name@yahoo.com", "Example Client", "1234");
        userController.showAllManagers();

        Location loc1 = orderController.createLocation(Locations.Bucuresti, manager1);

        List<Product> productList = new ArrayList<>();
        productController.createMainDish("Hamburger", 12, 1307, DishSize.MEDIUM);
        productList.add(productController.getProduct("Hamburger"));
        orderController.createOrder(client1, loc1, productList);
    }
}