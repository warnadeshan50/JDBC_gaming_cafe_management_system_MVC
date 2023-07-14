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
import javafx.stage.Stage;
import lk.ijse.gaming_arena.dto.Membership;
import lk.ijse.gaming_arena.model.MembershipModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class UpdateMembershipFormController implements Initializable {
    @FXML
    private JFXComboBox<String> type_cmb;

    @FXML
    private Label payment_lbl;

    @FXML
    private JFXTextField member_id_txt;

    @FXML
    private JFXTextField customer_id_txt;

    @FXML
    private JFXButton delete_btn;

    @FXML
    private JFXButton update_btn;

    @FXML
    private JFXButton cancel_btn;

    @FXML
    private JFXButton search_btn;

    static String customer_id;
    static String member_id;
    static String type;
    static double payment;

    public void type_cmbOnAction(ActionEvent event) {
        type=type_cmb.getSelectionModel().getSelectedItem();
        if (type=="Iorn"){
            payment_lbl.setText("1000.00");
            payment=Double.valueOf(payment_lbl.getText());
        }if (type=="Silver"){
            payment_lbl.setText("1500.00");
            payment=Double.valueOf(payment_lbl.getText());
        }if (type=="Gold"){
            payment_lbl.setText("2000.00");
            payment=Double.valueOf(payment_lbl.getText());
        }if(type=="Platinum"){
            payment_lbl.setText("2500.00");
            payment=Double.valueOf(payment_lbl.getText());
        }
    }

    public void mem_id_txtOnAction(ActionEvent event) {
        member_id=member_id_txt.getText();
        Pattern idPattern=Pattern.compile("^(GG)[0-9]{4,5}$");
        boolean matches=idPattern.matcher(member_id).matches();
        if(matches){
            member_id_txt.setStyle("-fx-text-fill:yellow");
            member_id_txt.setVisible(true);

            memSearch();
        }else{

            member_id_txt.setStyle("-fx-text-fill:red");
            new Alert(Alert.AlertType.ERROR,"Wrong ID!").show();

        }
    }

    public void cust_id_txtOnAction(ActionEvent event) {
        customer_id=customer_id_txt.getText();
        Pattern idPattern=Pattern.compile("^(CU)[0-9]{4,5}$");
        boolean matches=idPattern.matcher(customer_id).matches();
        if(matches){
            customer_id_txt.setStyle("-fx-text-fill:yellow");
        }else{

            customer_id_txt.setStyle("-fx-text-fill:red");
            new Alert(Alert.AlertType.ERROR,"Worng customer Id !!!").show();

        }
    }

    public void delete_btnOnAction(ActionEvent event) {
        try {
            boolean isDeleted = MembershipModel.delete(member_id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Membership deleted !").show();
                resettext();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "something happened !").show();
        }
    }

    public void update_btnOnAction(ActionEvent event) {
        updateMem();
    }

    public void search_btnOnAction(ActionEvent event) {
        member_id=member_id_txt.getText();
        Pattern idPattern=Pattern.compile("^(GG)[0-9]{4,5}$");
        boolean matches=idPattern.matcher(member_id).matches();
        if(matches){
            member_id_txt.setStyle("-fx-text-fill:yellow");
            customer_id_txt.setVisible(true);
            memSearch();

        }else{

            member_id_txt.setStyle("-fx-text-fill:red");
            new Alert(Alert.AlertType.ERROR,"Wrong Member ID!").show();

        }
    }

    public void cancel_btnOnAction(ActionEvent event) {
        Stage stage = (Stage) cancel_btn.getScene().getWindow();
        stage.close();
    }
    private void loadTypes() {
        ObservableList<String> types= FXCollections.observableArrayList();

        types.add("Iorn");
        types.add("Silver");
        types.add("Gold");
        types.add("Platinum");
        type_cmb.setItems(types);
    }
    public void disableTxt(boolean visble){
        customer_id_txt.setVisible(visble);
        type_cmb.setVisible(visble);
    }
    void memSearch(){
        try {
            Membership membership = MembershipModel.search(member_id);
            if (membership != null) {
                member_id_txt.setText(membership.getMembership_id());
                customer_id_txt.setText(membership.getCustomer_id());
                type_cmb.setValue(membership.getType_of_membership());
                payment_lbl.setText(String.valueOf(membership.getMonthly_payment()));
            } else {
                new Alert(Alert.AlertType.WARNING, "no item found :(").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
        }
    }
    void updateMem(){
        String customer_id= customer_id_txt.getText();
        var membership = new Membership(member_id,customer_id,type,payment);   //type inference

        try {
            boolean isUpdated = MembershipModel.update(membership);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Updated!").show();
                resettext();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
        }
    }
    void resettext(){
        member_id_txt.setText("");
        disableTxt(true);
        type_cmb.setValue("Choose");
        payment_lbl.setText("Enter membershop id to update info");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTypes();
        disableTxt(true);
    }
}
