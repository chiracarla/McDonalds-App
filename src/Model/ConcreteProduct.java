package Model;

public class ConcreteProduct extends Product {
    public ConcreteProduct(String productName, int productPrice, int id) {
        super(productName, productPrice, id);
    }

    @Override
    public String getType() {
        return "ConcreteProduct";
    }

    @Override
    public Integer getId() {
        return getProdId();
    }
}