package Model;

public abstract class Product implements HasId{
    private int id;
    private String productName;
    private int productPrice;

    public Product(String productName, int productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public abstract int calc_points();

    private int pointsPrice;

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

    public int getPointsPrice() {
        return pointsPrice;
    }

    public void setPointsPrice(int pointsPrice) {
        this.pointsPrice = pointsPrice;
    }

    public int getProdId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
