
import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;


public class CustomerGUI extends WestminsterShoppingManager {

    JTable table;
    JTable table2;

    JPanel p1;
    JPanel p2;
    JScrollPane scrollPane;
    JScrollPane scrollPane2;
    ArrayList<Product> filteredList;
    ProductsTableModel tableModel;
    JLabel productLabel;

    JComboBox productsType;

    cartTableModel myCartTable;
    Frame2 frame2;
    JPanel p3;
    JPanel p4;

    shoppingCart myShoppingCart;
    private User user;



    private void updateTable(String selectedType) {
        productList = readFile();
        filteredList = new ArrayList<>();
        if (selectedType.equals("All")) {
            filteredList.addAll(productList);
            System.out.println(filteredList.size());
//            System.out.println(" selected All");
        } else {
            for (Product product : productList) {
                if (product.getClass().getSimpleName().equalsIgnoreCase(selectedType)) {
                    filteredList.add(product);
                    System.out.println(filteredList.size());
//                    System.out.println("selected pro");
                }
            }
        }
        if (scrollPane != null) {
            p1.remove(scrollPane);
        }

        // Create model with filtered list
        tableModel = new ProductsTableModel(filteredList);

        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(250, 120, 850, 200);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.darkGray, 2));
        table.setGridColor(Color.darkGray);


        int i;
        int j;
        for (i = 0; i < 6; i++) {
            for (j = 0; j < filteredList.size(); j++) {
                tableModel.getValueAt(j, i);
            }
        }
        table.getColumnModel().getColumn(0).setCellRenderer(new MyTableCellRenderer());
        table.getSelectionModel().addListSelectionListener(rowListener);
        table.getTableHeader().setBackground(Color.LIGHT_GRAY);
        scrollPane.setVisible(true);
        p1.add(scrollPane);


        p1.revalidate();
        p1.repaint();


    }



    ListSelectionListener rowListener = new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent event) {
            if (table != null) {
                if (!event.getValueIsAdjusting()) {
                    int viewRow = table.getSelectedRow();
                    if (viewRow != -1) {
                        int modelRow = table.convertRowIndexToModel(viewRow);
                        Product product = tableModel.tableProductList.get(modelRow);
                        updateProductLabel(product); // method to update label
//                        System.out.println("not null");
                    } else {
                        clearProductLabel(); // Clear label if no row is selected

                    }
                } else System.out.println("no use");
            }
        }


        private void updateProductLabel(Product product) {

            if (productLabel != null) { // Remove existing label if present
                p2.remove(productLabel);
            }

            if (product instanceof Electronics) {
                productLabel = new JLabel("<html>------------------------<br/><br/>Selected Product Information <br/><br/> Product ID : " + product.getProductID() + "<br/><br/>Product name: " + product.getProductName() +
                        " <br/><br/>Product Category: " + product.getClass().getSimpleName() + " <br/><br/>Brand name: " + ((Electronics) product).getBrandName() + " <br/><br/>Warranty period (Months): " + ((Electronics) product).getWarrantyPeriod()
                        + " <br/><br/>No of available items: " + product.getNoOfAvailableItem() + "<br/><br/> -------------------------</html>", SwingConstants.CENTER);
                //System.out.println("ele row");

            } else if (product instanceof Clothing) {
                productLabel = new JLabel("<html>------------------------<br/><br/>Selected Product Information <br/><br/> Product ID : " + product.getProductID() + "<br/><br/>Product name: " + product.getProductName() +
                        " <br/><br/>Product Category: " + product.getClass().getSimpleName() + " <br/><br/>Size: " + ((Clothing) product).getSize() + " <br/><br/>Colour : " + ((Clothing) product).getColour()
                        + " <br/><br/>No of available items: " + product.getNoOfAvailableItem() + "<br/><br/> -------------------------</html>", SwingConstants.CENTER);
                //System.out.println("ele row");
                //System.out.println("clo row");
            }
            productLabel.setBounds(400, 350, 400, 200);
            productLabel.setVisible(true);
            p2.add(productLabel);

            p2.revalidate(); // sure label is displayed
        }




        private void clearProductLabel() {
            if (productLabel != null) {
                p2.remove(productLabel);
                productLabel = null;
                p2.revalidate();
            }
        }
    };

    ActionListener cartActionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (table != null) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int modelRow = table.convertRowIndexToModel(selectedRow);
                    Product selectedProduct = tableModel.tableProductList.get(modelRow);

                    myShoppingCart = user.getMyCart();
                    myShoppingCart.addToCart(selectedProduct);
                    //System.out.println("buttonclicke "+myShoppingCart.getCart().size());
                    saveFile();
                    updateTable(productsType.getSelectedItem().toString());


                }
            } else
                System.out.println("no table");

        }
    };

    ActionListener cbActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedType = (String) productsType.getSelectedItem();
            updateTable(selectedType);
            //System.out.println("ActionEvent");

        }
    };


    ItemListener cbItemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedType = (String) productsType.getSelectedItem();
                updateTable(selectedType);
            }
        }
    };
    private void updateCartTable()
    {


        myCartTable = new cartTableModel(myShoppingCart); // Create model with filtered list

        table2 = new JTable(myCartTable);
        scrollPane2 = new JScrollPane(table2);
        scrollPane2.setBounds(50, 50, 600, 300);
        scrollPane2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        table2.setGridColor(Color.BLACK);
//        System.out.println("my shopping cart size"+myShoppingCart.getCart().size());



        int i;
        int j;
        for (i = 0; i < 3; i++) {
            for (j = 0; j <myShoppingCart.getCart().size() ; j++) {
                myCartTable.getValueAt(j, i);
            }
        }

        table2.getTableHeader().setBackground(Color.LIGHT_GRAY);
        scrollPane2.setVisible(true);
        p3.add(scrollPane2,BorderLayout.CENTER);


        p3.revalidate();
        p3.repaint();

    }

    private void finalOutput(User user) {
        double discount1=0;
        double discount2=0;

        JLabel text1 = new JLabel(" Total :                                                                         "+ myShoppingCart.totalAmount());


        p4.add(text1);
        if (user.getNewCustomer() == true) {
            discount1=myShoppingCart.totalAmount() * 0.1;
            JLabel text2 = new JLabel(" First Purchase discount (10%) :                                   -" + discount1);
            p4.add(text2);
        }



        for (CartItem cartItem : user.getMyCart().getCart())
        {
            int productCount = cartItem.getCount();
            if (productCount >= 3) {
                discount2=myShoppingCart.totalAmount() * 0.2;

                JLabel text3 = new JLabel(" Three items in the same Category Discount (20%) :   -" + discount2);
                p4.add(text3);
                break;
            }

        }


        JLabel text4 = new JLabel(" Final Total :                                                                 " + (myShoppingCart.totalAmount() - myShoppingCart.totalAmount() * 0.1 - myShoppingCart.totalAmount() * 0.2));

        text4.setBounds(250,650,100,40);
        p4.add(text4);

    }



    public class frame extends JFrame {
        public frame() {
            setTitle("Westminster Shopping center");
            setLayout(new BorderLayout());
            setSize(1500, 1200);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            JButton shoppingCartBtn = new JButton("Shopping Cart");
            shoppingCartBtn.setBounds(1250, 25, 150, 50);
            p1 = new JPanel();
            p1.setLayout(null);
            p2 = new JPanel();


            shoppingCartBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame2 = new Frame2();
                    p3=new JPanel();

                    p4=new JPanel(new GridLayout(8, 8));
                    p4.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
                    frame2.setLocationRelativeTo(null);
                    frame2.add(p3, BorderLayout.CENTER);
                    frame2.add(p4, BorderLayout.SOUTH);
                    if(myShoppingCart!=null){
                        if (table2 != null) {
                            p3.remove(table2);
                        }
                        updateCartTable();
                        finalOutput(user);

                        frame2.setVisible(true);}
                }
            });
            p1.add(shoppingCartBtn);


        }
    }


    public  CustomerGUI(User user) {

        this.user = user;
        frame frame1 = new frame();




        JLabel text = new JLabel("Select Product Category");
        text.setBounds(500, 35, 200, 100);
        p1.add(text);


        String productTypeList[] = {"All", "Electronics", "Clothing"};
        productsType = new JComboBox<>(productTypeList);
        productsType.setSelectedIndex(0);
        productsType.setBounds(700, 40, 200, 50);
        p1.add(productsType);




        productsType.addActionListener(cbActionListener);
        productsType.addItemListener(cbItemListener);



        p2.setBackground(Color.GRAY);

        JButton btn = new JButton("Add to shopping cart");
        btn.setSize(30, 50);
        btn.setBounds(800, 100, 200, 100);
        btn.addActionListener(cartActionListener);
        p2.add(btn);


        frame1.add(p1, BorderLayout.CENTER);
        frame1.add(p2, BorderLayout.SOUTH);


        frame1.setVisible(true);
        updateTable(productsType.getSelectedItem().toString());

    }

    class Frame2 extends JFrame {
        public Frame2() {
            setSize(900, 700);
            setLayout(new BorderLayout());
            setTitle("Shopping cart");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        }
    }


}

