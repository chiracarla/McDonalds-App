package Model;

import Model.Manager;
import Model.User;

public class Employee extends User{
    Manager manager;

    public Employee(String email, String name, int id) {
        super(email, name, id);
    }

    public Integer getId() {
        return this.getUserID();
    }
}
