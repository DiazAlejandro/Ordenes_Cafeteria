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
import modelo.Menu;
import modelo.Orden;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Bebida;

/**
 *
 * @author aleja
 */
public class BebidaJpaController implements Serializable {

    public BebidaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bebida bebida) throws PreexistingEntityException, Exception {
        if (bebida.getOrdenList() == null) {
            bebida.setOrdenList(new ArrayList<Orden>());
        }
        if (bebida.getMenuList() == null) {
            bebida.setMenuList(new ArrayList<Menu>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Menu nummenu = bebida.getNummenu();
            if (nummenu != null) {
                nummenu = em.getReference(nummenu.getClass(), nummenu.getNummenu());
                bebida.setNummenu(nummenu);
            }
            List<Orden> attachedOrdenList = new ArrayList<Orden>();
            for (Orden ordenListOrdenToAttach : bebida.getOrdenList()) {
                ordenListOrdenToAttach = em.getReference(ordenListOrdenToAttach.getClass(), ordenListOrdenToAttach.getNumorden());
                attachedOrdenList.add(ordenListOrdenToAttach);
            }
            bebida.setOrdenList(attachedOrdenList);
            List<Menu> attachedMenuList = new ArrayList<Menu>();
            for (Menu menuListMenuToAttach : bebida.getMenuList()) {
                menuListMenuToAttach = em.getReference(menuListMenuToAttach.getClass(), menuListMenuToAttach.getNummenu());
                attachedMenuList.add(menuListMenuToAttach);
            }
            bebida.setMenuList(attachedMenuList);
            em.persist(bebida);
            if (nummenu != null) {
                nummenu.getBebidaList().add(bebida);
                nummenu = em.merge(nummenu);
            }
            for (Orden ordenListOrden : bebida.getOrdenList()) {
                ordenListOrden.getBebidaList().add(bebida);
                ordenListOrden = em.merge(ordenListOrden);
            }
            for (Menu menuListMenu : bebida.getMenuList()) {
                menuListMenu.getBebidaList().add(bebida);
                menuListMenu = em.merge(menuListMenu);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBebida(bebida.getIdbebida()) != null) {
                throw new PreexistingEntityException("Bebida " + bebida + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bebida bebida) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bebida persistentBebida = em.find(Bebida.class, bebida.getIdbebida());
            Menu nummenuOld = persistentBebida.getNummenu();
            Menu nummenuNew = bebida.getNummenu();
            List<Orden> ordenListOld = persistentBebida.getOrdenList();
            List<Orden> ordenListNew = bebida.getOrdenList();
            List<Menu> menuListOld = persistentBebida.getMenuList();
            List<Menu> menuListNew = bebida.getMenuList();
            if (nummenuNew != null) {
                nummenuNew = em.getReference(nummenuNew.getClass(), nummenuNew.getNummenu());
                bebida.setNummenu(nummenuNew);
            }
            List<Orden> attachedOrdenListNew = new ArrayList<Orden>();
            for (Orden ordenListNewOrdenToAttach : ordenListNew) {
                ordenListNewOrdenToAttach = em.getReference(ordenListNewOrdenToAttach.getClass(), ordenListNewOrdenToAttach.getNumorden());
                attachedOrdenListNew.add(ordenListNewOrdenToAttach);
            }
            ordenListNew = attachedOrdenListNew;
            bebida.setOrdenList(ordenListNew);
            List<Menu> attachedMenuListNew = new ArrayList<Menu>();
            for (Menu menuListNewMenuToAttach : menuListNew) {
                menuListNewMenuToAttach = em.getReference(menuListNewMenuToAttach.getClass(), menuListNewMenuToAttach.getNummenu());
                attachedMenuListNew.add(menuListNewMenuToAttach);
            }
            menuListNew = attachedMenuListNew;
            bebida.setMenuList(menuListNew);
            bebida = em.merge(bebida);
            if (nummenuOld != null && !nummenuOld.equals(nummenuNew)) {
                nummenuOld.getBebidaList().remove(bebida);
                nummenuOld = em.merge(nummenuOld);
            }
            if (nummenuNew != null && !nummenuNew.equals(nummenuOld)) {
                nummenuNew.getBebidaList().add(bebida);
                nummenuNew = em.merge(nummenuNew);
            }
            for (Orden ordenListOldOrden : ordenListOld) {
                if (!ordenListNew.contains(ordenListOldOrden)) {
                    ordenListOldOrden.getBebidaList().remove(bebida);
                    ordenListOldOrden = em.merge(ordenListOldOrden);
                }
            }
            for (Orden ordenListNewOrden : ordenListNew) {
                if (!ordenListOld.contains(ordenListNewOrden)) {
                    ordenListNewOrden.getBebidaList().add(bebida);
                    ordenListNewOrden = em.merge(ordenListNewOrden);
                }
            }
            for (Menu menuListOldMenu : menuListOld) {
                if (!menuListNew.contains(menuListOldMenu)) {
                    menuListOldMenu.getBebidaList().remove(bebida);
                    menuListOldMenu = em.merge(menuListOldMenu);
                }
            }
            for (Menu menuListNewMenu : menuListNew) {
                if (!menuListOld.contains(menuListNewMenu)) {
                    menuListNewMenu.getBebidaList().add(bebida);
                    menuListNewMenu = em.merge(menuListNewMenu);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bebida.getIdbebida();
                if (findBebida(id) == null) {
                    throw new NonexistentEntityException("The bebida with id " + id + " no longer exists.");
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
            Bebida bebida;
            try {
                bebida = em.getReference(Bebida.class, id);
                bebida.getIdbebida();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bebida with id " + id + " no longer exists.", enfe);
            }
            Menu nummenu = bebida.getNummenu();
            if (nummenu != null) {
                nummenu.getBebidaList().remove(bebida);
                nummenu = em.merge(nummenu);
            }
            List<Orden> ordenList = bebida.getOrdenList();
            for (Orden ordenListOrden : ordenList) {
                ordenListOrden.getBebidaList().remove(bebida);
                ordenListOrden = em.merge(ordenListOrden);
            }
            List<Menu> menuList = bebida.getMenuList();
            for (Menu menuListMenu : menuList) {
                menuListMenu.getBebidaList().remove(bebida);
                menuListMenu = em.merge(menuListMenu);
            }
            em.remove(bebida);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bebida> findBebidaEntities() {
        return findBebidaEntities(true, -1, -1);
    }

    public List<Bebida> findBebidaEntities(int maxResults, int firstResult) {
        return findBebidaEntities(false, maxResults, firstResult);
    }

    private List<Bebida> findBebidaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bebida.class));
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

    public Bebida findBebida(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bebida.class, id);
        } finally {
            em.close();
        }
    }

    public int getBebidaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bebida> rt = cq.from(Bebida.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
