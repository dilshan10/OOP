package model;

public class Supplier {
    private String Sup_ID;
    private String Sup_Name;
    private String Sup_Address;
    private String Sup_TelNumber;

    public Supplier() {
    }

    public Supplier(String sup_ID, String sup_Name, String sup_Address, String sup_TelNumber) {
        Sup_ID = sup_ID;
        Sup_Name = sup_Name;
        Sup_Address = sup_Address;
        Sup_TelNumber = sup_TelNumber;
    }

    public String getSup_ID() {
        return Sup_ID;
    }

    public void setSup_ID(String sup_ID) {
        Sup_ID = sup_ID;
    }

    public String getSup_Name() {
        return Sup_Name;
    }

    public void setSup_Name(String sup_Name) {
        Sup_Name = sup_Name;
    }

    public String getSup_Address() {
        return Sup_Address;
    }

    public void setSup_Address(String sup_Address) {
        Sup_Address = sup_Address;
    }

    public String getSup_TelNumber() {
        return Sup_TelNumber;
    }

    public void setSup_TelNumber(String sup_TelNumber) {
        Sup_TelNumber = sup_TelNumber;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "Sup_ID='" + Sup_ID + '\'' +
                ", Sup_Name='" + Sup_Name + '\'' +
                ", Sup_Address='" + Sup_Address + '\'' +
                ", Sup_TelNumber='" + Sup_TelNumber + '\'' +
                '}';
    }
}