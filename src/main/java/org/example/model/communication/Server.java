package org.example.model.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;


public class Server implements Runnable {

    private final ArrayBlockingQueue<Package> buffer = new ArrayBlockingQueue<>(64);

    @Override
    public void run() {
        System.out.println("Server starting...");
        // TODO: catch error correctly
        try (ServerSocket s = new ServerSocket(9999)){
            System.out.println("Listening...");
            Socket socket = s.accept();
            System.out.println("Connexion accepted...");
            try {
//                ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());

                Package p = new Package("hello");
                ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
                objectOutput.writeObject(p);
//                Package tmp = (Package) objectInput.readObject();
//                System.out.println("Received :" + tmp);
//                this.buffer.add(tmp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Package getNextPackage() {
        return this.buffer.poll();
    }


}
