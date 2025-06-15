
package proyecto2.proyecto2anddyprendasmatarrita;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List; 
import java.util.HashSet;
import java.util.Set; 

/**
 *
 * @author Anddy Prendas
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "MAHN_ROOMS")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "MahnRooms.findAll", query = "SELECT m FROM MahnRooms m"),
    @javax.persistence.NamedQuery(name = "MahnRooms.findByRoomId", query = "SELECT m FROM MahnRooms m WHERE m.roomId = :roomId"),
    @javax.persistence.NamedQuery(name = "MahnRooms.findByName", query = "SELECT m FROM MahnRooms m WHERE m.name = :name"),
    @javax.persistence.NamedQuery(name = "MahnRooms.findByDescription", query = "SELECT m FROM MahnRooms m WHERE m.description = :description"),
    @javax.persistence.NamedQuery(name = "MahnRooms.findByMainTheme", query = "SELECT m FROM MahnRooms m WHERE m.mainTheme = :mainTheme")})
public class MahnRooms implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "ROOM_ID")
    private BigDecimal roomId;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "NAME")
    private String name;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "DESCRIPTION")
    private String description;
    @javax.persistence.Column(name = "MAIN_THEME")
    private String mainTheme;
    
    @javax.persistence.OneToMany(cascade = javax.persistence.CascadeType.ALL, mappedBy = "roomId", fetch = javax.persistence.FetchType.EAGER)
    private Set<MahnCollections> mahnCollectionsCollection = new HashSet<>();
    
    @javax.persistence.JoinColumn(name = "MUSEUM_ID", referencedColumnName = "MUSEUM_ID")
    @javax.persistence.ManyToOne(optional = false, fetch = javax.persistence.FetchType.EAGER)
    private MahnMuseums museumId;
    
    @javax.persistence.OneToMany(cascade = javax.persistence.CascadeType.ALL, mappedBy = "roomId", fetch = javax.persistence.FetchType.EAGER)
    private Set<MahnTopics> mahnTopicsCollection = new HashSet<>();
    
    @javax.persistence.OneToMany(cascade = javax.persistence.CascadeType.ALL, mappedBy = "roomId", fetch = javax.persistence.FetchType.EAGER)
    private Set<MahnTicketRoom> mahnTicketRoomCollection = new HashSet<>();
    
    @javax.persistence.OneToMany(cascade = javax.persistence.CascadeType.ALL, mappedBy = "roomId", fetch = javax.persistence.FetchType.EAGER)
    private Set<MahnPrices> mahnPricesCollection = new HashSet<>();
    
    @javax.persistence.OneToMany(cascade = javax.persistence.CascadeType.ALL, mappedBy = "roomId", fetch = javax.persistence.FetchType.EAGER)
    private Set<MahnRatings> mahnRatingsCollection = new HashSet<>();
    // -----------------------------------------------------------

    public MahnRooms() {
    }

    public MahnRooms(BigDecimal roomId) {
        this.roomId = roomId;
    }

    public MahnRooms(BigDecimal roomId, String name, String description) {
        this.roomId = roomId;
        this.name = name;
        this.description = description;
    }

    public BigDecimal getRoomId() {
        return roomId;
    }

    public void setRoomId(BigDecimal roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMainTheme() {
        return mainTheme;
    }

    public void setMainTheme(String mainTheme) {
        this.mainTheme = mainTheme;
    }

    public Set<MahnCollections> getMahnCollectionsCollection() {
        return mahnCollectionsCollection;
    }

    public void setMahnCollectionsCollection(Set<MahnCollections> mahnCollectionsCollection) {
        this.mahnCollectionsCollection = mahnCollectionsCollection;
    }

    public MahnMuseums getMuseumId() {
        return museumId;
    }

    public void setMuseumId(MahnMuseums museumId) {
        this.museumId = museumId;
    }

    public Set<MahnTopics> getMahnTopicsCollection() {
        return mahnTopicsCollection;
    }

    public void setMahnTopicsCollection(Set<MahnTopics> mahnTopicsCollection) {
        this.mahnTopicsCollection = mahnTopicsCollection;
    }

    public Set<MahnTicketRoom> getMahnTicketRoomCollection() {
        return mahnTicketRoomCollection;
    }

    public void setMahnTicketRoomCollection(Set<MahnTicketRoom> mahnTicketRoomCollection) {
        this.mahnTicketRoomCollection = mahnTicketRoomCollection;
    }

    public Set<MahnPrices> getMahnPricesCollection() { 
        return mahnPricesCollection;
    }

    public void setMahnPricesCollection(Set<MahnPrices> mahnPricesCollection) {
        this.mahnPricesCollection = mahnPricesCollection;
    }

    public Set<MahnRatings> getMahnRatingsCollection() {
        return mahnRatingsCollection;
    }

    public void setMahnRatingsCollection(Set<MahnRatings> mahnRatingsCollection) {
        this.mahnRatingsCollection = mahnRatingsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomId != null ? roomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MahnRooms)) {
            return false;
        }
        MahnRooms other = (MahnRooms) object;
        if ((this.roomId == null && other.roomId != null) || (this.roomId != null && !this.roomId.equals(other.roomId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto2.proyecto2anddyprendasmatarrita.MahnRooms[ roomId=" + roomId + " ]";
    }
    
}