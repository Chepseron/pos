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
@Table(name = "products")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Products.findAll", query = "SELECT p FROM Products p")
    , @NamedQuery(name = "Products.findByIdproducts", query = "SELECT p FROM Products p WHERE p.idproducts = :idproducts")
    , @NamedQuery(name = "Products.findByName", query = "SELECT p FROM Products p WHERE p.name = :name")
    , @NamedQuery(name = "Products.findByWholesaleprice", query = "SELECT p FROM Products p WHERE p.wholesaleprice = :wholesaleprice")
    , @NamedQuery(name = "Products.findByRetailprice", query = "SELECT p FROM Products p WHERE p.retailprice = :retailprice")
    , @NamedQuery(name = "Products.findByCreatedOn", query = "SELECT p FROM Products p WHERE p.createdOn = :createdOn")
    , @NamedQuery(name = "Products.findByStockedQTY", query = "SELECT p FROM Products p WHERE p.stockedQTY = :stockedQTY")
    , @NamedQuery(name = "Products.findByImageurl", query = "SELECT p FROM Products p WHERE p.imageurl = :imageurl")
    , @NamedQuery(name = "Products.findByOtherdetails", query = "SELECT p FROM Products p WHERE p.otherdetails = :otherdetails")})
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idproducts")
    private Integer idproducts;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wholesaleprice")
    private int wholesaleprice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "retailprice")
    private int retailprice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createdOn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stockedQTY")
    private int stockedQTY;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "imageurl")
    private String imageurl;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "otherdetails")
    private String otherdetails;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productID")
    private Collection<Producttransaction> producttransactionCollection;
    @JoinColumn(name = "category", referencedColumnName = "idproductcategory")
    @ManyToOne(optional = false)
    private Productcategory category;
    @JoinColumn(name = "createdby", referencedColumnName = "idusers")
    @ManyToOne(optional = false)
    private User createdby;
    @JoinColumn(name = "statusID", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status statusID;

    public Products() {
    }

    public Products(Integer idproducts) {
        this.idproducts = idproducts;
    }

    public Products(Integer idproducts, String name, int wholesaleprice, int retailprice, Date createdOn, int stockedQTY, String imageurl, String otherdetails) {
        this.idproducts = idproducts;
        this.name = name;
        this.wholesaleprice = wholesaleprice;
        this.retailprice = retailprice;
        this.createdOn = createdOn;
        this.stockedQTY = stockedQTY;
        this.imageurl = imageurl;
        this.otherdetails = otherdetails;
    }

    public Integer getIdproducts() {
        return idproducts;
    }

    public void setIdproducts(Integer idproducts) {
        this.idproducts = idproducts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWholesaleprice() {
        return wholesaleprice;
    }

    public void setWholesaleprice(int wholesaleprice) {
        this.wholesaleprice = wholesaleprice;
    }

    public int getRetailprice() {
        return retailprice;
    }

    public void setRetailprice(int retailprice) {
        this.retailprice = retailprice;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public int getStockedQTY() {
        return stockedQTY;
    }

    public void setStockedQTY(int stockedQTY) {
        this.stockedQTY = stockedQTY;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getOtherdetails() {
        return otherdetails;
    }

    public void setOtherdetails(String otherdetails) {
        this.otherdetails = otherdetails;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<Producttransaction> getProducttransactionCollection() {
        return producttransactionCollection;
    }

    public void setProducttransactionCollection(Collection<Producttransaction> producttransactionCollection) {
        this.producttransactionCollection = producttransactionCollection;
    }

    public Productcategory getCategory() {
        return category;
    }

    public void setCategory(Productcategory category) {
        this.category = category;
    }

    public User getCreatedby() {
        return createdby;
    }

    public void setCreatedby(User createdby) {
        this.createdby = createdby;
    }

    public Status getStatusID() {
        return statusID;
    }

    public void setStatusID(Status statusID) {
        this.statusID = statusID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idproducts != null ? idproducts.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Products)) {
            return false;
        }
        Products other = (Products) object;
        if ((this.idproducts == null && other.idproducts != null) || (this.idproducts != null && !this.idproducts.equals(other.idproducts))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amon.db.Products[ idproducts=" + idproducts + " ]";
    }
    
}
