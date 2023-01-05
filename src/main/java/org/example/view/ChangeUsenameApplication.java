package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChangeUsenameApplication extends Application {

    Stage parentStage;

    public ChangeUsenameApplication(Stage stage) {
        parentStage = stage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/Test_IHM.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/frame_change_username.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // ---- Permettre d'avoir accès au stage dans le contrôleur lors du démarrage
        ChangeUsernameController controller = fxmlLoader.getController();
        controller.setStage(stage, parentStage);
        // ---------------------------------------------------------------------------

        //stage.setResizable(false);
        stage.setTitle("Change your username");
        stage.setScene(scene);
        stage.show();
        parentStage.hide();

    }
}
