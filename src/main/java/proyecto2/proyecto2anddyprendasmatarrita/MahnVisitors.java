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
@javax.persistence.Table(name = "MAHN_VISITORS")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "MahnVisitors.findAll", query = "SELECT m FROM MahnVisitors m"),
    @javax.persistence.NamedQuery(name = "MahnVisitors.findByVisitorId", query = "SELECT m FROM MahnVisitors m WHERE m.visitorId = :visitorId"),
    @javax.persistence.NamedQuery(name = "MahnVisitors.findByName", query = "SELECT m FROM MahnVisitors m WHERE m.name = :name"),
    @javax.persistence.NamedQuery(name = "MahnVisitors.findByEmail", query = "SELECT m FROM MahnVisitors m WHERE m.email = :email"),
    @javax.persistence.NamedQuery(name = "MahnVisitors.findByPhone", query = "SELECT m FROM MahnVisitors m WHERE m.phone = :phone")})
public class MahnVisitors implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "VISITOR_ID")
    private BigDecimal visitorId;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "NAME")
    private String name;
    @javax.persistence.Column(name = "EMAIL")
    private String email;
    @javax.persistence.Column(name = "PHONE")
    private String phone;
    @javax.persistence.OneToMany(cascade = javax.persistence.CascadeType.ALL, mappedBy = "visitorId", fetch = javax.persistence.FetchType.EAGER)
    private Collection<MahnTickets> mahnTicketsCollection;

    public MahnVisitors() {
    }

    public MahnVisitors(BigDecimal visitorId) {
        this.visitorId = visitorId;
    }

    public MahnVisitors(BigDecimal visitorId, String name) {
        this.visitorId = visitorId;
        this.name = name;
    }

    public BigDecimal getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(BigDecimal visitorId) {
        this.visitorId = visitorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Collection<MahnTickets> getMahnTicketsCollection() {
        return mahnTicketsCollection;
    }

    public void setMahnTicketsCollection(Collection<MahnTickets> mahnTicketsCollection) {
        this.mahnTicketsCollection = mahnTicketsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (visitorId != null ? visitorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MahnVisitors)) {
            return false;
        }
        MahnVisitors other = (MahnVisitors) object;
        if ((this.visitorId == null && other.visitorId != null) || (this.visitorId != null && !this.visitorId.equals(other.visitorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "proyecto2.proyecto2anddyprendasmatarrita.MahnVisitors[ visitorId=" + visitorId + " ]";
    }
    
}
