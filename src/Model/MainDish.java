package Model;

import Enums.DishSize;

public class MainDish extends Product{
    private int calories;
    public DishSize size; //small, medium, large, Xlarge

    public MainDish(String productName, int productPrice, int calories, DishSize size, int id) {
        super(productName, productPrice, id);
        this.calories = calories;
        this.size = size;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
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

    @Override
    public String toString() {
        return "MainDish{" +
                "calories=" + calories +
                ", size=" + size +
                '}';
    }

    //    @Override
//    public int calc_points() {
//        return (int) (this.getProductPrice() * 0.7);
//    }
}
//TODO:
// cumpar de: 30 lei
// primesc: 10 p
// pot sa cumpar de: 15 lei