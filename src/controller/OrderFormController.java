package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Order;
import util.CrudUtil;

import java.sql.*;

public class OrderFormController {
    public JFXTextField ORID;
    public JFXTextField CID;
    public Button saveBtn;
    public TableView<Order>OrederTable;
    public TableColumn tCID;
    public TableColumn TORID;
    public Button updateBtn;
    public TextField SearchID;
    public Button serachbtn;

    public void initialize(){
        TORID.setCellValueFactory(new PropertyValueFactory("Ordr_ID"));
        tCID.setCellValueFactory(new PropertyValueFactory("Cum_ID"));

        try {
            LoadAllOrder();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void LoadAllOrder() throws ClassNotFoundException, SQLException {
        ResultSet result=CrudUtil.execute("SELECT * FROM takeoder");
        ObservableList<Order> oblist= FXCollections.observableArrayList();
        while (result.next()){
            oblist.add(
                    new Order(
                            result.getString(1),
                            result.getString(2)
                    )
            );
        }
       OrederTable.setItems(oblist);
    }

    public void ClickOnNew(ActionEvent actionEvent) {
        Order order=new Order(
                ORID.getText(),
                CID.getText()
        );
        try {
            if(CrudUtil.execute("INSERT INTO takeoder VALUES(?,?)", order.getOrdr_ID(),order.getCum_ID())){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved!...").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void ClickSearchOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void search() throws ClassNotFoundException, SQLException {
        ResultSet result=CrudUtil.execute("SELECT * FROM takeoder WHERE orde_id=?", SearchID.getText());
        if (result.next()){
            ORID.setText(result.getString(1));
            CID.setText(result.getString(2));
        }else {
            new Alert(Alert.AlertType.WARNING,"Empty Result!..").show();
        }
    }
}
