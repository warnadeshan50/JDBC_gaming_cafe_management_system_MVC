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
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.gaming_arena.db.DBConnection;
import lk.ijse.gaming_arena.dto.Customer;
import lk.ijse.gaming_arena.dto.tm.CustomerTM;
import lk.ijse.gaming_arena.model.CustomerModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerMainFormController implements Initializable {
    @FXML
    private TableView<CustomerTM> tblCustomer;

    @FXML
    private TableColumn<?, ?> custIDCol;

    @FXML
    private TableColumn<?, ?> empIDCol;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> addressCol;

    @FXML
    private TableColumn<?, ?> contactCol;

    @FXML
    private TableColumn<?, ?> emailCol;

    @FXML
    private JFXButton add_customer_btn;

    @FXML
    private JFXButton more_btn;
    @FXML
    private JFXButton refresh_btn;

    public void add_customer_btnOnAction(ActionEvent event) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/add_customer_window_form.fxml"));
        Scene scene=new Scene(parent);
        Stage primaryStage=new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add Customer Window");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public void more_btnOnAction(ActionEvent event) throws IOException {
        Parent parent=FXMLLoader.load(getClass().getResource("/view/update_customer_form.fxml"));
        Scene scene=new Scene(parent);
        Stage stage=new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Update Customer Window");
        stage.show();
    }

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle){
        setCellValueFactory();
        getAll();
    }
    void setCellValueFactory() {
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        empIDCol.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("customer_address"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("customer_contact"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("customer_email"));
    }
    private void getAll() {
        try {
            ObservableList<CustomerTM> obList = FXCollections.observableArrayList();
            List<Customer> cusList = CustomerModel.getAll();

            for(Customer customer : cusList) {
                obList.add(new CustomerTM(
                        customer.getCustomer_id(),
                        customer.getEmployee_id(),
                        customer.getCustomer_name(),
                        customer.getCustomer_address(),
                        customer.getCustomer_contact(),
                        customer.getCustomer_email()
                ));
            }
            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    public void refresh_btnOnAction(ActionEvent event) {
        tblCustomer.getItems().clear();
        getAll();
    }

    public void report_btnOnAction(ActionEvent event) {
        try{
            Connection con = DBConnection.getInstance().getConnection();

            InputStream input = new FileInputStream(new File("C://Users//User//OneDrive//Desktop//New Project//src//main//java//lk//ijse//gaming_arena//Reports//customers_report.jrxml"));
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
