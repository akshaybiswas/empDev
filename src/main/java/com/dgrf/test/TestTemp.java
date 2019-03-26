/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgrf.test;

import com.dgrf.empdev.DAO.EmpDataDAO;
import com.dgrf.empdev.DAO.ProdInfoDAO;
import com.dgrf.empdev.DTO.EmployeeDTO;
import com.dgrf.empdev.DTO.PostDTO;
import com.dgrf.empdev.entities.EmpDetails;
import com.dgrf.empdev.entities.ProductInfo;
import com.org.empdev.EmpData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dgrfiv
 */
public class TestTemp {

    public static void main(String[] args) {
//        int responseCode;
//        
//        List<ProductInfo> productInfoList = new ArrayList<>();
//        
//        EmployeeDTO employeeDTO = new EmployeeDTO();
//        EmpDetails empDetails = new EmpDetails();
//        employeeDTO.setId(7);
//        employeeDTO.setPostId(3);
//        
//        
//        ProdInfoDAO prodInfoDAO = new ProdInfoDAO();
//        
//        productInfoList.add(prodInfoDAO.findProductInfo(5));
//        productInfoList.add(prodInfoDAO.findProductInfo(1));
//        productInfoList.add(prodInfoDAO.findProductInfo(4));
//        
//        empDetails.setProductInfoList(productInfoList);
//        
//        EmpData empData = new EmpData();
//        
//        responseCode = empData.addProductsToEmp(employeeDTO, productInfoList);
//        System.out.println(responseCode);
    }

    public void randomTest() {
        System.out.println("hello");

        EmpDataDAO empDataDAO = new EmpDataDAO();
        List<EmpDetails> empDetailsList = empDataDAO.getEmpsByProdId(1);

        for (int i = 0; i < empDetailsList.size(); i++) {
            System.out.println(empDetailsList.get(i).getEmpName());
        }

        ProdInfoDAO prodInfoDAO = new ProdInfoDAO();
        List<ProductInfo> productInfoList = prodInfoDAO.getProdsByEmpId(3);

        for (int j = 0; j < productInfoList.size(); j++) {
            System.out.println(productInfoList.get(j).getProductName());
        }
    }
}
