package Products;

public abstract class Product {
    private final String name;
    private final Double price;
    private Integer quantity;

    Product(String name, Double price, Integer quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }
    public void reduceQuantity(int diff) {
        this.quantity -= diff;
    }
    public Boolean isAvailable() {
        return quantity > 0;
    }
}
