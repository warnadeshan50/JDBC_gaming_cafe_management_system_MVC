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
import lk.ijse.gaming_arena.dto.Supplier;
import lk.ijse.gaming_arena.model.CustomerModel;
import lk.ijse.gaming_arena.model.ProductModel;
import lk.ijse.gaming_arena.model.StockDetailsModel;
import lk.ijse.gaming_arena.model.SupplierModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class SupplyItemsFormController implements Initializable {
    @FXML
    private Label date_lbl;

    @FXML
    private JFXComboBox<String> item_cmb;

    @FXML
    private JFXComboBox<String> supply_cmb;

    @FXML
    private JFXTextField num_of_unit_txt;

    @FXML
    private JFXTextField one_price_txt;

    @FXML
    private JFXButton add_btn;


    public void item_cmbOnAction(ActionEvent event) {

    }

    public void supply_cmbOnAction(ActionEvent event) {
    }

    public void num_of_unit_txtOnKeyReleased(KeyEvent keyEvent) {
    }

    public void onePrice_txtOnKeyReleased(KeyEvent keyEvent) {
    }

    public void add_btnOnAction(ActionEvent event) {
        String item_id=item_cmb.getValue();
        String supplier_id=supply_cmb.getValue();
        int num_of_unit=Integer.valueOf(num_of_unit_txt.getText());
        double one_unit_price=Double.valueOf(one_price_txt.getText());

        try{
            boolean isSaved = StockDetailsModel.save(item_id,supplier_id,num_of_unit,one_unit_price, LocalDate.now());
            if(isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "customer saved!!!").show();
            }
        } catch (SQLException e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened").show();
        }
    }
    private void loadproductIds() {
        try {
            List<String> ids = ProductModel.getIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            item_cmb.setItems(obList);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
    private void loadsupplyIds() {
        try {
            List<String> ids = SupplierModel.getIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            supply_cmb.setItems(obList);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadproductIds();
        loadsupplyIds();
    }
}
