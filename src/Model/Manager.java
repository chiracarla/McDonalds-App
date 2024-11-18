package Model;

import Enums.ManagerRank;
import Model.User;

/**
 * Represents managers in the system
 */
public class Manager extends User {
    private ManagerRank rank;
    /**
     * Constructs a Manager with the specified email, name, user ID, and password.
     *
     * @param email the email of the manager
     * @param name the name of the manager
     * @param id the ID of the manager
     * @param password the password of the manager
     */
    public Manager(String email, String name, int id, String password, ManagerRank rank) {
        super(email, name, id, password);
        this.rank = rank;
    }

    @Override
    public void displayOptions(){
        System.out.println("Manager Options: \n" +
                "5. Add new Product\n" +
                "6. Add new Offer\n" +
                "7. View all\n" +
                "0. Exit \n");
    }

    @Override
    public String toString() {
        return "Manager{" +
                "rank='" + rank + '\'' +
                '}';
    }

    @Override
    public Integer getId() {
        return this.getUserID();
    }

    public ManagerRank getRank() {
        return rank;
    }

    public void setRank(ManagerRank rank) {
        this.rank = rank;
    }

    @Override
    public String toFile() {
        return getUserID() + "," + getEmail() + "," + getName() + "," + getPassword() + "," + rank;
    }
}