package lk.ijse.gaming_arena.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gaming_arena.db.DBConnection;
import lk.ijse.gaming_arena.dto.CartDTO;
import lk.ijse.gaming_arena.dto.Customer;
import lk.ijse.gaming_arena.dto.Product;
import lk.ijse.gaming_arena.dto.tm.PlaceOrderTM;
import lk.ijse.gaming_arena.model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class BillingMainFormController implements Initializable {


    @FXML
    private Label orderId_lbl;

    @FXML
    private JFXComboBox<String> cust_id_cmb;

    @FXML
    private JFXButton new_cust_btn;

    @FXML
    private JFXComboBox<String> prod_id_cmb;

    @FXML
    private Label descrip_lbl;

    @FXML
    private JFXButton add_to_cart_btn;

    @FXML
    private TableView<PlaceOrderTM> itemPrice_table;

    @FXML
    private TableColumn<?, ?> id_col;

    @FXML
    private TableColumn<?, ?> des_col;

    @FXML
    private TableColumn<?, ?> one_price_col;

    @FXML
    private TableColumn<?, ?> num_qty;

    @FXML
    private TableColumn<?, ?> price_col;

    @FXML
    private TableColumn<?, ?> remove_col;

    @FXML
    private Label one_price_lbl;

    @FXML
    private JFXTextField num_of_qty_txt;

    @FXML
    private Label tot_price_lbl;

    @FXML
    private Label net_price_lbl;
    @FXML
    private Label name_lbl;

    @FXML
    private JFXButton printbill_btn;

    private ObservableList<PlaceOrderTM> obList = FXCollections.observableArrayList();
    static String price1;
    static int num_of_qty;

    public void cust_id_cmbOnAction(ActionEvent event){
        String cus_id=cust_id_cmb.getSelectionModel().getSelectedItem();
        try {
            Customer customer = CustomerModel.searchById(cus_id);
            name_lbl.setText(customer.getCustomer_name());
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void prod_id_cmbOnAction(ActionEvent event) {
        String prod_id=prod_id_cmb.getSelectionModel().getSelectedItem();
        try {
            Product product = ProductModel.searchById(prod_id);
            descrip_lbl.setText(product.getItem_description());
            System.out.println(product.getItem_on_hand_qty());
            price1=String.valueOf(product.getItem_one_qty_price());
            one_price_lbl.setText(price1);
            System.out.println(product.getItem_one_qty_price());


        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void num_txtOnAction(ActionEvent event) {

    }

    public void new_cust_btn(ActionEvent event) {
    }

    public void addtocart_onAction(ActionEvent event) {
        String code = prod_id_cmb.getValue();
        String description = descrip_lbl.getText();
        int qty = Integer.parseInt(num_of_qty_txt.getText());
        double onePrice = Double.parseDouble(one_price_lbl.getText());
        double total = qty * onePrice;
        tot_price_lbl.setText(String.valueOf(total));
        Button btnRemove = new Button("Remove ");
        btnRemove.setCursor(Cursor.HAND);

        setRemoveBtnOnAction(btnRemove); /* set action to the btnRemove */

        if (!obList.isEmpty()) {
            for (int i = 0; i < itemPrice_table.getItems().size(); i++) {
                if (id_col.getCellData(i).equals(code)) {
                    qty += (int) num_qty.getCellData(i);
                    total = qty * onePrice;

                    obList.get(i).setQty(qty);
                    obList.get(i).setTotal(total);

                    itemPrice_table.refresh();
                    calculateNetTotal();
                    return;
                }
            }
        }

        PlaceOrderTM tm = new PlaceOrderTM(code, description, qty, onePrice, total, btnRemove);

        obList.add(tm);
        itemPrice_table.setItems(obList);
        calculateNetTotal();

        num_of_qty_txt.setText("");
    }
    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i < itemPrice_table.getItems().size(); i++) {
            double total  = (double) price_col.getCellData(i);
            netTotal += total;
        }
        net_price_lbl.setText(String.valueOf(netTotal));
    }
    private void setRemoveBtnOnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (result.orElse(no) == yes) {

                int index = itemPrice_table.getSelectionModel().getSelectedIndex();
                obList.remove(index);

                itemPrice_table.refresh();
                calculateNetTotal();
            }

        });
    }

    void setCellValueFactory() {
        id_col.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        des_col.setCellValueFactory(new PropertyValueFactory<>("description"));
        num_qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        one_price_col.setCellValueFactory(new PropertyValueFactory<>("onePrice"));
        price_col.setCellValueFactory(new PropertyValueFactory<>("total"));
        remove_col.setCellValueFactory(new PropertyValueFactory<>("btn"));
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
    private void loadproductIds() {
        try {
            List<String> ids = ProductModel.getIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            prod_id_cmb.setItems(obList);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
    private void generateNextOrderId() {
        try {
            String nextId = OrderModel.generateNextOrderId();
            orderId_lbl.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadproductIds();
        loadCustomerIds();
        generateNextOrderId();
        setCellValueFactory();
    }

    public void printBill_btnOnAction(ActionEvent event) {
        String oId = orderId_lbl.getText();
        String cusId = cust_id_cmb.getValue();

        List<CartDTO> cartDTOList = new ArrayList<>();

        for (int i = 0; i < itemPrice_table.getItems().size(); i++) {
            PlaceOrderTM tm = obList.get(i);

            CartDTO cartDTO = new CartDTO(tm.getItem_id(), tm.getQty(), tm.getOnePrice());
            cartDTOList.add(cartDTO);
        }
        try {
            System.out.println(cusId);
            boolean isPlaced = PlaceOrderModel.placeOrder(oId, cusId, cartDTOList);
            System.out.println(isPlaced);
            if(isPlaced) {

                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
                generateNextOrderId();
                cust_id_cmb.setPromptText("Choose");
                prod_id_cmb.setPromptText("Choose");
                one_price_lbl.setText("00.00");
                descrip_lbl.setText("not yet");
                net_price_lbl.setText("000.00");
                tot_price_lbl.setText("0000.00");
                for ( int i = 0; i<itemPrice_table.getItems().size(); i++) {
                    itemPrice_table.getItems().clear();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Order Not Placed!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void report_btnOnAction(ActionEvent event) {
        try{
            Connection con = DBConnection.getInstance().getConnection();

            InputStream input = new FileInputStream(new File("C://Users//User//OneDrive//Desktop//New Project//src//main//java//lk//ijse//gaming_arena//Reports//order_details_report.jrxml"));
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            JasperPrint fillReport = JasperFillManager.fillReport(jasperReport, null, con);
            JasperViewer.viewReport(fillReport, false);
            //JasperExportManager .exportReportToPdfFile(jasperReport,fillReport,dataS);

        }catch(JRException | FileNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }
}
