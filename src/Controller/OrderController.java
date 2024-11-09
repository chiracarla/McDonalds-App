package Controller;

import Enums.Locations;
import Model.*;
import Service.OrderService;

import java.util.List;

public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public void createOrder(Client user, Location location, List<Product> products) {
        orderService.createOrder(user, location, products);
    }

    public Location createLocation(Locations location, Manager manager) {

        orderService.create_location(location, manager);
        return null;
    }
}
