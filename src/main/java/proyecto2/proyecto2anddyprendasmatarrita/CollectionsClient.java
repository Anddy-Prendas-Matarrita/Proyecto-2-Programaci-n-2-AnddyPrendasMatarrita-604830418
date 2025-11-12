package proyecto2.proyecto2anddyprendasmatarrita;

import java.io.*;
import java.net.*;
import java.util.List;

public class CollectionsClient {

    private String serverIP = "localhost"; // o la IP del servidor
    private int serverPort = 12345;

    public List<MahnCollections> getCollectionsFromServer() {
        try (Socket socket = new Socket(serverIP, serverPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // Enviar comando al servidor
            out.writeObject("GET_COLLECTIONS");
            out.flush();

            // Recibir lista de colecciones
            List<MahnCollections> collections = (List<MahnCollections>) in.readObject();
            return collections;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
