package Products;

import Interfaces.Shippable;

import java.time.LocalDate;

public class TV extends Product implements Shippable {
    private Double weight;
    private Double shippingFee;

    public TV(String name, Double price, Integer quantity, Double weight, Double fee) {
        super(name, price, quantity);
        if (weight > 0 && weight < 250000)
            this.weight = weight;
        else {
            System.out.println("Invalid weight please insert weight > 0 and < 250kg");
        }
        this.shippingFee = fee;
    }

    public Double calculateShippingFee() {
        // add any business logic
        if (weight == 0) throw new Error("Weight can't be 0");
        return (weight / 1000.0) * shippingFee;
    }

    @Override
    public Double getWeight() {
        return weight;
    }
    public void setWeight(Double w) {
        this.weight = w;
    }
    public String getName() {
        return super.getName();
    }
}
