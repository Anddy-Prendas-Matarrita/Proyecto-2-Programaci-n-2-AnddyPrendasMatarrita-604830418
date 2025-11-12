package proyecto2.proyecto2anddyprendasmatarrita;

import java.io.*;
import java.net.*;
import java.util.List;

public class TopicsClient {

    private String serverIP = "localhost"; // o la IP del servidor
    private int serverPort = 12345;

    public List<MahnTopics> getTopicsFromServer() {
        try (Socket socket = new Socket(serverIP, serverPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // Enviar comando al servidor
            out.writeObject("GET_TOPICS");
            out.flush();

            // Recibir lista de colecciones
            List<MahnTopics> topics = (List<MahnTopics>) in.readObject();
            return topics;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
