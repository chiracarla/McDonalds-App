package Repository;

import Enums.Allergens;
import Enums.Locations;
import Enums.ManagerRank;
import Model.Desserts;
import Model.Location;
import Model.Manager;

public class LocationFileRepository extends FileRepository<Location> {

    public LocationFileRepository(String filePath) {
        super(filePath);
    }

    @Override
    protected String toFile(Location obj) {
        return  obj.getId() + "," + obj.getStoreLocation() + "," + + obj.getStoreManager().getId()+ "," + obj.getStoreManager().getEmail() + "," +
                obj.getStoreManager().getName() + "," + obj.getStoreManager().getPassword() + "," +
                obj.getStoreManager().getRank();
    }

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
