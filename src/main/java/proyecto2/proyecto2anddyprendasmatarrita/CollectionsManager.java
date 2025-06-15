package proyecto2.proyecto2anddyprendasmatarrita;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import javax.persistence.TypedQuery;

public class CollectionsManager {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("user_management");

    public void addPrice(MahnCollections collections) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(collections);
        em.getTransaction().commit();
        em.close();
    }

    public List<MahnCollections> getPrice() {
        EntityManager em = emf.createEntityManager();
        List<MahnCollections> collections = em.createQuery("SELECT u FROM MahnCollections u", MahnCollections.class).getResultList();
        em.close();
        return collections;
    }

    public void updatePrice(MahnCollections collections) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(collections);
        em.getTransaction().commit();
        em.close();
    }

    public void deletePrice(BigDecimal userId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        MahnCollections collections = em.find(MahnCollections.class, userId);
        if (collections != null) {
            em.remove(collections);
        }
        em.getTransaction().commit();
        em.close();
    }
    public List<MahnPrices> filterPricesByRoomName(String roomName) { //Esta funci[on es para buscar en filtro 1, el precio pero por el nombre de la sala a la que pertenece
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