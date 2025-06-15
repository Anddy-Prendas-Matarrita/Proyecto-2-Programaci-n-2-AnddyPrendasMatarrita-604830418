package proyecto2.proyecto2anddyprendasmatarrita;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RatingsManager {
        private EntityManagerFactory emf = Persistence.createEntityManagerFactory("user_management");

    public void addRating(MahnRatings ratings) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(ratings);
        em.getTransaction().commit();
        em.close();
    }

    public List<MahnRatings> getRating() {
        EntityManager em = emf.createEntityManager();
        List<MahnRatings> ratings = em.createQuery("SELECT u FROM MahnRatings u", MahnRatings.class).getResultList();
        em.close();
        return ratings;
    }

    public void updateRating(MahnRatings ratings) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(ratings);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteRating(BigDecimal userId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        MahnRatings ratings = em.find(MahnRatings.class, userId);
        if (ratings != null) {
            em.remove(ratings);
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
