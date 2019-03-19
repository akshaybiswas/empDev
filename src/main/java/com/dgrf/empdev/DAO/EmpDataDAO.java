/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgrf.empdev.DAO;

import com.dgrf.empdev.JPA.EmpDetailsJpaController;
import com.dgrf.empdev.entities.EmpDetails;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author dgrfiv
 */
public class EmpDataDAO extends EmpDetailsJpaController {

    public EmpDataDAO() {
        super(Persistence.createEntityManagerFactory("com.dgrf_empDev_jar_1.0-SNAPSHOTPU"));
    }

    public List<EmpDetails> getEmpsByPostId(int postId) {
        EntityManager em = getEntityManager();
        TypedQuery<EmpDetails> query = em.createNamedQuery("EmpDetails.getEmpsByPostId", EmpDetails.class);
        query.setParameter("postId", postId);
        List<EmpDetails> empDataByPost = query.getResultList();
        return empDataByPost;
    }
    
    public List<EmpDetails> getEmpsByProdId(int prodId) {
        EntityManager em = getEntityManager();
        TypedQuery<EmpDetails> query = em.createNamedQuery("EmpDetails.findEmpsByProdId", EmpDetails.class);
        query.setParameter("prodId", prodId);
        List<EmpDetails> empDataByProduct = query.getResultList();
        return empDataByProduct;
    }

}
