/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgrf.empdev.DAO;

import com.dgrf.empdev.JPA.ProductInfoJpaController;
import com.dgrf.empdev.entities.EmpDetails;
import com.dgrf.empdev.entities.ProductInfo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author dgrfiv
 */
public class ProdInfoDAO extends ProductInfoJpaController{
    
    public ProdInfoDAO() {
        super(Persistence.createEntityManagerFactory("com.dgrf_empDev_jar_1.0-SNAPSHOTPU"));
    }
    
    public List<ProductInfo> getProdsbyEmpId(int empId) {
        EntityManager em = getEntityManager();
        TypedQuery<ProductInfo> query = em.createNamedQuery("ProductInfo.findAllMeoaw", ProductInfo.class);
        query.setParameter("empId", empId);
        List<ProductInfo> productsByEmpId = query.getResultList();
        return productsByEmpId;
    }
}
