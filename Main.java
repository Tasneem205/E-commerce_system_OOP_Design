import Products.*;
import View.*;
import Interfaces.Expirable;
import Interfaces.Shippable;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("**** Test cases ****");
//        testProductClasses();
//        testCartFunctionality();
        testCustomerCheckout();
        testEdgeCases();
        System.out.println("\nAll tests completed!");
    }
    private static void testProductClasses() {
        System.out.println("=== PRODUCT CLASS TESTS ===");

        // Test TV (Shippable)
        TV samsungTV = new TV("Samsung QLED", 999.99, 10, 15000.0, 2.5);
        assertTest("TV Name", "Samsung QLED", samsungTV.getName());
        assertTest("calculate shipping fee", ((Double)37.5), samsungTV.calculateShippingFee());
        assertTest("TV Weight", ((Double)15000.0), samsungTV.getWeight());
        assertTest("TV Availability", ((Boolean) true), samsungTV.isAvailable());

        // Test Cheese (Expirable & Shippable)
        Cheese cheddar = new Cheese("Aged Cheddar", 8.99, 15,
                LocalDate.now().plusDays(10), 500.0, 3.0);
        assertTest("Cheese Not Expired", ((Boolean) false), cheddar.isExpired());
        assertTest("Cheese Shipping Fee", ((Double) 1.5), cheddar.calculateShippingFee());

        Cheese expiredBrie = new Cheese("Brie", 12.99, 5,
                LocalDate.now().minusDays(5), 300.0, 3.0);
        assertTest("Expired Cheese", ((Boolean) true), expiredBrie.isExpired());

        // Test Biscuits (Expirable)
        Biscuits boreos = new Biscuits("Boreos", 3.49, 20, LocalDate.now().plusMonths(6));
        assertTest("Biscuits Not Expired", ((Boolean) false), boreos.isExpired());

        Biscuits staleCookies = new Biscuits("Stale Cookies", 1.99, 3,
                LocalDate.now().minusWeeks(2));
        assertTest("Expired Biscuits", ((Boolean) true), staleCookies.isExpired());

        // Test Mobile Products
        Mobile iphone = new Mobile("iPhone 15", 1099.99, 8);
        assertTest("Mobile Product Name", "iPhone 15", iphone.getName());

        MobileScratchCard card = new MobileScratchCard("$10 Credit", 9.99, 100);
        card.reduceQuantity(5);
        assertTest("Scratch Card Quantity", ((Integer) 95), card.getQuantity());
    }

    private static void testCartFunctionality() {
        System.out.println("\n=== CART FUNCTIONALITY TESTS ===");

        Product tv = new TV("LG OLED", 1299.99, 5, 18000.0, 2.0);
        Product cheese = new Cheese("Gouda", 7.49, 10,
                LocalDate.now().plusDays(14), 400.0, 3.5);
        Product biscuits = new Biscuits("Digestives", 2.99, 15,
                LocalDate.now().plusMonths(3));

        Cart cart = new Cart();
        CartItem tvItem = new CartItem(tv, 1);
        CartItem cheeseItem = new CartItem(cheese, 3);
        CartItem biscuitItem = new CartItem(biscuits, 2);

        // Test adding items
        cart.addItem(tvItem);
        cart.addItem(cheeseItem);
        cart.addItem(biscuitItem);
        assertTest("Cart Item Count", 3, cart.getCart().size());

        // Test quantity merging
        cart.addItem(new CartItem(cheese, 2));
        assertTest("Merged Cheese Quantity", ((Integer) 5), cart.getCart().get(1).getQuantity());

        // Test removing items
        cart.removeItem(new CartItem(cheese, 2));
        assertTest("After Partial Removal", ((Integer) 3), cart.getCart().get(1).getQuantity());

        cart.removeItem(new CartItem(cheese, 3));
        assertTest("After Full Removal", 2, cart.getCart().size());

        // Test total calculation
        double expectedTotal = 1299.99 + (2.99 * 2);
        assertTest("Cart Total", ((Double) expectedTotal), cart.getTotal());
    }

    private static void testCustomerCheckout() {
        System.out.println("\n=== CUSTOMER CHECKOUT TESTS ===");

        // Create products
        Product tv = new TV("Sony Bravia", 899.99, 3, 12000.0, 3.0);
        Product freshCheese = new Cheese("Mozzarella", 6.99, 8,
                LocalDate.now().plusDays(7), 250.0, 4.0);
        Product expiredCheese = new Cheese("Blue Cheese", 9.99, 4,
                LocalDate.now().minusDays(1), 350.0, 4.0);
        Product biscuits = new Biscuits("Shortbread", 4.49, 12,
                LocalDate.now().plusMonths(2));

        // Create customer
        Customer customer = new Customer("John Doe", 2000.00);

        // Add items to cart
        customer.addToCart(tv, 1);
        customer.addToCart(freshCheese, 2);
        customer.addToCart(expiredCheese, 1);  // Should be removed at checkout
        customer.addToCart(biscuits, 3);

        System.out.println("\n--- Valid Checkout Test ---");
        customer.checkout();

        // Verify balances and quantities
        Double balance = 2000.00 - (899.99 + (6.99*2) + (4.49*3) + (12*3.0) + (0.25*4.0*2));
        assertTest("Customer Balance", balance, customer.getBalance());
        assertTest("TV Quantity After Purchase", ((Integer) 2), tv.getQuantity());
        assertTest("Expired Cheese Removed", ((Integer) 4), expiredCheese.getQuantity());

        System.out.println("\n--- Insufficient Funds Test ---");
        Customer poorCustomer = new Customer("Jane Smith", 10.00);
        poorCustomer.addToCart(biscuits, 2);
        poorCustomer.checkout();
    }

    private static void testEdgeCases() {
        System.out.println("\n=== EDGE CASE TESTS ===");

        // Test zero/negative values
//        TV zeroWeightTV = new TV("Zero Weight TV", 499.99, 2, 0.0, 2.5);
//        assertTest("Zero Weight Shipping", ((Double) 0.0), zeroWeightTV.calculateShippingFee());

        // Test empty cart
        Customer customer = new Customer("Test User", 100.00);
        System.out.println("\n--- Empty Cart Test ---");
        customer.checkout();

        // Test product availability
        Product rareItem = new Mobile("Rare Phone", 1999.99, 1);
        customer.addToCart(rareItem, 2);  // More than available
        assertTest("Cart After Over-add", 0, customer.cart.getCart().size());

        // Test exact balance
        Customer exactBalanceCustomer = new Customer("Exact Balance", 50.00);
        Product testProduct = new MobileScratchCard("Test Card", 50.00, 10);
        exactBalanceCustomer.addToCart(testProduct, 1);
        System.out.println("\n--- Exact Balance Checkout ---");
        exactBalanceCustomer.checkout();
        assertTest("Exact Balance After Purchase", ((Double) 0.0), exactBalanceCustomer.getBalance());
    }

    // Helper assertion methods
    private static void assertTest(String testName, Object expected, Object actual) {
        System.out.printf("[%s] Expected: %s, Actual: %s -> ",
                testName, expected, actual);
        if (expected.equals(actual)) {
            System.out.println("PASS");
        } else {
            System.out.println("FAIL ❌");
        }
    }

    private static void assertTest(String testName, double expected, double actual) {
        final double EPSILON = 0.001;
        System.out.printf("[%s] Expected: %.2f, Actual: %.2f -> ",
                testName, expected, actual);
        if (Math.abs(expected - actual) < EPSILON) {
            System.out.println("PASS");
        } else {
            System.out.println("FAIL ❌");
        }
    }

    private static void assertTest(String testName, boolean expected, boolean actual) {
        System.out.printf("[%s] Expected: %s, Actual: %s -> ",
                testName, expected, actual);
        if (expected == actual) {
            System.out.println("PASS");
        } else {
            System.out.println("FAIL");
        }
    }
}
