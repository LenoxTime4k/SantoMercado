/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPA;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kevin_PC
 */
@Entity
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByRutUsuario", query = "SELECT u FROM Usuario u WHERE u.rutUsuario = :rutUsuario")
    , @NamedQuery(name = "Usuario.findByNombreUsu", query = "SELECT u FROM Usuario u WHERE u.nombreUsu = :nombreUsu")
    , @NamedQuery(name = "Usuario.findByApellido", query = "SELECT u FROM Usuario u WHERE u.apellido = :apellido")
    , @NamedQuery(name = "Usuario.findByFechaNac", query = "SELECT u FROM Usuario u WHERE u.fechaNac = :fechaNac")
    , @NamedQuery(name = "Usuario.findByCorreoUsu", query = "SELECT u FROM Usuario u WHERE u.correoUsu = :correoUsu")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rut_usuario")
    private Integer rutUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_usu")
    private String nombreUsu;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "apellido")
    private String apellido;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_nac")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNac;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "correo_usu")
    private String correoUsu;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuariorutusuario")
    private Collection<Compra> compraCollection;
    @JoinColumn(name = "Comuna_idComuna", referencedColumnName = "idComuna")
    @ManyToOne(optional = false)
    private Comuna comunaidComuna;
    @JoinColumn(name = "Producto_id_producto", referencedColumnName = "id_producto")
    @ManyToOne(optional = false)
    private Producto productoidproducto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuariorutusuario")
    private Collection<Reporte> reporteCollection;

    public Usuario() {
    }

    public Usuario(Integer rutUsuario) {
        this.rutUsuario = rutUsuario;
    }

    public Usuario(Integer rutUsuario, String nombreUsu, String apellido, Date fechaNac, String correoUsu) {
        this.rutUsuario = rutUsuario;
        this.nombreUsu = nombreUsu;
        this.apellido = apellido;
        this.fechaNac = fechaNac;
        this.correoUsu = correoUsu;
    }

    public Integer getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(Integer rutUsuario) {
        this.rutUsuario = rutUsuario;
    }

    public String getNombreUsu() {
        return nombreUsu;
    }

    public void setNombreUsu(String nombreUsu) {
        this.nombreUsu = nombreUsu;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getCorreoUsu() {
        return correoUsu;
    }

    public void setCorreoUsu(String correoUsu) {
        this.correoUsu = correoUsu;
    }

    @XmlTransient
    public Collection<Compra> getCompraCollection() {
        return compraCollection;
    }

    public void setCompraCollection(Collection<Compra> compraCollection) {
        this.compraCollection = compraCollection;
    }

    public Comuna getComunaidComuna() {
        return comunaidComuna;
    }

    public void setComunaidComuna(Comuna comunaidComuna) {
        this.comunaidComuna = comunaidComuna;
    }

    public Producto getProductoidproducto() {
        return productoidproducto;
    }

    public void setProductoidproducto(Producto productoidproducto) {
        this.productoidproducto = productoidproducto;
    }

    @XmlTransient
    public Collection<Reporte> getReporteCollection() {
        return reporteCollection;
    }

    public void setReporteCollection(Collection<Reporte> reporteCollection) {
        this.reporteCollection = reporteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rutUsuario != null ? rutUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.rutUsuario == null && other.rutUsuario != null) || (this.rutUsuario != null && !this.rutUsuario.equals(other.rutUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Data.Usuario[ rutUsuario=" + rutUsuario + " ]";
    }
    
}
