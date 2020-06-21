/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gonzalez Duerto
 */
public class Conexion {
        private static Connection con;
    private String host2="jdbc:mysql://localhost";
    private Statement statement;
    Encriptar en=new Encriptar();
    private static final String user2="root";
    private static final String driver="com.mysql.jdbc.Driver";
    public Conexion(){
        con=null;
        try{
            Class.forName(driver);
            con=DriverManager.getConnection(host2,user2,"");
            if(con!=null){
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public boolean conecta(){
        if(con!=null){
            return true;
        }
        else return false;
    }
    public Connection getconnection(){
        return con;
    }
    public void desconectar(){
        try {
            con.close();
           // System.out.println("desconectado");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Statement retorna(){
        try {
            Statement estado=con.createStatement();
            return estado;
        } catch (SQLException ex) {
        }
        return null;
    }
    public String getencriptar(String documento) {
        try{
            MessageDigest md= MessageDigest.getInstance("md5");
            byte[] medi=md.digest(documento.getBytes());
            BigInteger numero=new BigInteger(1,medi);
            String hashtexto=numero.toString(16);
            return hashtexto;}catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }
        public String iniciar(String usu,String pass){
        try {
            String encrip=en.getencriptar(pass);
            statement=con.createStatement();
            ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`usuarios` WHERE `nombre` LIKE '"+usu+"' AND `pass` LIKE '"+encrip+"'");
            if(consulta.next()) {
                return consulta.getString("tipo");
            }
            else{
                System.out.println("error");
            }
            consulta.close();
            desconectar();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

        desconectar();
        return "error";
    }
}