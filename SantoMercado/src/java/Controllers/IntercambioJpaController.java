/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.exceptions.IllegalOrphanException;
import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import JPA.Estadointercambio;
import JPA.Intercambio;
import JPA.Producto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Kevin_PC
 */
public class IntercambioJpaController implements Serializable {

    public IntercambioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Intercambio intercambio) throws RollbackFailureException, Exception {
        if (intercambio.getProductoCollection() == null) {
            intercambio.setProductoCollection(new ArrayList<Producto>());
        }
        if (intercambio.getProductoCollection1() == null) {
            intercambio.setProductoCollection1(new ArrayList<Producto>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Estadointercambio estadoIntercambioidEstado = intercambio.getEstadoIntercambioidEstado();
            if (estadoIntercambioidEstado != null) {
                estadoIntercambioidEstado = em.getReference(estadoIntercambioidEstado.getClass(), estadoIntercambioidEstado.getIdEstado());
                intercambio.setEstadoIntercambioidEstado(estadoIntercambioidEstado);
            }
            Collection<Producto> attachedProductoCollection = new ArrayList<Producto>();
            for (Producto productoCollectionProductoToAttach : intercambio.getProductoCollection()) {
                productoCollectionProductoToAttach = em.getReference(productoCollectionProductoToAttach.getClass(), productoCollectionProductoToAttach.getIdProducto());
                attachedProductoCollection.add(productoCollectionProductoToAttach);
            }
            intercambio.setProductoCollection(attachedProductoCollection);
            Collection<Producto> attachedProductoCollection1 = new ArrayList<Producto>();
            for (Producto productoCollection1ProductoToAttach : intercambio.getProductoCollection1()) {
                productoCollection1ProductoToAttach = em.getReference(productoCollection1ProductoToAttach.getClass(), productoCollection1ProductoToAttach.getIdProducto());
                attachedProductoCollection1.add(productoCollection1ProductoToAttach);
            }
            intercambio.setProductoCollection1(attachedProductoCollection1);
            em.persist(intercambio);
            if (estadoIntercambioidEstado != null) {
                estadoIntercambioidEstado.getIntercambioCollection().add(intercambio);
                estadoIntercambioidEstado = em.merge(estadoIntercambioidEstado);
            }
            for (Producto productoCollectionProducto : intercambio.getProductoCollection()) {
                Intercambio oldIntercambioidintercambioOfProductoCollectionProducto = productoCollectionProducto.getIntercambioidintercambio();
                productoCollectionProducto.setIntercambioidintercambio(intercambio);
                productoCollectionProducto = em.merge(productoCollectionProducto);
                if (oldIntercambioidintercambioOfProductoCollectionProducto != null) {
                    oldIntercambioidintercambioOfProductoCollectionProducto.getProductoCollection().remove(productoCollectionProducto);
                    oldIntercambioidintercambioOfProductoCollectionProducto = em.merge(oldIntercambioidintercambioOfProductoCollectionProducto);
                }
            }
            for (Producto productoCollection1Producto : intercambio.getProductoCollection1()) {
                Intercambio oldIntercambioidintercambio1OfProductoCollection1Producto = productoCollection1Producto.getIntercambioidintercambio1();
                productoCollection1Producto.setIntercambioidintercambio1(intercambio);
                productoCollection1Producto = em.merge(productoCollection1Producto);
                if (oldIntercambioidintercambio1OfProductoCollection1Producto != null) {
                    oldIntercambioidintercambio1OfProductoCollection1Producto.getProductoCollection1().remove(productoCollection1Producto);
                    oldIntercambioidintercambio1OfProductoCollection1Producto = em.merge(oldIntercambioidintercambio1OfProductoCollection1Producto);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Intercambio intercambio) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Intercambio persistentIntercambio = em.find(Intercambio.class, intercambio.getIdIntercambio());
            Estadointercambio estadoIntercambioidEstadoOld = persistentIntercambio.getEstadoIntercambioidEstado();
            Estadointercambio estadoIntercambioidEstadoNew = intercambio.getEstadoIntercambioidEstado();
            Collection<Producto> productoCollectionOld = persistentIntercambio.getProductoCollection();
            Collection<Producto> productoCollectionNew = intercambio.getProductoCollection();
            Collection<Producto> productoCollection1Old = persistentIntercambio.getProductoCollection1();
            Collection<Producto> productoCollection1New = intercambio.getProductoCollection1();
            List<String> illegalOrphanMessages = null;
            for (Producto productoCollectionOldProducto : productoCollectionOld) {
                if (!productoCollectionNew.contains(productoCollectionOldProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Producto " + productoCollectionOldProducto + " since its intercambioidintercambio field is not nullable.");
                }
            }
            for (Producto productoCollection1OldProducto : productoCollection1Old) {
                if (!productoCollection1New.contains(productoCollection1OldProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Producto " + productoCollection1OldProducto + " since its intercambioidintercambio1 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (estadoIntercambioidEstadoNew != null) {
                estadoIntercambioidEstadoNew = em.getReference(estadoIntercambioidEstadoNew.getClass(), estadoIntercambioidEstadoNew.getIdEstado());
                intercambio.setEstadoIntercambioidEstado(estadoIntercambioidEstadoNew);
            }
            Collection<Producto> attachedProductoCollectionNew = new ArrayList<Producto>();
            for (Producto productoCollectionNewProductoToAttach : productoCollectionNew) {
                productoCollectionNewProductoToAttach = em.getReference(productoCollectionNewProductoToAttach.getClass(), productoCollectionNewProductoToAttach.getIdProducto());
                attachedProductoCollectionNew.add(productoCollectionNewProductoToAttach);
            }
            productoCollectionNew = attachedProductoCollectionNew;
            intercambio.setProductoCollection(productoCollectionNew);
            Collection<Producto> attachedProductoCollection1New = new ArrayList<Producto>();
            for (Producto productoCollection1NewProductoToAttach : productoCollection1New) {
                productoCollection1NewProductoToAttach = em.getReference(productoCollection1NewProductoToAttach.getClass(), productoCollection1NewProductoToAttach.getIdProducto());
                attachedProductoCollection1New.add(productoCollection1NewProductoToAttach);
            }
            productoCollection1New = attachedProductoCollection1New;
            intercambio.setProductoCollection1(productoCollection1New);
            intercambio = em.merge(intercambio);
            if (estadoIntercambioidEstadoOld != null && !estadoIntercambioidEstadoOld.equals(estadoIntercambioidEstadoNew)) {
                estadoIntercambioidEstadoOld.getIntercambioCollection().remove(intercambio);
                estadoIntercambioidEstadoOld = em.merge(estadoIntercambioidEstadoOld);
            }
            if (estadoIntercambioidEstadoNew != null && !estadoIntercambioidEstadoNew.equals(estadoIntercambioidEstadoOld)) {
                estadoIntercambioidEstadoNew.getIntercambioCollection().add(intercambio);
                estadoIntercambioidEstadoNew = em.merge(estadoIntercambioidEstadoNew);
            }
            for (Producto productoCollectionNewProducto : productoCollectionNew) {
                if (!productoCollectionOld.contains(productoCollectionNewProducto)) {
                    Intercambio oldIntercambioidintercambioOfProductoCollectionNewProducto = productoCollectionNewProducto.getIntercambioidintercambio();
                    productoCollectionNewProducto.setIntercambioidintercambio(intercambio);
                    productoCollectionNewProducto = em.merge(productoCollectionNewProducto);
                    if (oldIntercambioidintercambioOfProductoCollectionNewProducto != null && !oldIntercambioidintercambioOfProductoCollectionNewProducto.equals(intercambio)) {
                        oldIntercambioidintercambioOfProductoCollectionNewProducto.getProductoCollection().remove(productoCollectionNewProducto);
                        oldIntercambioidintercambioOfProductoCollectionNewProducto = em.merge(oldIntercambioidintercambioOfProductoCollectionNewProducto);
                    }
                }
            }
            for (Producto productoCollection1NewProducto : productoCollection1New) {
                if (!productoCollection1Old.contains(productoCollection1NewProducto)) {
                    Intercambio oldIntercambioidintercambio1OfProductoCollection1NewProducto = productoCollection1NewProducto.getIntercambioidintercambio1();
                    productoCollection1NewProducto.setIntercambioidintercambio1(intercambio);
                    productoCollection1NewProducto = em.merge(productoCollection1NewProducto);
                    if (oldIntercambioidintercambio1OfProductoCollection1NewProducto != null && !oldIntercambioidintercambio1OfProductoCollection1NewProducto.equals(intercambio)) {
                        oldIntercambioidintercambio1OfProductoCollection1NewProducto.getProductoCollection1().remove(productoCollection1NewProducto);
                        oldIntercambioidintercambio1OfProductoCollection1NewProducto = em.merge(oldIntercambioidintercambio1OfProductoCollection1NewProducto);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = intercambio.getIdIntercambio();
                if (findIntercambio(id) == null) {
                    throw new NonexistentEntityException("The intercambio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Intercambio intercambio;
            try {
                intercambio = em.getReference(Intercambio.class, id);
                intercambio.getIdIntercambio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The intercambio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Producto> productoCollectionOrphanCheck = intercambio.getProductoCollection();
            for (Producto productoCollectionOrphanCheckProducto : productoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Intercambio (" + intercambio + ") cannot be destroyed since the Producto " + productoCollectionOrphanCheckProducto + " in its productoCollection field has a non-nullable intercambioidintercambio field.");
            }
            Collection<Producto> productoCollection1OrphanCheck = intercambio.getProductoCollection1();
            for (Producto productoCollection1OrphanCheckProducto : productoCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Intercambio (" + intercambio + ") cannot be destroyed since the Producto " + productoCollection1OrphanCheckProducto + " in its productoCollection1 field has a non-nullable intercambioidintercambio1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Estadointercambio estadoIntercambioidEstado = intercambio.getEstadoIntercambioidEstado();
            if (estadoIntercambioidEstado != null) {
                estadoIntercambioidEstado.getIntercambioCollection().remove(intercambio);
                estadoIntercambioidEstado = em.merge(estadoIntercambioidEstado);
            }
            em.remove(intercambio);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Intercambio> findIntercambioEntities() {
        return findIntercambioEntities(true, -1, -1);
    }

    public List<Intercambio> findIntercambioEntities(int maxResults, int firstResult) {
        return findIntercambioEntities(false, maxResults, firstResult);
    }

    private List<Intercambio> findIntercambioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Intercambio.class));
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

    public Intercambio findIntercambio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Intercambio.class, id);
        } finally {
            em.close();
        }
    }

    public int getIntercambioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Intercambio> rt = cq.from(Intercambio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
