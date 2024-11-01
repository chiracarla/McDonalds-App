package Model;

import java.util.List;
import Model.Product;
public class Offer {
    private int originalPrice;
    private int newPrice;
    List<Product> products;
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
}
