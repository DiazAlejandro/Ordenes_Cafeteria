/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesoDato;

import accesoDato.exceptions.NonexistentEntityException;
import accesoDato.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.TipoIngrediente;
import modelo.Platillo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Ingrediente;

/**
 *
 * @author aleja
 */
public class IngredienteJpaController implements Serializable {

    public IngredienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ingrediente ingrediente) throws PreexistingEntityException, Exception {
        if (ingrediente.getPlatilloList() == null) {
            ingrediente.setPlatilloList(new ArrayList<Platillo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoIngrediente tipoing = ingrediente.getTipoing();
            if (tipoing != null) {
                tipoing = em.getReference(tipoing.getClass(), tipoing.getIdting());
                ingrediente.setTipoing(tipoing);
            }
            List<Platillo> attachedPlatilloList = new ArrayList<Platillo>();
            for (Platillo platilloListPlatilloToAttach : ingrediente.getPlatilloList()) {
                platilloListPlatilloToAttach = em.getReference(platilloListPlatilloToAttach.getClass(), platilloListPlatilloToAttach.getIdplatillo());
                attachedPlatilloList.add(platilloListPlatilloToAttach);
            }
            ingrediente.setPlatilloList(attachedPlatilloList);
            em.persist(ingrediente);
            if (tipoing != null) {
                tipoing.getIngredienteList().add(ingrediente);
                tipoing = em.merge(tipoing);
            }
            for (Platillo platilloListPlatillo : ingrediente.getPlatilloList()) {
                platilloListPlatillo.getIngredienteList().add(ingrediente);
                platilloListPlatillo = em.merge(platilloListPlatillo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIngrediente(ingrediente.getIding()) != null) {
                throw new PreexistingEntityException("Ingrediente " + ingrediente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ingrediente ingrediente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingrediente persistentIngrediente = em.find(Ingrediente.class, ingrediente.getIding());
            TipoIngrediente tipoingOld = persistentIngrediente.getTipoing();
            TipoIngrediente tipoingNew = ingrediente.getTipoing();
            List<Platillo> platilloListOld = persistentIngrediente.getPlatilloList();
            List<Platillo> platilloListNew = ingrediente.getPlatilloList();
            if (tipoingNew != null) {
                tipoingNew = em.getReference(tipoingNew.getClass(), tipoingNew.getIdting());
                ingrediente.setTipoing(tipoingNew);
            }
            List<Platillo> attachedPlatilloListNew = new ArrayList<Platillo>();
            for (Platillo platilloListNewPlatilloToAttach : platilloListNew) {
                platilloListNewPlatilloToAttach = em.getReference(platilloListNewPlatilloToAttach.getClass(), platilloListNewPlatilloToAttach.getIdplatillo());
                attachedPlatilloListNew.add(platilloListNewPlatilloToAttach);
            }
            platilloListNew = attachedPlatilloListNew;
            ingrediente.setPlatilloList(platilloListNew);
            ingrediente = em.merge(ingrediente);
            if (tipoingOld != null && !tipoingOld.equals(tipoingNew)) {
                tipoingOld.getIngredienteList().remove(ingrediente);
                tipoingOld = em.merge(tipoingOld);
            }
            if (tipoingNew != null && !tipoingNew.equals(tipoingOld)) {
                tipoingNew.getIngredienteList().add(ingrediente);
                tipoingNew = em.merge(tipoingNew);
            }
            for (Platillo platilloListOldPlatillo : platilloListOld) {
                if (!platilloListNew.contains(platilloListOldPlatillo)) {
                    platilloListOldPlatillo.getIngredienteList().remove(ingrediente);
                    platilloListOldPlatillo = em.merge(platilloListOldPlatillo);
                }
            }
            for (Platillo platilloListNewPlatillo : platilloListNew) {
                if (!platilloListOld.contains(platilloListNewPlatillo)) {
                    platilloListNewPlatillo.getIngredienteList().add(ingrediente);
                    platilloListNewPlatillo = em.merge(platilloListNewPlatillo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ingrediente.getIding();
                if (findIngrediente(id) == null) {
                    throw new NonexistentEntityException("The ingrediente with id " + id + " no longer exists.");
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
            Ingrediente ingrediente;
            try {
                ingrediente = em.getReference(Ingrediente.class, id);
                ingrediente.getIding();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ingrediente with id " + id + " no longer exists.", enfe);
            }
            TipoIngrediente tipoing = ingrediente.getTipoing();
            if (tipoing != null) {
                tipoing.getIngredienteList().remove(ingrediente);
                tipoing = em.merge(tipoing);
            }
            List<Platillo> platilloList = ingrediente.getPlatilloList();
            for (Platillo platilloListPlatillo : platilloList) {
                platilloListPlatillo.getIngredienteList().remove(ingrediente);
                platilloListPlatillo = em.merge(platilloListPlatillo);
            }
            em.remove(ingrediente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ingrediente> findIngredienteEntities() {
        return findIngredienteEntities(true, -1, -1);
    }

    public List<Ingrediente> findIngredienteEntities(int maxResults, int firstResult) {
        return findIngredienteEntities(false, maxResults, firstResult);
    }

    private List<Ingrediente> findIngredienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ingrediente.class));
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

    public Ingrediente findIngrediente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ingrediente.class, id);
        } finally {
            em.close();
        }
    }

    public int getIngredienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ingrediente> rt = cq.from(Ingrediente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
