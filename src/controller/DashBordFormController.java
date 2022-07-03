package controller;

import com.sun.deploy.util.FXLoader;
import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Products;
import util.CrudUtil;
import util.Loader;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class DashBordFormController<PieChartData> implements Loader {
    public Label Date;
    public Label Time;
    public AnchorPane Root;
    public Label IncomeShow;
    public Label CostShow;
    public Label InComeRete;
    public AnchorPane Window;
    public BarChart<String,Integer>BarChar;
    public CategoryAxis x;
    public NumberAxis y;
    public PieChart Piechart;

    public void initialize(){
        DateAndTime();
        setIncomeValues();
        setOutGoingValues();
        setInComeRate();
        setBarChar();
        setPieChart();
    }

    private void setInComeRate() {
        double rete=((Double.parseDouble(IncomeShow.getText())-Double.parseDouble(CostShow.getText()))/Double.parseDouble(IncomeShow.getText()))*100;
        InComeRete.setText(rete+"%");
    }

    private void setOutGoingValues() {
        try {
            String sql="SELECT sum(outgongValue) FROM payment";
            PreparedStatement stm= DBConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet result=stm.executeQuery();
            if (result.next()){
                CostShow.setText(result.getString(1));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void setIncomeValues() {
        try {
            String sql="SELECT sum(inComevalue) FROM payment";
            PreparedStatement stm= DBConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet result=stm.executeQuery();
            if (result.next()){
                IncomeShow.setText(result.getString(1));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void DateAndTime() {
        Date.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        Timeline Clock =new Timeline(new KeyFrame(Duration.ZERO,e ->{
            LocalTime currentTime = LocalTime.now();
            Time.setText(currentTime.getHour()+":"+
                    currentTime.getMinute()+":"+
                    currentTime.getSecond());
        }),
               new KeyFrame(Duration.seconds(1))
                );
        Clock.setCycleCount(Animation.INDEFINITE);
        Clock.play();
    }

    public void ClickOnLogOut(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/StartFrom.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window =(Stage) Root.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void ClickOnProducts(ActionEvent actionEvent) throws IOException {
        LoadUi("ProductsForm");
    }

    public void ClickOnCustomer(ActionEvent actionEvent) throws IOException {
        LoadUi("CustomerForm");
    }

    public void ClickOnEmployee(ActionEvent actionEvent) throws IOException {
        LoadUi("EmployeeForm");
    }

    public void ClickOnUser(ActionEvent actionEvent) throws IOException {
        LoadUi("UserForm");
    }

    public void ClickOnSupplier(ActionEvent actionEvent) throws IOException {
        LoadUi("SupplierForm");
    }

    public void ClickOnOrderDetails(ActionEvent actionEvent) throws IOException {
        LoadUi("OderDetailsForm");
    }

    public void ClickOnPayment(ActionEvent actionEvent) throws IOException {
        LoadUi("PaymentForm");
    }

    public void ClickOnHomeDelivery(ActionEvent actionEvent) throws IOException {
        LoadUi("HomeDeliveryForm");
    }

    public void ClickOnOrder(ActionEvent actionEvent) throws IOException {
        LoadUi("OrderForm");
    }
    public void ClickOnSupplyDetails(ActionEvent actionEvent) throws IOException {
        LoadUi("SupplyDetailsForm");
    }

    @Override
    public void LoadUi(String Location) throws IOException {
        Window.getChildren().clear();
        Parent parent=FXMLLoader.load(getClass().getResource("../view/"+Location+".fxml"));
        Window.getChildren().add(parent);
    }

    public void ClickOnDashBord(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/DashBordForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window =(Stage) Root.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void ClickOnPrintbill(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/BillPrintForm.fxml"));
        Stage stage=new Stage();
        stage.setScene(new Scene(load));
        stage.show();
    }

    public void setBarChar() {
        XYChart.Series<String,Integer> series=new XYChart.Series<>();
        try {
            ResultSet result=CrudUtil.execute("SELECT name,count FROM product");
            while (result.next()){
                series.getData().add(new XYChart.Data<>(result.getString(1),result.getInt(2)));
            }
            BarChar.getData().add(series);
            BarChar.setStyle("-fx-border-color: Green;");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setPieChart() {
       ObservableList <PieChart.Data>pieData=FXCollections.observableArrayList(
               new PieChart.Data("inCiome",Integer.parseInt(IncomeShow.getText())),
               new PieChart.Data("out", Integer.parseInt(CostShow.getText()))
       );
       Piechart.setData(pieData);
       Piechart.setStyle("-fx-border-color: green");
    }
}
