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
@Table(name = "paymentmethods")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paymentmethods.findAll", query = "SELECT p FROM Paymentmethods p")
    , @NamedQuery(name = "Paymentmethods.findByIdpaymentmethods", query = "SELECT p FROM Paymentmethods p WHERE p.idpaymentmethods = :idpaymentmethods")
    , @NamedQuery(name = "Paymentmethods.findByCreatedOn", query = "SELECT p FROM Paymentmethods p WHERE p.createdOn = :createdOn")
    , @NamedQuery(name = "Paymentmethods.findByMethod", query = "SELECT p FROM Paymentmethods p WHERE p.method = :method")
    , @NamedQuery(name = "Paymentmethods.findByDescription", query = "SELECT p FROM Paymentmethods p WHERE p.description = :description")})
public class Paymentmethods implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpaymentmethods")
    private Integer idpaymentmethods;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createdOn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "method")
    private String method;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "methodcode")
    private Collection<Payments> paymentsCollection;
    @JoinColumn(name = "createdBy", referencedColumnName = "idusers")
    @ManyToOne(optional = false)
    private User createdBy;
    @JoinColumn(name = "status", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status status;

    public Paymentmethods() {
    }

    public Paymentmethods(Integer idpaymentmethods) {
        this.idpaymentmethods = idpaymentmethods;
    }

    public Paymentmethods(Integer idpaymentmethods, Date createdOn, String method, String description) {
        this.idpaymentmethods = idpaymentmethods;
        this.createdOn = createdOn;
        this.method = method;
        this.description = description;
    }

    public Integer getIdpaymentmethods() {
        return idpaymentmethods;
    }

    public void setIdpaymentmethods(Integer idpaymentmethods) {
        this.idpaymentmethods = idpaymentmethods;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<Payments> getPaymentsCollection() {
        return paymentsCollection;
    }

    public void setPaymentsCollection(Collection<Payments> paymentsCollection) {
        this.paymentsCollection = paymentsCollection;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpaymentmethods != null ? idpaymentmethods.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paymentmethods)) {
            return false;
        }
        Paymentmethods other = (Paymentmethods) object;
        if ((this.idpaymentmethods == null && other.idpaymentmethods != null) || (this.idpaymentmethods != null && !this.idpaymentmethods.equals(other.idpaymentmethods))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amon.db.Paymentmethods[ idpaymentmethods=" + idpaymentmethods + " ]";
    }
    
}
