package Model;

import Enums.Allergens;

/**
 * Represents a product in the system
 * Products are anything food related
 */
public abstract class Product implements HasId{
    private int productId;
    private String productName;
    private int productPrice;
    private Allergens allergens;
    //TODO
    // stock? atunci va tb sa fie la fiec locatie diferit-maybe not
    //poate fiecare locatie sa inceapa cu o lista de stock la fiec produs?
    /**
     * Constructs a Product with the specified name, price, and ID.
     *
     * @param productName the name of the product
     * @param productPrice the price of the product
     * @param id the ID of the product
     */
    public Product(String productName, int productPrice, int id) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productId = id;
    }

    public Product(String productName, int productPrice, int id, Allergens allergens) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productId = id;
        this.allergens = allergens;
    }

//    public abstract int calc_points();

//    private int pointsPrice;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

//    public int getPointsPrice() {
//        return pointsPrice;
//    }
//
//    public void setPointsPrice(int pointsPrice) {
//        this.pointsPrice = pointsPrice;
//    }

    public int getProdId() {
        return productId;
    }

    public void setId(int id) {
        this.productId = id;
    }

    public Allergens getAllergens() {
        return allergens;
    }

    public void setAllergens(Allergens allergens) {
        this.allergens = allergens;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", allergens=" + allergens +
                '}';
    }

    public abstract String getType();

//    @Override
//    public String toFile() {
//        return productId + "," + productName + "," + productPrice + "," + allergens;
//    }
}
