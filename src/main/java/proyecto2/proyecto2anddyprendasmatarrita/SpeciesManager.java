
package proyecto2.proyecto2anddyprendasmatarrita;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SpeciesManager {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("user_management");

    public void addUser(MahnSpecies species) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(species);
        em.getTransaction().commit();
        em.close();
    }

    public List<MahnSpecies> getUsers() {
        EntityManager em = emf.createEntityManager();
        List<MahnSpecies> species = em.createQuery("SELECT u FROM MahnSpecies u", MahnSpecies.class).getResultList();
        em.close();
        return species;
    }

    public void updateUser(MahnSpecies species) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(species);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteUser(Long userId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        MahnSpecies species = em.find(MahnSpecies.class, userId);
        if (species != null) {
            em.remove(species);
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
