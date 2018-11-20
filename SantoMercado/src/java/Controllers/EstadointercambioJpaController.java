/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.exceptions.IllegalOrphanException;
import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.RollbackFailureException;
import JPA.Estadointercambio;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import JPA.Intercambio;
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
public class EstadointercambioJpaController implements Serializable {

    public EstadointercambioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estadointercambio estadointercambio) throws RollbackFailureException, Exception {
        if (estadointercambio.getIntercambioCollection() == null) {
            estadointercambio.setIntercambioCollection(new ArrayList<Intercambio>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Intercambio> attachedIntercambioCollection = new ArrayList<Intercambio>();
            for (Intercambio intercambioCollectionIntercambioToAttach : estadointercambio.getIntercambioCollection()) {
                intercambioCollectionIntercambioToAttach = em.getReference(intercambioCollectionIntercambioToAttach.getClass(), intercambioCollectionIntercambioToAttach.getIdIntercambio());
                attachedIntercambioCollection.add(intercambioCollectionIntercambioToAttach);
            }
            estadointercambio.setIntercambioCollection(attachedIntercambioCollection);
            em.persist(estadointercambio);
            for (Intercambio intercambioCollectionIntercambio : estadointercambio.getIntercambioCollection()) {
                Estadointercambio oldEstadoIntercambioidEstadoOfIntercambioCollectionIntercambio = intercambioCollectionIntercambio.getEstadoIntercambioidEstado();
                intercambioCollectionIntercambio.setEstadoIntercambioidEstado(estadointercambio);
                intercambioCollectionIntercambio = em.merge(intercambioCollectionIntercambio);
                if (oldEstadoIntercambioidEstadoOfIntercambioCollectionIntercambio != null) {
                    oldEstadoIntercambioidEstadoOfIntercambioCollectionIntercambio.getIntercambioCollection().remove(intercambioCollectionIntercambio);
                    oldEstadoIntercambioidEstadoOfIntercambioCollectionIntercambio = em.merge(oldEstadoIntercambioidEstadoOfIntercambioCollectionIntercambio);
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

    public void edit(Estadointercambio estadointercambio) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Estadointercambio persistentEstadointercambio = em.find(Estadointercambio.class, estadointercambio.getIdEstado());
            Collection<Intercambio> intercambioCollectionOld = persistentEstadointercambio.getIntercambioCollection();
            Collection<Intercambio> intercambioCollectionNew = estadointercambio.getIntercambioCollection();
            List<String> illegalOrphanMessages = null;
            for (Intercambio intercambioCollectionOldIntercambio : intercambioCollectionOld) {
                if (!intercambioCollectionNew.contains(intercambioCollectionOldIntercambio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Intercambio " + intercambioCollectionOldIntercambio + " since its estadoIntercambioidEstado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Intercambio> attachedIntercambioCollectionNew = new ArrayList<Intercambio>();
            for (Intercambio intercambioCollectionNewIntercambioToAttach : intercambioCollectionNew) {
                intercambioCollectionNewIntercambioToAttach = em.getReference(intercambioCollectionNewIntercambioToAttach.getClass(), intercambioCollectionNewIntercambioToAttach.getIdIntercambio());
                attachedIntercambioCollectionNew.add(intercambioCollectionNewIntercambioToAttach);
            }
            intercambioCollectionNew = attachedIntercambioCollectionNew;
            estadointercambio.setIntercambioCollection(intercambioCollectionNew);
            estadointercambio = em.merge(estadointercambio);
            for (Intercambio intercambioCollectionNewIntercambio : intercambioCollectionNew) {
                if (!intercambioCollectionOld.contains(intercambioCollectionNewIntercambio)) {
                    Estadointercambio oldEstadoIntercambioidEstadoOfIntercambioCollectionNewIntercambio = intercambioCollectionNewIntercambio.getEstadoIntercambioidEstado();
                    intercambioCollectionNewIntercambio.setEstadoIntercambioidEstado(estadointercambio);
                    intercambioCollectionNewIntercambio = em.merge(intercambioCollectionNewIntercambio);
                    if (oldEstadoIntercambioidEstadoOfIntercambioCollectionNewIntercambio != null && !oldEstadoIntercambioidEstadoOfIntercambioCollectionNewIntercambio.equals(estadointercambio)) {
                        oldEstadoIntercambioidEstadoOfIntercambioCollectionNewIntercambio.getIntercambioCollection().remove(intercambioCollectionNewIntercambio);
                        oldEstadoIntercambioidEstadoOfIntercambioCollectionNewIntercambio = em.merge(oldEstadoIntercambioidEstadoOfIntercambioCollectionNewIntercambio);
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
                Integer id = estadointercambio.getIdEstado();
                if (findEstadointercambio(id) == null) {
                    throw new NonexistentEntityException("The estadointercambio with id " + id + " no longer exists.");
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
            Estadointercambio estadointercambio;
            try {
                estadointercambio = em.getReference(Estadointercambio.class, id);
                estadointercambio.getIdEstado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadointercambio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Intercambio> intercambioCollectionOrphanCheck = estadointercambio.getIntercambioCollection();
            for (Intercambio intercambioCollectionOrphanCheckIntercambio : intercambioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estadointercambio (" + estadointercambio + ") cannot be destroyed since the Intercambio " + intercambioCollectionOrphanCheckIntercambio + " in its intercambioCollection field has a non-nullable estadoIntercambioidEstado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estadointercambio);
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

    public List<Estadointercambio> findEstadointercambioEntities() {
        return findEstadointercambioEntities(true, -1, -1);
    }

    public List<Estadointercambio> findEstadointercambioEntities(int maxResults, int firstResult) {
        return findEstadointercambioEntities(false, maxResults, firstResult);
    }

    private List<Estadointercambio> findEstadointercambioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estadointercambio.class));
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

    public Estadointercambio findEstadointercambio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estadointercambio.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadointercambioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estadointercambio> rt = cq.from(Estadointercambio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
