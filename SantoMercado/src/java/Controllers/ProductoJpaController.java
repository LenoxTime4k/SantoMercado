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
import JPA.Categoria;
import JPA.Intercambio;
import JPA.Compra;
import JPA.Producto;
import java.util.ArrayList;
import java.util.Collection;
import JPA.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Kevin_PC
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) throws RollbackFailureException, Exception {
        if (producto.getCompraCollection() == null) {
            producto.setCompraCollection(new ArrayList<Compra>());
        }
        if (producto.getUsuarioCollection() == null) {
            producto.setUsuarioCollection(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Categoria categoriaidcategoria = producto.getCategoriaidcategoria();
            if (categoriaidcategoria != null) {
                categoriaidcategoria = em.getReference(categoriaidcategoria.getClass(), categoriaidcategoria.getIdCategoria());
                producto.setCategoriaidcategoria(categoriaidcategoria);
            }
            Intercambio intercambioidintercambio = producto.getIntercambioidintercambio();
            if (intercambioidintercambio != null) {
                intercambioidintercambio = em.getReference(intercambioidintercambio.getClass(), intercambioidintercambio.getIdIntercambio());
                producto.setIntercambioidintercambio(intercambioidintercambio);
            }
            Intercambio intercambioidintercambio1 = producto.getIntercambioidintercambio1();
            if (intercambioidintercambio1 != null) {
                intercambioidintercambio1 = em.getReference(intercambioidintercambio1.getClass(), intercambioidintercambio1.getIdIntercambio());
                producto.setIntercambioidintercambio1(intercambioidintercambio1);
            }
            Collection<Compra> attachedCompraCollection = new ArrayList<Compra>();
            for (Compra compraCollectionCompraToAttach : producto.getCompraCollection()) {
                compraCollectionCompraToAttach = em.getReference(compraCollectionCompraToAttach.getClass(), compraCollectionCompraToAttach.getIdCompra());
                attachedCompraCollection.add(compraCollectionCompraToAttach);
            }
            producto.setCompraCollection(attachedCompraCollection);
            Collection<Usuario> attachedUsuarioCollection = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionUsuarioToAttach : producto.getUsuarioCollection()) {
                usuarioCollectionUsuarioToAttach = em.getReference(usuarioCollectionUsuarioToAttach.getClass(), usuarioCollectionUsuarioToAttach.getRutUsuario());
                attachedUsuarioCollection.add(usuarioCollectionUsuarioToAttach);
            }
            producto.setUsuarioCollection(attachedUsuarioCollection);
            em.persist(producto);
            if (categoriaidcategoria != null) {
                categoriaidcategoria.getProductoCollection().add(producto);
                categoriaidcategoria = em.merge(categoriaidcategoria);
            }
            if (intercambioidintercambio != null) {
                intercambioidintercambio.getProductoCollection().add(producto);
                intercambioidintercambio = em.merge(intercambioidintercambio);
            }
            if (intercambioidintercambio1 != null) {
                intercambioidintercambio1.getProductoCollection().add(producto);
                intercambioidintercambio1 = em.merge(intercambioidintercambio1);
            }
            for (Compra compraCollectionCompra : producto.getCompraCollection()) {
                Producto oldProductoidproductoOfCompraCollectionCompra = compraCollectionCompra.getProductoidproducto();
                compraCollectionCompra.setProductoidproducto(producto);
                compraCollectionCompra = em.merge(compraCollectionCompra);
                if (oldProductoidproductoOfCompraCollectionCompra != null) {
                    oldProductoidproductoOfCompraCollectionCompra.getCompraCollection().remove(compraCollectionCompra);
                    oldProductoidproductoOfCompraCollectionCompra = em.merge(oldProductoidproductoOfCompraCollectionCompra);
                }
            }
            for (Usuario usuarioCollectionUsuario : producto.getUsuarioCollection()) {
                Producto oldProductoidproductoOfUsuarioCollectionUsuario = usuarioCollectionUsuario.getProductoidproducto();
                usuarioCollectionUsuario.setProductoidproducto(producto);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
                if (oldProductoidproductoOfUsuarioCollectionUsuario != null) {
                    oldProductoidproductoOfUsuarioCollectionUsuario.getUsuarioCollection().remove(usuarioCollectionUsuario);
                    oldProductoidproductoOfUsuarioCollectionUsuario = em.merge(oldProductoidproductoOfUsuarioCollectionUsuario);
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

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Producto persistentProducto = em.find(Producto.class, producto.getIdProducto());
            Categoria categoriaidcategoriaOld = persistentProducto.getCategoriaidcategoria();
            Categoria categoriaidcategoriaNew = producto.getCategoriaidcategoria();
            Intercambio intercambioidintercambioOld = persistentProducto.getIntercambioidintercambio();
            Intercambio intercambioidintercambioNew = producto.getIntercambioidintercambio();
            Intercambio intercambioidintercambio1Old = persistentProducto.getIntercambioidintercambio1();
            Intercambio intercambioidintercambio1New = producto.getIntercambioidintercambio1();
            Collection<Compra> compraCollectionOld = persistentProducto.getCompraCollection();
            Collection<Compra> compraCollectionNew = producto.getCompraCollection();
            Collection<Usuario> usuarioCollectionOld = persistentProducto.getUsuarioCollection();
            Collection<Usuario> usuarioCollectionNew = producto.getUsuarioCollection();
            List<String> illegalOrphanMessages = null;
            for (Compra compraCollectionOldCompra : compraCollectionOld) {
                if (!compraCollectionNew.contains(compraCollectionOldCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Compra " + compraCollectionOldCompra + " since its productoidproducto field is not nullable.");
                }
            }
            for (Usuario usuarioCollectionOldUsuario : usuarioCollectionOld) {
                if (!usuarioCollectionNew.contains(usuarioCollectionOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioCollectionOldUsuario + " since its productoidproducto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (categoriaidcategoriaNew != null) {
                categoriaidcategoriaNew = em.getReference(categoriaidcategoriaNew.getClass(), categoriaidcategoriaNew.getIdCategoria());
                producto.setCategoriaidcategoria(categoriaidcategoriaNew);
            }
            if (intercambioidintercambioNew != null) {
                intercambioidintercambioNew = em.getReference(intercambioidintercambioNew.getClass(), intercambioidintercambioNew.getIdIntercambio());
                producto.setIntercambioidintercambio(intercambioidintercambioNew);
            }
            if (intercambioidintercambio1New != null) {
                intercambioidintercambio1New = em.getReference(intercambioidintercambio1New.getClass(), intercambioidintercambio1New.getIdIntercambio());
                producto.setIntercambioidintercambio1(intercambioidintercambio1New);
            }
            Collection<Compra> attachedCompraCollectionNew = new ArrayList<Compra>();
            for (Compra compraCollectionNewCompraToAttach : compraCollectionNew) {
                compraCollectionNewCompraToAttach = em.getReference(compraCollectionNewCompraToAttach.getClass(), compraCollectionNewCompraToAttach.getIdCompra());
                attachedCompraCollectionNew.add(compraCollectionNewCompraToAttach);
            }
            compraCollectionNew = attachedCompraCollectionNew;
            producto.setCompraCollection(compraCollectionNew);
            Collection<Usuario> attachedUsuarioCollectionNew = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionNewUsuarioToAttach : usuarioCollectionNew) {
                usuarioCollectionNewUsuarioToAttach = em.getReference(usuarioCollectionNewUsuarioToAttach.getClass(), usuarioCollectionNewUsuarioToAttach.getRutUsuario());
                attachedUsuarioCollectionNew.add(usuarioCollectionNewUsuarioToAttach);
            }
            usuarioCollectionNew = attachedUsuarioCollectionNew;
            producto.setUsuarioCollection(usuarioCollectionNew);
            producto = em.merge(producto);
            if (categoriaidcategoriaOld != null && !categoriaidcategoriaOld.equals(categoriaidcategoriaNew)) {
                categoriaidcategoriaOld.getProductoCollection().remove(producto);
                categoriaidcategoriaOld = em.merge(categoriaidcategoriaOld);
            }
            if (categoriaidcategoriaNew != null && !categoriaidcategoriaNew.equals(categoriaidcategoriaOld)) {
                categoriaidcategoriaNew.getProductoCollection().add(producto);
                categoriaidcategoriaNew = em.merge(categoriaidcategoriaNew);
            }
            if (intercambioidintercambioOld != null && !intercambioidintercambioOld.equals(intercambioidintercambioNew)) {
                intercambioidintercambioOld.getProductoCollection().remove(producto);
                intercambioidintercambioOld = em.merge(intercambioidintercambioOld);
            }
            if (intercambioidintercambioNew != null && !intercambioidintercambioNew.equals(intercambioidintercambioOld)) {
                intercambioidintercambioNew.getProductoCollection().add(producto);
                intercambioidintercambioNew = em.merge(intercambioidintercambioNew);
            }
            if (intercambioidintercambio1Old != null && !intercambioidintercambio1Old.equals(intercambioidintercambio1New)) {
                intercambioidintercambio1Old.getProductoCollection().remove(producto);
                intercambioidintercambio1Old = em.merge(intercambioidintercambio1Old);
            }
            if (intercambioidintercambio1New != null && !intercambioidintercambio1New.equals(intercambioidintercambio1Old)) {
                intercambioidintercambio1New.getProductoCollection().add(producto);
                intercambioidintercambio1New = em.merge(intercambioidintercambio1New);
            }
            for (Compra compraCollectionNewCompra : compraCollectionNew) {
                if (!compraCollectionOld.contains(compraCollectionNewCompra)) {
                    Producto oldProductoidproductoOfCompraCollectionNewCompra = compraCollectionNewCompra.getProductoidproducto();
                    compraCollectionNewCompra.setProductoidproducto(producto);
                    compraCollectionNewCompra = em.merge(compraCollectionNewCompra);
                    if (oldProductoidproductoOfCompraCollectionNewCompra != null && !oldProductoidproductoOfCompraCollectionNewCompra.equals(producto)) {
                        oldProductoidproductoOfCompraCollectionNewCompra.getCompraCollection().remove(compraCollectionNewCompra);
                        oldProductoidproductoOfCompraCollectionNewCompra = em.merge(oldProductoidproductoOfCompraCollectionNewCompra);
                    }
                }
            }
            for (Usuario usuarioCollectionNewUsuario : usuarioCollectionNew) {
                if (!usuarioCollectionOld.contains(usuarioCollectionNewUsuario)) {
                    Producto oldProductoidproductoOfUsuarioCollectionNewUsuario = usuarioCollectionNewUsuario.getProductoidproducto();
                    usuarioCollectionNewUsuario.setProductoidproducto(producto);
                    usuarioCollectionNewUsuario = em.merge(usuarioCollectionNewUsuario);
                    if (oldProductoidproductoOfUsuarioCollectionNewUsuario != null && !oldProductoidproductoOfUsuarioCollectionNewUsuario.equals(producto)) {
                        oldProductoidproductoOfUsuarioCollectionNewUsuario.getUsuarioCollection().remove(usuarioCollectionNewUsuario);
                        oldProductoidproductoOfUsuarioCollectionNewUsuario = em.merge(oldProductoidproductoOfUsuarioCollectionNewUsuario);
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
                Integer id = producto.getIdProducto();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
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
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getIdProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Compra> compraCollectionOrphanCheck = producto.getCompraCollection();
            for (Compra compraCollectionOrphanCheckCompra : compraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Compra " + compraCollectionOrphanCheckCompra + " in its compraCollection field has a non-nullable productoidproducto field.");
            }
            Collection<Usuario> usuarioCollectionOrphanCheck = producto.getUsuarioCollection();
            for (Usuario usuarioCollectionOrphanCheckUsuario : usuarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Usuario " + usuarioCollectionOrphanCheckUsuario + " in its usuarioCollection field has a non-nullable productoidproducto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Categoria categoriaidcategoria = producto.getCategoriaidcategoria();
            if (categoriaidcategoria != null) {
                categoriaidcategoria.getProductoCollection().remove(producto);
                categoriaidcategoria = em.merge(categoriaidcategoria);
            }
            Intercambio intercambioidintercambio = producto.getIntercambioidintercambio();
            if (intercambioidintercambio != null) {
                intercambioidintercambio.getProductoCollection().remove(producto);
                intercambioidintercambio = em.merge(intercambioidintercambio);
            }
            Intercambio intercambioidintercambio1 = producto.getIntercambioidintercambio1();
            if (intercambioidintercambio1 != null) {
                intercambioidintercambio1.getProductoCollection().remove(producto);
                intercambioidintercambio1 = em.merge(intercambioidintercambio1);
            }
            em.remove(producto);
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

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
