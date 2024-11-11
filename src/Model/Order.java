package Model;

import Model.User;
import Model.Product;
import Model.Location;
import java.util.List;
import java.util.ArrayList;

public class Order implements HasId {
    private List<Product> products;
    private int totalPrice;
    private Location location;
    private User user;
    private int orderID;

    public Order(List<Product> products, Location location, User user, int orderID) {
        this.products = products;
        this.location = location;
        this.user = user;
        this.orderID = orderID;
    }
    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public int getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(int totalPrice) {
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
}
