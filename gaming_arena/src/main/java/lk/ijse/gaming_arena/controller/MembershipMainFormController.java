package lk.ijse.gaming_arena.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.gaming_arena.dto.Customer;
import lk.ijse.gaming_arena.dto.Membership;
import lk.ijse.gaming_arena.model.CustomerModel;
import lk.ijse.gaming_arena.model.MembershipModel;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.regex.Pattern;

public class MembershipMainFormController {
    private final static String URL = "jdbc:mysql://localhost:3306/gaming_arena";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    @FXML
    private Label name_lbl;

    @FXML
    private Label address_lbl;

    @FXML
    private Label contact_lbl;

    @FXML
    private Label email_lbl;

    @FXML
    private Label type_lbl;

    @FXML
    private Label monthlypayment_lbl;

    @FXML
    private JFXTextField membership_id_txt;

    @FXML
    private JFXTextField customer_id_txt;

    @FXML
    private JFXButton SearchBymemberId_btn;

    @FXML
    private JFXButton searchByCustomerId_btn;

    static String memberId,customerId;

    public void membership_id_txtOnAction(ActionEvent event) {
        memberId=membership_id_txt.getText();
        memSearch();
    }

    public void customer_id_txtOnAction(ActionEvent event) {
        customerId=customer_id_txt.getText();
    }

    public void membership_id_txtOnKeyReleased(KeyEvent keyEvent) {
        memberId=membership_id_txt.getText();
        Pattern idPattern=Pattern.compile("^(GG|IC)[0-9]{4,5}$");
        boolean matches=idPattern.matcher(memberId).matches();
        if(matches){
            membership_id_txt.setStyle("-fx-border-color:green");
            membership_id_txt.setStyle("-fx-text-fill:yellow");


        }else{
            membership_id_txt.setStyle("-fx-border-color:red");
            membership_id_txt.setStyle("-fx-text-fill:red");
        }
    }

    public void customer_id_txtOnKeyReleased(KeyEvent keyEvent) {
        customerId=customer_id_txt.getText();
        Pattern idPattern=Pattern.compile("^(CU)[0-9]{4,5}$");
        boolean matches=idPattern.matcher(customerId).matches();
        if(matches){
            customer_id_txt.setStyle("-fx-border-color:green");
            customer_id_txt.setStyle("-fx-text-fill:yellow");

        }else{
            customer_id_txt.setStyle("-fx-border-color:red");
            customer_id_txt.setStyle("-fx-text-fill:red");
        }

    }

    public void SearchBymemberId_btnOnAction(ActionEvent event) throws SQLException {
        memberId=membership_id_txt.getText();
        Pattern idPattern=Pattern.compile("^(GG)[0-9]{4,5}$");
        boolean matches=idPattern.matcher(memberId).matches();
        if(matches){
            membership_id_txt.setStyle("-fx-text-fill:yellow");
            memSearch();

        }else{

            membership_id_txt.setStyle("-fx-text-fill:red");
            new Alert(Alert.AlertType.ERROR,"Wrong Member ID!").show();

        }
    }

    public void searchByCustomerId_btnOnAction(ActionEvent event) throws SQLException{
        try {
            customerId=customer_id_txt.getText();
            Membership membership = MembershipModel.searchBycustomerId(customerId);
            if (membership != null) {
                membership_id_txt.setText(membership.getMembership_id());
                customer_id_txt.setText(membership.getCustomer_id());
                type_lbl.setText(membership.getType_of_membership());
                monthlypayment_lbl.setText(String.valueOf(membership.getMonthly_payment()));
                customerId=membership.getCustomer_id();
                System.out.println(customerId+" rrrr");
                searchCustomer(customerId);
            } else {
                new Alert(Alert.AlertType.WARNING, "no membership found ").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
        }
    }

    void searchCustomer(String custID) throws SQLException {
        try {
            Customer customer = CustomerModel.search(custID);
            if (customer != null) {
                customer_id_txt.setText(customer.getCustomer_id());
                name_lbl.setText(customer.getCustomer_name());
                address_lbl.setText(customer.getCustomer_address());
                email_lbl.setText(customer.getCustomer_email());
                contact_lbl.setText(String.valueOf(customer.getCustomer_contact()));
                customerId=customer.getCustomer_id();
            } else {
                new Alert(Alert.AlertType.WARNING, "no Customer found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
        }

    }

    public void show_all_btnOnAction(ActionEvent event) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/show_all_membership_form.fxml"));
        Scene scene=new Scene(parent);
        Stage stage=new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Show All memberships");
        stage.centerOnScreen();
        stage.show();
    }

    public void add_mem_btnOnAction(ActionEvent event) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/add_membership_form.fxml"));
        Scene scene=new Scene(parent);
        Stage stage=new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Add memberships");
        stage.centerOnScreen();
        stage.show();
    }

    public void more_btn_OnAction(ActionEvent event) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/update_membership_form.fxml"));
        Scene scene=new Scene(parent);
        Stage stage=new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Update memberships");
        stage.centerOnScreen();
        stage.show();
    }
    void memSearch() {
        try {
            Membership membership = MembershipModel.search(memberId);
            if (membership != null) {
                membership_id_txt.setText(membership.getMembership_id());
                customer_id_txt.setText(membership.getCustomer_id());
                type_lbl.setText(membership.getType_of_membership());
                monthlypayment_lbl.setText(String.valueOf(membership.getMonthly_payment()));
                customerId=membership.getCustomer_id();
                System.out.println(customerId+" rrrr");
                searchCustomer(customerId);
            } else {
                new Alert(Alert.AlertType.WARNING, "no membership found ").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
        }
    }

}
