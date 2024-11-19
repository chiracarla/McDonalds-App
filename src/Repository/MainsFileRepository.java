package Repository;

import Enums.DishSize;
import Enums.DrinkVolume;
import Model.Drinks;
import Model.MainDish;
import com.sun.tools.javac.Main;

public class MainsFileRepository extends FileRepository<MainDish>{
    public MainsFileRepository(String filePath){
        super(filePath);
    }

    @Override
    protected String toFile(MainDish obj) {
        return obj.getId() + "," + obj.getProductName() + "," +
                obj.getProductPrice() + "," + obj.getCalories() + "," + obj.getSize();
    }

    @Override
    protected MainDish fromFile(String data) {
        String[] parts = data.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        int price = Integer.parseInt(parts[2]);
        int calories = Integer.parseInt(parts[3]);
        DishSize size = DishSize.valueOf(parts[4]);
        return new MainDish(name, price, calories, size, id);
    }
}
