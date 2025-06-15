
package proyecto2.proyecto2anddyprendasmatarrita;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class PricesManager {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("user_management");

    public void addPrice(MahnPrices prices) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(prices);
        em.getTransaction().commit();
        em.close();
    }

    public List<MahnPrices> getPrice() {
        EntityManager em = emf.createEntityManager();
        List<MahnPrices> prices = em.createQuery("SELECT u FROM MahnPrices u", MahnPrices.class).getResultList();
        em.close();
        return prices;
    }
    public List<MahnPrices> getAllPrices() {
        EntityManager em = emf.createEntityManager();
        List<MahnPrices> prices = em.createQuery("SELECT p FROM MahnPrices p", MahnPrices.class).getResultList();
        em.close();
        return prices;
    }

    public void updatePrice(MahnPrices prices) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(prices);
        em.getTransaction().commit();
        em.close();
    }

    public void deletePrice(BigDecimal userId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        MahnPrices prices = em.find(MahnPrices.class, userId);
        if (prices != null) {
            em.remove(prices);
        }
        em.getTransaction().commit();
        em.close();
    }

    public List<MahnPrices> filterPricesByRoomName(String roomName) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<MahnPrices> query = em.createQuery(
                "SELECT p FROM MahnPrices p WHERE p.roomId.roomName LIKE :roomName", MahnPrices.class);
            query.setParameter("roomName", "%" + roomName + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    public List<MahnPrices> filterPricesByRoom(MahnRooms room) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<MahnPrices> query = em.createQuery(
                "SELECT p FROM MahnPrices p WHERE p.roomId = :room", MahnPrices.class);
            query.setParameter("room", room);
            return query.getResultList();
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
