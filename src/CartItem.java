public class CartItem {
    private Product product;
    private int count;

    public CartItem(Product product) {
        this.product = product;
        this.count = 1; // Initialized to 1 when first added to cart
    }

    public Product getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }

    public void incrementCount() {
        count++;
    }
}
