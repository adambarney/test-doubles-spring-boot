package net.adambarney.testdoublesspringboot.pricing;

import java.util.LinkedList;
import java.util.Queue;

public class KBBClientConfigurableStub implements KBBClientInterface {
    private Queue<Integer> prices = new LinkedList<>();

    @Override
    public int getUpdatedPrice(String vin) {
        return prices.poll();
    }

    public KBBClientConfigurableStub setUpdatedPrice(int newPrice) {
        prices.add(newPrice);
        return this;
    }

}
