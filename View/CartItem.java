package View;

import Products.Product;

public class CartItem {
    protected Product prod;
    protected Integer quantity;
    public CartItem(Product prod, int quantity) {
        this.prod = prod;
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return prod.getPrice() * this.getQuantity();
    }
}
