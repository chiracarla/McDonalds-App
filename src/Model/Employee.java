package Model;

import Model.Manager;
import Model.User;

/**
 * Represents the employees of the system
 */
public class Employee extends User{
    Manager manager;
    /**
     * Constructs an Employee with the specified email, name, user ID, password, and position.
     *
     * @param email the email of the employee
     * @param name the name of the employee
     * @param id the ID of the employee
     * @param password the password of the employee
     * @param manager the manager of the employee
     */
    public Employee(String email, String name, int id, String password, Manager manager) {
        super(email, name, id, password);
        this.manager = manager;
    }

    @Override
    public void displayOptions(){
        System.out.println("Employee Options: \n" +
                "1. Place order\n" +
                "2. View all offers\n" +
                "3. Delete account\n" +
                "4. Show entire Menu\n" +
                "0. Exit \n");
    }

    @Override
    public String toString() {
        return "Employee{" +
                "manager=" + manager +
                '}';
    }

    public Integer getId() {
        return this.getUserID();
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
//    @Override
//    public String toFile(){
//        return getUserID() + "," + getEmail() + "," + getName() + "," + getPassword() + "," + manager;
//    }

    @Override
    public String getUserType() {
        return "Employee";
    }
}
