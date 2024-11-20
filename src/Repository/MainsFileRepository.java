package Repository;

import Enums.DishSize;
import Enums.DrinkVolume;
import Model.Drinks;
import Model.MainDish;
import com.sun.tools.javac.Main;

/**
 * The {@code MainsFileRepository} class extends the {@code FileRepository} class
 */
public class MainsFileRepository extends FileRepository<MainDish>{
    public MainsFileRepository(String filePath){
        super(filePath);
    }

    /**
     * Converts a {@code MainDish} entity to a string representation for storage.
     * @param obj the entity to convert
     * @return
     */
    @Override
    protected String toFile(MainDish obj) {
        return obj.getId() + "," + obj.getProductName() + "," +
                obj.getProductPrice() + "," + obj.getCalories() + "," + obj.getSize();
    }

    /**
     * Converts a string representation of a {@code MainDish} entity to an actual entity.
     * @param data the string representation of the entity
     * @return
     */
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
