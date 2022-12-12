package org.example.model.communication.server;

import org.example.model.conversation.ConnectedUser;

import javax.lang.model.type.NullType;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.net.NetworkInterface;

public class UDPBroadcast {
    private static final int MAX_UDP_DATAGRAM_LEN = 100;


    /**
     * Class braodcastUDP qui permet l'envoie en UDP
     */

    public static class broadcastUDP {

        /**
         * Constructeur broadcast UDP
         */
        public broadcastUDP() {
        }

        /**
         * Methode pour envoyer un message en broadcast
         *
         * @param //envoiebroadcast message a envoyer
         * @param port              port a utiliser
         * @throws IOException
         */

        public void SendBroadcast(String BroadcastMessage, int port) throws IOException {
            for (InetAddress AddrBroadcast : listAllBroadcastAddresses()) {
                try {
                    DatagramSocket socket = new DatagramSocket();
                    socket.setBroadcast(true);
                    byte[] buffer = BroadcastMessage.getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, AddrBroadcast, port);
                    System.out.println("Envoie du message en broadcast to" + AddrBroadcast);

                    //DatagramPacket packet = new DatagramPacket(buffer,buffer.length,AddrBroadcast,port);
                    //System.out.println("Envoie du message en broadcast to" + AddrBroadcast );

                    socket.send(packet);
                    socket.close();
                } catch (IOException e) {
                    // todo : handler
                    e.printStackTrace();
                }
            }
            // TODO: 12/12/22 split listener and sender in two differents methods
//            String lText;
//            byte[] receivedData = new byte[MAX_UDP_DATAGRAM_LEN];

        }


        /**
         * Method to get the adresses associated to a broadcast adress
         *
         * @return list of adresses
         * @throws SocketException
         */
        public List<InetAddress> listAllBroadcastAddresses() throws SocketException {
            List<InetAddress> broadcastList = new ArrayList<>();
            Enumeration<NetworkInterface> interfaces
                    = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();

                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue;
                }

                networkInterface.getInterfaceAddresses().stream()
                        .map(a -> a.getBroadcast())
                        .filter(Objects::nonNull)
                        .forEach(broadcastList::add);
            }
            return broadcastList;
        }

        /**
         * Methode pour envoyer un message en udp
         *
         * @param msg   message a envoyer
         * @param port  port a utiliser
         * @param laddr adresse a laquelle envoyer
         * @throws SocketException
         */
        public void sendUDP(String msg, int port, String laddr) throws SocketException {

            DatagramSocket socket = new DatagramSocket();

            byte[] buffer = msg.getBytes();
            DatagramPacket packet;
            try {
                packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(laddr), port);
                System.out.println("Envoie msg");
                socket.send(packet);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public List<ConnectedUser> DiscoverNetwork() {
            ArrayList<ConnectedUser> m_list = new ArrayList<ConnectedUser>();
            //todo : list of  connected users ;
            return m_list;
        }
    }
}

