/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgrf.empdev.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dgrfiv
 */
@Entity
@Table(name = "emp_post")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpPost.findAll", query = "SELECT e FROM EmpPost e"),
    @NamedQuery(name = "EmpPost.findByPostId", query = "SELECT e FROM EmpPost e WHERE e.postId = :postId"),
    @NamedQuery(name = "EmpPost.findByPostName", query = "SELECT e FROM EmpPost e WHERE e.postName = :postName"),
    @NamedQuery(name = "EmpPost.findByPostGp", query = "SELECT e FROM EmpPost e WHERE e.postGp = :postGp")})
public class EmpPost implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "post_id")
    private Integer postId;
    @Basic(optional = false)
    @Column(name = "post_name")
    private String postName;
    @Basic(optional = false)
    @Column(name = "post_gp")
    private int postGp;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empPost")
    private List<EmpDetails> empDetailsList;

    public EmpPost() {
    }

    public EmpPost(Integer postId) {
        this.postId = postId;
    }

    public EmpPost(Integer postId, String postName, int postGp) {
        this.postId = postId;
        this.postName = postName;
        this.postGp = postGp;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public int getPostGp() {
        return postGp;
    }

    public void setPostGp(int postGp) {
        this.postGp = postGp;
    }

    @XmlTransient
    public List<EmpDetails> getEmpDetailsList() {
        return empDetailsList;
    }

    public void setEmpDetailsList(List<EmpDetails> empDetailsList) {
        this.empDetailsList = empDetailsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (postId != null ? postId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpPost)) {
            return false;
        }
        EmpPost other = (EmpPost) object;
        if ((this.postId == null && other.postId != null) || (this.postId != null && !this.postId.equals(other.postId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dgrf.empdev.entities.EmpPost[ postId=" + postId + " ]";
    }
    
}
