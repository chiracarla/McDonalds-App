package Model;

import Model.Manager;
import Model.User;

public class Employee extends User{
    Manager manager;

    public Employee(String email, String name, int id, String password, Manager manager) {
        super(email, name, id, password);
        this.manager = manager;
    }

    public Integer getId() {
        return this.getUserID();
    }
}
