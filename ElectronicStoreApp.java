import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class ElectronicStoreApp extends Application {
    ElectronicStore my_store; // instance of ElectronicStore,
    public ElectronicStoreApp(){
        my_store = ElectronicStore.createStore();// the method is in electronic store to add the products and make a store
    }
    public void start(Stage primaryStage) {
        ElectronicStoreView view = new ElectronicStoreView();

        primaryStage.setTitle("Electronic Store Application : " + my_store.getName()); //  window title must include the name of the store.
        primaryStage.setResizable(false); //You should make the GUI window non-resizable
        primaryStage.setScene(new Scene(view, 750, 375)); //The ratio of the
        //window width/height should be approximately 2:1 (e.g., 800 wide, 400 high).

        //‘Add to Cart’, ‘Remove from Cart’, and ‘Complete Sale’ buttons should initially be
        //disabled.
        // since these are private variables in the view , we need get methods for the buttons
        view.getSoldButton().setDisable(true);
        view.getRemoveButton().setDisable(true);
        view.getAddButton().setDisable(true);

        view.default_setting(my_store);  // setting the initial default look of the window
        view.update(my_store);
        primaryStage.show();

        view.getstoreStock().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) { // handling the event of clicking on a item in stock view list
                view.update(my_store); // update the view
            }
        });
        // handling the event of clicking on a item in cart view list
        view.getCurr_Cart().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                view.update(my_store); // update the view
            }
        });

        // when add button is clicked , add to cart method in electronic store is called (make necessary changes there)
        view.getAddButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                int selected = view.getstoreStock().getSelectionModel().getSelectedIndex();
                if (selected >= 0){ //
                    my_store.add_tocart(my_store.getStock().get(selected));
                    // get the  product selected by the user from the stock list and that would be the argument for add to cart method in electronic store
                    view.update(my_store); // once the product button is clicked the product must be visible on the stock list view
                }
            }
        });
        // handles the event of removing things from cart , calls remove_fromCart in electronic store
        view.getRemoveButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                int selected = view.getCurr_Cart().getSelectionModel().getSelectedIndex();
                if (selected >= 0) { //
                    my_store.remove_fromCart(my_store.getProducts_inCart().get(selected));//remove the selected index from the cart
                    view.update(my_store);//update the view
                }
            }

        });

        view.getComp_saleButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                if(!(my_store.getProducts_inCart().isEmpty())){ //there is at least one product in the current cart
                    my_store.checkoutCart(); // method (in electronic store) to sell the cart items
                    view.getSale_profit().setText(String.format("%.2f", my_store.getRevenue() / my_store.getSales()));
                    view.getSales_number().setText(String.valueOf(my_store.getSales()));//updating all the text fields
                    view.getrevenue().setText(String.format("%.2f", my_store.getRevenue()));
                    view.update(my_store);//You must update the text fields

                }
            }
        });
         // When the ‘Reset Store’ button is clicked,the model should be reset to its intial state
        view.getResetButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                my_store = ElectronicStore.createStore(); //  electronic store model should be recreated)
                view.default_setting(my_store); //method in view which sets everything to inital default
                view.update(my_store); //
            }
        });

    }








}
