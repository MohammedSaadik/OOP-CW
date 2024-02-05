import java.util.ArrayList;

public class shoppingCart  extends WestminsterShoppingManager{

    private ArrayList<CartItem> cart;
    //User user;
    //int count;
    //int totalCount=0;


    public shoppingCart()
    {
        this.cart = new ArrayList<>();
    }

    public ArrayList<CartItem> getCart() {
        return cart;
    }

    public void addToCart(Product item) {

        if (item.getNoOfAvailableItem() > 0) {
            boolean found = false;
            for (CartItem cartItem : cart) {
                if (cartItem.getProduct().getProductID().equals(item.getProductID())) {
                    cartItem.incrementCount();
                    item.noOfAvailableItem--;// Decrease availability
                    item.setNoOfAvailableItem(item.noOfAvailableItem);
                    found = true;
                    break;
                }
            }
            if (!found) {
                cart.add(new CartItem(item));
                item.noOfAvailableItem--;
                item.setNoOfAvailableItem(item.noOfAvailableItem);// Decrease availability
            }
        } else {
            System.out.println("The item is out of stock.");
        }
    }

    public double totalAmount() {
        double totalCost = 0.0;
        for (CartItem cartItem : cart) {
            totalCost += cartItem.getProduct().getPrice() * cartItem.getCount();
        }
        return totalCost;
    }
}






