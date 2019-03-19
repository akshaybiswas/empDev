/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.org.empdev;

import com.dgrf.empdev.DAO.EmpDataDAO;
import com.dgrf.empdev.DAO.ProdInfoDAO;
import com.dgrf.empdev.DTO.EmployeeDTO;
import com.dgrf.empdev.DTO.ProductDTO;
import com.dgrf.empdev.entities.EmpDetails;
import com.dgrf.empdev.entities.ProductInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dgrfiv
 */
public class EmpData {

    public static void main(String[] args) {
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

    public List<EmployeeDTO> getEmpByPost(int postId) {
        EmpDataDAO empDataDAO = new EmpDataDAO();

        List<EmpDetails> empDetailsList = empDataDAO.getEmpsByPostId(postId);
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        for (int i = 0; i < empDetailsList.size(); i++) {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setPostId(empDetailsList.get(i).getEmpDetailsPK().getPostId());
            employeeDTO.setName(empDetailsList.get(i).getEmpName());
            employeeDTO.setId(empDetailsList.get(i).getEmpDetailsPK().getEmpId());
            employeeDTO.setPostName(empDetailsList.get(i).getEmpPost().getPostName());
            employeeDTO.setJoined(empDetailsList.get(i).getEmpJoined());
            employeeDTO.setExp(empDetailsList.get(i).getEmpExp());
            employeeDTO.setSalary(empDetailsList.get(i).getEmpSalary());
            employeeDTOList.add(employeeDTO);
        }
        return employeeDTOList;
    }

    public List<ProductDTO> getAllProducts() {

        ProdInfoDAO prodInfoDAO = new ProdInfoDAO();
        List<ProductInfo> productInfo = prodInfoDAO.findProductInfoEntities();

        List<ProductDTO> productDTOList = new ArrayList<>();

        for (int i = 0; i < productInfo.size(); i++) {
            ProductDTO productDTO = new ProductDTO();

            productDTO.setId(productInfo.get(i).getProductId());
            productDTO.setName(productInfo.get(i).getProductName());
            productDTO.setPrice(productInfo.get(i).getProductPrice());

            productDTOList.add(productDTO);
        }

        return productDTOList;

    }

    public List<EmployeeDTO> getEmpByProd(int prodId) {
        SortBy sortBy = new SortBy();
        List<EmpDetails> empDetailsList = sortBy.getEmpByProduct(prodId);

        List<EmployeeDTO> employeeDTOList = new ArrayList<>();

        for (int i = 0; i < empDetailsList.size(); i++) {

            EmployeeDTO employeeDTO = new EmployeeDTO();

            employeeDTO.setName(empDetailsList.get(i).getEmpName());
            employeeDTO.setId(empDetailsList.get(i).getEmpDetailsPK().getEmpId());
            employeeDTO.setPostName(empDetailsList.get(i).getEmpPost().getPostName());

            employeeDTOList.add(employeeDTO);
        }
        return employeeDTOList;

    }

    public List<ProductDTO> getProdByEmp(int empId) {
        SortBy sortBy = new SortBy();
        List<ProductInfo> productInfoList = sortBy.getProdByEmployee(empId);

        List<ProductDTO> productDTOList = new ArrayList<>();
        
        for(int i = 0; i<productInfoList.size();i++) {
            ProductDTO productDTO = new ProductDTO();
            
            productDTO.setId(productInfoList.get(i).getProductId());
            productDTO.setName(productInfoList.get(i).getProductName());
            productDTO.setPrice(productInfoList.get(i).getProductPrice());
            
            productDTOList.add(productDTO);
        }
        return productDTOList;

    }

}
