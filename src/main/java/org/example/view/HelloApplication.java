package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.conversation.ConnectedUser;
import org.example.services.SessionService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class HelloApplication extends Application {

    String userName;

    public HelloApplication(String name) {
        userName = name;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/Test_IHM.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/frame_change_username.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // ---- Permettre d'avoir accès au stage dans le contrôleur lors du démarrage
        HelloController controller = fxmlLoader.getController();
        controller.subscribeToObservers();

        controller.addConnectedUser(SessionService.getInstance().getConnectedUsers());
        controller.setStage(stage);
        // ---------------------------------------------------------------------------

        stage.setResizable(false);
        stage.setTitle("You are connected as " + userName);
        stage.setScene(scene);
        System.out.println("AAAH : " + SessionService.getInstance().getConnectedUsers().size());
        stage.show();

    }



    public static void main(String[] args) {
        launch();
    }
}