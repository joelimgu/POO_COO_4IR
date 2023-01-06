package org.example.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.conversation.ConnectedUser;
import org.jetbrains.annotations.Async;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static java.lang.Thread.sleep;

public class HelloController {
    public VBox listPeopleConnected;
    public TextArea messageSendField;
    @FXML
    private TextField welcomeText;
    @FXML
    private VBox chatList;
    @FXML
    ScrollPane scrollChatPane;

    @FXML
    BorderPane borderPaneGlobal;


    private boolean isListened = false;
    private int nbMessages = 0;

    Stage myStage;


    public void setStage(Stage s) {
        myStage = s;
    }
    @FXML
    public void addNewMessages(ScrollEvent scrollEvent) {
        System.out.println(scrollEvent.getDeltaY());
    }
    @FXML
    public void addMessage(MouseEvent mouseEvent) {
    }

    public void addNewPerson(MouseEvent mouseEvent) {

        PersonObject po = new PersonObject(new ConnectedUser("re", null));
        //PersonObject po2 = new PersonObject("KIKI", true, null);

        // *****  LISTENER CONFIGURATION *****

        // Here : get(0) in order to get the textField area of the person you want to append
        //TextField p1 = (TextField) po.getChildren().get(0);
        TextField p1 = (TextField) po.getChildren().get(0);

        // Put a listener to the TextField in order to print the conversation linked to this user
        p1.setOnMouseClicked(
                event -> {
                    // *** ACCESS TO UUID ** ///
                    UUID uuid = po.getUUID();
                    System.out.println(uuid.toString());
                    chatList.getChildren().clear();
                    chatList.getChildren().add(new MessageObject("This is a conversation with " + po.getUsername(), true));
                    chatList.getChildren().add(new MessageObject("Yes we currently talk with " + po.getUsername() + " !", false));
                    chatList.getChildren().add(new MessageObject("And here a second message with " + po.getUsername() + " !", false));

                    // ********************* //
                }
        );

        // ***** END OF LISTENER CONFIGURATION *****


        // Add to the main frame
        listPeopleConnected.getChildren().add(po);

    }


    public void sendMessageClick(MouseEvent mouseEvent) {

        MessageObject mo;

        if (nbMessages%2 == 0) {
            mo = new MessageObject(messageSendField.getText(), true);
            chatList.getChildren().add(mo);
        } else {
            mo = new MessageObject(messageSendField.getText(), false);
            chatList.getChildren().add(mo);
        }
        nbMessages++;
        messageSendField.setText("");
        messageSendField.setPromptText("Write your message here");

        // Put the scroll pane at the end of the list
        if (!isListened) {
            chatList.heightProperty().addListener(observable -> scrollChatPane.setVvalue(1D));
            isListened = true;
        }
    }

    public void sendMessageEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            sendMessageClick(null);
        }
    }

    public void goToChangeUsernameFrame(ActionEvent actionEvent) throws Exception {
        ChangeUsenameApplication cua = new ChangeUsenameApplication(myStage);
        cua.start(new Stage());
    }

    public void validateOK(MouseEvent mouseEvent) {

    }

    public void changeName(String name) {
        myStage.setTitle("You are connected as " + name);
    }

    public void addConnectedUser(List<ConnectedUser> users) {
        for (ConnectedUser user : users) {
            PersonObject po = new PersonObject(user);
            TextField p1 = (TextField) po.getChildren().get(0);
            p1.setOnMouseClicked(
                    event -> {
                        // *** ACCESS TO UUID ** ///
                        UUID uuid = po.getUUID();
                        System.out.println(uuid.toString());
                        chatList.getChildren().clear();
                        // ********************* //
                        // TODO : Find a way to get the messages of a connected user in the front part with his UUID
                    }
            );
        }
    }

}