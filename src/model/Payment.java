package model;

public class Payment {
    private String Pay_ID;
    private String time;
    private String Date;
    private double inComeValue;
    private double OutgoingValue;

    public Payment() {
    }

    public Payment(String pay_ID, String time, String date, double inComeValue, double outgoingValue) {
        Pay_ID = pay_ID;
        this.time = time;
        Date = date;
        this.inComeValue = inComeValue;
        OutgoingValue = outgoingValue;
    }

    public String getPay_ID() {
        return Pay_ID;
    }

    public void setPay_ID(String pay_ID) {
        Pay_ID = pay_ID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public double getInComeValue() {
        return inComeValue;
    }

    public void setInComeValue(double inComeValue) {
        this.inComeValue = inComeValue;
    }

    public double getOutgoingValue() {
        return OutgoingValue;
    }

    public void setOutgoingValue(double outgoingValue) {
        OutgoingValue = outgoingValue;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "Pay_ID='" + Pay_ID + '\'' +
                ", time='" + time + '\'' +
                ", Date='" + Date + '\'' +
                ", inComeValue=" + inComeValue +
                ", OutgoingValue=" + OutgoingValue +
                '}';
    }
}
