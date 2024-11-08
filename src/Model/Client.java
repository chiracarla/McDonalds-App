package Model;

import Model.User;

public class Client extends User {
    public Client(String email, String name, int id, String password) {
        super(email, name, id, password);
    }

    @Override
    public Integer getId() {
        return this.getUserID();
    }
}