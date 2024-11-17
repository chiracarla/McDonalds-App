package Model;

import Model.User;

/**
 * Represents the buyers/clients of the system
 */
public class Client extends User {
    /**
     * Constructs a Client with the specified email, name, user ID, password, and address.
     *
     * @param email the email of the client
     * @param name the name of the client
     * @param id the ID of the client
     * @param password the password of the client
     */
    public Client(String email, String name, int id, String password) {
        super(email, name, id, password);
    }

    @Override
    public void displayOptions(){
        System.out.println("Client Options: \n" +
                "1. Place order\n" +
                "2. View all offers\n" +
                "3. Delete account\n" +
                "0. Exit \n");
    }
    @Override
    public Integer getId() {
        return this.getUserID();
    }

    public String toFile(){
        return getId() + "," + getEmail() + "," + getName() + "," + getPassword();
    }
}