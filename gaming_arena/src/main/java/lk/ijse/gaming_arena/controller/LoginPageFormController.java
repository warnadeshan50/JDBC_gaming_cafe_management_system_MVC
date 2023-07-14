package lk.ijse.gaming_arena.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPageFormController {
    @FXML
    private JFXTextField username_txt;

    @FXML
    private JFXPasswordField password_txt;

    @FXML
    private JFXButton login_btn;

    public void username_txtOnAction(ActionEvent event) {
    }

    public void password_txtOnAction(ActionEvent event) {
    }

    public void login_btnOnAction(ActionEvent event) throws IOException {
        Scene stage=new Scene(FXMLLoader.load(getClass().getResource("/view/cafe_main_form.fxml")));
        Stage window=(Stage) login_btn.getScene().getWindow();
        window.setScene(stage);
        window.centerOnScreen();
    }
}
