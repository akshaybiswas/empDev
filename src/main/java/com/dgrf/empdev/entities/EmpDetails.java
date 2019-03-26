/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgrf.empdev.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dgrfiv
 */
@Entity
@Table(name = "emp_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpDetails.findAll", query = "SELECT e FROM EmpDetails e"),
    @NamedQuery(name = "EmpDetails.findByPostId", query = "SELECT e FROM EmpDetails e WHERE e.empDetailsPK.postId = :postId"),
    @NamedQuery(name = "EmpDetails.findByEmpId", query = "SELECT e FROM EmpDetails e WHERE e.empDetailsPK.empId = :empId"),
    @NamedQuery(name = "EmpDetails.findByEmpName", query = "SELECT e FROM EmpDetails e WHERE e.empName = :empName"),
    @NamedQuery(name = "EmpDetails.findByEmpJoined", query = "SELECT e FROM EmpDetails e WHERE e.empJoined = :empJoined"),
    @NamedQuery(name = "EmpDetails.findByEmpExp", query = "SELECT e FROM EmpDetails e WHERE e.empExp = :empExp")})
public class EmpDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EmpDetailsPK empDetailsPK;
    @Basic(optional = false)
    @Column(name = "emp_name")
    private String empName;
    @Basic(optional = false)
    @Column(name = "emp_joined")
    @Temporal(TemporalType.TIMESTAMP)
    private Date empJoined;
    @Basic(optional = false)
    @Column(name = "emp_exp")
    private int empExp;
    @JoinTable(name = "product_info_has_emp_details", joinColumns = {
        @JoinColumn(name = "emp_details_post_id", referencedColumnName = "post_id"),
        @JoinColumn(name = "emp_details_emp_id", referencedColumnName = "emp_id")}, inverseJoinColumns = {
        @JoinColumn(name = "product_info_product_id", referencedColumnName = "product_id")})
    @ManyToMany
    private List<ProductInfo> productInfoList;
    @JoinColumn(name = "post_id", referencedColumnName = "post_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private EmpPost empPost;

    public EmpDetails() {
    }

    public EmpDetails(EmpDetailsPK empDetailsPK) {
        this.empDetailsPK = empDetailsPK;
    }

    public EmpDetails(EmpDetailsPK empDetailsPK, String empName, Date empJoined, int empExp) {
        this.empDetailsPK = empDetailsPK;
        this.empName = empName;
        this.empJoined = empJoined;
        this.empExp = empExp;
    }

    public EmpDetails(int postId, int empId) {
        this.empDetailsPK = new EmpDetailsPK(postId, empId);
    }

    public EmpDetailsPK getEmpDetailsPK() {
        return empDetailsPK;
    }

    public void setEmpDetailsPK(EmpDetailsPK empDetailsPK) {
        this.empDetailsPK = empDetailsPK;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Date getEmpJoined() {
        return empJoined;
    }

    public void setEmpJoined(Date empJoined) {
        this.empJoined = empJoined;
    }

    public int getEmpExp() {
        return empExp;
    }

    public void setEmpExp(int empExp) {
        this.empExp = empExp;
    }

    @XmlTransient
    public List<ProductInfo> getProductInfoList() {
        return productInfoList;
    }

    public void setProductInfoList(List<ProductInfo> productInfoList) {
        this.productInfoList = productInfoList;
    }

    public EmpPost getEmpPost() {
        return empPost;
    }

    public void setEmpPost(EmpPost empPost) {
        this.empPost = empPost;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empDetailsPK != null ? empDetailsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpDetails)) {
            return false;
        }
        EmpDetails other = (EmpDetails) object;
        if ((this.empDetailsPK == null && other.empDetailsPK != null) || (this.empDetailsPK != null && !this.empDetailsPK.equals(other.empDetailsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dgrf.empdev.entities.EmpDetails[ empDetailsPK=" + empDetailsPK + " ]";
    }
    
}
