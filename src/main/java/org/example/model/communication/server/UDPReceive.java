package org.example.model.communication.server;
import org.example.model.conversation.User;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;


public class UDPReceive extends Thread implements Runnable {
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

    private List<Flow.Subscriber> subscribers = new ArrayList<>();

    /* --------------------------------------------------------------------------*/
    public void run() {
        System.out.println("je suis lancé");
        String lText;
        byte[] lMsg = new byte[MAX_UDP_DATAGRAM_LEN];
        DatagramPacket dp = new DatagramPacket(lMsg, lMsg.length);
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket(4000);
            //disable timeout for testing
            //ds.setSoTimeout(100000);
            ds.receive(dp);
            lText = new String(dp.getData());
            System.out.println("UDP packet received" + lText);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ds != null) {
                ds.close();
            }
        }
    }


    public static void main(String arg[]) throws IOException {
        DatagramSocket m_socket = new DatagramSocket();
        byte[] receive = new byte[10000];
        DatagramPacket m_packet = null;
     /*   while (true){
            System.out.println("listening");
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
        System.out.println("I'm the main");
    }

}
