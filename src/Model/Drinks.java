package Model;

import Enums.DrinkVolume;
/**
 *Represents the drinks of the system
 **/
public class Drinks extends Product {

    private DrinkVolume volume;
    /**
     * Constructs a Drinks with the specified name, price, ID, and alcoholic status.
     *
     * @param productName the name of the drink
     * @param productPrice the price of the drink
     * @param id the ID of the drink
     */
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
    public String getType() {
        return "Drink";
    }

//    @Override
//    public String toFile() {
//        return getProdId() + "," + getProductName() + "," + getProductPrice() + "," + volume;
//    }

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
