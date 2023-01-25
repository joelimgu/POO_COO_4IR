package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ChangeUsernameController {

    public Stage myStage;
    public Stage parentStage;
    public TextField usernameChange;

    public void setStage(Stage stage, Stage pStage) {
        myStage = stage;
        parentStage = pStage;
    }

    public void tryStartSession(MouseEvent mouseEvent) {
        // TODO : Do a completable future to change the username : same behave as first connection maybe
        // Behave expected :
        //  Yes -> Change username and go back to main frame
        //  No -> Create an ErrorDialog frame and print it with message "Error while changing username" + raison erreur (si on peut la trouver)

        // ---- TO DELETE WHILE DOING THE REAL FUNCTION -----

        ErrorDialog ed = new ErrorDialog("Error while changing username, abort.", this.myStage);
        try {
            ed.start(new Stage());
            myStage.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // ----------------

        /*parentStage.setTitle("You are connected as " + usernameChange.getText());
        parentStage.show();
        myStage.close();*/
    }

    public void validateCancel(MouseEvent mouseEvent) {
        parentStage.show();
        myStage.close();
    }


    public void changePseudoEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            tryStartSession(null);
        }
    }

}
