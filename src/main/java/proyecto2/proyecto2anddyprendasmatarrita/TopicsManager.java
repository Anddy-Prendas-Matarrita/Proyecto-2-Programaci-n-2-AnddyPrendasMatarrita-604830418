
package proyecto2.proyecto2anddyprendasmatarrita;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TopicsManager {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("user_management");

    public void addTopic(MahnTopics topics) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(topics);
        em.getTransaction().commit();
        em.close();
    }

    public List<MahnTopics> getTopic() {
        EntityManager em = emf.createEntityManager();
        List<MahnTopics> topics = em.createQuery("SELECT u FROM MahnTopics u", MahnTopics.class).getResultList();
        em.close();
        return topics;
    }
public List<MahnTopics> getAllTopics() { 
        EntityManager em = emf.createEntityManager();
        List<MahnTopics> topics = em.createQuery("SELECT s FROM MahnTopics s", MahnTopics.class).getResultList();
        em.close();
        return topics;
    }
 
    public void updateTopic(MahnTopics topics) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(topics);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteTopic(BigDecimal userId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        MahnTopics topics = em.find(MahnTopics.class, userId);
        if (topics != null) {
            em.remove(topics);
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
