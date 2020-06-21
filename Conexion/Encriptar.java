package Conexion;


import java.math.BigInteger;
import java.security.MessageDigest;

/**
 *
 * @author Código Lite - https://codigolite.com
 */
public class Encriptar {
    public String getencriptar(String documento) {
        try{
            MessageDigest md= MessageDigest.getInstance("md5");
            byte[] medi=md.digest(documento.getBytes());
            BigInteger numero=new BigInteger(1,medi);
            String hashtexto=numero.toString(16);
            System.out.println(hashtexto);
            System.out.println(documento);
            return hashtexto;}catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }
    
}
