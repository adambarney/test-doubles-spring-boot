package net.adambarney.testdoublesspringboot.pricing;

import org.springframework.stereotype.Component;

@Component
public class KBBClient implements KBBClientInterface {
    @Override
    public int getUpdatedPrice(String vin) {
        return 0;
    }
}
