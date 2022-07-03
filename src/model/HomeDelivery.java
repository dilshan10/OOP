package model;

public class HomeDelivery {
    private String Order_ID;
    private String Delivery_ID;
    private double Delivery_Cost;

    public HomeDelivery() {
    }

    public HomeDelivery(String order_ID, String delivery_ID, double delivery_Cost) {
        Order_ID = order_ID;
        Delivery_ID = delivery_ID;
        Delivery_Cost = delivery_Cost;
    }

    public String getOrder_ID() {
        return Order_ID;
    }

    public void setOrder_ID(String order_ID) {
        Order_ID = order_ID;
    }

    public String getDelivery_ID() {
        return Delivery_ID;
    }

    public void setDelivery_ID(String delivery_ID) {
        Delivery_ID = delivery_ID;
    }

    public double getDelivery_Cost() {
        return Delivery_Cost;
    }

    public void setDelivery_Cost(double delivery_Cost) {
        Delivery_Cost = delivery_Cost;
    }

    @Override
    public String toString() {
        return "HomeDelivery{" +
                "Order_ID='" + Order_ID + '\'' +
                ", Delivery_ID='" + Delivery_ID + '\'' +
                ", Delivery_Cost=" + Delivery_Cost +
                '}';
    }
}
