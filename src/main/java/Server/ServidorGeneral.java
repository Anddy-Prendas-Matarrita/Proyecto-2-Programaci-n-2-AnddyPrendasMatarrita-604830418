
package Server;

import java.io.*;
import java.net.*;
import java.util.List;

import java.io.IOException;
import proyecto2.proyecto2anddyprendasmatarrita.CollectionsManager;
import proyecto2.proyecto2anddyprendasmatarrita.CreditCardsManager;
import proyecto2.proyecto2anddyprendasmatarrita.MuseumsManager;
import proyecto2.proyecto2anddyprendasmatarrita.PricesManager;
import proyecto2.proyecto2anddyprendasmatarrita.RatingsManager;
import proyecto2.proyecto2anddyprendasmatarrita.RoomsManager;
import proyecto2.proyecto2anddyprendasmatarrita.SpeciesManager;
import proyecto2.proyecto2anddyprendasmatarrita.TopicsManager;


public class ServidorGeneral {

    private static final int PORT = 12345;

    private MuseumsManager museumsManager = new MuseumsManager();
    private RoomsManager roomsManager = new RoomsManager();
    private CollectionsManager collectionsManager = new CollectionsManager();
    private SpeciesManager speciesManager = new SpeciesManager();
    private TopicsManager topicsManager = new TopicsManager();
    private PricesManager pricesManager = new PricesManager();
    private CreditCardsManager creditCardsManager = new CreditCardsManager();
    private RatingsManager ratingsManager = new RatingsManager();

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("?Servidor general iniciado en el puerto " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());
                new Thread(() -> handleClient(clientSocket)).start();
            }

        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor general: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void handleClient(Socket socket) {
        try (
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            String command = (String) in.readObject();
            System.out.println("Comando recibido: " + command);

            switch (command.toUpperCase()) {
                case "GET_MUSEUMS":
                    out.writeObject(museumsManager.getAllMuseums());
                    break;

                case "GET_ROOMS":
                    out.writeObject(roomsManager.getAllRooms());
                    break;

                case "GET_COLLECTIONS":
                    out.writeObject(collectionsManager.getAllCollections());
                    break;

                case "GET_SPECIES":
                    out.writeObject(speciesManager.getAllSpecies());
                    break;

                case "GET_TOPICS":
                    out.writeObject(topicsManager.getAllTopics());
                    break;

                case "GET_PRICES":
                    out.writeObject(pricesManager.getAllPrices());
                    break;

                case "GET_CREDITCARDS":
                    out.writeObject(creditCardsManager.getAllCreditCards());
                    break;

                case "GET_RATINGS":
                    out.writeObject(ratingsManager.getAllRatings());
                    break;

                default:
                    out.writeObject("Comando no reconocido: " + command);
                    break;
            }

            out.flush();
            socket.close();
            System.out.println("Cliente desconectado correctamente.");

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Error manejando cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ServidorGeneral().startServer();
    }
}
