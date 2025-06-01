package proyecto2.proyecto2anddyprendasmatarrita;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class CollectionsManager {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("user_management");

    public void addUser(MahnCollections collections) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(collections);
        em.getTransaction().commit();
        em.close();
    }

    public List<MahnCollections> getUsers() {
        EntityManager em = emf.createEntityManager();
        List<MahnCollections> collections = em.createQuery("SELECT u FROM MahnCollections u", MahnCollections.class).getResultList();
        em.close();
        return collections;
    }

    public void updateUser(MahnCollections collections) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(collections);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteUser(Long userId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        MahnCollections collections = em.find(MahnCollections.class, userId);
        if (collections != null) {
            em.remove(collections);
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