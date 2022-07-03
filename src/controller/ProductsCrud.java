package controller;

import model.Products;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductsCrud {
        public static ArrayList<String> getItemCodes() throws ClassNotFoundException, SQLException {
            ResultSet result = CrudUtil.execute("SELECT id FROM product");
            ArrayList<String> codeList = new ArrayList<>();
            while (result.next()) {
                codeList.add(result.getString(1));
            }
            return codeList;
        }

        public static Products getItem(String code) throws SQLException, ClassNotFoundException {
            ResultSet result = CrudUtil.execute("SELECT * FROM product WHERE id=?",code);
            if (result.next()) {
                return new Products(
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getInt(4),
                        result.getDouble(5)
                );
            }
            return null;
        }
}
