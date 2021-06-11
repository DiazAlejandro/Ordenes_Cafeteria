/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesoDato;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.TipoIngrediente;

/**
 *
 * @author aleja
 */
public class ModeloTablaTipoIngredientes extends AbstractTableModel {
    //Encabezados del modelo a presentar
    private String encabezados[] = new String[]{
            "ID_TIPO_ING","NOMBRE","UNIDAD_MEDIDA","ESPECIE","CARACTERISTICAS"};
    
    private Class tipos[] = new Class[]{
        Integer.class, String.class, String.class, String.class, String.class
    };
    
    
    private List<TipoIngrediente>datos;
    
    public ModeloTablaTipoIngredientes(List elementos){
        datos = elementos;
    }

    public int getRowCount() {
         return datos.size();
    }

    @Override
    public int getColumnCount() {
        return encabezados.length;
    }

    @Override
    public Object getValueAt(int r, int c) {
         switch(c){
            case 0: return datos.get(r).getIdting();
            case 1: return datos.get(r).getNombre();
            case 2: return datos.get(r).getUnidadMedida();
            case 3: return datos.get(r).getEspecie();
            default: return datos.get(r).getCaracteristicas();
        }
    }
    public String getColumnName(int c){
            return encabezados[c];
    }
    public void setDatos(List elementos){
        datos = elementos;
    }
}
