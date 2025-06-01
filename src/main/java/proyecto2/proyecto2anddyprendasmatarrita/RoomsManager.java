package proyecto2.proyecto2anddyprendasmatarrita;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RoomsManager {
   private EntityManagerFactory emf = Persistence.createEntityManagerFactory("user_management");

    public void addUser(MahnRooms rooms) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(rooms);
        em.getTransaction().commit();
        em.close();
    }

    public List<MahnRooms> getUsers() {
        EntityManager em = emf.createEntityManager();
        List<MahnRooms> rooms = em.createQuery("SELECT u FROM MahnRooms u", MahnRooms.class).getResultList();
        em.close();
        return rooms;
    }

    public void updateUser(MahnRooms rooms) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(rooms);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteUser(Long userId) {
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
