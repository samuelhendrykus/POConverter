/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package documentHandlers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;

import java.util.Scanner;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import dataModels.DaftarPesanan;
/**
 *
 * @author Albertus Alvin
 */
public class PurchaseOrderReader 
{    
    private DaftarPesanan daftarPesanan;
    private String namaTokoSekarang, kodeToko, kodeDepartemen, nomorPOSekarang, tanggalKirimSekarang, tanggalPesan, kodeSupp;
    
    public PurchaseOrderReader()
    {
        namaTokoSekarang = "noname";
        daftarPesanan = new DaftarPesanan();
    }
    
    /**
     * Parses a PDF to a plain text file.
     * @param pdfs the PDFs to be read
     * @param txt the resulting text
     * @throws IOException
     */
    public void parsePdfToTxt(String[] pdfs, String txt) throws IOException 
    {
        //replace the file txt's content with the content from pdfs[0]
        PdfReader reader = new PdfReader(pdfs[0]);
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        PrintWriter out = new PrintWriter(new FileOutputStream(txt));
        TextExtractionStrategy strategy;
        for (int i = 1; i <= reader.getNumberOfPages(); i++) 
        {
            strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
            out.println(strategy.getResultantText());
        }
        out.flush();
        out.close();
        reader.close();
        
        //append the content of the rest of the pdfs to txt
        for(int i=1; i<pdfs.length; i++)
        {
            reader = new PdfReader(pdfs[i]);
            parser = new PdfReaderContentParser(reader);
            out = new PrintWriter(new FileOutputStream(txt, true));
            for(int j = 1; j <= reader.getNumberOfPages(); j++)
            {
                strategy = parser.processContent(j, new SimpleTextExtractionStrategy());
                out.println(strategy.getResultantText());
            }
            out.flush();
            out.close();
            reader.close();
        }
    }
    
    /**
     * Read a .txt file containing the content of a PDF file that was opened by iText library.
     * @param file the name of the .txt file (including the file format)
     * @throws Exception 
     */
    public void parseTxtToObjects(String file) throws Exception
    {
        Scanner sc = new Scanner(new File(file));        
        String line = sc.nextLine();
        while(sc.hasNextLine())
        {            
            if(line.matches("No Fax Supplier")){
                kodeToko = sc.nextLine();
                kodeDepartemen = sc.nextLine();
                line = sc.nextLine();
            }else if(line.matches("Pengiriman Ke.*"))
            {
                //get the Toko's name                    
                String[] words = line.split(" Ke ");
                namaTokoSekarang = words[1];
                nomorPOSekarang = sc.nextLine();
                tanggalPesan = sc.nextLine();
                tanggalKirimSekarang = sc.nextLine();
                kodeSupp = sc.nextLine();
                line = sc.nextLine();
            }
            else if(line.equals("No"))
            {
                //TABLE HEADER part
                //skip the remaining table headers
                for(int i=0; i<9; i++) sc.nextLine();
                //read the table rows
                line = readPurchaseOrders(sc);
            }
            else
            {                
                do{
                    line = sc.nextLine();
                }while(!line.matches("No Fax Supplier") && !line.matches("Pengiriman Ke.*") && !line.equals("No") && sc.hasNextLine());
            }
        }        
    }
    
    /**
     * Read through the order rows, each time adding a new order object into the DaftarPesanan. 
     * This function reads as long as the row it's reading is a valid order row.
     * It stops when not reading a valid order row, then returns the last row that it read.
     * @param sc the Scanner object that is used to scan the input.
     * @return the content of the last line that this function read.
     */
    private String readPurchaseOrders(Scanner sc)
    {        
        String line = "";
        while(sc.hasNextLine())
        {   
            try
            {
                //VALIDATE - confirm that this is indeed an order row.
                //If an exception is caught/it breaks the loop, no Pesanan object will be created.
                
                //scan the line number
                line = sc.nextLine();
                Integer.parseInt(line);
                //scan the unit
                line = sc.nextLine();
                String[] sat = line.split(" ");
                if(sat.length < 2 || sat.length > 3) break;
                String kodeBarang = sat[0];
                String satuan = (sat.length == 2 ? sat[1].substring(1,3) : sat[2]);                

                //validate the numbers line
                line = sc.nextLine();
                String[] somenumbers = line.split(" ");
                if(somenumbers.length != 3) break;

                //scan the product name
                sc.nextLine();
                String nama = sc.nextLine();

                //scan the quantity
                sc.nextLine();
                somenumbers = sc.nextLine().split(" ");
                double kuantitas = Double.parseDouble(somenumbers[3]);

                //scan the unit price                
                somenumbers[4] = somenumbers[4].replaceAll(",", "");
                double hargaUnit = Double.parseDouble(somenumbers[4]);
                //dump the closing line
                
                //Kode Produk
                kodeBarang = sc.nextLine().concat(kodeBarang);
//                System.out.println(kodeBarang);
                //CREATE the Pesanan object
                boolean create = daftarPesanan.tambahPesanan(nama, satuan, hargaUnit, kodeBarang, namaTokoSekarang, kodeToko, kodeDepartemen, nomorPOSekarang, tanggalKirimSekarang, tanggalPesan, kodeSupp, kuantitas);               

                if(create == false) break;
            }
            catch(Exception e)
            {         
                //e.printStackTrace();
//                System.out.println(e.getMessage());
                break;
            }            
        }
        return line;
    }
    
    /**
     * Retrieve the DaftarPesanan containing the order objects representing the orders in the PO file that this class has read.
     * @return the DaftarPesanan object.
     */
    public DaftarPesanan getDaftarPesanan()
    {
        return this.daftarPesanan;
    }
}
