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
import modelo.Platillo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Bebida;
import modelo.Menu;

/**
 *
 * @author aleja
 */
public class MenuJpaController implements Serializable {

    public MenuJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Menu menu) throws PreexistingEntityException, Exception {
        if (menu.getPlatilloList() == null) {
            menu.setPlatilloList(new ArrayList<Platillo>());
        }
        if (menu.getBebidaList() == null) {
            menu.setBebidaList(new ArrayList<Bebida>());
        }
        if (menu.getPlatilloList1() == null) {
            menu.setPlatilloList1(new ArrayList<Platillo>());
        }
        if (menu.getBebidaList1() == null) {
            menu.setBebidaList1(new ArrayList<Bebida>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Platillo> attachedPlatilloList = new ArrayList<Platillo>();
            for (Platillo platilloListPlatilloToAttach : menu.getPlatilloList()) {
                platilloListPlatilloToAttach = em.getReference(platilloListPlatilloToAttach.getClass(), platilloListPlatilloToAttach.getIdplatillo());
                attachedPlatilloList.add(platilloListPlatilloToAttach);
            }
            menu.setPlatilloList(attachedPlatilloList);
            List<Bebida> attachedBebidaList = new ArrayList<Bebida>();
            for (Bebida bebidaListBebidaToAttach : menu.getBebidaList()) {
                bebidaListBebidaToAttach = em.getReference(bebidaListBebidaToAttach.getClass(), bebidaListBebidaToAttach.getIdbebida());
                attachedBebidaList.add(bebidaListBebidaToAttach);
            }
            menu.setBebidaList(attachedBebidaList);
            List<Platillo> attachedPlatilloList1 = new ArrayList<Platillo>();
            for (Platillo platilloList1PlatilloToAttach : menu.getPlatilloList1()) {
                platilloList1PlatilloToAttach = em.getReference(platilloList1PlatilloToAttach.getClass(), platilloList1PlatilloToAttach.getIdplatillo());
                attachedPlatilloList1.add(platilloList1PlatilloToAttach);
            }
            menu.setPlatilloList1(attachedPlatilloList1);
            List<Bebida> attachedBebidaList1 = new ArrayList<Bebida>();
            for (Bebida bebidaList1BebidaToAttach : menu.getBebidaList1()) {
                bebidaList1BebidaToAttach = em.getReference(bebidaList1BebidaToAttach.getClass(), bebidaList1BebidaToAttach.getIdbebida());
                attachedBebidaList1.add(bebidaList1BebidaToAttach);
            }
            menu.setBebidaList1(attachedBebidaList1);
            em.persist(menu);
            for (Platillo platilloListPlatillo : menu.getPlatilloList()) {
                platilloListPlatillo.getMenuList().add(menu);
                platilloListPlatillo = em.merge(platilloListPlatillo);
            }
            for (Bebida bebidaListBebida : menu.getBebidaList()) {
                bebidaListBebida.getMenuList().add(menu);
                bebidaListBebida = em.merge(bebidaListBebida);
            }
            for (Platillo platilloList1Platillo : menu.getPlatilloList1()) {
                Menu oldNummenuOfPlatilloList1Platillo = platilloList1Platillo.getNummenu();
                platilloList1Platillo.setNummenu(menu);
                platilloList1Platillo = em.merge(platilloList1Platillo);
                if (oldNummenuOfPlatilloList1Platillo != null) {
                    oldNummenuOfPlatilloList1Platillo.getPlatilloList1().remove(platilloList1Platillo);
                    oldNummenuOfPlatilloList1Platillo = em.merge(oldNummenuOfPlatilloList1Platillo);
                }
            }
            for (Bebida bebidaList1Bebida : menu.getBebidaList1()) {
                Menu oldNummenuOfBebidaList1Bebida = bebidaList1Bebida.getNummenu();
                bebidaList1Bebida.setNummenu(menu);
                bebidaList1Bebida = em.merge(bebidaList1Bebida);
                if (oldNummenuOfBebidaList1Bebida != null) {
                    oldNummenuOfBebidaList1Bebida.getBebidaList1().remove(bebidaList1Bebida);
                    oldNummenuOfBebidaList1Bebida = em.merge(oldNummenuOfBebidaList1Bebida);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMenu(menu.getNummenu()) != null) {
                throw new PreexistingEntityException("Menu " + menu + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Menu menu) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Menu persistentMenu = em.find(Menu.class, menu.getNummenu());
            List<Platillo> platilloListOld = persistentMenu.getPlatilloList();
            List<Platillo> platilloListNew = menu.getPlatilloList();
            List<Bebida> bebidaListOld = persistentMenu.getBebidaList();
            List<Bebida> bebidaListNew = menu.getBebidaList();
            List<Platillo> platilloList1Old = persistentMenu.getPlatilloList1();
            List<Platillo> platilloList1New = menu.getPlatilloList1();
            List<Bebida> bebidaList1Old = persistentMenu.getBebidaList1();
            List<Bebida> bebidaList1New = menu.getBebidaList1();
            List<Platillo> attachedPlatilloListNew = new ArrayList<Platillo>();
            for (Platillo platilloListNewPlatilloToAttach : platilloListNew) {
                platilloListNewPlatilloToAttach = em.getReference(platilloListNewPlatilloToAttach.getClass(), platilloListNewPlatilloToAttach.getIdplatillo());
                attachedPlatilloListNew.add(platilloListNewPlatilloToAttach);
            }
            platilloListNew = attachedPlatilloListNew;
            menu.setPlatilloList(platilloListNew);
            List<Bebida> attachedBebidaListNew = new ArrayList<Bebida>();
            for (Bebida bebidaListNewBebidaToAttach : bebidaListNew) {
                bebidaListNewBebidaToAttach = em.getReference(bebidaListNewBebidaToAttach.getClass(), bebidaListNewBebidaToAttach.getIdbebida());
                attachedBebidaListNew.add(bebidaListNewBebidaToAttach);
            }
            bebidaListNew = attachedBebidaListNew;
            menu.setBebidaList(bebidaListNew);
            List<Platillo> attachedPlatilloList1New = new ArrayList<Platillo>();
            for (Platillo platilloList1NewPlatilloToAttach : platilloList1New) {
                platilloList1NewPlatilloToAttach = em.getReference(platilloList1NewPlatilloToAttach.getClass(), platilloList1NewPlatilloToAttach.getIdplatillo());
                attachedPlatilloList1New.add(platilloList1NewPlatilloToAttach);
            }
            platilloList1New = attachedPlatilloList1New;
            menu.setPlatilloList1(platilloList1New);
            List<Bebida> attachedBebidaList1New = new ArrayList<Bebida>();
            for (Bebida bebidaList1NewBebidaToAttach : bebidaList1New) {
                bebidaList1NewBebidaToAttach = em.getReference(bebidaList1NewBebidaToAttach.getClass(), bebidaList1NewBebidaToAttach.getIdbebida());
                attachedBebidaList1New.add(bebidaList1NewBebidaToAttach);
            }
            bebidaList1New = attachedBebidaList1New;
            menu.setBebidaList1(bebidaList1New);
            menu = em.merge(menu);
            for (Platillo platilloListOldPlatillo : platilloListOld) {
                if (!platilloListNew.contains(platilloListOldPlatillo)) {
                    platilloListOldPlatillo.getMenuList().remove(menu);
                    platilloListOldPlatillo = em.merge(platilloListOldPlatillo);
                }
            }
            for (Platillo platilloListNewPlatillo : platilloListNew) {
                if (!platilloListOld.contains(platilloListNewPlatillo)) {
                    platilloListNewPlatillo.getMenuList().add(menu);
                    platilloListNewPlatillo = em.merge(platilloListNewPlatillo);
                }
            }
            for (Bebida bebidaListOldBebida : bebidaListOld) {
                if (!bebidaListNew.contains(bebidaListOldBebida)) {
                    bebidaListOldBebida.getMenuList().remove(menu);
                    bebidaListOldBebida = em.merge(bebidaListOldBebida);
                }
            }
            for (Bebida bebidaListNewBebida : bebidaListNew) {
                if (!bebidaListOld.contains(bebidaListNewBebida)) {
                    bebidaListNewBebida.getMenuList().add(menu);
                    bebidaListNewBebida = em.merge(bebidaListNewBebida);
                }
            }
            for (Platillo platilloList1OldPlatillo : platilloList1Old) {
                if (!platilloList1New.contains(platilloList1OldPlatillo)) {
                    platilloList1OldPlatillo.setNummenu(null);
                    platilloList1OldPlatillo = em.merge(platilloList1OldPlatillo);
                }
            }
            for (Platillo platilloList1NewPlatillo : platilloList1New) {
                if (!platilloList1Old.contains(platilloList1NewPlatillo)) {
                    Menu oldNummenuOfPlatilloList1NewPlatillo = platilloList1NewPlatillo.getNummenu();
                    platilloList1NewPlatillo.setNummenu(menu);
                    platilloList1NewPlatillo = em.merge(platilloList1NewPlatillo);
                    if (oldNummenuOfPlatilloList1NewPlatillo != null && !oldNummenuOfPlatilloList1NewPlatillo.equals(menu)) {
                        oldNummenuOfPlatilloList1NewPlatillo.getPlatilloList1().remove(platilloList1NewPlatillo);
                        oldNummenuOfPlatilloList1NewPlatillo = em.merge(oldNummenuOfPlatilloList1NewPlatillo);
                    }
                }
            }
            for (Bebida bebidaList1OldBebida : bebidaList1Old) {
                if (!bebidaList1New.contains(bebidaList1OldBebida)) {
                    bebidaList1OldBebida.setNummenu(null);
                    bebidaList1OldBebida = em.merge(bebidaList1OldBebida);
                }
            }
            for (Bebida bebidaList1NewBebida : bebidaList1New) {
                if (!bebidaList1Old.contains(bebidaList1NewBebida)) {
                    Menu oldNummenuOfBebidaList1NewBebida = bebidaList1NewBebida.getNummenu();
                    bebidaList1NewBebida.setNummenu(menu);
                    bebidaList1NewBebida = em.merge(bebidaList1NewBebida);
                    if (oldNummenuOfBebidaList1NewBebida != null && !oldNummenuOfBebidaList1NewBebida.equals(menu)) {
                        oldNummenuOfBebidaList1NewBebida.getBebidaList1().remove(bebidaList1NewBebida);
                        oldNummenuOfBebidaList1NewBebida = em.merge(oldNummenuOfBebidaList1NewBebida);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = menu.getNummenu();
                if (findMenu(id) == null) {
                    throw new NonexistentEntityException("The menu with id " + id + " no longer exists.");
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
            Menu menu;
            try {
                menu = em.getReference(Menu.class, id);
                menu.getNummenu();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The menu with id " + id + " no longer exists.", enfe);
            }
            List<Platillo> platilloList = menu.getPlatilloList();
            for (Platillo platilloListPlatillo : platilloList) {
                platilloListPlatillo.getMenuList().remove(menu);
                platilloListPlatillo = em.merge(platilloListPlatillo);
            }
            List<Bebida> bebidaList = menu.getBebidaList();
            for (Bebida bebidaListBebida : bebidaList) {
                bebidaListBebida.getMenuList().remove(menu);
                bebidaListBebida = em.merge(bebidaListBebida);
            }
            List<Platillo> platilloList1 = menu.getPlatilloList1();
            for (Platillo platilloList1Platillo : platilloList1) {
                platilloList1Platillo.setNummenu(null);
                platilloList1Platillo = em.merge(platilloList1Platillo);
            }
            List<Bebida> bebidaList1 = menu.getBebidaList1();
            for (Bebida bebidaList1Bebida : bebidaList1) {
                bebidaList1Bebida.setNummenu(null);
                bebidaList1Bebida = em.merge(bebidaList1Bebida);
            }
            em.remove(menu);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Menu> findMenuEntities() {
        return findMenuEntities(true, -1, -1);
    }

    public List<Menu> findMenuEntities(int maxResults, int firstResult) {
        return findMenuEntities(false, maxResults, firstResult);
    }

    private List<Menu> findMenuEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Menu.class));
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

    public Menu findMenu(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Menu.class, id);
        } finally {
            em.close();
        }
    }

    public int getMenuCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Menu> rt = cq.from(Menu.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
