package org.example.view;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

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

    public void addNewPerson(MouseEvent mouseEvent) {
        GridPane gp = new GridPane();
        TextField textPersonConnect = new TextField("Person connected");
        textPersonConnect.setEditable(false);
        textPersonConnect.setBackground(new Background(new BackgroundFill(Color.valueOf("#00FF00"), CornerRadii.EMPTY, Insets.EMPTY)));
        gp.add(textPersonConnect, 0, 0, 1, 1);
        gp.add(new Circle(), 0, 1, 1, 1);
        listPeopleConnected.getChildren().add(gp);
    }
}