package proyecto2.proyecto2anddyprendasmatarrita;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class VisitorsManager {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("user_management");

    public void addVisitor(MahnVisitors visitor) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(visitor);
        em.getTransaction().commit();
        em.close();
    }

    public MahnVisitors findVisitorByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            // Asegúrate de tener un NamedQuery o crea la consulta directamente si no existe.
            // Por ejemplo, añade esto en MahnVisitors.java:
            // @NamedQuery(name = "MahnVisitors.findByEmail", query = "SELECT m FROM MahnVisitors m WHERE m.email = :email")
            TypedQuery<MahnVisitors> query = em.createNamedQuery("MahnVisitors.findByEmail", MahnVisitors.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public void updateVisitor(MahnVisitors visitor) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(visitor);
        em.getTransaction().commit();
        em.close();
    }

    public List<MahnVisitors> getVisitors() {
        EntityManager em = emf.createEntityManager();
        List<MahnVisitors> visitors = em.createQuery("SELECT v FROM MahnVisitors v", MahnVisitors.class).getResultList();
        em.close();
        return visitors;
    }
    
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}