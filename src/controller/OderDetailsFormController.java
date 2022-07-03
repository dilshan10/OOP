package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Customer;
import model.OrderDetails;
import util.CrudUtil;

import java.sql.*;
import java.util.regex.Pattern;

public class OderDetailsFormController {
    public AnchorPane Root;
    public Label Date;
    public Label Time;
    public JFXTextField ORID;
    public JFXTextField PYID;
    public JFXTextField PID;
    public TableView <OrderDetails>tblOrderDetails;
    public TableColumn TOID;
    public TableColumn TPID;
    public TableColumn TPYID;
    public TextField SearchID;
    public Button searcgbtn;
    public Button saveBtn;
    public Button updateBtn;
    public TableColumn TPQty;
    public JFXTextField Qty;

    public void initialize(){
        TOID.setCellValueFactory(new PropertyValueFactory("Order_ID"));
        TPID.setCellValueFactory(new PropertyValueFactory("Product_ID"));
        TPQty.setCellValueFactory(new PropertyValueFactory("Qty"));
        TPYID.setCellValueFactory(new PropertyValueFactory("Payment_ID"));

        try {
            LoadAllOrderDetails();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void LoadAllOrderDetails() throws ClassNotFoundException, SQLException {
        ResultSet result= CrudUtil.execute("SELECT * FROM orderdetails");
        ObservableList<OrderDetails> oblist= FXCollections.observableArrayList();
        while (result.next()){
            oblist.add(
                    new OrderDetails(
                            result.getString(1),
                            result.getString(2),
                            result.getInt(3),
                            result.getString(4)
                    )
            );
        }
        tblOrderDetails.setItems(oblist);
    }

    public void ClickOnNew(ActionEvent actionEvent) {
       OrderDetails orderDetails=new OrderDetails(
               ORID.getText(),
               PID.getText(),
               Integer.parseInt(Qty.getText()),
               PYID.getText()
       );
        try {
            if(CrudUtil.execute("INSERT INTO orderdetails VALUES (?,?,?,?)", orderDetails.getOrder_ID(),orderDetails.getProduct_ID(),orderDetails.getQty(),orderDetails.getPayment_ID())){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved!...").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        try {
            Search();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void ClickSearchOnAction(ActionEvent actionEvent) {
        try {
            Search();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void Search() throws ClassNotFoundException, SQLException {
        ResultSet result=CrudUtil.execute("SELECT * FROM orderdetails WHERE order_id=?",SearchID.getText());
        if (result.next()){
            ORID.setText(result.getString(1));
            PID.setText(result.getString(2));
            Qty.setText(String.valueOf(result.getInt(3)));
            PYID.setText(result.getString(4));
        }else {
            new Alert(Alert.AlertType.WARNING,"Empty Result!..").show();
        }
    }

    public void searchIDvalide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[(PY),0-9]{4}$");
        searcgbtn.setDisable(true);
        boolean matches=idPattern.matcher(SearchID.getText()).matches();
        if(matches){
            searcgbtn.setDisable(false);
        }else{
            searcgbtn.setDisable(true);
        }
    }
}
