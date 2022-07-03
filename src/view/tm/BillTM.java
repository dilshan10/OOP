package view.tm;

import javafx.scene.control.Button;

public class BillTM {
    private String id;
    private String name;
    private String type;
    private double unitPrize;
    private int qty;
    private double totalPrize;
    private Button btn;

    public BillTM() {
    }

    public BillTM(String id, String name, String type, double unitPrize, int qty, double totalPrize, Button btn) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.unitPrize = unitPrize;
        this.qty = qty;
        this.totalPrize = totalPrize;
        this.btn = btn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getUnitPrize() {
        return unitPrize;
    }

    public void setUnitPrize(double unitPrize) {
        this.unitPrize = unitPrize;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotalPrize() {
        return totalPrize;
    }

    public void setTotalPrize(double totalPrize) {
        this.totalPrize = totalPrize;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "BillTM{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", unitPrize=" + unitPrize +
                ", qty=" + qty +
                ", totalPrize=" + totalPrize +
                ", btn=" + btn +
                '}';
    }
}
