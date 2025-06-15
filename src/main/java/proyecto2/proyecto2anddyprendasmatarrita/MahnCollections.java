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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Anddy Prendas
 */
@Entity
@Table(name = "MAHN_COLLECTIONS")
@NamedQueries({
    @NamedQuery(name = "MahnCollections.findAll", query = "SELECT m FROM MahnCollections m"),
    @NamedQuery(name = "MahnCollections.findByCollectionId", query = "SELECT m FROM MahnCollections m WHERE m.collectionId = :collectionId"),
    @NamedQuery(name = "MahnCollections.findByName", query = "SELECT m FROM MahnCollections m WHERE m.name = :name"),
    @NamedQuery(name = "MahnCollections.findByCentury", query = "SELECT m FROM MahnCollections m WHERE m.century = :century"),
    @NamedQuery(name = "MahnCollections.findByDescription", query = "SELECT m FROM MahnCollections m WHERE m.description = :description")})
public class MahnCollections implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "COLLECTION_ID")
    private BigDecimal collectionId;
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;
    @Column(name = "CENTURY")
    private String century;
    @Basic(optional = false)
    @Column(name = "DESCRIPTION")
    private String description;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "collectionId", fetch = FetchType.LAZY)
    private Collection<MahnSpecies> mahnSpeciesCollection;
    
    @JoinColumn(name = "ROOM_ID", referencedColumnName = "ROOM_ID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
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
        return name;
    }
}