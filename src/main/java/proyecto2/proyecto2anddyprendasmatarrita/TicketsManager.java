package proyecto2.proyecto2anddyprendasmatarrita;

import java.math.BigDecimal; // Asegúrate de importar BigDecimal
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class TicketsManager {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("user_management");

    public void addTickets(MahnTickets tickets) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(tickets);
        em.getTransaction().commit();
        em.close();
    }

    public List<MahnTickets> getTickets() {
        EntityManager em = emf.createEntityManager();
        List<MahnTickets> tickets = em.createQuery("SELECT t FROM MahnTickets t", MahnTickets.class).getResultList();
        em.close();
        return tickets;
    }

     public MahnTickets getTicketByQrCode(String qrCode) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<MahnTickets> query = em.createQuery("SELECT t FROM MahnTickets t WHERE t.qrCode = :qrCode", MahnTickets.class);
            query.setParameter("qrCode", qrCode);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    public MahnTickets getTicketById(BigDecimal ticketId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<MahnTickets> query = em.createQuery("SELECT t FROM MahnTickets t WHERE t.ticketId = :ticketId", MahnTickets.class);
            query.setParameter("ticketId", ticketId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}