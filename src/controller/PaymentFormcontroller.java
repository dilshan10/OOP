package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import model.Payment;
import util.CrudUtil;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.regex.Pattern;

public class PaymentFormcontroller {
    public JFXTextField PYID;
    public JFXTextField PYIN;
    public JFXTextField PYOUT;
    public Label PYTIME;
    public Label PYDATE;
    public TextField SearchID;
    public TableView<Payment> tblPayment;
    public TableColumn TPYID;
    public TableColumn TPYTime;
    public TableColumn TPYDate;
    public TableColumn TPYICV;
    public TableColumn TPYOGV;
    public Button serachbtn;
    public Button saveBtn;
    public Button updateBtn;

    public void initialize(){
        TPYID.setCellValueFactory(new PropertyValueFactory("Pay_ID"));
        TPYTime.setCellValueFactory(new PropertyValueFactory("time"));
        TPYDate.setCellValueFactory(new PropertyValueFactory("date"));
        TPYICV.setCellValueFactory(new PropertyValueFactory("inComeValue"));
        TPYOGV.setCellValueFactory(new PropertyValueFactory("OutgoingValue"));

        DateAndTime();
        try {
            LoadAllPayment();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void DateAndTime() {
        PYDATE.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        Timeline Clock =new Timeline(new KeyFrame(Duration.ZERO, e ->{
            LocalTime currentTime = LocalTime.now();
            PYTIME.setText(currentTime.getHour()+":"+
                    currentTime.getMinute()+":"+
                    currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        Clock.setCycleCount(Animation.INDEFINITE);
        Clock.play();
    }

    private void LoadAllPayment() throws ClassNotFoundException, SQLException {
        ResultSet result= CrudUtil.execute("SELECT * FROM payment");
        ObservableList<Payment> oblist= FXCollections.observableArrayList();
        while (result.next()){
            oblist.add(
                    new Payment(
                            result.getString("pay_id"),
                            result.getString("time"),
                            result.getString("date"),
                            result.getDouble("inComevalue"),
                            result.getDouble("outgongValue")
                    )
            );
        }
        tblPayment.setItems(oblist);
    }

    public void ClickOnNew(ActionEvent actionEvent) {
        Payment payment=new Payment(
                PYID.getText(), PYTIME.getText(), PYDATE.getText(), Double.parseDouble(PYIN.getText()), Double.parseDouble(PYOUT.getText())
        );
        try {
            if(CrudUtil.execute("INSERT INTO payment VALUES (?,?,?,?,?)", payment.getPay_ID(),payment.getTime(),payment.getDate(),payment.getInComeValue(),payment.getOutgoingValue())){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved!...").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void ClickOnUpdate(ActionEvent actionEvent) {
        Payment payment=new Payment(
                PYID.getText(), PYTIME.getText(), PYDATE.getText(), Double.parseDouble(PYIN.getText()), Double.parseDouble(PYOUT.getText())
        );
        try {
            boolean isUpdate=CrudUtil.execute("UPDATE payment SET time=?,date=?,inComevalue=?,outgongValue=? WHERE pay_id=?",payment.getTime(),payment.getDate(),payment.getInComeValue(),payment.getOutgoingValue(),payment.getPay_ID());
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
        ResultSet result=CrudUtil.execute("SELECT * FROM payment WHERE pay_id=?", SearchID.getText());
        if (result.next()){
            PYID.setText(result.getString(1));
            PYTIME.setText(result.getString(2));
            PYDATE.setText(result.getString(3));
            PYIN.setText(result.getString(4));
            PYOUT.setText(result.getString(5));
        }else {
            new Alert(Alert.AlertType.WARNING,"Empty Result!..").show();
        }
    }

    public void ClickOnDelete(ActionEvent actionEvent) {
        try {
            boolean isDelete=CrudUtil.execute("DELETE FROM payment WHERE pay_id=?", SearchID.getText());
            if (isDelete){
                new Alert(Alert.AlertType.CONFIRMATION,"Delete...").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Try Again...").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
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

    public void inComeValide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[0-9,.]{5,10}$");
        saveBtn.setDisable(true);
        updateBtn.setDisable(true);
        boolean matches=idPattern.matcher(PYIN.getText()).matches();
        if(matches){
            PYIN.setStyle("-fx-border-color:Green");
            saveBtn.setDisable(false);
            updateBtn.setDisable(false);
        }else{
            PYIN.setStyle("-fx-border-color: red");
            saveBtn.setDisable(true);
            updateBtn.setDisable(true);
        }
    }

    public void outgoingValide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[0-9,.]{5,10}$");
        saveBtn.setDisable(true);
        updateBtn.setDisable(true);
        boolean matches=idPattern.matcher(PYOUT.getText()).matches();
        if(matches){
            PYOUT.setStyle("-fx-border-color:Green");
            saveBtn.setDisable(false);
            updateBtn.setDisable(false);
        }else{
            PYOUT.setStyle("-fx-border-color: red");
            saveBtn.setDisable(true);
            updateBtn.setDisable(true);
        }
    }

    public void serachIDvalide(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^[(PY),0-9]{5}$");
        serachbtn.setDisable(true);
        boolean matches=idPattern.matcher(PYID.getText()).matches();
        if(matches){
            serachbtn.setDisable(false);
        }else{
            serachbtn.setDisable(true);
        }
    }
}
