package View;

import Interfaces.Shippable;

import java.util.ArrayList;

public class ShippingService {
    ArrayList<Shippable> items;
    ShippingService(ArrayList<Shippable> items) {
        this.items = items;
    }
    void applyForShipping() {
        System.out.println("Shipping service applied for your current items");
    }
}
