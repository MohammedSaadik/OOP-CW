public abstract class Product implements java.io.Serializable, Comparable<Product>
{
    protected String productId;
    protected String productName;
    protected int noOfAvailableItem;
    protected double price;



    public Product(String productId,String productName,int noOfAvailableItem,double price)
    {
        this.productId=productId;
        this.productName=productName;
        this.noOfAvailableItem=noOfAvailableItem;
        this.price=price;

    }

    public Product() {

    }

    public String getProductID() {
        return productId;
    }

    public void setProductID(String productID) {
        this.productId = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getNoOfAvailableItem() {
        return noOfAvailableItem;
    }

    public void setNoOfAvailableItem(int noOfAvailableItem) {
        this.noOfAvailableItem = noOfAvailableItem;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String toString() {
        return "Product ID: " + productId +
                "\n Name: " + productName +
                "\nAvailable Items: " + noOfAvailableItem +
                "\n Price: " + price;
    }
    @Override
    public int compareTo(Product p){
        return this.productId.compareTo(p.productId);
    }
}
