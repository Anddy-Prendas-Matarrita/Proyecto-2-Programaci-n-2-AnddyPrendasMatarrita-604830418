package proyecto2.proyecto2anddyprendasmatarrita;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MuseumsManager {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("user_management");

    public void addUser(MahnMuseums museums) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(museums);
        em.getTransaction().commit();
        em.close();
    }

    public List<MahnMuseums> getUsers() {
        EntityManager em = emf.createEntityManager();
        List<MahnMuseums> museums = em.createQuery("SELECT u FROM MahnMuseums u", MahnMuseums.class).getResultList();
        em.close();
        return museums;
    }

    public void updateUser(MahnMuseums museums) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(museums);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteUser(Long userId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        MahnMuseums museums = em.find(MahnMuseums.class, userId);
        if (museums != null) {
            em.remove(museums);
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
