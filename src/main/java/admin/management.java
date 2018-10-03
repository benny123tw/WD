package admin;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.*;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Optional;

public class management {
    private static int i = 1;
    private static boolean check = true; //set false to TEST
    private ArrayList<String> uidList = new ArrayList<>();

    @FXML
    private StackPane rootPane;

    @FXML
    private AnchorPane rootAnchor;

    @FXML
    private JFXListView<Label> listView;

    @FXML
    private JFXPopup popup;

    @FXML
    private JFXButton btn_back;

    @FXML
    void showDialog(ActionEvent event) {
        BoxBlur blur = new BoxBlur(3, 3, 3);
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXButton btn = new JFXButton("Okay");
        JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mousEvent) -> {
            //rootAnchor.setEffect(null);
            dialog.close();
        });

        dialogLayout.setHeading(new Label("DAMN"));
        dialogLayout.setActions(btn);
        dialog.show();

        dialog.setOnDialogClosed(closeEvent -> {
            rootAnchor.setEffect(null);
            System.out.println("I am closed!");
        });

        rootAnchor.setEffect(blur);


    }

    @FXML
    void backtoMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load((getClass().getResource("fxml/syncingWork.fxml")));
        Scene mainScene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();
    }

    @FXML
    public void initialize() throws Exception{
        if(!check){
            try {
                FileInputStream serviceAccount = new FileInputStream("ServerKey.json");

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("https://workdesign-ff08e.firebaseio.com")
                        .build();

                FirebaseApp.initializeApp(options);
                check = true;

                /*UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail("benny123tw@gmail.com");
                // See the UserRecord reference doc for the contents of userRecord.
                System.out.println("Successfully fetched user data: " + userRecord.getUid());*/

            } catch (Exception ex){
                expContext(ex, "Connect Failed. Check your network connection.");
            }
        }
        initList();
        initPopup();

    }

    private void initList() throws FirebaseAuthException {
        uidList.clear();
        listView.getItems().clear();
        i = 1;
        ListUsersPage page = FirebaseAuth.getInstance().listUsers(null);
        while (page != null) {
            for (ExportedUserRecord user : page.getValues()) {
                System.out.println("User: " + user.getUid());
                Label label = new Label(i+ ". " + user.getEmail());
                listView.getItems().add(label);
                uidList.add(user.getUid());
                i++;
            }
            page = page.getNextPage();
        }
    }

    private void initPopup() throws Exception{
        JFXButton btn1 = new JFXButton("重設密碼");
        JFXButton btn2 = new JFXButton("停用帳戶");
        JFXButton btn3 = new JFXButton("刪除帳戶");

        btn1.setPadding(new Insets(10));
        btn2.setPadding(new Insets(10));
        btn3.setPadding(new Insets(10));

        VBox vBox = new VBox(btn1, btn2, btn3);


        popup = new JFXPopup(vBox);
        popup.setAutoHide(true);

        btn1.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    try {
                        resetPassword();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                });
        btn2.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> disableAccount());
        btn3.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    try {
                        deleteAccount();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                });
    }

    @FXML
    void showPopup(MouseEvent event) {
        System.out.println(""+ listView.getSelectionModel().getSelectedItem());
        if(event.getButton() == MouseButton.SECONDARY)
        popup.show(listView, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX(), event.getY());
        popup.addEventHandler(MouseEvent.MOUSE_CLICKED, event1 -> {popup.hide();});
//        else popup.hide();

    }

    @FXML
    void keyPressedDel(KeyEvent event) throws Exception{
        System.out.println(""+event.getCode());
        if(event.getCode().equals(KeyCode.DELETE) && listView.getSelectionModel().getSelectedItems()!=null){
            deleteAccount();
        }
    }

    void resetPassword() throws Exception{
        System.out.println("RESET");
        resetWindow();
    }

    void disableAccount(){
        System.out.println("DISABLE");
    }

    void deleteAccount() throws Exception{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("確認刪除？");
        alert.setHeaderText(null);
        alert.setContentText("是否刪除此帳戶？");
        Optional<ButtonType> result = alert.showAndWait();
        if(!result.isPresent()){
            // alert is exited, no button has been pressed.

        }else if(result.get() == ButtonType.OK){
//            popup.hide();
            System.out.println("DELETE");
            FirebaseAuth.getInstance().deleteUser(uidList.get(listView.getSelectionModel().getSelectedIndex()));
            System.out.println("Successfully deleted user.");
            initList();
        }
        else if(result.get() == ButtonType.CANCEL){
            // cancel button is pressed

        }

    }

    private void resetWindow() throws Exception{
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Reset Password");
        dialog.setHeaderText(null);

// Set the icon (must be included in the project).
//        dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

// Set the button types.
        ButtonType loginButtonType = new ButtonType("確認", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        PasswordField repassword = new PasswordField();
        repassword.setPromptText("Repeat Password");

        grid.add(new Label("新密碼:"), 0, 0);
        grid.add(password, 1, 0);
        grid.add(new Label("重複密碼:"), 0, 1);
        grid.add(repassword, 1, 1);

// Enable/Disable login button depending on whether a username was entered.
        Node doneButton = dialog.getDialogPane().lookupButton(loginButtonType);
        doneButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            doneButton.setDisable(newValue.trim().isEmpty());

            if(!password.getText().equals(repassword.getText()) || password.getLength()<6){
                doneButton.setDisable(true);
            } else{
                doneButton.setDisable(false);
            }
        });

        repassword.textProperty().addListener((observable, oldValue, newValue) -> {
            doneButton.setDisable(newValue.trim().isEmpty());

            if(!password.getText().equals(repassword.getText()) || repassword.getLength()<6){
                doneButton.setDisable(true);
            } else{
                doneButton.setDisable(false);
            }
        });

        dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
        Platform.runLater(password::requestFocus);

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(password.getText(), repassword.getText());
            }
            return null;
        });

        String newp = "";
        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(newPassword -> {
            System.out.println("new Password= " + newPassword.getKey());
            UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uidList.get(listView.getSelectionModel().getSelectedIndex()))
                    .setPassword("" + newPassword.getKey());

            UserRecord userRecord = null;
            try {
                userRecord = FirebaseAuth.getInstance().updateUser(request);
            } catch (FirebaseAuthException e) {
                e.printStackTrace();
            }
            System.out.println("Successfully updated user: " + userRecord.getUid());
        });
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
