package model;

public class Products {
    private String Pro_ID;
    private String Pro_Name;
    private String Pro_Type;
    private int Pro_Count;
    private double Unit_Prize;

    public Products(String pro_id, String pro_name, String pro_type, String s, String s1) {}

    public Products(String pro_ID, String pro_Name, String pro_Type, int pro_Count, double unit_Prize) {
        Pro_ID = pro_ID;
        Pro_Name = pro_Name;
        Pro_Type = pro_Type;
        Pro_Count = pro_Count;
        Unit_Prize = unit_Prize;
    }

    public String getPro_ID() {
        return Pro_ID;
    }

    public void setPro_ID(String pro_ID) {
        Pro_ID = pro_ID;
    }

    public String getPro_Name() {
        return Pro_Name;
    }

    public void setPro_Name(String pro_Name) {
        Pro_Name = pro_Name;
    }

    public String getPro_Type() {
        return Pro_Type;
    }

    public void setPro_Type(String pro_Type) {
        Pro_Type = pro_Type;
    }

    public int getPro_Count() {
        return Pro_Count;
    }

    public void setPro_Count(int pro_Count) {
        Pro_Count = pro_Count;
    }

    public double getUnit_Prize() {
        return Unit_Prize;
    }

    public void setUnit_Prize(double unit_Prize) {
        Unit_Prize = unit_Prize;
    }

    @Override
    public String toString() {
        return "Products{" +
                "Pro_ID='" + Pro_ID + '\'' +
                ", Pro_Name='" + Pro_Name + '\'' +
                ", Pro_Type='" + Pro_Type + '\'' +
                ", Pro_Count=" + Pro_Count +
                ", Unit_Prize=" + Unit_Prize +
                '}';
    }
}
