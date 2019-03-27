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
import org.dgrf.empdev.entities.EmpDetails;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.dgrf.empdev.JPA.exceptions.NonexistentEntityException;
import org.dgrf.empdev.JPA.exceptions.PreexistingEntityException;
import org.dgrf.empdev.entities.ProductInfo;

/**
 *
 * @author dgrfiv
 */
public class ProductInfoJpaController implements Serializable {

    public ProductInfoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProductInfo productInfo) throws PreexistingEntityException, Exception {
        if (productInfo.getEmpDetailsList() == null) {
            productInfo.setEmpDetailsList(new ArrayList<EmpDetails>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<EmpDetails> attachedEmpDetailsList = new ArrayList<EmpDetails>();
            for (EmpDetails empDetailsListEmpDetailsToAttach : productInfo.getEmpDetailsList()) {
                empDetailsListEmpDetailsToAttach = em.getReference(empDetailsListEmpDetailsToAttach.getClass(), empDetailsListEmpDetailsToAttach.getEmpDetailsPK());
                attachedEmpDetailsList.add(empDetailsListEmpDetailsToAttach);
            }
            productInfo.setEmpDetailsList(attachedEmpDetailsList);
            em.persist(productInfo);
            for (EmpDetails empDetailsListEmpDetails : productInfo.getEmpDetailsList()) {
                empDetailsListEmpDetails.getProductInfoList().add(productInfo);
                empDetailsListEmpDetails = em.merge(empDetailsListEmpDetails);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProductInfo(productInfo.getProductId()) != null) {
                throw new PreexistingEntityException("ProductInfo " + productInfo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProductInfo productInfo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProductInfo persistentProductInfo = em.find(ProductInfo.class, productInfo.getProductId());
            List<EmpDetails> empDetailsListOld = persistentProductInfo.getEmpDetailsList();
            List<EmpDetails> empDetailsListNew = productInfo.getEmpDetailsList();
            List<EmpDetails> attachedEmpDetailsListNew = new ArrayList<EmpDetails>();
            for (EmpDetails empDetailsListNewEmpDetailsToAttach : empDetailsListNew) {
                empDetailsListNewEmpDetailsToAttach = em.getReference(empDetailsListNewEmpDetailsToAttach.getClass(), empDetailsListNewEmpDetailsToAttach.getEmpDetailsPK());
                attachedEmpDetailsListNew.add(empDetailsListNewEmpDetailsToAttach);
            }
            empDetailsListNew = attachedEmpDetailsListNew;
            productInfo.setEmpDetailsList(empDetailsListNew);
            productInfo = em.merge(productInfo);
            for (EmpDetails empDetailsListOldEmpDetails : empDetailsListOld) {
                if (!empDetailsListNew.contains(empDetailsListOldEmpDetails)) {
                    empDetailsListOldEmpDetails.getProductInfoList().remove(productInfo);
                    empDetailsListOldEmpDetails = em.merge(empDetailsListOldEmpDetails);
                }
            }
            for (EmpDetails empDetailsListNewEmpDetails : empDetailsListNew) {
                if (!empDetailsListOld.contains(empDetailsListNewEmpDetails)) {
                    empDetailsListNewEmpDetails.getProductInfoList().add(productInfo);
                    empDetailsListNewEmpDetails = em.merge(empDetailsListNewEmpDetails);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = productInfo.getProductId();
                if (findProductInfo(id) == null) {
                    throw new NonexistentEntityException("The productInfo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProductInfo productInfo;
            try {
                productInfo = em.getReference(ProductInfo.class, id);
                productInfo.getProductId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productInfo with id " + id + " no longer exists.", enfe);
            }
            List<EmpDetails> empDetailsList = productInfo.getEmpDetailsList();
            for (EmpDetails empDetailsListEmpDetails : empDetailsList) {
                empDetailsListEmpDetails.getProductInfoList().remove(productInfo);
                empDetailsListEmpDetails = em.merge(empDetailsListEmpDetails);
            }
            em.remove(productInfo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProductInfo> findProductInfoEntities() {
        return findProductInfoEntities(true, -1, -1);
    }

    public List<ProductInfo> findProductInfoEntities(int maxResults, int firstResult) {
        return findProductInfoEntities(false, maxResults, firstResult);
    }

    private List<ProductInfo> findProductInfoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProductInfo.class));
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

    public ProductInfo findProductInfo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProductInfo.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductInfoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProductInfo> rt = cq.from(ProductInfo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
