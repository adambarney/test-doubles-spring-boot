package net.adambarney.testdoublesspringboot.pricing;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InventoryRepository implements InventoryRepositoryInterface {
    @Override
    public List<Vehicle> getCurrentInventory() {
        return null;
    }

    @Override
    public void updateVehicle(Vehicle vehicle) {

    }

    @Override
    public void insertVehicle(Vehicle vehicle) {

    }
}
