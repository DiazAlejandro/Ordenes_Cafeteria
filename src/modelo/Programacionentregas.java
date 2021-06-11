/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aleja
 */
@Entity
@Table(name = "programacionentregas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Programacionentregas.findAll", query = "SELECT p FROM Programacionentregas p")
    , @NamedQuery(name = "Programacionentregas.findByIdentrega", query = "SELECT p FROM Programacionentregas p WHERE p.identrega = :identrega")
    , @NamedQuery(name = "Programacionentregas.findByDetalle", query = "SELECT p FROM Programacionentregas p WHERE p.detalle = :detalle")
    , @NamedQuery(name = "Programacionentregas.findByFecha", query = "SELECT p FROM Programacionentregas p WHERE p.fecha = :fecha")})
public class Programacionentregas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "identrega")
    private Integer identrega;
    @Column(name = "detalle")
    private String detalle;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    public Programacionentregas() {
    }

    public Programacionentregas(Integer identrega) {
        this.identrega = identrega;
    }

    public Integer getIdentrega() {
        return identrega;
    }

    public void setIdentrega(Integer identrega) {
        this.identrega = identrega;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (identrega != null ? identrega.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Programacionentregas)) {
            return false;
        }
        Programacionentregas other = (Programacionentregas) object;
        if ((this.identrega == null && other.identrega != null) || (this.identrega != null && !this.identrega.equals(other.identrega))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Programacionentregas[ identrega=" + identrega + " ]";
    }
    
}
