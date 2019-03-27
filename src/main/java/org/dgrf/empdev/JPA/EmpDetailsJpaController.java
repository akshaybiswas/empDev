/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.empdev.JPA;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.dgrf.empdev.entities.EmpPost;
import org.dgrf.empdev.entities.ProductInfo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.dgrf.empdev.JPA.exceptions.NonexistentEntityException;
import org.dgrf.empdev.JPA.exceptions.PreexistingEntityException;
import org.dgrf.empdev.entities.EmpDetails;
import org.dgrf.empdev.entities.EmpDetailsPK;

/**
 *
 * @author dgrfiv
 */
public class EmpDetailsJpaController implements Serializable {

    public EmpDetailsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EmpDetails empDetails) throws PreexistingEntityException, Exception {
        if (empDetails.getEmpDetailsPK() == null) {
            empDetails.setEmpDetailsPK(new EmpDetailsPK());
        }
        if (empDetails.getProductInfoList() == null) {
            empDetails.setProductInfoList(new ArrayList<ProductInfo>());
        }
        empDetails.getEmpDetailsPK().setPostId(empDetails.getEmpPost().getPostId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmpPost empPost = empDetails.getEmpPost();
            if (empPost != null) {
                empPost = em.getReference(empPost.getClass(), empPost.getPostId());
                empDetails.setEmpPost(empPost);
            }
            List<ProductInfo> attachedProductInfoList = new ArrayList<ProductInfo>();
            for (ProductInfo productInfoListProductInfoToAttach : empDetails.getProductInfoList()) {
                productInfoListProductInfoToAttach = em.getReference(productInfoListProductInfoToAttach.getClass(), productInfoListProductInfoToAttach.getProductId());
                attachedProductInfoList.add(productInfoListProductInfoToAttach);
            }
            empDetails.setProductInfoList(attachedProductInfoList);
            em.persist(empDetails);
            if (empPost != null) {
                empPost.getEmpDetailsList().add(empDetails);
                empPost = em.merge(empPost);
            }
            for (ProductInfo productInfoListProductInfo : empDetails.getProductInfoList()) {
                productInfoListProductInfo.getEmpDetailsList().add(empDetails);
                productInfoListProductInfo = em.merge(productInfoListProductInfo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpDetails(empDetails.getEmpDetailsPK()) != null) {
                throw new PreexistingEntityException("EmpDetails " + empDetails + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EmpDetails empDetails) throws NonexistentEntityException, Exception {
        empDetails.getEmpDetailsPK().setPostId(empDetails.getEmpPost().getPostId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmpDetails persistentEmpDetails = em.find(EmpDetails.class, empDetails.getEmpDetailsPK());
            EmpPost empPostOld = persistentEmpDetails.getEmpPost();
            EmpPost empPostNew = empDetails.getEmpPost();
            List<ProductInfo> productInfoListOld = persistentEmpDetails.getProductInfoList();
            List<ProductInfo> productInfoListNew = empDetails.getProductInfoList();
            if (empPostNew != null) {
                empPostNew = em.getReference(empPostNew.getClass(), empPostNew.getPostId());
                empDetails.setEmpPost(empPostNew);
            }
            List<ProductInfo> attachedProductInfoListNew = new ArrayList<ProductInfo>();
            for (ProductInfo productInfoListNewProductInfoToAttach : productInfoListNew) {
                productInfoListNewProductInfoToAttach = em.getReference(productInfoListNewProductInfoToAttach.getClass(), productInfoListNewProductInfoToAttach.getProductId());
                attachedProductInfoListNew.add(productInfoListNewProductInfoToAttach);
            }
            productInfoListNew = attachedProductInfoListNew;
            empDetails.setProductInfoList(productInfoListNew);
            empDetails = em.merge(empDetails);
            if (empPostOld != null && !empPostOld.equals(empPostNew)) {
                empPostOld.getEmpDetailsList().remove(empDetails);
                empPostOld = em.merge(empPostOld);
            }
            if (empPostNew != null && !empPostNew.equals(empPostOld)) {
                empPostNew.getEmpDetailsList().add(empDetails);
                empPostNew = em.merge(empPostNew);
            }
            for (ProductInfo productInfoListOldProductInfo : productInfoListOld) {
                if (!productInfoListNew.contains(productInfoListOldProductInfo)) {
                    productInfoListOldProductInfo.getEmpDetailsList().remove(empDetails);
                    productInfoListOldProductInfo = em.merge(productInfoListOldProductInfo);
                }
            }
            for (ProductInfo productInfoListNewProductInfo : productInfoListNew) {
                if (!productInfoListOld.contains(productInfoListNewProductInfo)) {
                    productInfoListNewProductInfo.getEmpDetailsList().add(empDetails);
                    productInfoListNewProductInfo = em.merge(productInfoListNewProductInfo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EmpDetailsPK id = empDetails.getEmpDetailsPK();
                if (findEmpDetails(id) == null) {
                    throw new NonexistentEntityException("The empDetails with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EmpDetailsPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmpDetails empDetails;
            try {
                empDetails = em.getReference(EmpDetails.class, id);
                empDetails.getEmpDetailsPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empDetails with id " + id + " no longer exists.", enfe);
            }
            EmpPost empPost = empDetails.getEmpPost();
            if (empPost != null) {
                empPost.getEmpDetailsList().remove(empDetails);
                empPost = em.merge(empPost);
            }
            List<ProductInfo> productInfoList = empDetails.getProductInfoList();
            for (ProductInfo productInfoListProductInfo : productInfoList) {
                productInfoListProductInfo.getEmpDetailsList().remove(empDetails);
                productInfoListProductInfo = em.merge(productInfoListProductInfo);
            }
            em.remove(empDetails);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EmpDetails> findEmpDetailsEntities() {
        return findEmpDetailsEntities(true, -1, -1);
    }

    public List<EmpDetails> findEmpDetailsEntities(int maxResults, int firstResult) {
        return findEmpDetailsEntities(false, maxResults, firstResult);
    }

    private List<EmpDetails> findEmpDetailsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EmpDetails.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public EmpDetails findEmpDetails(EmpDetailsPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EmpDetails.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpDetailsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EmpDetails> rt = cq.from(EmpDetails.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
