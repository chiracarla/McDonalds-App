package Model;

public abstract class Product implements HasId{
    private int productId;
    private String productName;
    private int productPrice;
    //TODO
    //stock? atunci va tb sa fie la fiec locatie diferit-maybe not
    //poate fiecare locatie sa inceapa cu o lista de stock la fiec produs?
    public Product(String productName, int productPrice, int id) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productId = id;
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
}
