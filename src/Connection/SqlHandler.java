/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samuelhendrykus
 */
public class SqlHandler {

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private final String user = "po";
    private final String password = "po";

    public void connectToDataBase() throws Exception {
        try {
            // this will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // setup the connection with the DB.
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/poconverter?"
                    + "user=" + user + "&password=" + password);
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean addProduk(String idProduk, String nama, String harga, String unit, String kemasan) {
        try {
            connectToDataBase();
            preparedStatement = connect.prepareStatement("insert into produk values (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, idProduk);
            preparedStatement.setString(2, nama);
            preparedStatement.setString(3, harga);
            preparedStatement.setString(4, unit);
            preparedStatement.setString(5, kemasan);
            preparedStatement.executeUpdate();
            close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public ResultSet findProduk(String field, String value) {
        try {
            connectToDataBase();
            statement = connect.createStatement();
            ResultSet result = statement.executeQuery("select * from produk where '" + field + "'='" + value + "'");
            close();
            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean addSupplier(String idSupplier, String namaSupplier, String alamat, String telepon, String fax) {
        try {
            connectToDataBase();
            preparedStatement = connect.prepareStatement("insert into supplier values (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, idSupplier);
            preparedStatement.setString(2, namaSupplier);
            preparedStatement.setString(3, alamat);
            preparedStatement.setString(4, telepon);
            preparedStatement.setString(5, fax);
            preparedStatement.execute();
            close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public ResultSet findSupplier(String field, String value) {
        try {
            connectToDataBase();
            statement = connect.createStatement();
            ResultSet result = statement.executeQuery("select * from supplier where '" + field + "'='" + value + "'");
            close();
            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean addToko(String idToko, String idDepartemen, String namaToko, String alamat, String telepon) {
        try {
            connectToDataBase();
            preparedStatement = connect.prepareStatement("insert into toko values (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, idToko);
            preparedStatement.setString(2, idDepartemen);
            preparedStatement.setString(3, namaToko);
            preparedStatement.setString(4, alamat);
            preparedStatement.setString(5, telepon);
            preparedStatement.execute();
            close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public ResultSet findToko(String field, String value) {
        try {
            connectToDataBase();
            statement = connect.createStatement();
            ResultSet result = statement.executeQuery("select * from toko where '" + field + "'='" + value + "'");
            close();
            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean addPesanan(String idPesanan, String idToko, String idDepartemen, String idSupplier, String idProduk, Date tanggalPesanan, Date tanggalPengiriman, int banyak) {
        try {
            connectToDataBase();
            preparedStatement = connect.prepareStatement("insert into pesanan values (?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, idPesanan);
            preparedStatement.setString(2, idToko);
            preparedStatement.setString(3, idDepartemen);
            preparedStatement.setString(4, idSupplier);
            preparedStatement.setString(5, idProduk);
            preparedStatement.setDate(6, tanggalPesanan);
            preparedStatement.setDate(7, tanggalPengiriman);
            preparedStatement.setInt(8, banyak);
            preparedStatement.execute();
            close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    public ResultSet findPesanan(String field, String idPesanan, String idToko, String idDepartemen, String idSuplier, String tanggalPesan, String tanggalKirim) {
        try {
            connectToDataBase();
            statement = connect.createStatement();
            java.util.Date tgglpesan = new java.util.Date(tanggalPesan);
            java.util.Date tgglkirim = new java.util.Date(tanggalKirim);
            ResultSet result = statement.executeQuery("select * from pesanan where 'IdPesanan' = '" + idPesanan + "' union"
                    + "select from pesanan where 'IdToko' = '" + idToko + "' union "
                    + "select from pesanan where 'IdDepartemen' = '" + idDepartemen + "' union" 
                    + "select from pesanan where 'IdSupplier' = '" + idSuplier + "' union" 
                    + "select from pesanan where 'TanggalPesan' = '" + new Date(tgglpesan.getTime()) + "' union"
                    + "select from pesanan where 'TanggalKirim' = '" + new Date(tgglkirim.getTime()) + "'");
            close();
            return result;
        } catch (Exception ex) {
            return null;
        }
    }
    
    private void close() {
        close(resultSet);
        close(statement);
        close(connect);
    }

    private void close(AutoCloseable c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            // don't throw now as it might leave following closables in undefined state
        }
    }
    
    public static void main(String[]args){
        SqlHandler tes = new SqlHandler();
        try {
            tes.connectToDataBase();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ohhh noooo");
        }
    }
}
