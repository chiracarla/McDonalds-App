package Model;

import Model.Manager;
import Model.User;

public class Employee extends User{
    Manager manager;

    public Employee(String phoneNumber, String name) {
        super(phoneNumber, name);
    }
}
