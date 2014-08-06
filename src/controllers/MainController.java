package controllers;

import com.itextpdf.text.DocumentException;
import dataModels.PurchaseOrder;
import documentHandlers.DaftarBelanjaGenerator;
import documentHandlers.PurchaseOrderReader;
import documentHandlers.SuratJalanGenerator;
import java.awt.Image;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import utils.*;
import views.MainWindow;
import views.POMain;

/**
 *
 * @author Albertus Alvin
 */
public class MainController {

    public static final String ICON_IMAGE = "images/icon.png";
    public static final String LOGO_IMAGE = "images/logo.jpg";
    private static final String SETTING_FILE = "setting.txt";
    private static final String PO_FILE = "purchase_order.txt";
    private MainWindow gui;
    private Preferences prefs;
    PurchaseOrderReader poReader;

    public MainController() {
        prefs = new Preferences();
        this.poReader = new PurchaseOrderReader();
        gui = new MainWindow(this);    // launch the main window / main user interface
    }

    public String[] getInputOutputFolderPaths() throws POCException {
        return prefs.getInputOutputFolderPaths(SETTING_FILE);
    }

    public boolean setInputOutputFolderPaths(String inputFolderPath, String outputFolderPath) throws POCException {
        return prefs.setInputOutputFolderPaths(inputFolderPath, outputFolderPath, SETTING_FILE);
    }

    public boolean generateDaftarBelanja(String[] inputFiles, String outputDirPath, double persenHBAtas, double persenHBBawah) {
        try {
            poReader = new PurchaseOrderReader();
            DaftarBelanjaGenerator dbGenerator = new DaftarBelanjaGenerator(persenHBBawah, persenHBAtas);

            poReader.parsePdfToTxt(inputFiles, PO_FILE);
            poReader.parseTxtToObjects(PO_FILE);
            dbGenerator.generateDaftarBelanja(outputDirPath.concat("\\Daftar Belanja\\"), poReader.getDaftarPesanan());
            return true;
        } catch (Exception e) {
//            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean generateDaftarSuratJalan(String[] inputFiles, DefaultTableModel tablemodel) {
        try {
            poReader = new PurchaseOrderReader();
            poReader.parsePdfToTxt(inputFiles, "purchase_order.txt");
            poReader.parseTxtToObjects("purchase_order.txt");

            for (PurchaseOrder po : poReader.getDaftarPesanan().getDaftarPO()) {
                tablemodel.addRow(new Object[]{po.nomorPO, po.tanggalKirim, po.namaToko, new String(" ")});
            }
            return true;
        } catch (Exception e) {
            // e.printStackTrace();
            return false;
        }
    }

    public boolean generateSuratJalan(String outputDirPath, DefaultTableModel tablemodel) {
        SuratJalanGenerator sjg;
        int idx = 0;
        for (PurchaseOrder po : poReader.getDaftarPesanan().getDaftarPO()) {
            po.setFaktur((String) tablemodel.getValueAt(idx, 3));
            idx++;
        }
        try {
            sjg = new SuratJalanGenerator();
            sjg.generateSuratJalan(outputDirPath + "\\Surat Jalan\\", poReader.getDaftarPesanan());
            return true;
        } catch (DocumentException ex) {
            Logger.getLogger(POMain.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (Exception ex) {
            Logger.getLogger(POMain.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Image getIconImage() throws POCException {
        return prefs.readImage(ICON_IMAGE);
    }

    // =======================================================================
    //                              Launch the program
    // =======================================================================
    public static void main(String[] args) {
        MainController mainController = new MainController();
    }
}
