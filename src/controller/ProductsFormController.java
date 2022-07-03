package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.Products;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import util.CrudUtil;

import java.sql.*;
import java.util.regex.Pattern;

public class ProductsFormController {
    public AnchorPane Root;
    public JFXTextField PID;
    public JFXTextField PType;
    public JFXTextField PPrize;
    public JFXTextField PCount;
    public JFXTextField PName;
    public TableView<Products> tblProducts;
    public TableColumn tPID;
    public TableColumn TPName;
    public TableColumn TPTpe;
    public TableColumn TPCount;
    public TableColumn TPUP;
    public TextField SearchID;
    public Button AddNew;
    public Button updateBtn;
    public Button searchbtn;

    public void initialize() {

        tPID.setCellValueFactory(new PropertyValueFactory("Pro_ID"));
        TPName.setCellValueFactory(new PropertyValueFactory("Pro_Name"));
        TPTpe.setCellValueFactory(new PropertyValueFactory("Pro_Type"));
        TPCount.setCellValueFactory(new PropertyValueFactory("Pro_Count"));
        TPUP.setCellValueFactory(new PropertyValueFactory("Unit_Prize"));

        try {
            LoadAllProducts();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    private void LoadAllProducts() throws ClassNotFoundException, SQLException {
        ResultSet result= CrudUtil.execute("SELECT * FROM product");
        ObservableList<Products> oblist= FXCollections.observableArrayList();
        while (result.next()){
            oblist.add(
                    new Products(
                            result.getString("id"),
                            result.getString("name"),
                            result.getString("type"),
                            result.getInt("count"),
                            result.getDouble("prize")
                    )
            );
            tblProducts.setItems(oblist);
        }
    }

    public void ClickOnNew(ActionEvent actionEvent) {
        Products products=new Products(
                PID.getText(),PName.getText(), PType.getText(),Integer.parseInt(PCount.getText()), Double.parseDouble(PPrize.getText())
        );
        try {
            if(CrudUtil.execute("INSERT INTO product VALUES (?,?,?,?,?)", products.getPro_ID(),products.getPro_Name(),products.getPro_Type(),products.getPro_Count(),products.getUnit_Prize())){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved!...").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void ClickOnUpdate(ActionEvent actionEvent) {
        Products pro=new Products(
                PID.getText(),PName.getText(), PType.getText(),Integer.parseInt(PCount.getText()), Double.parseDouble(PPrize.getText())
        );
        try {
            boolean isUpdate=CrudUtil.execute("UPDATE product SET name=?,type=?,count=?,prize=? WHERE id=?", pro.getPro_Name(), pro.getPro_Type(), pro.getPro_Count(),pro.getUnit_Prize(),pro.getPro_ID());
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
        ResultSet result=CrudUtil.execute("SELECT * FROM product WHERE id=?", SearchID.getText());
        if (result.next()){
            PID.setText(result.getString(1));
            PName.setText(result.getString(2));
            PType.setText(result.getString(3));
            PCount.setText(String.valueOf(result.getString(4)));
            PPrize.setText(String.valueOf(result.getString(5)));
        }else {
            new Alert(Alert.AlertType.WARNING,"Empty Result!..").show();
        }
    }

    public void ClickOnDelete(ActionEvent actionEvent) {
        try {
            boolean isDelete=CrudUtil.execute("DELETE FROM product WHERE id=?", SearchID.getText());
            if (isDelete){
                new Alert(Alert.AlertType.CONFIRMATION,"Delete...").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Try Again...").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void proIdValide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[(P),0-9]{4}$");
        AddNew.setDisable(true);
        updateBtn.setDisable(true);
        boolean matches=idPattern.matcher(PID.getText()).matches();
        if(matches){
            PID.setStyle("-fx-border-color:Green");
            AddNew.setDisable(false);
            updateBtn.setDisable(false);
        }else{
            PID.setStyle("-fx-border-color: red");
            AddNew.setDisable(true);
            updateBtn.setDisable(true);
        }
    }

    public void protypevalide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[A-Z,a-z,]{2,25}$");
        AddNew.setDisable(true);
        updateBtn.setDisable(true);
        boolean matches=idPattern.matcher(PType.getText()).matches();
        if(matches){
            PType.setStyle("-fx-border-color:Green");
            AddNew.setDisable(false);
            updateBtn.setDisable(false);
        }else{
            PType.setStyle("-fx-border-color: red");
            AddNew.setDisable(true);
            updateBtn.setDisable(true);
        }
    }

    public void proUnitPrizeValide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[0-9,.]{3,10}$");
        AddNew.setDisable(true);
        updateBtn.setDisable(true);
        boolean matches=idPattern.matcher(PPrize.getText()).matches();
        if(matches){
            PPrize.setStyle("-fx-border-color:Green");
            AddNew.setDisable(false);
            updateBtn.setDisable(false);
        }else{
            PPrize.setStyle("-fx-border-color: red");
            AddNew.setDisable(true);
            updateBtn.setDisable(true);
        }
    }

    public void procountvalide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[0-9]{1,5}$");
        AddNew.setDisable(true);
        updateBtn.setDisable(true);
        boolean matches=idPattern.matcher(PCount.getText()).matches();
        if(matches){
            PCount.setStyle("-fx-border-color:Green");
            AddNew.setDisable(false);
            updateBtn.setDisable(false);
        }else{
            PCount.setStyle("-fx-border-color: red");
            AddNew.setDisable(true);
            updateBtn.setDisable(true);
        }
    }

    public void proNameValide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[A-z, ,.,0-9,/]{5,30}$");
        AddNew.setDisable(true);
        updateBtn.setDisable(true);
        boolean matches=idPattern.matcher(PName.getText()).matches();
        if(matches){
            PName.setStyle("-fx-border-color:Green");
            AddNew.setDisable(false);
            updateBtn.setDisable(false);
        }else{
            PName.setStyle("-fx-border-color: red");
            AddNew.setDisable(true);
            updateBtn.setDisable(true);
        }
    }

    public void serchIDvalide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[(P),0-9]{4}$");
        searchbtn.setDisable(true);
        boolean matches=idPattern.matcher(SearchID.getText()).matches();
        if(matches){
            searchbtn.setDisable(false);
        }else{
            searchbtn.setDisable(true);
        }
    }
}