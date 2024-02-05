public class Electronics extends Product implements java.io.Serializable
{
    private String brand;
    private int warrantyPeriod;

    public Electronics(String productId,String productName,int noOfAvailableItem,double price,String brand,int warrantyPeriod)
    {
        super(productId,productName,noOfAvailableItem,price);
        this.brand=brand;
        this.warrantyPeriod=warrantyPeriod;

    }

    public String getBrandName() {
        return brand;
    }

    public void setBrandName(String brandName) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public  String toString() {
        return "-----Electronics------" +"\n"+
                "productID= " + productId +"\n"+
                "productName= " + productName + "\n" +
                "noOfAvailableItem= " + noOfAvailableItem +"\n" +
                "price= " + price +"\n" +
                "brandName= " + brand +"\n" +
                "warrantyPeriod= " + warrantyPeriod ;
    }

}

