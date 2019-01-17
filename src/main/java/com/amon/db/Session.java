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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author amon.sabul
 */
@Entity
@Table(name = "session")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Session.findAll", query = "SELECT s FROM Session s")
    , @NamedQuery(name = "Session.findByIdsession", query = "SELECT s FROM Session s WHERE s.idsession = :idsession")
    , @NamedQuery(name = "Session.findByStartstime", query = "SELECT s FROM Session s WHERE s.startstime = :startstime")
    , @NamedQuery(name = "Session.findByEndtime", query = "SELECT s FROM Session s WHERE s.endtime = :endtime")
    , @NamedQuery(name = "Session.findByCreatedon", query = "SELECT s FROM Session s WHERE s.createdon = :createdon")})
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsession")
    private Integer idsession;
    @Basic(optional = false)
    @NotNull
    @Column(name = "startstime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startstime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "endtime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endtime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createdon")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdon;
    @JoinColumn(name = "createdby", referencedColumnName = "idusers")
    @ManyToOne(optional = false)
    private User createdby;
    @JoinColumn(name = "statusid", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status statusid;
    @OneToMany(mappedBy = "sessionid")
    private Collection<User> userCollection;

    public Session() {
    }

    public Session(Integer idsession) {
        this.idsession = idsession;
    }

    public Session(Integer idsession, Date startstime, Date endtime, Date createdon) {
        this.idsession = idsession;
        this.startstime = startstime;
        this.endtime = endtime;
        this.createdon = createdon;
    }

    public Integer getIdsession() {
        return idsession;
    }

    public void setIdsession(Integer idsession) {
        this.idsession = idsession;
    }

    public Date getStartstime() {
        return startstime;
    }

    public void setStartstime(Date startstime) {
        this.startstime = startstime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getCreatedon() {
        return createdon;
    }

    public void setCreatedon(Date createdon) {
        this.createdon = createdon;
    }

    public User getCreatedby() {
        return createdby;
    }

    public void setCreatedby(User createdby) {
        this.createdby = createdby;
    }

    public Status getStatusid() {
        return statusid;
    }

    public void setStatusid(Status statusid) {
        this.statusid = statusid;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsession != null ? idsession.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Session)) {
            return false;
        }
        Session other = (Session) object;
        if ((this.idsession == null && other.idsession != null) || (this.idsession != null && !this.idsession.equals(other.idsession))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amon.db.Session[ idsession=" + idsession + " ]";
    }
    
}
