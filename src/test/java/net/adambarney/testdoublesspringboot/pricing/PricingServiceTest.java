package net.adambarney.testdoublesspringboot.pricing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PricingServiceTest {

    private PricingService pricingService;
    @Mock
    private KBBClient kbbClient;
    @Mock
    private InventoryRepository inventoryRepository;
    @Mock
    private TagPrinter tagPrinter;

    @BeforeEach
    void setup() {
        this.pricingService = new PricingService(inventoryRepository, kbbClient, tagPrinter);
    }

    @Test
    void pricingService_updatesPrice_whenNewPriceIsFiveDollarsHigher() throws Exception {
        List<Vehicle> currentInventory = List.of(Vehicle.builder().vin("123").price(5000).build());
        when(inventoryRepository.getCurrentInventory()).thenReturn(currentInventory);
        when(kbbClient.getUpdatedPrice("123")).thenReturn(5005);
        final List<Vehicle> vehicles = pricingService.updatePrice();
        final Vehicle actualVehicle = vehicles.get(0);
        assertThat(actualVehicle.getPrice()).isEqualTo(5005);
    }

    @Test
    void pricingService_doesNotChangePrice_whenNewPriceIsFourDollarsHigher() throws Exception {

        final List<Vehicle> vehicles = List.of(Vehicle.builder().vin("123").price(5000).build());
        when(inventoryRepository.getCurrentInventory()).thenReturn(vehicles);
        when(kbbClient.getUpdatedPrice(any())).thenReturn(5004);
        final List<Vehicle> actualVehicles = pricingService.updatePrice();
        final Vehicle actualVehicle = actualVehicles.get(0);
        assertThat(actualVehicle.getPrice()).isEqualTo(5000);
    }

    @Test
    void pricingService_updatesPriceInDatabase_whenPriceChanges() throws Exception {
        final Vehicle vehicle1 = Vehicle.builder().vin("123").price(8000).build();
        final Vehicle vehicle2 = Vehicle.builder().vin("321").price(9000).build();
        when(inventoryRepository.getCurrentInventory()).thenReturn(asList(vehicle1, vehicle2));
        when(kbbClient.getUpdatedPrice("123")).thenReturn(8000);
        when(kbbClient.getUpdatedPrice("321")).thenReturn(8769);

        pricingService.updatePrice();
        final List<Vehicle> vehicles = inventoryRepository.getCurrentInventory();
        assertThat(vehicles.get(0).getPrice()).isEqualTo(8000);
        assertThat(vehicles.get(1).getPrice()).isEqualTo(8769);
    }

    @Test
    void pricingService_printsTag_whenPriceChanges() throws Exception {
        final Vehicle vehicle1 = Vehicle.builder().vin("123").price(8000).build();
        final Vehicle vehicle2 = Vehicle.builder().vin("321").price(9000).build();
        when(inventoryRepository.getCurrentInventory()).thenReturn(asList(vehicle1, vehicle2));
        when(kbbClient.getUpdatedPrice("123")).thenReturn(8000);
        when(kbbClient.getUpdatedPrice("321")).thenReturn(8769);

        pricingService.updatePrice();
        verify(inventoryRepository, times(1)).updateVehicle(any());
//        assertThat(inventoryRepositoryMock.getNumberOfUpdates()).isEqualTo(1);
        final ArgumentCaptor<Vehicle> vehicleArgumentCaptor = ArgumentCaptor.forClass(Vehicle.class);
        verify(tagPrinter, times(1)).print(vehicleArgumentCaptor.capture());
        final List<Vehicle> printedVehicles = vehicleArgumentCaptor.getAllValues();
        assertThat(printedVehicles.size()).isEqualTo(1);
        assertThat(printedVehicles.get(0).getPrice()).isEqualTo(8769);
        assertThat(printedVehicles.get(0).getVin()).isEqualTo("321");
    }

}
