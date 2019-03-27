/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.empdev.DAO;

import org.dgrf.empdev.JPA.EmpPostJpaController;
import org.dgrf.empdev.entities.EmpPost;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author dgrfiv
 */
public class EmpPostsDAO extends EmpPostJpaController{
    
    public EmpPostsDAO() {
        super(Persistence.createEntityManagerFactory("com.dgrf_empDev_jar_1.0-SNAPSHOTPU"));
    }
    
    
}
