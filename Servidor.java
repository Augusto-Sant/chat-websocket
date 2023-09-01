import java.io.*;
import java.net.*;

public class Servidor {
    private static Servidor instance = null;
    private ServerSocket serverSocket;

    private Servidor() {
        try {
            serverSocket = new ServerSocket(7000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pega a instancia do Servidor
     *
     * @return The Servidor instance.
     */
    public static synchronized Servidor getInstance() {
        if (instance == null) {
            instance = new Servidor();
        }
        return instance;
    }

    /**
     * Começa a escutar qualquer cliente que possa conectar
     * e chamando o handleClient para cada um.
     */
    public void startListening() {
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                // Create a new thread for each client connection
                Thread clientThread = new Thread(() -> handleClient(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lida com um cliente chamando os buffers para ler as mensagens
     * e notificando sobre a conexão deles.
     */
    private void handleClient(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String clientAddress = clientSocket.getInetAddress().getHostAddress();
            System.out.println("Connected to client " + clientAddress);

            String line;
            while ((line = reader.readLine()) != null && !line.equals("sair")) {
                System.out.println("Client " + clientAddress + ": " + line);
            }

            System.out.println("Client " + clientAddress + " disconnected.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ativa o Servidor
     */
    public static void main(String[] args) {
        Servidor server = Servidor.getInstance();
        server.startListening();
    }
}
