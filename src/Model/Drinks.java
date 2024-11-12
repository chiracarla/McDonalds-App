package Model;

import Enums.DrinkVolume;

public class Drinks extends Product {

    private DrinkVolume volume;

    public Drinks(String productName, int productPrice, DrinkVolume volume, int id) {
        super(productName, productPrice,id);
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
        return this.getProdId();
    }

    @Override
    public String toString() {
        return "Drinks{" +
                "volume=" + volume +
                '}';
    }

    //
//    @Override
//    public int calc_points() {
//        return (int) (this.getProductPrice() * 0.3);
//    }
}
