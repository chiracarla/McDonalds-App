package Repository;

import Enums.Allergens;
import Model.*;

public class DessertFileRepository extends FileRepository<Desserts>{
    public DessertFileRepository(String filePath) {
        super(filePath);
    }

    @Override
    protected String toFile(Desserts obj) {
        return obj.getId() + "," + obj.getProductName() + "," +
                obj.getProductPrice() + "," + obj.getAllergens();
    }

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
