package proyecto2.proyecto2anddyprendasmatarrita;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class RoomsManager {
   private EntityManagerFactory emf = Persistence.createEntityManagerFactory("user_management");

    public void addRoom(MahnRooms rooms) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(rooms);
        em.getTransaction().commit();
        em.close();
    }

    public List<MahnRooms> getRoom() {
        EntityManager em = emf.createEntityManager();
        List<MahnRooms> rooms = em.createQuery("SELECT u FROM MahnRooms u", MahnRooms.class).getResultList();
        em.close();
        return rooms;
    }
    public MahnRooms getRoomByName(String roomName) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<MahnRooms> query = em.createNamedQuery("MahnRooms.findByRoomName", MahnRooms.class);
            query.setParameter("roomName", roomName);
            return query.getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null; // No se encontró la sala
        } finally {
            em.close();
        }
    }
    
     public MahnRooms getRoomById(BigDecimal roomId) {
        EntityManager em = emf.createEntityManager();
        MahnRooms room = em.find(MahnRooms.class, roomId);
        em.close();
        return room;
    }
     public List<MahnRooms> getAllRooms() {
        EntityManager em = emf.createEntityManager();
        List<MahnRooms> rooms = em.createQuery("SELECT r FROM MahnRooms r", MahnRooms.class).getResultList();
        em.close();
        return rooms;
    }

    public void updateRoom(MahnRooms rooms) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(rooms);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteRoom(BigDecimal userId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        MahnRooms rooms = em.find(MahnRooms.class, userId);
        if (rooms != null) {
            em.remove(rooms);
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
