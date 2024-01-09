import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.util.*;;
public class ElectronicStoreView extends Pane {

    private ListView<String> mostpopularItems;
    private ListView<String> storeStock;
    private ListView<String> currentCart;
    private TextField Sales_number, revenue, Sale_profit;
    private Label store_summaryLabel, sales_numberLabel, revenueLabel, Sale_profitLabel, popularItemsLabel, current_cartLabel,store_stockLabel;
    private Button comp_saleButton,removeButton, addButton, resetButton;
    //private ArrayList<Product> current_products = new ArrayList<>();// list of current products in the cart


    public ElectronicStoreView(){
        // location and size of  buttons
        comp_saleButton = new Button("Complete Sale");
        comp_saleButton.setPrefSize(120, 37);
        comp_saleButton.relocate(560, 297);

        removeButton = new Button("Remove from Cart");
        removeButton.setPrefSize(120, 37);
        removeButton.relocate(440, 297);

        addButton = new Button("Add to Cart");
        addButton.setPrefSize(100, 40);
        addButton.relocate(255, 295);

        resetButton = new Button("Reset Store");
        resetButton.setPrefSize(110, 40);
        resetButton.relocate(40,295);

        // Set text, size and location on window for each label
        store_summaryLabel = new Label("Store Summary");
        store_summaryLabel.setPrefSize(100, 50);
        store_summaryLabel.relocate(50, 0);

        sales_numberLabel = new Label("# Sales");
        sales_numberLabel.setPrefSize(100, 50);
        sales_numberLabel.relocate(35, 28);

        revenueLabel = new Label("Revenue");
        revenueLabel.setPrefSize(105, 55);
        revenueLabel.relocate(27, 54);

        Sale_profitLabel = new Label("$ /Sale");
        Sale_profitLabel.setPrefSize(100, 50);
        Sale_profitLabel.relocate(33, 86);

        popularItemsLabel = new Label("Most Popular Items");
        popularItemsLabel.setPrefSize(120, 50);
        popularItemsLabel.relocate(40, 115);

        store_stockLabel= new Label("Store Stock");
        store_stockLabel.setPrefSize(100, 50);
        store_stockLabel.relocate(270, 0);

        current_cartLabel = new Label("Current Cart");
        current_cartLabel.setPrefSize(250, 50);
        current_cartLabel.relocate(510, 0);

        // Create ListViews, set their size and location on the window
        mostpopularItems= new ListView<String>();
        mostpopularItems.setPrefSize(160, 135);
        mostpopularItems.relocate(15, 155);

        storeStock = new ListView<String>();
        storeStock.setPrefSize(240, 250);
        storeStock.relocate(190, 40);

        currentCart = new ListView<String>();
        currentCart.setPrefSize(240, 250);
        currentCart.relocate(440, 40);


        Sales_number = new TextField();
        Sales_number.setPrefSize(95, 20);
        Sales_number.relocate(80, 40);

        revenue = new TextField();
        revenue.setPrefSize(95, 20);
        revenue.relocate(80, 70);

        Sale_profit = new TextField();
        Sale_profit.setPrefSize(95, 20);
        Sale_profit.relocate(80, 100);
        // adding everything to the pane
        getChildren().addAll( comp_saleButton,removeButton,addButton,resetButton,store_summaryLabel,sales_numberLabel,revenueLabel,Sale_profitLabel,popularItemsLabel,store_stockLabel,current_cartLabel,mostpopularItems,storeStock,currentCart,Sales_number,revenue,Sale_profit);
    }

    public Button getResetButton() {
        return resetButton;
    }

    public ListView<String> getCurr_Cart() {
        return currentCart;// list view of current products in the cart
    }
    public void viewStock(ElectronicStore store) {
        ArrayList<String> stocked_prod;
        stocked_prod = new ArrayList<>();
        for (Product prod : store.getStock()) { // get stock method is in store , returns the list of products in stock
            if (prod != null) { // it must not be null
                stocked_prod.add(prod.toString());
            }
        }
        storeStock.setItems(FXCollections.observableArrayList(stocked_prod));
    }
    public void viewCartStock(ElectronicStore store){
        ArrayList<String> cart_products = new ArrayList<>();
        double total=0.0;
        if (store.getProducts_inCart().isEmpty()){
            // incase the cart is empty the total remains 0
            current_cartLabel.setText("Current Cart: ($" + String.format("%.2f", total)+ ")");
        }
        //get the amount of each product present in the cart "x" the product itself and add to a list of strings
        for (Product p: store.getProducts_inCart()){
            //current_products.add(p);
            cart_products.add(p.getCart_quant() + "x" + p);
            // set the total of the cart in the cart label
            // amount of a given product* its price gives the money spent on that product
            // total+ money spent
            current_cartLabel.setText("Current Cart: ($" + String.format("%.2f", total+ p.getPrice() * p.getCart_quant()) + ")");
        }
        currentCart.setItems(FXCollections.observableArrayList(cart_products ));// fill the view  with the string list
        comp_saleButton.setDisable(cart_products.isEmpty()); // since there are no products in the cart the sale cannot be executed
    }
    public void viewPopularItems(ElectronicStore store){//method to update the popular items list view
        mostpopularItems.setItems(FXCollections.observableArrayList(store.sorted_Items()));
        //sorted items method returns the string list of top 3 customers
    }

    public void update(ElectronicStore store) {
        viewStock(store);// update the stocked  products being displayed
        viewCartStock(store); // update the view of products in cart
        viewPopularItems(store);
        // If no item has been selected, the button
        //should be disabled.
        addButton.setDisable(storeStock.getSelectionModel().getSelectedIndex() < 0);
        //the set disable takes a boolean as argument
        // if the selected index of the store stock array in the list view of stocks is negative , it implies nothing is selected which means true because 0 is false
        removeButton.setDisable(currentCart.getSelectionModel().getSelectedIndex() < 0);
        // if nothing is selected in the cart then nothing can be removed from it
    }


    public Button getSoldButton(){return comp_saleButton;}
    public Button getRemoveButton(){return removeButton;}
    public Button getAddButton(){return addButton;}
    public Button getComp_saleButton(){return comp_saleButton;}

    public ListView<String> getstoreStock() {
        return storeStock;
    }
    public TextField getSales_number() {
        return Sales_number;
    }
    public TextField getrevenue() {
        return revenue;
    }
    public TextField getSale_profit() {
        return Sale_profit;
    }

    public void default_setting (ElectronicStore store){
        Sales_number.setText("0");
        revenue.setText("0.00");
        Sale_profit.setText("N/A");
    }
}
