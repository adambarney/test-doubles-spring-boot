package net.adambarney.testdoublesspringboot.pricing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

public class PricingServiceTest {

    private PricingService pricingService;

    @BeforeEach
    void beforeEach() {
        pricingService = new PricingService();
    }

 
}
