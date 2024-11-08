package Service;

import Repository.*;
import Model.*;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final IRepository<User> userRepo;
    private final IRepository<Client> clientRepo;
    private final IRepository<Manager> managerRepo;
    private final IRepository<Employee> employeeRepo;

    public UserService(IRepository<User> userRepo, IRepository<Client> clientRepo, IRepository<Manager> managerRepo, IRepository<Employee> employeeRepo) {
        this.userRepo = userRepo;
        this.clientRepo = clientRepo;
        this.managerRepo = managerRepo;
        this.employeeRepo = employeeRepo;
    }

    public void sign_up_client(String email, String name, String password) {
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

    public void sign_up_manager(String email, String name, String password, String rank) {
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

    public void sign_up_employee(String email, String name, String password, Manager manager) {
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


        private int generateNewUserId() {
        return userRepo.getAll().stream()
                .mapToInt(User::getUserID)
                .max()
                .orElse(0) + 1;
        }

    public void sign_in(String email, String password) {
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

    public void delete_account(String email, String password) {
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

    public List<Client> getAllClients() {
        return clientRepo.getAll();
    }

    public List<Manager> getAllManagers(){
        return managerRepo.getAll();
    }

    public List<Employee> getAllEmployees() {
        return employeeRepo.getAll();
    }

}
// update account
// make order in-store/online
//complex function-- email sending API