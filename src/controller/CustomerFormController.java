package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Customer;
import util.CrudUtil;

import java.sql.*;
import java.util.regex.Pattern;

public class CustomerFormController {
    public AnchorPane Root;
    public ImageView Addnew;
    public JFXTextField CID;
    public JFXTextField CAddress;
    public JFXTextField CtelNumber;
    public JFXTextField CName;
    public TableView<Customer> tblCustomer;
    public TableColumn tCID;
    public TableColumn TCName;
    public TableColumn TCAddress;
    public TableColumn TCTL;
    public TextField SearchID;
    public Button saveBtn;
    public Button updateBtn;
    public Button btnSearch;
    public Button deleteBtn;

    public void initialize() {
        tCID.setCellValueFactory(new PropertyValueFactory("Cust_ID"));
        TCName.setCellValueFactory(new PropertyValueFactory("Cust_Name"));
        TCAddress.setCellValueFactory(new PropertyValueFactory("Cust_Address"));
        TCTL.setCellValueFactory(new PropertyValueFactory("Cust_TelNUmber"));
        try {
            LoadAllCustomer();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        saveBtn.setDisable(true);
        updateBtn.setDisable(true);

    }

    public void ValidIDKeyReles(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[(C),0-9]{4}$");
        boolean matches=idPattern.matcher(CID.getText()).matches();
        if(matches){
            CID.setStyle("-fx-border-color:Green");
            saveBtn.setDisable(false);
            updateBtn.setDisable(false);
        }else{
            CID.setStyle("-fx-border-color: red");
            saveBtn.setDisable(true);
            updateBtn.setDisable(true);
        }
    }

    public void ValidNameKeyReles(KeyEvent keyEvent) {
        Pattern NamePattern = Pattern.compile("^[A-Z,a-z,., ]{5,30}$");
        boolean matches=NamePattern.matcher(CName.getText()).matches();
        if(matches){
            CName.setStyle("-fx-border-color:Green");
            saveBtn.setDisable(false);
            updateBtn.setDisable(false);
        }else{
            CName.setStyle("-fx-border-color:red");
            saveBtn.setDisable(true);
            updateBtn.setDisable(true);
        }
    }

    public void ValidAddressKeyReles(KeyEvent keyEvent) {
        Pattern AddressPattern = Pattern.compile("^[A-Z,a-z,,,.,0-9,/, ]{5,40}$");
        boolean matches=AddressPattern.matcher(CAddress.getText()).matches();
        if (matches){
            CAddress.setStyle("-fx-border-color:Green");
            saveBtn.setDisable(false);
            updateBtn.setDisable(false);
        }else {
            CAddress.setStyle("-fx-border-color: red");
            saveBtn.setDisable(true);
            updateBtn.setDisable(true);
        }
    }

    public void ValidtellNumKeyReles(KeyEvent keyEvent) {
        Pattern TelNUmPattern = Pattern.compile("^[0-9, ]{10,11}$");
        boolean matches=TelNUmPattern.matcher(CtelNumber.getText()).matches();
        if (matches){
            CtelNumber.setStyle("-fx-border-color: Green");
            saveBtn.setDisable(false);
            updateBtn.setDisable(false);
        }else {
            CtelNumber.setStyle("-fx-border-color: red");
            saveBtn.setDisable(true);
            updateBtn.setDisable(true);
        }
    }

    public void searchIDvalide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[(C),0-9]{4}$");
        boolean matches = idPattern.matcher(SearchID.getText()).matches();
        btnSearch.setDisable(true);
        if (matches){
            btnSearch.setDisable(false);
        }else {
            btnSearch.setDisable(true);
        }
    }

    private void LoadAllCustomer() throws ClassNotFoundException, SQLException {
        ResultSet result=CrudUtil.execute("SELECT * FROM customer");
        ObservableList<Customer> oblist= FXCollections.observableArrayList();
        while (result.next()){
            oblist.add(
                    new Customer(
                            result.getString("Cust_ID"),
                            result.getString(2),
                            result.getString(3),
                            result.getString("cust_telnum")
                    )
            );
        }
        tblCustomer.setItems(oblist);
    }

    public void ClickOnNew(ActionEvent actionEvent) {
        Customer customer=new Customer(
                CID.getText(),
                CName.getText(),
                CAddress.getText(),
                CtelNumber.getText()
        );
        try {
            if(CrudUtil.execute("INSERT INTO customer VALUES (?,?,?,?)",customer.getCust_ID(),customer.getCust_Name(),customer.getCust_Address(),customer.getCust_TelNUmber())){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved!...").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        tblCustomer.refresh();
    }

    public void ClickOnUpdate(ActionEvent actionEvent) {
        Customer customer=new Customer(
                CID.getText(),
                CName.getText(),
                CAddress.getText(),
                CtelNumber.getText()
        );
        try {
            boolean isUpdate=CrudUtil.execute("UPDATE customer SET cust_name=?,cust_address=?,cust_telnum=? WHERE cust_id=?", customer.getCust_Name(),customer.getCust_Address(),customer.getCust_TelNUmber(),customer.getCust_ID());
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
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    public void ClickSearchOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    private void search() throws ClassNotFoundException,SQLException {
         ResultSet result=CrudUtil.execute("SELECT * FROM customer WHERE cust_id=?", SearchID.getText());
         if (result.next()){
             CID.setText(result.getString(1));
             CName.setText(result.getString(2));
             CAddress.setText(result.getString(3));
             CtelNumber.setText(result.getString(4));
         }else {
             new Alert(Alert.AlertType.WARNING,"Empty Result!..").show();
         }
    }

    public void ClickOnDelete(ActionEvent actionEvent) {
        try {
            boolean isDelete=CrudUtil.execute("DELETE FROM customer WHERE cust_id=?", SearchID.getText());
            if (isDelete){
                new Alert(Alert.AlertType.CONFIRMATION,"Delete...").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Try Again...").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}