package Repository;

import Enums.Locations;
import Model.Offer;
import Model.Order;
import Model.*;

import java.util.ArrayList;
import java.util.List;

public class OrderFileRepository extends FileRepository<Order>{
//Order(List<Product> products, Location location, User user, int orderID)
    public UserFileRepository userFR;
    public ProductFileRepository prodFR;
    public LocationFileRepository locFR;
    public OrderFileRepository(String filePath, UserFileRepository userR, ProductFileRepository prodR, LocationFileRepository locR) {
        super(filePath);
        userFR = userR;
        prodFR = prodR;
        locFR = locR;
    }
    @Override
    protected String toFile(Order obj) {
        StringBuilder productsString = new StringBuilder();
        for (Product product : obj.getProducts()) {
            productsString.append(product.getId()).append(";");
        }
        // Remove the last semicolon
        if (productsString.length() > 0) {
            productsString.setLength(productsString.length() - 1);
        }
        return obj.getId() + "," + obj.getTotalPrice() + "," + obj.getLocation().getId() + "," + obj.getUser().getId() + "," + productsString;

    }


    @Override
    protected Order fromFile(String data) {
        String[] parts = data.split(",");
        int orderId = Integer.parseInt(parts[0]);
        int price = Integer.parseInt(parts[1]); // Original price of the offer
        Location loc = locFR.read(Integer.parseInt(parts[2])); // New price of the offer
        User u1 = userFR.read(Integer.parseInt(parts[3]));
        // Extract and process the product IDs from the products string
        String[] productIds = parts[4].split(";");
        List<Product> products = new ArrayList<>();
        for (String productIdString : productIds) {
            int productId = Integer.parseInt(productIdString.trim()); // Parse the product ID
            Product product = prodFR.read(productId); // Read the product from the repository by its ID
            if (product != null) {
                products.add(product); // Add the product to the list
            }
        }
        // Return the constructed Offer object
        return new Order(products, loc, u1, orderId, price);
    }
}
