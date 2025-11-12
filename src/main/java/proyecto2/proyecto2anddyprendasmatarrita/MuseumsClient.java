package proyecto2.proyecto2anddyprendasmatarrita;

import java.io.*;
import java.net.*;
import java.util.List;

public class MuseumsClient {

    private String serverIP = "localhost"; // o la IP del servidor
    private int serverPort = 12345;

    public List<MahnMuseums> getMuseumsFromServer() {
        try (Socket socket = new Socket(serverIP, serverPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // Enviar comando al servidor
            out.writeObject("GET_MUSEUMS");
            out.flush();

            // Recibir lista de museos
            List<MahnMuseums> museums = (List<MahnMuseums>) in.readObject();
            return museums;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
