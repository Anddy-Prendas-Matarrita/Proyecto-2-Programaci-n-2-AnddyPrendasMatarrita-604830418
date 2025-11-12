package proyecto2.proyecto2anddyprendasmatarrita;

import java.io.*;
import java.net.*;
import java.util.List;

public class CreditCardsClient {

    private String serverIP = "localhost"; // o la IP del servidor
    private int serverPort = 12345;

    public List<MahnCreditCards> getCreditCardsFromServer() {
        try (Socket socket = new Socket(serverIP, serverPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // Enviar comando al servidor
            out.writeObject("GET_CREDITCARDS");
            out.flush();

            // Recibir lista de salas
            List<MahnCreditCards> creditCards = (List<MahnCreditCards>) in.readObject();
            return creditCards;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
