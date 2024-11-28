package Model;

import Enums.Locations;

public class Location implements HasId {
    private Locations storeLocation;
    private Manager storeManager;
    int id;
    /**
     * Constructs a Location with the specified ID and address.
     *
     * @param location the name of the location
     * @param manager the manager of the location
     */
    public Location(Locations location, Manager manager, int id) {
        this.storeLocation = location;
        this.storeManager = manager;
        this.id = id;
    }

    public Locations getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(Locations storeLocation) {
        this.storeLocation = storeLocation;
    }

    public Manager getStoreManager() {
        return storeManager;
    }

    public void setStoreManager(Manager storeManager) {
        this.storeManager = storeManager;
    }

    @Override
    public String toString() {
        return "Location{" +
                "storeLocation=" + storeLocation +
                ", storeManager=" + storeManager +
                '}';
    }

    @Override
    public Integer getId() {
        return id;
    }

//    @Override
//    public String toFile() {
//        return storeLocation + "," + storeManager;
//    }
}
