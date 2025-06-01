/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2.proyecto2anddyprendasmatarrita;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Anddy Prendas
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "MAHN_PRICES")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "MahnPrices.findAll", query = "SELECT m FROM MahnPrices m"),
    @javax.persistence.NamedQuery(name = "MahnPrices.findByPriceId", query = "SELECT m FROM MahnPrices m WHERE m.priceId = :priceId"),
    @javax.persistence.NamedQuery(name = "MahnPrices.findByWeekdayPrice", query = "SELECT m FROM MahnPrices m WHERE m.weekdayPrice = :weekdayPrice"),
    @javax.persistence.NamedQuery(name = "MahnPrices.findBySundayPrice", query = "SELECT m FROM MahnPrices m WHERE m.sundayPrice = :sundayPrice")})
public class MahnPrices implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "PRICE_ID")
    private BigDecimal priceId;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "WEEKDAY_PRICE")
    private BigDecimal weekdayPrice;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "SUNDAY_PRICE")
    private BigDecimal sundayPrice;
    @javax.persistence.JoinColumn(name = "ROOM_ID", referencedColumnName = "ROOM_ID")
    @javax.persistence.ManyToOne(optional = false, fetch = javax.persistence.FetchType.EAGER)
    private MahnRooms roomId;

    public MahnPrices() {
    }

    public MahnPrices(BigDecimal priceId) {
        this.priceId = priceId;
    }

    public MahnPrices(BigDecimal priceId, BigDecimal weekdayPrice, BigDecimal sundayPrice) {
        this.priceId = priceId;
        this.weekdayPrice = weekdayPrice;
        this.sundayPrice = sundayPrice;
    }

    public BigDecimal getPriceId() {
        return priceId;
    }

    public void setPriceId(BigDecimal priceId) {
        this.priceId = priceId;
    }

    public BigDecimal getWeekdayPrice() {
        return weekdayPrice;
    }

    public void setWeekdayPrice(BigDecimal weekdayPrice) {
        this.weekdayPrice = weekdayPrice;
    }

    public BigDecimal getSundayPrice() {
        return sundayPrice;
    }

    public void setSundayPrice(BigDecimal sundayPrice) {
        this.sundayPrice = sundayPrice;
    }

    public MahnRooms getRoomId() {
        return roomId;
    }

    public void setRoomId(MahnRooms roomId) {
        this.roomId = roomId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (priceId != null ? priceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MahnPrices)) {
            return false;
        }
        MahnPrices other = (MahnPrices) object;
        if ((this.priceId == null && other.priceId != null) || (this.priceId != null && !this.priceId.equals(other.priceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto2.proyecto2anddyprendasmatarrita.MahnPrices[ priceId=" + priceId + " ]";
    }
    
}
