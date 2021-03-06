/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amon.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author amon.sabul
 */
@Entity
@Table(name = "productcategory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Productcategory.findAll", query = "SELECT p FROM Productcategory p")
    , @NamedQuery(name = "Productcategory.findByIdproductcategory", query = "SELECT p FROM Productcategory p WHERE p.idproductcategory = :idproductcategory")
    , @NamedQuery(name = "Productcategory.findByCreatedOn", query = "SELECT p FROM Productcategory p WHERE p.createdOn = :createdOn")
    , @NamedQuery(name = "Productcategory.findByName", query = "SELECT p FROM Productcategory p WHERE p.name = :name")
    , @NamedQuery(name = "Productcategory.findByDescription", query = "SELECT p FROM Productcategory p WHERE p.description = :description")})
public class Productcategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idproductcategory")
    private Integer idproductcategory;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createdOn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "createdBy", referencedColumnName = "idusers")
    @ManyToOne(optional = false)
    private User createdBy;
    @JoinColumn(name = "statusID", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status statusID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private Collection<Products> productsCollection;

    public Productcategory() {
    }

    public Productcategory(Integer idproductcategory) {
        this.idproductcategory = idproductcategory;
    }

    public Productcategory(Integer idproductcategory, Date createdOn, String name, String description) {
        this.idproductcategory = idproductcategory;
        this.createdOn = createdOn;
        this.name = name;
        this.description = description;
    }

    public Integer getIdproductcategory() {
        return idproductcategory;
    }

    public void setIdproductcategory(Integer idproductcategory) {
        this.idproductcategory = idproductcategory;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Status getStatusID() {
        return statusID;
    }

    public void setStatusID(Status statusID) {
        this.statusID = statusID;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<Products> getProductsCollection() {
        return productsCollection;
    }

    public void setProductsCollection(Collection<Products> productsCollection) {
        this.productsCollection = productsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idproductcategory != null ? idproductcategory.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productcategory)) {
            return false;
        }
        Productcategory other = (Productcategory) object;
        if ((this.idproductcategory == null && other.idproductcategory != null) || (this.idproductcategory != null && !this.idproductcategory.equals(other.idproductcategory))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amon.db.Productcategory[ idproductcategory=" + idproductcategory + " ]";
    }
    
}
