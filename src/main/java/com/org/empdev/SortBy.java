/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.org.empdev;

import com.dgrf.empdev.DAO.EmpDataDAO;
import com.dgrf.empdev.entities.EmpDetails;
import java.util.List;

/**
 *
 * @author dgrfiv
 */
public class SortBy {
    
    public List<EmpDetails> getEmpByPost(int pId) {
        EmpDataDAO empDataDAO = new EmpDataDAO();

        List<EmpDetails> eds = empDataDAO.getEmpsByPostId(pId);

        return eds;
    }
    
    public List<EmpDetails> getEmpByProduct(int pId) {
        EmpDataDAO empDataDAO = new EmpDataDAO();
        
        List<EmpDetails> empDetailsList = empDataDAO.getEmpsByProdId(pId);
        
        return empDetailsList;
    }
    
}
