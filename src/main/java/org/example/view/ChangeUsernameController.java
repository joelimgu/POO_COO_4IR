package org.example.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.model.communication.server.HTTPServer;
import org.example.model.conversation.ConnectedUser;
import org.example.model.conversation.User;
import org.example.services.HTTPService;
import org.example.services.SessionService;
import org.example.services.StorageService;

import java.sql.SQLException;
import java.util.List;

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

        String username = usernameChange.getText();
        boolean isUsedUsername = false;
        List<ConnectedUser> lu = SessionService.getInstance().getConnectedUsers();
        for (User u : lu) {
            if (u.getPseudo().equals(username)) {
                isUsedUsername = true;
            }
        }

        if (!isUsedUsername) {
            try {
                Gson g = new GsonBuilder().setPrettyPrinting().create();
                SessionService.getInstance().getRemoteConnectedUsers().forEach((u) -> {
                    HTTPService.getInstance().sendRequest(u.getIP(),"/update_pseudo", HTTPService.HTTPMethods.PUT, g.toJson(u));
                });
                StorageService.getInstance().updatePseudo(SessionService.getInstance().getM_localUser(), username);
                parentStage.setTitle("You are connected as " + usernameChange.getText());
                parentStage.show();
                myStage.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {

            ErrorDialog ed = new ErrorDialog("Error while changing username, abort.", this.myStage);
            try {
                ed.start(new Stage());
                myStage.close();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // ----------------



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
