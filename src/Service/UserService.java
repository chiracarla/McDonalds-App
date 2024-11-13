package Service;

import Repository.*;
import Model.*;

import java.util.List;
import java.util.Optional;

/**
 *The {@code UserService} class provides various services related to user actions
 * in the application. This includes user authentication, retrieval, and management
 * of user information and roles.
 */
public class UserService {
    private final IRepository<User> userRepo;
    private final IRepository<Client> clientRepo;
    private final IRepository<Manager> managerRepo;
    private final IRepository<Employee> employeeRepo;

    /**
     * uses repositories to acces the information
     * @param userRepo
     * @param clientRepo
     * @param managerRepo
     * @param employeeRepo
     */
    public UserService(IRepository<User> userRepo, IRepository<Client> clientRepo, IRepository<Manager> managerRepo, IRepository<Employee> employeeRepo) {
        this.userRepo = userRepo;
        this.clientRepo = clientRepo;
        this.managerRepo = managerRepo;
        this.employeeRepo = employeeRepo;
    }

    /**
     * creates a client account using the following information
     * @param email
     * @param name
     * @param password
     */
    public void signUpClient(String email, String name, String password) {
        Optional<User> existingUser = userRepo.getAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();

        if (existingUser.isPresent()) {
            System.out.println("This email is already in use");
            return;
        }

        int newUserID = generateNewUserId();
        User newUser = new Client(email, name, newUserID, password);
        userRepo.create(newUser);
        clientRepo.create((Client) newUser);
        System.out.println("Client signed up successfully!");
    }

    /**
     * creates an employee account using the following information
     * @param email
     * @param name
     * @param password
     * @param rank
     */
    public void signUpManager(String email, String name, String password, String rank) {
        Optional<User> existingUser = userRepo.getAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();

        if (existingUser.isPresent()) {
            System.out.println("This email is already in use");
            return;
        }

        int newUserID = generateNewUserId();
        User newUser = new Manager(email, name, newUserID, password, rank);
        userRepo.create(newUser);
        managerRepo.create((Manager) newUser);
        System.out.println("Manager signed up successfully!");
    }

    /**
     * creates a manager account using the following information
     * @param email
     * @param name
     * @param password
     * @param manager
     */
    public void signUpEmployee(String email, String name, String password, Manager manager) {
        Optional<User> existingUser = userRepo.getAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();

        if (existingUser.isPresent()) {
            System.out.println("This email is already in use");
            return;
        }

        int newUserID = generateNewUserId();
        User newUser = new Employee(email, name, newUserID, password, manager);
        userRepo.create(newUser);
        employeeRepo.create((Employee) newUser);
        System.out.println("Client signed up successfully!");
    }

    /**
     *
     * @return
     */
    private int generateNewUserId() {
        return userRepo.getAll().stream()
                .mapToInt(User::getUserID)
                .max()
                .orElse(0) + 1;
        }

    /**
     * Signs in a user based on their email and password. If successful,
     * it identifies the user type (Client, Employee, Manager) and displays
     * a message indicating the type. If the email or password is incorrect,
     * an error message is displayed.
     * @param email
     * @param password
     */
    public void signIn(String email, String password) {
        Optional<User> existingUser = userRepo.getAll().stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password)) // Check for email and password
                .findFirst();

        if (existingUser.isPresent()) {
            User user = existingUser.get();

            if (user instanceof Client) {
                System.out.println("Successfully signed in as Client!");
            }
            else if (user instanceof Employee) {
                System.out.println("Successfully signed in as Employee!");
            }
            else if (user instanceof Manager) {
                System.out.println("Successfully signed in as Manager!");
            }
            else {
                System.out.println("User type is unknown!");
            }
        } else {
            System.out.println("User not found or incorrect password!");
        }
    }

    /**
     * deletes any account if the email and password match
     * @param email
     * @param password
     */
    public void deleteAccount(String email, String password) {
        Optional<User> existingUser = userRepo.getAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();

        if (existingUser.isPresent()) {
            userRepo.delete(existingUser.get().getUserID());
            System.out.println("Account successfully deleted!");
        }
        else {
            System.out.println("User not found or incorrect credentials!");
        }
    }

    /**
     * provides a list of all clients signed up
     * @return
     */
    public List<Client> getAllClients() {
        return clientRepo.getAll();
    }

    /**
     * provides a list of all managers signed up
     * @return
     */
    public List<Manager> getAllManagers(){
        return managerRepo.getAll();
    }

    /**
     * provides a list of all employees signed up
     * @return
     */
    public List<Employee> getAllEmployees() {
        return employeeRepo.getAll();
    }

}
//TODO:
// update account
// make order in-store/online
// complex function-- email sending API