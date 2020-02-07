package net.adambarney.testdoublesspringboot.pricing;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PricingService {
    private InventoryRepository inventoryRepository;
    private KBBClient kbbClient;

    public PricingService(InventoryRepository inventoryRepository, KBBClient kbbClient) {
        this.inventoryRepository = inventoryRepository;
        this.kbbClient = kbbClient;
    }


    public List<Vehicle> updatePrice() {
        return null;
    }
}
