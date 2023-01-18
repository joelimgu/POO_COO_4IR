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

import java.util.UUID;


public class PersonObject extends BorderPane {

    private ImageView image;
    private ConnectedUser user;

    public PersonObject(ConnectedUser u)  {
        user = u;
        Insets i = new Insets(3,3,3,5);
        Insets iImage = new Insets(9,3,3,5);

       /* if (status) {
            image = new ImageView(String.valueOf(getClass().getResource("../../../pictures/connected.png")));
        } else {*/
            image = new ImageView(String.valueOf(getClass().getResource("../../../pictures/connected.png")));
        //}

        image.setFitHeight(13);
        image.setFitWidth(13);
        image.setStyle("-fx-alignment: center");
        TextField textPersonConnect = new TextField(u.getPseudo());
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

    public UUID getUUID() {
        return user.getUuid();
    }

    public ConnectedUser getConnectedUser() {
        return user;
    }

}
