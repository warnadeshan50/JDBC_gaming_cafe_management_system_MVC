import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent= FXMLLoader.load(getClass().getResource("/view/login_page_form.fxml"));
        Scene scene=new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login_page_form");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
