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
@Table(name = "bebida")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bebida.findAll", query = "SELECT b FROM Bebida b")
    , @NamedQuery(name = "Bebida.findByIdbebida", query = "SELECT b FROM Bebida b WHERE b.idbebida = :idbebida")
    , @NamedQuery(name = "Bebida.findByNombre", query = "SELECT b FROM Bebida b WHERE b.nombre = :nombre")
    , @NamedQuery(name = "Bebida.findByTipo", query = "SELECT b FROM Bebida b WHERE b.tipo = :tipo")})
public class Bebida implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idbebida")
    private Integer idbebida;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "tipo")
    private String tipo;
    @JoinTable(name = "ordenbebidas", joinColumns = {
        @JoinColumn(name = "idbebida", referencedColumnName = "idbebida")}, inverseJoinColumns = {
        @JoinColumn(name = "numorden", referencedColumnName = "numorden")})
    @ManyToMany
    private List<Orden> ordenList;
    @JoinTable(name = "menubebida", joinColumns = {
        @JoinColumn(name = "idbebida", referencedColumnName = "idbebida")}, inverseJoinColumns = {
        @JoinColumn(name = "nummenu", referencedColumnName = "nummenu")})
    @ManyToMany
    private List<Menu> menuList;
    @JoinColumn(name = "nummenu", referencedColumnName = "nummenu")
    @ManyToOne
    private Menu nummenu;

    public Bebida() {
    }

    public Bebida(Integer idbebida) {
        this.idbebida = idbebida;
    }

    public Integer getIdbebida() {
        return idbebida;
    }

    public void setIdbebida(Integer idbebida) {
        this.idbebida = idbebida;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public List<Orden> getOrdenList() {
        return ordenList;
    }

    public void setOrdenList(List<Orden> ordenList) {
        this.ordenList = ordenList;
    }

    @XmlTransient
    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public Menu getNummenu() {
        return nummenu;
    }

    public void setNummenu(Menu nummenu) {
        this.nummenu = nummenu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idbebida != null ? idbebida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bebida)) {
            return false;
        }
        Bebida other = (Bebida) object;
        if ((this.idbebida == null && other.idbebida != null) || (this.idbebida != null && !this.idbebida.equals(other.idbebida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Bebida[ idbebida=" + idbebida + " ]";
    }
    
}
