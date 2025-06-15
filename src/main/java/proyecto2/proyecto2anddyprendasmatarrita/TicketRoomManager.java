package proyecto2.proyecto2anddyprendasmatarrita;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TicketRoomManager {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("user_management");

    public void addTicketRoom(MahnTicketRoom ticketRoom) { // Renombrado a addTicketRoom
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(ticketRoom);
        em.getTransaction().commit();
        em.close();
    }

    public List<MahnTicketRoom> getTicketRooms() { // Renombrado a getTicketRooms
        EntityManager em = emf.createEntityManager();
        List<MahnTicketRoom> ticketRoom = em.createQuery("SELECT u FROM MahnTicketRoom u", MahnTicketRoom.class).getResultList();
        em.close();
        return ticketRoom;
    }

    public void updateTicketRoom(MahnTicketRoom ticketRoom) { // Renombrado a updateTicketRoom
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(ticketRoom);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteTicketRoom(BigDecimal ticketRoomId) { // Corregido el parámetro a ticketRoomId y tipo
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        MahnTicketRoom ticketRoom = em.find(MahnTicketRoom.class, ticketRoomId);
        if (ticketRoom != null) {
            em.remove(ticketRoom);
        }
        em.getTransaction().commit();
        em.close();
    }

    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }    
}