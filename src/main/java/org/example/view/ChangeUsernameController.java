package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ChangeUsernameController {

    public Stage myStage;
    public Stage parentStage;

    public void setStage(Stage stage, Stage pStage) {
        myStage = stage;
        parentStage = pStage;
    }

    public void validateOK(MouseEvent mouseEvent) {
        parentStage.show();
        myStage.close();
    }

    public void validateCancel(MouseEvent mouseEvent) {
        parentStage.show();
        myStage.close();
    }


}
