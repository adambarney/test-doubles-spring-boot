package net.adambarney.testdoublesspringboot.pricing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    private String vin;
    private Integer price;
}
