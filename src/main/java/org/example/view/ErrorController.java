package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ErrorController {

    @FXML
    Label textError;

    Stage currentStage;
    Stage parentStage;

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
