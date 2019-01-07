/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amon.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author amon.sabul
 */
@Entity
@Table(name = "producttransaction")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producttransaction.findAll", query = "SELECT p FROM Producttransaction p")
    , @NamedQuery(name = "Producttransaction.findByIdproducttransaction", query = "SELECT p FROM Producttransaction p WHERE p.idproducttransaction = :idproducttransaction")
    , @NamedQuery(name = "Producttransaction.findByTransactionID", query = "SELECT p FROM Producttransaction p WHERE p.transactionID = :transactionID")
    , @NamedQuery(name = "Producttransaction.findByProductID", query = "SELECT p FROM Producttransaction p WHERE p.productID = :productID")
    , @NamedQuery(name = "Producttransaction.findByQuantity", query = "SELECT p FROM Producttransaction p WHERE p.quantity = :quantity")})
public class Producttransaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idproducttransaction")
    private Integer idproducttransaction;
    @Basic(optional = false)
    @NotNull
    @Column(name = "transactionID")
    private int transactionID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "productID")
    private int productID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    private int quantity;
    @JoinColumn(name = "statusID", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status statusID;

    public Producttransaction() {
    }

    public Producttransaction(Integer idproducttransaction) {
        this.idproducttransaction = idproducttransaction;
    }

    public Producttransaction(Integer idproducttransaction, int transactionID, int productID, int quantity) {
        this.idproducttransaction = idproducttransaction;
        this.transactionID = transactionID;
        this.productID = productID;
        this.quantity = quantity;
    }

    public Integer getIdproducttransaction() {
        return idproducttransaction;
    }

    public void setIdproducttransaction(Integer idproducttransaction) {
        this.idproducttransaction = idproducttransaction;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
        hash += (idproducttransaction != null ? idproducttransaction.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producttransaction)) {
            return false;
        }
        Producttransaction other = (Producttransaction) object;
        if ((this.idproducttransaction == null && other.idproducttransaction != null) || (this.idproducttransaction != null && !this.idproducttransaction.equals(other.idproducttransaction))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amon.db.Producttransaction[ idproducttransaction=" + idproducttransaction + " ]";
    }
    
}
