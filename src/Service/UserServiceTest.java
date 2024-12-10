package Service;

import Controller.UserController;
import Enums.ManagerRank;
import Model.*;
import Repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;
    private UserController userController;
    private IRepository<Client> clientRepo;
    private IRepository<Manager> managerRepo;
    private IRepository<User> userRepo;
    private IRepository<Employee> employeeRepo;
    @BeforeEach
    void setUp() {
        clientRepo = new ClientFileRepository("clients.txt");
        managerRepo = new ManagerFileRepository("managers.txt");
        userRepo = new UserFileRepository("users.txt");
        employeeRepo = new EmployeeFileRepository("employee.txt");

        userService = new UserService(userRepo, clientRepo, managerRepo, employeeRepo);
        userController = new UserController(userService);
    }

    @Test
    void signUpClient() {
        userController.signUpClient("client122@example.com", "Client Name", "password");
        User client = clientRepo.read(11);
        assertNotNull(client);
        assertEquals("Client Name", client.getName());
    }

    @Test
    void signUpManager() {
        userController.signUpManager("manager@example.com", "Manager Name", "password", ManagerRank.Senior);
        User manager = managerRepo.read(1);
        assertNotNull(manager);
        assertEquals("Manager Name", manager.getName());
    }

    @Test
    void signUpEmployee() {
        userController.signUpEmployee("employee@example.com", "Employee Name", "password", null);
        User employee = employeeRepo.read(1);
        assertNotNull(employee);
        assertEquals("Employee Name", employee.getName());
    }

    @Test
    void signIn() {
        userController.signUpClient("client@example.com", "Client Name", "password");
        User client = userController.signIn("client@example.com", "password");
        assertNotNull(client);
        assertEquals("Client Name", client.getName());
    }

    @Test
    void deleteAccount() {
        userController.signUpClient("client@example.com", "Client Name", "password");
        userController.deleteAccount("client@example.com", "password");
        User client = clientRepo.read(1);
        assertNull(client);
    }

    @Test
    void getAllClients() {
        userController.signUpClient("client1@example.com", "Client One", "password");
        userController.signUpClient("client2@example.com", "Client Two", "password");
        List<Client> clients = userService.getAllClients();
        assertEquals(2, clients.size());
    }

    @Test
    void getAllManagers() {
        userController.signUpManager("manager1@example.com", "Manager One", "password", ManagerRank.Senior);
        userController.signUpManager("manager2@example.com", "Manager Two", "password", ManagerRank.Junior);
        List<Manager> managers = userService.getAllManagers();
        assertEquals(2, managers.size());
    }

    @Test
    void getAllEmployees() {
        userController.signUpEmployee("employee1@example.com", "Employee One", "password", null);
        userController.signUpEmployee("employee2@example.com", "Employee Two", "password", null);
        List<Employee> employees = userService.getAllEmployees();
        assertEquals(2, employees.size());
    }

    @Test
    void readManager() {
        userController.signUpManager("manager@example.com", "Manager Name", "password", ManagerRank.Senior);
        Manager manager = userService.readManager(1);
        assertNotNull(manager);
        assertEquals("Manager Name", manager.getName());
    }

    @Test
    void employeesSortByName() {
        userController.signUpEmployee("employee2@example.com", "Employee Two", "password", null);
        userController.signUpEmployee("employee1@example.com", "Employee One", "password", null);
        List<Employee> employees = userService.employeesSortByName();
        assertEquals("Employee One", employees.get(0).getName());
        assertEquals("Employee Two", employees.get(1).getName());
    }
}