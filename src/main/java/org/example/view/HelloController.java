package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static java.lang.Thread.sleep;

public class HelloController {
    @FXML
    private TextField welcomeText;

    @FXML
    protected void onHelloButtonClick() throws InterruptedException {
        welcomeText.setText(welcomeText.getText() + "Yaya");
    }
}