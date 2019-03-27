/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.empdev.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dgrfiv
 */
@Entity
@Table(name = "product_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductInfo.findAll", query = "SELECT p FROM ProductInfo p"),
    @NamedQuery(name = "ProductInfo.findByProductId", query = "SELECT p FROM ProductInfo p WHERE p.productId = :productId"),
    @NamedQuery(name = "ProductInfo.findByProductName", query = "SELECT p FROM ProductInfo p WHERE p.productName = :productName"),
    @NamedQuery(name = "ProductInfo.findByProductPrice", query = "SELECT p FROM ProductInfo p WHERE p.productPrice = :productPrice")})
public class ProductInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "product_id")
    private Integer productId;
    @Basic(optional = false)
    @Column(name = "product_name")
    private String productName;
    @Basic(optional = false)
    @Column(name = "product_price")
    private int productPrice;
    @ManyToMany(mappedBy = "productInfoList")
    private List<EmpDetails> empDetailsList;

    public ProductInfo() {
    }

    public ProductInfo(Integer productId) {
        this.productId = productId;
    }

    public ProductInfo(Integer productId, String productName, int productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
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
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductInfo)) {
            return false;
        }
        ProductInfo other = (ProductInfo) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dgrf.empdev.entities.ProductInfo[ productId=" + productId + " ]";
    }
    
}
