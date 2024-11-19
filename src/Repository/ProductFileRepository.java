package Repository;

import Enums.Allergens;
import Enums.DishSize;
import Enums.DrinkVolume;
import Enums.ManagerRank;
import Model.*;

public class ProductFileRepository extends FileRepository <Product> {
    public ProductFileRepository(String filePath) {
        super(filePath);
    }

    @Override
    protected String toFile(Product obj) {
        // Assuming that User class has a type (Client, Manager, etc.)
        // This will handle serialization of the common fields (id, name, email, etc.)
        String prodType = obj.getType();
        switch (prodType) {
            case "Dessert":
                return obj.getId() + "," + obj.getProductName() + "," +
                        obj.getProductPrice() + "," + obj.getAllergens()+ "," + obj.getType();
            case "Drink":
                Drinks drink = (Drinks) obj;
                return obj.getId() + "," + obj.getProductName() + "," +
                        obj.getProductPrice() + "," + drink.getVolume() + "," + obj.getType();
            case "Main":
                MainDish mainDish = (MainDish) obj;
                return obj.getId() + "," + obj.getProductName() + "," +
                        obj.getProductPrice() + "," + mainDish.getCalories() + "," + mainDish.getSize() + "," + obj.getType();
            case "Side":
                SideDish sideDish = (SideDish) obj;
                return obj.getId() + "," + obj.getProductName() + "," +
                        obj.getProductPrice() + "," + sideDish.getSize() + "," + obj.getType();
            default:
                throw new IllegalArgumentException("Unknown user type: " + prodType);
        }
//        return obj.getId() + "," + obj.getEmail() + "," + obj.getName() + "," + obj.getPassword() + "," + obj.getUserType();
    }

    @Override
    protected Product fromFile(String data) {
        // Split the data string into parts based on comma separator
        String[] parts = data.split(",");

        // Extract individual fields
        String userType = parts[parts.length - 1]; // Manager, Client, etc.
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        int price = Integer.parseInt(parts[2]);
        // Instantiate the specific subclass based on the userType
        switch (userType) {
            case "Dessert":
                Allergens allergens = Allergens.valueOf(parts[3]);
                return new Desserts(name, price, allergens, id);
            case "Drink":
                DrinkVolume vol = DrinkVolume.valueOf(parts[3]);
                return new Drinks(name, price, vol, id);
            case "Main":
                int calories = Integer.parseInt(parts[3]);
                DishSize msize = DishSize.valueOf(parts[4]);
                return new MainDish(name, price, calories, msize, id);
            case "Side":
                DishSize dsize = DishSize.valueOf(parts[3]);
                return new SideDish(name, price, dsize, id);
            default:
                throw new IllegalArgumentException("Unknown user type: " + userType);
        }
    }

}
