/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPA;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kevin_PC
 */
@Entity
@Table(name = "estadointercambio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estadointercambio.findAll", query = "SELECT e FROM Estadointercambio e")
    , @NamedQuery(name = "Estadointercambio.findByIdEstado", query = "SELECT e FROM Estadointercambio e WHERE e.idEstado = :idEstado")
    , @NamedQuery(name = "Estadointercambio.findByNombreEstado", query = "SELECT e FROM Estadointercambio e WHERE e.nombreEstado = :nombreEstado")})
public class Estadointercambio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_Estado")
    private Integer idEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_estado")
    private String nombreEstado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadoIntercambioidEstado")
    private Collection<Intercambio> intercambioCollection;

    public Estadointercambio() {
    }

    public Estadointercambio(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public Estadointercambio(Integer idEstado, String nombreEstado) {
        this.idEstado = idEstado;
        this.nombreEstado = nombreEstado;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    @XmlTransient
    public Collection<Intercambio> getIntercambioCollection() {
        return intercambioCollection;
    }

    public void setIntercambioCollection(Collection<Intercambio> intercambioCollection) {
        this.intercambioCollection = intercambioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstado != null ? idEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estadointercambio)) {
            return false;
        }
        Estadointercambio other = (Estadointercambio) object;
        if ((this.idEstado == null && other.idEstado != null) || (this.idEstado != null && !this.idEstado.equals(other.idEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Data.Estadointercambio[ idEstado=" + idEstado + " ]";
    }
    
}
