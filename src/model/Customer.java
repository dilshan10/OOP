package model;

public class Customer {
    private String Cust_ID;
    private String Cust_Name;
    private String Cust_Address;
    private String Cust_TelNUmber;

    public Customer() {
    }

    public Customer(String cust_ID, String cust_Name, String cust_Address, String cust_TelNUmber) {
        Cust_ID = cust_ID;
        Cust_Name = cust_Name;
        Cust_Address = cust_Address;
        Cust_TelNUmber = cust_TelNUmber;
    }

    public String getCust_ID() {
        return Cust_ID;
    }

    public void setCust_ID(String cust_ID) {
        Cust_ID = cust_ID;
    }

    public String getCust_Name() {
        return Cust_Name;
    }

    public void setCust_Name(String cust_Name) {
        Cust_Name = cust_Name;
    }

    public String getCust_Address() {
        return Cust_Address;
    }

    public void setCust_Address(String cust_Address) {
        Cust_Address = cust_Address;
    }

    public String getCust_TelNUmber() {
        return Cust_TelNUmber;
    }

    public void setCust_TelNUmber(String cust_TelNUmber) {
        Cust_TelNUmber = cust_TelNUmber;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "Cust_ID='" + Cust_ID + '\'' +
                ", Cust_Name='" + Cust_Name + '\'' +
                ", Cust_Address='" + Cust_Address + '\'' +
                ", Cust_TelNUmber='" + Cust_TelNUmber + '\'' +
                '}';
    }
}
