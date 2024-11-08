package Model;

import Enums.DishSize;

public class MainDish extends Product{
    private int calories;
    public DishSize size; //small, medium, large, Xlarge

    public MainDish(String productName, int productPrice, int pointsPrice, int calories, DishSize size) {
        super(productName, productPrice, pointsPrice);
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
        return 0;
    }
}
