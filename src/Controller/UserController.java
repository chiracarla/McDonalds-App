package Controller;

import Model.*;
import Service.UserService;

import java.util.List;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public Client sign_Up_Client(String email, String name, String password) {
        userService.sign_up_client(email, name, password);
        return null;
    } //dc nu void?

    public Manager sign_Up_Manager(String email, String name, String password, String rank) {
        userService.sign_up_manager(email, name, password, rank);
        return null;
    }

    public void sign_Up_Employee(String email, String name, String password, Manager manager) {
        userService.sign_up_employee(email, name, password, manager);
    }

    public void sign_In(String email, String password) {
        userService.sign_in(email, password);
    } //nu se potriveste cu conventiile

    public void delete_Account(String email, String password) {
        userService.delete_account(email, password);
    }

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
