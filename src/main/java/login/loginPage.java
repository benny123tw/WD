package login;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import syncingWork.Controller;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

public class loginPage {

    private static final String Account = "benny123yw";
    private static final String Password = "benny123tw2";
    private static boolean check = false;




    @FXML
    public void initialize(){
        if(!check){
            try {
                FileInputStream serviceAccount = new FileInputStream("ServerKey.json");

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("https://workdesign-ff08e.firebaseio.com")
                        .build();

                FirebaseApp.initializeApp(options);
                check = true;


//                UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail("benny123tw@gmail.com");
//                // See the UserRecord reference doc for the contents of userRecord.
//                System.out.println("Successfully fetched user data: " + userRecord.getUid());

            } catch (Exception ex){
                expContext(ex, "Connect Failed. Check your network connection.");
            }
        }

    }

    @FXML
    private JFXTextField textField_Account;

    @FXML
    private JFXTextField textField_Password;

    @FXML
    private JFXButton btn_Login;

    @FXML
    private JFXButton btn_Register;

    @FXML
    private JFXButton btn_Forgot;

    @FXML
    private JFXCheckBox checkBox_Rember;

    @FXML
    private JFXProgressBar progressBar_Login;

    @FXML
    void forgotClicked(ActionEvent event) {
        System.out.println("for");
    }

    @FXML
    void loginClicked(ActionEvent event) {
        System.out.println("login");
        progressBar_Login.setVisible(true);

        try {
            String s = FireBaseAuth.getInstance().auth(textField_Account.getText(), textField_Password.getText());
            System.out.println(FireBaseAuth.getInstance().getAccountInfo(s));
            if (FireBaseAuth.getInstance().getAccountInfo(s)!=null){
                Controller.token = s;
                Parent fxml = FXMLLoader.load((getClass().getResource("fxml/syncingWork.fxml")));
                Scene mainScene = new Scene(fxml);
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(mainScene);
                window.show();
                progressBar_Login.setVisible(false);
            } else{
                System.out.println("Wrong Account or Password." );
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Wrong Account or Password.");
                alert.showAndWait();
                progressBar_Login.setVisible(false);
            }
        } catch (Exception e) {
            expContext(e, "login failed");
        }


        /*try{
            if(textField_Account.getText().equals(Account) && textField_Password.getText().equals(Password)){
                System.out.println("Success Login");
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/syncingWork.fxml"));
                Scene mainScene = new Scene(root);
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(mainScene);
                window.show();
                progressBar_Login.setVisible(false);

            } else {
                System.out.println("Wrong Account or Password." );
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Wrong Account or Password.");
                alert.showAndWait();
                progressBar_Login.setVisible(false);
            }
        }catch (Exception ex){
            expContext(ex, "Error: Login Failed. Please check network connection.");
        }*/

    }

    @FXML
    void registerClicked(ActionEvent event) {
        System.out.println("register");
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/registerPage.fxml"));
            Scene mainScene = new Scene(root);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(mainScene);
            window.show();
        }catch (Exception ex){
            expContext(ex, "Error: 404.");
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
}
