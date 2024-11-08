package Controller;

import Model.Location;
import Model.Order;
import Model.Product;
import Model.User;
import Service.OrderService;

import java.util.List;

public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public void createOrder(User user, Location location, List<Product> products) {
        orderService.createOrder(user, location, products);
    }

}
