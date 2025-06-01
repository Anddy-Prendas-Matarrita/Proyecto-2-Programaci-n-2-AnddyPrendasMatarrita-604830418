
package proyecto2.proyecto2anddyprendasmatarrita;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TicketsManager {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("user_management");

    public void addUser(MahnTickets tickets) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(tickets);
        em.getTransaction().commit();
        em.close();
    }

    public List<MahnTickets> getUsers() {
        EntityManager em = emf.createEntityManager();
        List<MahnTickets> tickets = em.createQuery("SELECT u FROM MahnTickets u", MahnTickets.class).getResultList();
        em.close();
        return tickets;
    }

    public void updateUser(MahnTickets tickets) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(tickets);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteUser(Long userId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        MahnTickets tickets = em.find(MahnTickets.class, userId);
        if (tickets != null) {
            em.remove(tickets);
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
