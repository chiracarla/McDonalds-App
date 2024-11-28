package Service;

import Enums.Locations;
import Model.*;
import Repository.IRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 */
public class OrderService {
    private final IRepository<Order> orderRepo;  // Repository for orders
    private final IRepository<Location> locationRepo;

    /**
     *
     * @param orderRepo
     * @param locationRepo
     */
    public OrderService(IRepository<Order> orderRepo, IRepository<Location> locationRepo) {
        this.orderRepo = orderRepo;
        this.locationRepo = locationRepo;
    }

    /**
     * @param user
     * @param location
     * @param products
     * @param offer
     * @param payWithPoints
     */
    public void createOrder(Client user, Location location, List<Product> products, Optional<Offer> offer, boolean payWithPoints) {
        int orderID = generateNewOrderID();
        double dif = 0;
        Order order = new Order(products, location, user, orderID);
        if(offer.isPresent()) {
            user.removeOffer(offer.get());
            for (Product prod: offer.get().getProducts()) {
                dif += prod.getProductPrice();
            }
            dif = dif - offer.get().getNewPrice();
        }
        double totalPrice = calculateTotalPrice(products) - dif;
        if(payWithPoints){
            user.subtractPoints((int)(totalPrice/3*2));
        }
        order.setTotalPrice(totalPrice);
        orderRepo.create(order);

        System.out.println("Order placed! Total price: " + totalPrice);
    }

    /**
     * @param user
     * @param location
     * @param products
     * @param offer
     * @param payWithPoints
     */
    public void createOrderEmployee(Employee user, Location location, List<Product> products, Optional<Offer> offer, boolean payWithPoints) {
        int orderID = generateNewOrderID();
        int dif = 0;
        Order order = new Order(products, location, user, orderID);
        if(offer.isPresent()) {
            user.removeOffer(offer.get());
            for (Product prod: offer.get().getProducts()) {
                dif += prod.getProductPrice();
            }
            dif = dif - offer.get().getNewPrice();
        }
        double totalPrice = calculateTotalPrice(products) - dif;
        if(payWithPoints){
            user.subtractPoints((int)(totalPrice/3*2));
        }
        user.addPoints((int) (totalPrice/3));
        totalPrice = totalPrice * 4 / 5;//80%
        order.setTotalPrice(totalPrice);
        orderRepo.create(order);

        System.out.println("Order placed! Total price: " + totalPrice);
    }

    /**
     * @param products
     * @return
     */
    private int calculateTotalPrice(List<Product> products) {
        int totalPrice = 0;
        for (Product product : products) {
            totalPrice += product.getProductPrice();
        }
        return totalPrice;
    }

    /**
     *generates new orderID
     * @return
     */
    private int generateNewOrderID() {
        return orderRepo.getAll().stream()
                .mapToInt(Order::getOrderID)
                .max().orElse(0)+1;
    }
    private int generateNewLocID() {
        return locationRepo.getAll().stream()
                .mapToInt(Location::getId)
                .max().orElse(0)+1;
    }

    /**
     * @param location
     * @param manager
     */
    public void createLocation(Locations location, Manager manager) {
        Location loc = new Location(location, manager, generateNewLocID());
        locationRepo.create(loc);

        System.out.println("New location placed! Location: " + loc);
    }

    /**
     * this function analyzes the most ordered product and gives out how likely it was to get ordered
     */
    public void analyzeMostOrdered() {
        List<Order> orders = orderRepo.getAll();
        Map<Product, Integer> productCount = new HashMap<>();

        for (Order order : orders) {
            for (Product product : order.getProducts()) {
                productCount.put(product, productCount.getOrDefault(product, 0) + 1);
            }
        }

        Product mostOrderedProduct = null;
        int maxCount = 0;
        int totalProducts = 0;

        for (Map.Entry<Product, Integer> entry : productCount.entrySet()) {
            totalProducts += entry.getValue();
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostOrderedProduct = entry.getKey();
            }
        }

        if (mostOrderedProduct != null) {
            double percentage = (double) maxCount / totalProducts * 100;
            System.out.println("\nMost ordered product: " + mostOrderedProduct.getProductName());
            System.out.println("Percentage out of orders: " + String.format("%.2f", percentage) + "%");
        } else {
            System.out.println("No orders found.");
        }
    }

    public Location getByName(Locations loc){
        for(Location l : getAllLocations()){
            if(l.getStoreLocation() == loc){
                return l;
            }
        }
        System.out.println("Location not found");
        return null;
    }

    public List<Location> getAllLocations() {
        return locationRepo.getAll();
    }

    /**
     * gets the most active client in a location
     * @param location
     * @return
     */
    public Client getMostActiveClientByLocation(Location location) {
        Map<Client, Integer> clientOrderCountMap = new HashMap<>();

        List<Order> orders = orderRepo.getAll();
        for (Order order : orders) {
            if (order.getLocation().equals(location)) {
                Client client = (Client) order.getUser();
                clientOrderCountMap.put(client, clientOrderCountMap.getOrDefault(client, 0) + 1);
            }
        }

        return clientOrderCountMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}
