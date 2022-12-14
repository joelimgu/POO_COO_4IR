package org.example.model.communication.server;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.model.conversation.User;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;



public class UDPReceive extends Thread implements Runnable {
    String m_ip;

    private static DatagramSocket m_ServerSock ;
    private DatagramPacket m_ReceivedPacket ;

    private boolean open;
    private boolean Connected ;

    private int m_state ;

    private User m_user;

    private static final int MAX_UDP_DATAGRAM_LEN = 100;
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

    /* --------------------------------------------------------------------------*/
    public void run() {
        //System.out.println("je suis lancé");
        String lText;
        byte[] receivedData = new byte[MAX_UDP_DATAGRAM_LEN];
        byte[] sendData = new byte[MAX_UDP_DATAGRAM_LEN];
        DatagramPacket dp = new DatagramPacket(receivedData, receivedData.length);
        DatagramSocket ds = null;
        Gson g = new GsonBuilder().create();
        try {
            ds = new DatagramSocket(4000);
            //disable timeout for testing
            //ds.setSoTimeout(100000);
            ds.receive(dp);
            lText = new String(dp.getData());
            User user = g.fromJson(lText,User.class);
            System.out.println("UDP packet received : \n" + lText);
            InetAddress IpAddress = dp.getAddress();
            String sendString = "Moi aussi je aussi co";
            sendData = sendString.getBytes();
            System.out.println(IpAddress.getHostAddress());
            DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,IpAddress,ds.getLocalPort());
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


    public static void main(String arg[]) throws IOException{
        DatagramSocket m_socket = new DatagramSocket();
        byte[] receive = new byte[10000];
        DatagramPacket m_packet = null;
        UDPReceive m_runnable = new UDPReceive();
        Thread t1 = new Thread(m_runnable);
        t1.start();
        System.out.println("I'm the main");
    }
}
