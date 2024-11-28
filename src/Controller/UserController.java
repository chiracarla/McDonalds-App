package Controller;

import Enums.ManagerRank;
import Model.*;
import Service.UserService;

import java.util.List;
import java.util.Optional;

/**
 * Controller for the user
 */
public class UserController {
    private final UserService userService;

    /**
     * Constructs the controller linked to service
     * @param userService
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Signs up a client
     * @param email
     * @param name
     * @param password
     * @return
     */
    public void signUpClient(String email, String name, String password) {
        name.trim();
        if (!isValidName(name)) {
            throw new IllegalArgumentException("Name must contain at least two words, each starting with a capital letter.");
        }
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Email is not valid. Please provide a valid email address.");
        }
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Password must contain at least one letter and one number.");
        }
        userService.signUpClient(email, name, password);
    }

    /**
     * signs up a manager
     * @param email
     * @param name
     * @param password
     * @param rank
     * @return
     */
    public void signUpManager(String email, String name, String password, ManagerRank rank) {
        name.trim();
        if (!isValidName(name)) {
            throw new IllegalArgumentException("Name must contain at least two words, each starting with a capital letter.");
        }
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Email is not valid. Please provide a valid email address.");
        }
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Password must contain at least one letter and one number.");
        }
//        userService.signUpManager(email, name, password, rank);
        userService.signUpManager(email, name, password, rank);
    }

    /**
     * signs up an employee
     * @param email
     * @param name
     * @param password
     * @param manager
     */
    public void signUpEmployee(String email, String name, String password, Manager manager) {
        name.trim();
        if (!isValidName(name)) {
            throw new IllegalArgumentException("Name must contain at least two words, each starting with a capital letter.");
        }
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Email is not valid. Please provide a valid email address.");
        }
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Password must contain at least one letter and one number.");
        }
        userService.signUpEmployee(email, name, password, manager);
    }

    /**
     * sign in->linked to the service
     * @param email
     * @param password
     */
    public User signIn(String email, String password) {
        return userService.signIn(email, password);
    }

    /**
     * Allows the signed in user to delete the account
     * @param email
     * @param password
     */
    public void deleteAccount(String email, String password) {
        userService.deleteAccount(email, password);
    }

    /**
     * Shows all clients
     */
    public void showAllClients() {
        List<Client> clients = userService.getAllClients();
        if (clients.isEmpty()) {
            System.out.println("No clients found.");
        } else {
            clients.forEach(client -> System.out.println(client.getName()));
        }
    }

    public void showAllManagers() {
        List<Manager> managers = userService.getAllManagers();
        if (managers.isEmpty()) {
            System.out.println("No managers found.");
        } else {
            managers.forEach(manager -> System.out.println(manager.getName()));
        }
    }

    public void showAllEmployees() {
        List<Employee> employees = userService.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            employees.forEach(employee -> System.out.println(employee.getName()));
        }
    }

    /**
     * reads a manager
     */
    public Manager readManager(int id) {
        Manager manager = userService.readManager(id);
        if (manager == null) {
            System.out.println("No manager found with id: " + id);
        }
        return manager;
    }

    /**
     * sort the employees by name
     * @return
     */
    public List<Employee> employeeSortByName() {
        List<Employee> employees = userService.employeesSortByName();
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        }
        return employees;
    }

    private boolean isValidName(String name) {
        String[] parts = name.trim().split("\\s+");
        if (parts.length < 2) {
            return false;
        }
        for (String part : parts) {
            if (!part.matches("[A-Z].*")){
                return false;
            }
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email.matches(emailRegex);
    }//Oare astea tb simplificate?

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d).+$");
    }

}
