package Controller;

import Enums.Locations;
import Model.*;
import Service.OrderService;

import java.util.List;
import java.util.Optional;

public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public void createOrder(Client user, Location location, List<Product> products, Optional<Offer> offer, boolean payWithPoints) {
        orderService.createOrder(user, location, products, offer, payWithPoints);
    }
    //TODO: ar tb cu user input

    public Location createLocation(Locations location, Manager manager) {

        orderService.create_location(location, manager);
        return null;
    }
}
