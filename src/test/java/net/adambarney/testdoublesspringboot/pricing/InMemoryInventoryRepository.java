package net.adambarney.testdoublesspringboot.pricing;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryInventoryRepository implements InventoryRepositoryInterface {
    // AKA: Fake

    Map<String, Integer> inventory = new HashMap<>();

    @Override
    public List<Vehicle> getCurrentInventory() {
        return inventory.entrySet().stream()
            .map(entry -> Vehicle.builder().price(entry.getValue()).vin(entry.getKey()).build())
            .collect(Collectors.toList());
    }

    @Override
    public void updateVehicle(Vehicle vehicle) {
        inventory.put(vehicle.getVin(), vehicle.getPrice());

    }

    @Override
    public void insertVehicle(Vehicle vehicle) {
        if (!inventory.containsKey(vehicle.getVin())) {
            inventory.put(vehicle.getVin(), vehicle.getPrice());
        }
    }
}
