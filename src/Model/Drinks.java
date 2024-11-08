package Model;

import Enums.DrinkVolume;

public class Drinks extends Product {

    private DrinkVolume volume;

    public Drinks(String productName, int productPrice, int pointsPrice, DrinkVolume volume) {
        super(productName, productPrice, pointsPrice);
        this.volume = volume;
    }

    public DrinkVolume getVolume() {
        return volume;
    }

    public void setVolume(DrinkVolume volume) {
        this.volume = volume;
    }

    @Override
    public Integer getId() {
        return 0;
    }
}
