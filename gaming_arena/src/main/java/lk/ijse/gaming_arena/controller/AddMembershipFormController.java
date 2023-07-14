package lk.ijse.gaming_arena.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lk.ijse.gaming_arena.dto.Membership;
import lk.ijse.gaming_arena.model.CustomerModel;
import lk.ijse.gaming_arena.model.MembershipModel;
import lk.ijse.gaming_arena.model.OrderModel;
import lk.ijse.gaming_arena.model.ProductModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddMembershipFormController implements Initializable {
    @FXML
    private Label payment_lbl;

    @FXML
    private Label member_id_lbl;

    @FXML
    private JFXComboBox<String> cust_id_cmb;

    @FXML
    private JFXComboBox<String> type_cmb;

    @FXML
    private JFXButton new_customer_btn;

    @FXML
    private JFXButton add_btn;

    @FXML
    private JFXButton cancel_btn;

    public void cust_id_cmbOnAction(ActionEvent event) {

    }

    public void new_customer_btn(ActionEvent event) {
    }

    public void add_btnOnAction(ActionEvent event) {
        String mem_id=member_id_lbl.getText();
        String cus_id=cust_id_cmb.getValue();
        String type=type_cmb.getValue();
        Double payment=Double.valueOf(payment_lbl.getText());
        try{
            boolean isSaved = MembershipModel.save(mem_id,cus_id,type,payment);
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Membership saved!!!").show();
                generateNextMemberId();
                payment_lbl.setText("Choose type for payment");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened").show();
        }
    }

    public void cancel_btnOnAction(ActionEvent event) {
        Stage stage = (Stage) cancel_btn.getScene().getWindow();
        stage.close();
    }
    public void type_cmbOnAction(ActionEvent event) {
        String type=type_cmb.getSelectionModel().getSelectedItem();
        if (type=="Iorn"){
            payment_lbl.setText("1000.00");
        }if (type=="Silver"){
            payment_lbl.setText("1500.00");
        }if (type=="Gold"){
            payment_lbl.setText("2000.00");
        }if(type=="Platinum"){
            payment_lbl.setText("2500.00");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateNextMemberId();
        loadCustomerIds();
        loadTypes();

    }
    private void loadCustomerIds() {
        try {
            List<String> ids = CustomerModel.getIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            cust_id_cmb.setItems(obList);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
    private void loadTypes() {
        ObservableList<String> types=FXCollections.observableArrayList();

        types.add("Iorn");
        types.add("Silver");
        types.add("Gold");
        types.add("Platinum");
        type_cmb.setItems(types);
    }
    private void generateNextMemberId() {
        try {
            String nextId = MembershipModel.generateNextMemberId();
            member_id_lbl.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
