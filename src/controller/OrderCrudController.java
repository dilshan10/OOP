package controller;

import model.Order;
import model.OrderDetails;
import model.Payment;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderCrudController {
   public boolean saveOrder(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO takeoder VALUES (?,?)",order.getOrdr_ID(),order.getCum_ID());
   }
   public boolean savePayMent(Payment payment) throws SQLException, ClassNotFoundException {
       return CrudUtil.execute("INSERT INTO payment VALUES (?,?,?,?,?)",payment.getPay_ID(),payment.getTime(),payment.getDate(),payment.getInComeValue(),payment.getOutgoingValue());
   }
   public boolean saveOrderDetails(ArrayList<OrderDetails>details) throws SQLException, ClassNotFoundException {
        for (OrderDetails det:details){
            boolean isDetailsSave=CrudUtil.execute("INSERT INTO orderdetails VALUES (?,?,?,?)",det.getOrder_ID(),det.getProduct_ID(),det.getQty(),det.getPayment_ID() );
            if (isDetailsSave){
                if(!updateQty(det.getProduct_ID(),det.getQty())){
                    return false;
                }
            }else {
                return false;
            }
        }
        return true;
   }

   private boolean updateQty(String product_id, int qty) throws SQLException, ClassNotFoundException {
       return CrudUtil.execute("UPDATE  product SET count=count-? WHERE id=?",qty,product_id );
   }
   public String getOderID() throws SQLException, ClassNotFoundException {
       ResultSet set=CrudUtil.execute("SELECT orde_id FROM takeoder ORDER BY orde_id DESC LIMIT 1");
       if (set.next()){
           return set.getString(1);
       }else {
           return "OR001";
       }
   }
   public String getPaymentID() throws SQLException, ClassNotFoundException {
       ResultSet set=CrudUtil.execute("SELECT pay_id FROM payment ORDER BY pay_id DESC LIMIT 1");
       if (set.next()){
           return set.getString(1);
       }else {
           return "PY001";
       }
   }
}
