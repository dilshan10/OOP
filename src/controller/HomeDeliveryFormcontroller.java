package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Customer;
import model.HomeDelivery;
import org.omg.CORBA.ORB;
import util.CrudUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.regex.Pattern;

public class HomeDeliveryFormcontroller {

    public AnchorPane Root;
    public JFXTextField ORID;
    public JFXTextField DCost;
    public JFXTextField DID;
    public TableView <HomeDelivery>tblDelivery;
    public TableColumn tORID;
    public TableColumn TDID;
    public TableColumn TDCost;
    public TextField SearchID;
    public Button seachbtn;
    public Button saveBtn;
    public Button updetaBtn;

    public void initialize() {
        tORID.setCellValueFactory(new PropertyValueFactory("Order_ID"));
        TDID.setCellValueFactory(new PropertyValueFactory("Delivery_ID"));
        TDCost.setCellValueFactory(new PropertyValueFactory("Delivery_Cost"));

        try {
            LoadAllHomeDelivery();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void LoadAllHomeDelivery() throws ClassNotFoundException, SQLException {
        ResultSet result= CrudUtil.execute("SELECT * FROM homedelivery");
        ObservableList<HomeDelivery> oblist= FXCollections.observableArrayList();
        while (result.next()){
            oblist.add(
                    new HomeDelivery(
                            result.getString("dly_id"),
                            result.getString("orde_id"),
                            result.getDouble("dly_cost")
                    )
            );
        }
        tblDelivery.setItems(oblist);
    }

    public void ClickOnNew(ActionEvent actionEvent) {
       HomeDelivery h=new HomeDelivery(
               DID.getText(),
               ORID.getText(),
               Double.parseDouble(DCost.getText())
       );
        try {
            if(CrudUtil.execute("INSERT INTO homedelivery VALUES (?,?,?)", h.getDelivery_ID(),h.getOrder_ID(),h.getDelivery_Cost())){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved!...").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void ClickOnUpdate(ActionEvent actionEvent) {
        HomeDelivery hd=new HomeDelivery(
                DID.getText(),
                ORID.getText(),
                Double.parseDouble(DCost.getText())
        );
        try {
            boolean isUpdate=CrudUtil.execute("UPDATE homedelivery SET orde_id=?,dly_cost=? WHERE dly_id=?", hd.getOrder_ID(),hd.getDelivery_Cost(),hd.getDelivery_ID());
            if (isUpdate==true){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Try Again..").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
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
        ResultSet result=CrudUtil.execute("SELECT * FROM homedelivery WHERE dly_id=?", SearchID.getText());
        if (result.next()){
            ORID.setText(result.getString(1));
            DID.setText(result.getString(2));
            DCost.setText(result.getString(3));
        }else {
            new Alert(Alert.AlertType.WARNING,"Empty Result!..").show();
        }
    }

    public void ClickOnDelete(ActionEvent actionEvent) {
        try {
            boolean isDelete=CrudUtil.execute("DELETE FROM homedelivery WHERE dly_id=?", SearchID.getText());
            if (isDelete){
                new Alert(Alert.AlertType.CONFIRMATION,"Delete...").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Try Again...").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void orIDvalide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[(OR),0-9]{5}$");
        saveBtn.setDisable(true);
        updetaBtn.setDisable(true);
        boolean matches=idPattern.matcher(ORID.getText()).matches();
        if(matches){
            ORID.setStyle("-fx-border-color:Green");
            saveBtn.setDisable(false);
            updetaBtn.setDisable(false);
        }else{
            ORID.setStyle("-fx-border-color: red");
            saveBtn.setDisable(true);
            updetaBtn.setDisable(true);
        }
    }

    public void dlyCostValide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[0-9,.]{1,9}$");
        saveBtn.setDisable(true);
        updetaBtn.setDisable(true);
        boolean matches=idPattern.matcher(DCost.getText()).matches();
        if(matches){
            DCost.setStyle("-fx-border-color:Green");
            saveBtn.setDisable(false);
            updetaBtn.setDisable(false);
        }else{
            DCost.setStyle("-fx-border-color: red");
            saveBtn.setDisable(true);
            updetaBtn.setDisable(true);
        }
    }

    public void dlyIDvalide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[(D),0-9]{4}$");
        saveBtn.setDisable(true);
        updetaBtn.setDisable(true);
        boolean matches=idPattern.matcher(DID.getText()).matches();
        if(matches){
            DID.setStyle("-fx-border-color:Green");
            saveBtn.setDisable(false);
            updetaBtn.setDisable(false);
        }else{
            DID.setStyle("-fx-border-color: red");
            saveBtn.setDisable(true);
            updetaBtn.setDisable(true);
        }
    }

    public void seachIDvalide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[(D),0-9]{4}$");
        seachbtn.setDisable(true);
        boolean matches=idPattern.matcher(SearchID.getText()).matches();
        if(matches){
            seachbtn.setDisable(false);
        }else{
            seachbtn.setDisable(true);
        }
    }
}

