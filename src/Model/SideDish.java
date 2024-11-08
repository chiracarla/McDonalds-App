package Model;

import Enums.DishSize;

public class SideDish extends Product{

    private DishSize size;

    public SideDish(String productName, int productPrice, int pointsPrice, DishSize size) {
        super(productName, productPrice, pointsPrice);
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
}
