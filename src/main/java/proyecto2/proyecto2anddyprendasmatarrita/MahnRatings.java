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
@javax.persistence.Table(name = "MAHN_RATINGS")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "MahnRatings.findAll", query = "SELECT m FROM MahnRatings m"),
    @javax.persistence.NamedQuery(name = "MahnRatings.findByRatingId", query = "SELECT m FROM MahnRatings m WHERE m.ratingId = :ratingId"),
    @javax.persistence.NamedQuery(name = "MahnRatings.findByScore", query = "SELECT m FROM MahnRatings m WHERE m.score = :score"),
    @javax.persistence.NamedQuery(name = "MahnRatings.findByReview", query = "SELECT m FROM MahnRatings m WHERE m.review = :review"),
    @javax.persistence.NamedQuery(name = "MahnRatings.findByRatingDate", query = "SELECT m FROM MahnRatings m WHERE m.ratingDate = :ratingDate")})
public class MahnRatings implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "RATING_ID")
    private BigDecimal ratingId;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "SCORE")
    private short score;
    @javax.persistence.Column(name = "REVIEW")
    private String review;
    @javax.persistence.Column(name = "RATING_DATE")
    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date ratingDate;
    @javax.persistence.JoinColumn(name = "ROOM_ID", referencedColumnName = "ROOM_ID")
    @javax.persistence.ManyToOne(optional = false, fetch = javax.persistence.FetchType.EAGER)
    private MahnRooms roomId;

    public MahnRatings() {
    }

    public MahnRatings(BigDecimal ratingId) {
        this.ratingId = ratingId;
    }

    public MahnRatings(BigDecimal ratingId, short score) {
        this.ratingId = ratingId;
        this.score = score;
    }

    public BigDecimal getRatingId() {
        return ratingId;
    }

    public void setRatingId(BigDecimal ratingId) {
        this.ratingId = ratingId;
    }

    public short getScore() {
        return score;
    }

    public void setScore(short score) {
        this.score = score;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Date getRatingDate() {
        return ratingDate;
    }

    public void setRatingDate(Date ratingDate) {
        this.ratingDate = ratingDate;
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
        hash += (ratingId != null ? ratingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MahnRatings)) {
            return false;
        }
        MahnRatings other = (MahnRatings) object;
        if ((this.ratingId == null && other.ratingId != null) || (this.ratingId != null && !this.ratingId.equals(other.ratingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto2.proyecto2anddyprendasmatarrita.MahnRatings[ ratingId=" + ratingId + " ]";
    }
    
}
