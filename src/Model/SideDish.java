package Model;

import Enums.DishSize;

/**
 * Represents a side dish in the system.
 * Side dishes consist mainly of fries and sauces
 */
public class SideDish extends Product{

    private DishSize size;
    /**
     * Constructs a SideDish with the specified name, price, ID, and size.
     *
     * @param productName the name of the side dish
     * @param productPrice the price of the side dish
     * @param id the ID of the side dish
     * @param size the size of the side dish
     */
    public SideDish(String productName, int productPrice, DishSize size, int id) {
        super(productName, productPrice, id);
        this.size = size;
    }
    /**
     * Gets the size of the side dish.
     *
     * @return the size of the side dish
     */
    public DishSize getSize() {
        return size;
    }
    /**
     * Sets the size of the side dish.
     *
     * @param size the size to set
     */
    public void setSize(DishSize size) {
        this.size = size;
    }

    @Override
    public Integer getId() {
        return this.getProdId();
    }

//    @Override
    public String getType() {
        return "Side";
    }

//    @Override
//    public String toFile() {
//        return this.getProdId() + "," + this.getProductName() + "," + this.getProductPrice() + "," + this.size;
//    }
//
//    @Override
//    public int calc_points() {
//        return (int) (this.getProductPrice() * 0.5);
//    }

    @Override
    public String toString() {
        return "SideDish{" +
                "size=" + size +
                '}';
    }
}
