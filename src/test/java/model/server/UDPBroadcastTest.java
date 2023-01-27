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

}
