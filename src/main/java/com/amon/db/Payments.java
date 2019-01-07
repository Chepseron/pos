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
@Table(name = "payments")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Payments.findAll", query = "SELECT p FROM Payments p")
    , @NamedQuery(name = "Payments.findByIdpayments", query = "SELECT p FROM Payments p WHERE p.idpayments = :idpayments")
    , @NamedQuery(name = "Payments.findByPaymentAmount", query = "SELECT p FROM Payments p WHERE p.paymentAmount = :paymentAmount")
    , @NamedQuery(name = "Payments.findByOtherdetails", query = "SELECT p FROM Payments p WHERE p.otherdetails = :otherdetails")})
public class Payments implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpayments")
    private Integer idpayments;
    @Basic(optional = false)
    @NotNull
    @Column(name = "paymentAmount")
    private int paymentAmount;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "otherdetails")
    private String otherdetails;
    @JoinColumn(name = "transactionID", referencedColumnName = "idtransactions")
    @ManyToOne(optional = false)
    private Transactions transactionID;
    @JoinColumn(name = "methodcode", referencedColumnName = "idpaymentmethods")
    @ManyToOne(optional = false)
    private Paymentmethods methodcode;

    public Payments() {
    }

    public Payments(Integer idpayments) {
        this.idpayments = idpayments;
    }

    public Payments(Integer idpayments, int paymentAmount, String otherdetails) {
        this.idpayments = idpayments;
        this.paymentAmount = paymentAmount;
        this.otherdetails = otherdetails;
    }

    public Integer getIdpayments() {
        return idpayments;
    }

    public void setIdpayments(Integer idpayments) {
        this.idpayments = idpayments;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getOtherdetails() {
        return otherdetails;
    }

    public void setOtherdetails(String otherdetails) {
        this.otherdetails = otherdetails;
    }

    public Transactions getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Transactions transactionID) {
        this.transactionID = transactionID;
    }

    public Paymentmethods getMethodcode() {
        return methodcode;
    }

    public void setMethodcode(Paymentmethods methodcode) {
        this.methodcode = methodcode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpayments != null ? idpayments.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Payments)) {
            return false;
        }
        Payments other = (Payments) object;
        if ((this.idpayments == null && other.idpayments != null) || (this.idpayments != null && !this.idpayments.equals(other.idpayments))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amon.db.Payments[ idpayments=" + idpayments + " ]";
    }
    
}
