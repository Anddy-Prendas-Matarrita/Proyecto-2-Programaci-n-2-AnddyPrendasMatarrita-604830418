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
@javax.persistence.Table(name = "MAHN_MUSEUMS")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "MahnMuseums.findAll", query = "SELECT m FROM MahnMuseums m"),
    @javax.persistence.NamedQuery(name = "MahnMuseums.findByMuseumId", query = "SELECT m FROM MahnMuseums m WHERE m.museumId = :museumId"),
    @javax.persistence.NamedQuery(name = "MahnMuseums.findByName", query = "SELECT m FROM MahnMuseums m WHERE m.name = :name"),
    @javax.persistence.NamedQuery(name = "MahnMuseums.findByMuseumType", query = "SELECT m FROM MahnMuseums m WHERE m.museumType = :museumType"),
    @javax.persistence.NamedQuery(name = "MahnMuseums.findByLocation", query = "SELECT m FROM MahnMuseums m WHERE m.location = :location"),
    @javax.persistence.NamedQuery(name = "MahnMuseums.findByFoundationDate", query = "SELECT m FROM MahnMuseums m WHERE m.foundationDate = :foundationDate"),
    @javax.persistence.NamedQuery(name = "MahnMuseums.findByDirector", query = "SELECT m FROM MahnMuseums m WHERE m.director = :director"),
    @javax.persistence.NamedQuery(name = "MahnMuseums.findByWebsite", query = "SELECT m FROM MahnMuseums m WHERE m.website = :website")})
public class MahnMuseums implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "MUSEUM_ID")
    private BigDecimal museumId;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "NAME")
    private String name;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "MUSEUM_TYPE")
    private String museumType;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "LOCATION")
    private String location;
    @javax.persistence.Column(name = "FOUNDATION_DATE")
    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date foundationDate;
    @javax.persistence.Column(name = "DIRECTOR")
    private String director;
    @javax.persistence.Column(name = "WEBSITE")
    private String website;
    @javax.persistence.OneToMany(cascade = javax.persistence.CascadeType.ALL, mappedBy = "museumId", fetch = javax.persistence.FetchType.EAGER)
    private Collection<MahnRooms> mahnRoomsCollection;

    public MahnMuseums() {
    }

    public MahnMuseums(BigDecimal museumId) {
        this.museumId = museumId;
    }

    public MahnMuseums(BigDecimal museumId, String name, String museumType, String location) {
        this.museumId = museumId;
        this.name = name;
        this.museumType = museumType;
        this.location = location;
    }

    public BigDecimal getMuseumId() {
        return museumId;
    }

    public void setMuseumId(BigDecimal museumId) {
        this.museumId = museumId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMuseumType() {
        return museumType;
    }

    public void setMuseumType(String museumType) {
        this.museumType = museumType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getFoundationDate() {
        return foundationDate;
    }

    public void setFoundationDate(Date foundationDate) {
        this.foundationDate = foundationDate;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Collection<MahnRooms> getMahnRoomsCollection() {
        return mahnRoomsCollection;
    }

    public void setMahnRoomsCollection(Collection<MahnRooms> mahnRoomsCollection) {
        this.mahnRoomsCollection = mahnRoomsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (museumId != null ? museumId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MahnMuseums)) {
            return false;
        }
        MahnMuseums other = (MahnMuseums) object;
        if ((this.museumId == null && other.museumId != null) || (this.museumId != null && !this.museumId.equals(other.museumId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto2.proyecto2anddyprendasmatarrita.MahnMuseums[ museumId=" + museumId + " ]";
    }
    
}
