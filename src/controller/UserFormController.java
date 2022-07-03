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
import model.User;
import util.CrudUtil;

import java.sql.*;
import java.util.regex.Pattern;

public class UserFormController {
    public AnchorPane Root;
    public Label Date;
    public JFXTextField UID;
    public JFXTextField UPass;
    public JFXTextField UName;
    public TableView tblUser;
    public TableColumn tUID;
    public TableColumn TUName;
    public TableColumn TUPass;
    public TextField SearchID;
    public Button serachbtn;
    public Button saveBtn;
    public Button updateBtn;

    public void initialize(){

        tUID.setCellValueFactory(new PropertyValueFactory("User_ID"));
        TUName.setCellValueFactory(new PropertyValueFactory("User_Name"));
        TUPass.setCellValueFactory(new PropertyValueFactory("User_PassWord"));

        try {
            LoadAllUser();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void LoadAllUser() throws ClassNotFoundException, SQLException {
        ResultSet result= CrudUtil.execute("SELECT * FROM user");
        ObservableList<User> oblist= FXCollections.observableArrayList();
        while (result.next()){
            oblist.add(
                    new User(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3)
                    )
            );
        }
        tblUser.setItems(oblist);
    }

    public void ClickOnNew(ActionEvent actionEvent) {
        User user=new User(
                UID.getText(),UName.getText(),UPass.getText()
        );
        try {
            if(CrudUtil.execute("INSERT INTO user VALUES (?,?,?)", user.getUser_ID(),user.getUser_Name(),user.getUser_PassWord())){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved!...").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void ClickOnUpdate(ActionEvent actionEvent) {
        User user=new User(UID.getText(),UName.getText(),UPass.getText());
        try {
            boolean isUpdate=CrudUtil.execute("UPDATE user SET username=?,password=? WHERE id=?", user.getUser_Name(),user.getUser_PassWord(),user.getUser_ID());
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
        ResultSet result=CrudUtil.execute("SELECT * FROM user WHERE id=?", SearchID.getText());
        if (result.next()){
            UID.setText(result.getString(1));
            UName.setText(result.getString(2));
            UPass.setText(result.getString(3));
        }else {
            new Alert(Alert.AlertType.WARNING,"Empty Result!..").show();
        }
    }

    public void ClickOnDelete(ActionEvent actionEvent) {
        try {
            boolean isDelete=CrudUtil.execute("DELETE FROM user WHERE id=?", SearchID.getText());
            if (isDelete){
                new Alert(Alert.AlertType.CONFIRMATION,"Delete...").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Try Again...").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void UseIDvalide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[(U),0-9]{4}$");
        saveBtn.setDisable(true);
        updateBtn.setDisable(true);
        boolean matches=idPattern.matcher(UID.getText()).matches();
        if(matches){
            UID.setStyle("-fx-border-color:Green");
            saveBtn.setDisable(false);
            updateBtn.setDisable(false);
        }else{
            UID.setStyle("-fx-border-color: red");
            saveBtn.setDisable(true);
            updateBtn.setDisable(true);
        }
    }

    public void usePawordValide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[A-Z,a-z,@,0-9,.]{5,30}$");
        saveBtn.setDisable(true);
        updateBtn.setDisable(true);
        boolean matches=idPattern.matcher(UPass.getText()).matches();
        if(matches){
            UPass.setStyle("-fx-border-color:Green");
            saveBtn.setDisable(false);
            updateBtn.setDisable(false);
        }else{
            UPass.setStyle("-fx-border-color: red");
            saveBtn.setDisable(true);
            updateBtn.setDisable(true);
        }
    }

    public void useNameValide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[A-Z,a-z, ,.,@,0-9,/,-]{5,35}$");
        saveBtn.setDisable(true);
        updateBtn.setDisable(true);
        boolean matches=idPattern.matcher(UName.getText()).matches();
        if(matches){
            UName.setStyle("-fx-border-color:Green");
            saveBtn.setDisable(false);
            updateBtn.setDisable(false);
        }else{
            UName.setStyle("-fx-border-color: red");
            saveBtn.setDisable(true);
            updateBtn.setDisable(true);
        }
    }

    public void searchIDvalide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[(U),0-9]{4}$");
        boolean matches=idPattern.matcher(SearchID.getText()).matches();
        if(matches){
            serachbtn.setStyle("-fx-border-color:Green");
        }else{
            serachbtn.setStyle("-fx-border-color: red");
        }
    }
}
