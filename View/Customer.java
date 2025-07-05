package View;

import Interfaces.Expirable;
import Interfaces.Shippable;
import Products.Product;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String name;
    private Double balance;
    public Cart cart;
    public Customer(String name, Double balance) {
        this.name = name;
        this.balance = balance;
        this.cart = new Cart();
    }

    public Double getBalance() {
        return this.balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void checkout() {
        if (cart.getCart().isEmpty()) {
            System.out.println("WARNING: Can't checkout\t cart is empty\n");
            return;
        }
        double subtotal = 0.0;
        double shippingFee = 0.0;
        double totalWeight = 0.0;
        ArrayList<Shippable> shippable = new ArrayList<>();
        System.out.println("** Shipment notice **");
        for (CartItem i : cart.getCart()) {
            if (i.prod instanceof Expirable exp) {
                if (exp.isExpired()) {
                    System.out.println("Product expired: " + i.prod.getName() + "\n");
                    cart.removeItem(i);
                    continue;
                }
            }
            subtotal += i.getTotalPrice();
            if (i.prod instanceof Shippable s) {
                shippable.add(s);
                shippingFee += s.calculateShippingFee();
                totalWeight += (s.getWeight() * i.getQuantity());
                System.out.println(i.getQuantity() + "x " + i.prod.getName() + "\t" + s.getWeight() + "g\n");
            }
        }
        totalWeight /= 1000;
        if (this.getBalance() < (shippingFee + subtotal)) {
            System.out.println("ERROR: Balance unable to cover cost");
            return;
        }
        this.setBalance(this.getBalance()-(shippingFee + subtotal));
        System.out.println("Total package weight " + totalWeight + "kg");

        // send shippable to shipping service
        System.out.println("Sending shipping information to the shipping service");
        ShippingService service = new ShippingService(shippable);
        service.applyForShipping();

        // loop for all items again
        for (CartItem i : cart.getCart()) {
            // all products are valid
            System.out.println(i.getQuantity() + "x " + i.prod.getName() + "\t" + i.prod.getPrice());
            i.prod.reduceQuantity(i.getQuantity());
        }
        System.out.println("---------------------------------------------");
        System.out.println("Subtotal\t" + subtotal);
        System.out.println("Shipping\t" + shippingFee);
        System.out.println("Amount\t" + (shippingFee + subtotal));
        System.out.println("Current Balance " + this.getBalance());
    }

    public void addToCart(Product prod, Integer quantity) {
        if (prod.getQuantity() >= quantity)
            this.cart.addItem(new CartItem(prod, quantity));
        System.out.println("WARNING: Unable to add " + prod.getName() + " to the cart\n not enough quantity\n");
    }

    public void removeFromCart(Product prod, Integer quantity) {
        this.cart.removeItem(new CartItem(prod, quantity));
    }
}
