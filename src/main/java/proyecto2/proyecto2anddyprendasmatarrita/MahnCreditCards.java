package proyecto2.proyecto2anddyprendasmatarrita;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Anddy Prendas
 */
@Entity
@Table(name = "MAHN_CREDIT_CARDS")
@NamedQueries({
    @NamedQuery(name = "MahnCreditCards.findAll", query = "SELECT m FROM MahnCreditCards m"),
    @NamedQuery(name = "MahnCreditCards.findByCardId", query = "SELECT m FROM MahnCreditCards m WHERE m.cardId = :cardId"),
    @NamedQuery(name = "MahnCreditCards.findByType", query = "SELECT m FROM MahnCreditCards m WHERE m.type = :type"),
    @NamedQuery(name = "MahnCreditCards.findByCommissionRate", query = "SELECT m FROM MahnCreditCards m WHERE m.commissionRate = :commissionRate")})
public class MahnCreditCards implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CARD_ID")
    private BigDecimal cardId;
    @Basic(optional = false)
    @Column(name = "TYPE")
    private String type;
    @Basic(optional = false)
    @Column(name = "COMMISSION_RATE")
    private BigDecimal commissionRate;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cardId", fetch = FetchType.LAZY)
    private Collection<MahnTickets> mahnTicketsCollection;

    public MahnCreditCards() {
    }

    public MahnCreditCards(BigDecimal cardId) {
        this.cardId = cardId;
    }

    public MahnCreditCards(BigDecimal cardId, String type, BigDecimal commissionRate) {
        this.cardId = cardId;
        this.type = type;
        this.commissionRate = commissionRate;
    }

    public BigDecimal getCardId() {
        return cardId;
    }

    public void setCardId(BigDecimal cardId) {
        this.cardId = cardId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(BigDecimal commissionRate) {
        this.commissionRate = commissionRate;
    }

    public Collection<MahnTickets> getMahnTicketsCollection() {
        return mahnTicketsCollection;
    }

    public void setMahnTicketsCollection(Collection<MahnTickets> mahnTicketsCollection) {
        this.mahnTicketsCollection = mahnTicketsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cardId != null ? cardId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MahnCreditCards)) {
            return false;
        }
        MahnCreditCards other = (MahnCreditCards) object;
        if ((this.cardId == null && other.cardId != null) || (this.cardId != null && !this.cardId.equals(other.cardId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return type;
    }
}