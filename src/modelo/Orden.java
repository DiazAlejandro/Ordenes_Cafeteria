/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aleja
 */
@Entity
@Table(name = "orden")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orden.findAll", query = "SELECT o FROM Orden o")
    , @NamedQuery(name = "Orden.findByNumorden", query = "SELECT o FROM Orden o WHERE o.numorden = :numorden")
    , @NamedQuery(name = "Orden.findByFecha", query = "SELECT o FROM Orden o WHERE o.fecha = :fecha")
    , @NamedQuery(name = "Orden.findByEstado", query = "SELECT o FROM Orden o WHERE o.estado = :estado")})
public class Orden implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "numorden")
    private Integer numorden;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "estado")
    private Boolean estado;
    @ManyToMany(mappedBy = "ordenList")
    private List<Bebida> bebidaList;
    @ManyToMany(mappedBy = "ordenList")
    private List<Platillo> platilloList;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne
    private Cliente idCliente;

    public Orden() {
    }

    public Orden(Integer numorden) {
        this.numorden = numorden;
    }

    public Integer getNumorden() {
        return numorden;
    }

    public void setNumorden(Integer numorden) {
        this.numorden = numorden;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<Bebida> getBebidaList() {
        return bebidaList;
    }

    public void setBebidaList(List<Bebida> bebidaList) {
        this.bebidaList = bebidaList;
    }

    @XmlTransient
    public List<Platillo> getPlatilloList() {
        return platilloList;
    }

    public void setPlatilloList(List<Platillo> platilloList) {
        this.platilloList = platilloList;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numorden != null ? numorden.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orden)) {
            return false;
        }
        Orden other = (Orden) object;
        if ((this.numorden == null && other.numorden != null) || (this.numorden != null && !this.numorden.equals(other.numorden))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Orden[ numorden=" + numorden + " ]";
    }
    
}
