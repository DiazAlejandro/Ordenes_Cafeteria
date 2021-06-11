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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Ingrediente;
import modelo.Orden;
import modelo.Platillo;

/**
 *
 * @author aleja
 */
public class PlatilloJpaController implements Serializable {

    public PlatilloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Platillo platillo) throws PreexistingEntityException, Exception {
        if (platillo.getMenuList() == null) {
            platillo.setMenuList(new ArrayList<Menu>());
        }
        if (platillo.getIngredienteList() == null) {
            platillo.setIngredienteList(new ArrayList<Ingrediente>());
        }
        if (platillo.getOrdenList() == null) {
            platillo.setOrdenList(new ArrayList<Orden>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Menu nummenu = platillo.getNummenu();
            if (nummenu != null) {
                nummenu = em.getReference(nummenu.getClass(), nummenu.getNummenu());
                platillo.setNummenu(nummenu);
            }
            List<Menu> attachedMenuList = new ArrayList<Menu>();
            for (Menu menuListMenuToAttach : platillo.getMenuList()) {
                menuListMenuToAttach = em.getReference(menuListMenuToAttach.getClass(), menuListMenuToAttach.getNummenu());
                attachedMenuList.add(menuListMenuToAttach);
            }
            platillo.setMenuList(attachedMenuList);
            List<Ingrediente> attachedIngredienteList = new ArrayList<Ingrediente>();
            for (Ingrediente ingredienteListIngredienteToAttach : platillo.getIngredienteList()) {
                ingredienteListIngredienteToAttach = em.getReference(ingredienteListIngredienteToAttach.getClass(), ingredienteListIngredienteToAttach.getIding());
                attachedIngredienteList.add(ingredienteListIngredienteToAttach);
            }
            platillo.setIngredienteList(attachedIngredienteList);
            List<Orden> attachedOrdenList = new ArrayList<Orden>();
            for (Orden ordenListOrdenToAttach : platillo.getOrdenList()) {
                ordenListOrdenToAttach = em.getReference(ordenListOrdenToAttach.getClass(), ordenListOrdenToAttach.getNumorden());
                attachedOrdenList.add(ordenListOrdenToAttach);
            }
            platillo.setOrdenList(attachedOrdenList);
            em.persist(platillo);
            if (nummenu != null) {
                nummenu.getPlatilloList().add(platillo);
                nummenu = em.merge(nummenu);
            }
            for (Menu menuListMenu : platillo.getMenuList()) {
                menuListMenu.getPlatilloList().add(platillo);
                menuListMenu = em.merge(menuListMenu);
            }
            for (Ingrediente ingredienteListIngrediente : platillo.getIngredienteList()) {
                ingredienteListIngrediente.getPlatilloList().add(platillo);
                ingredienteListIngrediente = em.merge(ingredienteListIngrediente);
            }
            for (Orden ordenListOrden : platillo.getOrdenList()) {
                ordenListOrden.getPlatilloList().add(platillo);
                ordenListOrden = em.merge(ordenListOrden);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlatillo(platillo.getIdplatillo()) != null) {
                throw new PreexistingEntityException("Platillo " + platillo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Platillo platillo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Platillo persistentPlatillo = em.find(Platillo.class, platillo.getIdplatillo());
            Menu nummenuOld = persistentPlatillo.getNummenu();
            Menu nummenuNew = platillo.getNummenu();
            List<Menu> menuListOld = persistentPlatillo.getMenuList();
            List<Menu> menuListNew = platillo.getMenuList();
            List<Ingrediente> ingredienteListOld = persistentPlatillo.getIngredienteList();
            List<Ingrediente> ingredienteListNew = platillo.getIngredienteList();
            List<Orden> ordenListOld = persistentPlatillo.getOrdenList();
            List<Orden> ordenListNew = platillo.getOrdenList();
            if (nummenuNew != null) {
                nummenuNew = em.getReference(nummenuNew.getClass(), nummenuNew.getNummenu());
                platillo.setNummenu(nummenuNew);
            }
            List<Menu> attachedMenuListNew = new ArrayList<Menu>();
            for (Menu menuListNewMenuToAttach : menuListNew) {
                menuListNewMenuToAttach = em.getReference(menuListNewMenuToAttach.getClass(), menuListNewMenuToAttach.getNummenu());
                attachedMenuListNew.add(menuListNewMenuToAttach);
            }
            menuListNew = attachedMenuListNew;
            platillo.setMenuList(menuListNew);
            List<Ingrediente> attachedIngredienteListNew = new ArrayList<Ingrediente>();
            for (Ingrediente ingredienteListNewIngredienteToAttach : ingredienteListNew) {
                ingredienteListNewIngredienteToAttach = em.getReference(ingredienteListNewIngredienteToAttach.getClass(), ingredienteListNewIngredienteToAttach.getIding());
                attachedIngredienteListNew.add(ingredienteListNewIngredienteToAttach);
            }
            ingredienteListNew = attachedIngredienteListNew;
            platillo.setIngredienteList(ingredienteListNew);
            List<Orden> attachedOrdenListNew = new ArrayList<Orden>();
            for (Orden ordenListNewOrdenToAttach : ordenListNew) {
                ordenListNewOrdenToAttach = em.getReference(ordenListNewOrdenToAttach.getClass(), ordenListNewOrdenToAttach.getNumorden());
                attachedOrdenListNew.add(ordenListNewOrdenToAttach);
            }
            ordenListNew = attachedOrdenListNew;
            platillo.setOrdenList(ordenListNew);
            platillo = em.merge(platillo);
            if (nummenuOld != null && !nummenuOld.equals(nummenuNew)) {
                nummenuOld.getPlatilloList().remove(platillo);
                nummenuOld = em.merge(nummenuOld);
            }
            if (nummenuNew != null && !nummenuNew.equals(nummenuOld)) {
                nummenuNew.getPlatilloList().add(platillo);
                nummenuNew = em.merge(nummenuNew);
            }
            for (Menu menuListOldMenu : menuListOld) {
                if (!menuListNew.contains(menuListOldMenu)) {
                    menuListOldMenu.getPlatilloList().remove(platillo);
                    menuListOldMenu = em.merge(menuListOldMenu);
                }
            }
            for (Menu menuListNewMenu : menuListNew) {
                if (!menuListOld.contains(menuListNewMenu)) {
                    menuListNewMenu.getPlatilloList().add(platillo);
                    menuListNewMenu = em.merge(menuListNewMenu);
                }
            }
            for (Ingrediente ingredienteListOldIngrediente : ingredienteListOld) {
                if (!ingredienteListNew.contains(ingredienteListOldIngrediente)) {
                    ingredienteListOldIngrediente.getPlatilloList().remove(platillo);
                    ingredienteListOldIngrediente = em.merge(ingredienteListOldIngrediente);
                }
            }
            for (Ingrediente ingredienteListNewIngrediente : ingredienteListNew) {
                if (!ingredienteListOld.contains(ingredienteListNewIngrediente)) {
                    ingredienteListNewIngrediente.getPlatilloList().add(platillo);
                    ingredienteListNewIngrediente = em.merge(ingredienteListNewIngrediente);
                }
            }
            for (Orden ordenListOldOrden : ordenListOld) {
                if (!ordenListNew.contains(ordenListOldOrden)) {
                    ordenListOldOrden.getPlatilloList().remove(platillo);
                    ordenListOldOrden = em.merge(ordenListOldOrden);
                }
            }
            for (Orden ordenListNewOrden : ordenListNew) {
                if (!ordenListOld.contains(ordenListNewOrden)) {
                    ordenListNewOrden.getPlatilloList().add(platillo);
                    ordenListNewOrden = em.merge(ordenListNewOrden);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = platillo.getIdplatillo();
                if (findPlatillo(id) == null) {
                    throw new NonexistentEntityException("The platillo with id " + id + " no longer exists.");
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
            Platillo platillo;
            try {
                platillo = em.getReference(Platillo.class, id);
                platillo.getIdplatillo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The platillo with id " + id + " no longer exists.", enfe);
            }
            Menu nummenu = platillo.getNummenu();
            if (nummenu != null) {
                nummenu.getPlatilloList().remove(platillo);
                nummenu = em.merge(nummenu);
            }
            List<Menu> menuList = platillo.getMenuList();
            for (Menu menuListMenu : menuList) {
                menuListMenu.getPlatilloList().remove(platillo);
                menuListMenu = em.merge(menuListMenu);
            }
            List<Ingrediente> ingredienteList = platillo.getIngredienteList();
            for (Ingrediente ingredienteListIngrediente : ingredienteList) {
                ingredienteListIngrediente.getPlatilloList().remove(platillo);
                ingredienteListIngrediente = em.merge(ingredienteListIngrediente);
            }
            List<Orden> ordenList = platillo.getOrdenList();
            for (Orden ordenListOrden : ordenList) {
                ordenListOrden.getPlatilloList().remove(platillo);
                ordenListOrden = em.merge(ordenListOrden);
            }
            em.remove(platillo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Platillo> findPlatilloEntities() {
        return findPlatilloEntities(true, -1, -1);
    }

    public List<Platillo> findPlatilloEntities(int maxResults, int firstResult) {
        return findPlatilloEntities(false, maxResults, firstResult);
    }

    private List<Platillo> findPlatilloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Platillo.class));
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

    public Platillo findPlatillo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Platillo.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlatilloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Platillo> rt = cq.from(Platillo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
