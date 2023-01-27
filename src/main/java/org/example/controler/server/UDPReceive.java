package org.example.controler.server;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.model.CustomObservable;
import org.example.model.CustomObserver;
import org.example.model.conversation.ConnectedUser;
import org.example.model.conversation.User;
import org.example.services.HTTPService;
import org.example.services.LoggerService;
import org.example.services.SessionService;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpTimeoutException;
import java.util.ArrayList;
import java.util.List;


public class UDPReceive extends Thread implements Runnable, CustomObservable<List<ConnectedUser>> {
    String m_ip;

    private static DatagramSocket m_ServerSock ;
    private DatagramPacket m_ReceivedPacket ;

    private boolean open;
    private boolean Connected ;

    private int m_state ;

    private User m_user;

    private static final int MAX_UDP_DATAGRAM_LEN = 1000;
    /* -------------------- Getter & Setter --------------------------------*/
    public boolean isOpen()
    {
        return this.open;
    }

    public void setOpen(boolean open)
    {
        this.open = open;
    }

    public int getM_state()
    {
        return this.m_state;
    }

    public  void setM_state(int state)
    {
        this.m_state = state;
    }

    private List<CustomObserver<List<ConnectedUser>>> subscribers= new ArrayList<>();
    /* --------------------------------------------------------------------------*/
    public void run() {
        int port = SessionService.getInstance().getUdp_port();

        LoggerService.getInstance().log("UDP Server started on port: " + port);
        String lText;
        byte[] lMsg = new byte[MAX_UDP_DATAGRAM_LEN];
        DatagramPacket dp = new DatagramPacket(lMsg, lMsg.length);
        DatagramSocket ds = null;
            try {
                ds = new DatagramSocket(port);
                Gson g = new GsonBuilder().setPrettyPrinting().create();
                SessionService s = SessionService.getInstance();
                //disable timeout for testing
                //ds.setSoTimeout(100000);
                while (true) {
                    ds.receive(dp);
                    lText = new String(dp.getData());
                    LoggerService.getInstance().log("UDP packet received of len: " + lText.length() + " | " + lText);
                    if (s.getM_localUser() == null) {
                        continue;
                    }
                    // IP format: /192.168.43.205
                    // TODO: put inside the HTTP service
                    String remoteIp = dp.getAddress().toString().replace("/","");
                    // get local IP
                    String localIp = HTTPService.getInstance()
                            .sendRequest(remoteIp,"/get_self_ip", HTTPService.HTTPMethods.GET,"").join().body();
                    if (remoteIp.equals(localIp)) {
                        continue;
                    }
                    LoggerService.getInstance().log("received IP: " + localIp);
                    SessionService.getInstance().setLocalIP(localIp);
                    // we include ourselves
                    String connectedUsers = g.toJson(SessionService.getInstance().getConnectedUsers());
                    HTTPService.getInstance()
                            .sendRequest(remoteIp, "/receive_connected_users_list", HTTPService.HTTPMethods.POST, connectedUsers)
                                    .thenAccept((r) -> LoggerService.getInstance().log("Sent http from UDP recv correctly"))
                                    .exceptionally((e) -> {
                                        LoggerService.getInstance().log("UDPRECV error: " + e);
                                        LoggerService.getInstance().log("When trying to send HTTP request: " + dp.getAddress() + " and port " + SessionService.getInstance().getHttp_port());
                                        return null;
                                    });
                    LoggerService.getInstance().log("send HTTP request from UDP: to " + dp.getAddress() + " and port " + SessionService.getInstance().getHttp_port() + " with users: " + connectedUsers);
                }
            } catch (HttpTimeoutException e) {
                // TODO: log
            } catch (IOException e) {
                e.printStackTrace();
            }
//        finally {
//            if (ds != null) {
//                ds.close();
//            }
//        }
    }


    public static void main(String arg[]) throws IOException {
        DatagramSocket m_socket = new DatagramSocket();
        byte[] receive = new byte[10000];
        DatagramPacket m_packet = null;
     /*   while (true){
            LoggerService.getInstance().log("listening");
            m_packet = new DatagramPacket(receive, receive.length);
            m_socket.receive(m_packet);

            String str = new String(m_packet.getData());

            byte[] answer = ("I received your packet" ).getBytes(StandardCharsets.UTF_8);
            InetAddress m_adress = m_socket.getLocalAddress();
            //DatagramPacket m_Answer = new DatagramPacket(answer, answer.length, m_adress, m_socket);

        }*/
        UDPReceive m_runnable = new UDPReceive();
        Thread t1 = new Thread(m_runnable);
        t1.start();
        LoggerService.getInstance().log("I'm the main");
    }

    public void notifyAllSubscribers(List<ConnectedUser> c) {
        this.subscribers.forEach((s) -> {
            if(s==null) {
                return;
            }
            s.notify(c);
        });
    }
    @Override
    public int subscribe(CustomObserver<List<ConnectedUser>> o) {
        this.subscribers.add(o);
        return this.subscribers.size() - 1;
    }

    @Override
    public CustomObserver<List<ConnectedUser>> unsubscribe(int i) {
        CustomObserver<List<ConnectedUser>> o = this.subscribers.get(i);
        this.subscribers.set(i, null);
        return o;
    }
}
