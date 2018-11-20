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
import JPA.Comuna;
import JPA.Producto;
import JPA.Compra;
import java.util.ArrayList;
import java.util.Collection;
import JPA.Reporte;
import JPA.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Kevin_PC
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws RollbackFailureException, Exception {
        if (usuario.getCompraCollection() == null) {
            usuario.setCompraCollection(new ArrayList<Compra>());
        }
        if (usuario.getReporteCollection() == null) {
            usuario.setReporteCollection(new ArrayList<Reporte>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Comuna comunaidComuna = usuario.getComunaidComuna();
            if (comunaidComuna != null) {
                comunaidComuna = em.getReference(comunaidComuna.getClass(), comunaidComuna.getIdComuna());
                usuario.setComunaidComuna(comunaidComuna);
            }
            Producto productoidproducto = usuario.getProductoidproducto();
            if (productoidproducto != null) {
                productoidproducto = em.getReference(productoidproducto.getClass(), productoidproducto.getIdProducto());
                usuario.setProductoidproducto(productoidproducto);
            }
            Collection<Compra> attachedCompraCollection = new ArrayList<Compra>();
            for (Compra compraCollectionCompraToAttach : usuario.getCompraCollection()) {
                compraCollectionCompraToAttach = em.getReference(compraCollectionCompraToAttach.getClass(), compraCollectionCompraToAttach.getIdCompra());
                attachedCompraCollection.add(compraCollectionCompraToAttach);
            }
            usuario.setCompraCollection(attachedCompraCollection);
            Collection<Reporte> attachedReporteCollection = new ArrayList<Reporte>();
            for (Reporte reporteCollectionReporteToAttach : usuario.getReporteCollection()) {
                reporteCollectionReporteToAttach = em.getReference(reporteCollectionReporteToAttach.getClass(), reporteCollectionReporteToAttach.getIdReporte());
                attachedReporteCollection.add(reporteCollectionReporteToAttach);
            }
            usuario.setReporteCollection(attachedReporteCollection);
            em.persist(usuario);
            if (comunaidComuna != null) {
                comunaidComuna.getUsuarioCollection().add(usuario);
                comunaidComuna = em.merge(comunaidComuna);
            }
            if (productoidproducto != null) {
                productoidproducto.getUsuarioCollection().add(usuario);
                productoidproducto = em.merge(productoidproducto);
            }
            for (Compra compraCollectionCompra : usuario.getCompraCollection()) {
                Usuario oldUsuariorutusuarioOfCompraCollectionCompra = compraCollectionCompra.getUsuariorutusuario();
                compraCollectionCompra.setUsuariorutusuario(usuario);
                compraCollectionCompra = em.merge(compraCollectionCompra);
                if (oldUsuariorutusuarioOfCompraCollectionCompra != null) {
                    oldUsuariorutusuarioOfCompraCollectionCompra.getCompraCollection().remove(compraCollectionCompra);
                    oldUsuariorutusuarioOfCompraCollectionCompra = em.merge(oldUsuariorutusuarioOfCompraCollectionCompra);
                }
            }
            for (Reporte reporteCollectionReporte : usuario.getReporteCollection()) {
                Usuario oldUsuariorutusuarioOfReporteCollectionReporte = reporteCollectionReporte.getUsuariorutusuario();
                reporteCollectionReporte.setUsuariorutusuario(usuario);
                reporteCollectionReporte = em.merge(reporteCollectionReporte);
                if (oldUsuariorutusuarioOfReporteCollectionReporte != null) {
                    oldUsuariorutusuarioOfReporteCollectionReporte.getReporteCollection().remove(reporteCollectionReporte);
                    oldUsuariorutusuarioOfReporteCollectionReporte = em.merge(oldUsuariorutusuarioOfReporteCollectionReporte);
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

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getRutUsuario());
            Comuna comunaidComunaOld = persistentUsuario.getComunaidComuna();
            Comuna comunaidComunaNew = usuario.getComunaidComuna();
            Producto productoidproductoOld = persistentUsuario.getProductoidproducto();
            Producto productoidproductoNew = usuario.getProductoidproducto();
            Collection<Compra> compraCollectionOld = persistentUsuario.getCompraCollection();
            Collection<Compra> compraCollectionNew = usuario.getCompraCollection();
            Collection<Reporte> reporteCollectionOld = persistentUsuario.getReporteCollection();
            Collection<Reporte> reporteCollectionNew = usuario.getReporteCollection();
            List<String> illegalOrphanMessages = null;
            for (Compra compraCollectionOldCompra : compraCollectionOld) {
                if (!compraCollectionNew.contains(compraCollectionOldCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Compra " + compraCollectionOldCompra + " since its usuariorutusuario field is not nullable.");
                }
            }
            for (Reporte reporteCollectionOldReporte : reporteCollectionOld) {
                if (!reporteCollectionNew.contains(reporteCollectionOldReporte)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reporte " + reporteCollectionOldReporte + " since its usuariorutusuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (comunaidComunaNew != null) {
                comunaidComunaNew = em.getReference(comunaidComunaNew.getClass(), comunaidComunaNew.getIdComuna());
                usuario.setComunaidComuna(comunaidComunaNew);
            }
            if (productoidproductoNew != null) {
                productoidproductoNew = em.getReference(productoidproductoNew.getClass(), productoidproductoNew.getIdProducto());
                usuario.setProductoidproducto(productoidproductoNew);
            }
            Collection<Compra> attachedCompraCollectionNew = new ArrayList<Compra>();
            for (Compra compraCollectionNewCompraToAttach : compraCollectionNew) {
                compraCollectionNewCompraToAttach = em.getReference(compraCollectionNewCompraToAttach.getClass(), compraCollectionNewCompraToAttach.getIdCompra());
                attachedCompraCollectionNew.add(compraCollectionNewCompraToAttach);
            }
            compraCollectionNew = attachedCompraCollectionNew;
            usuario.setCompraCollection(compraCollectionNew);
            Collection<Reporte> attachedReporteCollectionNew = new ArrayList<Reporte>();
            for (Reporte reporteCollectionNewReporteToAttach : reporteCollectionNew) {
                reporteCollectionNewReporteToAttach = em.getReference(reporteCollectionNewReporteToAttach.getClass(), reporteCollectionNewReporteToAttach.getIdReporte());
                attachedReporteCollectionNew.add(reporteCollectionNewReporteToAttach);
            }
            reporteCollectionNew = attachedReporteCollectionNew;
            usuario.setReporteCollection(reporteCollectionNew);
            usuario = em.merge(usuario);
            if (comunaidComunaOld != null && !comunaidComunaOld.equals(comunaidComunaNew)) {
                comunaidComunaOld.getUsuarioCollection().remove(usuario);
                comunaidComunaOld = em.merge(comunaidComunaOld);
            }
            if (comunaidComunaNew != null && !comunaidComunaNew.equals(comunaidComunaOld)) {
                comunaidComunaNew.getUsuarioCollection().add(usuario);
                comunaidComunaNew = em.merge(comunaidComunaNew);
            }
            if (productoidproductoOld != null && !productoidproductoOld.equals(productoidproductoNew)) {
                productoidproductoOld.getUsuarioCollection().remove(usuario);
                productoidproductoOld = em.merge(productoidproductoOld);
            }
            if (productoidproductoNew != null && !productoidproductoNew.equals(productoidproductoOld)) {
                productoidproductoNew.getUsuarioCollection().add(usuario);
                productoidproductoNew = em.merge(productoidproductoNew);
            }
            for (Compra compraCollectionNewCompra : compraCollectionNew) {
                if (!compraCollectionOld.contains(compraCollectionNewCompra)) {
                    Usuario oldUsuariorutusuarioOfCompraCollectionNewCompra = compraCollectionNewCompra.getUsuariorutusuario();
                    compraCollectionNewCompra.setUsuariorutusuario(usuario);
                    compraCollectionNewCompra = em.merge(compraCollectionNewCompra);
                    if (oldUsuariorutusuarioOfCompraCollectionNewCompra != null && !oldUsuariorutusuarioOfCompraCollectionNewCompra.equals(usuario)) {
                        oldUsuariorutusuarioOfCompraCollectionNewCompra.getCompraCollection().remove(compraCollectionNewCompra);
                        oldUsuariorutusuarioOfCompraCollectionNewCompra = em.merge(oldUsuariorutusuarioOfCompraCollectionNewCompra);
                    }
                }
            }
            for (Reporte reporteCollectionNewReporte : reporteCollectionNew) {
                if (!reporteCollectionOld.contains(reporteCollectionNewReporte)) {
                    Usuario oldUsuariorutusuarioOfReporteCollectionNewReporte = reporteCollectionNewReporte.getUsuariorutusuario();
                    reporteCollectionNewReporte.setUsuariorutusuario(usuario);
                    reporteCollectionNewReporte = em.merge(reporteCollectionNewReporte);
                    if (oldUsuariorutusuarioOfReporteCollectionNewReporte != null && !oldUsuariorutusuarioOfReporteCollectionNewReporte.equals(usuario)) {
                        oldUsuariorutusuarioOfReporteCollectionNewReporte.getReporteCollection().remove(reporteCollectionNewReporte);
                        oldUsuariorutusuarioOfReporteCollectionNewReporte = em.merge(oldUsuariorutusuarioOfReporteCollectionNewReporte);
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
                Integer id = usuario.getRutUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getRutUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Compra> compraCollectionOrphanCheck = usuario.getCompraCollection();
            for (Compra compraCollectionOrphanCheckCompra : compraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Compra " + compraCollectionOrphanCheckCompra + " in its compraCollection field has a non-nullable usuariorutusuario field.");
            }
            Collection<Reporte> reporteCollectionOrphanCheck = usuario.getReporteCollection();
            for (Reporte reporteCollectionOrphanCheckReporte : reporteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Reporte " + reporteCollectionOrphanCheckReporte + " in its reporteCollection field has a non-nullable usuariorutusuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Comuna comunaidComuna = usuario.getComunaidComuna();
            if (comunaidComuna != null) {
                comunaidComuna.getUsuarioCollection().remove(usuario);
                comunaidComuna = em.merge(comunaidComuna);
            }
            Producto productoidproducto = usuario.getProductoidproducto();
            if (productoidproducto != null) {
                productoidproducto.getUsuarioCollection().remove(usuario);
                productoidproducto = em.merge(productoidproducto);
            }
            em.remove(usuario);
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

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
