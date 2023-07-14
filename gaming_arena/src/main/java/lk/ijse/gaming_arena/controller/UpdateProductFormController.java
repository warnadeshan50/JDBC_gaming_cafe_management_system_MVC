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
import javafx.stage.Stage;
import lk.ijse.gaming_arena.dto.Membership;
import lk.ijse.gaming_arena.dto.Product;
import lk.ijse.gaming_arena.model.MembershipModel;
import lk.ijse.gaming_arena.model.ProductModel;
import lk.ijse.gaming_arena.util.CrudUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateProductFormController implements Initializable {
    @FXML
    private JFXTextField product_id_txt;

    @FXML
    private JFXTextField description_txt;

    @FXML
    private JFXTextField type_txt;

    @FXML
    private JFXTextField OnHand_txt;

    @FXML
    private JFXTextField unit_price_txt;

    @FXML
    private JFXButton update_btn;

    @FXML
    private JFXButton cancle_btn;

    @FXML
    private JFXButton search_btn;

    @FXML
    private JFXButton delete_btn1;

    @FXML
    private JFXComboBox<String> type_cmb;
    public void product_idOnAction(ActionEvent event) {
    }

    public void description_txtOnAction(ActionEvent event) {
    }

    public void type_cmbOnAction(ActionEvent event) {
    }

    public void OnHand_txtOnAction(ActionEvent event) {
    }

    public void unit_price_txtOnAction(ActionEvent event) {
    }

    public void cancel_btnOnAction(ActionEvent event) {
        Stage stage = (Stage) cancle_btn.getScene().getWindow();
        stage.close();
    }

    public void search_btnOnAction(ActionEvent event) {
        String id = product_id_txt.getText();
        try {
            Product item = ProductModel.search(id);
            if (item != null) {
                setvisible(true);
                product_id_txt.setText(item.getItem_id());
                description_txt.setText(item.getItem_description());
                type_cmb.setValue(item.getType());
                OnHand_txt.setText(String.valueOf(item.getItem_on_hand_qty()));
                unit_price_txt.setText(String.valueOf(item.getItem_one_qty_price()));

            } else {
                new Alert(Alert.AlertType.WARNING, "no item found :(").show();
            }
        } catch (SQLException e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
        }
    }

    public void delete_btnOnAction(ActionEvent event) {
        String id = product_id_txt.getText();

        try {
            boolean isDeleted = ProductModel.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item deleted !").show();
            }
        } catch (SQLException e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "something happened !").show();
        }
    }

    public void update_btnOnAction(ActionEvent event) {
        String item_id=product_id_txt.getText();
        String discription=description_txt.getText();
        int on_hand_qty= Integer.valueOf(OnHand_txt.getText());
        double one_price=Double.valueOf(unit_price_txt.getText());
        String type=type_cmb.getValue();
        var item = new Product(item_id,discription,on_hand_qty,one_price,type);

        try {
            boolean isUpdated = ProductModel.update(item);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Product Updated!").show();
            }
        } catch (SQLException e) {
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
        }
    }
    private void loadTypes(){

        ObservableList<String> types= FXCollections.observableArrayList();

        types.add("Processors");
        types.add("Ram");
        types.add("VGA");
        types.add("Monitors");
        types.add("Laptops");
        types.add("Mother Boards");
        types.add("Storage");
        types.add("Cooler");
        types.add("Casing");
        type_cmb.setItems(types);
    }
    private void setvisible(boolean visible){
        description_txt.setVisible(visible);
        type_cmb.setVisible(visible);
        OnHand_txt.setVisible(visible);
        unit_price_txt.setVisible(visible);
        update_btn.setVisible(visible);
        delete_btn1.setVisible(visible);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTypes();
        setvisible(false);
    }
}
