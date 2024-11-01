package Model;

import Model.User;

public class Manager extends User {
    private String managerName;

    public Manager(String phoneNumber, String name) {
        super(phoneNumber, name);
    }
}