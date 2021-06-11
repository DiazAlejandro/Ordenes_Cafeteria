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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aleja
 */
@Entity
@Table(name = "tipo_ingrediente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoIngrediente.findAll", query = "SELECT t FROM TipoIngrediente t")
    , @NamedQuery(name = "TipoIngrediente.findByIdting", query = "SELECT t FROM TipoIngrediente t WHERE t.idting = :idting")
    , @NamedQuery(name = "TipoIngrediente.findByNombre", query = "SELECT t FROM TipoIngrediente t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "TipoIngrediente.findByUnidadMedida", query = "SELECT t FROM TipoIngrediente t WHERE t.unidadMedida = :unidadMedida")
    , @NamedQuery(name = "TipoIngrediente.findByEspecie", query = "SELECT t FROM TipoIngrediente t WHERE t.especie = :especie")
    , @NamedQuery(name = "TipoIngrediente.findByCaracteristicas", query = "SELECT t FROM TipoIngrediente t WHERE t.caracteristicas = :caracteristicas")})
public class TipoIngrediente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idting")
    private Integer idting;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "unidad_medida")
    private String unidadMedida;
    @Column(name = "especie")
    private String especie;
    @Column(name = "caracteristicas")
    private String caracteristicas;
    @OneToMany(mappedBy = "tipoing")
    private List<Ingrediente> ingredienteList;

    public TipoIngrediente() {
    }

    public TipoIngrediente(Integer idting) {
        this.idting = idting;
    }

    public Integer getIdting() {
        return idting;
    }

    public void setIdting(Integer idting) {
        this.idting = idting;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    @XmlTransient
    public List<Ingrediente> getIngredienteList() {
        return ingredienteList;
    }

    public void setIngredienteList(List<Ingrediente> ingredienteList) {
        this.ingredienteList = ingredienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idting != null ? idting.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoIngrediente)) {
            return false;
        }
        TipoIngrediente other = (TipoIngrediente) object;
        if ((this.idting == null && other.idting != null) || (this.idting != null && !this.idting.equals(other.idting))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TipoIngrediente[ idting=" + idting + " ]";
    }
    
}
