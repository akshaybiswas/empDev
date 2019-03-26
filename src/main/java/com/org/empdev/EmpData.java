/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.org.empdev;

import com.dgrf.empdev.DAO.EmpDataDAO;
import com.dgrf.empdev.DAO.EmpPostsDAO;
import com.dgrf.empdev.DAO.ProdInfoDAO;
import com.dgrf.empdev.DTO.EmployeeDTO;
import com.dgrf.empdev.DTO.PostDTO;
import com.dgrf.empdev.DTO.ProductDTO;
import com.dgrf.empdev.DTO.ResponseCode;
import com.dgrf.empdev.JPA.exceptions.PreexistingEntityException;
import com.dgrf.empdev.entities.EmpDetails;
import com.dgrf.empdev.entities.EmpDetailsPK;
import com.dgrf.empdev.entities.EmpPost;
import com.dgrf.empdev.entities.ProductInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dgrfiv
 */
public class EmpData {
    
    public List<PostDTO> getPostDTO() {
        EmpPostsDAO empPostsDAO = new EmpPostsDAO();
        
        List<EmpPost> empPostList = empPostsDAO.findEmpPostEntities();
        List<PostDTO> postDTOList = new ArrayList<>();
        
        for(int i = 0; i<empPostList.size(); i++){
            PostDTO postDTO = new PostDTO();
            
            postDTO.setId(empPostList.get(i).getPostId());
            postDTO.setName(empPostList.get(i).getPostName());
            postDTO.setGp(empPostList.get(i).getPostGp());
            
            postDTOList.add(postDTO);
        }
        return postDTOList;
    }
    
    public List<ProductDTO> getProductDTO() {
        ProdInfoDAO prodInfoDAO = new ProdInfoDAO();
        
        List<ProductInfo> productInfoList = prodInfoDAO.findProductInfoEntities();
        List<ProductDTO> productDTOList = new ArrayList<>();
        
        for(int i = 0; i<productInfoList.size(); i++){
            ProductDTO productDTO = new ProductDTO();
            
            productDTO.setId(productInfoList.get(i).getProductId());
            productDTO.setName(productInfoList.get(i).getProductName());
            productDTO.setPrice(productInfoList.get(i).getProductPrice());
            
            productDTOList.add(productDTO);
        }
        return productDTOList;
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

        for (int i = 0; i < productInfoList.size(); i++) {
            ProductDTO productDTO = new ProductDTO();

            productDTO.setId(productInfoList.get(i).getProductId());
            productDTO.setName(productInfoList.get(i).getProductName());
            productDTO.setPrice(productInfoList.get(i).getProductPrice());

            productDTOList.add(productDTO);
        }
        return productDTOList;

    }

    public int addEmployee(EmployeeDTO employeeDTO) {
        int responseCode;

        try {

            EmpDataDAO empDataDAO = new EmpDataDAO();
            EmpDetails empDetails = new EmpDetails();
            EmpDetailsPK empDetailsPK = new EmpDetailsPK();
            EmpPost empPost = new EmpPost(employeeDTO.getPostId());
            Date empJoined = new Date();

            empDetailsPK.setEmpId(employeeDTO.getId());
            empDetailsPK.setPostId(employeeDTO.getPostId());

            empDetails.setEmpDetailsPK(empDetailsPK);
            empDetails.setEmpExp(0);
            empDetails.setEmpJoined(empJoined);
            empDetails.setEmpName(employeeDTO.getName());
            empDetails.setEmpPost(empPost);
            
            ProdInfoDAO prodInfoDAO = new ProdInfoDAO();
            List<Integer> productIds = Arrays.asList(employeeDTO.getProductIdList());
            
            for(int i = 0; i<productIds.size(); i++) {
                prodInfoDAO.findProductInfo(i);
            }
            
            
            
            empDataDAO.create(empDetails);
            responseCode = ResponseCode.SUCCESS;

        } catch (PreexistingEntityException ex) {
            Logger.getLogger(EmpData.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = ResponseCode.ALREADY_EXISISTS;
        } catch (Exception ex) {
            Logger.getLogger(EmpData.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = ResponseCode.CONTACT_ADMIN;
        }
        return responseCode;
    }
    
    public int addPost(PostDTO postDTO) {
        int responseCode;
        
        EmpPostsDAO empPostsDAO = new EmpPostsDAO();
        EmpPost empPost = new EmpPost();
        
        empPost.setPostId(postDTO.getId());
        empPost.setPostName(postDTO.getName());
        empPost.setPostGp(postDTO.getGp());
        
        try {
            empPostsDAO.create(empPost);
            responseCode = ResponseCode.SUCCESS;
            
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(EmpData.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = ResponseCode.ALREADY_EXISISTS;
            
        } catch (Exception ex) {
            Logger.getLogger(EmpData.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = ResponseCode.CONTACT_ADMIN;
        }
        return responseCode;
    }
    
    public int addProductsToEmp(EmployeeDTO employeeDTO, List<ProductInfo> productInfoList) {
        int responseCode;
        
        EmpDataDAO empDataDAO = new EmpDataDAO();
        EmpDetailsPK empDetailsPK = new EmpDetailsPK();
        
        empDetailsPK.setEmpId(employeeDTO.getId());
        empDetailsPK.setPostId(employeeDTO.getPostId());
        
        EmpDetails empDetails = empDataDAO.findEmpDetails(empDetailsPK);
        
        empDetails.setProductInfoList(productInfoList);
        
        try {
            empDataDAO.edit(empDetails);
            responseCode = ResponseCode.SUCCESS;

        } catch (Exception ex) {
            Logger.getLogger(EmpData.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = ResponseCode.CONTACT_ADMIN;
        }
        return responseCode;
    }
}
