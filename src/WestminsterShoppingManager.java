import java.awt.event.ActionEvent;
import java.util.*;
import java.io.*;
import java.util.Collections;
public  class WestminsterShoppingManager implements ShoppingManager,java.io.Serializable
{
    private static final long serialVersionUID = 1L;
    ArrayList<Product> productList = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    static int productsCount;
    String productID = "";

    ArrayList<User> userList=new ArrayList<>();

    shoppingCart myShoppingCart ;

    public static int intInputValidation(Scanner scanner, String prompt) {
        int userInput = 0;
        boolean Input = false;

        while (!Input) {
            try {
                System.out.print(prompt);
                userInput = input.nextInt();
                Input = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid integer.");
                input.next();
            }
        }
        return userInput;
    }

    public String productIdValidation() {
        boolean Input1 = false;
        while (!Input1) {
            System.out.print("Enter the Product ID(Eg: A01-A50):  ");
            if (input.hasNextInt()) {
                System.out.println("Error: Please enter a correct input.");
                input.next();
            } else {
                productID = input.next().toUpperCase();
                if (productID.charAt(0) != 'A' || productID.length() != 3 || !(Character.isDigit(productID.charAt(1)) && Character.isDigit(productID.charAt(2)))) {
                    System.out.println("Error: Please enter a correct input.");
                } else {
                    Input1 = true;
                }
            }
        }
        return productID;

    }
    public void addProduct() {

        productsCount=productList.size();
        if (productsCount < 50) {
            productID = productIdValidation();
            System.out.print("Enter the product name: ");
            String productName = input.next();
            int noOfItems = intInputValidation(input, "Enter the no.of items: ");

            boolean Input2 = false;
            double price = 0.0;
            while (!Input2) {
                System.out.print("Enter the price of the product:  ");
                if (input.hasNextDouble()) {
                    price = input.nextDouble();
                    Input2 = true;
                } else {
                    System.out.println("Error: Please enter a correct value");
                    input.next();
                }
            }

            boolean Input3 = false;
            System.out.println("\nOption for electronics is 1 \n Option for clothing is 2 \n ");
            Product p = null;
            while (!Input3) {
                int option = intInputValidation(input, "Enter the product type: ");
                if (option == 1) {
                    System.out.print("Enter the brand name: ");
                    String brandName = input.next();
                    int warrantyPeriod = intInputValidation(input, "Enter the warrant period(No.of Months): ");
                    p = new Electronics(productID, productName, noOfItems, price, brandName, warrantyPeriod);
                    Input3 = true;
                } else if (option == 2) {
                    boolean Input4 = false;
                    int i;
                    String size = " ";
                    String[] sizeList = {"S", "M", "L", "XL", "XXL"};
                    while (!Input4) {
                        System.out.print("\nEnter the dress size(S,M,L,XL,XXL): ");
                        size = input.next().toUpperCase();
                        for (i = 0; i < 5; i++) {
                            if (sizeList[i].equals(size)) {
                                Input4 = true;
                                break;
                            }
                        }
                        if (!Input4) {
                            System.out.print("Error: Please enter a  correct input.");
                        }
                    }
                    System.out.print("Enter the colour:  ");
                    String colour = input.next();
                    p = new Clothing(productID, productName, noOfItems, price, size, colour);
                    Input3 = true;
                } else {
                    System.out.println("Entered a invalid number.Please enter the correct number");
                }
            }
            productList.add(p);
            productsCount++;

        } else {
            System.out.println("50 products only can be added");

        }
    }

    public void deleteProduct() {
        productList=readFile();
        productID = productIdValidation();
        boolean Input6 =false;
        boolean itemAvailable=false;
        for(int i = 0; i < productList.size(); i++)
        {
            Product product = productList.get(i);
            if(productID.equals(product.getProductID())){
                itemAvailable=true;
                System.out.println(product.toString());
                while (!Input6) {
                    System.out.print("Do you want to delete the product(Y/N): ");
                    String answer = input.next().toUpperCase();
                    if (answer.equals("Y")) {
                        productList.remove(product);
                        System.out.println(productID + " is deleted.");
                        saveFile();
                        Input6=true;
                        break;
                    } else if (answer.equals("N") ){
                        System.out.println("Thank you");
                        Input6=false;
                        break;
                    }
                    else{
                        System.out.println("Enter the correct input");
                    }
                }

            }


        }if(!itemAvailable){
            System.out.println(productID+"is not available");
        }


    }

    public void printProduct()  {
        productList=readFile();
        Collections.sort(productList);
        for(Product product : productList){
            System.out.println(product.toString());
            System.out.println("----------------------");
        }
    }

    public ArrayList<Product> readFile(){

        if (Utility.fileExists("file.ser")) try
        {
            FileInputStream fileIn = new FileInputStream("file.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            productList = (ArrayList<Product>) in.readObject();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return productList;
    }

    public void saveFile()  {

        try{
            FileOutputStream fileOut = new FileOutputStream("file.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(productList);
            System.out.println("Products are saved successfully");
            out.close();


        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }


    public  void userVerify()
    {
        boolean Input8=false;
        while (!Input8) {
            System.out.println("Are you a new customer (Y/N): ");
            String result = input.next().toUpperCase();
            if (result.equals("Y")) {
                System.out.println("Create a Username and Password.");
                System.out.println("Create a username:  ");
                String username=input.next();
                System.out.println("Create a password: ");
                String password= input.next();
                User user=new User(username,password,true);
                userList.add(user);
                System.out.println(userList.get(0));
                myShoppingCart=new shoppingCart();
                user.setCart(myShoppingCart);
                userSaveFile();
                userPage(user);
                Input8=true;
                break;
            } else if (result.equals("N") ){
                userList=userFile();
                System.out.println(userList.get(0));
                System.out.println("Enter your username: ");
                String username= input.next();
                System.out.println("Enter your password: ");
                String password= input.next();
                boolean userThere=false;
                for( User u:userList){
                    if(u.getUserName().equals(username))
                    {
                        if((u.getPassword().equals(password) ))
                        {   u.setNewCustomer(false);
                            myShoppingCart=new shoppingCart();
                            u.setCart(myShoppingCart);
                            userSaveFile();
                            System.out.println(u.getNewCustomer());
                            userThere=true;
                            userPage(u);
                            Input8=true;
                            break;}

                    }}
                if(!userThere){
                    System.out.println("User or password is wrong");
                    Input8=true;
                    break;
                }

            }
            else{
                System.out.println("Enter the correct input");
            }
        }

    }


    public ActionEvent userPage(User verifiedUser)
    {

        new CustomerGUI( verifiedUser);
        return null;
    }

    public void userSaveFile()  {

        try{
            FileOutputStream userFileOut = new FileOutputStream("user.ser");
            ObjectOutputStream userOut = new ObjectOutputStream(userFileOut);
            userOut.writeObject(userList);
            System.out.println("User details are saved successfully");
            userOut.close();


        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public ArrayList<User> userFile(){

        if (Utility.fileExists("user.ser")) try
        {
            FileInputStream userFileIn = new FileInputStream("user.ser");
            ObjectInputStream userIn = new ObjectInputStream(userFileIn);
            userList = (ArrayList<User>) userIn.readObject();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userList;
    }







    public static void main(String[] args) {

        ShoppingManager Manager = new WestminsterShoppingManager();
        boolean Input5 = false;
        while(!Input5)

        {
            System.out.println(" ");
            System.out.println("**---------Welcome to the Westminster online shop-----------**");
            System.out.println("**                                                          **");
            System.out.println("**     Add product enter ->                      :1         **");
            System.out.println("**     Remove product enter ->                   :2         **");
            System.out.println("**     Print the list of products enter ->       :3         **");
            System.out.println("**     Save in the file enter ->                 :4         **");
            System.out.println("**     Open GUI enter ->                         :5         **");
            System.out.println("**     Exit the program enter ->                 :6         **");
            System.out.println("*****************************************************************");
            int option = intInputValidation(input, "Please enter your option:  ");
            switch (option) {
                case (1):
                    Manager.addProduct();
                    break;
                case (2):
                    Manager.deleteProduct();
                    break;
                case (3):
                    Manager.printProduct();
                    break;
                case (4):
                    Manager.saveFile();
                    break;
                case (5):
                    Manager.userVerify();
                    break;
                case (6):
                    System.out.println("Ending........");
                    Input5 = true;
                    break;
                default:
                    System.out.println("Enter valid option");
                    break;
            }
        }
    }
}