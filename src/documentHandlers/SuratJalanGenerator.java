/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package documentHandlers;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import controllers.MainController;

import dataModels.Produk;
import dataModels.DaftarPesanan;
import dataModels.PurchaseOrder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Locale;


/**
 *
 * @author samuelhendrykus
 */
public class SuratJalanGenerator {

    private static BaseColor tableHeaderBgColor;
    private static DateFormat abbrevMonth;
    private static Font cambriaTitle;
    private static Font cambriaStandard;
    private static Font cambriaSmallItalic;
    private static float wColNo = 7f;
    private static float wColNamaBarang = 24f;
    private static float wColOrder = 10f;
    private static float wColKapasitas = 10f;
    private static float wColKirim = 10f;
    private static float wColTerima = 10f;
    private static float wColReject = 10f;

    public SuratJalanGenerator() throws DocumentException, IOException {
        abbrevMonth = new SimpleDateFormat("MMM", Locale.ENGLISH);                                      //COLORS
        tableHeaderBgColor = new BaseColor(195, 195, 195);                                              //FONTS
        BaseFont base = BaseFont.createFont("cambria.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
        cambriaTitle = new Font(base, 12, Font.BOLD);
        cambriaStandard = new Font(base, 9, Font.NORMAL);
        cambriaSmallItalic = new Font(base, 8, Font.ITALIC);
    }

    public void generateSuratJalan(String outputDirectoryPath, DaftarPesanan daftarPesanan) throws Exception {
        Calendar now = new GregorianCalendar();
        Document doc = new Document();
        String documentName = outputDirectoryPath
                + "SuratJalan_"
                + now.get(Calendar.DAY_OF_MONTH) + abbrevMonth.format(new Date()) + now.get(Calendar.YEAR)
                + "_" + now.get(Calendar.HOUR_OF_DAY) + "h" + now.get(Calendar.MINUTE) + "m" + now.get(Calendar.SECOND) + "s.pdf";

        PdfWriter.getInstance(doc, new FileOutputStream(documentName));

        doc.open();
        doc.setPageSize(PageSize.A4);
        for(PurchaseOrder po : daftarPesanan.getDaftarPO()){
            doc.add(createPageHeader(po));
            doc.add(new Paragraph("\n"));
            doc.add(createTableSuratJalan(daftarPesanan, po));
            doc.add(new Paragraph("\n"));
            doc.add(createFooter());
            doc.newPage();
        }
        doc.close();
    }

    public Paragraph createPageHeader(PurchaseOrder order) throws DocumentException, BadElementException, IOException 
    {
        Calendar now = new GregorianCalendar();
        Image img = Image.getInstance(MainController.LOGO_IMAGE);
        img.scaleToFit(100, 50);
        
        PdfPTable tableheader = new PdfPTable(2);
        tableheader.setWidthPercentage(100);
        {
            float[] colWidth = {20, 80};
            tableheader.setWidths(colWidth);
        }
        
        PdfPCell cell = new PdfPCell(img);
        cell.setBackgroundColor(new BaseColor(255, 255, 255));
        cell.setBorder(0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableheader.addCell(cell);
        
        cell = new PdfPCell(new Phrase("MSH \n Fresh and Vegetables Supplier \n Jl. H.Ali No.77, Jakarta 13540", cambriaTitle));
        cell.setBackgroundColor(new BaseColor(255, 255, 255));
        cell.setBorder(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableheader.addCell(cell);
        
        Paragraph title = new Paragraph("Surat Jalan", cambriaTitle);
        title.setAlignment(Element.ALIGN_RIGHT);
        title.add(new Paragraph(" "));

        PdfPTable tableKepada = new PdfPTable(2);
        tableKepada.setWidthPercentage(100);
        {
            float[] colWidth = {60, 40};
            tableKepada.setWidths(colWidth);
        }
        cell = new PdfPCell(new Phrase("Kepada Yth,", cambriaStandard));
        cell.setBackgroundColor(new BaseColor(255, 255, 255));
        cell.setBorder(0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableKepada.addCell(cell);

        cell = new PdfPCell(new Phrase("Nomor Faktur    : " + order.nomorFaktur, cambriaStandard));
        cell.setBorder(0);
        cell.setBackgroundColor(new BaseColor(255, 255, 255));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableKepada.addCell(cell);

        cell = new PdfPCell(new Phrase( order.toko.nama, cambriaStandard));
        cell.setBorder(0);
        cell.setBackgroundColor(new BaseColor(255, 255, 255));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableKepada.addCell(cell);

        cell = new PdfPCell(new Phrase("Nomor PO            : " + order.nomorPO, cambriaStandard));
        cell.setBorder(0);
        cell.setBackgroundColor(new BaseColor(255, 255, 255));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableKepada.addCell(cell);

        cell = new PdfPCell(new Phrase("\n", cambriaStandard));
        cell.setBorder(0);
        cell.setBackgroundColor(new BaseColor(255, 255, 255));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableKepada.addCell(cell);

        cell = new PdfPCell(new Phrase("Tanggal                 : " + order.tanggalKirim, cambriaStandard));
        cell.setBorder(0);
        cell.setBackgroundColor(new BaseColor(255, 255, 255));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableKepada.addCell(cell);

        Paragraph res = new Paragraph();
        res.add(tableheader);
        res.add(title);
        res.add(tableKepada);
        return res;
    }

    public Paragraph createTableSuratJalan(DaftarPesanan pesanan, PurchaseOrder order) throws DocumentException {
        LinkedList<Produk> daftarProduk = pesanan.getDaftarProduk();

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        {
            float[] colWidths = new float[7];
            colWidths[0] = wColNo;
            colWidths[1] = wColNamaBarang;
            colWidths[2] = wColOrder;
            colWidths[3] = wColKapasitas;
            colWidths[4] = wColKirim;
            colWidths[5] = wColTerima;
            colWidths[6] = wColReject;
            table.setWidths(colWidths);
        }

        PdfPCell cell = new PdfPCell(new Phrase("No.", cambriaStandard));
        cell.setBackgroundColor(tableHeaderBgColor);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Nama Barang", cambriaStandard));
        cell.setBackgroundColor(tableHeaderBgColor);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Order", cambriaStandard));
        cell.setBackgroundColor(tableHeaderBgColor);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Kapasitas", cambriaStandard));
        cell.setBackgroundColor(tableHeaderBgColor);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Kirim", cambriaStandard));
        cell.setBackgroundColor(tableHeaderBgColor);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Terima", cambriaStandard));
        cell.setBackgroundColor(tableHeaderBgColor);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Reject", cambriaStandard));
        cell.setBackgroundColor(tableHeaderBgColor);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        Produk prod;
        int num = 1;
        double jumlahOrder = 0;
        for (int i = 0; i < daftarProduk.size(); i++) {
            prod = daftarProduk.get(i);
            jumlahOrder = prod.getPesanan(order);
            if (jumlahOrder > 0) {
                cell = new PdfPCell(new Phrase(num+"", cambriaStandard));
                cell.setBackgroundColor(new BaseColor(255, 255, 255));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(prod.nama, cambriaStandard));
                cell.setBackgroundColor(new BaseColor(255, 255, 255));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(jumlahOrder+"", cambriaStandard));
                cell.setBackgroundColor(new BaseColor(255, 255, 255));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase("", cambriaStandard));
                cell.setBackgroundColor(new BaseColor(255, 255, 255));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase("", cambriaStandard));
                cell.setBackgroundColor(new BaseColor(255, 255, 255));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase("", cambriaStandard));
                cell.setBackgroundColor(new BaseColor(255, 255, 255));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase("", cambriaStandard));
                cell.setBackgroundColor(new BaseColor(255, 255, 255));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
                
                num++;
            }
        }
        
        Paragraph res = new Paragraph();
        res.add(table);
        
        return res;
    }
    
    public Paragraph createFooter() throws DocumentException{
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        {
            float[] colWidth = {50, 25, 25};
            table.setWidths(colWidth);
        }
        
        PdfPCell cell = new PdfPCell(new Phrase("Supir  : ", cambriaStandard));
        cell.setBackgroundColor(new BaseColor(255, 255, 255));
        cell.setBorder(0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("\n", cambriaStandard));
        cell.setBackgroundColor(new BaseColor(255, 255, 255));
        cell.setBorder(0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("\n", cambriaStandard));
        cell.setBackgroundColor(new BaseColor(255, 255, 255));
        cell.setBorder(0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("\n", cambriaStandard));
        cell.setBackgroundColor(new BaseColor(255, 255, 255));
        cell.setBorder(0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("\n", cambriaStandard));
        cell.setBackgroundColor(new BaseColor(255, 255, 255));
        cell.setBorder(0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("\n", cambriaStandard));
        cell.setBackgroundColor(new BaseColor(255, 255, 255));
        cell.setBorder(0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("\n", cambriaStandard));
        cell.setBackgroundColor(new BaseColor(255, 255, 255));
        cell.setBorder(0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Dikirim Oleh", cambriaStandard));
        cell.setBackgroundColor(new BaseColor(255, 255, 255));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Dikirim Oleh", cambriaStandard));
        cell.setBackgroundColor(new BaseColor(255, 255, 255));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Kontak  : \nPT.MSH \nTelp : (021) 8415711 \nFax   : (021) 8401934", cambriaStandard));
        cell.setBackgroundColor(new BaseColor(255, 255, 255));
        cell.setBorder(0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("\n \n \n \n Tanda Tangan/Cap", cambriaStandard));
        cell.setBackgroundColor(new BaseColor(255, 255, 255));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("\n \n \n \n Tanda Tangan/Cap", cambriaStandard));
        cell.setBackgroundColor(new BaseColor(255, 255, 255));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        
        Paragraph res = new Paragraph();
        res.setAlignment(Element.ALIGN_LEFT);
        res.add(table);
        return res;
    }
}
