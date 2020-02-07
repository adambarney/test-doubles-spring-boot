package net.adambarney.testdoublesspringboot.pricing;

import org.junit.jupiter.api.BeforeEach;

public class PricingServiceTest {

    private PricingService pricingService;

    @BeforeEach
    void beforeEach() {
        pricingService = new PricingService();
    }
}
