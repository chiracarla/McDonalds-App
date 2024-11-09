package Model;

import Model.User;

public class Manager extends User {
    private String rank;

    public Manager(String email, String name, int id, String password, String rank) {
        super(email, name, id, password);
        this.rank = rank;
    }

    @Override
    public Integer getId() {
        return this.getUserID();
    }
}