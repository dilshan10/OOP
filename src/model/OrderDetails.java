package model;

public class OrderDetails {
    private String Order_ID;
    private String Product_ID;
    private int Qty;
    private String Payment_ID;

    public OrderDetails() {
    }

    public OrderDetails(String order_ID, String product_ID, int qty, String payment_ID) {
        Order_ID = order_ID;
        Product_ID = product_ID;
        Qty = qty;
        Payment_ID = payment_ID;
    }

    public String getOrder_ID() {
        return Order_ID;
    }

    public void setOrder_ID(String order_ID) {
        Order_ID = order_ID;
    }

    public String getProduct_ID() {
        return Product_ID;
    }

    public void setProduct_ID(String product_ID) {
        Product_ID = product_ID;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public String getPayment_ID() {
        return Payment_ID;
    }

    public void setPayment_ID(String payment_ID) {
        Payment_ID = payment_ID;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "Order_ID='" + Order_ID + '\'' +
                ", Product_ID='" + Product_ID + '\'' +
                ", Qty=" + Qty +
                ", Payment_ID='" + Payment_ID + '\'' +
                '}';
    }
}
