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
    }

    @Override
    public int calc_points() {
        return (int) (calc_points() * 0.5);
    }
}
