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
import org.dgrf.empdev.JPA.exceptions.IllegalOrphanException;
import org.dgrf.empdev.JPA.exceptions.NonexistentEntityException;
import org.dgrf.empdev.JPA.exceptions.PreexistingEntityException;
import org.dgrf.empdev.entities.EmpPost;

/**
 *
 * @author dgrfiv
 */
public class EmpPostJpaController implements Serializable {

    public EmpPostJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EmpPost empPost) throws PreexistingEntityException, Exception {
        if (empPost.getEmpDetailsList() == null) {
            empPost.setEmpDetailsList(new ArrayList<EmpDetails>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<EmpDetails> attachedEmpDetailsList = new ArrayList<EmpDetails>();
            for (EmpDetails empDetailsListEmpDetailsToAttach : empPost.getEmpDetailsList()) {
                empDetailsListEmpDetailsToAttach = em.getReference(empDetailsListEmpDetailsToAttach.getClass(), empDetailsListEmpDetailsToAttach.getEmpDetailsPK());
                attachedEmpDetailsList.add(empDetailsListEmpDetailsToAttach);
            }
            empPost.setEmpDetailsList(attachedEmpDetailsList);
            em.persist(empPost);
            for (EmpDetails empDetailsListEmpDetails : empPost.getEmpDetailsList()) {
                EmpPost oldEmpPostOfEmpDetailsListEmpDetails = empDetailsListEmpDetails.getEmpPost();
                empDetailsListEmpDetails.setEmpPost(empPost);
                empDetailsListEmpDetails = em.merge(empDetailsListEmpDetails);
                if (oldEmpPostOfEmpDetailsListEmpDetails != null) {
                    oldEmpPostOfEmpDetailsListEmpDetails.getEmpDetailsList().remove(empDetailsListEmpDetails);
                    oldEmpPostOfEmpDetailsListEmpDetails = em.merge(oldEmpPostOfEmpDetailsListEmpDetails);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpPost(empPost.getPostId()) != null) {
                throw new PreexistingEntityException("EmpPost " + empPost + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EmpPost empPost) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmpPost persistentEmpPost = em.find(EmpPost.class, empPost.getPostId());
            List<EmpDetails> empDetailsListOld = persistentEmpPost.getEmpDetailsList();
            List<EmpDetails> empDetailsListNew = empPost.getEmpDetailsList();
            List<String> illegalOrphanMessages = null;
            for (EmpDetails empDetailsListOldEmpDetails : empDetailsListOld) {
                if (!empDetailsListNew.contains(empDetailsListOldEmpDetails)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EmpDetails " + empDetailsListOldEmpDetails + " since its empPost field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<EmpDetails> attachedEmpDetailsListNew = new ArrayList<EmpDetails>();
            for (EmpDetails empDetailsListNewEmpDetailsToAttach : empDetailsListNew) {
                empDetailsListNewEmpDetailsToAttach = em.getReference(empDetailsListNewEmpDetailsToAttach.getClass(), empDetailsListNewEmpDetailsToAttach.getEmpDetailsPK());
                attachedEmpDetailsListNew.add(empDetailsListNewEmpDetailsToAttach);
            }
            empDetailsListNew = attachedEmpDetailsListNew;
            empPost.setEmpDetailsList(empDetailsListNew);
            empPost = em.merge(empPost);
            for (EmpDetails empDetailsListNewEmpDetails : empDetailsListNew) {
                if (!empDetailsListOld.contains(empDetailsListNewEmpDetails)) {
                    EmpPost oldEmpPostOfEmpDetailsListNewEmpDetails = empDetailsListNewEmpDetails.getEmpPost();
                    empDetailsListNewEmpDetails.setEmpPost(empPost);
                    empDetailsListNewEmpDetails = em.merge(empDetailsListNewEmpDetails);
                    if (oldEmpPostOfEmpDetailsListNewEmpDetails != null && !oldEmpPostOfEmpDetailsListNewEmpDetails.equals(empPost)) {
                        oldEmpPostOfEmpDetailsListNewEmpDetails.getEmpDetailsList().remove(empDetailsListNewEmpDetails);
                        oldEmpPostOfEmpDetailsListNewEmpDetails = em.merge(oldEmpPostOfEmpDetailsListNewEmpDetails);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = empPost.getPostId();
                if (findEmpPost(id) == null) {
                    throw new NonexistentEntityException("The empPost with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmpPost empPost;
            try {
                empPost = em.getReference(EmpPost.class, id);
                empPost.getPostId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empPost with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<EmpDetails> empDetailsListOrphanCheck = empPost.getEmpDetailsList();
            for (EmpDetails empDetailsListOrphanCheckEmpDetails : empDetailsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EmpPost (" + empPost + ") cannot be destroyed since the EmpDetails " + empDetailsListOrphanCheckEmpDetails + " in its empDetailsList field has a non-nullable empPost field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(empPost);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EmpPost> findEmpPostEntities() {
        return findEmpPostEntities(true, -1, -1);
    }

    public List<EmpPost> findEmpPostEntities(int maxResults, int firstResult) {
        return findEmpPostEntities(false, maxResults, firstResult);
    }

    private List<EmpPost> findEmpPostEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EmpPost.class));
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

    public EmpPost findEmpPost(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EmpPost.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpPostCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EmpPost> rt = cq.from(EmpPost.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
