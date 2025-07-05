package Products;
import Interfaces.Expirable;
import Interfaces.Shippable;

import java.time.LocalDate;

public class Cheese extends Product implements Expirable, Shippable {
    private final LocalDate expiryDate;
    private Double weight;
    private Double shippingFee;
    public Cheese(String name, Double price, Integer quantity, LocalDate expire, Double weight, Double fee) {
        super(name, price, quantity);
        this.expiryDate = expire;
        if (weight > 0 && weight < 250000)
            this.weight = weight;
        else {
            System.out.println("Invalid weight please insert weight > 0 and < 250kg");
        }
        this.shippingFee = fee;
    }

    @Override
    public Boolean isExpired() {
        return this.expiryDate.isBefore(LocalDate.now());
    }

    @Override
    public Double calculateShippingFee() {
        // add any business logic
        return (weight/1000.0) * shippingFee;
    }

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
