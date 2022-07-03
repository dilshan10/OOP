package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Employee;
import util.CrudUtil;

import java.sql.*;
import java.util.regex.Pattern;

public class EmployeeFormController {
    public AnchorPane Root;
    public JFXTextField EID;
    public JFXTextField EName;
    public JFXTextField EDOB;
    public JFXTextField EAddress;
    public JFXTextField EUID;
    public JFXTextField ESalery;
    public Button AddNew;
    public TableView<Employee>tblEmployee;
    public TextField SearchID;
    public TableColumn TEID;
    public TableColumn TUID;
    public TableColumn TEName;
    public TableColumn TEAddress;
    public TableColumn TEDOB;
    public TableColumn TESalary;
    public Button updatebtn;
    public Button searchbtn;

    public void initialize(){
        TEID.setCellValueFactory(new PropertyValueFactory("Emp_ID"));
        TUID.setCellValueFactory(new PropertyValueFactory("User_ID"));
        TEName.setCellValueFactory(new PropertyValueFactory("Emp_Name"));
        TEAddress.setCellValueFactory(new PropertyValueFactory("Emp_Address"));
        TEDOB.setCellValueFactory(new PropertyValueFactory("DOB"));
        TESalary.setCellValueFactory(new PropertyValueFactory("Salery"));

        try {
            LoadAllEmployees();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void LoadAllEmployees() throws ClassNotFoundException, SQLException {
        ResultSet result= CrudUtil.execute("SELECT * FROM employee");
        ObservableList<Employee> oblist= FXCollections.observableArrayList();
        while (result.next()){
            oblist.add(
                    new Employee(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            result.getString(5),
                            result.getDouble(6)
                    )
            );
        }
        tblEmployee.setItems(oblist);
    }

    public void ClickOnNew(ActionEvent actionEvent) {
        Employee emp=new Employee(
                EID.getText(),
                EUID.getText(),
                EName.getText(),
                EAddress.getText(),
                EDOB.getText(),
                Double.parseDouble(ESalery.getText())
        );
        try {
            if(CrudUtil.execute("INSERT INTO employee VALUES (?,?,?,?,?,?)", emp.getEmp_ID(),emp.getUser_ID(), emp.getEmp_Name(),emp.getEmp_Address(),emp.getDOB(),emp.getSalery())){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved!...").show();
            }
        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e1.getMessage()).show();
        }
    }

    public void ClickOnUpdate(ActionEvent actionEvent) {
        Employee emp=new Employee(
                EID.getText(),
                EUID.getText(),
                EName.getText(),
                EAddress.getText(),
                EDOB.getText(),
                Double.parseDouble(ESalery.getText())
        );
        try {
            boolean isUpdate=CrudUtil.execute("UPDATE employee SET id=?,emp_name=?,emp_address=?,dob=?,salery=? WHERE emp_id=?",  emp.getUser_ID(),emp.getEmp_Name(),emp.getEmp_Address(),emp.getDOB(),emp.getSalery(),emp.getEmp_ID());
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
        ResultSet result=CrudUtil.execute("SELECT * FROM employee WHERE emp_id=?", SearchID.getText());
        if (result.next()){
            EID.setText(result.getString(1));
            EUID.setText(result.getString(2));
            EName.setText(result.getString(3));
           EAddress.setText(result.getString(4));
            EDOB.setText(result.getString(5));
            ESalery.setText(String.valueOf(result.getString(6)));
        }else {
            new Alert(Alert.AlertType.WARNING,"Empty Result!..").show();
        }
    }

    public void ClickOnDelete(ActionEvent actionEvent) {
        try {
            boolean isDelete=CrudUtil.execute("DELETE FROM employee WHERE emp_id=?", SearchID.getText());
            if (isDelete){
                new Alert(Alert.AlertType.CONFIRMATION,"Delete...").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Try Again...").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void empIDvalide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[(E),0-9]{4}$");
        boolean matches = idPattern.matcher(EID.getText()).matches();
        AddNew.setDisable(true);
        updatebtn.setDisable(true);
        if (matches){
            EID.setStyle("-fx-border-color:Green");
            AddNew.setDisable(false);
            updatebtn.setDisable(false);
        }else {
            EID.setStyle("-fx-border-color: red");
            AddNew.setDisable(true);
            updatebtn.setDisable(true);
        }
    }

    public void empNamevalide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[A-Z,a-z,., ]{5,30}$");
        boolean matches = idPattern.matcher(EName.getText()).matches();
        AddNew.setDisable(true);
        updatebtn.setDisable(true);
        if (matches){
            EName.setStyle("-fx-border-color:Green");
            AddNew.setDisable(false);
            updatebtn.setDisable(false);
        }else {
            EName.setStyle("-fx-border-color: red");
            AddNew.setDisable(true);
            updatebtn.setDisable(true);
        }
    }

    public void dobvalide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[0-9,/,-]{6,10}$");
        boolean matches = idPattern.matcher(EDOB.getText()).matches();
        AddNew.setDisable(true);
        updatebtn.setDisable(true);
        if (matches){
            EDOB.setStyle("-fx-border-color:Green");
            AddNew.setDisable(false);
            updatebtn.setDisable(false);
        }else {
            EDOB.setStyle("-fx-border-color: red");
            AddNew.setDisable(true);
            updatebtn.setDisable(true);
        }
    }

    public void empAddressvalide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[A-Z,a-z,,,.,0-9,/, ]{5,40}$");
        boolean matches = idPattern.matcher(EAddress.getText()).matches();
        AddNew.setDisable(true);
        updatebtn.setDisable(true);
        if (matches){
            EAddress.setStyle("-fx-border-color:Green");
            AddNew.setDisable(false);
            updatebtn.setDisable(false);
        }else {
            EAddress.setStyle("-fx-border-color: red");
            AddNew.setDisable(true);
            updatebtn.setDisable(true);
        }
    }

    public void uesIDvalide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^(U)[0-9]{4}$");
        boolean matches = idPattern.matcher(EUID.getText()).matches();
        AddNew.setDisable(true);
        updatebtn.setDisable(true);
        if (matches){
            EUID.setStyle("-fx-border-color:Green");
            AddNew.setDisable(false);
            updatebtn.setDisable(false);
        }else {
            EUID.setStyle("-fx-border-color: red");
            AddNew.setDisable(true);
            updatebtn.setDisable(true);
        }
    }

    public void saleryvalide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[0-9,.]{1,9}$");
        boolean matches = idPattern.matcher(ESalery.getText()).matches();
        AddNew.setDisable(true);
        updatebtn.setDisable(true);
        if (matches){
            ESalery.setStyle("-fx-border-color: Green");
            AddNew.setDisable(false);
            updatebtn.setDisable(false);
        }else {
            ESalery.setStyle("-fx-border-color: red");
            AddNew.setDisable(true);
            updatebtn.setDisable(true);
        }
    }

    public void SearchIDValide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[(E),0-9]{4}$");
        boolean matches = idPattern.matcher(ESalery.getText()).matches();
        searchbtn.setDisable(true);
        if (matches){
            searchbtn.setDisable(false);
        }else {
            searchbtn.setDisable(true);
        }
    }
}
