package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;

public class LoginFromController {
    public TextField UserName;
    public AnchorPane Root;
    public PasswordField Password;

    public void ClickOnCancel(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/StartFrom.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) Root.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void ClickOnLogin(ActionEvent actionEvent)  {
        try {
            Login();
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    public void ClickOnTxtLogin(ActionEvent actionEvent) {
        try {
            Login();
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void Login() throws ClassNotFoundException, SQLException, IOException {
        String sql="SELECT * FROM user WHERE username='"+UserName.getText()+"'AND password='"+Password.getText()+"'";
        PreparedStatement stm= DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet result=stm.executeQuery(sql);
        if (result.next()){
            URL resource = getClass().getResource("../View/DashBordForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Stage window = (Stage) Root.getScene().getWindow();
            window.setScene(new Scene(load));
        }else {
            new Alert(Alert.AlertType.WARNING,"Pleace Check your UserName Password!...").show();
        }
    }
}
