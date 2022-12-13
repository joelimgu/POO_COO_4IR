package org.example.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

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

    public void addNewPerson(MouseEvent mouseEvent) throws IOException {
        PersonObject po = new PersonObject("YAYA", false);
        PersonObject po2 = new PersonObject("KIKI", true);
        listPeopleConnected.getChildren().add(po2.getValue());
        listPeopleConnected.getChildren().add(po.getValue());
    }


    public void sendMessageClick(MouseEvent mouseEvent) {
        scrollChatPane.setVvalue(1.0);
        MessageObject mo;
        if (nbMessages%2 == 0) {
            mo = new MessageObject(messageSendField.getText(), true);
            //VBox.setVgrow(mo, Priority.ALWAYS);
            chatList.getChildren().add(mo);
            scrollChatPane.setVvalue(1.0);

        } else {
            mo = new MessageObject(messageSendField.getText(), false);
            //VBox.setVgrow(mo, Priority.ALWAYS);
            chatList.getChildren().add(mo);
            scrollChatPane.setVvalue(1.0);

            //VBox.setVgrow(mo, Priority.ALWAYS);

        }
        nbMessages++;
        messageSendField.setText("");
        messageSendField.setPromptText("Write your message here");

        scrollChatPane.setVvalue(1.0);
        scrollChatPane.setVvalue(1.0);

        // Put the scroll pane at the end of the list
        if (!isListened) {
            chatList.heightProperty().addListener(observable -> scrollChatPane.setVvalue(1D));
            isListened = true;
        }
    }
}