package org.example.view;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
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
    @FXML
    private TextField welcomeText;
    @FXML
    private VBox chatList;



    @FXML
    protected void onHelloButtonClick() throws InterruptedException {
        welcomeText.setText(welcomeText.getText() + "Yaya");
    }

    @FXML
    public void addNewMessages(ScrollEvent scrollEvent) {
        System.out.println(scrollEvent.getDeltaY());
    }
    @FXML
    public void addMessage(MouseEvent mouseEvent) {
        chatList.getChildren().add(new Text("MESSAGE"));
    }

    public void addNewPerson(MouseEvent mouseEvent) throws IOException {

        PersonObject po = new PersonObject("CHOU FLEUR", false);
        PersonObject po2 = new PersonObject("PATATE", true);
        listPeopleConnected.getChildren().add(po2.getValue());
        listPeopleConnected.getChildren().add(po.getValue());
    }
}