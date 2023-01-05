package org.example.model.communication.server;
import org.example.model.conversation.User;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UDPReceive extends Thread {
    String m_ip;

    private static DatagramSocket m_ServerSock ;
    private DatagramPacket m_ReceivedPacket ;

    private boolean open;
    private boolean Connected ;

    private int m_state ;

    private User m_user;
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

    public static void main(String arg[]) throws IOException{
        DatagramSocket m_socket = new DatagramSocket();
        byte[] receive = new byte[10000];
        DatagramPacket m_packet = null;
        while (true){
            m_packet = new DatagramPacket(receive, receive.length);
            m_socket.receive(m_packet);

            String str = new String(m_packet.getData());

            byte[] answer = ("I received your packet" ).getBytes(StandardCharsets.UTF_8);
            InetAddress m_adress = m_socket.getLocalAddress();
            //DatagramPacket m_Answer = new DatagramPacket(answer, answer.length, m_adress, m_socket);

        }
    }
}
