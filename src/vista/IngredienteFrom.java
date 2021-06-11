/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import accesoDato.TipoIngredienteJpaController;
import accesoDato.IngredienteJpaController;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Ingrediente;
import modelo.TipoIngrediente;

/**
 *
 * @author aleja
 */
public class IngredienteFrom extends javax.swing.JDialog {

    
    
    private EntityManagerFactory         emf; 
    private TipoIngredienteJpaController cti;          //Controlador del TipoIngrediente
    private List <TipoIngrediente>       datoTipoIngrediente;//Lista que almacena los registros de Tipo Ingrediente
    private TipoIngrediente              tipoIng;
    
    private Ingrediente              ingrediente; //Instancia para crear los ingrdientes
    private IngredienteJpaController ctlIng;      //Controlador de Ingrediente
    
    public IngredienteFrom(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        emf = Persistence.createEntityManagerFactory("Ordenes_CafeteriaPU");
        cti = new TipoIngredienteJpaController(emf);
        datoTipoIngrediente = cti.findTipoIngredienteEntities();
        llenarComboBox(datoTipoIngrediente);
        
        /**Guardar Ingrediente*/
        ctlIng = new IngredienteJpaController(emf);
        
    }
    
    /**
     * Método para recorrer la lista de los ingredientes y volcarlos
     * dentro de un combobox.
     * @param tipoIngrediente : Lista del tipo de ingrediente
     */
    public void llenarComboBox(List tipoIngrediente){
        //System.out.println("ITERATOR");
        Iterator<TipoIngrediente> it = tipoIngrediente.iterator(); //Interface Iterator para recorrer la lista
        TipoIngrediente tipoIng;  //Variable del tipo TipoIngrediente
        while (it.hasNext()){     //Ciclo para recorrer el Iterator
            tipoIng = it.next();  //Le pasa a la instnaica de Tipo ingrediente el objeto que haya recorrigo
            System.out.println(tipoIng.getNombre());  //Imprime el nombre del tipo del ingrediente
            System.out.println(tipoIng.getIdting());
            comboBoxTipoI.addItem(tipoIng.getNombre());
        }
    }
    
    public int obtenerId(List tipoIngrediente, String nombre){
        Iterator<TipoIngrediente> it = tipoIngrediente.iterator(); //Interface Iterator para recorrer la lista
        TipoIngrediente tipoIng;  //Variable del tipo TipoIngrediente
        while (it.hasNext()){     //Ciclo para recorrer el Iterator
            tipoIng = it.next();  //Le pasa a la instnaica de Tipo ingrediente el objeto que haya recorrigo
            if (tipoIng.getNombre().equals(nombre)){
                return tipoIng.getIdting();
            }
        }
        return 0;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtIdIngrediente = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        comboBoxTipoI = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("ID_INGREDIENTE:");

        jLabel2.setText("NOMBRE:");

        jLabel3.setText("TIPO INGREDIENTE: ");

        txtIdIngrediente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdIngredienteActionPerformed(evt);
            }
        });

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        comboBoxTipoI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxTipoIActionPerformed(evt);
            }
        });

        jButton1.setText("REGISTRAR INGREDIENTE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboBoxTipoI, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtIdIngrediente, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jButton1)))
                .addContainerGap(388, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIdIngrediente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(comboBoxTipoI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(122, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIdIngredienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdIngredienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdIngredienteActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void comboBoxTipoIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxTipoIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBoxTipoIActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String nombre = comboBoxTipoI.getSelectedItem().toString();
        TipoIngrediente ting = new TipoIngrediente();
        ting.setIdting(obtenerId(datoTipoIngrediente, nombre));
        
        ingrediente = new Ingrediente();
        ingrediente.setIding(Integer.parseInt(txtIdIngrediente.getText()));
        ingrediente.setNombre(txtNombre.getText());
        ingrediente.setTipoing(ting);
        
        try {
            ctlIng.create(ingrediente);
        } catch (Exception ex) {
            Logger.getLogger(IngredienteFrom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IngredienteFrom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IngredienteFrom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IngredienteFrom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IngredienteFrom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                IngredienteFrom dialog = new IngredienteFrom(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboBoxTipoI;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField txtIdIngrediente;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
