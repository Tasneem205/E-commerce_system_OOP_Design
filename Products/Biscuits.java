package Products;

import Interfaces.Expirable;
import java.time.LocalDate;

public class Biscuits extends Product implements Expirable {
    private final LocalDate expiryDate;

    public Biscuits(String name, Double price, Integer quantity, LocalDate expire) {
        super(name, price, quantity);
        this.expiryDate = expire;
    }

    @Override
    public Boolean isExpired() {
        return this.expiryDate.isBefore(LocalDate.now());
    }
}
