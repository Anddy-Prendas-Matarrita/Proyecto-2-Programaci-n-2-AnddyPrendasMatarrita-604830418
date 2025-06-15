/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2.proyecto2anddyprendasmatarrita;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

/**
 *
 * @author Anddy Prendas
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "MAHN_CREDIT_CARDS")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "MahnCreditCards.findAll", query = "SELECT m FROM MahnCreditCards m"),
    @javax.persistence.NamedQuery(name = "MahnCreditCards.findByCardId", query = "SELECT m FROM MahnCreditCards m WHERE m.cardId = :cardId"),
    @javax.persistence.NamedQuery(name = "MahnCreditCards.findByType", query = "SELECT m FROM MahnCreditCards m WHERE m.type = :type"),
    @javax.persistence.NamedQuery(name = "MahnCreditCards.findByCommissionRate", query = "SELECT m FROM MahnCreditCards m WHERE m.commissionRate = :commissionRate")})
public class MahnCreditCards implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "CARD_ID")
    private BigDecimal cardId;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "TYPE")
    private String type;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "COMMISSION_RATE")
    private BigDecimal commissionRate;
    @javax.persistence.OneToMany(cascade = javax.persistence.CascadeType.ALL, mappedBy = "cardId", fetch = javax.persistence.FetchType.EAGER)
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
        // TODO: Warning - this method won't work in the case the id fields are not set
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
        return "proyecto2.proyecto2anddyprendasmatarrita.MahnCreditCards[ cardId=" + cardId + " ]";
    }
    
}
