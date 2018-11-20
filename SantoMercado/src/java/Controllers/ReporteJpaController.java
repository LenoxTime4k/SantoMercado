/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import JPA.Compra;
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
public class ReporteJpaController implements Serializable {

    public ReporteJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reporte reporte) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Compra compraidcompra = reporte.getCompraidcompra();
            if (compraidcompra != null) {
                compraidcompra = em.getReference(compraidcompra.getClass(), compraidcompra.getIdCompra());
                reporte.setCompraidcompra(compraidcompra);
            }
            Usuario usuariorutusuario = reporte.getUsuariorutusuario();
            if (usuariorutusuario != null) {
                usuariorutusuario = em.getReference(usuariorutusuario.getClass(), usuariorutusuario.getRutUsuario());
                reporte.setUsuariorutusuario(usuariorutusuario);
            }
            em.persist(reporte);
            if (compraidcompra != null) {
                compraidcompra.getReporteCollection().add(reporte);
                compraidcompra = em.merge(compraidcompra);
            }
            if (usuariorutusuario != null) {
                usuariorutusuario.getReporteCollection().add(reporte);
                usuariorutusuario = em.merge(usuariorutusuario);
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

    public void edit(Reporte reporte) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Reporte persistentReporte = em.find(Reporte.class, reporte.getIdReporte());
            Compra compraidcompraOld = persistentReporte.getCompraidcompra();
            Compra compraidcompraNew = reporte.getCompraidcompra();
            Usuario usuariorutusuarioOld = persistentReporte.getUsuariorutusuario();
            Usuario usuariorutusuarioNew = reporte.getUsuariorutusuario();
            if (compraidcompraNew != null) {
                compraidcompraNew = em.getReference(compraidcompraNew.getClass(), compraidcompraNew.getIdCompra());
                reporte.setCompraidcompra(compraidcompraNew);
            }
            if (usuariorutusuarioNew != null) {
                usuariorutusuarioNew = em.getReference(usuariorutusuarioNew.getClass(), usuariorutusuarioNew.getRutUsuario());
                reporte.setUsuariorutusuario(usuariorutusuarioNew);
            }
            reporte = em.merge(reporte);
            if (compraidcompraOld != null && !compraidcompraOld.equals(compraidcompraNew)) {
                compraidcompraOld.getReporteCollection().remove(reporte);
                compraidcompraOld = em.merge(compraidcompraOld);
            }
            if (compraidcompraNew != null && !compraidcompraNew.equals(compraidcompraOld)) {
                compraidcompraNew.getReporteCollection().add(reporte);
                compraidcompraNew = em.merge(compraidcompraNew);
            }
            if (usuariorutusuarioOld != null && !usuariorutusuarioOld.equals(usuariorutusuarioNew)) {
                usuariorutusuarioOld.getReporteCollection().remove(reporte);
                usuariorutusuarioOld = em.merge(usuariorutusuarioOld);
            }
            if (usuariorutusuarioNew != null && !usuariorutusuarioNew.equals(usuariorutusuarioOld)) {
                usuariorutusuarioNew.getReporteCollection().add(reporte);
                usuariorutusuarioNew = em.merge(usuariorutusuarioNew);
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
                Integer id = reporte.getIdReporte();
                if (findReporte(id) == null) {
                    throw new NonexistentEntityException("The reporte with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Reporte reporte;
            try {
                reporte = em.getReference(Reporte.class, id);
                reporte.getIdReporte();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reporte with id " + id + " no longer exists.", enfe);
            }
            Compra compraidcompra = reporte.getCompraidcompra();
            if (compraidcompra != null) {
                compraidcompra.getReporteCollection().remove(reporte);
                compraidcompra = em.merge(compraidcompra);
            }
            Usuario usuariorutusuario = reporte.getUsuariorutusuario();
            if (usuariorutusuario != null) {
                usuariorutusuario.getReporteCollection().remove(reporte);
                usuariorutusuario = em.merge(usuariorutusuario);
            }
            em.remove(reporte);
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

    public List<Reporte> findReporteEntities() {
        return findReporteEntities(true, -1, -1);
    }

    public List<Reporte> findReporteEntities(int maxResults, int firstResult) {
        return findReporteEntities(false, maxResults, firstResult);
    }

    private List<Reporte> findReporteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reporte.class));
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

    public Reporte findReporte(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reporte.class, id);
        } finally {
            em.close();
        }
    }

    public int getReporteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reporte> rt = cq.from(Reporte.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
