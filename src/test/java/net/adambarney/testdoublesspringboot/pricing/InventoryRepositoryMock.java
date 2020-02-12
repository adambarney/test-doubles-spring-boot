package net.adambarney.testdoublesspringboot.pricing;

import java.util.ArrayList;
import java.util.List;

public class InventoryRepositoryMock implements InventoryRepositoryInterface {
    private List<Vehicle> vehicles = new ArrayList<>();
    private int count = 0;

    @Override
    public List<Vehicle> getCurrentInventory() {
        return vehicles;
    }

    @Override
    public void updateVehicle(Vehicle vehicle) {
        count++;
    }

    public Integer getNumberOfUpdates() {
        return count;
    }

    @Override
    public void insertVehicle(Vehicle vehicle) {

    }

    public void setState(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
