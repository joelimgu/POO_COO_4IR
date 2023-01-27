package org.example.view;

import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.model.conversation.ConnectedUser;
import org.example.model.conversation.User;

import java.util.Objects;
import java.util.UUID;


public class PersonObject extends BorderPane {

    private ImageView image;
    private ConnectedUser user;
    TextField textPersonConnect;

    public PersonObject(ConnectedUser u, boolean status)  {
        user = u;
        Insets i = new Insets(3,3,3,5);
        Insets iImage = new Insets(9,3,3,5);

        if (status) {
            image = new ImageView(String.valueOf(Objects.requireNonNull(getClass().getResource("/pictures/connected.png")).toExternalForm()));
        } else {
            image = new ImageView(String.valueOf(Objects.requireNonNull(getClass().getResource("/pictures/disconnected.png")).toExternalForm()));
        }

        image.setFitHeight(13);
        image.setFitWidth(13);
        image.setStyle("-fx-alignment: center");
        textPersonConnect = new TextField(u.getPseudo());
        textPersonConnect.setFont(Font.font("Liberation Mono", FontWeight.NORMAL, 12));
        textPersonConnect.setBackground(Background.EMPTY);
        textPersonConnect.setPrefSize(130,20);
        textPersonConnect.setStyle("-fx-text-fill: #ecf0f1; -fx-alignment: center");
        textPersonConnect.setEditable(false);

        this.setRight(textPersonConnect);
        BorderPane.setMargin(textPersonConnect, i);
        this.setLeft(image);
        this.centerProperty();
        BorderPane.setMargin(image, iImage);
    }

    public PersonObject getValue() {
        return this;
    }

    public String getUsername() {
        return user.getPseudo();
    }

    public void setNewUsername(String s) {
        textPersonConnect.setText(s);
    }

    public UUID getUUID() {
        return user.getUuid();
    }

    public ConnectedUser getConnectedUser() {
        return user;
    }

}