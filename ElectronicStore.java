//Class representing an electronic store
//Has an array of products that represent the items the store can sell

import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ElectronicStore {

    private ArrayList<Product> initial_stock; // list of the original stock when store is made
    private ArrayList<Product> Products_inCart; // list of the products that are put in cart
    //public final int MAX_PRODUCTS = 10; //Maximum number of products the store can have // not required anymore

    private int curProducts;
    private String name;
    private int sales; // number of times you sell something,number of sales you made
    private  ArrayList<Product> stock; //Array to hold all products
    // i changed it to array list
    private double revenue;

    public ElectronicStore(String initName) {
        sales=0;
        Products_inCart = new ArrayList<Product>();
        revenue = 0.0;
        name = initName;
        stock = new ArrayList<Product>();
        curProducts = 0;
        initial_stock = new ArrayList<Product>();


    }
    public int getSales() {
        return sales;
    }

    public String getName() {
        return name;
    }

    //Adds a product and returns true if there is space in the array
    //Returns false otherwise
    public boolean addProduct(Product p) {
        if(!stock.contains(p)) {
            stock.add(p);
            initial_stock.add(p);
            curProducts++;
            return true;
        }
        return false;
    }
    // method to add things inn cart ; will use in app when event is handeled
    public void add_tocart(Product p){// products in cart will represent the list of products in the cart
        if (p.getStockQuantity()==0){
            stock.remove(p);//If a product does not have any items left in stock (i.e., they have all been sold
                    //or added to the current cart), it should not be displayed in the Store Stock
            //ListView.
        }
        if (!Products_inCart.contains(p)){
            Products_inCart.add(p);
        }
        p.setStockQuantity(-1);//when a product is added to the cart, the amount of that product available in stock should decrease.
        revenue=revenue+p.getPrice();
        p.setCart_quant(1);// counting and setting the quantity of each product that has been added to the cart
    }
    public ArrayList<Product> getStock (){  // will be used in view class to display the stocked products
        return stock;
    }
    public ArrayList<Product> getProducts_inCart (){ // will be used in view class to display the  products in the cart
        return Products_inCart;
    }
    // method that removes things from cart
    // 1. update the revenue
    //2. add the product back to stock and remove from cart list
    //3. decrease the product amt in cart and increment in stock
    public void remove_fromCart(Product p){
        if (Products_inCart.contains(p)) {
            p.setCart_quant(-1);
            p.setStockQuantity(1);
            if(p.getCart_quant() == 0) {
                Products_inCart.remove(p);
                System.out.println("REMOVED");
                //view.getCart_products().remove(p);
            }
            revenue=revenue-p.getPrice();
            addProduct(p);
        }
    }
    public void checkoutCart(){// checks out the items in your cart, sell all the items in the cart
        for (Product p: Products_inCart){ // get the quantity of each product in the cart
            p.sellUnits(p.getCart_quant()); // sell units method in product class sells the quantity passed as the argument and updates the sold quantity and other attributes

            if (p.getCart_quant() != 0){ // if the quantity of the product is not 0 , set it
                p.setCart_quant(p.getCart_quant());
            }
        }
        Products_inCart.clear(); // the cart must be empty now
        sales++; // . You must update the ‘# Sales’ (increase by 1),
    }

    public static ElectronicStore createStore() {
        ElectronicStore store1 = new ElectronicStore("Watts Up Electronics");
        Desktop d1 = new Desktop(100, 10, 3.0, 16, false, 250, "Compact");
        Desktop d2 = new Desktop(200, 10, 4.0, 32, true, 500, "Server");
        Laptop l1 = new Laptop(150, 10, 2.5, 16, true, 250, 15);
        Laptop l2 = new Laptop(250, 10, 3.5, 24, true, 500, 16);
        Fridge f1 = new Fridge(500, 10, 250, "White", "Sub Zero", false);
        Fridge f2 = new Fridge(750, 10, 125, "Stainless Steel", "Sub Zero", true);
        ToasterOven t1 = new ToasterOven(25, 10, 50, "Black", "Danby", false);
        ToasterOven t2 = new ToasterOven(75, 10, 50, "Silver", "Toasty", true);
        store1.addProduct(d1);
        store1.addProduct(d2);
        store1.addProduct(l1);
        store1.addProduct(l2);
        store1.addProduct(f1);
        store1.addProduct(f2);
        store1.addProduct(t1);
        store1.addProduct(t2);
        return store1;
    }
    // the method will be used in view
    // sort the items based on which has the mostsold units

    public  ArrayList<String> sorted_Items(){
        ArrayList<String> popular_Items = new ArrayList<>();// the list which store 3  popular items
        // Compare prodcut 1 and 2, these products are in the intial stock list
        Collections.sort(initial_stock , new Comparator<Product>() {
            public int compare(Product p1, Product p2) {
                if (p1.getSoldQuantity() > p2.getSoldQuantity()){
                    return -1;
                }
                if (p1.getSoldQuantity() < p2.getSoldQuantity()){
                    return 1;
                }
                else{return 0;}
            }
        });

        // Add the top three items in popular_items list
        for (int i = 0; i < 3; i++) {
            popular_Items.add(initial_stock.get(i).toString());
        }
        return popular_Items;
    }


    public double getRevenue() {
        return revenue;
    }
}
