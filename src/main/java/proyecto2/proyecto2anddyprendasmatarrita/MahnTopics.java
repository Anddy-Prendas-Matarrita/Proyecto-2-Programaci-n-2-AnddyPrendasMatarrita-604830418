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
@javax.persistence.Table(name = "MAHN_TOPICS")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "MahnTopics.findAll", query = "SELECT m FROM MahnTopics m"),
    @javax.persistence.NamedQuery(name = "MahnTopics.findByTopicId", query = "SELECT m FROM MahnTopics m WHERE m.topicId = :topicId"),
    @javax.persistence.NamedQuery(name = "MahnTopics.findByName", query = "SELECT m FROM MahnTopics m WHERE m.name = :name"),
    @javax.persistence.NamedQuery(name = "MahnTopics.findByCharacteristics", query = "SELECT m FROM MahnTopics m WHERE m.characteristics = :characteristics"),
    @javax.persistence.NamedQuery(name = "MahnTopics.findByEra", query = "SELECT m FROM MahnTopics m WHERE m.era = :era")})
public class MahnTopics implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "TOPIC_ID")
    private BigDecimal topicId;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "NAME")
    private String name;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "CHARACTERISTICS")
    private String characteristics;
    @javax.persistence.Column(name = "ERA")
    private String era;
    @javax.persistence.JoinColumn(name = "ROOM_ID", referencedColumnName = "ROOM_ID")
    @javax.persistence.ManyToOne(optional = false, fetch = javax.persistence.FetchType.EAGER)
    private MahnRooms roomId;

    public MahnTopics() {
    }

    public MahnTopics(BigDecimal topicId) {
        this.topicId = topicId;
    }

    public MahnTopics(BigDecimal topicId, String name, String characteristics) {
        this.topicId = topicId;
        this.name = name;
        this.characteristics = characteristics;
    }

    public BigDecimal getTopicId() {
        return topicId;
    }

    public void setTopicId(BigDecimal topicId) {
        this.topicId = topicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public String getEra() {
        return era;
    }

    public void setEra(String era) {
        this.era = era;
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
        hash += (topicId != null ? topicId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MahnTopics)) {
            return false;
        }
        MahnTopics other = (MahnTopics) object;
        if ((this.topicId == null && other.topicId != null) || (this.topicId != null && !this.topicId.equals(other.topicId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto2.proyecto2anddyprendasmatarrita.MahnTopics[ topicId=" + topicId + " ]";
    }
    
}
