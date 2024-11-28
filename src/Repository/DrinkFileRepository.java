package Repository;
import Enums.Allergens;
import Enums.DrinkVolume;
import Model.*;

/**
 * Uses the FileRepo as schema
 */
public class DrinkFileRepository extends FileRepository<Drinks>{
    public DrinkFileRepository(String filePath) {
        super(filePath);
    }

    /**
     * Saves a Drink's string to the file
     * @param obj the entity to convert
     * @return
     */
    @Override
    protected String toFile(Drinks obj) {
        return obj.getId() + "," + obj.getProductName() + "," +
                obj.getProductPrice() + "," + obj.getVolume();
    }

    /**
     * Creates the object of a drink from file
     * @param data the string representation of the entity
     * @return
     */
    @Override
    protected Drinks fromFile(String data) {
        String[] parts = data.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        int price = Integer.parseInt(parts[2]);
        DrinkVolume vol = DrinkVolume.valueOf(parts[3]);
        return new Drinks(name, price, vol, id);
    }
}
