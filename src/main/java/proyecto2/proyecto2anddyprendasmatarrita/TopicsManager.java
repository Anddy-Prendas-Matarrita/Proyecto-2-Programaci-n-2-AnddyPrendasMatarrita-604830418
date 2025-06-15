
package proyecto2.proyecto2anddyprendasmatarrita;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

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
    public MahnRooms getRoomByName(String roomName) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<MahnRooms> query = em.createNamedQuery("MahnRooms.findByName", MahnRooms.class);
            query.setParameter("name", roomName);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    public List<MahnRooms> getAllRooms() {
        EntityManager em = emf.createEntityManager();
        List<MahnRooms> rooms = em.createQuery("SELECT r FROM MahnRooms r", MahnRooms.class).getResultList();
        em.close();
        return rooms;
    }
    public List<MahnTopics> getAllTopics() {
        EntityManager em = emf.createEntityManager();
        List<MahnTopics> topics = em.createQuery("SELECT t FROM MahnTopics t", MahnTopics.class).getResultList();
        em.close();
        return topics;
    }
    public List<MahnTopics> getTopicsFiltered(String filterType, String filterValue) {
        EntityManager em = emf.createEntityManager();
        List<MahnTopics> topics;
        try {
            String jpql;
            TypedQuery<MahnTopics> query;

            if (filterType == null || filterValue == null || filterValue.trim().isEmpty()) {
                jpql = "SELECT t FROM MahnTopics t";
                query = em.createQuery(jpql, MahnTopics.class);
            } else {
                switch (filterType.toLowerCase()) {
                    case "nombre":
                        jpql = "SELECT t FROM MahnTopics t WHERE LOWER(t.name) LIKE :filterValue";
                        query = em.createQuery(jpql, MahnTopics.class);
                        query.setParameter("filterValue", "%" + filterValue.toLowerCase() + "%");
                        break;
                    case "época":
                        jpql = "SELECT t FROM MahnTopics t WHERE LOWER(t.era) LIKE :filterValue";
                        query = em.createQuery(jpql, MahnTopics.class);
                        query.setParameter("filterValue", "%" + filterValue.toLowerCase() + "%");
                        break;
                    case "características":
                        jpql = "SELECT t FROM MahnTopics t WHERE LOWER(t.characteristics) LIKE :filterValue";
                        query = em.createQuery(jpql, MahnTopics.class);
                        query.setParameter("filterValue", "%" + filterValue.toLowerCase() + "%");
                        break;
                    case "sala": // Filtrar por nombre de la sala relacionada
                        jpql = "SELECT t FROM MahnTopics t WHERE LOWER(t.roomId.name) LIKE :filterValue";
                        query = em.createQuery(jpql, MahnTopics.class);
                        query.setParameter("filterValue", "%" + filterValue.toLowerCase() + "%");
                        break;
                    default:
                        jpql = "SELECT t FROM MahnTopics t";
                        query = em.createQuery(jpql, MahnTopics.class);
                        break;
                }
            }
            topics = query.getResultList();
        } finally {
            em.close();
        }
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
