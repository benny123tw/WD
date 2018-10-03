package login;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class registerPage {
    public static boolean termsCheck = false;

    @FXML
    private JFXTextField register_account;

    @FXML
    private JFXTextField register_password;

    @FXML
    private JFXTextField register_repassword;

    @FXML
    private Text text_Error;

    @FXML
    private JFXButton btn_Register;

    @FXML
    private JFXCheckBox checkBox_News;

    @FXML
    private JFXCheckBox checkBox_Use;

    @FXML
    private Text hyperLink_Use;

    @FXML
    private JFXProgressBar progressBar_Login;

    @FXML
    private JFXButton btn_BacktoLogin;

    @FXML
    void BacktoLogin(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/loginPage.fxml"));
            Scene mainScene = new Scene(root);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(mainScene);
            window.show();
        }catch (Exception ex){
            expContext(ex, "Error: 404");
        }
    }

    @FXML
    void registerClicked(ActionEvent event) {

        if(!termsCheck){
            text_Error.setText("請確認使用條款");
            text_Error.setVisible(true);
        } else{
            if(!register_account.getText().contains("@") && !register_account.getText().contains(".com")){
                text_Error.setText("請確認郵箱是否輸入正確");
                text_Error.setVisible(true);
                register_account.requestFocus();
            }
            else if(register_password.getLength()<6){
                text_Error.setText("密碼需要六個字元");
                text_Error.setVisible(true);
                register_password.requestFocus();
            }
            else if(!register_password.getText().equals(register_repassword.getText())){
                text_Error.setText("密碼不一致");
                text_Error.setVisible(true);
                register_password.requestFocus();
            }
            else{

                try{
                    UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                            .setEmail(""+register_account.getText())
                            .setEmailVerified(false)
                            .setPassword(""+register_password.getText())
                            .setDisabled(false);
                    try{
                        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
                        System.out.println("Successfully created new user: " + userRecord.getUid());
                        Parent root = FXMLLoader.load(getClass().getResource("/fxml/loginPage.fxml"));
                        Scene mainScene = new Scene(root);
                        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                        window.setScene(mainScene);
                        window.show();
                    }catch (Exception ex){
                        text_Error.setText("郵箱已被註冊");
                        text_Error.setVisible(true);
                        register_account.requestFocus();
                    }

                }catch (Exception ex){
                    expContext(ex, "Failed to Connect network.");
                }
            }
        }



    }

    @FXML
    void checkTerms(ActionEvent event) {
        if(termsCheck) checkBox_Use.setSelected(true);
        else checkBox_Use.setSelected(false);
    }

    @FXML
    void showTerms(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("fxml/terms.fxml")));
        Stage stage = new Stage();
        stage.setTitle("使用條款");
        stage.setScene(new Scene(root));
        stage.show();
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
