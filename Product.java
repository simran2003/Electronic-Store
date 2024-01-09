//Base class for all products the store will sell
public abstract class Product{
    private double price;
    private int stockQuantity;
    private int soldQuantity;
    private int Cart_quant;// the quantity  of particular product in the cart


    public Product(double initPrice, int initQuantity) {
        Cart_quant=0;
        price = initPrice;
        stockQuantity = initQuantity;
    }
    public void setCart_quant(int amount){Cart_quant += amount;}// will use in adding things to cart and removing
    public int getCart_quant() {
        return Cart_quant;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }
    public void setStockQuantity(int quantity) {// will use in electronic store when something is added to cart
        stockQuantity=stockQuantity +quantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public double getPrice() {
        return price;
    }

    //Returns the total revenue (price * amount) if there are at least amount items in stock
    //Return 0 otherwise (i.e., there is no sale completed)
    public double sellUnits(int amount) {
        if (amount > 0 && stockQuantity >= amount) {
            stockQuantity -= amount;
            soldQuantity += amount;
            return price * amount;
        }
        return 0.0;
    }
}