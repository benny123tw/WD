import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent fxml = FXMLLoader.load((getClass().getResource("fxml/loginPage.fxml")));
        primaryStage.setTitle("工作研究公式");
        primaryStage.setScene(new Scene(fxml));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
