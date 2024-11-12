import Controller.OfferController;
import Controller.OrderController;
import Controller.ProductController;
import Controller.UserController;
import Enums.DishSize;
import Enums.Locations;
import Model.*;
import Repository.IRepository;
import Repository.InMemoryRepository;
import Service.OfferService;
import Service.OrderService;
import Service.ProductService;
import Service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
        IRepository<Offer> offerRepo = new InMemoryRepository<>();
        UserService userService = new UserService(userRepo, clientRepo, managerRepo, employeeRepo);
        UserController userController = new UserController(userService); //mai simplu: apelat mai putine clase

        OrderService orderService = new OrderService(orderRepo, locationRepo);
        OrderController orderController = new OrderController(orderService);

        ProductService productService = new ProductService(productRepo, mainDRepo, sideDRepo, dessertRepo, drinkRepo);
        ProductController productController = new ProductController(productService);

        OfferService offerService = new OfferService(offerRepo);
        OfferController offerController = new OfferController(offerService);
        userController.sign_Up_Manager("klara.orban@yahoo.com", "Orban Klara", "1234", "Top manager" );
        userController.sign_Up_Client("chira.carla@gmail.com", "Chira Carla", "5678");

        userController.sign_In("klara.orban@yahoo.com", "1234");
        userController.sign_Up_Client("example.name@yahoo.com", "Example Client", "1234");
        userController.showAllManagers();

        Location loc1 = orderController.createLocation(Locations.Bucuresti, userService.getAllManagers().get(0));

        productController.createMainDish("Hamburger", 12, 1307, DishSize.MEDIUM, 1);
        productController.createMainDish("Cheeseburger", 13, 1350, DishSize.MEDIUM, 2);
        List<Product> productList = new ArrayList<>();
        productList.add(productController.getProduct("Hamburger"));
        productList.add(productController.getProduct("Cheeseburger"));
//        productRepo.getAll().forEach(System.out::println);
        List<Product> offerList = new ArrayList<>();
        offerList.add(productController.getProduct("Cheeseburger"));
        offerController.add(3, offerList);
        System.out.println(offerService.getOffer(0));
        userService.getAllClients().get(0).addOffer(offerService.getOffer(0));
        orderController.createOrder(userService.getAllClients().get(0), loc1, productList, Optional.of(offerService.getOffer(0)), false);
    }
}