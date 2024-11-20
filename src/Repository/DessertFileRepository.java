package Repository;

import Enums.Allergens;
import Model.*;

/**
 * Uses the FileRepository class to access the information about the desserts
 */
public class DessertFileRepository extends FileRepository<Desserts>{
    public DessertFileRepository(String filePath) {
        super(filePath);
    }

    /**
     * Saves the string of a dessert entity
     * @param obj the entity to convert
     * @return
     */
    @Override
    protected String toFile(Desserts obj) {
        return obj.getId() + "," + obj.getProductName() + "," +
                obj.getProductPrice() + "," + obj.getAllergens();
    }

    /**
     * Reads the string of a dessert entity creating it into object
     * @param data the string representation of the entity
     * @return
     */
    @Override
    protected Desserts fromFile(String data) {
        String[] parts = data.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        int price = Integer.parseInt(parts[2]);
        Allergens allergens = Allergens.valueOf(parts[3]);
        return new Desserts(name, price, allergens, id);
    }
}
