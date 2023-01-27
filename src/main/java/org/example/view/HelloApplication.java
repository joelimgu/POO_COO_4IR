package org.example.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.conversation.ConnectedUser;
import org.example.model.conversation.Message;
import org.example.services.HTTPService;
import org.example.services.LoggerService;
import org.example.services.SessionService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class HelloApplication extends Application {

    public static HelloController hc = null;
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
        hc = controller;
        controller.subscribeToObservers();

        controller.addConnectedUser(SessionService.getInstance().getRemoteConnectedUsers());
        if (SessionService.getInstance().getRemoteConnectedUsers().size() == 0) {
            try {
                controller.addAllDisconnectedUser();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        controller.setStage(stage);
        // ---------------------------------------------------------------------------

        stage.setResizable(false);
        stage.setTitle("You are connected as " + userName);
        stage.setScene(scene);
        /*try {
            controller.addAllDisconnectedUser();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
        LoggerService.getInstance().log("AAAH : " + SessionService.getInstance().getConnectedUsers().size());
        stage.show();

        // SET BEHAVIOUR WHILE CLOSING
        stage.setOnCloseRequest(event -> {

            LoggerService.getInstance().log("Stage is closing");
            Gson g = new GsonBuilder().setPrettyPrinting().create();

            SessionService ss = SessionService.getInstance();

            List<ConnectedUser> list = SessionService.getInstance().getRemoteConnectedUsers();
            List<CompletableFuture<?>> disconnectFutures = new ArrayList<>();
            for (ConnectedUser cu : list) {
                LoggerService.getInstance().log(cu.getPseudo());
                if (cu.getIP() == null) {
                    continue;
                }
                disconnectFutures.add(HTTPService.getInstance().sendRequest(cu.getIP(), "/disconnect", HTTPService.HTTPMethods.POST, g.toJson(ss.getM_localUser())).exceptionally(err -> {
                    LoggerService.getInstance().log("Error while sending the message");
                    err.printStackTrace();
                    return null;
                }));
                }
            CompletableFuture.allOf(disconnectFutures.toArray(new CompletableFuture<?>[disconnectFutures.size()])).join();
            // Close all the threads which does not depend on the JavaFX frame by a SIGNAL SIGOK at the end of the execution
            System.exit(0);

            // Save file
        });

    }

    public static void main(String[] args) {
        launch();
    }
}
