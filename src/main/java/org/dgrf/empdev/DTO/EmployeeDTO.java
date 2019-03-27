/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.empdev.DTO;

import org.dgrf.empdev.entities.ProductInfo;
import java.util.Date;
import java.util.List;

/**
 *
 * @author dgrfiv
 */
public class EmployeeDTO {
    private int Id;
    private String name;
    private int postId;
    private String postName;
    private Date joined;
    private int exp;
    private Integer[] productIdList; 

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public Date getJoined() {
        return joined;
    }

    public void setJoined(Date joined) {
        this.joined = joined;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public Integer[] getProductIdList() {
        return productIdList;
    }

    public void setProductIdList(Integer[] productIdList) {
        this.productIdList = productIdList;
    }

    
}
