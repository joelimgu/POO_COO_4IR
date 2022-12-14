package org.example.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
import org.jetbrains.annotations.Async;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import static java.lang.Thread.sleep;

public class HelloController {
    public VBox listPeopleConnected;
    public TextField messageSendField;
    @FXML
    private TextField welcomeText;
    @FXML
    private VBox chatList;
    @FXML
    ScrollPane scrollChatPane;

    @FXML
    BorderPane borderPaneGlobal;


    boolean isListened = false;
    int nbMessages = 0;


    @FXML
    public void addNewMessages(ScrollEvent scrollEvent) {
        System.out.println(scrollEvent.getDeltaY());
    }
    @FXML
    public void addMessage(MouseEvent mouseEvent) {
    }

    public void addNewPerson(MouseEvent mouseEvent) {
        PersonObject po = new PersonObject("YAYA", false);
        PersonObject po2 = new PersonObject("KIKI", true);

        // *****  LISTENER CONFIGURATION *****
        // Here : get(0) in order to get the textField area of the person you want to append
        TextField p1 = (TextField) po.getChildren().get(0);

        // Put a listener to the TextField in order to print the conversation linked to this user
        p1.setOnMouseClicked(
                event -> {
                    chatList.getChildren().clear();
                    chatList.getChildren().add(new MessageObject("This is a conversation with " + p1.getText(), true));
                    chatList.getChildren().add(new MessageObject("Yes we currently talk with " + p1.getText() + " !", false));
                }

        );

        TextField p2 = (TextField) po2.getChildren().get(0);

        // Put a listener to the TextField in order to print the conversation linked to this user
        p2.setOnMouseClicked(
                event -> {
                    chatList.getChildren().clear();
                    chatList.getChildren().add(new MessageObject("This is a conversation with " + p2.getText(), true));
                    chatList.getChildren().add(new MessageObject("Yes we currently talk with " + p2.getText() + " !", false));
                }
        );

        // ***** END OF LISTENERS *****


        // Add to the main frame
        listPeopleConnected.getChildren().add(po2);
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
}