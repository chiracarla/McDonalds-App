package Model;

import Enums.Allergens;

public class Desserts extends Product{

    private Allergens allergens;

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

//    @Override
//    public int calc_points() {
//        return (int) (this.getProductPrice()*0.5);
//    }
}
