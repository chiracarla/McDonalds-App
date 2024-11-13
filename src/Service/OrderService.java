package Service;

import Enums.Locations;
import Model.*;
import Repository.IRepository;

import java.util.List;
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
    public void create_location(Locations location, Manager manager) {
        Location loc = new Location(location, manager);
        locationRepo.create(loc);

        System.out.println("New location placed! Location: " + loc);
    }
}
