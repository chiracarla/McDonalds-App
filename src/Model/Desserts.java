package Model;

import Enums.Allergens;

public class Desserts extends Product{

    private Allergens allergens;

    public Desserts(String productName, int productPrice, int pointsPrice, Allergens allergens) {
        super(productName, productPrice, pointsPrice);
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
        return 0;
    }
}
