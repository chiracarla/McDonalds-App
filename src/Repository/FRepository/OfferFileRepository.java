package Repository.FRepository;

import Model.Offer;
import Model.Product;
import Repository.IRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Uses the FileRepo as schema
 */
public class OfferFileRepository implements IRepository<Offer> {
    private final String filePath;
    private Map<Integer, Offer> data;
    private final ProductFileRepository prodFRepo;
//    public final ProductController prodC;
    public OfferFileRepository(String filePath, ProductFileRepository prodFRepo) {
//        super(filePath);
        this.filePath = filePath;
        this.prodFRepo = prodFRepo;
        this.data = loadFromFile();
        System.out.println(prodFRepo);
    }
//    public void setProdController(ProductController pc){
//        prodC = pc;
//    }

    /**
     * Saves a Offer's string to the file
     * @param obj the entity to convert
     * @return
     */
//    @Override
    protected String toFile(Offer obj) {
        StringBuilder productsString = new StringBuilder();
        for (Product product : obj.getProducts()) {
            productsString.append(product.getId()).append(";");
        }
        // Remove the last semicolon
        if (productsString.length() > 0) {
            productsString.setLength(productsString.length() - 1);
        }
        return obj.getId() + "," + obj.getOriginalPrice() + "," + obj.getNewPrice() + "," + productsString;

    }

    /**
     * Creates the object of a offer from file
     * @param data the string representation of the entity
     * @return
     */

    protected Offer fromFile(String data) {
        System.out.println(prodFRepo);
        String[] parts = data.split(",");
        int offerId = Integer.parseInt(parts[0]); // Offer ID
        int originalPrice = Integer.parseInt(parts[1]); // Original price of the offer
        int newPrice = Integer.parseInt(parts[2]); // New price of the offer

        // Extract and process the product IDs from the products string
        String[] productIds = parts[3].split(";");
        List<Product> products = new ArrayList<>();
        for (String productIdString : productIds) {
            int productId = Integer.parseInt(productIdString.trim()); // Parse the product ID
            Product product = prodFRepo.read(productId); // Read the product from the repository by its ID
            if (product != null) {
                products.add(product); // Add the product to the list
            }
        }
        // Return the constructed Offer object
        return new Offer(originalPrice, newPrice, products, offerId);
    }

    @Override
    public void create(Offer obj) {
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
    public void update(Offer obj) {
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
    public Offer read(Integer id) {
        return data.get(id);
    }
    /**
     * Retrieves all entities from the repository.
     *
     * @return a list of all entities
     */
    @Override
    public List<Offer> getAll() {
        return new ArrayList<>(data.values());
    }

    /**
     * Saves the data to the file.
     */
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Offer obj : data.values()) {
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
    private Map<Integer, Offer> loadFromFile() {
        Map<Integer, Offer> result = new HashMap<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return result;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Offer obj = fromFile(line);
                result.put(obj.getId(), obj);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading from file", e);
        }
        return result;
    }

}
