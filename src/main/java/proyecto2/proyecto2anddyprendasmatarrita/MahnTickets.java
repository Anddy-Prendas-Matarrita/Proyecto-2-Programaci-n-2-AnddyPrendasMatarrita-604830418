/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2.proyecto2anddyprendasmatarrita;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author Anddy Prendas
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "MAHN_TICKETS")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "MahnTickets.findAll", query = "SELECT m FROM MahnTickets m"),
    @javax.persistence.NamedQuery(name = "MahnTickets.findByTicketId", query = "SELECT m FROM MahnTickets m WHERE m.ticketId = :ticketId"),
    @javax.persistence.NamedQuery(name = "MahnTickets.findByPurchaseDate", query = "SELECT m FROM MahnTickets m WHERE m.purchaseDate = :purchaseDate"),
    @javax.persistence.NamedQuery(name = "MahnTickets.findByTotalAmount", query = "SELECT m FROM MahnTickets m WHERE m.totalAmount = :totalAmount"),
    @javax.persistence.NamedQuery(name = "MahnTickets.findByCommissionAmount", query = "SELECT m FROM MahnTickets m WHERE m.commissionAmount = :commissionAmount"),
    @javax.persistence.NamedQuery(name = "MahnTickets.findByQrCode", query = "SELECT m FROM MahnTickets m WHERE m.qrCode = :qrCode")})
public class MahnTickets implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "TICKET_ID")
    private BigDecimal ticketId;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "PURCHASE_DATE")
    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date purchaseDate;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "TOTAL_AMOUNT")
    private BigDecimal totalAmount;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "COMMISSION_AMOUNT")
    private BigDecimal commissionAmount;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "QR_CODE")
    private String qrCode;
    @javax.persistence.JoinColumn(name = "CARD_ID", referencedColumnName = "CARD_ID")
    @javax.persistence.ManyToOne(optional = false, fetch = javax.persistence.FetchType.EAGER)
    private MahnCreditCards cardId;
    @javax.persistence.JoinColumn(name = "VISITOR_ID", referencedColumnName = "VISITOR_ID")
    @javax.persistence.ManyToOne(optional = false, fetch = javax.persistence.FetchType.EAGER)
    private MahnVisitors visitorId;
    @javax.persistence.OneToMany(cascade = javax.persistence.CascadeType.ALL, mappedBy = "ticketId", fetch = javax.persistence.FetchType.EAGER)
    private Collection<MahnTicketRoom> mahnTicketRoomCollection;

    public MahnTickets() {
    }

    public MahnTickets(BigDecimal ticketId) {
        this.ticketId = ticketId;
    }

    public MahnTickets(BigDecimal ticketId, Date purchaseDate, BigDecimal totalAmount, BigDecimal commissionAmount, String qrCode) {
        this.ticketId = ticketId;
        this.purchaseDate = purchaseDate;
        this.totalAmount = totalAmount;
        this.commissionAmount = commissionAmount;
        this.qrCode = qrCode;
    }

    public BigDecimal getTicketId() {
        return ticketId;
    }

    public void setTicketId(BigDecimal ticketId) {
        this.ticketId = ticketId;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(BigDecimal commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public MahnCreditCards getCardId() {
        return cardId;
    }

    public void setCardId(MahnCreditCards cardId) {
        this.cardId = cardId;
    }

    public MahnVisitors getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(MahnVisitors visitorId) {
        this.visitorId = visitorId;
    }

    public Collection<MahnTicketRoom> getMahnTicketRoomCollection() {
        return mahnTicketRoomCollection;
    }

    public void setMahnTicketRoomCollection(Collection<MahnTicketRoom> mahnTicketRoomCollection) {
        this.mahnTicketRoomCollection = mahnTicketRoomCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ticketId != null ? ticketId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MahnTickets)) {
            return false;
        }
        MahnTickets other = (MahnTickets) object;
        if ((this.ticketId == null && other.ticketId != null) || (this.ticketId != null && !this.ticketId.equals(other.ticketId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto2.proyecto2anddyprendasmatarrita.MahnTickets[ ticketId=" + ticketId + " ]";
    }
    
}