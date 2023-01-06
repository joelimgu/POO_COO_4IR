package model.server;

import org.example.model.communication.server.UDPBroadcast;
import org.example.model.communication.server.UDPReceive;
import org.example.model.conversation.ConnectedUser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.net.*;
import java.util.ArrayList;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UDPBroadcastTest {
    public int num_port = 4000;
    public UDPBroadcast m_broadcast = new UDPBroadcast();
    public UDPBroadcast.broadcastUDP m_broadcaster = new UDPBroadcast.broadcastUDP();

    public UDPBroadcastTest() throws IOException {

    }
    @BeforeAll

    public void m_listener() throws IOException {
        // System.out.println("Thread is listening");
        UDPReceive listener = new UDPReceive();
        listener.start();

    }

    @Test
    public void testSendBroadcast() throws IOException {
        DatagramSocket socket = new DatagramSocket();
        int port = socket.getLocalPort();
        m_broadcaster.SendBroadcast("test broadcast",4000);
    }

    @Test
    public void testNetworkInterface() throws SocketException, UnknownHostException {
        System.out.println("printing network interface");
        System.out.println("mon local host" + InetAddress.getLocalHost());
        List<InetAddress> m_list = this.m_broadcaster.listAllBroadcastAddresses();
        System.out.println(m_list);
    }

}
