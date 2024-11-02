package Model;

import Model.User;

public class Client extends User {
    public Client(String email, String name, int id) {
        super(email, name, id);
    }

    @Override
    public Integer getId() {
        return this.getUserID();
    }
}