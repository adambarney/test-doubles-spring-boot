package net.adambarney.testdoublesspringboot.pricing;

import java.util.List;

public interface InventoryRepositoryInterface {
    List<Vehicle> getCurrentInventory();
    void updateVehicle(Vehicle vehicle);
    void insertVehicle(Vehicle vehicle);
}
