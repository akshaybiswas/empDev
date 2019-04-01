/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emp.bl;

import org.dgrf.empdev.DAO.EmpDataDAO;
import org.dgrf.empdev.DAO.EmpPostsDAO;
import org.dgrf.empdev.DAO.ProdInfoDAO;
import org.dgrf.empdev.DTO.EmployeeDTO;
import org.dgrf.empdev.DTO.PostDTO;
import org.dgrf.empdev.DTO.ProductDTO;
import org.dgrf.empdev.DTO.ResponseCode;
import org.dgrf.empdev.JPA.exceptions.PreexistingEntityException;
import org.dgrf.empdev.entities.EmpDetails;
import org.dgrf.empdev.entities.EmpDetailsPK;
import org.dgrf.empdev.entities.EmpPost;
import org.dgrf.empdev.entities.ProductInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dgrf.empdev.JPA.exceptions.IllegalOrphanException;
import org.dgrf.empdev.JPA.exceptions.NonexistentEntityException;

/**
 *
 * @author dgrfiv
 */
public class EmployeeDataService {

    public PostDTO getPostDTO(int postId) {
        EmpPostsDAO empPostsDAO = new EmpPostsDAO();
        EmpPost empPost = empPostsDAO.findEmpPost(postId);
        PostDTO postDTO = new PostDTO();

        postDTO.setId(empPost.getPostId());
        postDTO.setName(empPost.getPostName());
        postDTO.setGp(empPost.getPostGp());

        return postDTO;
    }

    public EmployeeDTO getEmployeeDTO(EmployeeDTO employeeDTO) {
        EmpDataDAO empDataDAO = new EmpDataDAO();
        EmpDetailsPK empDetailsPK = new EmpDetailsPK();

        empDetailsPK.setEmpId(employeeDTO.getId());
        empDetailsPK.setPostId(employeeDTO.getPostId());

        EmpDetails empDetails = empDataDAO.findEmpDetails(empDetailsPK);

//        List<ProductDTO> productDTOList = getProdByEmp(employeeDTO.getId());
//        Integer productIdList[]=new Integer[productDTOList.size()];
//        for(int i = 0; i<productDTOList.size(); i++) {
//            productIdList[i] = productDTOList.get(i).getId();
//        }
        List<ProductInfo> productInfoList = empDetails.getProductInfoList();
        Integer productIdList[] = new Integer[productInfoList.size()];
        for (int i = 0; i < productInfoList.size(); i++) {
            productIdList[i] = productInfoList.get(i).getProductId();
        }

        employeeDTO.setName(empDetails.getEmpName());
        employeeDTO.setProductIdList(productIdList);

        return employeeDTO;
    }

    public ProductDTO getProductDTO(int prodId) {
        ProdInfoDAO prodInfoDAO = new ProdInfoDAO();
        ProductInfo productInfo = prodInfoDAO.findProductInfo(prodId);
        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(productInfo.getProductId());
        productDTO.setName(productInfo.getProductName());
        productDTO.setPrice(productInfo.getProductPrice());

        return productDTO;
    }

    public List<EmployeeDTO> getAllEmployeeDTO() {
        EmpDataDAO empDataDAO = new EmpDataDAO();
        List<EmpDetails> empDetailsList = empDataDAO.findEmpDetailsEntities();
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();

        for (int i = 0; i < empDetailsList.size(); i++) {
            EmployeeDTO employeeDTO = new EmployeeDTO();

            employeeDTO.setId(empDetailsList.get(i).getEmpDetailsPK().getEmpId());
            employeeDTO.setPostId(empDetailsList.get(i).getEmpDetailsPK().getPostId());
            employeeDTO.setName(empDetailsList.get(i).getEmpName());
            employeeDTO.setJoined(empDetailsList.get(i).getEmpJoined());
            employeeDTO.setExp(empDetailsList.get(i).getEmpExp());

            employeeDTOList.add(employeeDTO);
        }
        return employeeDTOList;
    }

    public List<EmployeeDTO> getAllEmployeeDTO(int first,int pageSize) {
        EmpDataDAO empDataDAO = new EmpDataDAO();
        List<EmpDetails> empDetailsList = empDataDAO.findEmpDetailsEntities(pageSize, first);
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();

        for (int i = 0; i < empDetailsList.size(); i++) {
            EmployeeDTO employeeDTO = new EmployeeDTO();

            employeeDTO.setId(empDetailsList.get(i).getEmpDetailsPK().getEmpId());
            employeeDTO.setPostId(empDetailsList.get(i).getEmpDetailsPK().getPostId());
            employeeDTO.setName(empDetailsList.get(i).getEmpName());
            employeeDTO.setJoined(empDetailsList.get(i).getEmpJoined());
            employeeDTO.setExp(empDetailsList.get(i).getEmpExp());

            employeeDTOList.add(employeeDTO);
        }
        return employeeDTOList;
    }

    public List<PostDTO> getAllPostDTO() {
        EmpPostsDAO empPostsDAO = new EmpPostsDAO();

        List<EmpPost> empPostList = empPostsDAO.findEmpPostEntities();
        List<PostDTO> postDTOList = new ArrayList<>();

        for (int i = 0; i < empPostList.size(); i++) {
            PostDTO postDTO = new PostDTO();

            postDTO.setId(empPostList.get(i).getPostId());
            postDTO.setName(empPostList.get(i).getPostName());
            postDTO.setGp(empPostList.get(i).getPostGp());

            postDTOList.add(postDTO);
        }
        return postDTOList;
    }

    public List<ProductDTO> getAllProductDTO() {

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
            List<ProductInfo> productInfoList = new ArrayList<>();
            Integer[] productIdArray = employeeDTO.getProductIdList();

            for (int i = 0; i < productIdArray.length; i++) {
                productInfoList.add(prodInfoDAO.findProductInfo(productIdArray[i]));
            }

            empDetails.setProductInfoList(productInfoList);

            empDataDAO.create(empDetails);
            responseCode = ResponseCode.SUCCESS;

        } catch (PreexistingEntityException ex) {
            Logger.getLogger(EmployeeDataService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = ResponseCode.ALREADY_EXISISTS;
        } catch (Exception ex) {
            Logger.getLogger(EmployeeDataService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = ResponseCode.CONTACT_ADMIN;
        }
        return responseCode;
    }

    public int updateEmployee(EmployeeDTO employeeDTO) {
        int responseCode;

        EmpDataDAO empDataDAO = new EmpDataDAO();
        EmpDetailsPK empDetailsPK = new EmpDetailsPK();
        EmpPostsDAO empPostsDAO = new EmpPostsDAO();

        empDetailsPK.setEmpId(employeeDTO.getId());
        empDetailsPK.setPostId(employeeDTO.getPostId());

        EmpDetails empDetails = empDataDAO.findEmpDetails(empDetailsPK);

        EmpPost empPost = empPostsDAO.findEmpPost(employeeDTO.getPostId());

        empDetails.setEmpDetailsPK(empDetailsPK);
        empDetails.setEmpPost(empPost);
        empDetails.setEmpName(employeeDTO.getName());

        ProdInfoDAO prodInfoDAO = new ProdInfoDAO();
        List<ProductInfo> productInfoList = new ArrayList<>();
        Integer[] productIdArray = employeeDTO.getProductIdList();

        for (int i = 0; i < productIdArray.length; i++) {
            productInfoList.add(prodInfoDAO.findProductInfo(productIdArray[i]));
        }

        empDetails.setProductInfoList(productInfoList);

        try {
            empDataDAO.edit(empDetails);
            responseCode = ResponseCode.SUCCESS;

        } catch (Exception ex) {
            Logger.getLogger(EmployeeDataService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = ResponseCode.CONTACT_ADMIN;
        }
        return responseCode;
    }

    public int deleteEmployee(EmployeeDTO employeeDTO) {
        int responseCode;

        EmpDataDAO empDataDAO = new EmpDataDAO();
        EmpDetailsPK empDetailsPK = new EmpDetailsPK();

        empDetailsPK.setEmpId(employeeDTO.getId());
        empDetailsPK.setPostId(employeeDTO.getPostId());

        EmpDetails empDetails = empDataDAO.findEmpDetails(empDetailsPK);

        try {
            empDataDAO.destroy(empDetails.getEmpDetailsPK());
            responseCode = ResponseCode.SUCCESS;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(EmployeeDataService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = ResponseCode.INVALID;
        } catch (Exception ex) {
            Logger.getLogger(EmployeeDataService.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EmployeeDataService.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EmployeeDataService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = ResponseCode.ALREADY_EXISISTS;

        } catch (Exception ex) {
            Logger.getLogger(EmployeeDataService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = ResponseCode.CONTACT_ADMIN;
        }
        return responseCode;
    }

    public int updatePost(PostDTO postDTO) {
        int responseCode;

        EmpPostsDAO empPostsDAO = new EmpPostsDAO();
        EmpPost empPost = empPostsDAO.findEmpPost(postDTO.getId());

        empPost.setPostName(postDTO.getName());
        empPost.setPostGp(postDTO.getGp());

        try {
            empPostsDAO.edit(empPost);
            responseCode = ResponseCode.SUCCESS;

        } catch (Exception ex) {
            Logger.getLogger(EmployeeDataService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = ResponseCode.CONTACT_ADMIN;
        }
        return responseCode;
    }

    public int deletePost(PostDTO postDTO) {
        int responseCode;

        EmpPostsDAO empPostsDAO = new EmpPostsDAO();
        EmpPost empPost = empPostsDAO.findEmpPost(postDTO.getId());

        try {
            empPostsDAO.destroy(empPost.getPostId());
            responseCode = ResponseCode.SUCCESS;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(EmployeeDataService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = ResponseCode.INVALID;
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(EmployeeDataService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = ResponseCode.ILLEGAL_ORPHAN;
        }
        return responseCode;
    }

    public int addProduct(ProductDTO productDTO) {
        int responseCode;

        ProdInfoDAO prodInfoDAO = new ProdInfoDAO();
        ProductInfo productInfo = new ProductInfo();

        productInfo.setProductId(productDTO.getId());
        productInfo.setProductName(productDTO.getName());
        productInfo.setProductPrice(productDTO.getPrice());

        try {
            prodInfoDAO.create(productInfo);
            responseCode = ResponseCode.SUCCESS;
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(EmployeeDataService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = ResponseCode.ALREADY_EXISISTS;
        } catch (Exception ex) {
            Logger.getLogger(EmployeeDataService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = ResponseCode.CONTACT_ADMIN;
        }

        return responseCode;
    }

    public int updateProduct(ProductDTO productDTO) {
        int responseCode;

        ProdInfoDAO prodInfoDAO = new ProdInfoDAO();
        ProductInfo productInfo = prodInfoDAO.findProductInfo(productDTO.getId());

        productInfo.setProductName(productDTO.getName());
        productInfo.setProductPrice(productDTO.getPrice());

        try {
            prodInfoDAO.edit(productInfo);
            responseCode = ResponseCode.SUCCESS;

        } catch (Exception ex) {
            Logger.getLogger(EmployeeDataService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = ResponseCode.CONTACT_ADMIN;
        }
        return responseCode;
    }

    public int deleteProduct(ProductDTO productDTO) {
        int responseCode;

        ProdInfoDAO prodInfoDAO = new ProdInfoDAO();
        ProductInfo productInfo = prodInfoDAO.findProductInfo(productDTO.getId());

        try {
            prodInfoDAO.destroy(productInfo.getProductId());
            responseCode = ResponseCode.SUCCESS;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(EmployeeDataService.class.getName()).log(Level.SEVERE, null, ex);
            responseCode = ResponseCode.INVALID;
        }
        return responseCode;
    }

}
