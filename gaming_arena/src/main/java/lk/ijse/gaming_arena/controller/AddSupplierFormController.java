package lk.ijse.gaming_arena.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import lk.ijse.gaming_arena.dto.Supplier;
import lk.ijse.gaming_arena.model.CustomerModel;
import lk.ijse.gaming_arena.model.SupplierModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddSupplierFormController implements Initializable {
    @FXML
    private Label sup_id_lbl;

    @FXML
    private JFXTextField name_txt;

    @FXML
    private JFXTextField company_txt;

    @FXML
    private JFXTextField contact_txt;

    @FXML
    private JFXTextField email_txt;

    @FXML
    private JFXButton add_btn;

    @FXML
    private JFXButton cancle_btn;

    public void name_txtOnKeyReleased(KeyEvent keyEvent) {
    }

    public void company_txtOnKeyReleased(KeyEvent keyEvent) {
    }

    public void contact_txtOnKeyReleased(KeyEvent keyEvent) {
    }

    public void email_txtOnKeyReleased(KeyEvent keyEvent) {
    }

    public void add_btnOnAction(ActionEvent event){
        String id=sup_id_lbl.getText();
        String name=name_txt.getText();
        String contact=contact_txt.getText();
        String company=company_txt.getText();
        String email=email_txt.getText();
        try{
            boolean isSaved = SupplierModel.add(id,name,company,contact,email);
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "customer saved!!!").show();
                generateNextSupplierId();
                name_txt.setText("");
                company_txt.setText("");
                contact_txt.setText("");
                email_txt.setText("");

            }
        } catch (SQLException e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened").show();
        }
    }

    public  void cancel_btnOnAction(ActionEvent event) {
    }
    private void generateNextSupplierId() {
        try {
            String nextId = SupplierModel.generateNextSupplierId();
            sup_id_lbl.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateNextSupplierId();
    }
}
