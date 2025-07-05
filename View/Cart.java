package View;

import java.util.ArrayList;

public class Cart {
    private ArrayList<CartItem> cartList;
    public Cart() {
        this.cartList = new ArrayList<>();
    }

    public ArrayList<CartItem> addItem(CartItem newItem) {
        if (newItem.getQuantity() <= newItem.prod.getQuantity()) {
            for (CartItem i : this.cartList) {
                if (i.prod.getName().equals(newItem.prod.getName())) {
                    i.setQuantity(i.getQuantity() + newItem.getQuantity());
                    return this.cartList;
                }
            }
            cartList.add(newItem);
        } else {
            System.out.println("Item can't be added to cart (quantity shortage)");
        }
        return this.cartList;
    }

    public ArrayList<CartItem> removeItem(CartItem oldItem) {
        // find product in the cart, if found decrease until 0 then remove from list
        for (int i = 0; i < cartList.size(); i++) {
            CartItem cur = cartList.get(i);
            if (cur.prod.getName().equals(oldItem.prod.getName())) {
                int updatedQty = cur.getQuantity() - oldItem.getQuantity();
                if (updatedQty > 0) {
                    cur.setQuantity(updatedQty);
                } else {
                    cartList.remove(i);
                }
                break;
            }
        }
        return this.cartList;
    }

    public ArrayList<CartItem> getCart() {
        return cartList;
    }

    public Double getTotal() {
        double tot = 0.0;
        for (CartItem i : this.cartList) {
            tot += i.getTotalPrice();
        }
        return tot;
    }
}
