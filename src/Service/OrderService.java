package Service;

import Enums.Locations;
import Model.*;
import Repository.IRepository;

import java.util.List;

public class OrderService {
    private final IRepository<Order> orderRepo;  // Repository for orders
    private final IRepository<Location> locationRepo;

    public OrderService(IRepository<Order> orderRepo, IRepository<Location> locationRepo) {
        this.orderRepo = orderRepo;
        this.locationRepo = locationRepo;
    }

    public void createOrder(Client user, Location location, List<Product> products) {
        int orderID = generateNewOrderID();
        Order order = new Order(products, location, user, orderID);
        int totalPrice = calculateTotalPrice(products);
        order.setTotalPrice(totalPrice);
        orderRepo.create(order);

        System.out.println("Order placed! Total price: " + totalPrice);
    }//question

    private int calculateTotalPrice(List<Product> products) {
        int totalPrice = 0;
        for (Product product : products) {
            totalPrice += product.getProductPrice();
        }
        return totalPrice;
    } //+met de adaugat oferte

    private int generateNewOrderID() {
        return orderRepo.getAll().stream()
                .mapToInt(Order::getOrderID)
                .max().orElse(0)+1;
    }

    public void create_location(Locations location, Manager manager) {
        Location loc = new Location(location, manager);
        locationRepo.create(loc);

        System.out.println("New location placed! Location: " + loc);
    } //question
}
