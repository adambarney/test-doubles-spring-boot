package net.adambarney.testdoublesspringboot.pricing;

import java.util.ArrayList;
import java.util.List;

public class TagPrinterSpy implements TagPrinterInterface {
    List<Vehicle> vehicleList = new ArrayList<>();
    @Override
    public void print(Vehicle vehicle) {
        vehicleList.add(vehicle);
    }


    public List<Vehicle> getArguments() {
        return vehicleList;
    }
}
