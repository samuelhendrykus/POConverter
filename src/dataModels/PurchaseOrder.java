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
    public String namaToko, nomorPO, tanggalKirim, nomorFaktur;
    
    public PurchaseOrder(String nama, String nomor, String tanggalKirim)
    {
        namaToko = nama;
        nomorPO = nomor;
        this.tanggalKirim = tanggalKirim;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof PurchaseOrder){
            PurchaseOrder po = (PurchaseOrder)o;
            if(this.namaToko.equals(po.namaToko) && this.nomorPO.equals(po.nomorPO) && this.tanggalKirim.equals(po.tanggalKirim)){
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
}
