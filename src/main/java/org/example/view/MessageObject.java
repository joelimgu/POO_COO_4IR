package org.example.view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MessageObject extends BorderPane {

    /*
    MessageObject : Create an JavaFX object to put on the chat frame
    Parameters :
        - message -> Text of the message to write
        - type -> if true, message sended
                  if false, message received
     */

    private String message;
    private boolean type;
    private Label messageText;


    public MessageObject(String message, boolean type) {
        this.message = message;
        this.type = type;
        messageText = new Label(message);


        double widthConfiguration;

        // Configure the width of the text area to put the background
        if (message.length() < 73) {
            widthConfiguration = (message.length())* (500.0/72) + 15;
        } else {
            widthConfiguration = 515;
        }

        Insets i = new Insets(10,10,10,10);

        if (type) {
            messageText.setStyle("-fx-text-fill: #444444; -fx-padding: 5px;-fx-background-radius: 5px;-fx-alignment: center-left; -fx-word-break: keep-all;-fx-background-color: #bdc3c7");
            messageText.setFont(Font.font("Ubuntu Mono", FontWeight.NORMAL, 14));
            //messageText.backgroundProperty().set(new Background());
            this.setLeft(messageText);

        } else {
            messageText.setStyle("-fx-text-fill: #EEEEEE;-fx-alignment: center-right; -fx-background-radius: 5px; -fx-padding: 5px ;-fx-word-break: break-all;-fx-background-color: #3498db");
            messageText.setFont(Font.font("Ubuntu Mono", FontWeight.NORMAL, 14));

            this.setRight(messageText);
        }

        messageText.setWrapText(true);
        messageText.setMaxSize(widthConfiguration,((int) ((message.length()/74)+1)*16) + 15);
        messageText.setMinSize(widthConfiguration,((int) ((message.length()/74)+1)*16) + 15);
        messageText.setPrefSize(widthConfiguration,((int) ((message.length()/74)+1)*16) + 15);
        //messageText.setMaxHeight(Double.MAX_VALUE);
        BorderPane.setMargin(messageText, i);
        //this.maxHeight(Double.MAX_VALUE);
        //this.maxWidth(Double.MAX_VALUE);
    }

    public MessageObject getValue() {
        return this;
    }
}
