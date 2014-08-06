/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controllers.MainController;
import java.io.File;
import javax.swing.*;
import utils.POCException;

/**
 *
 * @author samuelhendrykus
 */
public class POSetting extends javax.swing.JFrame {
    private MainController mainController;
    private String input, output;
    /**
     * Creates new form POSetting
     */
    public POSetting(MainController main) {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e){
            JOptionPane.showMessageDialog(this, "Failed to set Look and Feel", "GUI Exception", JOptionPane.WARNING_MESSAGE);
        }
        mainController = main;
        initComponents();
        
        try
        {
            String[] paths = mainController.getInputOutputFolderPaths();
            input = paths[0];
            output = paths[1];
        }
        catch(POCException ex)
        {
            input = "";
            output = "";
            JOptionPane.showMessageDialog(this, ex.getMessage(), ex.getTitle(), JOptionPane.WARNING_MESSAGE);
        }
        
        // buttons, text fields, etc
                
        this.textFieldFolderMasukan.setText(this.input);
        this.textFieldFolderKeluaran.setText(this.output);
        
        // frame title, icon, etc
        /*                
        ImageIcon icon = new ImageIcon(this.getClass().getResource("/abc/icon.png"));
        this.setIconImage(icon.getImage());
        this.setTitle("PO Converter");
        */
        try{
            this.setIconImage(mainController.getIconImage());
//            this.setIconImage(new ImageIcon(getClass().getResource("/abc/icon.png")).getImage());
        }catch(POCException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), e.getTitle(), JOptionPane.WARNING_MESSAGE);
        }
        this.setTitle("PO Converter");
        
        // launch the window  
      
        pack();
        validate();
        //setExtendedState(Frame.MAXIMIZED_BOTH);   // for full screen
        setLocationRelativeTo(null);    // centering the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jFileChooser2 = new javax.swing.JFileChooser();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        textFieldFolderMasukan = new javax.swing.JTextField();
        textFieldFolderKeluaran = new javax.swing.JTextField();
        buttonCariFolderMasukan = new javax.swing.JButton();
        buttonCariFolderKeluaran = new javax.swing.JButton();
        buttonSimpan = new javax.swing.JButton();

        jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser1ActionPerformed(evt);
            }
        });

        jFileChooser2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser2ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPanel5FocusLost(evt);
            }
        });

        jLabel1.setText("Folder masukan");

        jLabel2.setText("Folder keluaran");

        textFieldFolderMasukan.setEditable(false);

        textFieldFolderKeluaran.setEditable(false);

        buttonCariFolderMasukan.setText("Cari");
        buttonCariFolderMasukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCariFolderMasukanActionPerformed(evt);
            }
        });

        buttonCariFolderKeluaran.setText("Cari");
        buttonCariFolderKeluaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCariFolderKeluaranActionPerformed(evt);
            }
        });

        buttonSimpan.setText("Simpan");
        buttonSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSimpanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(43, 43, 43)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textFieldFolderMasukan)
                    .addComponent(textFieldFolderKeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonCariFolderMasukan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonCariFolderKeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textFieldFolderMasukan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCariFolderMasukan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(textFieldFolderKeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonCariFolderKeluaran))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonSimpan)
                .addContainerGap(203, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCariFolderMasukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCariFolderMasukanActionPerformed
        // TODO add your handling code here:
        this.jFileChooser1.setCurrentDirectory(new File(this.textFieldFolderMasukan.getText() + "\\"));
        this.jFileChooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.jFileChooser1.showOpenDialog(this);
    }//GEN-LAST:event_buttonCariFolderMasukanActionPerformed

    private void buttonCariFolderKeluaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCariFolderKeluaranActionPerformed
        // TODO add your handling code here:
        this.jFileChooser2.setCurrentDirectory(new File(this.textFieldFolderKeluaran.getText() + "\\"));
        this.jFileChooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.jFileChooser2.showOpenDialog(this);
    }//GEN-LAST:event_buttonCariFolderKeluaranActionPerformed

    private void buttonSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSimpanActionPerformed
        try {
            boolean success = mainController.setInputOutputFolderPaths(this.textFieldFolderMasukan.getText(), this.textFieldFolderKeluaran.getText());
            if (success) {
                //update the view

                this.input = this.textFieldFolderMasukan.getText();
                this.output = this.textFieldFolderKeluaran.getText();
//                showListPDFFiles(input);
                JOptionPane.showMessageDialog(this, "Tersimpan!", "", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (POCException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), e.getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_buttonSimpanActionPerformed

    private void jPanel5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanel5FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel5FocusLost

    private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
        // TODO add your handling code here:
        try {
            this.textFieldFolderMasukan.setText(this.jFileChooser1.getSelectedFile().getPath());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jFileChooser1ActionPerformed

    private void jFileChooser2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser2ActionPerformed
        // TODO add your handlin2 code here:
        try {
            this.textFieldFolderKeluaran.setText(this.jFileChooser2.getSelectedFile().getPath());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jFileChooser2ActionPerformed

    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCariFolderKeluaran;
    private javax.swing.JButton buttonCariFolderMasukan;
    private javax.swing.JButton buttonSimpan;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JFileChooser jFileChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField textFieldFolderKeluaran;
    private javax.swing.JTextField textFieldFolderMasukan;
    // End of variables declaration//GEN-END:variables
}
