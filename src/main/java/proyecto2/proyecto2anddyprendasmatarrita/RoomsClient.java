package proyecto2.proyecto2anddyprendasmatarrita;

import java.io.*;
import java.net.*;
import java.util.List;

public class RoomsClient {

    private String serverIP = "localhost"; // o la IP del servidor
    private int serverPort = 12345;

    public List<MahnRooms> getRoomsFromServer() {
        try (Socket socket = new Socket(serverIP, serverPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // Enviar comando al servidor
            out.writeObject("GET_ROOMS");
            out.flush();

            // Recibir lista de salas
            List<MahnRooms> rooms = (List<MahnRooms>) in.readObject();
            return rooms;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
