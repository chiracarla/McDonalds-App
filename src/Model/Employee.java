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
    public String toString() {
        return "Employee{" +
                "manager=" + manager +
                '}';
    }

    public Integer getId() {
        return this.getUserID();
    }

    @Override
    public String toFile(){
        return getUserID() + "," + getEmail() + "," + getName() + "," + getPassword() + "," + manager;
    }
}
