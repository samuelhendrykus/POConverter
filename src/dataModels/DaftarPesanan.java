/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataModels;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

/**
 *
 * @author Albertus Alvin
 */
public class DaftarPesanan 
{
    private ArrayList<LinkedList<Produk>> daftarProduk; 
    //Each contains a LinkedList of Produk with the same first letter of the Produk's name.
    //This grouping is intended to reduce the time for looping through the list of Produk, which is done each time function tambahPesanan() is executed.
    private Queue<PurchaseOrder> daftarPO;    
    
    /**
     * Constructor.
     */
    public DaftarPesanan()
    {
        daftarProduk = new ArrayList<>(); 
        for(int i=0; i<26; i++)
        {
            daftarProduk.add(i, new LinkedList<Produk>());
        }
        daftarPO = new ArrayDeque<>();
    }
    
    public boolean tambahPesanan(String namaProduk, String satuan, double hargaUnit, String kodeBarang, String namaToko, String kodeToko, String kodeDept, String nomorPO, String tglKirim, String tglPesan, String kodeSupp, double kuantitas)
    {
        //VALIDITY CHECKING
        //make sure no empty or nonsense params
        if(namaProduk.trim().isEmpty() || satuan.trim().isEmpty() || hargaUnit < 0.0 || namaToko.trim().isEmpty() || kuantitas < 0.0)
            return false;
        //make sure the Produk's name starts with a letter (a-z, A-Z)
        if(((int)namaProduk.charAt(0) < 65 || (int)namaProduk.charAt(0) > 90) && ((int)namaProduk.charAt(0) < 97 || (int)namaProduk.charAt(0) > 122))
            return false;
        
        //NOTE the Toko's name
        PurchaseOrder po = new PurchaseOrder(namaToko, kodeToko, kodeDept, nomorPO, tglKirim, tglPesan, kodeSupp);
        if(!daftarPO.contains(po)){
            daftarPO.add(po);
        }
        //INSERTING
        //the list into which this Produk/pesanan will be added is determined by the first letter of the Produk's name
        int asciiFirstLetter = (int)namaProduk.charAt(0);
        int idxProductList = (asciiFirstLetter < 91 ? (asciiFirstLetter % 65) : ((asciiFirstLetter - 32) % 65)); 
        
        
        if(daftarProduk.get(idxProductList).size() == 0)
        {
            //list is empty, add new
            Produk p = new Produk(namaProduk, satuan, hargaUnit, kodeBarang);
            p.tambahPesanan(po, kuantitas);
            daftarProduk.get(idxProductList).add(p);
            return true;
        }
        else
        {
            //list isn't empty, see if it already has the Produk we're about to order
            ListIterator lit = daftarProduk.get(idxProductList).listIterator();
            while(lit.hasNext())
            {
                Produk p = (Produk) lit.next();
                if(p.nama.equals(namaProduk) && p.satuan.equals(satuan) && p.hargaUnit == hargaUnit)
                {
                    p.tambahPesanan(po, kuantitas);
                    lit.set(p);
                    return true;
                }
            }
            //list doesn't have the Produk yet, add new
            Produk p = new Produk(namaProduk, satuan, hargaUnit, kodeBarang);
            p.tambahPesanan(po, kuantitas);
            daftarProduk.get(idxProductList).add(p);
            return true;
        }
    }
    
    public String[] getDaftarNamaToko()
    {
        ArrayList<String> daftarNama = new ArrayList<>();
        for(PurchaseOrder po : daftarPO)
        {
            if(daftarNama.isEmpty())
                daftarNama.add(po.toko.nama);
            else
            {
                //check if the name already exists
                for(int i=0; i<daftarNama.size(); i++)
                {
                    if(daftarNama.get(i).equals(po.toko.nama))
                        break;
                    else if(i == daftarNama.size() - 1)
                    {
                        daftarNama.add(po.toko.nama);
                    }
                }
            }
        }
        return daftarNama.toArray(new String[daftarNama.size()]);        
    }
    
    public LinkedList<Produk> getDaftarProduk()
    {
        LinkedList<Produk> result = new LinkedList<>();
        for(LinkedList<Produk> l : daftarProduk)
        {
            result.addAll(l);
        }
        return result;
    }
    
    public Queue<PurchaseOrder> getDaftarPO(){
        return this.daftarPO;
    }
}
