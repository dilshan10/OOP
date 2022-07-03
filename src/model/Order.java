package model;

public class Order {
    private String Ordr_ID;
    private String Cum_ID;

    public Order() {
    }

    public Order(String ordr_ID, String cum_ID) {
        Ordr_ID = ordr_ID;
        Cum_ID = cum_ID;
    }

    public String getOrdr_ID() {
        return Ordr_ID;
    }

    public void setOrdr_ID(String ordr_ID) {
        Ordr_ID = ordr_ID;
    }

    public String getCum_ID() {
        return Cum_ID;
    }

    public void setCum_ID(String cum_ID) {
        Cum_ID = cum_ID;
    }

    @Override
    public String toString() {
        return "Order{" +
                "Ordr_ID='" + Ordr_ID + '\'' +
                ", Cum_ID='" + Cum_ID + '\'' +
                '}';
    }
}