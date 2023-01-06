package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ErrorDialog extends Application {

    String errorMessage;
    Stage parentSta;

    public ErrorDialog(String msg, Stage parentStage) {
        errorMessage = msg;
        parentSta = parentStage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/dialog_error.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        ErrorController controller = fxmlLoader.getController();
        controller.setCurrentStage(stage);
        controller.setParentStage(parentSta);
        controller.setErrorText(errorMessage);

        // ---- Permettre d'avoir accès au stage dans le contrôleur lors du démarrage
        //LoginController controller = fxmlLoader.getController();
        // ---------------------------------------------------------------------------
        //controller.setStage(stage);
        stage.setResizable(false);
        stage.setTitle("Error !");
        stage.setScene(scene);
        stage.show();

        // Put action on close of the error message
        stage.setOnCloseRequest(event -> {
            parentSta.show();
            stage.close();
        });

    }
}
