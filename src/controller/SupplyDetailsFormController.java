package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.Customer;
import model.SupplyDetails;
import util.CrudUtil;

import java.sql.*;
import java.util.regex.Pattern;

public class SupplyDetailsFormController {
    public JFXTextField SID;
    public JFXTextField PYID;
    public JFXTextField PID;
    public TableView tblOrderDetails;
    public TableColumn TOID;
    public TableColumn TPID;
    public TableColumn TPYID;
    public TextField SearchID;
    public Button serachbtn;
    public Button saveBtn;
    public Button updateBtn;

    public void initialize(){

        TOID.setCellValueFactory(new PropertyValueFactory("sup_ID"));
        TPID.setCellValueFactory(new PropertyValueFactory("Pro_ID"));
        TPYID.setCellValueFactory(new PropertyValueFactory("Pay_ID"));

        try {
            LoadAllSupplyDetails();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void LoadAllSupplyDetails() throws ClassNotFoundException, SQLException {
        ResultSet result= CrudUtil.execute("SELECT * FROM supplydetails");
        ObservableList<SupplyDetails> oblist= FXCollections.observableArrayList();
        while (result.next()){
            oblist.add(
                    new SupplyDetails(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3)
                    )
            );
            tblOrderDetails.setItems(oblist);
        }
    }

    public void ClickOnNew(ActionEvent actionEvent) {
        SupplyDetails sd=new SupplyDetails(SID.getText(), PID.getText(), PYID.getText());
        try {
            if(CrudUtil.execute("INSERT INTO supplydetails VALUES (?,?,?)",sd.getSup_ID(),sd.getPro_ID(),sd.getPay_ID())){ new Alert(Alert.AlertType.CONFIRMATION,"Saved!...").show(); }
        } catch (ClassNotFoundException | SQLException e) { e.printStackTrace();new Alert(Alert.AlertType.ERROR,e.getMessage()).show(); }
    }

    public void ClickOnUpdate(ActionEvent actionEvent) {
        SupplyDetails sd=new SupplyDetails(SID.getText(), PID.getText(), PYID.getText());
        try {
            boolean isUpdate=CrudUtil.execute("UPDATE supplydetails SET pro_id=?,pay_id=? WHERE sup_id=?", sd.getPro_ID(),sd.getPay_ID(),sd.getSup_ID());
            if (isUpdate==true){ new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show(); }
            else { new Alert(Alert.AlertType.ERROR,"Try Again..").show(); }
        } catch (ClassNotFoundException | SQLException e) { e.printStackTrace(); }
    }

    public void ClickSearchOnAction(ActionEvent actionEvent) {
        try {
            search();
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

    private void search() throws ClassNotFoundException, SQLException {
        ResultSet result=CrudUtil.execute("SELECT * FROM supplydetails WHERE sup_id=?",SearchID.getText());
        if (result.next()){
            SID.setText(result.getString(1));
            PID.setText(result.getString(2));
            PYID.setText(result.getString(3));
        }else {
            new Alert(Alert.AlertType.WARNING,"Empty Result!..").show();
        }
    }

    public void ClockOnDelete(ActionEvent actionEvent) {
        try {
            boolean isDelete=CrudUtil.execute("DELETE FROM supplydetails WHERE sup_id=?", SearchID.getText());
            if (isDelete){
                new Alert(Alert.AlertType.CONFIRMATION,"Delete...").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Try Again...").show(); }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void SupIDvalide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[(S),0-9]{4}$");
        saveBtn.setDisable(true);
        updateBtn.setDisable(true);
        boolean matches=idPattern.matcher(SID.getText()).matches();
        if(matches){
            SID.setStyle("-fx-border-color:Green");
            saveBtn.setDisable(false);
            updateBtn.setDisable(false);
        }else{
            SID.setStyle("-fx-border-color: red");
            saveBtn.setDisable(true);
            updateBtn.setDisable(true);
        }
    }

    public void payIDvalide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[(PY),0-9]{5}$");
        saveBtn.setDisable(true);
        updateBtn.setDisable(true);
        boolean matches=idPattern.matcher(PYID.getText()).matches();
        if(matches){
            PYID.setStyle("-fx-border-color:Green");
            saveBtn.setDisable(false);
            updateBtn.setDisable(false);
        }else{
            PYID.setStyle("-fx-border-color: red");
            saveBtn.setDisable(true);
            updateBtn.setDisable(true);
        }
    }

    public void proIDvalide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[(P),0-9]{4}$");
        saveBtn.setDisable(true);
        updateBtn.setDisable(true);
        boolean matches=idPattern.matcher(PID.getText()).matches();
        if(matches){
            PID.setStyle("-fx-border-color:Green");
            saveBtn.setDisable(false);
            updateBtn.setDisable(false);
        }else{
            PID.setStyle("-fx-border-color: red");
            saveBtn.setDisable(true);
            updateBtn.setDisable(true);
        }
    }

    public void serachIDvalide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[(S),0-9]{4}$");
        serachbtn.setDisable(true);
        boolean matches=idPattern.matcher(SearchID.getText()).matches();
        if(matches){
            serachbtn.setDisable(false);
        }else{
            serachbtn.setDisable(true);
        }
    }
}
