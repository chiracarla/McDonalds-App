package Model;

import Model.Offer;
import Model.Order;
import java.util.List;
import java.util.ArrayList;

public abstract class User {
    private int points;
    Offer offers;
    Order order;

    public User(String phoneNumber, String name) {
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    private String phoneNumber;
    private String name;


    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}