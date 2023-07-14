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
import lk.ijse.gaming_arena.model.ProductModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddProductWindowFormController implements Initializable {
    @FXML
    private JFXTextField product_id_txt;

    @FXML
    private JFXTextField description_txt;

    @FXML
    private JFXComboBox<String> type_cmb;

    @FXML
    private JFXTextField OnHand_txt;

    @FXML
    private JFXTextField unit_price_txt;

    @FXML
    private JFXButton add_btn;

    @FXML
    private JFXButton cancle_btn;

    @FXML
    private Label product_id_lbl;

    static String id,descrip,type,onhand,unitPrice;
    static double price;
    static int onhandqt;

    public void product_idOnAction(ActionEvent event) {
        id=product_id_txt.getText();
        Pattern pattern=Pattern.compile("^(IT)[0-9]{4,5}$");
        boolean matches=pattern.matcher(id).matches();
        if(matches){
            product_id_txt.setStyle("-fx-text-fill:yellow");
            id=product_id_txt.getText();

        }else{

            product_id_txt.setStyle("-fx-text-fill:red");
            new Alert(Alert.AlertType.ERROR,"Worng ID pattern !!!").show();
        }
    }

    public void description_txtOnAction(ActionEvent event) {
        descrip=description_txt.getText();
    }

    public void type_cmbOnAction(ActionEvent event) {
        type=type_cmb.getValue();

    }

    public void OnHand_txtOnAction(ActionEvent event) {
        onhand=OnHand_txt.getText();
        Pattern numPattern=Pattern.compile("^[0-9]{2,3}$");
        boolean matches=numPattern.matcher(onhand).matches();
        if(matches){
            OnHand_txt.setStyle("-fx-text-fill:yellow");
            onhandqt=Integer.valueOf(onhand);
        }else{

            OnHand_txt.setStyle("-fx-text-fill:red");
            // new Alert(Alert.AlertType.ERROR,"waradi bijjo !!!").show();
        }
    }

    public void unit_price_txtOnAction(ActionEvent event) {
        unitPrice=unit_price_txt.getText();
        Pattern numPattern=Pattern.compile("^[0-9]{1,10}(.)[0-9]{2,2}$");
        boolean matches=numPattern.matcher(unitPrice).matches();
        if(matches){
            unit_price_txt.setStyle("-fx-text-fill:yellow");
            price=Double.valueOf(unitPrice);
        }else{

            unit_price_txt.setStyle("-fx-text-fill:red");
            new Alert(Alert.AlertType.ERROR,"waradi bijjo !!!").show();
        }
    }

    public void add_btnOnAction(ActionEvent event) {
        String id= product_id_lbl.getText();
        String descrip=description_txt.getText();
        int onhandqt=Integer.valueOf(OnHand_txt.getText());
        double price=Double.valueOf(unit_price_txt.getText());
        String type=type_cmb.getSelectionModel().getSelectedItem();

      try{
          System.out.println(id);
          boolean isSaved = ProductModel.add(id,descrip,onhandqt,price,type);
          System.out.println(id);
          System.out.println(isSaved);
          if(isSaved){
              new Alert(Alert.AlertType.CONFIRMATION, "Item saved!!!").show();
              generateNextProductId();
              description_txt.setText("");
              unit_price_txt.setText("");
              OnHand_txt.setText("");
              type_cmb.setValue("Choose");
          }
      } catch (SQLException e) {
          System.out.println(e);
          new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
      }
    }

    public void cancel_btnOnAction(ActionEvent event) {
        Stage stage = (Stage) cancle_btn.getScene().getWindow();
        stage.close();
    }
    private void generateNextProductId() {
        try {
            String nextId = ProductModel.generateNextProductId();
            product_id_lbl.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateNextProductId();
        loadTypes();
    }
}
