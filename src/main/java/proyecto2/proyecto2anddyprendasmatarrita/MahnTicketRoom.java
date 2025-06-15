/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2.proyecto2anddyprendasmatarrita;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Anddy Prendas
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "MAHN_TICKET_ROOM")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "MahnTicketRoom.findAll", query = "SELECT m FROM MahnTicketRoom m"),
    @javax.persistence.NamedQuery(name = "MahnTicketRoom.findByTicketRoomId", query = "SELECT m FROM MahnTicketRoom m WHERE m.ticketRoomId = :ticketRoomId"),
    @javax.persistence.NamedQuery(name = "MahnTicketRoom.findByVisitDate", query = "SELECT m FROM MahnTicketRoom m WHERE m.visitDate = :visitDate")})
public class MahnTicketRoom implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "TICKET_ROOM_ID")
    private BigDecimal ticketRoomId;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "VISIT_DATE")
    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date visitDate;
    @javax.persistence.JoinColumn(name = "ROOM_ID", referencedColumnName = "ROOM_ID")
    @javax.persistence.ManyToOne(optional = false, fetch = javax.persistence.FetchType.EAGER)
    private MahnRooms roomId;
    @javax.persistence.JoinColumn(name = "TICKET_ID", referencedColumnName = "TICKET_ID")
    @javax.persistence.ManyToOne(optional = false, fetch = javax.persistence.FetchType.EAGER)
    private MahnTickets ticketId;

    public MahnTicketRoom() {
    }

    public MahnTicketRoom(BigDecimal ticketRoomId) {
        this.ticketRoomId = ticketRoomId;
    }

    public MahnTicketRoom(BigDecimal ticketRoomId, Date visitDate) {
        this.ticketRoomId = ticketRoomId;
        this.visitDate = visitDate;
    }

    public BigDecimal getTicketRoomId() {
        return ticketRoomId;
    }

    public void setTicketRoomId(BigDecimal ticketRoomId) {
        this.ticketRoomId = ticketRoomId;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public MahnRooms getRoomId() {
        return roomId;
    }

    public void setRoomId(MahnRooms roomId) {
        this.roomId = roomId;
    }

    public MahnTickets getTicketId() {
        return ticketId;
    }

    public void setTicketId(MahnTickets ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ticketRoomId != null ? ticketRoomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MahnTicketRoom)) {
            return false;
        }
        MahnTicketRoom other = (MahnTicketRoom) object;
        if ((this.ticketRoomId == null && other.ticketRoomId != null) || (this.ticketRoomId != null && !this.ticketRoomId.equals(other.ticketRoomId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto2.proyecto2anddyprendasmatarrita.MahnTicketRoom[ ticketRoomId=" + ticketRoomId + " ]";
    }
    
}