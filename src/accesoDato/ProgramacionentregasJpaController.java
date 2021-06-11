/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesoDato;

import accesoDato.exceptions.NonexistentEntityException;
import accesoDato.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Programacionentregas;

/**
 *
 * @author aleja
 */
public class ProgramacionentregasJpaController implements Serializable {

    public ProgramacionentregasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Programacionentregas programacionentregas) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(programacionentregas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProgramacionentregas(programacionentregas.getIdentrega()) != null) {
                throw new PreexistingEntityException("Programacionentregas " + programacionentregas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Programacionentregas programacionentregas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            programacionentregas = em.merge(programacionentregas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = programacionentregas.getIdentrega();
                if (findProgramacionentregas(id) == null) {
                    throw new NonexistentEntityException("The programacionentregas with id " + id + " no longer exists.");
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
            Programacionentregas programacionentregas;
            try {
                programacionentregas = em.getReference(Programacionentregas.class, id);
                programacionentregas.getIdentrega();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The programacionentregas with id " + id + " no longer exists.", enfe);
            }
            em.remove(programacionentregas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Programacionentregas> findProgramacionentregasEntities() {
        return findProgramacionentregasEntities(true, -1, -1);
    }

    public List<Programacionentregas> findProgramacionentregasEntities(int maxResults, int firstResult) {
        return findProgramacionentregasEntities(false, maxResults, firstResult);
    }

    private List<Programacionentregas> findProgramacionentregasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Programacionentregas.class));
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

    public Programacionentregas findProgramacionentregas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Programacionentregas.class, id);
        } finally {
            em.close();
        }
    }

    public int getProgramacionentregasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Programacionentregas> rt = cq.from(Programacionentregas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
