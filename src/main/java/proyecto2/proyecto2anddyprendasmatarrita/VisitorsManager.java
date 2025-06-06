
package proyecto2.proyecto2anddyprendasmatarrita;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class VisitorsManager {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("user_management");

    public void addUser(MahnVisitors visitors) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(visitors);
        em.getTransaction().commit();
        em.close();
    }

    public List<MahnVisitors> getUsers() {
        EntityManager em = emf.createEntityManager();
        List<MahnVisitors> visitors = em.createQuery("SELECT u FROM MahnVisitors u", MahnVisitors.class).getResultList();
        em.close();
        return visitors;
    }

    public void updateUser(MahnVisitors visitors) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(visitors);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteUser(Long userId) {
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
