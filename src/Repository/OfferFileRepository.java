package Repository;

import Controller.ProductController;
import Model.Offer;
import Model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Uses the FileRepo as schema
 */
public class OfferFileRepository extends FileRepository<Offer> {

    public final ProductFileRepository prodFRepo;
//    public final ProductController prodC;
    public OfferFileRepository(String filePath, ProductFileRepository pr) {
        super(filePath);
        this.prodFRepo = pr;
    }
//    public void setProdController(ProductController pc){
//        prodC = pc;
//    }

    /**
     * Saves a Offer's string to the file
     * @param obj the entity to convert
     * @return
     */
    @Override
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
    @Override
    protected Offer fromFile(String data) {
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

}
