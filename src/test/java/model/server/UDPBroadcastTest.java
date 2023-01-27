package model.server;

import org.example.controler.server.UDPBroadcast;
import org.example.controler.server.UDPReceive;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.net.*;

import java.io.IOException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UDPBroadcastTest {
    public int num_port = 3000;
    public UDPBroadcast m_broadcast = new UDPBroadcast();
    public UDPBroadcast.broadcastUDP m_broadcaster = new UDPBroadcast.broadcastUDP();

    public UDPBroadcastTest() throws IOException {

    }
    @BeforeAll

    public void m_listener() throws IOException {
        // LoggerService.getInstance().log("Thread is listening");
        UDPReceive listener = new UDPReceive();
        listener.start();

    }

    @Test
    public void testSendBroadcast() throws IOException {
        DatagramSocket socket = new DatagramSocket();
        int port = socket.getLocalPort();
        //m_broadcaster.SendBroadcast("test broadcast",4000);

    }

//    @Test
//    public void testNetworkInterface() throws SocketException, UnknownHostException {
//        LoggerService.getInstance().log("printing network interface");
//        LoggerService.getInstance().log("mon local host" + InetAddress.getLocalHost());
//        List<InetAddress> m_list = this.m_broadcaster.listAllBroadcastAddresses();
//        LoggerService.getInstance().log(m_list);
//
//    }
    @Test
    public void testSendUserBroadcast() throws IOException{
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        User user_test = new User("Amnay");
//        SessionService m_session = SessionService.getInstance();
//        m_session.setM_localUser((ConnectedUser) user_test);
//        DatagramSocket socket = new DatagramSocket();
//        int port = socket.getLocalPort();
       // m_broadcaster.SendBroadcast(gson.toJson(m_session.getM_localUser()),4000);

    }

}
