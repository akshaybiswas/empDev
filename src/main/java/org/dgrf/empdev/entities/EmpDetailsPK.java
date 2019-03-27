/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.empdev.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author dgrfiv
 */
@Embeddable
public class EmpDetailsPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "post_id")
    private int postId;
    @Basic(optional = false)
    @Column(name = "emp_id")
    private int empId;

    public EmpDetailsPK() {
    }

    public EmpDetailsPK(int postId, int empId) {
        this.postId = postId;
        this.empId = empId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) postId;
        hash += (int) empId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpDetailsPK)) {
            return false;
        }
        EmpDetailsPK other = (EmpDetailsPK) object;
        if (this.postId != other.postId) {
            return false;
        }
        if (this.empId != other.empId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dgrf.empdev.entities.EmpDetailsPK[ postId=" + postId + ", empId=" + empId + " ]";
    }
    
}
