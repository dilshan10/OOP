package model;

public class SupplyDetails {
    private String sup_ID;
    private String Pro_ID;
    private String Pay_ID;

    public SupplyDetails() {
    }

    public SupplyDetails(String sup_ID, String pro_ID, String pay_ID) {
        this.sup_ID = sup_ID;
        Pro_ID = pro_ID;
        Pay_ID = pay_ID;
    }

    public String getSup_ID() {
        return sup_ID;
    }

    public void setSup_ID(String sup_ID) {
        this.sup_ID = sup_ID;
    }

    public String getPro_ID() {
        return Pro_ID;
    }

    public void setPro_ID(String pro_ID) {
        Pro_ID = pro_ID;
    }

    public String getPay_ID() {
        return Pay_ID;
    }

    public void setPay_ID(String pay_ID) {
        Pay_ID = pay_ID;
    }

    @Override
    public String toString() {
        return "SupplyDetails{" +
                "sup_ID='" + sup_ID + '\'' +
                ", Pro_ID='" + Pro_ID + '\'' +
                ", Pay_ID='" + Pay_ID + '\'' +
                '}';
    }
}
