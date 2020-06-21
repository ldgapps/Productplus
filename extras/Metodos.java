package extras;

import Conexion.Conexion;
import Conexion.Encriptar;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Metodos {
    private Statement statement;
    private Encriptar en=new Encriptar();
    private Conexion co;
    public String iniciar(String usu,String pass){
        co=new Conexion();
        try {
            statement=co.getconnection().createStatement();
            ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`usuarios` WHERE `nombre` LIKE '"+usu+"'");
            while(consulta.next()){
                if(consulta.getString("pass").equals(en.getencriptar(pass))){
                    if(consulta.getString("tipo").equals("admin")){
                        return "admin";
                    }
                    else if(consulta.getString("tipo").equals("cajero")){
                        return "cajero";
                    }
                    else return "error";
                }
                else return "error";
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        co.desconectar();
        return "error";
    }
    public void add_proveedor(String nombre,String rif,String telefono,String ciudad,String email,String banco,String tipoc,String cuenta,String productos) throws SQLException {
        co=new Conexion();
        co.getconnection().prepareStatement("INSERT INTO `product`.`proveedores` (`id`, `nombre`, `rif`, `telefono`, `ciudad`, `email`, `banco`, `tipo de cuenta`, `cuenta`, `productos`) "
                + "VALUES (NULL,'"+nombre+"', '"+rif+"', '"+telefono+"', '"+ciudad+"', '"+email+"', '"+banco+"', '"+tipoc+"', '"+cuenta+"', '"+productos+"')").executeUpdate();
        co.desconectar();

    }

    public void add_charcuteria(String codigo,String peso,String costo) throws SQLException{
        co=new Conexion();
        statement=co.getconnection().createStatement();
        ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`charcuteria` WHERE `codigo` LIKE '"+codigo+"'");
        while(consulta.next()){
            int c=consulta.getInt("peso");
            float c2=Float.parseFloat(peso);
            float costoa=Float.parseFloat(costo);
            float cantt=c+c2;
            float costot=cantt*costoa;
            float pre=consulta.getFloat("precio");
            float preciot=pre*cantt;
            co.getconnection().prepareStatement("UPDATE `product`.`charcuteria` SET `precio total`='"+preciot+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
            co.getconnection().prepareStatement("UPDATE `product`.`charcuteria` SET `peso`='"+cantt+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
            co.getconnection().prepareStatement("UPDATE `product`.`charcuteria` SET `costo total`='"+costot+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
            co.getconnection().prepareStatement("UPDATE `product`.`charcuteria` SET `costo`='"+costo+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
        }
        co.desconectar();
    }
    public void add_viveres(String codigo,String cantidad,String costo) throws SQLException{
        co=new Conexion();
        statement=co.getconnection().createStatement();
        ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`viveres` WHERE `codigo` LIKE '"+codigo+"'");
        while(consulta.next()){
            int c=consulta.getInt("cantidad");
            int c2=Integer.parseInt(cantidad);
            float costoa=Float.parseFloat(costo);
            int cantt=c+c2;
            float costot=cantt*costoa;
            float pre=consulta.getFloat("precio");
            float preciot=pre*cantt;
            co.getconnection().prepareStatement("UPDATE `product`.`viveres` SET `precio total`='"+preciot+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
            co.getconnection().prepareStatement("UPDATE `product`.`viveres` SET `cantidad`='"+cantt+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
            co.getconnection().prepareStatement("UPDATE `product`.`viveres` SET `costo total`='"+costot+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
            co.getconnection().prepareStatement("UPDATE `product`.`viveres` SET `costo`='"+costo+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
        }
        co.desconectar();
    }

    public void del_charcuteria(String codigo,String precio) throws SQLException{
        co=new Conexion();
        statement=co.getconnection().createStatement();
        ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`charcuteria` WHERE `codigo` LIKE '"+codigo+"'");
        while(consulta.next()){
            float c=consulta.getFloat("peso");
            float precioa=Float.parseFloat(precio);
            float pre=consulta.getFloat("precio");
            float preciott=consulta.getFloat("precio total");
            float cost=consulta.getFloat("costo");
            float preciot=preciott-precioa;
            float can=preciot/pre;
            float costot=cost*can;
            System.out.println(costot);
            co.getconnection().prepareStatement("UPDATE `product`.`charcuteria` SET `precio total`='"+preciot+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
            co.getconnection().prepareStatement("UPDATE `product`.`charcuteria` SET `peso`='"+can+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
            co.getconnection().prepareStatement("UPDATE `product`.`charcuteria` SET `costo total`='"+costot+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
        }
        co.desconectar();
    }
    public void del_viveres(String codigo,String cantidad,String preciot) throws SQLException{
        co=new Conexion();
        statement=co.getconnection().createStatement();
        ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`viveres` WHERE `codigo` LIKE '"+codigo+"'");
        while(consulta.next()){
            int c=consulta.getInt("cantidad");
            float c2=Float.parseFloat(cantidad);
            float costoa=consulta.getFloat("costo");
            float cantt=c-c2;
            float costot=cantt*costoa;
            float pre=consulta.getFloat("precio total");
            float pre2=Float.parseFloat(preciot);
            float preciot2=pre-pre2;
            co.getconnection().prepareStatement("UPDATE `product`.`viveres` SET `precio total`='"+preciot2+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
            co.getconnection().prepareStatement("UPDATE `product`.`viveres` SET `cantidad`='"+cantt+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
            co.getconnection().prepareStatement("UPDATE `product`.`viveres` SET `costo total`='"+costot+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
        }
        co.desconectar();
    }
    public void add_venta(String tipo,String codigo,String productos,String cantidad,String cliente,String ci,String precio,String preciot,String total,String forma,String ref) throws SQLException{
        co=new Conexion();
        co.getconnection().prepareStatement("INSERT INTO `product`.`ventas` (`codigo`, `tipo`, `productos`, `cantidad`, `precio`, `precio t`,`total`, `tipo de pago`, `cliente`, `ci`, `referencia`, `fecha`, `hora`) " +
                "VALUES ('"+codigo+"', '"+tipo+"', '"+productos+"', '"+cantidad+"', '"+precio+"', '"+preciot+"', '"+total+"', '"+forma+"', '"+cliente+"', '"+ci+"','"+ref+"', CURRENT_DATE(), CURRENT_TIME())").executeUpdate();
        co.desconectar();
    }
    public void add_cuenta_cobrar(String codigo,String tipo,String producto,String cliente,String precio,String preciot,String ci,String abono,String total,String tipopago,String resta,String cantidad,String fechatope,String ref) throws SQLException{
        co=new Conexion();
        co.getconnection().prepareStatement("INSERT INTO `product`.`cuentas por cobrar` (`itm`, `codigo`, `tipo`, `productos`, `cantidad`, `precio`, `precio t`, `total`, `abono`, `resta`, `tipo de pago`, `cliente`, `ci`, `referencia`, `fecha`, `hora`, `fecha tope`) " +
                "VALUES (NULL, '"+codigo+"', '"+tipo+"', '"+producto+"', '"+cantidad+"', '"+precio+"', '"+preciot+"', '"+total+"', '"+abono+"', '"+resta+"', '"+tipopago+"', '"+cliente+"', '"+ci+"', '"+ref+"', CURRENT_DATE(), CURRENT_TIME(), '"+fechatope+"')").executeUpdate();
        co.desconectar();
    }
    public void add_cuenta_pagar(String tipo,String codigo,String producto,String proveedor,String precio,String preciot,String abo,String total,String cantidad,String fechatope,String factura,String forma,String ref,String resta) throws SQLException{
        co=new Conexion();
        co.getconnection().prepareStatement("INSERT INTO `product`.`cuentas por pagar` (`itm`,`tipo` ,`codigo`, `producto`, `cantidad`, `precio u`, `precio t`, `total`,`abono`, `resta`, `proveedor`, `fecha`, `hora`, `fecha tope`, `factura`, `forma de pago`, `referencia`) " +
                "VALUES (NULL,'"+tipo+"', '"+codigo+"', '"+producto+"', '"+cantidad+"', '"+precio+"', '"+preciot+"', '"+total+"', '"+abo+"','"+resta+"', '"+proveedor+"', CURRENT_DATE(), CURRENT_TIME(), '"+fechatope+"', '"+factura+"', '"+forma+"','"+ref+"')").executeUpdate();
        co.desconectar();
    }
    public String get_prop_producto(String inventario,String codigo,String propiedad) {
        co=new Conexion();
        try {
            Statement statement=co.getconnection().createStatement();
            ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`"+inventario+"` WHERE `codigo` LIKE '"+codigo+"'");
            while(consulta.next()){
                return consulta.getString(propiedad);
            }   }
        catch (SQLException ex) {
        }
        finally {
            co.desconectar();
        }

        return null;
    }

    public void add_producto(String codigo,String producto,String marca,String tipo) throws SQLException{
        co=new Conexion();
        co.getconnection().prepareStatement("INSERT INTO `product`.`productos` (`codigo`, `producto`, `marca`, `tipo`) VALUES ('"+codigo+"', '"+producto+"', '"+marca+"', '"+tipo+"')").executeUpdate();
        if(tipo.equals("Viveres")){
            co.getconnection().prepareStatement("INSERT INTO `product`.`viveres` (`codigo`, `producto`, `marca`, `cantidad`, `costo`, `precio`, `costo total`, `precio total`) VALUES ('"+codigo+"', '"+producto+"', '"+marca+"', '0', '0', '0', '0', '0')").executeUpdate();
        }
        else if(tipo.equals("Charcuteria")){
            co.getconnection().prepareStatement("INSERT INTO `product`.`charcuteria` (`codigo`, `producto`, `marca`, `peso`, `costo`, `precio`, `costo total`, `precio total`) VALUES ('"+codigo+"', '"+producto+"', '"+marca+"', '0', '0', '0', '0', '0')").executeUpdate();
        }
        co.desconectar();
    }
    public void add_usuario(String nombre,String contra,String tipo) throws SQLException{
        co=new Conexion();
        co.getconnection().prepareStatement("INSERT INTO `product`.`usuarios` (`nombre`, `pass`, `tipo`) VALUES ('"+nombre+"', '"+en.getencriptar(contra)+"', '"+tipo+"')").executeUpdate();
        co.desconectar();
    }
    public void add_perdida(String codigo,String producto,String cantidad,String precio,String causa) throws SQLException{
        float preciof=Float.parseFloat(precio);
        float cantidadf=Float.parseFloat(cantidad);
        float preciot=preciof*cantidadf;
        co=new Conexion();
        co.getconnection().prepareStatement("INSERT INTO `product`.`perdidas` (`codigo`, `producto`, `cantidad`, `costo`, `causa`, `costo total`, `fecha`) VALUES ('"+codigo+"', '"+producto+"', '"+cantidad+"', '"+precio+"', '"+causa+"', '"+preciot+"', CURRENT_DATE())").executeUpdate();
        co.desconectar();
    }
    public void editarprecio(String tipo,String codigo,String nprecio) throws SQLException {
        co=new Conexion();
        //co.getconnection().prepareStatement("UPDATE `product`.`"+tipo+"` SET `precio`='"+nprecio+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
        ResultSet consulta=co.getconnection().createStatement().executeQuery("SELECT * FROM `product`.`"+tipo+"` WHERE `codigo` LIKE '"+codigo+"'");
        while (consulta.next()){
                int can=consulta.getInt(4);
                float preciof=Float.parseFloat(nprecio);
                float preciot=can*preciof;
                co.getconnection().prepareStatement("UPDATE `product`.`"+tipo+"` SET `precio`='"+nprecio+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
                co.getconnection().prepareStatement("UPDATE `product`.`"+tipo+"` SET `precio total`='"+preciot+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
        }
        consulta.close();
        co.getconnection().close();
        co.desconectar();
    }
    public void editarcosto(String tipo,String codigo,String nprecio) throws SQLException {
        co=new Conexion();
        //co.getconnection().prepareStatement("UPDATE `product`.`"+tipo+"` SET `precio`='"+nprecio+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
        ResultSet consulta=co.getconnection().createStatement().executeQuery("SELECT * FROM `product`.`"+tipo+"` WHERE `codigo` LIKE '"+codigo+"'");
        while (consulta.next()){
            int can=consulta.getInt(4);
            float preciof=Float.parseFloat(nprecio);
            float preciot=can*preciof;
            co.getconnection().prepareStatement("UPDATE `product`.`"+tipo+"` SET `costo`='"+nprecio+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
            co.getconnection().prepareStatement("UPDATE `product`.`"+tipo+"` SET `costo total`='"+preciot+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
        }
        consulta.close();
        co.getconnection().close();
        co.desconectar();
    }
    public void desconecta(){
        co.desconectar();
    }
    public boolean Elimnausuario(String nombre,String usuario,String contra){
        co=new Conexion();
        if(iniciar(usuario,contra).equals("admin")){
            String encry=nombre;
            try {
                co.getconnection().prepareStatement("DELETE FROM `product`.`usuarios` WHERE `usuarios`.`nombre` = '"+encry+"'").executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            finally {
                co.desconectar();
            }
        }
        else{
            return false;
        }

    }
    public boolean Elimnaprovee(String id,String usuario,String contra){
        co=new Conexion();
        if(iniciar(usuario,contra).equals("admin")){
            try {
                co.getconnection().prepareStatement("DELETE FROM `product`.`proveedores` WHERE `proveedores`.`id` = '"+id+"'").executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            finally {
                co.desconectar();
            }
        }
        else{
            return false;
        }

    }
    public boolean Elimnaproduct(String id,String usuario,String contra,String tipo){
        co=new Conexion();
        if(iniciar(usuario,contra).equals("admin")){
            try {
                tipo=tipo.toLowerCase();
                co.getconnection().prepareStatement("DELETE FROM `product`.`productos` WHERE `productos`.`codigo` = '"+id+"'").executeUpdate();
                co.getconnection().prepareStatement("DELETE FROM `product`.`"+tipo+"` WHERE `codigo` LIKE '"+id+"'").executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            finally {
                co.desconectar();
            }
        }
        else{
            return false;
        }

    }
    public boolean editacontra(String usuario,String contra,String ncontra){
        co=new Conexion();
        if(iniciar(usuario,contra).equals("admin")||iniciar(usuario,contra).equals("cajero")){
            String encry=en.getencriptar(ncontra);
            try {
                System.out.println("si");
                co.getconnection().prepareStatement("UPDATE `product`.`usuarios` SET `pass`='"+encry+"' WHERE `nombre` LIKE '"+usuario+"' ").executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            finally {
                co.desconectar();
            }
        }
        else{
            return false;
        }
    }
    public boolean editanombre(String usuario,String contra,String nusuario){
        co=new Conexion();
        try {
            if(iniciar(usuario,contra).equals("admin")||iniciar(usuario,contra).equals("cajero")){
                String encry=nusuario;
                co.getconnection().prepareStatement("UPDATE `product`.`usuarios` SET `nombre`='"+encry+"' WHERE `nombre` LIKE '"+usuario+"' ").executeUpdate();
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            co.desconectar();
        }
    }
    public String empresa() {
        co=new Conexion();
        try {
            statement=co.getconnection().createStatement();

            ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`nombre`");
            while(consulta.next()){
                return consulta.getString(1);}
            return "";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            co.desconectar();
        }
        return "";
    }
    public String precio(String tipo,String codi){
        co=new Conexion();
        try {
                    statement=co.getconnection().createStatement();
            ResultSet consulta= null;

            consulta = statement.executeQuery("SELECT * FROM `product`.`"+tipo+"` where `codigo` LIKE '"+codi+"'");
            while(consulta.next()){
                System.out.println();
                return consulta.getString("precio");}
            return "0.0";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            co.desconectar();
        }
        return "0.0";
    }
    public void perdida(String tipo,String codigo,String cantidad) throws SQLException {
        co=new Conexion();
        statement=co.getconnection().createStatement();
        ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`"+tipo+"` WHERE `codigo` LIKE '"+codigo+"'");
        while(consulta.next()){
            if(tipo.equals("Charcuteria")){
                float c=consulta.getFloat("unidades");
                float unidades=Float.parseFloat(cantidad);
                float nunidades=c-unidades;
                float costo=consulta.getFloat("costo");
                float precio=consulta.getFloat("precio");
                float nct=costo*nunidades;
                float preciot=precio*unidades;
                co.getconnection().prepareStatement("UPDATE `product`.`charcuteria` SET `peso`='"+nunidades+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
                co.getconnection().prepareStatement("UPDATE `product`.`charcuteria` SET `costo total`='"+nct+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
                co.getconnection().prepareStatement("UPDATE `product`.`charcuteria` SET `precio total`='"+preciot+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
            }
            else {
                float c=consulta.getFloat("cantidad");
                float unidades=Float.parseFloat(cantidad);
                float nunidades=c-unidades;
                float costo=consulta.getFloat("costo");
                float nct=costo*nunidades;
                float precio=consulta.getFloat("precio");
                float preciot=precio*unidades;
                co.getconnection().prepareStatement("UPDATE `product`.`"+tipo+"` SET `cantidad`='"+nunidades+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
                co.getconnection().prepareStatement("UPDATE `product`.`"+tipo+"` SET `costo total`='"+nct+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
                co.getconnection().prepareStatement("UPDATE `product`.`"+tipo+"` SET `precio total`='"+preciot+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
            }
        }
        co.desconectar();
    }
    public void abonacp(String cuenta,String abono,String ref,String pago,String resta) throws SQLException {
        co=new Conexion();
        statement=co.getconnection().createStatement();
        ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`cuentas por pagar` WHERE `itm` LIKE '"+cuenta+"'");
        while(consulta.next()){
            String sabono=consulta.getString("abono");
            String sresta=consulta.getString("resta");
            String nsresta=sresta.substring(0,sresta.length()-3);
            float fresta=Float.parseFloat(nsresta);
            float fabono=Float.parseFloat(abono);
            float nresta=fresta-fabono;
            String nabono=sabono+'\n'+abono;
            String ref2=consulta.getString("referencia");
            String nref=ref2+'\n'+ref;
            String f2=consulta.getString("forma de pago");
            String nf=f2+'\n'+pago;
            co.getconnection().prepareStatement("UPDATE `product`.`cuentas por pagar` SET `abono`='"+nabono+"' WHERE `itm` LIKE '"+cuenta+"' ").executeUpdate();
            co.getconnection().prepareStatement("UPDATE `product`.`cuentas por pagar` SET `resta`='"+resta+"' WHERE `itm` LIKE '"+cuenta+"' ").executeUpdate();
            co.getconnection().prepareStatement("UPDATE `product`.`cuentas por pagar` SET `referencia`='"+nref+"' WHERE `itm` LIKE '"+cuenta+"' ").executeUpdate();
            co.getconnection().prepareStatement("UPDATE `product`.`cuentas por pagar` SET `forma de pago`='"+nf+"' WHERE `itm` LIKE '"+cuenta+"' ").executeUpdate();
        }
        co.desconectar();
    }
    public void abonacc(String cuenta,String abono,String ref,String pago,String resta) throws SQLException {
        co=new Conexion();
        statement=co.getconnection().createStatement();
        ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`cuentas por cobrar` WHERE `itm` LIKE '"+cuenta+"'");
        while(consulta.next()){
            String sabono=consulta.getString("abono");
            String sresta=consulta.getString("resta");
            String nsresta=sresta.substring(0,sresta.length()-3);
            float fresta=Float.parseFloat(nsresta);
            float fabono=Float.parseFloat(abono);
            float nresta=fresta-fabono;
            String nabono=sabono+'\n'+abono;
            String ref2=consulta.getString("referencia");
            String nref=ref2+'\n'+ref;
            String f2=consulta.getString("tipo de pago");
            String nf=f2+'\n'+pago;
            float ppresta=Float.parseFloat(resta);
            co.getconnection().prepareStatement("UPDATE `product`.`cuentas por cobrar` SET `abono`='"+nabono+"' WHERE `itm` LIKE '"+cuenta+"' ").executeUpdate();
            co.getconnection().prepareStatement("UPDATE `product`.`cuentas por cobrar` SET `resta`='"+ppresta+"' WHERE `itm` LIKE '"+cuenta+"' ").executeUpdate();
            co.getconnection().prepareStatement("UPDATE `product`.`cuentas por cobrar` SET `referencia`='"+nref+"' WHERE `itm` LIKE '"+cuenta+"' ").executeUpdate();
            co.getconnection().prepareStatement("UPDATE `product`.`cuentas por cobrar` SET `tipo de pago`='"+nf+"' WHERE `itm` LIKE '"+cuenta+"' ").executeUpdate();
        }
        co.desconectar();
    }
    public void editaproducto(String codigo,String producto,String marca,String tipo) throws SQLException {
        tipo=tipo.trim();
        marca=marca.trim();
        producto=producto.trim();
        co=new Conexion();
        co.getconnection().prepareStatement("UPDATE `product`.`productos` SET `producto`='"+producto+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
        co.getconnection().prepareStatement("UPDATE `product`.`productos` SET `marca`='"+marca+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
        co.getconnection().prepareStatement("UPDATE `product`.`"+tipo+"` SET `producto`='"+producto+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
        co.getconnection().prepareStatement("UPDATE `product`.`"+tipo+"` SET `marca`='"+marca+"' WHERE `codigo` LIKE '"+codigo+"' ").executeUpdate();
        co.desconectar();
    }
    public void add_consumo(String tipo,String codigo,String producto,String cantidad) throws SQLException{

        String gcosto=get_prop_producto(tipo,codigo,"costo");
        float costof=Float.parseFloat(gcosto);
        float cantidadf=Float.parseFloat(cantidad);
        float costot=costof*cantidadf;
        co=new Conexion();
        co.getconnection().prepareStatement("INSERT INTO `product`.`consumo interno` (`codigo`, `producto`, `cantidad`, `costo`, `costo total`, `fecha`, `hora`) VALUES ('"+codigo+"', '"+producto+"', '"+cantidad+"', '"+costof+"', '"+costot+"', CURRENT_DATE(), CURRENT_TIME())").executeUpdate();
        co.desconectar();
    }
    public void setempresa(String empresa) throws SQLException {
        co=new Conexion();
        co.getconnection().prepareStatement("UPDATE `product`.`nombre` SET `empresa` = '"+empresa+"'").executeUpdate();
        co.desconectar();
    }

    public ArrayList cuentapagar(String cuenta){
        co=new Conexion();
        try {
            ArrayList materia=new ArrayList();
            statement=co.getconnection().createStatement();
            ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`cuentas por pagar` WHERE `itm` = '"+cuenta+"'");
            while(consulta.next()){
                materia.add(consulta.getString("total"));
                materia.add(consulta.getString("abono"));
                materia.add(consulta.getString("resta"));
                materia.add(consulta.getString("proveedor"));
                materia.add(consulta.getString("fecha tope"));
                materia.add(consulta.getString("factura"));
            }
            return materia;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            co.desconectar();
        }
        return null;
    }
    public ArrayList cuentacobrar(String cuenta){
        co=new Conexion();
        ArrayList materia2=new ArrayList();
        try {

            statement=co.getconnection().createStatement();
            ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`cuentas por cobrar` WHERE `itm` = '"+cuenta+"'");
            while(consulta.next()){
                materia2.add(consulta.getString("total"));
                materia2.add(consulta.getString("abono"));
                materia2.add(consulta.getString("resta"));
                materia2.add(consulta.getString("cliente"));
                materia2.add(consulta.getString("fecha tope"));
                materia2.add(consulta.getString("ci"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }finally {
            co.desconectar();
            return materia2;
        }
    }
    public ArrayList productos(String tipo,String busqueda){
        co=new Conexion();
        try {
            ArrayList materia=new ArrayList();
            statement=co.getconnection().createStatement();

            ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`productos` where tipo like '"+tipo+"' AND  `codigo` like  '%"+busqueda+"%'");
            while(consulta.next()){
                materia.add(consulta.getString(1)+": "+consulta.getString(2)+" marca:"+consulta.getString(3));
            }
            return materia;
        } catch (SQLException ex) {
            return null;
        }
        finally {
            co.desconectar();
        }
    }

    public ArrayList viveres(){
        co=new Conexion();
        try {
            ArrayList materia=new ArrayList();
            statement=co.getconnection().createStatement();
            ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`productos` WHERE `tipo` LIKE 'Viveres'");
            while(consulta.next()){
                materia.add(consulta.getString(1)+": "+consulta.getString(2)+" ("+consulta.getString(3)+")");
            }
            return materia;
        } catch (SQLException ex) {
            return null;
        }finally {
            co.desconectar();
        }
    }
    public ArrayList proveedores(){
        co=new Conexion();
        try {
            ArrayList proveedor=new ArrayList();
            statement=co.getconnection().createStatement();
            ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`proveedores`");
            while(consulta.next()){
                proveedor.add(consulta.getString(1)+": "+consulta.getString(2)+" ("+consulta.getString(3)+")");
            }
            return proveedor;
        } catch (SQLException ex) {
            return null;
        }
        finally {
            co.desconectar();
        }

    }
    public ArrayList charcuteria(){
        co=new Conexion();
        try {
            ArrayList charcuteria=new ArrayList();
            statement=co.getconnection().createStatement();
            ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`productos` WHERE `tipo` LIKE 'Charcuteria'");
            while(consulta.next()){
                charcuteria.add(consulta.getString(1)+": "+consulta.getString(2)+" ("+consulta.getString(3)+")");
            }
            return charcuteria;
        } catch (SQLException ex) {
            return null;
        }finally {
            co.desconectar();
        }
    }
    public ArrayList usuario(String usuario){
        co=new Conexion();
        try {
            ArrayList usuarios=new ArrayList();
            statement=co.getconnection().createStatement();
            String usuen=usuario;
            ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`usuarios` WHERE `nombre` NOT LIKE '"+usuen+"'");
            while(consulta.next()){
                usuarios.add(consulta.getString(2));
            }
            return usuarios;
        } catch (SQLException ex) {
            return null;
        }finally {
            co.desconectar();
        }
    }
    public ArrayList proveedores(String busqueda){
        co=new Conexion();
        try {
            ArrayList usuarios=new ArrayList();
            statement=co.getconnection().createStatement();
            ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`proveedores` WHERE `nombre` LIKE '"+busqueda+"'");
            while(consulta.next()){
                usuarios.add(consulta.getString(2));
            }
            return usuarios;
        } catch (SQLException ex) {
            return null;
        }finally {
            co.desconectar();
        }
    }
    public String getusuario(String id){
        co=new Conexion();
        try {
            statement=co.getconnection().createStatement();
            ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`usuarios` WHERE `id` LIKE '"+id+"'");
            while(consulta.next()){
                return consulta.getString(2);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        finally {
            co.desconectar();
        }
        return null;
    }
    public String getid(String usuario){
        co=new Conexion();
        try {
            statement=co.getconnection().createStatement();
            String usuen=usuario;
            ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`usuarios` WHERE `nombre` LIKE '"+usuen+"'");
            while(consulta.next()){
                return consulta.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;

        }
        finally {
            co.desconectar();
        }
        return null;
    }
    public ArrayList viveres(String bus){
        co=new Conexion();
        try {
            ArrayList materia=new ArrayList();
            statement=co.getconnection().createStatement();
            ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`viveres` WHERE `codigo` LIKE '%"+bus+"%' OR `producto` LIKE '%"+bus+"%' or `marca` LIKE '%"+bus+"%'");
            while(consulta.next()){
                materia.add(consulta.getString(1)+": "+consulta.getString(2)+" marca: "+consulta.getString(3)+" dispo:"+consulta.getString(4)+" $:"+consulta.getString(6));
            }
            return materia;
        } catch (SQLException ex) {
            return null;
        }finally {
            co.desconectar();
        }
    }
    public ArrayList viverescom(String bus){
        co=new Conexion();
        try {
            ArrayList materia=new ArrayList();
            statement=co.getconnection().createStatement();
            ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`viveres` WHERE `codigo` LIKE '%"+bus+"%' OR `producto` LIKE '%"+bus+"%' or `marca` LIKE '%"+bus+"%'");
            while(consulta.next()){
                materia.add(consulta.getString(1)+": "+consulta.getString(2)+" marca: "+consulta.getString(3));
            }
            return materia;
        } catch (SQLException ex) {
            return null;
        }finally {
            co.desconectar();
        }
    }
    public ArrayList galletas(String bus){
        co=new Conexion();
        try {
            ArrayList materia=new ArrayList();
            statement=co.getconnection().createStatement();
            ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`galletas` WHERE `codigo` LIKE '%"+bus+"%' OR `producto` LIKE '%"+bus+"%' ");
            while(consulta.next()){
                materia.add(consulta.getString(1)+": "+consulta.getString(2)+" dispo: "+consulta.getString(3)+" $:"+consulta.getString(6));
            }
            return materia;
        } catch (SQLException ex) {
            return null;
        }finally {
            co.desconectar();
        }
    }

    public ArrayList productos_lo(String bus){
        co=new Conexion();
        try {
            ArrayList materia=new ArrayList();
            statement=co.getconnection().createStatement();
            ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`local` WHERE `codigo` LIKE '%"+bus+"%' OR `producto` LIKE '%"+bus+"%'");
            while(consulta.next()){
                materia.add(consulta.getString(1)+": "+consulta.getString(2)+" dispo: "+consulta.getString(3)+" $:"+consulta.getString(6));
            }
            return materia;
        } catch (SQLException ex) {
            return null;
        }finally {
            co.desconectar();
        }
    }
    public ArrayList charcuteria(String bus){
        co=new Conexion();
        try {
            ArrayList charcuteria=new ArrayList();
            statement=co.getconnection().createStatement();
            ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`charcuteria` WHERE `codigo` LIKE '%"+bus+"%' OR `producto` LIKE '%"+bus+"%' or `marca` LIKE '%"+bus+"%'");
            while(consulta.next()){
                charcuteria.add(consulta.getString(1)+": "+consulta.getString(2)+" marca: "+consulta.getString(3)+" dispo:"+consulta.getString(4)+" $:"+consulta.getString(6));
            }
            return charcuteria;
        } catch (SQLException ex) {
            return null;
        }
        finally {
            co.desconectar();
        }
    }
    public ArrayList charcuteriacom(String bus){
        co=new Conexion();
        try {
            ArrayList charcuteria=new ArrayList();
            statement=co.getconnection().createStatement();
            ResultSet consulta=statement.executeQuery("SELECT * FROM `product`.`charcuteria` WHERE `codigo` LIKE '%"+bus+"%' OR `producto` LIKE '%"+bus+"%' or `marca` LIKE '%"+bus+"%'");
            while(consulta.next()){
                charcuteria.add(consulta.getString(1)+": "+consulta.getString(2)+" marca: "+consulta.getString(3));
            }
            return charcuteria;
        } catch (SQLException ex) {
            return null;
        }
        finally {
            co.desconectar();
        }
    }
}
