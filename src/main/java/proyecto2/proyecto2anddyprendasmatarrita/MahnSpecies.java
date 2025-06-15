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
@javax.persistence.Table(name = "MAHN_SPECIES")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "MahnSpecies.findAll", query = "SELECT m FROM MahnSpecies m"),
    @javax.persistence.NamedQuery(name = "MahnSpecies.findBySpeciesId", query = "SELECT m FROM MahnSpecies m WHERE m.speciesId = :speciesId"),
    @javax.persistence.NamedQuery(name = "MahnSpecies.findByScientificName", query = "SELECT m FROM MahnSpecies m WHERE m.scientificName = :scientificName"),
    @javax.persistence.NamedQuery(name = "MahnSpecies.findByCommonName", query = "SELECT m FROM MahnSpecies m WHERE m.commonName = :commonName"),
    @javax.persistence.NamedQuery(name = "MahnSpecies.findByExtinctionDate", query = "SELECT m FROM MahnSpecies m WHERE m.extinctionDate = :extinctionDate"),
    @javax.persistence.NamedQuery(name = "MahnSpecies.findByEra", query = "SELECT m FROM MahnSpecies m WHERE m.era = :era"),
    @javax.persistence.NamedQuery(name = "MahnSpecies.findByWeight", query = "SELECT m FROM MahnSpecies m WHERE m.weight = :weight"),
    @javax.persistence.NamedQuery(name = "MahnSpecies.findBySpeciesSize", query = "SELECT m FROM MahnSpecies m WHERE m.speciesSize = :speciesSize"),
    @javax.persistence.NamedQuery(name = "MahnSpecies.findByCharacteristics", query = "SELECT m FROM MahnSpecies m WHERE m.characteristics = :characteristics")})
public class MahnSpecies implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "SPECIES_ID")
    private BigDecimal speciesId;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "SCIENTIFIC_NAME")
    private String scientificName;
    @javax.persistence.Column(name = "COMMON_NAME")
    private String commonName;
    @javax.persistence.Column(name = "EXTINCTION_DATE")
    private String extinctionDate;
    @javax.persistence.Column(name = "ERA")
    private String era;
    @javax.persistence.Column(name = "WEIGHT")
    private String weight;
    @javax.persistence.Column(name = "SPECIES_SIZE")
    private String speciesSize;
    @javax.persistence.Column(name = "CHARACTERISTICS")
    private String characteristics;
    @javax.persistence.JoinColumn(name = "COLLECTION_ID", referencedColumnName = "COLLECTION_ID")
    @javax.persistence.ManyToOne(optional = false, fetch = javax.persistence.FetchType.EAGER)
    private MahnCollections collectionId;

    public MahnSpecies() {
    }

    public MahnSpecies(BigDecimal speciesId) {
        this.speciesId = speciesId;
    }

    public MahnSpecies(BigDecimal speciesId, String scientificName) {
        this.speciesId = speciesId;
        this.scientificName = scientificName;
    }

    public BigDecimal getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(BigDecimal speciesId) {
        this.speciesId = speciesId;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getExtinctionDate() {
        return extinctionDate;
    }

    public void setExtinctionDate(String extinctionDate) {
        this.extinctionDate = extinctionDate;
    }

    public String getEra() {
        return era;
    }

    public void setEra(String era) {
        this.era = era;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSpeciesSize() {
        return speciesSize;
    }

    public void setSpeciesSize(String speciesSize) {
        this.speciesSize = speciesSize;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public MahnCollections getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(MahnCollections collectionId) {
        this.collectionId = collectionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (speciesId != null ? speciesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MahnSpecies)) {
            return false;
        }
        MahnSpecies other = (MahnSpecies) object;
        if ((this.speciesId == null && other.speciesId != null) || (this.speciesId != null && !this.speciesId.equals(other.speciesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto2.proyecto2anddyprendasmatarrita.MahnSpecies[ speciesId=" + speciesId + " ]";
    }
    
}
