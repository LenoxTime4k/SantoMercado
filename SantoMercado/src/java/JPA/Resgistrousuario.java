/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPA;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kevin_PC
 */
@Entity
@Table(name = "resgistrousuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Resgistrousuario.findAll", query = "SELECT r FROM Resgistrousuario r")
    , @NamedQuery(name = "Resgistrousuario.findById", query = "SELECT r FROM Resgistrousuario r WHERE r.id = :id")
    , @NamedQuery(name = "Resgistrousuario.findByRutReg", query = "SELECT r FROM Resgistrousuario r WHERE r.rutReg = :rutReg")
    , @NamedQuery(name = "Resgistrousuario.findByNombreCompleto", query = "SELECT r FROM Resgistrousuario r WHERE r.nombreCompleto = :nombreCompleto")
    , @NamedQuery(name = "Resgistrousuario.findByComunaresidencia", query = "SELECT r FROM Resgistrousuario r WHERE r.comunaresidencia = :comunaresidencia")
    , @NamedQuery(name = "Resgistrousuario.findByEdad", query = "SELECT r FROM Resgistrousuario r WHERE r.edad = :edad")
    , @NamedQuery(name = "Resgistrousuario.findBySede", query = "SELECT r FROM Resgistrousuario r WHERE r.sede = :sede")})
public class Resgistrousuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "rut_reg")
    private String rutReg;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre_completo")
    private String nombreCompleto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Comuna_residencia")
    private String comunaresidencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "edad")
    private int edad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "sede")
    private String sede;

    public Resgistrousuario() {
    }

    public Resgistrousuario(Integer id) {
        this.id = id;
    }

    public Resgistrousuario(Integer id, String rutReg, String nombreCompleto, String comunaresidencia, int edad, String sede) {
        this.id = id;
        this.rutReg = rutReg;
        this.nombreCompleto = nombreCompleto;
        this.comunaresidencia = comunaresidencia;
        this.edad = edad;
        this.sede = sede;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRutReg() {
        return rutReg;
    }

    public void setRutReg(String rutReg) {
        this.rutReg = rutReg;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getComunaresidencia() {
        return comunaresidencia;
    }

    public void setComunaresidencia(String comunaresidencia) {
        this.comunaresidencia = comunaresidencia;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resgistrousuario)) {
            return false;
        }
        Resgistrousuario other = (Resgistrousuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JPA.Resgistrousuario[ id=" + id + " ]";
    }
    
}
