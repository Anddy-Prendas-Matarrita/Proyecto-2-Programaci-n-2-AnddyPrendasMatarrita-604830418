package proyecto2.proyecto2anddyprendasmatarrita;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class SpeciesManager {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("user_management");

    public void addSpecie(MahnSpecies species) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(species);
        em.getTransaction().commit();
        em.close();
    }

    public List<MahnSpecies> getAllSpecies() {
        EntityManager em = emf.createEntityManager();
        List<MahnSpecies> species = em.createQuery("SELECT s FROM MahnSpecies s", MahnSpecies.class).getResultList();
        em.close();
        return species;
    }

    public void updateSpecie(MahnSpecies species) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(species);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteSpecie(BigDecimal speciesId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        MahnSpecies species = em.find(MahnSpecies.class, speciesId);
        if (species != null) {
            em.remove(species);
        }
        em.getTransaction().commit();
        em.close();
    }
    
    public List<MahnSpecies> getSpeciesFiltered(String filterType, String filterValue) {
        EntityManager em = emf.createEntityManager();
        List<MahnSpecies> speciesList;
        try {
            String jpql;
            TypedQuery<MahnSpecies> query;

            if (filterType == null || filterValue == null || filterValue.trim().isEmpty()) {
                jpql = "SELECT s FROM MahnSpecies s";
                query = em.createQuery(jpql, MahnSpecies.class);
            } else {
                switch (filterType.toLowerCase()) {
                    case "nombre científico":
                        jpql = "SELECT s FROM MahnSpecies s WHERE LOWER(s.scientificName) LIKE :filterValue";
                        query = em.createQuery(jpql, MahnSpecies.class);
                        query.setParameter("filterValue", "%" + filterValue.toLowerCase() + "%");
                        break;
                    case "nombre común":
                        jpql = "SELECT s FROM MahnSpecies s WHERE LOWER(s.commonName) LIKE :filterValue";
                        query = em.createQuery(jpql, MahnSpecies.class);
                        query.setParameter("filterValue", "%" + filterValue.toLowerCase() + "%");
                        break;
                    case "época":
                        jpql = "SELECT s FROM MahnSpecies s WHERE LOWER(s.era) LIKE :filterValue";
                        query = em.createQuery(jpql, MahnSpecies.class);
                        query.setParameter("filterValue", "%" + filterValue.toLowerCase() + "%");
                        break;
                    case "características":
                        jpql = "SELECT s FROM MahnSpecies s WHERE LOWER(s.characteristics) LIKE :filterValue";
                        query = em.createQuery(jpql, MahnSpecies.class);
                        query.setParameter("filterValue", "%" + filterValue.toLowerCase() + "%");
                        break;
                    default:
                        jpql = "SELECT s FROM MahnSpecies s";
                        query = em.createQuery(jpql, MahnSpecies.class);
                        break;
                }
            }
            speciesList = query.getResultList();
        } finally {
            em.close();
        }
        return speciesList;
    }
    public List<MahnCollections> getAllCollections() {
        EntityManager em = emf.createEntityManager();
        List<MahnCollections> collections = em.createQuery("SELECT c FROM MahnCollections c", MahnCollections.class).getResultList();
        em.close();
        return collections;
    }

    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}