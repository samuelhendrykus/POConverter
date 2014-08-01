/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Albertus Alvin
 */
public class Preferences 
{    
    
    public String[] getInputOutputFolderPaths(String settingFile) throws POCException
    {
        try
        {
            String[] res = new String[2];            
            BufferedReader br = new BufferedReader(new FileReader(settingFile));            
            
            if(br.readLine().equalsIgnoreCase("Masukan"))
                res[0] = br.readLine().trim();
            else throw new POCException("Folder masukan tidak ditemukan.", "Preferences Exception");

            if(br.readLine().equalsIgnoreCase("Keluaran"))
                res[1] = br.readLine().trim();
            else throw new POCException("Folder keluaran tidak ditemukan.", "Preferences Exception");

            Path path1 = new File(res[0]).toPath();
            Path path2 = new File(res[1]).toPath();
            
//            mengecek apakah directory input dan output sudah tersedia
            if(!Files.exists(path1, LinkOption.NOFOLLOW_LINKS) || !Files.exists(path2, LinkOption.NOFOLLOW_LINKS)) throw new POCException("Folder masukan dan folder keluaran tidak ditemukan.", "Preferences Exception");
            return res;
        }
        catch(Exception e)
        {
//            new File("/input").mkdir();
//            new File("/output").mkdir();
//            try{
//                BufferedWriter bw = new BufferedWriter(new FileWriter(settingFile));
//                bw.write("Masukan\n");
//                bw.write("\\input\n");
//                bw.write("Keluaran\n");
//                bw.write("\\output");
//                bw.close();
//            }catch(IOException ex){
//                JOptionPane.showMessageDialog(null, "Gagal membuat setting baru", "Warning", JOptionPane.WARNING_MESSAGE);
//            }
//            JOptionPane.showMessageDialog(null, "Gagal membaca berkas ".concat(settingFile) + " menggunangakan pengaruran awal", "Warning", JOptionPane.WARNING_MESSAGE);
//            String[] res = {"\\input","\\output"};
//            return res;
            //Menampilkan warning message gagal membaca file setting
//            throw new POCException("Gagal membaca berkas ".concat(settingFile) + " menggunangakan pengaruran awal", "Preferences Exception");   
            throw new POCException(e.getMessage(), "Preferences Exception");
        }
    }
    
    public boolean setInputOutputFolderPaths(String inputFolderPath, String outputFolderPath, String settingFile) throws POCException
    {
        try
        {
            // Check whether folder "Daftar Belanja" and "Surat Jalan" already exists inside the selected output folder.
            // If not then create them.
            File dir_DaftarBelanja = new File(outputFolderPath + "\\Daftar Belanja");
            File dir_SuratJalan = new File(outputFolderPath + "\\Surat Jalan");
            if(!(dir_DaftarBelanja.exists() && dir_DaftarBelanja.isDirectory()))
            {
                dir_DaftarBelanja.mkdir();
            }
            if(!(dir_SuratJalan.exists() && dir_SuratJalan.isDirectory()))
            {
                dir_SuratJalan.mkdir();
            }
            
            // Overwrite setting.txt
            
            BufferedWriter bw = new BufferedWriter(new FileWriter(settingFile));
            bw.write("Masukan\n");
            bw.write(inputFolderPath + "\n");
            bw.write("Keluaran\n");
            bw.write(outputFolderPath + "\n");
            bw.close();
            
            return true;
        }
        catch(Exception e)
        {
            throw new POCException("Gagal menyimpan folder masukan dan folder keluaran.", "Preferences Exception");
        }
    }
    
    public Image readImage(String imgURL) throws POCException
    {
        try
        {
//            get image versi albert
//            BufferedImage buffimg = ImageIO.read(new File(imgURL));
//            return buffimg.getScaledInstance(1, 1, Image.SCALE_DEFAULT);
            
//            get image versi kus
            ImageIcon img = new ImageIcon(imgURL);
            return img.getImage();
            
        }
        catch(Exception e)
        {
            throw new POCException("Unable to load icon image.", "Preferences Exception");
        }
    }
}
