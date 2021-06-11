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
@Table(name = "platillo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Platillo.findAll", query = "SELECT p FROM Platillo p")
    , @NamedQuery(name = "Platillo.findByIdplatillo", query = "SELECT p FROM Platillo p WHERE p.idplatillo = :idplatillo")
    , @NamedQuery(name = "Platillo.findByUnidadMedida", query = "SELECT p FROM Platillo p WHERE p.unidadMedida = :unidadMedida")
    , @NamedQuery(name = "Platillo.findByPrecio", query = "SELECT p FROM Platillo p WHERE p.precio = :precio")})
public class Platillo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idplatillo")
    private Integer idplatillo;
    @Column(name = "unidad_medida")
    private String unidadMedida;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio")
    private Double precio;
    @JoinTable(name = "menuplatillo", joinColumns = {
        @JoinColumn(name = "idplatillo", referencedColumnName = "idplatillo")}, inverseJoinColumns = {
        @JoinColumn(name = "nummenu", referencedColumnName = "nummenu")})
    @ManyToMany
    private List<Menu> menuList;
    @ManyToMany(mappedBy = "platilloList")
    private List<Ingrediente> ingredienteList;
    @JoinTable(name = "ordenplatillo", joinColumns = {
        @JoinColumn(name = "idplatillo", referencedColumnName = "idplatillo")}, inverseJoinColumns = {
        @JoinColumn(name = "numorden", referencedColumnName = "numorden")})
    @ManyToMany
    private List<Orden> ordenList;
    @JoinColumn(name = "nummenu", referencedColumnName = "nummenu")
    @ManyToOne
    private Menu nummenu;

    public Platillo() {
    }

    public Platillo(Integer idplatillo) {
        this.idplatillo = idplatillo;
    }

    public Integer getIdplatillo() {
        return idplatillo;
    }

    public void setIdplatillo(Integer idplatillo) {
        this.idplatillo = idplatillo;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @XmlTransient
    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    @XmlTransient
    public List<Ingrediente> getIngredienteList() {
        return ingredienteList;
    }

    public void setIngredienteList(List<Ingrediente> ingredienteList) {
        this.ingredienteList = ingredienteList;
    }

    @XmlTransient
    public List<Orden> getOrdenList() {
        return ordenList;
    }

    public void setOrdenList(List<Orden> ordenList) {
        this.ordenList = ordenList;
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
        hash += (idplatillo != null ? idplatillo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Platillo)) {
            return false;
        }
        Platillo other = (Platillo) object;
        if ((this.idplatillo == null && other.idplatillo != null) || (this.idplatillo != null && !this.idplatillo.equals(other.idplatillo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Platillo[ idplatillo=" + idplatillo + " ]";
    }
    
}
