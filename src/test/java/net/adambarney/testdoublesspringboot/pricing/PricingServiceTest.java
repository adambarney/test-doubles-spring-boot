package net.adambarney.testdoublesspringboot.pricing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class PricingServiceTest {

    private PricingService pricingService;
    private KBBClientConfigurableStub kbbClient;
    private InventoryRepositoryInterface inventoryRepository;
    private TagPrinterSpy tagPrinter;

    @BeforeEach
    void setup() {
        kbbClient = new KBBClientConfigurableStub();
        inventoryRepository = new InMemoryInventoryRepository();
        tagPrinter = new TagPrinterSpy();
        this.pricingService = new PricingService(inventoryRepository, kbbClient, tagPrinter);
    }

    @Test
    void pricingService_updatesPrice_whenNewPriceIsFiveDollarsHigher() throws Exception {
        inventoryRepository.insertVehicle(Vehicle.builder().vin("123").price(5000).build());
        kbbClient.setUpdatedPrice(5005);
        final List<Vehicle> vehicles = pricingService.updatePrice();
        final Vehicle actualVehicle = vehicles.get(0);
        assertThat(actualVehicle.getPrice()).isEqualTo(5005);
    }

    @Test
    void pricingService_doesNotChangePrice_whenNewPriceIsFourDollarsHigher() throws Exception {
        inventoryRepository.insertVehicle(Vehicle.builder().vin("123").price(5000).build());
        kbbClient.setUpdatedPrice(5004);
        final List<Vehicle> vehicles = pricingService.updatePrice();
        final Vehicle actualVehicle = vehicles.get(0);
        assertThat(actualVehicle.getPrice()).isEqualTo(5000);
    }



    @Test
    void pricingService_updatesPriceInDatabase_whenPriceChanges() throws Exception {
        final Vehicle vehicle1 = Vehicle.builder().vin("123").price(8000).build();
        final Vehicle vehicle2 = Vehicle.builder().vin("321").price(9000).build();
        inventoryRepository.insertVehicle(vehicle1);
        inventoryRepository.insertVehicle(vehicle2);

        kbbClient.setUpdatedPrice(8000).setUpdatedPrice(8769);

        pricingService.updatePrice();
        final List<Vehicle> vehicles = inventoryRepository.getCurrentInventory();
        assertThat(vehicles.get(0).getPrice()).isEqualTo(8000);
        assertThat(vehicles.get(1).getPrice()).isEqualTo(8769);
    }

    @Test
    void pricingService_printsTag_whenPriceChanges() throws Exception {
        InventoryRepositoryMock inventoryRepositoryMock = new InventoryRepositoryMock();
        pricingService = new PricingService(inventoryRepositoryMock, kbbClient, tagPrinter);
        inventoryRepositoryMock.setState(List.of(Vehicle.builder().vin("123").price(8000).build(),
            Vehicle.builder().vin("321").price(9000).build()));
        kbbClient.setUpdatedPrice(8000).setUpdatedPrice(8769);

        pricingService.updatePrice();
        assertThat(inventoryRepositoryMock.getNumberOfUpdates()).isEqualTo(1);
        List<Vehicle> printedVehicles = tagPrinter.getArguments();
        assertThat(printedVehicles.size()).isEqualTo(1);
        assertThat(printedVehicles.get(0).getPrice()).isEqualTo(8769);
        assertThat(printedVehicles.get(0).getVin()).isEqualTo("321");
    }

}
