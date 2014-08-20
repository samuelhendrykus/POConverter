/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataModels;

/**
 *
 * @author Albertus Alvin
 */
public class PurchaseOrder 
{
    public String  nomorPO, tanggalKirim, nomorFaktur, tanggalPesan, kodeSupp;
    public Toko toko;
    
    public PurchaseOrder(String nama, String kodeToko, String kodeDept, String nomor, String tanggalKirim, String tanggalPesan, String kodeSupp)
    {
        toko = new Toko(kodeToko, kodeDept, nama);
        nomorPO = nomor;
        this.tanggalKirim = tanggalKirim;
        this.tanggalPesan = tanggalPesan;
        this.kodeSupp = kodeSupp;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof PurchaseOrder){
            PurchaseOrder po = (PurchaseOrder)o;
            if(this.toko.nama.equals(po.toko.nama) && this.nomorPO.equals(po.nomorPO) && this.tanggalKirim.equals(po.tanggalKirim)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    
    public void setFaktur(String set){
        this.nomorFaktur = set;
    }
    
    public class Toko{
        public String kodeToko, kodeDepartemen, nama;

        public Toko(String kodeToko, String kodeDepartemen, String nama) {
            this.kodeToko = kodeToko;
            this.kodeDepartemen = kodeDepartemen;
            this.nama = nama;
        }
    }
}
