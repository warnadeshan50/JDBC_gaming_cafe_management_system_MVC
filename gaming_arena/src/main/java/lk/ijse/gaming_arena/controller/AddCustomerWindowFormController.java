package lk.ijse.gaming_arena.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lk.ijse.gaming_arena.dto.Employee;
import lk.ijse.gaming_arena.model.CustomerModel;
import lk.ijse.gaming_arena.model.EmployeeModel;
import lk.ijse.gaming_arena.model.MembershipModel;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddCustomerWindowFormController implements Initializable {

    @FXML
    private JFXTextField name_txt;

    @FXML
    private JFXTextField address_txt;

    @FXML
    private JFXTextField contact_txt;

    @FXML
    private JFXTextField email_txt;

    @FXML
    private JFXButton cancle_btn;

    @FXML
    private JFXTextField customer_id_txt;

    @FXML
    private JFXComboBox<String> emplyee_id_cmb;
    @FXML
    private Label customer_id_lbl;

    static String name;
    static String address;
    static String contact;
    static String email;
    static String customer_id;
    static String employee_id;

    public void name_txtOnAction(ActionEvent event) {
        name=name_txt.getText();
    }

    public void address_txtOnAction(ActionEvent event) {
        address=address_txt.getText();
    }

    public void contact_txt_OnAction(ActionEvent event) {
        contact=contact_txt.getText();
    }

    public void email_txtOnAction(ActionEvent event) {
        email=email_txt.getText();
        email=email_txt.getText();
        Pattern emailPattern=Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
        boolean matches=emailPattern.matcher(email).matches();
        if(matches){
            email_txt.setStyle("-fx-text-fill:yellow");
        }else {

            email_txt.setStyle("-fx-text-fill:red");
            new Alert(Alert.AlertType.ERROR, "waradi bijjo !!!").show();
        }
    }
    public void employee_id_cmbOnAction(ActionEvent event){
        employee_id=emplyee_id_cmb.getValue();
        System.out.println(employee_id);
    }
    public void customer_id_txtOnAction(ActionEvent event) {
        customer_id=customer_id_txt.getText();
    }

    public void add_btnOnAction(ActionEvent event) throws SQLException {
        customer_id=customer_id_lbl.getText();
        employee_id=emplyee_id_cmb.getValue();
        name=name_txt.getText();
        address=address_txt.getText();
        contact=contact_txt.getText();
        email=email_txt.getText();
        try{
            boolean isSaved = CustomerModel.add(customer_id,employee_id,name,address,contact,email);
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "customer saved!!!").show();
                generateNextCustomerId();
                name_txt.setText("");
                address_txt.setText("");
                contact_txt.setText("");
                email_txt.setText("");

            }
        } catch (SQLException e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened").show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    generateNextCustomerId();
    loadEmployeeIds();
    }

    public void name_txtOnKeyReleased(KeyEvent keyEvent) {
        name=name_txt.getText();
        Pattern namePattern=Pattern.compile("^[A-Z][a-zA-z ]{1,29}$");
        boolean matches=namePattern.matcher(name).matches();
        if(matches){
            name_txt.setStyle("-fx-text-fill:yellow");
        }else{

            name_txt.setStyle("-fx-text-fill:red");
        }
    }

    public void address_txtOnKeyReleased(KeyEvent keyEvent) {
    }

    public void contact_txt_OnKeyReleased(KeyEvent keyEvent) {
        contact=contact_txt.getText();
        Pattern contactPattern=Pattern.compile("^[0-9]{10}$");
        boolean matches=contactPattern.matcher(contact).matches();
        if(matches){
            contact_txt.setStyle("-fx-text-fill:yellow");
        }else{

            contact_txt.setStyle("-fx-text-fill:red");
        }
    }

    public void email_txtOnKeyReleased(KeyEvent keyEvent) {
        email=email_txt.getText();
        Pattern emailPattern=Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
        boolean matches=emailPattern.matcher(email).matches();
        if(matches){
            email_txt.setStyle("-fx-text-fill:yellow");
        }else{

            email_txt.setStyle("-fx-text-fill:red");
           // new Alert(Alert.AlertType.ERROR,"waradi bijjo !!!").show();

        }
    }


    private void generateNextCustomerId() {
        try {
            String nextId = CustomerModel.generateNextCustomerId();
            customer_id_lbl.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadEmployeeIds() {
        try {
            List<String> ids = EmployeeModel.getEmployeeIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            emplyee_id_cmb.setItems(obList);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void cancel_btnOnAction(ActionEvent event) {
        Stage stage = (Stage) cancle_btn.getScene().getWindow();
        stage.close();
    }
}
