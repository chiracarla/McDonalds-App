package Model;

import java.util.List;
import Model.Product;

/**
 * Represents offers in the system
 * Offers give you the ability to change the prive of an order
 */
public class Offer implements HasId{
    private int originalPrice;
    private int newPrice;
    private int offerId;
    private List<Product> products;
    /**
     * Constructs an Offer with the specified original price, new price, and list of products.
     *
     * @param originalPrice the original price of the offer
     * @param newPrice the new price of the offer
     * @param products the list of products included in the offer
     */
    public Offer(int originalPrice, int newPrice, List<Product> products, int offerId) {
        this.originalPrice = originalPrice;
        this.newPrice = newPrice;
        this.products = products;
        this.offerId = offerId;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public int getNewPrice() {
        return newPrice;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public void setNewPrice(int newPrice) {
        this.newPrice = newPrice;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "originalPrice=" + originalPrice +
                ", newPrice=" + newPrice +
                ", products=" + products +
                ", offerId=" + offerId +
                '}';
    }

    @Override
    public Integer getId() {
        return offerId;
    }

//    @Override
//    public String toFile() {
//        return offerId + "," + originalPrice + "," + newPrice + "," + products;
//    }

}
