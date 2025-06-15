package proyecto2.proyecto2anddyprendasmatarrita;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public Map<String, Double> getTotalCommissionsByCardType(Date startDate, Date endDate) {// entre las fechas dadas por el usuario, busca las ventas hecha,
        EntityManager em = emf.createEntityManager();                                        // y devuelve el total de comisiones a pagar en ese lapso
        Map<String, Double> commissions = new HashMap<>();
        try {
            String jpql = "SELECT cc.type, SUM(mt.commissionAmount) " +
                          "FROM MahnTickets mt JOIN mt.cardId cc " + 
                          "WHERE mt.purchaseDate BETWEEN :startDate AND :endDate " +
                          "GROUP BY cc.type";

            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);

            List<Object[]> results = query.getResultList();

            for (Object[] result : results) {
                String cardName = (String) result[0];
                Double totalCommission = ((BigDecimal) result[1]).doubleValue(); 
                commissions.put(cardName, totalCommission);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener comisiones por tipo de tarjeta desde la base de datos: " + e.getMessage(), e);
        } finally {
            em.close();
        }
        return commissions;
    }
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}