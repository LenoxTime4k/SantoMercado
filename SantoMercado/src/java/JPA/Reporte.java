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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kevin_PC
 */
@Entity
@Table(name = "reporte")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reporte.findAll", query = "SELECT r FROM Reporte r")
    , @NamedQuery(name = "Reporte.findByIdReporte", query = "SELECT r FROM Reporte r WHERE r.idReporte = :idReporte")
    , @NamedQuery(name = "Reporte.findByPuntuacionNumerica", query = "SELECT r FROM Reporte r WHERE r.puntuacionNumerica = :puntuacionNumerica")})
public class Reporte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_reporte")
    private Integer idReporte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "puntuacion_numerica")
    private int puntuacionNumerica;
    @JoinColumn(name = "Compra_id_compra", referencedColumnName = "id_compra")
    @ManyToOne(optional = false)
    private Compra compraidcompra;
    @JoinColumn(name = "Usuario_rut_usuario", referencedColumnName = "rut_usuario")
    @ManyToOne(optional = false)
    private Usuario usuariorutusuario;

    public Reporte() {
    }

    public Reporte(Integer idReporte) {
        this.idReporte = idReporte;
    }

    public Reporte(Integer idReporte, int puntuacionNumerica) {
        this.idReporte = idReporte;
        this.puntuacionNumerica = puntuacionNumerica;
    }

    public Integer getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(Integer idReporte) {
        this.idReporte = idReporte;
    }

    public int getPuntuacionNumerica() {
        return puntuacionNumerica;
    }

    public void setPuntuacionNumerica(int puntuacionNumerica) {
        this.puntuacionNumerica = puntuacionNumerica;
    }

    public Compra getCompraidcompra() {
        return compraidcompra;
    }

    public void setCompraidcompra(Compra compraidcompra) {
        this.compraidcompra = compraidcompra;
    }

    public Usuario getUsuariorutusuario() {
        return usuariorutusuario;
    }

    public void setUsuariorutusuario(Usuario usuariorutusuario) {
        this.usuariorutusuario = usuariorutusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReporte != null ? idReporte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reporte)) {
            return false;
        }
        Reporte other = (Reporte) object;
        if ((this.idReporte == null && other.idReporte != null) || (this.idReporte != null && !this.idReporte.equals(other.idReporte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Data.Reporte[ idReporte=" + idReporte + " ]";
    }
    
}
