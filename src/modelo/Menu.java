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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "menu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m")
    , @NamedQuery(name = "Menu.findByNummenu", query = "SELECT m FROM Menu m WHERE m.nummenu = :nummenu")
    , @NamedQuery(name = "Menu.findByFecha", query = "SELECT m FROM Menu m WHERE m.fecha = :fecha")
    , @NamedQuery(name = "Menu.findByHora", query = "SELECT m FROM Menu m WHERE m.hora = :hora")})
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nummenu")
    private Integer nummenu;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @ManyToMany(mappedBy = "menuList")
    private List<Platillo> platilloList;
    @ManyToMany(mappedBy = "menuList")
    private List<Bebida> bebidaList;
    @OneToMany(mappedBy = "nummenu")
    private List<Platillo> platilloList1;
    @OneToMany(mappedBy = "nummenu")
    private List<Bebida> bebidaList1;

    public Menu() {
    }

    public Menu(Integer nummenu) {
        this.nummenu = nummenu;
    }

    public Integer getNummenu() {
        return nummenu;
    }

    public void setNummenu(Integer nummenu) {
        this.nummenu = nummenu;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    @XmlTransient
    public List<Platillo> getPlatilloList() {
        return platilloList;
    }

    public void setPlatilloList(List<Platillo> platilloList) {
        this.platilloList = platilloList;
    }

    @XmlTransient
    public List<Bebida> getBebidaList() {
        return bebidaList;
    }

    public void setBebidaList(List<Bebida> bebidaList) {
        this.bebidaList = bebidaList;
    }

    @XmlTransient
    public List<Platillo> getPlatilloList1() {
        return platilloList1;
    }

    public void setPlatilloList1(List<Platillo> platilloList1) {
        this.platilloList1 = platilloList1;
    }

    @XmlTransient
    public List<Bebida> getBebidaList1() {
        return bebidaList1;
    }

    public void setBebidaList1(List<Bebida> bebidaList1) {
        this.bebidaList1 = bebidaList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nummenu != null ? nummenu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Menu)) {
            return false;
        }
        Menu other = (Menu) object;
        if ((this.nummenu == null && other.nummenu != null) || (this.nummenu != null && !this.nummenu.equals(other.nummenu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Menu[ nummenu=" + nummenu + " ]";
    }
    
}
