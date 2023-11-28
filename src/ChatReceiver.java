import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
public class ChatReceiver {
    private static final int RECEIVE_PORT = 5002;
    private static List<PeerInfo> receivedPeers = new ArrayList<>();

    public void start() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(RECEIVE_PORT);
            System.out.println("Receiver started on port " + RECEIVE_PORT);

            while (true) {
// Accept incoming connections from peers
                Socket clientSocket = serverSocket.accept();

// Create a new thread to handle the peer's registration
                Thread receiveThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handleMessage(clientSocket);
                    }
                });
                receiveThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleMessage(Socket clientSocket) {
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
// Retrieve the client's IP address
            String clientIP = clientSocket.getInetAddress().getHostAddress();
            System.out.println("Client connected from IP: " + clientIP);
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

// Receive chat message
            ChatMessage m = (ChatMessage) inputStream.readObject();
// peerInfo.setIpAddress(clientIP);
//
// // Register the peer
// receivePeer(peerInfo);

// Send a success response to the peer
            outputStream.writeObject(new MessageResponse(true));

// System.out.println("Peer received: " + peerInfo.getName());
            System.out.println("Message: " + m.getContent());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
// Close the streams in the finally block
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void receivePeer(PeerInfo peerInfo) {
        receivedPeers.add(peerInfo);
    }

    public static void main(String[] args) {
        ChatReceiver chatReceiver = new ChatReceiver();
        chatReceiver.start();
    }

}
