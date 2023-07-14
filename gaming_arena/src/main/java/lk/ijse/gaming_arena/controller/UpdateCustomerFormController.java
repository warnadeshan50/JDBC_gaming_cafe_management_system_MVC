package lk.ijse.gaming_arena.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lk.ijse.gaming_arena.dto.Customer;
import lk.ijse.gaming_arena.dto.Product;
import lk.ijse.gaming_arena.model.CustomerModel;
import lk.ijse.gaming_arena.model.ProductModel;

import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class UpdateCustomerFormController implements Initializable {

    @FXML
    private JFXTextField name_txt;

    @FXML
    private JFXTextField address_txt;

    @FXML
    private JFXTextField contact_txt;

    @FXML
    private JFXTextField email_txt;

    @FXML
    private JFXButton update_btn;

    @FXML
    private JFXButton delete_btn;

    @FXML
    private JFXButton cancel_btn;

    @FXML
    private JFXTextField customer_id_txt;

    @FXML
    private JFXTextField employee_id_txt;


    static String id,em_id,name,address,con,mail;

    public void name_txtOnAction(ActionEvent event) {
        name=name_txt.getText();
        Pattern namePattern=Pattern.compile("^[A-Z][a-zA-z ]{1,29}$");
        boolean matches=namePattern.matcher(name).matches();
        if(matches){
            name_txt.setStyle("-fx-text-fill:yellow");
        }else{

            name_txt.setStyle("-fx-text-fill:red");
        }
    }

    public void address_txtOnAction(ActionEvent event) {

    }

    public void contact_txt_OnAction(ActionEvent event) {
        con=contact_txt.getText();
        Pattern contactPattern=Pattern.compile("^[0-9]{10}$");
        boolean matches=contactPattern.matcher(con).matches();
        if(matches){
            contact_txt.setStyle("-fx-text-fill:yellow");
        }else{

            contact_txt.setStyle("-fx-text-fill:red");
        }
    }

    public void email_txtOnAction(ActionEvent event) {
        mail=email_txt.getText();
        Pattern emailPattern=Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
        boolean matches=emailPattern.matcher(mail).matches();
        if(matches){
            email_txt.setStyle("-fx-text-fill:yellow");
        }else{

            email_txt.setStyle("-fx-text-fill:red");
            // new Alert(Alert.AlertType.ERROR,"waradi bijjo !!!").show();

        }
    }

    public void update_btnOnAction(ActionEvent event)throws SQLException {
        String id = customer_id_txt.getText();
        String emId = employee_id_txt.getText();
        String name = name_txt.getText();
        String address = address_txt.getText();
        String cont= contact_txt.getText();
        String mail= email_txt.getText();
        var customer = new Customer(id,emId,name,address,cont,mail);

        try {
            boolean isUpdated = CustomerModel.update(customer);
            System.out.println(isUpdated);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated!").show();

            }
        } catch (SQLException e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
        }


    }

    public void customer_id_txtOnAction(ActionEvent event) {
        id=customer_id_txt.getText();
        Pattern idPattern=Pattern.compile("^(CU)[0-9]{4,5}$");
        boolean matches=idPattern.matcher(id).matches();
        if(matches){
            customer_id_txt.setStyle("-fx-text-fill:yellow");
        }else{

            customer_id_txt.setStyle("-fx-text-fill:red");
            // new Alert(Alert.AlertType.ERROR,"waradi bijjo !!!").show();

        }
    }

    public void employee_id_txtOnAction(ActionEvent event) {
        em_id=employee_id_txt.getText();
        Pattern idPattern=Pattern.compile("^(EM)[0-9]{4,5}$");
        boolean matches=idPattern.matcher(em_id).matches();
        if(matches){
            employee_id_txt.setStyle("-fx-text-fill:yellow");
        }else{

            employee_id_txt.setStyle("-fx-text-fill:red");
            // new Alert(Alert.AlertType.ERROR,"waradi bijjo !!!").show();

        }
    }

    public void search_btnOnAction(ActionEvent event) throws SQLException {
        String id =customer_id_txt.getText();
        try {
            Customer customer = CustomerModel.search(id);
            if (customer != null) {
                setVisibel(true);
                customer_id_txt.setText(customer.getCustomer_id());
                name_txt.setText(customer.getCustomer_name());
                employee_id_txt.setText(customer.getEmployee_id());
                address_txt.setText(customer.getCustomer_address());
                contact_txt.setText(customer.getCustomer_contact());
                email_txt.setText(customer.getCustomer_email());
            } else {
                new Alert(Alert.AlertType.WARNING, "No Customer found").show();
                setVisibel(true);
            }
        } catch (SQLException e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
        }

    }

    public void delete_btnOnAction(ActionEvent event)throws SQLException {
        try {
            boolean isDeleted = CustomerModel.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer deleted !").show();
            }
        } catch (SQLException e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "something happened !").show();
        }
    }

    public void cancel_btnOnAction(ActionEvent event) {
        Stage stage = (Stage) cancel_btn.getScene().getWindow();
        stage.close();
    }
    void setVisibel(boolean visibel){
        name_txt.setVisible(visibel);
        address_txt.setVisible(visibel);
        contact_txt.setVisible(visibel);
        email_txt.setVisible(visibel);
        employee_id_txt.setVisible(visibel);
        update_btn.setVisible(visibel);
        delete_btn.setVisible(visibel);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setVisibel(false);
    }
}
