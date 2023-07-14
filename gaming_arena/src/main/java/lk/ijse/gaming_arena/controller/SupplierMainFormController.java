package lk.ijse.gaming_arena.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.gaming_arena.db.DBConnection;
import lk.ijse.gaming_arena.dto.Customer;
import lk.ijse.gaming_arena.dto.Supplier;
import lk.ijse.gaming_arena.dto.tm.CustomerTM;
import lk.ijse.gaming_arena.dto.tm.SupplierTM;
import lk.ijse.gaming_arena.model.CustomerModel;
import lk.ijse.gaming_arena.model.SupplierModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class SupplierMainFormController implements Initializable {
    @FXML
    private TableView<SupplierTM> sup_tbl;

    @FXML
    private TableColumn<?, ?> id_col;

    @FXML
    private TableColumn<?, ?> name_col;

    @FXML
    private TableColumn<?, ?> comp_col;

    @FXML
    private TableColumn<?, ?> cont_col;

    @FXML
    private TableColumn<?, ?> email_col;

    @FXML
    private JFXButton add_btn;

    @FXML
    private JFXButton more_btn;

    @FXML
    private JFXButton supply_item_btn;

    @FXML
    private JFXButton refresh_btn;
    public void add_btnOnAction(ActionEvent event) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/add_supplier_form.fxml"));
        Scene scene=new Scene(parent);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Supplier");
        stage.show();
    }

    public void more_btnOnAction(ActionEvent event) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/update_supplier_form.fxml"));
        Scene scene=new Scene(parent);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("Update Supplier");
        stage.show();
    }

    public void supply_item_btnOnAction(ActionEvent event) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/supply_items_form.fxml"));
        Scene scene=new Scene(parent);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("Supply items");
        stage.show();
    }
    void setCellValueFactory() {
        id_col.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("supplier_name"));
        comp_col.setCellValueFactory(new PropertyValueFactory<>("supplier_company"));
        cont_col.setCellValueFactory(new PropertyValueFactory<>("supplier_contact"));
        email_col.setCellValueFactory(new PropertyValueFactory<>("supplier_email"));

    }
    private void getAll() {
        try {
            ObservableList<SupplierTM> obList = FXCollections.observableArrayList();
            List<Supplier> supList = SupplierModel.getAll();

            for(Supplier supplier : supList) {
                obList.add(new SupplierTM(
                        supplier.getSupplier_id(),
                        supplier.getSupplier_name(),
                        supplier.getSupplier_company(),
                        supplier.getSupplier_contact(),
                        supplier.getSupplier_email()

                ));
            }
            sup_tbl.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    public void refresh_btnOnAction(ActionEvent event) {
        sup_tbl.getItems().clear();
        getAll();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getAll();
        setCellValueFactory();
    }

    public void report_btnOnAction(ActionEvent event) {
        try{
            Connection con = DBConnection.getInstance().getConnection();

            InputStream input = new FileInputStream(new File("C://Users//User//OneDrive//Desktop//New Project//src//main//java//lk//ijse//gaming_arena//Reports//supplier_report.jrxml"));
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
