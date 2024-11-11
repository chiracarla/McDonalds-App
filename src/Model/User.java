package Model;

import java.util.ArrayList;
import java.util.List;

public abstract class User implements HasId {
    private int points;
    private int userID;
    private String email;
    private String name;
    private String password;
    List<Offer> offers;
    Order order;

    public User(String email, String name, int userID, String password) {
        this.email = email;
        this.name = name;
        this.userID = userID;
        this.password = password;
        offers = new ArrayList<>();
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID){ this.userID = userID; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addOffer(Offer offer) {
        offers.add(offer);
    }

    public void removeOffer(Offer offer) {
        offers.remove(offer);
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int subtractPoints(int points) {
        this.points -= points;
        return this.points - points;
    }

}