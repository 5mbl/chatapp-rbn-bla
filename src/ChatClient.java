import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChatClient {
    private static final String SERVER_IP = "10.50.205.10";
    private static final int SERVER_PORT = 5000;
//    private static final int MESSAGE_PORT = 5001;

    public static void main(String[] args) {
        String name = "Serdar (ChatReceiver ALIVE!)"; // Peer name

        PeerInfo peerInfo = new PeerInfo(name);

        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            // Send peer registration information to the central server
            outputStream.writeObject(peerInfo);

            // Receive the registration response from the server
            RegistrationResponse response = (RegistrationResponse) inputStream.readObject();

            if (response.isSuccess()) {
                System.out.println("Peer registration successful");
                for ( PeerInfo pi : response.getRegisteredPeers()) {
                    System.out.println("Peer: " + pi.getName() + " (" +
                            pi.getIpAddress() + ")");
                }
            } else {
                System.out.println("Peer registration failed");
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
