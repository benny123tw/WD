package syncingWork;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import login.FireBaseAuth;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

public class Controller {
    public static String token = "";
    private static String userMail;


    @FXML
    private MenuItem menu_open;

    @FXML
    private MenuItem menu_about;

    @FXML
    private MenuItem menuprofile_user;

    @FXML
    private MenuItem menuprofile_manage;

    @FXML
    private TextField worker_L;

    @FXML
    private TextField machine_M;

    @FXML
    private TextField worker_W;

    @FXML
    private TextField worker_K1;

    @FXML
    private TextField machine_K2;

    @FXML
    private Button btn_Clear;

    @FXML
    private Button btn_Calc;

    @FXML
    private TitledPane result1_Pane;

    @FXML
    private TextField result1_WorkerCount;

    @FXML
    private TextField result1_MachineCount;

    @FXML
    private TextField result1_CycleTime;

    @FXML
    private TextField result1_R;

    @FXML
    private TextField result1_Tec;

    @FXML
    private TextField result1_ldle;

    @FXML
    private TitledPane result2_Pane;

    @FXML
    private TextField result2_WorkerCount;

    @FXML
    private TextField result2_MachineCount;

    @FXML
    private TextField result2_CycleTime;

    @FXML
    private TextField result2_R;

    @FXML
    private TextField result2_Tec;

    @FXML
    private TextField result2_ldle;

    @FXML
    private ImageView imageView;

    @FXML
    private MenuBar menubar;



    @FXML
    public void initialize() throws Exception {
        userMail = FireBaseAuth.getInstance().getAccountInfo(token);

        try {
            if(userMail!=null){
                if(userMail.equals("admin@gmail.com")){
                    menuprofile_manage.setVisible(true);
                }
                System.out.println("text");
                menuprofile_user.setText("登出： "+userMail);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        worker_L.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    worker_L.setText(oldValue);
                }
            }
        });
        worker_W.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    worker_W.setText(oldValue);
                }
            }
        });
        worker_K1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                worker_K1.setText(oldValue);
            }
        });
        machine_M.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    machine_M.setText(oldValue);
                }
            }
        });
        machine_K2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    machine_K2.setText(oldValue);
                }
            }
        });

    }

    @FXML
    void toManagement(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/management.fxml"));
            Scene mainScene = new Scene(root);
            Stage window = (Stage) menubar.getScene().getWindow();
            window.setScene(mainScene);
            window.show();
        }catch (Exception ex){
            expContext(ex, "Error: 404");
        }
    }

    @FXML
    void aboutMe(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Me");
        alert.setHeaderText("Auth. Benny Yen");
        alert.setContentText("Copyright © 2018 by Benny Yen \n Mail: benny123tw@gmail.com");
        Image image = new Image("/drawable/dabingAuth.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);
        alert.setGraphic(imageView);
        alert.setResizable(false);
        alert.showAndWait();
    }

    @FXML
    void openFile(ActionEvent event) {
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File selectedFile = fileChooser.showOpenDialog(null);
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
            imageView.setFitHeight(300);
            imageView.setFitWidth(300);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);

        }catch (Exception ex){
            expContext(ex, "Can't find the file!");
        }

    }

    @FXML
    void onLogout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("登出");
        alert.setHeaderText(null);
        alert.setContentText("確認登出？");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            try{
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/loginPage.fxml"));
                Scene mainScene = new Scene(root);
                Stage window = (Stage) menubar.getScene().getWindow();
                window.setScene(mainScene);
                window.show();
            }catch (Exception ex){
                expContext(ex, "Error: 404");
            }
        } else {
            //do nothing
        }
    }

    private void expContext(Exception ex, String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("Error! Exception");
        alert.setContentText(errorMessage);

// Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

// Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

    @FXML
    void calcData(MouseEvent event) {

        Worker worker = new Worker(Double.parseDouble(worker_L.getText()), Double.parseDouble(worker_W.getText()), Double.parseDouble(worker_K1.getText()));
        Machine machine = new Machine(Double.parseDouble(machine_M.getText()),Double.parseDouble(machine_K2.getText()));
        Calc calc = new Calc();

        if(Calc.remender !=0){
            printDataR1();
            printDataR2();
            result1_Pane.setVisible(true);
            result2_Pane.setVisible(true);

        } else {
            printDataR1();
            result1_Pane.setVisible(true);
            result2_Pane.setVisible(false);
        }


    }

    private void printDataR1(){
        result1_WorkerCount.setText("1"); //還沒製作
        result1_MachineCount.setText(""+(int)Calc.n1);
        checkInt(result1_CycleTime, rounddecimal(Calc.Ct1));
        checkInt(result1_R, rounddecimal(Calc.R1));
        checkInt(result1_Tec, rounddecimal(Calc.TEC1));
        checkInt(result1_ldle, rounddecimal(Calc.ldle1));
    }

    private void printDataR2(){
        result2_WorkerCount.setText("1"); //還沒製作
        result2_MachineCount.setText(""+(int)Calc.n2);
        checkInt(result2_CycleTime, rounddecimal(Calc.Ct2));
        checkInt(result2_R, rounddecimal(Calc.R2));
        checkInt(result2_Tec, rounddecimal(Calc.TEC2));
        checkInt(result2_ldle, rounddecimal(Calc.ldle2));
    }

    private double rounddecimal(double val){
        val = Math.round(val * 100.0)/100.0;
        return val;
    }

    private void checkInt(TextField textField, double val){
        if(val%1==0){
            textField.setText(""+(int)val);
        } else {
            textField.setText(""+val);
        }
    }

    @FXML
    void clearData(MouseEvent event) {
        worker_L.setText("");
        worker_W.setText("");
        worker_K1.setText("");
        machine_M.setText("");
        machine_K2.setText("");
    }


}
