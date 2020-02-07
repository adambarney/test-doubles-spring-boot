package net.adambarney.testdoublesspringboot.pricing;

import org.junit.jupiter.api.BeforeEach;

public class PricingServiceTest {

    private PricingService pricingService;

    @BeforeEach
    void setup() {
        this.pricingService = new PricingService(new InventoryRepository(), new KBBClient());
    }

}
