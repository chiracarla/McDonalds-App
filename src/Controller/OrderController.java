package Controller;

import Enums.Locations;
import Model.*;
import Service.OrderService;

import java.util.List;
import java.util.Optional;

/**
 * Controller for managing orders
 */
public class OrderController {
    private final OrderService orderService;
    /**
     * Constructs an OrderController with the specified order service.
     *
     * @param orderService the service for managing orders
     */
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    /**
     * Creates a new order.
     *
     * @param user the client placing the order
     * @param location the location of the order
     * @param products the list of products in the order
     * @param offer an optional offer applied to the order
     * @param payWithPoints whether the order is paid with points
     */
    public void createOrder(Client user, Location location, List<Product> products, Optional<Offer> offer, boolean payWithPoints) {
        orderService.createOrder(user, location, products, offer, payWithPoints);
    }
    //TODO: ar tb cu user input

    /**
     * Creates a new location
     * @param location
     * @param manager
     * @return
     */
    public Location createLocation(Locations location, Manager manager) {

        orderService.createLocation(location, manager);
        return null;
    }

    public void analyzeMostOrdered(){
        orderService.analyzeMostOrdered();
    }
}
