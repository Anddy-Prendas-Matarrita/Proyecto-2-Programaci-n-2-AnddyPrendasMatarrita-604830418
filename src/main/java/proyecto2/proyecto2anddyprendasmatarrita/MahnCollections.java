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
@javax.persistence.Table(name = "MAHN_COLLECTIONS")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "MahnCollections.findAll", query = "SELECT m FROM MahnCollections m"),
    @javax.persistence.NamedQuery(name = "MahnCollections.findByCollectionId", query = "SELECT m FROM MahnCollections m WHERE m.collectionId = :collectionId"),
    @javax.persistence.NamedQuery(name = "MahnCollections.findByName", query = "SELECT m FROM MahnCollections m WHERE m.name = :name"),
    @javax.persistence.NamedQuery(name = "MahnCollections.findByCentury", query = "SELECT m FROM MahnCollections m WHERE m.century = :century"),
    @javax.persistence.NamedQuery(name = "MahnCollections.findByDescription", query = "SELECT m FROM MahnCollections m WHERE m.description = :description")})
public class MahnCollections implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "COLLECTION_ID")
    private BigDecimal collectionId;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "NAME")
    private String name;
    @javax.persistence.Column(name = "CENTURY")
    private String century;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "DESCRIPTION")
    private String description;
    @javax.persistence.OneToMany(cascade = javax.persistence.CascadeType.ALL, mappedBy = "collectionId", fetch = javax.persistence.FetchType.EAGER)
    private Collection<MahnSpecies> mahnSpeciesCollection;
    @javax.persistence.JoinColumn(name = "ROOM_ID", referencedColumnName = "ROOM_ID")
    @javax.persistence.ManyToOne(optional = false, fetch = javax.persistence.FetchType.EAGER)
    private MahnRooms roomId;

    public MahnCollections() {
    }

    public MahnCollections(BigDecimal collectionId) {
        this.collectionId = collectionId;
    }

    public MahnCollections(BigDecimal collectionId, String name, String description) {
        this.collectionId = collectionId;
        this.name = name;
        this.description = description;
    }

    public BigDecimal getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(BigDecimal collectionId) {
        this.collectionId = collectionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCentury() {
        return century;
    }

    public void setCentury(String century) {
        this.century = century;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<MahnSpecies> getMahnSpeciesCollection() {
        return mahnSpeciesCollection;
    }

    public void setMahnSpeciesCollection(Collection<MahnSpecies> mahnSpeciesCollection) {
        this.mahnSpeciesCollection = mahnSpeciesCollection;
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
        hash += (collectionId != null ? collectionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MahnCollections)) {
            return false;
        }
        MahnCollections other = (MahnCollections) object;
        if ((this.collectionId == null && other.collectionId != null) || (this.collectionId != null && !this.collectionId.equals(other.collectionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto2.proyecto2anddyprendasmatarrita.MahnCollections[ collectionId=" + collectionId + " ]";
    }
    
}
