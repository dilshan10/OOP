package controller;

import model.Customer;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerCrudController {
    public static ArrayList<String> getCustomerIDs() throws SQLException, ClassNotFoundException {
        ResultSet resul=CrudUtil.execute("SELECT cust_id FROM customer"  );
        ArrayList<String> ids=new ArrayList<>();
        while (resul.next()){
            ids.add(resul.getString(1));
        }
        return ids;
    }
    public static Customer getCustomer(String id) throws SQLException, ClassNotFoundException {
        ResultSet result=CrudUtil.execute("SELECT * FROM customer WHERE cust_id=?", id);
        if (result.next()){
            return new Customer(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)
            );
        }
        return null;
    }
}
