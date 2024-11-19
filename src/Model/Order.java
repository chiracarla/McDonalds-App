package Model;

import Model.User;
import Model.Product;
import Model.Location;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents and order in the system
 * The order can be very complex and very simple as well - meaning it can consist of anything.
 */
public class Order implements HasId {
    private List<Product> products;
    private double totalPrice;
    private Location location;
    private User user;
    private int orderID;
    /**
     * Constructs an Order with the specified products, location, user, and order ID.
     *
     * @param products the list of products in the order
     * @param location the location of the order
     * @param user the user who placed the order
     * @param orderID the ID of the order
     */
    public Order(List<Product> products, Location location, User user, int orderID) {
        this.products = products;
        this.location = location;
        this.user = user;
        this.orderID = orderID;
    }

    /**
     * Getters and Setters related to the Order
     */
    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public int getOrderID() {
        return orderID;
    }
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    @Override
    public Integer getId() {
        return 0;
    }

//    @Override
//    public String toFile() {
//        return products.toString() + " " + location.toString() + " " + user.toString() + " " + orderID;
//    }
    //TODO id
}
