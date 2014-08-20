/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package documentHandlers;

import Connection.SqlHandler;
import dataModels.Produk;
import dataModels.DaftarPesanan;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dataModels.PurchaseOrder;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Albertus Alvin
 */
public class DaftarBelanjaGenerator {

    private static BaseColor tableHeaderBgColor;
    private static DateFormat abbrevMonth;
    private static Font cambriaTitle;
    private static Font cambriaStandard;
    private static Font cambriaSmallItalic;
    private static double persentaseHargaBeliBawah;
    private static double persentaseHargaBeliAtas;
    //relative widths of the table columns (total 100f)
    private static float wColNo = 7f;
    private static float wColNamaBarang = 23f;
    private static float wColPesanan = 34f;
    private static float wColJumlah = 10f;
    private static float wColSatuan = 6f;
    private static float wColHargaBeliBawah = 10f;
    private static float wColHargaBeliAtas = 10f;

    /**
     * Constructor.
     *
     * @throws Exception
     */
    public DaftarBelanjaGenerator(double persentaseHBBawah, double persentaseHBAtas) throws Exception {
        persentaseHargaBeliBawah = persentaseHBBawah;
        persentaseHargaBeliAtas = persentaseHBAtas;
        abbrevMonth = new SimpleDateFormat("MMM", Locale.ENGLISH);

        //colors
        tableHeaderBgColor = new BaseColor(195, 195, 195);

        //fonts
        BaseFont base = BaseFont.createFont("cambria.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
        cambriaTitle = new Font(base, 11, Font.BOLD);
        cambriaStandard = new Font(base, 9, Font.NORMAL);
        cambriaSmallItalic = new Font(base, 8, Font.ITALIC);
    }

    /**
     * Generate a Daftar Belanja (shopping list) in the given output directory,
     * representing the given Daftar Pesanan (order list).
     *
     * @param outputDirectoryPath the directory that will contain the Daftar
     * Belanja.
     * @param daftarPesanan the Daftar Pesanan which will be converted into
     * Daftar Belanja.
     * @throws Exception
     */
    public void generateDaftarBelanja(SqlHandler handler, String outputDirectoryPath, DaftarPesanan daftarPesanan) throws Exception {
        //compose a name for the generated document
        Calendar now = new GregorianCalendar();
        String documentName = outputDirectoryPath
                + "DaftarBelanja_"
                + now.get(Calendar.DAY_OF_MONTH) + abbrevMonth.format(new Date()) + now.get(Calendar.YEAR)
                + "_" + now.get(Calendar.HOUR_OF_DAY) + "h" + now.get(Calendar.MINUTE) + "m" + now.get(Calendar.SECOND) + "s.pdf";

        //create the document
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(documentName));

        //create the document's content
        doc.open();
        doc.setPageSize(PageSize.A4);
        doc.add(createPageHeader());
        doc.add(createTableDaftarBelanja(handler, daftarPesanan));
        doc.close();
    }

    public void addMetaData(Document document, String title, String subject, String keywords, String author, String creator) {
        document.addTitle(title);
        document.addSubject(subject);
        document.addKeywords(keywords);
        document.addAuthor(author);
        document.addCreator(creator);
    }

    private Paragraph createPageHeader() throws Exception {
        Paragraph title = new Paragraph("Daftar Belanja", cambriaTitle);
        title.setAlignment(Element.ALIGN_CENTER);
        title.add(new Paragraph(" "));
        return title;
    }

    private Paragraph createTableDaftarBelanja(SqlHandler handler, DaftarPesanan daftarPesanan) throws Exception {
        //obtain the data from daftarPesanan
        String[] toko = daftarPesanan.getDaftarNamaToko();
        LinkedList<Produk> daftarProduk = daftarPesanan.getDaftarProduk();

        //configure the table's layout
        PdfPTable table = new PdfPTable(toko.length + 6);
        table.setWidthPercentage(100);
        {
            float[] colWidths = new float[toko.length + 6];
            colWidths[0] = wColNo; //column No.
            colWidths[1] = wColNamaBarang; //column Nama Barang
            for (int i = 2; i < (2 + toko.length); i++) {
                colWidths[i] = (wColPesanan / toko.length); //column orders from individual Toko
            }
            colWidths[2 + toko.length] = wColJumlah; //column Jumlah
            colWidths[3 + toko.length] = wColSatuan; //column Satuan
            colWidths[4 + toko.length] = wColHargaBeliBawah; //column Harga Beli Bawah
            colWidths[5 + toko.length] = wColHargaBeliAtas; //column Harga Beli Atas
            table.setWidths(colWidths);
        }

        //writing table headers
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

        for (int i = 1; i <= toko.length; i++) {
            cell = new PdfPCell(new Phrase("CF" + i, cambriaStandard));
            cell.setBackgroundColor(tableHeaderBgColor);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);
        }

        cell = new PdfPCell(new Phrase("Jumlah", cambriaStandard));
        cell.setBackgroundColor(tableHeaderBgColor);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Satuan", cambriaStandard));
        cell.setBackgroundColor(tableHeaderBgColor);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Harga Beli Bawah", cambriaStandard));
        cell.setBackgroundColor(tableHeaderBgColor);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Harga Beli Atas", cambriaStandard));
        cell.setBackgroundColor(tableHeaderBgColor);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        //writing table contents
        Iterator<Produk> it = daftarProduk.iterator();
        int rowNum = 1;
        while (it.hasNext()) {
            Produk p = it.next();
            //number
            cell = new PdfPCell(new Phrase(rowNum + "", cambriaStandard));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            //product's name
            cell = new PdfPCell(new Phrase(p.nama, cambriaStandard));
            table.addCell(cell);
            //orders from individual Toko
            double totalPesanan = 0;
            for (String namaToko : toko) {
                double nPesanan = p.getAndRemovePesanan(namaToko);
                totalPesanan += nPesanan;
                cell = new PdfPCell(new Phrase((nPesanan > 0 ? nPesanan + "" : ""), cambriaStandard));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }
            //total orders
            cell = new PdfPCell(new Phrase(totalPesanan + "", cambriaStandard));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            //satuan
            cell = new PdfPCell(new Phrase(p.satuan, cambriaStandard));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            //harga beli bawah
            cell = new PdfPCell(new Phrase((int) (persentaseHargaBeliBawah * p.hargaUnit * totalPesanan) + "", cambriaStandard));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
            //harga beli atas
            cell = new PdfPCell(new Phrase((int) (persentaseHargaBeliAtas * p.hargaUnit * totalPesanan) + "", cambriaStandard));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);

            rowNum++;
        }

        addPesananToDatabase(handler, daftarPesanan);

        //column legends
        Paragraph legend = new Paragraph("Keterangan: \n", cambriaSmallItalic);
        List listKeterangan = new List(List.UNORDERED);
        for (int i = 1; i <= toko.length; i++) {
            listKeterangan.add(new ListItem("CF" + i + ": " + toko[i - 1], cambriaSmallItalic));
        }
        legend.add(listKeterangan);


        Paragraph res = new Paragraph();
        res.add(table);
        res.add(legend);

        return res;
    }

    public void addPesananToDatabase(SqlHandler handler, DaftarPesanan pesanan) {
        try {
            handler.connectToDataBase();
            Iterator<PurchaseOrder> iter = pesanan.getDaftarPO().iterator();
            PurchaseOrder order;
            LinkedList<Produk> produk = pesanan.getDaftarProduk();
            while (iter.hasNext()) {
                order = iter.next();
                for (int i = 0; i < produk.size(); i++) {
                    if (produk.get(i).getPesanan(order) != 0) {
                        Date tanggalPesan = new Date(order.tanggalPesan);
                        Date tanggalKirim = new Date(order.tanggalKirim);
                        handler.addPesanan(order.nomorPO, order.toko.kodeToko, order.toko.kodeDepartemen, order.kodeSupp, produk.get(i).kodeBarang, new java.sql.Date(tanggalPesan.getTime()), new java.sql.Date(tanggalKirim.getTime()), (int) produk.get(i).getPesanan(order));
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
