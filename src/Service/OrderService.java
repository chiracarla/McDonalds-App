package Service;

import Model.*;
import Repository.IRepository;

import java.util.List;

public class OrderService {
    private final IRepository<Order> orderRepo;  // Repository for orders
    private final IRepository<MainDish> mainDishRepo;
    private final IRepository<SideDish> sideDishRepo;
    private final IRepository<Desserts> dessertRepo;
    private final IRepository<Drinks> drinkRepo;

    public OrderService(IRepository<Order> orderRepo, IRepository<MainDish> mainDishRepo, IRepository<SideDish> sideDishRepo, IRepository<Desserts> dessertRepo, IRepository<Drinks> drinkRepo) {
        this.orderRepo = orderRepo;
        this.mainDishRepo = mainDishRepo;
        this.sideDishRepo = sideDishRepo;
        this.dessertRepo = dessertRepo;
        this.drinkRepo = drinkRepo;
    }

    public void createOrder(User user, Location location, List<Product> products) {
        int orderID = generateNewOrderID();
        Order order = new Order(products, location, user, orderID);
        int totalPrice = calculateTotalPrice(products);
        order.setTotalPrice(totalPrice);
        orderRepo.create(order);

        System.out.println("Order placed! Total price: " + totalPrice);
    }

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
}
