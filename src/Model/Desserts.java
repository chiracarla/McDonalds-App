package Model;

import Enums.Allergens;

public class Desserts extends Product{

    private Allergens allergens;
    /**
     * Constructs a Dessert with the specified name, price, ID, and sugar-free status.
     *
     * @param productName the name of the dessert
     * @param productPrice the price of the dessert
     * @param allergens the allergen elements of the dessert
     * @param id the ID of the dessert
     */
    public Desserts(String productName, int productPrice, Allergens allergens, int id) {
        super(productName, productPrice, id);
        this.allergens = allergens;
    }

    public Allergens getAllergens() {
        return allergens;
    }

    public void setAllergens(Allergens allergens) {
        this.allergens = allergens;
    }

    @Override
    public Integer getId() {
        return this.getProdId();
    }

    @Override
    public String toFile() {
        return getProdId() + "," + getProductName() + "," + getProductPrice() + "," + allergens;
    }

    @Override
    public String toString() {
        return "Desserts{" +
                "allergens=" + allergens +
                '}';
    }

    //    @Override
//    public int calc_points() {
//        return (int) (this.getProductPrice()*0.5);
//    }
}
