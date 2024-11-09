package Model;

import Enums.DishSize;

public class SideDish extends Product{

    private DishSize size;

    public SideDish(String productName, int productPrice, DishSize size) {
        super(productName, productPrice);
        this.size = size;
    }

    public DishSize getSize() {
        return size;
    }

    public void setSize(DishSize size) {
        this.size = size;
    }

    @Override
    public Integer getId() {
        return 0;
    } //tb id

    @Override
    public int calc_points() {
        return (int) (this.getProductPrice() * 0.5);
    }
}
