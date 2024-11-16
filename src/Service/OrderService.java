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
        //TODO:transformare meniu
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
        //TODO:optiune transform menu
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
     *
     * @return
     */
    private int generateNewOrderID() {
        return orderRepo.getAll().stream()
                .mapToInt(Order::getOrderID)
                .max().orElse(0)+1;
    }

    /**
     * @param location
     * @param manager
     */
    public void createLocation(Locations location, Manager manager) {
        Location loc = new Location(location, manager);
        locationRepo.create(loc);

        System.out.println("New location placed! Location: " + loc);
    }

    //this function analyzes the most ordered product and gives out how likely it was to get ordered
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

    public List<Location> getAllLocations() {
        return locationRepo.getAll();
    }
}
