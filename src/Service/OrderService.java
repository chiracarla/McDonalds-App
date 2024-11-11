package Service;

import Enums.Locations;
import Model.*;
import Repository.IRepository;

import java.util.List;
import java.util.Optional;

public class OrderService {
    private final IRepository<Order> orderRepo;  // Repository for orders
    private final IRepository<Location> locationRepo;

    public OrderService(IRepository<Order> orderRepo, IRepository<Location> locationRepo) {
        this.orderRepo = orderRepo;
        this.locationRepo = locationRepo;
    }

//client poate sa fie oricare dintre useri
    public void createOrder(Client user, Location location, List<Product> products, Optional<Offer> offer) {
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
        int totalPrice = calculateTotalPrice(products) - dif;
        user.addPoints(totalPrice/3);
        order.setTotalPrice(totalPrice);
        orderRepo.create(order);

        System.out.println("Order placed! Total price: " + totalPrice);
    }
    //TODO:optiune de pay cu puncte

    private int calculateTotalPrice(List<Product> products) {
        int totalPrice = 0;
        for (Product product : products) {
            totalPrice += product.getProductPrice();
        }
        return totalPrice;
    }

    private int generateNewOrderID() {
        return orderRepo.getAll().stream()
                .mapToInt(Order::getOrderID)
                .max().orElse(0)+1;
    }

    public void create_location(Locations location, Manager manager) {
        Location loc = new Location(location, manager);
        locationRepo.create(loc);

        System.out.println("New location placed! Location: " + loc);
    }
}
