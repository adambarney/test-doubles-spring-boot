package net.adambarney.testdoublesspringboot.pricing;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PricingService {
    private InventoryRepository inventoryRepository;
    private KBBClient kbbClient;
    private TagPrinter tagPrinter;

    public PricingService(InventoryRepository inventoryRepository,
                          KBBClient kbbClient,
                          TagPrinter tagPrinter) {
        this.inventoryRepository = inventoryRepository;
        this.kbbClient = kbbClient;
        this.tagPrinter = tagPrinter;
    }


    public List<Vehicle> updatePrice() {
        final List<Vehicle> currentInventory = inventoryRepository.getCurrentInventory();
        currentInventory.forEach(vehicle -> {
            final int updatedPrice = kbbClient.getUpdatedPrice(vehicle.getVin());
            if (Math.abs(vehicle.getPrice() - updatedPrice) >= 5) {
                vehicle.setPrice(updatedPrice);
                inventoryRepository.updateVehicle(vehicle);
                tagPrinter.print(vehicle);
            }
        });

        return currentInventory;
    }
}
