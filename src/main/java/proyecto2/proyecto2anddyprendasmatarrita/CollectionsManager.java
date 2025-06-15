package proyecto2.proyecto2anddyprendasmatarrita;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class CollectionsManager {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("user_management");

    public void addCollection(MahnCollections collection) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(collection);
        em.getTransaction().commit();
        em.close();
    }

    public List<MahnCollections> getAllCollections() {// permite obtener todas las colecciones
        EntityManager em = emf.createEntityManager();
        List<MahnCollections> collections = em.createQuery("SELECT c FROM MahnCollections c", MahnCollections.class).getResultList();
        em.close();
        return collections;
    }

    public void updateCollection(MahnCollections collection) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(collection);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteCollection(BigDecimal collectionId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        MahnCollections collection = em.find(MahnCollections.class, collectionId);
        if (collection != null) {
            em.remove(collection);
        }
        em.getTransaction().commit();
        em.close();
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
    public List<MahnRooms> getAllRooms() {// permite obtener todas las salas
        EntityManager em = emf.createEntityManager();
        List<MahnRooms> rooms = em.createQuery("SELECT r FROM MahnRooms r", MahnRooms.class).getResultList();
        em.close();
        return rooms;
    }
    public List<MahnCollections> getCollectionsFiltered(String filterType, String filterValue) {// permite obtener las colleciones filtradas
        EntityManager em = emf.createEntityManager();
        List<MahnCollections> collections;
        try {
            String jpql;
            TypedQuery<MahnCollections> query;

            if (filterType == null || filterValue == null || filterValue.trim().isEmpty()) {
                jpql = "SELECT c FROM MahnCollections c";
                query = em.createQuery(jpql, MahnCollections.class);
            } else {
                switch (filterType.toLowerCase()) {
                    case "nombre": //filtra por nombre
                        jpql = "SELECT c FROM MahnCollections c WHERE LOWER(c.name) LIKE :filterValue";
                        query = em.createQuery(jpql, MahnCollections.class);
                        query.setParameter("filterValue", "%" + filterValue.toLowerCase() + "%");
                        break;
                    case "descripción": //filtra por descripcion
                        jpql = "SELECT c FROM MahnCollections c WHERE LOWER(c.description) LIKE :filterValue";
                        query = em.createQuery(jpql, MahnCollections.class);
                        query.setParameter("filterValue", "%" + filterValue.toLowerCase() + "%");
                        break;
                    case "siglo": //filtra por siglo
                        jpql = "SELECT c FROM MahnCollections c WHERE LOWER(c.century) LIKE :filterValue";
                        query = em.createQuery(jpql, MahnCollections.class);
                        query.setParameter("filterValue", "%" + filterValue.toLowerCase() + "%");
                        break;
                    case "sala"://filtra por nombre de sala
                        jpql = "SELECT c FROM MahnCollections c WHERE LOWER(c.roomId.name) LIKE :filterValue";
                        query = em.createQuery(jpql, MahnCollections.class);
                        query.setParameter("filterValue", "%" + filterValue.toLowerCase() + "%");
                        break;
                    default:
                        jpql = "SELECT c FROM MahnCollections c";
                        query = em.createQuery(jpql, MahnCollections.class);
                        break;
                }
            }
            collections = query.getResultList();
        } finally {
            em.close();
        }
        return collections;
    }

    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}