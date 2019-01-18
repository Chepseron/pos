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
import javax.validation.constraints.Size;
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
    , @NamedQuery(name = "Producttransaction.findByQuantity", query = "SELECT p FROM Producttransaction p WHERE p.quantity = :quantity")
    , @NamedQuery(name = "Producttransaction.findBySessionDetails", query = "SELECT p FROM Producttransaction p WHERE p.sessionDetails = :sessionDetails")})
public class Producttransaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idproducttransaction")
    private Integer idproducttransaction;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    private int quantity;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "sessionDetails")
    private String sessionDetails;
    @JoinColumn(name = "transactionID", referencedColumnName = "idtransactions")
    @ManyToOne(optional = false)
    private Transactions transactionID;
    @JoinColumn(name = "statusID", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status statusID;
    @JoinColumn(name = "productID", referencedColumnName = "idproducts")
    @ManyToOne(optional = false)
    private Products productID;

    public Producttransaction() {
    }

    public Producttransaction(Integer idproducttransaction) {
        this.idproducttransaction = idproducttransaction;
    }

    public Producttransaction(Integer idproducttransaction, int quantity, String sessionDetails) {
        this.idproducttransaction = idproducttransaction;
        this.quantity = quantity;
        this.sessionDetails = sessionDetails;
    }

    public Integer getIdproducttransaction() {
        return idproducttransaction;
    }

    public void setIdproducttransaction(Integer idproducttransaction) {
        this.idproducttransaction = idproducttransaction;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSessionDetails() {
        return sessionDetails;
    }

    public void setSessionDetails(String sessionDetails) {
        this.sessionDetails = sessionDetails;
    }

    public Transactions getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Transactions transactionID) {
        this.transactionID = transactionID;
    }

    public Status getStatusID() {
        return statusID;
    }

    public void setStatusID(Status statusID) {
        this.statusID = statusID;
    }

    public Products getProductID() {
        return productID;
    }

    public void setProductID(Products productID) {
        this.productID = productID;
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
