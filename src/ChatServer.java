import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
	private static final int REGISTER_PORT = 5000;
	private static List<PeerInfo> registeredPeers = new ArrayList<>();

	public void start() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(REGISTER_PORT);
			System.out.println("Central Server started on port " + REGISTER_PORT);

			while (true) {
				// Accept incoming connections from peers
				Socket clientSocket = serverSocket.accept();

				// Create a new thread to handle the peer's registration
				Thread registrationThread = new Thread(new Runnable() {
					@Override
					public void run() {
						handlePeerRegistration(clientSocket);
					}
				});
				registrationThread.start();
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

	private void handlePeerRegistration(Socket clientSocket) {
		ObjectInputStream inputStream = null;
		ObjectOutputStream outputStream = null;
		try {
			// Retrieve the client's IP address
			String clientIP = clientSocket.getInetAddress().getHostAddress();
			System.out.println("Client connected from IP: " + clientIP);

			inputStream = new ObjectInputStream(clientSocket.getInputStream());
			outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

			// Receive peer registration information
			PeerInfo peerInfo = (PeerInfo) inputStream.readObject();
			peerInfo.setIpAddress(clientIP);

			// Register the peer
			registerPeer(peerInfo);

			// Send a success response to the peer
			outputStream.writeObject(new RegistrationResponse(true, registeredPeers));

			System.out.println("Peer registered: " + peerInfo.getName());

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

	private synchronized void registerPeer(PeerInfo peerInfo) {
		registeredPeers.add(peerInfo);
	}

	public static void main(String[] args) {
		ChatServer chatServer = new ChatServer();
		chatServer.start();
	}
}
