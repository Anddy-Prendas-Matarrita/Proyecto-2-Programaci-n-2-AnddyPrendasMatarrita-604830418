package proyecto2.proyecto2anddyprendasmatarrita;

import java.io.*;
import java.net.*;
import java.util.List;

public class MuseumsServer {

    private static final int PORT = 12345; // puerto donde escucha el servidor
    private MuseumsManager museumManager = new MuseumsManager();

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor de Museos iniciado en puerto " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // aceptar nueva conexión
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                // Crear un hilo para manejar la conexión del cliente
                new Thread(() -> handleClient(clientSocket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket socket) {
        try (
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            // Recibir un comando del cliente
            String command = (String) in.readObject();

            if ("GET_MUSEUMS".equalsIgnoreCase(command)) {
                List<MahnMuseums> museums = museumManager.getAllMuseums();
                out.writeObject(museums); // enviar lista de museos
                out.flush();
            }

            socket.close(); // cerrar conexión con el cliente
            System.out.println("Cliente desconectado");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Método principal para arrancar el servidor
    public static void main(String[] args) {
        new MuseumsServer().startServer();
    }
}
