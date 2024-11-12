package Model;

import Enums.DishSize;

public class SideDish extends Product{

    private DishSize size;

    public SideDish(String productName, int productPrice, DishSize size, int id) {
        super(productName, productPrice, id);
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
        return this.getProdId();
    }
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
