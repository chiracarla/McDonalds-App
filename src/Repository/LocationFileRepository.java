package Repository;

import Enums.Allergens;
import Enums.Locations;
import Enums.ManagerRank;
import Model.Desserts;
import Model.Location;
import Model.Manager;

/**
 * The {@code LocationFileRepository} class extends the {@code FileRepository} class
 */
public class LocationFileRepository extends FileRepository<Location> {

    public LocationFileRepository(String filePath) {
        super(filePath);
    }

    /**
     * Converts a {@code Location} entity to a string representation for storage.
     * @param obj the entity to convert
     * @return
     */
    @Override
    protected String toFile(Location obj) {
        return  obj.getId() + "," + obj.getStoreLocation() + "," + + obj.getStoreManager().getId()+ "," + obj.getStoreManager().getEmail() + "," +
                obj.getStoreManager().getName() + "," + obj.getStoreManager().getPassword() + "," +
                obj.getStoreManager().getRank();
    }

    /**
     * Converts a string representation of a {@code Location} entity to an actual entity.
     * @param data the string representation of the entity
     * @return
     */
    @Override
    protected Location fromFile(String data) {
        String[] parts = data.split(",");
        int locId = Integer.parseInt(parts[0]);
        Locations loc = Locations.valueOf(parts[1]);
        String manEmail = parts[3];
        String manName = parts[4];
        String manPassword = parts[5];
        ManagerRank rank = ManagerRank.valueOf(parts[6]);
        int manID = Integer.parseInt(parts[2]);
        return new Location(loc, new Manager(manEmail, manName, manID, manPassword, rank), locId);
    }
}
