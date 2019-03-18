/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.org.empdev;

import com.dgrf.empdev.DAO.EmpDataDAO;
import com.dgrf.empdev.DAO.ProdInfoDAO;

/**
 *
 * @author dgrfiv
 */
public class OverallCount {
    
    private int empCount;
    private int prodCount;
    
    public int numberOfEmp() {
        EmpDataDAO dataDAO = new EmpDataDAO();
        empCount = dataDAO.getEmpDetailsCount();
        return empCount;
    }
    
    public int numberOfProd() {
        ProdInfoDAO prodInfoDAO = new ProdInfoDAO();
        prodCount = prodInfoDAO.getProductInfoCount();
        return prodCount;
    }
    
}
