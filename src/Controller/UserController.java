package Controller;

import Model.*;
import Service.UserService;

import java.util.List;

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
    public Client signUpClient(String email, String name, String password) {
        userService.signUpClient(email, name, password);
        return null;
    } //dc nu void?

    /**
     * signs up a manager
     * @param email
     * @param name
     * @param password
     * @param rank
     * @return
     */
    public Manager signUpManager(String email, String name, String password, String rank) {
        userService.signUpManager(email, name, password, rank);
        return null;
    }

    /**
     * signs up an employee
     * @param email
     * @param name
     * @param password
     * @param manager
     */
    public void signUpEmployee(String email, String name, String password, Manager manager) {
        userService.signUpEmployee(email, name, password, manager);
    }

    /**
     * sign in->linked to the service
     * @param email
     * @param password
     */
    public void signIn(String email, String password) {
        userService.signIn(email, password);
    } //nu se potriveste cu conventiile

    /**
     * Allows the signed in user to delete the account
     * @param email
     * @param password
     */
    public void deleteAccount(String email, String password) {
        userService.deleteAccount(email, password);
    }

    /**
     * Shows all clinets
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
            System.out.println("No clients found.");
        } else {
            employees.forEach(employee -> System.out.println(employee.getName()));
        }
    }



}
