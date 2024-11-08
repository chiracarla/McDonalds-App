import Controller.OrderController;
import Controller.UserController;
import Model.*;
import Repository.IRepository;
import Repository.InMemoryRepository;
import Service.OrderService;
import Service.UserService;

public class Main {
    public static void main(String[] args) {
        IRepository<User> userRepo = new InMemoryRepository<>();
        IRepository<Client> clientRepo = new InMemoryRepository<>();
        IRepository<Manager> managerRepo = new InMemoryRepository<>();
        IRepository<Employee> employeeRepo = new InMemoryRepository<>();
        IRepository<Order> orderRepo = new InMemoryRepository<>();
        IRepository<MainDish> mainDRepo = new InMemoryRepository<>();
        IRepository<SideDish> sideDRepo = new InMemoryRepository<>();
        IRepository<Drinks> drinkRepo = new InMemoryRepository<>();
        IRepository<Desserts> dessertRepo = new InMemoryRepository<>();
        UserService userService = new UserService(userRepo, clientRepo, managerRepo, employeeRepo);
        UserController userController = new UserController(userService);

        OrderService orderService = new OrderService(orderRepo, mainDRepo, sideDRepo, dessertRepo, drinkRepo);
        OrderController orderController = new OrderController(orderService);

        userController.sign_Up_Manager("klara.orban@yahoo.com", "Orban Klara", "1234", "Top manager" );
//        controller.sign_Up_Client("chira.carla@gmail.com", "Chira Carla", "5678");
//
//        controller.sign_In("klara.orban@yahoo.com", "1234");

        userController.showAllManagers();
    }
}