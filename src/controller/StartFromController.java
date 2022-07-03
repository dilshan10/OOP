package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class StartFromController {
    public AnchorPane StartFrom;
    public Label Time;
    public Label Year;

    public void initialize(){
        DateAndTime();
    }

    private void DateAndTime() {
        Year.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
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

    public void ClickOnLogin(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../View/LoginFrom.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) StartFrom.getScene().getWindow();
        window.setScene(new Scene(load));
    }
}
