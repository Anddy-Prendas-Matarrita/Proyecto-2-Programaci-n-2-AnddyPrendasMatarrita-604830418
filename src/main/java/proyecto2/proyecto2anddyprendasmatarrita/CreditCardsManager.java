package proyecto2.proyecto2anddyprendasmatarrita;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CreditCardsManager {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("user_management");

    public void addCreditCard(MahnCreditCards creditCards) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(creditCards);
        em.getTransaction().commit();
        em.close();
    }

    public List<MahnCreditCards> getUsers() {
        EntityManager em = emf.createEntityManager();
        List<MahnCreditCards> creditCards = em.createQuery("SELECT u FROM MahnCreditCards u", MahnCreditCards.class).getResultList();
        em.close();
        return creditCards;
    }

    public void updateCreditCard(MahnCreditCards creditCards) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(creditCards);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteCreditCard(BigDecimal userId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        MahnCreditCards creditCards = em.find(MahnCreditCards.class, userId);
        if (creditCards != null) {
            em.remove(creditCards);
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
