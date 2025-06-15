package proyecto2.proyecto2anddyprendasmatarrita;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
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
import javax.persistence.Table;

/**
 *
 * @author Anddy Prendas
 */
@Entity
@Table(name = "MAHN_SPECIES")
@NamedQueries({
    @NamedQuery(name = "MahnSpecies.findAll", query = "SELECT m FROM MahnSpecies m"),
    @NamedQuery(name = "MahnSpecies.findBySpeciesId", query = "SELECT m FROM MahnSpecies m WHERE m.speciesId = :speciesId"),
    @NamedQuery(name = "MahnSpecies.findByScientificName", query = "SELECT m FROM MahnSpecies m WHERE m.scientificName = :scientificName"),
    @NamedQuery(name = "MahnSpecies.findByCommonName", query = "SELECT m FROM MahnSpecies m WHERE m.commonName = :commonName"),
    @NamedQuery(name = "MahnSpecies.findByExtinctionDate", query = "SELECT m FROM MahnSpecies m WHERE m.extinctionDate = :extinctionDate"),
    @NamedQuery(name = "MahnSpecies.findByEra", query = "SELECT m FROM MahnSpecies m WHERE m.era = :era"),
    @NamedQuery(name = "MahnSpecies.findByWeight", query = "SELECT m FROM MahnSpecies m WHERE m.weight = :weight"),
    @NamedQuery(name = "MahnSpecies.findBySpeciesSize", query = "SELECT m FROM MahnSpecies m WHERE m.speciesSize = :speciesSize"),
    @NamedQuery(name = "MahnSpecies.findByCharacteristics", query = "SELECT m FROM MahnSpecies m WHERE m.characteristics = :characteristics")})
public class MahnSpecies implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SPECIES_ID")
    private BigDecimal speciesId;
    @Basic(optional = false)
    @Column(name = "SCIENTIFIC_NAME")
    private String scientificName;
    @Column(name = "COMMON_NAME")
    private String commonName;
    @Column(name = "EXTINCTION_DATE")
    private String extinctionDate;
    @Column(name = "ERA")
    private String era;
    @Column(name = "WEIGHT")
    private String weight;
    @Column(name = "SPECIES_SIZE")
    private String speciesSize;
    @Column(name = "CHARACTERISTICS")
    private String characteristics;
    @JoinColumn(name = "COLLECTION_ID", referencedColumnName = "COLLECTION_ID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER) 
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
        return (commonName != null && !commonName.isEmpty()) ? commonName : scientificName;
    }
}