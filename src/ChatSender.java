import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChatSender {
    private static final String SERVER_IP = "10.90.0.87";
    private static final int MESSAGE_PORT = 5001;


    public static void main(String[] args) {
        String message = "TEEEST, SERDAR IST HIEER"; // Peer msg

        ChatMessage chatmessage = new ChatMessage(message);



        try (Socket socket = new Socket(SERVER_IP, MESSAGE_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            // Send peer registration information to the central server
            outputStream.writeObject(chatmessage);

            MessageResponse messageresponse = (MessageResponse) inputStream.readObject();

            if (messageresponse.isSuccess()) {
                System.out.println("Message sent successful");
                System.out.println("Serdar hat folgendes gesendet: "+chatmessage.getContent());
            } else {
                System.out.println("Message failed");
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
