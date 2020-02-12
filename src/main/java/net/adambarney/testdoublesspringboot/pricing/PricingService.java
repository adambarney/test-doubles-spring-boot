package net.adambarney.testdoublesspringboot.pricing;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PricingService {
    private InventoryRepositoryInterface inventoryRepository;
    private KBBClientInterface kbbClient;
    private TagPrinterInterface tagPrinter;

    public PricingService(InventoryRepositoryInterface inventoryRepository,
                          KBBClientInterface kbbClient, TagPrinterInterface tagPrinter) {
        this.inventoryRepository = inventoryRepository;
        this.kbbClient = kbbClient;
        this.tagPrinter = tagPrinter;
    }


    public List<Vehicle> updatePrice() {
        final List<Vehicle> currentInventory = inventoryRepository.getCurrentInventory();
        currentInventory.forEach(vehicle -> {
            final int updatedPrice = kbbClient.getUpdatedPrice(currentInventory.get(0).getVin());
            if (Math.abs(vehicle.getPrice() - updatedPrice) >= 5) {
                vehicle.setPrice(updatedPrice);
                inventoryRepository.updateVehicle(vehicle);
                tagPrinter.print(vehicle);
            }
        });

        return currentInventory;
    }
}
