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
import model.Supplier;
import util.CrudUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.regex.Pattern;

public class SupplierFormController {
    public AnchorPane Root;
    public Label Date;
    public Label Time;
    public JFXTextField SID;
    public JFXTextField SAddress;
    public JFXTextField StelNumber;
    public JFXTextField SName;
    public TableView<Supplier>tblSupplier;
    public TableColumn TSID;
    public TableColumn TSName;
    public TableColumn TSAddress;
    public TableColumn TStelNumber;
    public TextField SearchID;
    public Button serachbtn;
    public Button saveBtn;
    public Button updetaBtn;

    public void initialize(){

        TSID.setCellValueFactory(new PropertyValueFactory("Sup_ID"));
        TSName.setCellValueFactory(new PropertyValueFactory("Sup_Name"));
        TSAddress.setCellValueFactory(new PropertyValueFactory("Sup_Address"));
        TStelNumber.setCellValueFactory(new PropertyValueFactory("Sup_TelNumber"));

        try {
            LoadAllSupplier();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void LoadAllSupplier() throws ClassNotFoundException, SQLException {
        ResultSet result= CrudUtil.execute("SELECT * FROM supplier");
        ObservableList<Supplier> oblist= FXCollections.observableArrayList();
        while (result.next()){
            oblist.add(
                    new Supplier(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4)
                    )
            );
        }
        tblSupplier.setItems(oblist);
    }

    public void ClickOnNew(ActionEvent actionEvent) {
        Supplier supplier=new Supplier(
                SID.getText(),SName.getText(),SAddress.getText(),StelNumber.getText()
        );
        try {
            if(CrudUtil.execute("INSERT INTO supplier VALUES (?,?,?,?)",supplier.getSup_ID(),supplier.getSup_Name(),supplier.getSup_Address(),supplier.getSup_TelNumber())){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved!...").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void ClickOnUpdate(ActionEvent actionEvent) {
        Supplier s=new Supplier(
                SID.getText(),SName.getText(),SAddress.getText(),StelNumber.getText()
        );
        try {
            boolean isUpdate=CrudUtil.execute("UPDATE supplier SET sup_name=?,sup_address=?,sup_telNumber=? WHERE sup_id=?", s.getSup_Name(),s.getSup_Address(),s.getSup_TelNumber(), s.getSup_ID());
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
        ResultSet result=CrudUtil.execute("SELECT * FROM supplier WHERE sup_id=?", SearchID.getText());
        if (result.next()){
            SID.setText(result.getString(1));
            SName.setText(result.getString(2));
            SAddress.setText(result.getString(3));
            StelNumber.setText(result.getString(4));
        }else {
            new Alert(Alert.AlertType.WARNING,"Empty Result!..").show();
        }
    }

    public void ClickOnDelete(ActionEvent actionEvent) {
        try {
            boolean isDelete=CrudUtil.execute("DELETE FROM supplier WHERE sup_id=?", SearchID.getText());
            if (isDelete){
                new Alert(Alert.AlertType.CONFIRMATION,"Delete...").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Try Again...").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchIDvalide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[(S),0-9]{4}$");
        serachbtn.setDisable(true);
        boolean matches=idPattern.matcher(SearchID.getText()).matches();
        if(matches){
            serachbtn.setDisable(false);
        }else{
            serachbtn.setDisable(true);
        }
    }

    public void SupIDvalide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[(S),0-9]{4}$");
        saveBtn.setDisable(true);
        updetaBtn.setDisable(true);
        boolean matches=idPattern.matcher(SID.getText()).matches();
        if(matches){
            SID.setStyle("-fx-border-color:Green");
            saveBtn.setDisable(false);
            updetaBtn.setDisable(false);
        }else{
            SID.setStyle("-fx-border-color: red");
            saveBtn.setDisable(true);
            updetaBtn.setDisable(true);
        }
    }

    public void supAddressValide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[A-Z,a-z,,,.,0-9,/, ]{15,40}$");
        saveBtn.setDisable(true);
        updetaBtn.setDisable(true);
        boolean matches=idPattern.matcher(SAddress.getText()).matches();
        if(matches){
            SAddress.setStyle("-fx-border-color:Green");
            saveBtn.setDisable(false);
            updetaBtn.setDisable(false);
        }else{
            SAddress.setStyle("-fx-border-color: red");
            saveBtn.setDisable(true);
            updetaBtn.setDisable(true);
        }
    }

    public void suptelvalide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[0-9, ]{10,11}$");
        saveBtn.setDisable(true);
        updetaBtn.setDisable(true);
        boolean matches=idPattern.matcher(StelNumber.getText()).matches();
        if(matches){
            StelNumber.setStyle("-fx-border-color:Green");
            saveBtn.setDisable(false);
            updetaBtn.setDisable(false);
        }else{
            StelNumber.setStyle("-fx-border-color: red");
            saveBtn.setDisable(true);
            updetaBtn.setDisable(true);
        }
    }

    public void supNamevalide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[A-Z,a-z,., ]{5,30}$");
        saveBtn.setDisable(true);
        updetaBtn.setDisable(true);
        boolean matches=idPattern.matcher(SName.getText()).matches();
        if(matches){
            SName.setStyle("-fx-border-color:Green");
            saveBtn.setDisable(false);
            updetaBtn.setDisable(false);
        }else{
            SName.setStyle("-fx-border-color: red");
            saveBtn.setDisable(true);
            updetaBtn.setDisable(true);
        }
    }
}
