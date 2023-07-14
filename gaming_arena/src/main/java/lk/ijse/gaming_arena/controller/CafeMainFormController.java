package lk.ijse.gaming_arena.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gaming_arena.db.DBConnection;
import lk.ijse.gaming_arena.dto.CafeDTO;
import lk.ijse.gaming_arena.dto.Computer;
import lk.ijse.gaming_arena.dto.Customer;
import lk.ijse.gaming_arena.dto.tm.CustomerTM;
import lk.ijse.gaming_arena.model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CafeMainFormController implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private JFXComboBox<String> cust_id_cb;
    @FXML
    private JFXComboBox<String> comp_id_cb;
    @FXML
    private Label name_lbl;
    @FXML
    private Label hour_price_lbl;


    @FXML
    private JFXButton cafe_btn;

    @FXML
    private JFXButton membership_btn;

    @FXML
    private JFXButton product_btn;

    @FXML
    private JFXButton billing_btn;

    @FXML
    private JFXButton supplier_btn;

    @FXML
    private JFXButton customer_btn;

    @FXML
    private JFXButton delivery_btn;

    @FXML
    private JFXButton return_btn;

    @FXML
    private JFXButton logout_btn;

    @FXML
    private Label tit_lbl;

    @FXML
    private JFXTextField start_time_txt;

    @FXML
    private JFXTextField end_time_txt;

    @FXML
    private Label total_price_lbl;

    @FXML
    private Label bill_id_lbl;

    @FXML
    private JFXButton printbill_btn;


    static String startTime;
    static String endTime;
    static double startT;
    static double endT;

    public void cafe_btnOnAction(ActionEvent event) throws IOException {
        Scene stage=new Scene(FXMLLoader.load(getClass().getResource("/view/cafe_main_form.fxml")));
        Stage window=(Stage) cafe_btn.getScene().getWindow();
        window.setScene(stage);
        window.setTitle("Gaming Arena Mangement System");
        window.centerOnScreen();
        tit_lbl.setText("Cafe Manage");
    }

    public void membership_btnOnAction(ActionEvent event) throws IOException {
        AnchorPane parent = FXMLLoader.load(getClass().getResource("/view/membership_main_form.fxml"));
        root.getChildren().setAll(parent);
        tit_lbl.setText("Membership Manage");
    }

    public void product_btnOnAction(ActionEvent event) throws IOException {
        AnchorPane parent = FXMLLoader.load(getClass().getResource("/view/products_main_form.fxml"));
        root.getChildren().setAll(parent);
        tit_lbl.setText("Product Manage");
    }

    public void billing_btnOnAction(ActionEvent event) throws IOException {
        AnchorPane parent = FXMLLoader.load(getClass().getResource("/view/billing_main_form.fxml"));
        root.getChildren().setAll(parent);
        tit_lbl.setText("Billing Manage");
    }

    public void suppler_btnOnAction(ActionEvent event) throws IOException {
        AnchorPane parent = FXMLLoader.load(getClass().getResource("/view/supplier_main_form.fxml"));
        root.getChildren().setAll(parent);
        tit_lbl.setText("Supplier Manage");
    }

    public void customer_btnOnAction(ActionEvent event) throws IOException {
        AnchorPane parent = FXMLLoader.load(getClass().getResource("/view/customer_main_form.fxml"));
        root.getChildren().setAll(parent);
        tit_lbl.setText("Customer Manage");
    }

    public void delivery_btnOnAction(ActionEvent event) throws IOException {
        AnchorPane parent = FXMLLoader.load(getClass().getResource("/view/delivery_main_form.fxml"));
        root.getChildren().setAll(parent);
        tit_lbl.setText("Delivery Manage");
    }

    public void return_btnOnAction(ActionEvent event) throws IOException {
        AnchorPane parent = FXMLLoader.load(getClass().getResource("/view/return_main_form.fxml"));
        root.getChildren().setAll(parent);
        tit_lbl.setText("Return Manage");
    }

    public void logout_btnOnAction(ActionEvent event) throws IOException {
        Scene stage=new Scene(FXMLLoader.load(getClass().getResource("/view/login_page_form.fxml")));
        Stage window=(Stage) logout_btn.getScene().getWindow();
        window.setScene(stage);
        window.centerOnScreen();
    }

    public void new_customer_btn(ActionEvent event) throws IOException {
        Parent parent=FXMLLoader.load(getClass().getResource("/view/add_customer_window_form.fxml"));
        Stage primaryStage=new Stage();
        Scene scene=new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.setTitle("New Customer Add");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public void end_time_txtOnAction(ActionEvent event) {
        startTime=start_time_txt.getText();
        endTime=end_time_txt.getText();
        startT=Double.valueOf(startTime);
        endT=Double.valueOf(endTime);
        double bill=valueOfBill(startT,endT);
        total_price_lbl.setText(String.valueOf(bill));


    }

    public void start_time_txt_OnAction(ActionEvent event) {
        startTime=start_time_txt.getText();
        startT=Double.valueOf(startTime);
        System.out.println(startTime);
    }
    public double valueOfBill(double startT,double endT){
        double hours,bill;
        hours=endT-startT;
        bill = hours * 200;
        if (bill>0){
            DecimalFormat df=new DecimalFormat("#.##");
            String dx=df.format(bill);
            return bill=Double.valueOf(dx);
        }
        return 0;
    }

    public void start_time_txtKey_Released(KeyEvent keyEvent) {
        startTime=start_time_txt.getText();
        Pattern timePattern=Pattern.compile("^[0-9]{1,2}(.)[0-9]{2,2}$");
        boolean matches=timePattern.matcher(startTime).matches();
        if(matches){
            start_time_txt.setStyle("-fx-border-color:green");
            start_time_txt.setStyle("-fx-text-fill:yellow");
        }else{
            start_time_txt.setStyle("-fx-border-color:red");
            start_time_txt.setStyle("-fx-text-fill:red");
        }
    }

    public void end_time_txtOnReleased(KeyEvent keyEvent) {
        endTime=end_time_txt.getText();
        Pattern timePattern=Pattern.compile("^[0-9]{1,2}(.)[0-9]{2,2}$");
        boolean matches=timePattern.matcher(endTime).matches();
        if(matches){
            end_time_txt.setStyle("-fx-border-color:green");
            end_time_txt.setStyle("-fx-text-fill:yellow");
        }else{
            end_time_txt.setStyle("-fx-border-color:red");
            end_time_txt.setStyle("-fx-text-fill:red");
        }
    }

    public void cust_id_cbOnAction(ActionEvent event) {
            String cus_id=cust_id_cb.getSelectionModel().getSelectedItem();
        try {
            Customer customer = CustomerModel.searchById(cus_id);
            name_lbl.setText(customer.getCustomer_name());
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void comp_id_cbOnAction(ActionEvent event) {
        String comp_id=comp_id_cb.getSelectionModel().getSelectedItem();
        try {
            Computer computer = ComputerModel.searchById(comp_id);
            String value=String.valueOf(computer.getComputer_amount_per_hour());
            hour_price_lbl.setText(value);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
    private void loadCustomerIds() {
       try {
            List<String> ids = CustomerModel.getIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            cust_id_cb.setItems(obList);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
    private void loadComputerIds() {
        try {
            List<String> ids = ComputerModel.getIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            comp_id_cb.setItems(obList);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadComputerIds();
        loadCustomerIds();
        generateNextBillId();
    }

    public void print_btnOnAction(ActionEvent event) {
        String oId = bill_id_lbl.getText();
        String comId = comp_id_cb.getValue();
        String cusId= cust_id_cb.getValue();
        double sT=Double.valueOf(start_time_txt.getText());
        double eT=Double.valueOf(end_time_txt.getText());
        double pay=Double.valueOf(total_price_lbl.getText());

            CafeDTO cafeDTO = new CafeDTO(comId,sT, eT,pay);

        System.out.println(cusId);
        try{
            System.out.println(cusId);
            boolean isPlaced = PlaceCafeBillModel.placeBill(oId, cusId, cafeDTO);
            System.out.println(isPlaced);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Bill Placed!").show();
                generateNextBillId();
                comp_id_cb.setPromptText("Choose");
                cust_id_cb.setPromptText("Choose");
                start_time_txt.setText("");
                end_time_txt.setText("");
            } else {
                new Alert(Alert.AlertType.ERROR, "Bill Not Placed!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    public void cancel_btnOnAction(ActionEvent event) {
    }

    public void showBills_btnOnAction(ActionEvent event) {
        try{
            Connection con = DBConnection.getInstance().getConnection();

            InputStream input = new FileInputStream(new File("C://Users//User//OneDrive//Desktop//New Project//src//main//java//lk//ijse//gaming_arena//Reports//cafe_details_report.jrxml"));
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            JasperPrint fillReport = JasperFillManager.fillReport(jasperReport, null, con);
            JasperViewer.viewReport(fillReport, false);
            //JasperExportManager .exportReportToPdfFile(jasperReport,fillReport,dataS);

        }catch(JRException | FileNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }
    private void generateNextBillId() {
        try {
            String nextId = CafeBillModel.generateNextBillId();
            bill_id_lbl.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
