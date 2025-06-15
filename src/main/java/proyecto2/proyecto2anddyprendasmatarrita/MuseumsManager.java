package proyecto2.proyecto2anddyprendasmatarrita;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class MuseumsManager {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("user_management");

    public void addMuseum(MahnMuseums museum) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(museum);
        em.getTransaction().commit();
        em.close();
    }

    public List<MahnMuseums> getAllMuseums() {
        EntityManager em = emf.createEntityManager();
        List<MahnMuseums> museums = em.createQuery("SELECT m FROM MahnMuseums m", MahnMuseums.class).getResultList();
        em.close();
        return museums;
    }
    
    public List<MahnMuseums> getMuseumsFiltered(String filterType, String filterValue) {//para filtrar
        EntityManager em = emf.createEntityManager();
        List<MahnMuseums> museums;
        try {
            String jpql;
            TypedQuery<MahnMuseums> query;

            if (filterType == null || filterValue == null || filterValue.trim().isEmpty()) {
                jpql = "SELECT m FROM MahnMuseums m";
                query = em.createQuery(jpql, MahnMuseums.class);
            } else {
                switch (filterType.toLowerCase()) {
                    case "nombre":
                        jpql = "SELECT m FROM MahnMuseums m WHERE LOWER(m.name) LIKE :filterValue";
                        query = em.createQuery(jpql, MahnMuseums.class);
                        query.setParameter("filterValue", "%" + filterValue.toLowerCase() + "%");
                        break;
                    case "tipo":
                        jpql = "SELECT m FROM MahnMuseums m WHERE LOWER(m.museumType) LIKE :filterValue";
                        query = em.createQuery(jpql, MahnMuseums.class);
                        query.setParameter("filterValue", "%" + filterValue.toLowerCase() + "%");
                        break;
                    default:
                        jpql = "SELECT m FROM MahnMuseums m";
                        query = em.createQuery(jpql, MahnMuseums.class);
                        break;
                }
            }
            museums = query.getResultList();
        } finally {
            em.close();
        }
        return museums;
    }


    public void updateMuseum(MahnMuseums museum) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(museum);
        em.getTransaction().commit();
        em.close();
    }
    public MahnMuseums getMuseumById(BigDecimal museumId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(MahnMuseums.class, museumId);
        } finally {
            em.close();
        }
    }

    public MahnMuseums getMuseumByName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<MahnMuseums> query = em.createQuery("SELECT m FROM MahnMuseums m WHERE m.name = :name", MahnMuseums.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public void deleteMuseum(BigDecimal museumId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        MahnMuseums museum = em.find(MahnMuseums.class, museumId);
        if (museum != null) {
            em.remove(museum);
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