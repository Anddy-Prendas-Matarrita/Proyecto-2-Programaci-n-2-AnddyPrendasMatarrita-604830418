
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

    public void addVisitor(MahnVisitors visitors) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(visitors);
        em.getTransaction().commit();
        em.close();
    }

    public List<MahnVisitors> getVisitor() {
        EntityManager em = emf.createEntityManager();
        List<MahnVisitors> visitors = em.createQuery("SELECT u FROM MahnVisitors u", MahnVisitors.class).getResultList();
        em.close();
        return visitors;
    }
    public MahnVisitors findVisitorByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<MahnVisitors> query = em.createQuery("SELECT v FROM MahnVisitors v WHERE v.email = :email", MahnVisitors.class);
            query.setParameter("email", email);
            return query.getSingleResult(); 
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public void updateVisitor(MahnVisitors visitors) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(visitors);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteVisitor(BigDecimal userId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        MahnVisitors visitors = em.find(MahnVisitors.class, userId);
        if (visitors != null) {
            em.remove(visitors);
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
