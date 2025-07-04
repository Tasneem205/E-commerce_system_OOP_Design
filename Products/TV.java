package Products;

import Interfaces.Shippable;

import java.time.LocalDate;

public class TV extends Product implements Shippable {
    private Double weight;
    private Double shippingFee;

    TV(String name, Double price, Integer quantity, Double weight, Double fee) {
        super(name, price, quantity);
        this.weight = weight;
        this.shippingFee = fee;
    }

    public Double calculateShippingFee() {
        // add any business logic
        return (weight/1000.0) * shippingFee;
    }

    @Override
    public Double getWeight() {
        return weight;
    }
}
