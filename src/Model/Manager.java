package Model;

import Model.User;

/**
 * Represents managers in the system
 */
public class Manager extends User {
    private String rank;
    /**
     * Constructs a Manager with the specified email, name, user ID, and password.
     *
     * @param email the email of the manager
     * @param name the name of the manager
     * @param id the ID of the manager
     * @param password the password of the manager
     */
    public Manager(String email, String name, int id, String password, String rank) {
        super(email, name, id, password);
        this.rank = rank;
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
}