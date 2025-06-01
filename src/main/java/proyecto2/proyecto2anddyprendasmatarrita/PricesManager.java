
package proyecto2.proyecto2anddyprendasmatarrita;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PricesManager {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("user_management");

    public void addUser(MahnPrices prices) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(prices);
        em.getTransaction().commit();
        em.close();
    }

    public List<MahnPrices> getUsers() {
        EntityManager em = emf.createEntityManager();
        List<MahnPrices> prices = em.createQuery("SELECT u FROM MahnPrices u", MahnPrices.class).getResultList();
        em.close();
        return prices;
    }

    public void updateUser(MahnPrices prices) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(prices);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteUser(Long userId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        MahnPrices prices = em.find(MahnPrices.class, userId);
        if (prices != null) {
            em.remove(prices);
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
