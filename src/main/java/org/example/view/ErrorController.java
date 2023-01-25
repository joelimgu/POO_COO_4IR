package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.Objects;

public class ErrorController {

    public ImageView imageCross;
    @FXML
    Label textError;

    Stage currentStage;
    Stage parentStage;

    public void setErrorMessage() {
        imageCross = new ImageView(String.valueOf(Objects.requireNonNull(getClass().getResource("/pictures/close.png")).toExternalForm()));

    }

    public void setCurrentStage(Stage s) {
        currentStage = s;
    }

    public void setParentStage(Stage s) {
        parentStage = s;
    }

    public void setErrorText(String errMsg) {
        textError.setText(errMsg);
    }



}
