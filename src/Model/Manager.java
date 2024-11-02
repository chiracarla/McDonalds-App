package Model;

import Model.User;

public class Manager extends User {
    private String managerName;

    public Manager(String email, String name, int id) {
        super(email, name, id);
    }

    @Override
    public Integer getId() {
        return this.getUserID();
    }
}