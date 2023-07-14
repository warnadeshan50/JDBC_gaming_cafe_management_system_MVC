package lk.ijse.gaming_arena.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.gaming_arena.db.DBConnection;
import lk.ijse.gaming_arena.dto.Customer;
import lk.ijse.gaming_arena.dto.Product;
import lk.ijse.gaming_arena.dto.tm.CustomerTM;
import lk.ijse.gaming_arena.dto.tm.ProductTM;
import lk.ijse.gaming_arena.model.CustomerModel;
import lk.ijse.gaming_arena.model.ProductModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductsMainFormController {
    @FXML
    private Label type_lbl;
    @FXML
    private JFXButton proc_btn;

    @FXML
    private JFXButton mB_btn;

    @FXML
    private JFXButton ram_btn;

    @FXML
    private JFXButton store_btn;

    @FXML
    private JFXButton vga_btn;

    @FXML
    private JFXButton casing_btn;

    @FXML
    private JFXButton cooler_btn;

    @FXML
    private JFXButton moni_btn;

    @FXML
    private JFXButton laptop_btn;

    @FXML
    private JFXButton add_product_btn;

    @FXML
    private JFXButton more_btn;

    @FXML
    private TableView<ProductTM> item_tbl;

    @FXML
    private TableColumn<?, ?> id_col;

    @FXML
    private TableColumn<?, ?> desc_col;

    @FXML
    private TableColumn<?, ?> price_col;

    @FXML
    private TableColumn<?, ?> status_col;
    
    public void add_product_btnOnAction(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/add_product_window_form.fxml"));
        Stage primaryStage=new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setScene(new Scene(parent));
        primaryStage.setTitle("add Product");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public void more_btn_OnAction(ActionEvent event) throws IOException {
        Parent parent=FXMLLoader.load(getClass().getResource("/view/update_product_form.fxml"));
        Stage primaryStage=new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setScene(new Scene(parent));
        primaryStage.setTitle("Update Product");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public void proc_btnOnAction(ActionEvent event) {
        setDetails("Processors");
    }

    public void mB_OnAction(ActionEvent event) {
        setDetails("Mother Boards");
    }

    public void ram_btnOnAction(ActionEvent event) {
        setDetails("Ram");
    }

    public void store_btnOnAction(ActionEvent event) {
        setDetails("Storage");
    }

    public void vga_btnOnAction(ActionEvent event) {
        setDetails("VGA");
    }

    public void casing_btnOnAction(ActionEvent event) {
        setDetails("Casing");
    }

    public void cooler_btnOnAction(ActionEvent event) {
        setDetails("Cooler");
    }

    public void moni_btnOnAction(ActionEvent event) {
        setDetails("Monitors");
    }

    public void laptop_btnOnAction(ActionEvent event) {
        setDetails("Laptops");
    }
    void setCellValueFactory() {
        id_col.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        desc_col.setCellValueFactory(new PropertyValueFactory<>("description"));
        price_col.setCellValueFactory(new PropertyValueFactory<>("onePrice"));
        status_col.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    private void getAll(String type) {
        try {
            ObservableList<ProductTM> obList = FXCollections.observableArrayList();
            List<Product> podList = ProductModel.getByType(type);

            for (Product product : podList) {
                obList.add(new ProductTM(
                        product.getItem_id(),
                        product.getItem_description(),
                        product.getItem_one_qty_price(),
                        product.setStatus(product.getItem_on_hand_qty())
                ));
            }
            item_tbl.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }
    private void setDetails(String type){
        type_lbl.setText(type);
        item_tbl.getItems().clear();
        getAll(type);
        setCellValueFactory();
    }

    public void report_btnOnAction(ActionEvent event) {
        try{
            Connection con = DBConnection.getInstance().getConnection();

            InputStream input = new FileInputStream(new File("C://Users//User//OneDrive//Desktop//New Project//src//main//java//lk//ijse//gaming_arena//Reports//product_report.jrxml"));
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            JasperPrint fillReport = JasperFillManager.fillReport(jasperReport, null, con);
            JasperViewer.viewReport(fillReport, false);
            //JasperExportManager .exportReportToPdfFile(jasperReport,fillReport,dataS);

        }catch(JRException | FileNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    public void show_all_btnAction(ActionEvent event) {
    }
}
