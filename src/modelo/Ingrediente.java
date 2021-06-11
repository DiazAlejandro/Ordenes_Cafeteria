/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aleja
 */
@Entity
@Table(name = "ingrediente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ingrediente.findAll", query = "SELECT i FROM Ingrediente i")
    , @NamedQuery(name = "Ingrediente.findByIding", query = "SELECT i FROM Ingrediente i WHERE i.iding = :iding")
    , @NamedQuery(name = "Ingrediente.findByNombre", query = "SELECT i FROM Ingrediente i WHERE i.nombre = :nombre")})
public class Ingrediente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "iding")
    private Integer iding;
    @Column(name = "nombre")
    private String nombre;
    @JoinTable(name = "platilloing", joinColumns = {
        @JoinColumn(name = "iding", referencedColumnName = "iding")}, inverseJoinColumns = {
        @JoinColumn(name = "idplatillo", referencedColumnName = "idplatillo")})
    @ManyToMany
    private List<Platillo> platilloList;
    @JoinColumn(name = "tipoing", referencedColumnName = "idting")
    @ManyToOne
    private TipoIngrediente tipoing;

    public Ingrediente() {
    }

    public Ingrediente(Integer iding) {
        this.iding = iding;
    }

    public Integer getIding() {
        return iding;
    }

    public void setIding(Integer iding) {
        this.iding = iding;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Platillo> getPlatilloList() {
        return platilloList;
    }

    public void setPlatilloList(List<Platillo> platilloList) {
        this.platilloList = platilloList;
    }

    public TipoIngrediente getTipoing() {
        return tipoing;
    }

    public void setTipoing(TipoIngrediente tipoing) {
        this.tipoing = tipoing;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iding != null ? iding.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ingrediente)) {
            return false;
        }
        Ingrediente other = (Ingrediente) object;
        if ((this.iding == null && other.iding != null) || (this.iding != null && !this.iding.equals(other.iding))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Ingrediente[ iding=" + iding + " ]";
    }
    
}
