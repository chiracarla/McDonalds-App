package Repository.FRepository;

import Model.Order;
import Model.*;
import Repository.IRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Uses the FileRepo as schema
 */
public class OrderFileRepository implements IRepository<Order> {
//Order(List<Product> products, Location location, User user, int orderID)
    public UserFileRepository userFR;
    public ProductFileRepository prodFR;
    public LocationFileRepository locFR;
    private final String filePath;
    private Map<Integer, Order> data;
    public OrderFileRepository(String filePath, UserFileRepository userR, ProductFileRepository prodR, LocationFileRepository locR) {
        this.filePath = filePath;
        userFR = userR;
        prodFR = prodR;
        locFR = locR;
        this.data = loadFromFile();
    }

    /**
     * Saves a Order's string to the file
     * @param obj the entity to convert
     * @return
     */
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

    /**
     * Creates the object of a order from file
     * @param data the string representation of the entity
     * @return
     */

    protected Order fromFile(String data) {
        String[] parts = data.split(",");
        int orderId = Integer.parseInt(parts[0]);
        double price = Double.valueOf(parts[1]); // Original price of the offer
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
        return new Order(products, loc, u1, orderId, (int) price);
    }

    @Override
    public void create(Order obj) {
        if (data.containsKey(obj.getId())) {
            throw new IllegalArgumentException("ID " + obj.getId() + " exists.");
        }
        data.put(obj.getId(), obj);
        saveToFile();
    }
    /**
     * Updates an existing entity in the repository.
     *
     * @param obj the entity to update
     * @throws IllegalArgumentException if the entity does not exist
     */
    @Override
    public void update(Order obj) {
        if (!data.containsKey(obj.getId())) {
            throw new IllegalArgumentException("ID " + obj.getId() + " doesn't exist.");
        }
        data.put(obj.getId(), obj);
        saveToFile();
    }
    /**
     * Deletes an entity from the repository.
     *
     * @param id the ID of the entity to delete
     * @throws IllegalArgumentException if the entity does not exist
     */
    @Override
    public void delete(Integer id) {
        if (!data.containsKey(id)) {
            throw new IllegalArgumentException("ID " + id + " doesn't exist.");
        }
        data.remove(id);
        saveToFile();
    }
    /**
     * Reads an entity from the repository by its ID.
     *
     * @param id the ID of the entity to read
     * @return the entity with the specified ID, or null if not found
     */
    @Override
    public Order read(Integer id) {
        return data.get(id);
    }
    /**
     * Retrieves all entities from the repository.
     *
     * @return a list of all entities
     */
    @Override
    public List<Order> getAll() {
        return new ArrayList<>(data.values());
    }

    /**
     * Saves the data to the file.
     */
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Order obj : data.values()) {
                writer.write(toFile(obj));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving to file", e);
        }
    }
    /**
     * Loads the data from the file.
     *
     * @return the data loaded from the file
     */
    private Map<Integer, Order> loadFromFile() {
        Map<Integer, Order> result = new HashMap<>();
        java.io.File file = new File(filePath);
        if (!file.exists()) {
            return result;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Order obj = fromFile(line);
                result.put(obj.getId(), obj);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading from file", e);
        }
        return result;
    }
}
