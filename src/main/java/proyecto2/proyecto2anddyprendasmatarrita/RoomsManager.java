package proyecto2.proyecto2anddyprendasmatarrita;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class RoomsManager {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("user_management");

    public void addRoom(MahnRooms room) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(room);
        em.getTransaction().commit();
        em.close();
    }

    public List<MahnRooms> getAllRooms() { 
        EntityManager em = emf.createEntityManager();
        List<MahnRooms> rooms = em.createQuery("SELECT r FROM MahnRooms r", MahnRooms.class).getResultList();
        em.close();
        return rooms;
    }
    public List<MahnRooms> getRoomsByMuseum(BigDecimal museumId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<MahnRooms> query = em.createQuery("SELECT r FROM MahnRooms r WHERE r.museumId.museumId = :museumId", MahnRooms.class);
            query.setParameter("museumId", museumId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
   
    public List<MahnMuseums> getAllMuseums() {
        EntityManager em = emf.createEntityManager();
        List<MahnMuseums> museums = em.createQuery("SELECT m FROM MahnMuseums m", MahnMuseums.class).getResultList();
        em.close();
        return museums;
    }
    
    public List<MahnRooms> getRoomsFiltered(String filterType, String filterValue) {
        EntityManager em = emf.createEntityManager();
        List<MahnRooms> rooms;
        try {
            String jpql;
            TypedQuery<MahnRooms> query;

            if (filterType == null || filterValue == null || filterValue.trim().isEmpty()) {
                jpql = "SELECT r FROM MahnRooms r";
                query = em.createQuery(jpql, MahnRooms.class);
            } else {
                switch (filterType.toLowerCase()) {
                    case "nombre":
                        jpql = "SELECT r FROM MahnRooms r WHERE LOWER(r.name) LIKE :filterValue";
                        query = em.createQuery(jpql, MahnRooms.class);
                        query.setParameter("filterValue", "%" + filterValue.toLowerCase() + "%");
                        break;
                    case "descripción":
                        jpql = "SELECT r FROM MahnRooms r WHERE LOWER(r.description) LIKE :filterValue";
                        query = em.createQuery(jpql, MahnRooms.class);
                        query.setParameter("filterValue", "%" + filterValue.toLowerCase() + "%");
                        break;
                    case "temática":
                        jpql = "SELECT r FROM MahnRooms r WHERE LOWER(r.mainTheme) LIKE :filterValue";
                        query = em.createQuery(jpql, MahnRooms.class);
                        query.setParameter("filterValue", "%" + filterValue.toLowerCase() + "%");
                        break;
                    default:
                        jpql = "SELECT r FROM MahnRooms r";
                        query = em.createQuery(jpql, MahnRooms.class);
                        break;
                }
            }
            rooms = query.getResultList();
        } finally {
            em.close();
        }
        return rooms;
    }


    public void updateRoom(MahnRooms room) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(room);
        em.getTransaction().commit();
        em.close();
    }

   public void deleteRoom(BigDecimal roomId) {
    EntityManager em = emf.createEntityManager();
    try {
        em.getTransaction().begin();

        MahnRooms room = em.find(MahnRooms.class, roomId);
        if (room != null) {
            em.remove(em.contains(room) ? room : em.merge(room));
        }

        em.getTransaction().commit();
    } catch (Exception e) {
        if (em.getTransaction().isActive()) em.getTransaction().rollback();
        e.printStackTrace();
    } finally {
        em.close();
    }
}


    public MahnRooms getRoomByName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<MahnRooms> query = em.createNamedQuery("MahnRooms.findByName", MahnRooms.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null; //devuelve nulo si no encuentra sala
        } finally {
            em.close();
        }
    }
    
     public MahnRooms getRoomById(BigDecimal roomId) {
        EntityManager em = emf.createEntityManager();
        try {
            MahnRooms room = em.find(MahnRooms.class, roomId);
            return room;
        } finally {
            em.close();
        }
    }
      public MahnMuseums getMuseumByName(String museumName) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<MahnMuseums> query = em.createNamedQuery("MahnMuseums.findByName", MahnMuseums.class);
            query.setParameter("name", museumName);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; 
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