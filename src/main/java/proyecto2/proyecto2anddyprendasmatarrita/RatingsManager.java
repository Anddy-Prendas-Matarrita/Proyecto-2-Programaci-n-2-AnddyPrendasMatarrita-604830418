package proyecto2.proyecto2anddyprendasmatarrita;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RatingsManager {
        private EntityManagerFactory emf = Persistence.createEntityManagerFactory("user_management");

    public void addUser(MahnRatings ratings) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(ratings);
        em.getTransaction().commit();
        em.close();
    }

    public List<MahnRatings> getUsers() {
        EntityManager em = emf.createEntityManager();
        List<MahnRatings> ratings = em.createQuery("SELECT u FROM MahnRatings u", MahnRatings.class).getResultList();
        em.close();
        return ratings;
    }

    public void updateUser(MahnRatings ratings) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(ratings);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteUser(Long userId) {
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
