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
import modelo.Cliente;
import modelo.Bebida;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Orden;
import modelo.Platillo;

/**
 *
 * @author aleja
 */
public class OrdenJpaController implements Serializable {

    public OrdenJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Orden orden) throws PreexistingEntityException, Exception {
        if (orden.getBebidaList() == null) {
            orden.setBebidaList(new ArrayList<Bebida>());
        }
        if (orden.getPlatilloList() == null) {
            orden.setPlatilloList(new ArrayList<Platillo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente idCliente = orden.getIdCliente();
            if (idCliente != null) {
                idCliente = em.getReference(idCliente.getClass(), idCliente.getIdCliente());
                orden.setIdCliente(idCliente);
            }
            List<Bebida> attachedBebidaList = new ArrayList<Bebida>();
            for (Bebida bebidaListBebidaToAttach : orden.getBebidaList()) {
                bebidaListBebidaToAttach = em.getReference(bebidaListBebidaToAttach.getClass(), bebidaListBebidaToAttach.getIdbebida());
                attachedBebidaList.add(bebidaListBebidaToAttach);
            }
            orden.setBebidaList(attachedBebidaList);
            List<Platillo> attachedPlatilloList = new ArrayList<Platillo>();
            for (Platillo platilloListPlatilloToAttach : orden.getPlatilloList()) {
                platilloListPlatilloToAttach = em.getReference(platilloListPlatilloToAttach.getClass(), platilloListPlatilloToAttach.getIdplatillo());
                attachedPlatilloList.add(platilloListPlatilloToAttach);
            }
            orden.setPlatilloList(attachedPlatilloList);
            em.persist(orden);
            if (idCliente != null) {
                idCliente.getOrdenList().add(orden);
                idCliente = em.merge(idCliente);
            }
            for (Bebida bebidaListBebida : orden.getBebidaList()) {
                bebidaListBebida.getOrdenList().add(orden);
                bebidaListBebida = em.merge(bebidaListBebida);
            }
            for (Platillo platilloListPlatillo : orden.getPlatilloList()) {
                platilloListPlatillo.getOrdenList().add(orden);
                platilloListPlatillo = em.merge(platilloListPlatillo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOrden(orden.getNumorden()) != null) {
                throw new PreexistingEntityException("Orden " + orden + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Orden orden) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orden persistentOrden = em.find(Orden.class, orden.getNumorden());
            Cliente idClienteOld = persistentOrden.getIdCliente();
            Cliente idClienteNew = orden.getIdCliente();
            List<Bebida> bebidaListOld = persistentOrden.getBebidaList();
            List<Bebida> bebidaListNew = orden.getBebidaList();
            List<Platillo> platilloListOld = persistentOrden.getPlatilloList();
            List<Platillo> platilloListNew = orden.getPlatilloList();
            if (idClienteNew != null) {
                idClienteNew = em.getReference(idClienteNew.getClass(), idClienteNew.getIdCliente());
                orden.setIdCliente(idClienteNew);
            }
            List<Bebida> attachedBebidaListNew = new ArrayList<Bebida>();
            for (Bebida bebidaListNewBebidaToAttach : bebidaListNew) {
                bebidaListNewBebidaToAttach = em.getReference(bebidaListNewBebidaToAttach.getClass(), bebidaListNewBebidaToAttach.getIdbebida());
                attachedBebidaListNew.add(bebidaListNewBebidaToAttach);
            }
            bebidaListNew = attachedBebidaListNew;
            orden.setBebidaList(bebidaListNew);
            List<Platillo> attachedPlatilloListNew = new ArrayList<Platillo>();
            for (Platillo platilloListNewPlatilloToAttach : platilloListNew) {
                platilloListNewPlatilloToAttach = em.getReference(platilloListNewPlatilloToAttach.getClass(), platilloListNewPlatilloToAttach.getIdplatillo());
                attachedPlatilloListNew.add(platilloListNewPlatilloToAttach);
            }
            platilloListNew = attachedPlatilloListNew;
            orden.setPlatilloList(platilloListNew);
            orden = em.merge(orden);
            if (idClienteOld != null && !idClienteOld.equals(idClienteNew)) {
                idClienteOld.getOrdenList().remove(orden);
                idClienteOld = em.merge(idClienteOld);
            }
            if (idClienteNew != null && !idClienteNew.equals(idClienteOld)) {
                idClienteNew.getOrdenList().add(orden);
                idClienteNew = em.merge(idClienteNew);
            }
            for (Bebida bebidaListOldBebida : bebidaListOld) {
                if (!bebidaListNew.contains(bebidaListOldBebida)) {
                    bebidaListOldBebida.getOrdenList().remove(orden);
                    bebidaListOldBebida = em.merge(bebidaListOldBebida);
                }
            }
            for (Bebida bebidaListNewBebida : bebidaListNew) {
                if (!bebidaListOld.contains(bebidaListNewBebida)) {
                    bebidaListNewBebida.getOrdenList().add(orden);
                    bebidaListNewBebida = em.merge(bebidaListNewBebida);
                }
            }
            for (Platillo platilloListOldPlatillo : platilloListOld) {
                if (!platilloListNew.contains(platilloListOldPlatillo)) {
                    platilloListOldPlatillo.getOrdenList().remove(orden);
                    platilloListOldPlatillo = em.merge(platilloListOldPlatillo);
                }
            }
            for (Platillo platilloListNewPlatillo : platilloListNew) {
                if (!platilloListOld.contains(platilloListNewPlatillo)) {
                    platilloListNewPlatillo.getOrdenList().add(orden);
                    platilloListNewPlatillo = em.merge(platilloListNewPlatillo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = orden.getNumorden();
                if (findOrden(id) == null) {
                    throw new NonexistentEntityException("The orden with id " + id + " no longer exists.");
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
            Orden orden;
            try {
                orden = em.getReference(Orden.class, id);
                orden.getNumorden();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orden with id " + id + " no longer exists.", enfe);
            }
            Cliente idCliente = orden.getIdCliente();
            if (idCliente != null) {
                idCliente.getOrdenList().remove(orden);
                idCliente = em.merge(idCliente);
            }
            List<Bebida> bebidaList = orden.getBebidaList();
            for (Bebida bebidaListBebida : bebidaList) {
                bebidaListBebida.getOrdenList().remove(orden);
                bebidaListBebida = em.merge(bebidaListBebida);
            }
            List<Platillo> platilloList = orden.getPlatilloList();
            for (Platillo platilloListPlatillo : platilloList) {
                platilloListPlatillo.getOrdenList().remove(orden);
                platilloListPlatillo = em.merge(platilloListPlatillo);
            }
            em.remove(orden);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Orden> findOrdenEntities() {
        return findOrdenEntities(true, -1, -1);
    }

    public List<Orden> findOrdenEntities(int maxResults, int firstResult) {
        return findOrdenEntities(false, maxResults, firstResult);
    }

    private List<Orden> findOrdenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orden.class));
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

    public Orden findOrden(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orden.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orden> rt = cq.from(Orden.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
