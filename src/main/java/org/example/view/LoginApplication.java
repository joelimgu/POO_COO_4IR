package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controler.ListenersInit;

import java.util.Arrays;

public class LoginApplication extends Application  {


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/frame_login.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/frame_change_username.fxml"));
       // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/dialog_error.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        // ---- Permettre d'avoir accès au stage dans le contrôleur lors du démarrage
        LoginController controller = fxmlLoader.getController();
        // ---------------------------------------------------------------------------
        controller.setStage(stage);
        stage.setResizable(false);
        stage.setTitle("Connexion page");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
        ListenersInit.startServers();
        if (Arrays.asList(args).contains("no-gui")) {
            System.out.println("Running with no gui");
            return;
        }
        launch();
    }
}
