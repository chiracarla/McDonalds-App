package Model;

import java.util.List;
import Model.Product;
public class Offer implements HasId{
    private int originalPrice;
    private int newPrice;
    List<Product> products;//TODO: unele cu locatii specifice maybe optional<>

    public Offer(int originalPrice, int newPrice, List<Product> products) {
        this.originalPrice = originalPrice;
        this.newPrice = newPrice;
        this.products = products;
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
                '}';
    }

    @Override
    public Integer getId() {
        return 0;
    }


}
