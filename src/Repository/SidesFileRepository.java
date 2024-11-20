package Repository;

import Enums.DishSize;
import Model.SideDish;

/**
 * The {@code SidesFileRepository} class extends the {@code FileRepository} class
 */
public class SidesFileRepository extends FileRepository<SideDish>{
    public SidesFileRepository(String filePath){
        super(filePath);
    }

    /**
     * Converts an entity to a string to be written to a file
     * @param obj the entity to convert
     * @return
     */
    @Override
    protected String toFile(SideDish obj) {
        return obj.getId() + "," + obj.getProductName() + "," +
                obj.getProductPrice() + "," + obj.getSize();
    }

    /**
     * Converts a string from a file to an entity
     * @param data the string representation of the entity
     * @return
     */
    @Override
    protected SideDish fromFile(String data) {
        String[] parts = data.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        int price = Integer.parseInt(parts[2]);
        DishSize size = DishSize.valueOf(parts[3]);
        return new SideDish(name, price, size, id);
    }
}
