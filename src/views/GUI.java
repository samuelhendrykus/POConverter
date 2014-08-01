package views;

import com.itextpdf.text.DocumentException;
import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controllers.MainController;
import dataModels.PurchaseOrder;
import java.awt.Frame;
import documentHandlers.DaftarBelanjaGenerator;
import documentHandlers.PurchaseOrderReader;
import documentHandlers.SuratJalanGenerator;
import javax.imageio.ImageIO;
import utils.POCException;

/**
 *
 * @author samuelhendrykus
 */
public class GUI extends javax.swing.JFrame 
{
    private MainController mainController;
    private PurchaseOrderReader reader;
    
    private JList<String> jList1;
    private JTable jTable;
    private String input, output;
    private DefaultListModel<String> model;
    private DefaultTableModel tablemodel;
    
    /**
     * Creates new form GUI
     */
    public GUI(MainController main)
    {
        // Setting look and feel
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e){
            JOptionPane.showMessageDialog(this, "Failed to set Look and Feel", "GUI Exception", JOptionPane.WARNING_MESSAGE);
        }
        
        // inits
        
        initComponents();               
        mainController = main;
        model = new DefaultListModel<>();
        
        // Find the input & output folder
        
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
        this.buttonBuatSuratJalan.setEnabled(false);    
        this.jList1 = new JList(model);
        this.jList1.setSize(this.scrollPaneDaftarPDFMasukan.getSize());
        this.scrollPaneDaftarPDFMasukan.add(jList1);
        this.jList1.setVisible(true);
        this.scrollPaneDaftarPDFMasukan.validate();
        this.scrollPaneDaftarPDFMasukan.setViewportView(this.jList1);
        if(input.isEmpty() == false) showListPDFFiles(input);
        
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        this.initTable();
        setVisible(true);       
        
    }
    
    public void initTable() 
    {
        tablemodel = new DefaultTableModel();
        this.jTable = new JTable(tablemodel) 
        {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 3) {
                    return true;
                }
                return false;
            }
        };
        tablemodel.addColumn("Nomor PO");
        tablemodel.addColumn("Tanggal pengiriman");
        tablemodel.addColumn("Tujuan");
        tablemodel.addColumn("Nomor Faktur");
        this.scrollPaneDaftarSuratJalan.add(this.jTable);
        this.scrollPaneDaftarSuratJalan.setViewportView(this.jTable);
    }

    /**
     * Display list of PDF files in the directory with given path.
     * @param folderPath the path to the directory containing PDFs.
     */
    private void showListPDFFiles(String folderPath)
    {       
        this.model.clear();
        File folder = new File(folderPath);
        File[] listFiles = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) 
            {
                String path = file.getAbsolutePath().toLowerCase();
                if(file.isFile() && file.getAbsolutePath().toLowerCase().endsWith("pdf")) return true;
                else return false;
            }
        });
        if (listFiles != null) 
        {
            for(File f : listFiles) model.addElement(f.getName());
            this.update(this.getGraphics());
        }
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
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelUtama = new javax.swing.JPanel();
        scrollPaneDaftarPDFMasukan = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        buttonBuatDaftarBelanja = new javax.swing.JButton();
        buttonPilihSemua = new javax.swing.JButton();
        buttonBuatDaftarSuratJalan = new javax.swing.JButton();
        buttonBuatSuratJalan = new javax.swing.JButton();
        buttonRefresh = new javax.swing.JButton();
        scrollPaneDaftarSuratJalan = new javax.swing.JScrollPane();
        labelBrand1 = new javax.swing.JLabel();
        labelBrand2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        fieldBatasAtas = new javax.swing.JTextField();
        fieldBatasBawah = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        panelSetting = new javax.swing.JPanel();
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
        setBackground(new java.awt.Color(255, 255, 255));
        setFont(new java.awt.Font("Caviar Dreams", 0, 14)); // NOI18N
        setForeground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(915, 460));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        panelUtama.setBackground(new java.awt.Color(255, 255, 255));
        panelUtama.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelUtamaMouseClicked(evt);
            }
        });
        panelUtama.setLayout(null);

        scrollPaneDaftarPDFMasukan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPaneDaftarPDFMasukan.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jPanel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 628, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 311, Short.MAX_VALUE)
        );

        scrollPaneDaftarPDFMasukan.setViewportView(jPanel1);

        panelUtama.add(scrollPaneDaftarPDFMasukan);
        scrollPaneDaftarPDFMasukan.setBounds(10, 11, 493, 176);

        buttonBuatDaftarBelanja.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buttonBuatDaftarBelanja.setText("Buar Daftar Belanja");
        buttonBuatDaftarBelanja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBuatDaftarBelanjaActionPerformed(evt);
            }
        });
        panelUtama.add(buttonBuatDaftarBelanja);
        buttonBuatDaftarBelanja.setBounds(700, 90, 180, 25);

        buttonPilihSemua.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buttonPilihSemua.setText("Pilih Semua");
        buttonPilihSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPilihSemuaActionPerformed(evt);
            }
        });
        panelUtama.add(buttonPilihSemua);
        buttonPilihSemua.setBounds(520, 90, 110, 25);

        buttonBuatDaftarSuratJalan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buttonBuatDaftarSuratJalan.setText("Buat Daftar Surat Jalan");
        buttonBuatDaftarSuratJalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBuatDaftarSuratJalanActionPerformed(evt);
            }
        });
        panelUtama.add(buttonBuatDaftarSuratJalan);
        buttonBuatDaftarSuratJalan.setBounds(700, 210, 180, 25);

        buttonBuatSuratJalan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buttonBuatSuratJalan.setText("Buat Surat Jalan");
        buttonBuatSuratJalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBuatSuratJalanActionPerformed(evt);
            }
        });
        panelUtama.add(buttonBuatSuratJalan);
        buttonBuatSuratJalan.setBounds(700, 240, 180, 25);

        buttonRefresh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buttonRefresh.setText("Refresh");
        buttonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRefreshActionPerformed(evt);
            }
        });
        panelUtama.add(buttonRefresh);
        buttonRefresh.setBounds(520, 120, 110, 25);

        scrollPaneDaftarSuratJalan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        panelUtama.add(scrollPaneDaftarSuratJalan);
        scrollPaneDaftarSuratJalan.setBounds(10, 193, 680, 161);

        labelBrand1.setFont(new java.awt.Font("Trajan Pro", 0, 11)); // NOI18N
        labelBrand1.setText("PO Converter");
        panelUtama.add(labelBrand1);
        labelBrand1.setBounds(420, 390, 91, 20);

        labelBrand2.setFont(new java.awt.Font("Trajan Pro", 0, 11)); // NOI18N
        labelBrand2.setText("| by Aristophanes A. Alvin & Samuel Hendrykus S. 2014");
        panelUtama.add(labelBrand2);
        labelBrand2.setBounds(530, 390, 352, 20);

        jLabel3.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jLabel3.setText("Harga beli");
        panelUtama.add(jLabel3);
        jLabel3.setBounds(521, 17, 74, 17);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Batas bawah");
        panelUtama.add(jLabel4);
        jLabel4.setBounds(520, 50, 80, 15);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Batas atas");
        panelUtama.add(jLabel5);
        jLabel5.setBounds(710, 50, 70, 15);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("%");
        panelUtama.add(jLabel7);
        jLabel7.setBounds(660, 50, 14, 17);
        panelUtama.add(fieldBatasAtas);
        fieldBatasAtas.setBounds(780, 50, 50, 20);
        panelUtama.add(fieldBatasBawah);
        fieldBatasBawah.setBounds(600, 50, 50, 20);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("%");
        panelUtama.add(jLabel8);
        jLabel8.setBounds(840, 50, 20, 20);

        jTabbedPane1.addTab("Utama", panelUtama);

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
                    .addComponent(textFieldFolderMasukan, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                    .addComponent(textFieldFolderKeluaran))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                    .addComponent(buttonCariFolderMasukan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonCariFolderKeluaran, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(445, Short.MAX_VALUE))
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
                .addContainerGap(328, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelSettingLayout = new javax.swing.GroupLayout(panelSetting);
        panelSetting.setLayout(panelSettingLayout);
        panelSettingLayout.setHorizontalGroup(
            panelSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelSettingLayout.setVerticalGroup(
            panelSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Pengaturan", panelSetting);

        jPanel3.add(jTabbedPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonPilihSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPilihSemuaActionPerformed
        // TODO add your handling code here:
        this.jList1.setSelectionInterval(0, this.jList1.getModel().getSize() - 1);
    }//GEN-LAST:event_buttonPilihSemuaActionPerformed

    private void buttonBuatDaftarBelanjaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBuatDaftarBelanjaActionPerformed
        
        double batasAtas, batasBawah;
        List<String> listfile;
        String[] inputFiles;
        
        // Validity checking        
        
        if(fieldBatasAtas.getText().trim().isEmpty() || fieldBatasBawah.getText().trim().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Batas atas dan batas bawah harus diisi.", "Ups...", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try{
            batasAtas = Double.parseDouble(fieldBatasAtas.getText());
            batasBawah = Double.parseDouble(fieldBatasBawah.getText());
            batasAtas /= 100.0;
            batasBawah /= 100.0;
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Batas atas & batas bawah harus berupa angka.", "Ups...", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        listfile = this.jList1.getSelectedValuesList();
        inputFiles = new String[listfile.size()];
        if (inputFiles.length == 0) {
            JOptionPane.showMessageDialog(this, "Pilih PDF masukan.");
            return;
        }
        
        // Run the operation  
        
        for (int i = 0; i < inputFiles.length; i++) inputFiles[i] = this.input + "\\" + listfile.get(i);        
        
        boolean success =  mainController.generateDaftarBelanja(inputFiles, output, batasAtas, batasBawah);
        if(success) JOptionPane.showMessageDialog(this, "Berhasil membuat Daftar Belanja", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        else JOptionPane.showMessageDialog(this, "Gagal membuat Daftar Belanja", "Gagal", JOptionPane.ERROR_MESSAGE);
        
        
        for(int i=0; i<this.tablemodel.getRowCount(); i++) this.tablemodel.removeRow(i);
    }//GEN-LAST:event_buttonBuatDaftarBelanjaActionPerformed

    private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
        // TODO add your handling code here:
        try{
            this.textFieldFolderMasukan.setText(this.jFileChooser1.getSelectedFile().getPath());
        }catch(Exception e){}
    }//GEN-LAST:event_jFileChooser1ActionPerformed

    private void jFileChooser2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser2ActionPerformed
        // TODO add your handlin2 code here:
        try{
            this.textFieldFolderKeluaran.setText(this.jFileChooser2.getSelectedFile().getPath());
        }catch(Exception e){}
    }//GEN-LAST:event_jFileChooser2ActionPerformed

    private void buttonCariFolderKeluaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCariFolderKeluaranActionPerformed
        // TODO add your handling code here:
        this.jFileChooser2.setCurrentDirectory(new File(this.textFieldFolderKeluaran.getText() + "\\"));
        this.jFileChooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.jFileChooser2.showOpenDialog(this);
    }//GEN-LAST:event_buttonCariFolderKeluaranActionPerformed

    private void buttonCariFolderMasukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCariFolderMasukanActionPerformed
        // TODO add your handling code here:
        this.jFileChooser1.setCurrentDirectory(new File(this.textFieldFolderMasukan.getText() + "\\"));
        this.jFileChooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.jFileChooser1.showOpenDialog(this);
    }//GEN-LAST:event_buttonCariFolderMasukanActionPerformed

    private void buttonSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSimpanActionPerformed
        try
        {
            boolean success = mainController.setInputOutputFolderPaths(this.textFieldFolderMasukan.getText(), this.textFieldFolderKeluaran.getText());
            if(success)
            {
                //update the view

                this.input = this.textFieldFolderMasukan.getText();
                this.output = this.textFieldFolderKeluaran.getText();
                showListPDFFiles(input);
                JOptionPane.showMessageDialog(this, "Tersimpan!", "", JOptionPane.INFORMATION_MESSAGE); 
            }
        }
        catch(POCException e)
        {
            JOptionPane.showMessageDialog(this, e.getMessage(), e.getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_buttonSimpanActionPerformed

    private void buttonBuatDaftarSuratJalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBuatDaftarSuratJalanActionPerformed
        // TODO add your handling code here:
        this.buttonBuatSuratJalan.setEnabled(true);
        int rowCount = this.tablemodel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            this.tablemodel.removeRow(0);
        }
        List<String> listfile = this.jList1.getSelectedValuesList();
        String[] inputFiles = new String[listfile.size()];
        if (inputFiles.length == 0) {
            JOptionPane.showMessageDialog(this, "Pilih PDF masukan!");
        } else {
            for (int i = 0; i < inputFiles.length; i++) {
                inputFiles[i] = this.input + "\\" + listfile.get(i);
            }
//            try {
//                reader = new PurchaseOrderReader();
//                reader.parsePdfToTxt(inputFiles, "purchase_order.txt");
//                reader.parseTxtToObjects("purchase_order.txt");
//
//                for (PurchaseOrder po : reader.getDaftarPesanan().getDaftarPO()) {
//                    this.tablemodel.addRow(new Object[]{po.nomorPO, po.tanggalKirim, po.namaToko, new String(" ")});
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            boolean success = this.mainController.generateDaftarSuratJalan(inputFiles, tablemodel);
            if(success) 
                JOptionPane.showMessageDialog(this, "Berhasil membuat Daftar Surat Jalan", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            else 
                JOptionPane.showMessageDialog(this, "Gagal membuat Daftar Surat Jalan", "Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_buttonBuatDaftarSuratJalanActionPerformed

    private void buttonBuatSuratJalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBuatSuratJalanActionPerformed
        // TODO add your handling code here:
//        dipindah ke maincontroller
//        SuratJalanGenerator sjg;
//        int idx = 0;
//        for (PurchaseOrder po : reader.getDaftarPesanan().getDaftarPO()) {
//            po.setFaktur((String)this.tablemodel.getValueAt(idx, 3));
//            idx++;
//        }
//        try {
//            sjg = new SuratJalanGenerator();
//            sjg.generateSuratJalan(this.output + "\\Surat Jalan\\", reader.getDaftarPesanan());
//        } catch (DocumentException ex) {
//            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception ex) {
//            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
//        }
        boolean success = this.mainController.generateSuratJalan(output, tablemodel);
        if(success) 
            JOptionPane.showMessageDialog(this, "Berhasil membuat Surat Jalan", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        else 
            JOptionPane.showMessageDialog(this, "Gagal membuat Surat Jalan", "Gagal", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_buttonBuatSuratJalanActionPerformed

    private void buttonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRefreshActionPerformed
        if(input.isEmpty() == false) this.showListPDFFiles(input);
    }//GEN-LAST:event_buttonRefreshActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        // TODO add your handling code here:
         
    }//GEN-LAST:event_jPanel2MouseClicked

    private void panelUtamaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelUtamaMouseClicked

    }//GEN-LAST:event_panelUtamaMouseClicked

    private void jPanel5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanel5FocusLost
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jPanel5FocusLost

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        // TODO add your handling code here:
        this.textFieldFolderMasukan.setText(input);
        this.textFieldFolderKeluaran.setText(output);
    }//GEN-LAST:event_jTabbedPane1StateChanged

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /*
//         * Set the Nimbus look and feel
//         */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /*
//         * If Nimbus (introduced in Java SE 6) is not available, stay with the
//         * default look and feel. For details see
//         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//         */
//        
//        /*
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Windows".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        */
//        
//        //</editor-fold>
//
//        /*
//         * Create and display the form
//         */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    GUI gui = new GUI();
//                } catch (IOException ex) {
//                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
//    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonBuatDaftarBelanja;
    private javax.swing.JButton buttonBuatDaftarSuratJalan;
    private javax.swing.JButton buttonBuatSuratJalan;
    private javax.swing.JButton buttonCariFolderKeluaran;
    private javax.swing.JButton buttonCariFolderMasukan;
    private javax.swing.JButton buttonPilihSemua;
    private javax.swing.JButton buttonRefresh;
    private javax.swing.JButton buttonSimpan;
    private javax.swing.JTextField fieldBatasAtas;
    private javax.swing.JTextField fieldBatasBawah;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JFileChooser jFileChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel labelBrand1;
    private javax.swing.JLabel labelBrand2;
    private javax.swing.JPanel panelSetting;
    private javax.swing.JPanel panelUtama;
    private javax.swing.JScrollPane scrollPaneDaftarPDFMasukan;
    private javax.swing.JScrollPane scrollPaneDaftarSuratJalan;
    private javax.swing.JTextField textFieldFolderKeluaran;
    private javax.swing.JTextField textFieldFolderMasukan;
    // End of variables declaration//GEN-END:variables
}
