package controller;

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
import model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.CrudUtil;
import view.tm.BillTM;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

public class BillPrintFormcontroller {
    public Label Date;
    public Label Time;
    public ComboBox<String> cmbCustomerID;
    public ComboBox<String> cmbProductID;
    public TextField CName;
    public TextField CAddress;
    public TextField CtelNumber;
    public TextField PName;
    public TextField PType;
    public TextField PCount;
    public TextField PValue;
    public TextField QTY;
    public Button AddToBill;
    public TableView <BillTM>tblBille;
    public TableColumn TPID;
    public TableColumn TPName;
    public TableColumn TPTYPE;
    public TableColumn TPUP;
    public TableColumn TQTY;
    public TableColumn TTC;
    public TableColumn TOPTION;
    public Label showTotal;
    public Label Example;
    public Label OrderId;
    public Label PayID;

    public void initialize()  {

        TPID.setCellValueFactory(new PropertyValueFactory<>("id"));
        TPName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TPTYPE.setCellValueFactory(new PropertyValueFactory<>("type"));
        TPUP.setCellValueFactory(new PropertyValueFactory<>("unitPrize"));
        TQTY.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        TTC.setCellValueFactory(new PropertyValueFactory<>("TotalPrize"));
        TOPTION.setCellValueFactory(new PropertyValueFactory<>("btn"));


        DateAndTime();
        setCustomerID();
        setProductID();
        setOrderId();
        setPaymentId();
        //----------------------------------------------------------------------------------------
        cmbCustomerID.getSelectionModel().selectedItemProperty().addListener((observable,oldVale,newVale)->{
            setCustomerDetails( newVale);
        });
        cmbProductID.getSelectionModel().selectedItemProperty().addListener((observable,oldVale,newVale)->{
            setProductDetails( newVale);
        });
        AddToBill.setDisable(true);
        //-----

    }

    private void setPaymentId() {
        String PYID = null;
        try {
            PYID = new OrderCrudController().getPaymentID();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (PYID != null) {
            String[] temparr = PYID.split("PY");
            int tempNumber = Integer.parseInt(temparr[1]);
            tempNumber+=1;
            if (tempNumber<10){
                PayID.setText("PY00"+tempNumber);
            }else if(tempNumber<100){
                PayID.setText("PY0"+tempNumber);
            }else {
                PayID.setText("PY"+tempNumber);
            }
        }else{
            PayID.setText("PY001");
        }
    }

    private void setOrderId(){
        String OrID = null;
        try {
            OrID = new OrderCrudController().getOderID();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (OrID != null) {
            String[] temparr = OrID.split("OR");
            int tempNumber = Integer.parseInt(temparr[1]);
            tempNumber+=1;
            if (tempNumber<10){
                OrderId.setText("OR00"+tempNumber);
            }else if(tempNumber<100){
                OrderId.setText("OR0"+tempNumber);
            }else {
                OrderId.setText("OR"+tempNumber);
            }
        }else{
            OrderId.setText("OR001");
        }

    }

    private void setCustomerDetails(String selectedCustomerId) {
        try {
            Customer c=CustomerCrudController.getCustomer(selectedCustomerId);
           if (c!=null){
               CName.setText(c.getCust_Name());
               CAddress.setText(c.getCust_Address());
               CtelNumber.setText(c.getCust_TelNUmber());
            }else{
               new Alert(Alert.AlertType.WARNING,"Empy Relust..").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setProductDetails(String selectProductID) {
        try {
            Products p=ProductsCrud.getItem(selectProductID);
            if (p!=null){
                PName.setText(p.getPro_Name());
                PType.setText(p.getPro_Type());
                PCount.setText(String.valueOf(p.getPro_Count()));
                PValue.setText(String.valueOf(p.getUnit_Prize()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void DateAndTime() {
        Date.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        Timeline Clock =new Timeline(new KeyFrame(Duration.ZERO, e ->{
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

    private void setCustomerID() {
        try {
            ObservableList<String> cidOblist=FXCollections.observableArrayList(
                    CustomerCrudController.getCustomerIDs()
            );
            cmbCustomerID.setItems(cidOblist);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setProductID() {
        try {
            ObservableList<String> pidOblist=FXCollections.observableArrayList(
                    ProductsCrud.getItemCodes()
            );
            cmbProductID.setItems(pidOblist);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void QtyValide(KeyEvent keyEvent) {
        Pattern AddressPattern = Pattern.compile("^[0-9]{1,4}$");
        boolean matches=AddressPattern.matcher(QTY.getText()).matches();
        if (matches){
            QTY.setStyle("-fx-border-color:Green");
            AddToBill.setDisable(false);
        }else {
            QTY.setStyle("-fx-border-color: red");
            AddToBill.setDisable(true);
        }
        setQtyValue();
    }

    private void setQtyValue() {
        double total=Double.parseDouble(PValue.getText())*Integer.parseInt(QTY.getText());
        Example.setText(String.valueOf(total));
    }

    ObservableList<BillTM> tmList=FXCollections.observableArrayList();

    public void AddToBillOnAction(ActionEvent actionEvent) {
        double UnitPrize=Double.parseDouble(PValue.getText());
        int Qty=Integer.parseInt(QTY.getText());
        double totalCost=UnitPrize*Qty;

        BillTM isEx=isExists(cmbProductID.getValue());

        if (isEx!=null) {
         for (BillTM temp:tmList){
             if (temp.equals(isEx)){
                 temp.setQty((temp.getQty()+Qty));
                 temp.setTotalPrize(temp.getTotalPrize()+totalCost);
             }
         }
        }else {
            Button button=new Button("Remove");

            BillTM tm=new BillTM(
                    cmbProductID.getValue(),
                    PName.getText(),
                    PType.getText(),
                    UnitPrize,
                    Qty,
                    totalCost,
                    button
            );
            button.setOnAction(event -> {
                        tmList.remove(tm);
                        setTotal();
            });

            tmList.add(tm);
            tblBille.setItems(tmList);
        }

        tblBille.refresh();
        setTotal();
    }
    private BillTM isExists(String Pcode){
        for (BillTM tm:tmList){
            if (tm.getId().equals(Pcode)){
                return tm;
            }
        }
        return null;
    }
    private void setTotal(){
        double total=0;
        for (BillTM tm:tmList){
            total+=tm.getTotalPrize();
        }
        showTotal.setText(String.valueOf(total));
    }

    public void placeBillOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {


        Order order =new Order(
                OrderId.getText(),
                cmbCustomerID.getValue()
        );
        Payment payment=new Payment(
                PayID.getText(),
                Time.getText(),
                Date.getText(),
                Double.parseDouble(showTotal.getText()),
                0.0
        );
        ArrayList<OrderDetails> details=new ArrayList();
        for (BillTM tm:tmList){
            details.add(
                    new OrderDetails(
                            OrderId.getText(),
                            tm.getId(),
                            tm.getQty(),
                            PayID.getText()
                    )
            );
        }
        Connection con=null;
        try {
            con= DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            boolean isOrderSaved=new OrderCrudController().saveOrder(order);

            if (isOrderSaved){
                boolean isPayMentSave=new OrderCrudController().savePayMent(payment);
                if (isPayMentSave){
                    boolean isDetailsSaved=new OrderCrudController().saveOrderDetails(details);
                    if (isDetailsSaved){
                        con.commit();
                        jasperReport();
                        Clear();
                    }else {
                        con.rollback();
                        new Alert(Alert.AlertType.ERROR,"Error").show();
                    }
                }
            }
        }catch (SQLException | ClassNotFoundException e){
            System.out.println(e);
        }
    }
    public void Clear(){
        CName.clear();
        CAddress.clear();
        CtelNumber.clear();
        setOrderId();
        setPaymentId();
        PName.clear();
        PType.clear();
        PCount.clear();
        PValue.clear();
        QTY.clear();
        Example.setText("0.0");
        showTotal.setText("0.0");
        tblBille.getItems().clear();
    }

    private void jasperReport() {

        String CusID=cmbCustomerID.getValue();
        String CusName=CName.getText();
        String CusAddress=CAddress.getText();
        String CusTelNum=CtelNumber.getText();
        String Orid=OrderId.getText();
        String Payid=PayID.getText();
        String total=showTotal.getText();

        HashMap paramMap=new HashMap();
        paramMap.put("Customer ID",CusID);
        paramMap.put("Customer Name",CusName);
        paramMap.put("Customer Address",CusAddress);
        paramMap.put("Tell Number",CusTelNum);
        paramMap.put("ORID",Orid);
        paramMap.put("PYID",Payid);
        paramMap.put("Total Value",total);

        try {
            JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("../view/reports/Asia.jasper"));
            ObservableList<BillTM>itams=tblBille.getItems();
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport,paramMap,new JRBeanCollectionDataSource(itams, false));
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
