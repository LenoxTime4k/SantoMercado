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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "intercambio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Intercambio.findAll", query = "SELECT i FROM Intercambio i")
    , @NamedQuery(name = "Intercambio.findByIdIntercambio", query = "SELECT i FROM Intercambio i WHERE i.idIntercambio = :idIntercambio")
    , @NamedQuery(name = "Intercambio.findByProducto1Id", query = "SELECT i FROM Intercambio i WHERE i.producto1Id = :producto1Id")
    , @NamedQuery(name = "Intercambio.findByProducto2Id", query = "SELECT i FROM Intercambio i WHERE i.producto2Id = :producto2Id")})
public class Intercambio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_intercambio")
    private Integer idIntercambio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "producto1_id")
    private String producto1Id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "producto2_id")
    private String producto2Id;
    @JoinColumn(name = "EstadoIntercambio_id_Estado", referencedColumnName = "id_Estado")
    @ManyToOne(optional = false)
    private Estadointercambio estadoIntercambioidEstado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "intercambioidintercambio")
    private Collection<Producto> productoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "intercambioidintercambio1")
    private Collection<Producto> productoCollection1;

    public Intercambio() {
    }

    public Intercambio(Integer idIntercambio) {
        this.idIntercambio = idIntercambio;
    }

    public Intercambio(Integer idIntercambio, String producto1Id, String producto2Id) {
        this.idIntercambio = idIntercambio;
        this.producto1Id = producto1Id;
        this.producto2Id = producto2Id;
    }

    public Integer getIdIntercambio() {
        return idIntercambio;
    }

    public void setIdIntercambio(Integer idIntercambio) {
        this.idIntercambio = idIntercambio;
    }

    public String getProducto1Id() {
        return producto1Id;
    }

    public void setProducto1Id(String producto1Id) {
        this.producto1Id = producto1Id;
    }

    public String getProducto2Id() {
        return producto2Id;
    }

    public void setProducto2Id(String producto2Id) {
        this.producto2Id = producto2Id;
    }

    public Estadointercambio getEstadoIntercambioidEstado() {
        return estadoIntercambioidEstado;
    }

    public void setEstadoIntercambioidEstado(Estadointercambio estadoIntercambioidEstado) {
        this.estadoIntercambioidEstado = estadoIntercambioidEstado;
    }

    @XmlTransient
    public Collection<Producto> getProductoCollection() {
        return productoCollection;
    }

    public void setProductoCollection(Collection<Producto> productoCollection) {
        this.productoCollection = productoCollection;
    }

    @XmlTransient
    public Collection<Producto> getProductoCollection1() {
        return productoCollection1;
    }

    public void setProductoCollection1(Collection<Producto> productoCollection1) {
        this.productoCollection1 = productoCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idIntercambio != null ? idIntercambio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Intercambio)) {
            return false;
        }
        Intercambio other = (Intercambio) object;
        if ((this.idIntercambio == null && other.idIntercambio != null) || (this.idIntercambio != null && !this.idIntercambio.equals(other.idIntercambio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Data.Intercambio[ idIntercambio=" + idIntercambio + " ]";
    }
    
}
