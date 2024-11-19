package Repository;
import Enums.Allergens;
import Enums.DrinkVolume;
import Model.*;
public class DrinkFileRepository extends FileRepository<Drinks>{
    public DrinkFileRepository(String filePath) {
        super(filePath);
    }

    @Override
    protected String toFile(Drinks obj) {
        return obj.getId() + "," + obj.getProductName() + "," +
                obj.getProductPrice() + "," + obj.getVolume();
    }

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
