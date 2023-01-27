package org.example.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;

import javafx.stage.Stage;

import org.example.model.communication.server.httpEvents.*;
import org.example.model.conversation.ConnectedUser;
import org.example.model.conversation.Conversation;
import org.example.model.conversation.Message;
import org.example.model.conversation.User;
import org.example.services.HTTPService;
import org.example.services.SessionService;
import org.example.services.StorageService;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class HelloController {
    public VBox listPeopleConnected;
    public TextArea messageSendField;
    @FXML
    private TextField welcomeText;
    @FXML
    private VBox chatList;
    @FXML
    ScrollPane scrollChatPane;

    @FXML
    BorderPane borderPaneGlobal;

    ConnectedUser selectedConnectedUser = null;

    private boolean isDisconnectedUserPrinted = false;

    private boolean isListened = false;
    private int nbMessages = 0;

    Stage myStage;

    public void subscribeToObservers() {
        // This function calls a recursive function to subscribe to completable futures to behave to events
        resubscribe();
    }


    public void resubscribe() {
        CompletableFuture<HTTPEvent> cf = new CompletableFuture<>();
        SessionService.getInstance().getHttpServer().addEventList(cf);
        cf.thenAccept((ev) -> {

                    System.out.println("CPF executed");
                    System.out.println("The type of ev is : " + ev.getClass());
                    System.out.println("The compared type : " + ConnectedUsersListReceived.class);


                }
        );

        CompletableFuture<HTTPEvent> cfDisco = new CompletableFuture<>();
        SessionService.getInstance().getHttpServer().addEventList(cfDisco);
        cfDisco.thenAccept((ev) -> {
            System.out.println("The type of ev is : " + ev.getClass());
            System.out.println("The compared type : " + UserDisconnectedEvent.class);
            resubscribe();

            // Case of disconnected user -> delete user from the main frame
            if (ev.getClass() == UserDisconnectedEvent.class) {
                UserDisconnectedEvent culr = (UserDisconnectedEvent) ev;
                Platform.runLater(() -> deleteUser(culr.u));
            }

            // Case of a new user connected -> refreshing connected users frame
            if (ev.getClass() == ConnectedUsersListReceived.class) {
                ConnectedUsersListReceived culr = (ConnectedUsersListReceived) ev;
                Platform.runLater(() -> addConnectedUser(culr.connectedUsers));
            }

            // Case of a new message received -> print it in the frame and save it to the database
            if (ev.getClass() == NewMessageEvent.class) {
                NewMessageEvent culr = (NewMessageEvent) ev;
                Platform.runLater(() -> addNewIncomingMessage(culr.getM()));
            }

            if (ev.getClass() == ChangedPseudoEvent.class) {
                ChangedPseudoEvent culr = (ChangedPseudoEvent) ev;
                Platform.runLater(() -> updatePseudoUsername(culr.updatedUser));
            }

        });

    }

    public void updatePseudoUsername(User u) {
        for (int i = 0; i < listPeopleConnected.getChildren().size(); i++) {
            PersonObject po = (PersonObject) listPeopleConnected.getChildren().get(i);
            if (po.getUUID().equals(u.getUuid())) {
                po.setNewUsername(u.getPseudo());
                SessionService.getInstance().updatePseudo(po.getConnectedUser(), u.getPseudo());
                try {
                    StorageService.getInstance().updatePseudo(po.getConnectedUser(), u.getPseudo());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void setStage(Stage s) {
        myStage = s;
    }
    @FXML
    public void addNewMessages(ScrollEvent scrollEvent) {
    }
    @FXML
    public void addMessage(MouseEvent mouseEvent) {
    }

    // TODO : delete this function or put a new behaviour (this one is useless)
    public void addNewPerson(MouseEvent mouseEvent) {
        System.out.println("You have clicked on the chat VBOX ! :)");

    }


    public void sendMessageClick(MouseEvent mouseEvent) {

        MessageObject mo;

        Gson g = new GsonBuilder().setPrettyPrinting().create();

        SessionService ss = SessionService.getInstance();
        Message m = new Message(ss.getM_localUser(), selectedConnectedUser, messageSendField.getText());

        if (selectedConnectedUser.getIP() == null) {
            ErrorDialog ed2 = new ErrorDialog("Error : the user is not connected", this.myStage);
            try {
                ed2.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("The IP address of the person is null :)");
        } else {
            System.out.println(selectedConnectedUser.getPseudo());
            HTTPService.getInstance()
                    .sendRequest(selectedConnectedUser.getIP(), "/receive_message", HTTPService.HTTPMethods.POST, g.toJson(m)).exceptionally(err -> {
                //System.out.println("Error while sending the message");
                ErrorDialog ed1 = new ErrorDialog("Error while sending a message", this.myStage);
                        try {
                            ed1.start(new Stage());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        err.printStackTrace();
                return null;
            });

            mo = new MessageObject(m.getText(), false);
            chatList.getChildren().add(mo);

            try {
                StorageService.getInstance().save(m);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            messageSendField.setText("");
            messageSendField.setPromptText("Write your message here");
        }

        // Put the scroll pane at the end of the list
        if (!isListened) {
            chatList.heightProperty().addListener(observable -> scrollChatPane.setVvalue(1D));
            isListened = true;
        }


    }

    public void sendMessageEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            sendMessageClick(null);
        }
    }

    public void goToChangeUsernameFrame(ActionEvent actionEvent) throws Exception {
        ChangeUsenameApplication cua = new ChangeUsenameApplication(myStage);
        cua.start(new Stage());
    }

    public void validateOK(MouseEvent mouseEvent) {

    }

    public void changeName(String name) {
        myStage.setTitle("You are connected as " + name);
    }

    synchronized void addConnectedUser(List<ConnectedUser> users) {
        listPeopleConnected.getChildren().clear();
        for (ConnectedUser user : users) {

            if (!(user.getUuid().equals(SessionService.getInstance().getM_localUser().getUuid()))) {
                Gson g = new GsonBuilder().setPrettyPrinting().create();
                System.out.println("Trying to add connected user" + g.toJson(users));
                PersonObject po = new PersonObject(user, true);
                System.out.println("IFJODJSOIFIJOSD" + user.toString());
                TextField p1 = (TextField) po.getChildren().get(0);
                p1.setOnMouseClicked(
                        event -> {
                            // *** ACCESS TO UUID ** ///
                            UUID uuid = po.getUUID();
                            System.out.println(uuid.toString() + "/" + po.getConnectedUser().getIP());
                            chatList.getChildren().clear();
                            selectedConnectedUser = po.getConnectedUser();
                            try {
                                Conversation c = StorageService.getInstance().getConversation(SessionService.getInstance().getM_localUser(), selectedConnectedUser);
                                List<Message> messages = c.getMessages();
                                for (Message m : messages) {
                                    if (m.getSender().getUuid().equals(SessionService.getInstance().getM_localUser().getUuid())) {
                                        chatList.getChildren().add(new MessageObject(m.getText(), false));
                                    } else {
                                        chatList.getChildren().add(new MessageObject(m.getText(), true));
                                    }
                                }
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            // ********************* //
                            // TODO : Find a way to get the messages of a connected user in the front part with his UUID
                        }
                );
                listPeopleConnected.getChildren().add(po);
                try {
                    isDisconnectedUserPrinted = false;
                    addAllDisconnectedUser();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    synchronized public void deleteUser(User u) {
        System.out.println("We try to delete an user of type : " + u.toString());
        for(int i = 0; i < listPeopleConnected.getChildren().size(); i++) {
            PersonObject po =  (PersonObject) listPeopleConnected.getChildren().get(i);
            ConnectedUser name = po.getConnectedUser();
            System.out.println(po.getUUID() + "/" + u.getUuid());
            if (po.getUUID().equals(u.getUuid())) {
                System.out.println("GOOD");
                listPeopleConnected.getChildren().remove(i);
                // TODO : Maybe try to find a way to delete an user by his UUID (the username can change)
                SessionService.getInstance().deleteConnectedUserByName(u.getPseudo());
                PersonObject po2 = new PersonObject(new ConnectedUser(name.getPseudo(), name.getUuid(), null), false);

                TextField p1 = (TextField) po2.getChildren().get(0);
                p1.setOnMouseClicked( event -> {
                    // *** ACCESS TO UUID ** ///
                    chatList.getChildren().clear();
                    selectedConnectedUser = po2.getConnectedUser();
                    System.out.println(selectedConnectedUser.getUuid().toString() + "/" + po2.getConnectedUser().getIP());

                    try {
                        Conversation c = StorageService.getInstance().getConversation(SessionService.getInstance().getM_localUser(), selectedConnectedUser);
                        List<Message> messages = c.getMessages();
                        chatList.getChildren().clear();
                        for (Message m : messages) {
                            if (m.getSender().getUuid().equals(SessionService.getInstance().getM_localUser().getUuid())) {
                                chatList.getChildren().add(new MessageObject(m.getText(), false));
                            } else {
                                chatList.getChildren().add(new MessageObject(m.getText(), true));
                            }
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                listPeopleConnected.getChildren().add(po2);
            }
        }

    }

    synchronized void addNewIncomingMessage(Message m) {
        MessageObject mo = new MessageObject(m.getText(), true);
        if (selectedConnectedUser != null) {
            chatList.getChildren().add(mo);
            try {
                StorageService.getInstance().save(m);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addAllDisconnectedUser() throws SQLException {
        if (!isDisconnectedUserPrinted) {
            StorageService sts = StorageService.getInstance();
            SessionService ss = SessionService.getInstance();
            List<User> lu = sts.getAllRegisteredUsers();


            for (User u : lu) {

                boolean isAlreadyPut = false;

                // Check if the UUID of the user is already in the list

                for (int i = 0; i < listPeopleConnected.getChildren().size(); i++) {
                    PersonObject poo = (PersonObject) listPeopleConnected.getChildren().get(i);
                    if (u.getUuid().equals(poo.getUUID())) {
                        isAlreadyPut = true;
                    }
                }

                if (!(u.getUuid().equals(ss.getM_localUser().getUuid())) && !isAlreadyPut) {
                    Gson g = new GsonBuilder().setPrettyPrinting().create();
                    System.out.println("Trying to add connected user" + g.toJson(lu));
                    PersonObject po = new PersonObject(new ConnectedUser(u.getPseudo(), u.getUuid(), null), false);
                    System.out.println("IFJODJSOIFIJOSD" + u.toString());
                    TextField p1 = (TextField) po.getChildren().get(0);

                    chatList.getChildren().clear();
                    selectedConnectedUser = po.getConnectedUser();

                    p1.setOnMouseClicked(
                            event -> {
                                // *** ACCESS TO UUID ** ///
                                chatList.getChildren().clear();
                                selectedConnectedUser = po.getConnectedUser();
                                System.out.println(selectedConnectedUser.getUuid().toString() + "/" + po.getConnectedUser().getIP());

                                try {
                                    Conversation c = StorageService.getInstance().getConversation(SessionService.getInstance().getM_localUser(), selectedConnectedUser);
                                    List<Message> messages = c.getMessages();
                                    chatList.getChildren().clear();
                                    for (Message m : messages) {
                                        if (m.getSender().getUuid().equals(SessionService.getInstance().getM_localUser().getUuid())) {
                                            chatList.getChildren().add(new MessageObject(m.getText(), false));
                                        } else {
                                            chatList.getChildren().add(new MessageObject(m.getText(), true));
                                        }
                                    }
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                // ********************* //
                            }
                    );
                    listPeopleConnected.getChildren().add(po);

                }
            }
        }
        isDisconnectedUserPrinted = true;
    }

}