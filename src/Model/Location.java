package Model;

import Enums.Locations;

public class Location implements HasId {
    private Locations storeLocation;
    private Manager storeManager;

    public Location(Locations location, Manager manager) {
        this.storeLocation = location;
        this.storeManager = manager;
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
    public Integer getId() {
        return 0;
    }//TODO: tb id
}
