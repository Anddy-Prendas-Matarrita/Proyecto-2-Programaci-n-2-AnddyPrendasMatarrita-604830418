package proyecto2.proyecto2anddyprendasmatarrita;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class CreditCardsManager {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("user_management");

    public void addCreditCard(MahnCreditCards creditCard) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(creditCard);
        em.getTransaction().commit();
        em.close();
    }
    public MahnCreditCards getCreditCardByType(String type) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<MahnCreditCards> query = em.createNamedQuery("MahnCreditCards.findByType", MahnCreditCards.class);
            query.setParameter("type", type);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<MahnCreditCards> getAllCreditCards() {
        EntityManager em = emf.createEntityManager();
        List<MahnCreditCards> creditCards = em.createQuery("SELECT c FROM MahnCreditCards c", MahnCreditCards.class).getResultList();
        em.close();
        return creditCards;
    }

    public void updateCreditCard(MahnCreditCards creditCard) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(creditCard);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteCreditCard(BigDecimal cardId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        MahnCreditCards creditCard = em.find(MahnCreditCards.class, cardId);
        if (creditCard != null) {
            em.remove(creditCard);
        }
        em.getTransaction().commit();
        em.close();
    }
    
    public List<MahnCreditCards> getCreditCardsFiltered(String filterType, String filterValue) {
        EntityManager em = emf.createEntityManager();
        List<MahnCreditCards> creditCards;
        try {
            String jpql;
            TypedQuery<MahnCreditCards> query;

            if (filterType == null || filterValue == null || filterValue.trim().isEmpty()) {
                jpql = "SELECT c FROM MahnCreditCards c";
                query = em.createQuery(jpql, MahnCreditCards.class);
            } else {
                switch (filterType.toLowerCase()) {
                    case "tipo de tarjeta":
                        jpql = "SELECT c FROM MahnCreditCards c WHERE LOWER(c.type) LIKE :filterValue";
                        query = em.createQuery(jpql, MahnCreditCards.class);
                        query.setParameter("filterValue", "%" + filterValue.toLowerCase() + "%");
                        break;
                    case "comisión cobrada":
                        try {
                            BigDecimal rate = new BigDecimal(filterValue);
                            jpql = "SELECT c FROM MahnCreditCards c WHERE c.commissionRate = :filterValue";
                            query = em.createQuery(jpql, MahnCreditCards.class);
                            query.setParameter("filterValue", rate);
                        } catch (NumberFormatException e) {
                            System.err.println("Valor de comisión inválido: " + filterValue);
                            return getAllCreditCards();
                        }
                        break;
                    default:
                        jpql = "SELECT c FROM MahnCreditCards c";
                        query = em.createQuery(jpql, MahnCreditCards.class);
                        break;
                }
            }
            creditCards = query.getResultList();
        } finally {
            em.close();
        }
        return creditCards;
    }

    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}