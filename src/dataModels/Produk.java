/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataModels;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author Albertus Alvin
 */
public class Produk 
{
    public String nama, satuan;
    public double hargaUnit;
    private LinkedList<Pesanan> pesanan;
    
    /**
     * Constructor.
     * @param nama The name of the product.
     * @param satuan The unit of the product (KG, PCS, etc).
     * @param harga The unit price of the product.
     */
    public Produk(String nama, String satuan, double harga)
    {
        this.nama = nama;
        this.satuan = satuan;
        hargaUnit = harga;
        pesanan = new LinkedList<>();
    }
    
    public boolean tambahPesanan(PurchaseOrder order, double kuantitas)
    {
        if(order.namaToko.trim().isEmpty() || kuantitas < 0.0)
            return false;
        
        if(pesanan.size() == 0)
        {
            Pesanan p = new Pesanan(order.namaToko, order.nomorPO, kuantitas);
            pesanan.add(p);
            return true;
        }
        else
        {
            //see if the same Pesanan already exists
            ListIterator lit = pesanan.listIterator();
            while(lit.hasNext())
            {
                Pesanan p = (Pesanan) lit.next();
                if(p.namaToko.equals(order.namaToko))
                {
                    p.kuantitas += kuantitas;
                    lit.set(p);
                    return true;
                }
            }
            //if no same Pesanan is found, create a new one
            Pesanan p = new Pesanan(order.namaToko, order.nomorPO, kuantitas);
            pesanan.add(p);
            return true;
        }
    }
    
    public double getPesanan(PurchaseOrder order)
    {
        if(pesanan.size() == 0) return 0.0;
        
        for(Pesanan p : pesanan)
        {
            if(p.namaToko.equals(order.namaToko) && p.nomorPO.equals(order.nomorPO))
            {
                return p.kuantitas;
            }
        }
        return 0.0;
    }
    
    public double getAndRemovePesanan(String namaToko)
    {
        if(pesanan.size() == 0) return 0.0;    
        Iterator<Pesanan> it = pesanan.iterator();
        while(it.hasNext())
        {
            Pesanan p = (Pesanan)it.next();
            if(p.namaToko.equals(namaToko))
            {
                double res = p.kuantitas;
                it.remove();
                return res;
            }
        }
        return 0;
    }
    
    public void testing123(){
        for(Pesanan p : pesanan){
            System.out.println(p.namaToko + " " + p.kuantitas);
        }
    }
    
    //======================= Class Pesanan =======================
    //Data structure for recording an order by a shop
    
    final class Pesanan
    {
        String namaToko;
        String nomorPO;
        double kuantitas;
        
        Pesanan(String namaToko, double kuantitas)
        {
            this.namaToko = namaToko;
            this.kuantitas = kuantitas;
        }
        
        Pesanan(String namaToko, String nomorPO, double kuantitas)
        {
            this.namaToko = namaToko;
            this.nomorPO = nomorPO;
            this.kuantitas = kuantitas;
        }
    }
}
