
package Admin;

import Cajero.*;
import Conexion.Conexion;

import com.jfoenix.controls.*;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import extras.Fecha;
import extras.Metodos;
import extras.Pdf;
import extras.Textoampo;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.dialog.Dialogs;
import sample.Acerca;
import sample.Main;

public class AdminEvent implements Initializable {
    private int estado;
    private Admin ProgramaPrincipal;
    private ObservableList<ObservableList> data;
    private String usuarion, datefecha, datefecha2,contra;

    @FXML
    private Tab principal;
    @FXML
    private TabPane tabs;
    @FXML
    private VBox ventana;
    ObservableList<listcompra> listacom = FXCollections.observableArrayList();
    @FXML
    private TableView<listcompra> compratabla;
    @FXML
    private TableColumn<listcompra, String> tipop, codigol, productol, cantidadl, precioul, preciotl;
    @FXML
    private TableColumn<listcompra,JFXButton> elimina;
    @FXML
    private TableView table;
     @FXML  private JFXComboBox  pago, proveedor, co_vive, pro_char;
    @FXML
    private Label usuario, titulo, empresa, resta, lprecio,error,items,itemcom, errorcompra;
    @FXML
    private JFXListView lista;
    @FXML
    private JFXDatePicker fecha1, fecha2, fechac1;
    @FXML
    private JFXTextField precioukgchar,pesounichar,busqueda, fact, ref, abono,  busvive,buschar,preciouvive,cantivive;
    @FXML
    private JFXButton full;
    private Fecha getfecha = new Fecha();
    private Pdf pdfs;
    private float precc;
    private Metodos metodos;

    private boolean datepickerboo() {
        datefecha = fecha1.getEditor().getText();
        datefecha2 = fecha2.getEditor().getText();
        if (datefecha.length() == 0 || datefecha2.length() == 0) {
            return false;
        } else {
            return true;
        }
    }

    private String datepicker() {
        datefecha = fecha1.getEditor().getText();
        datefecha2 = fecha2.getEditor().getText();
        if (datefecha.length() == 0 || datefecha2.length() == 0) {
            return "0";
        } else {
            String dia = datefecha.substring(0, 2);
            String mes = datefecha.substring(3, 5);
            String agno = datefecha.substring(6);
            String dia2 = datefecha2.substring(0, 2);
            String mes2 = datefecha2.substring(3, 5);
            String agno2 = datefecha2.substring(6);
            return agno + "-" + mes + "-" + dia + "' AND '" + agno2 + "-" + mes2 + "-" + dia2;
        }
    }

    @FXML
    public void compra() {
        if(compratabla.getItems().size()==0){
            errorcompra.setText("Errorn en Compra");
        }
        else {
            metodos = new Metodos();
            try {
                String tipo_s = "";
                String precio_s = "";
                String preciot_s = "";
                String codigo_s = "";
                String producto_s = "";
                String cantidad_s = "";
                for (int i = 0; i < compratabla.getItems().size(); i++) {
                    codigo_s += compratabla.getItems().get(i).getCodigo() + '\n';
                    producto_s += compratabla.getItems().get(i).getProducto() + '\n';
                    cantidad_s += compratabla.getItems().get(i).getCantidad() + '\n';
                    precio_s += compratabla.getItems().get(i).getPreciou() + '\n';
                    preciot_s += compratabla.getItems().get(i).getPreciot() + '\n';
                    tipo_s += compratabla.getItems().get(i).getTipo() + '\n';
                }
                String fecha = fechac1.getEditor().getText();
                if (pago.getSelectionModel().getSelectedIndex() == 0) {
                    metodos.add_cuenta_pagar(tipo_s, codigo_s, producto_s, proveedor.getSelectionModel().getSelectedItem().toString(), precio_s, preciot_s, abono.getText() + "", lprecio.getText(), cantidad_s, fecha, fact.getText(), "", "", resta.getText());
                } else {
                    metodos.add_cuenta_pagar(tipo_s, codigo_s, producto_s, proveedor.getSelectionModel().getSelectedItem().toString(), precio_s, preciot_s, abono.getText() + "", lprecio.getText(), cantidad_s, fecha, fact.getText(), pago.getSelectionModel().getSelectedItem().toString(), ref.getText(), resta.getText());
                }
                for (int i = 0; i < compratabla.getItems().size(); i++) {
                    if (compratabla.getItems().get(i).getTipo().equals("Viveres")) {
                        metodos.add_viveres(compratabla.getItems().get(i).getCodigo(), compratabla.getItems().get(i).getCantidad(), compratabla.getItems().get(i).getPreciou());
                    } else {
                        metodos.add_charcuteria(compratabla.getItems().get(i).getCodigo(), compratabla.getItems().get(i).getCantidad(), compratabla.getItems().get(i).getPreciou());
                    }
                }
                errorcompra.setText("Compra realizada");
                eliminart();
                abono.setText("");
                ref.setText("");
                fact.setText("");
            } catch (SQLException e) {
                errorcompra.setText("Factura repetida");
                e.printStackTrace();
            } finally {
                Timer t = new Timer();
                TimerTask tt = new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> errorcompra.setText(""));
                    }
                };
                t.schedule(tt, 2000);
                cuentaspt();
            }
        }
    }
    @FXML
    public void eliminart() {
        lprecio.setText("0.00");
        resta.setText("0.00");
        compratabla.getItems().clear();
    }

    @FXML
    public void abonar() {
        try {
            float ab = Float.parseFloat(abono.getText());
            float resto = precc - ab;
            resta.setText(resto + "");
        } catch (Exception e) {
        }
    }

    @FXML
    public void eliminar() {
        lista.getItems().clear();
    }

    private void datostablacompra(){
        tipop.setCellValueFactory(cellData -> cellData.getValue().tipoProperty());
        codigol.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
        productol.setCellValueFactory(cellData -> cellData.getValue().productoProperty());
        cantidadl.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty());
        preciotl.setCellValueFactory(cellData -> cellData.getValue().preciotProperty());
        precioul.setCellValueFactory(cellData -> cellData.getValue().preciouProperty());
        elimina.setCellValueFactory(cellData -> cellData.getValue().eliminaProperty());
        compratabla.setItems(listacom);
        setcancompr();
    }

    private static AdminEvent instance;
    public static synchronized AdminEvent getInstance() {

        if(instance == null)
            instance = new AdminEvent();

        return instance;
    }
    @FXML
    public void agrega() {
        JFXButton eli=new JFXButton("Borrar");
        eli.setOnMouseReleased(event -> {
            setcancompr();
            setprecio_eliminar();
        });
            String pro = co_vive.getSelectionModel().getSelectedItem().toString();
            float can = Float.parseFloat(cantivive.getText());
            float preci = Float.parseFloat(preciouvive.getText());
            float precit = can * preci;
            boolean encontrado=false;
            int nencontrado=0;
            for(int i=0;i<compratabla.getItems().size();i++){
                if(compratabla.getItems().get(i).codigoProperty().getValue().equals(pro.substring(0, pro.indexOf(":")))){
                    encontrado=true;
                    nencontrado=i;
                }
            }
            if(encontrado){
                int cant=Integer.parseInt(cantivive.getText());
                float anterior=Float.parseFloat(compratabla.getItems().get(nencontrado).cantidadProperty().getValue());
                float cantt=precit;
                float anteriort=Float.parseFloat(compratabla.getItems().get(nencontrado).preciotProperty().getValue());
                compratabla.getItems().get(nencontrado).cantidadProperty().setValue(cant+anterior+"");
                compratabla.getItems().get(nencontrado).preciotProperty().setValue(cantt+anteriort+"");
            }else {
                listacom.add(new listcompra("Viveres", pro.substring(0, pro.indexOf(":")), pro.substring(pro.indexOf(": ") + 2), cantivive.getText(), preci + "", precit + "", eli, compratabla));
            }
            datostablacompra();
            precc += precit;
            lprecio.setText(precc + "");
            abonar();
            cantivive.setText("0");
            preciouvive.setText("0.00");
    }
    private void setprecio_eliminar(){
        precc=0;
        for(int i=0;i<compratabla.getItems().size();i++){
            precc+=Float.parseFloat(compratabla.getItems().get(i).preciotProperty().getValue());
        }
        lprecio.setText(precc + "");
        abonar();
    }
    @FXML
    private void agregamchracuteriacom() {
        JFXButton eli=new JFXButton("Borrar");
        eli.setId("elimina");
        eli.setOnAction(event -> {
            setcancompr();
            setprecio_eliminar();
        });
        String pro = pro_char.getSelectionModel().getSelectedItem().toString();
        float can = Float.parseFloat(pesounichar.getText());
        float preci = Float.parseFloat(precioukgchar.getText());
        float precit = can * preci;
        boolean encontrado=false;
        int nencontrado=0;
        for(int i=0;i<compratabla.getItems().size();i++){
            if(compratabla.getItems().get(i).codigoProperty().getValue().equals(pro.substring(0, pro.indexOf(":")))){
                encontrado=true;
                nencontrado=i;
            }
        }
        if(encontrado){
            float cant=Float.parseFloat(pesounichar.getText());
            float anterior=Float.parseFloat(compratabla.getItems().get(nencontrado).cantidadProperty().getValue());
            float cantt=precit;
            float anteriort=Float.parseFloat(compratabla.getItems().get(nencontrado).preciotProperty().getValue());
            compratabla.getItems().get(nencontrado).cantidadProperty().setValue(cant+anterior+"");
            compratabla.getItems().get(nencontrado).preciotProperty().setValue(cantt+anteriort+"");
        }else {
            listacom.add(new listcompra("Charcuteria", pro.substring(0, pro.indexOf(":")), pro.substring(pro.indexOf(": ") + 2), pesounichar.getText(), preci + "", precit + "",eli,compratabla));
        }
        datostablacompra();
        precc += precit;
        lprecio.setText(precc + "");
        abonar();
        pesounichar.setText("0.00");
        preciouvive.setText("0.00");
    }
    @FXML
    public void elimina() {
//        listacom.remove(compratabla.getSelectionModel().getSelectedIndex());
    }

    @FXML
    public void agrega_usuario() {
        try {
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AgregarUsuario.fxml"));
            AnchorPane ventanaDos = (AnchorPane) loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Agregar Usuario");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
            ventana.setResizable(false);
            ventana.getIcons().add(new Image(getClass().getResource("../sample/product+.png").toExternalForm()));
            AgregausuEvent controller = loader.getController();
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void pdf(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        pdfs = new Pdf();
        fileChooser.setInitialFileName(titulo.getText() + ".pdf");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        File file = fileChooser.showSaveDialog(((Node) actionEvent.getSource()).getScene().getWindow());
        pdfs.utilJTableToPdf(file, titulo.getText(), table);
    }
    public void recibe(String  em){
        empresa.setText(em);
    }
    public void recibeusu(String  em){
        usuario.setText(em);
    }
    public void cambiaem() {

        try {
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Empresa.fxml"));
            AnchorPane ventanaDos = (AnchorPane) loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Cambiar empresa");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
            ventana.setResizable(false);
            ventana.getIcons().add(new Image(getClass().getResource("../sample/product+.png").toExternalForm()));
            EmpresaEvent controller = loader.getController();
            metodos=new Metodos();
            controller.parametros(this,empresa,metodos.empresa());
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void cambiacontra() {
        Editarcontra agregar = new Editarcontra();
        agregar.mostrarVentanaSecundaria(usuarion);
    }
    private String id;
    public void setUsu(String id){
            metodos=new Metodos();
            String us= metodos.getusuario(id);
            usuarion =us;
        usuario.setText(us);
    }
    public void cambiausuario() {

        metodos=new Metodos();
        try {
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Editarusuario.fxml"));
            AnchorPane ventanaDos = (AnchorPane) loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Editar usuario");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
            ventana.setResizable(false);
            ventana.getIcons().add(new Image(getClass().getResource("../sample/product+.png").toExternalForm()));
            EditarusuEvent controller = loader.getController();
            controller.setUsuario(usuario.getText());
            controller.parametros(this,usuario,metodos.getusuario(id));
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.show();
            ventana.setOnHiding(event -> {
                setusuario();
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void eliminausu() {
        try {
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EliminarUsuario.fxml"));
            AnchorPane ventanaDos = (AnchorPane) loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Editar usuario");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
            ventana.setResizable(false);
            ventana.getIcons().add(new Image(getClass().getResource("../sample/product+.png").toExternalForm()));
            EliminausuEvent controller = loader.getController();
            controller.setUsuario(usuario.getText());
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setUsuario(String usua) {
        metodos=new Metodos();
        usuarion = usua;
        id=metodos.getid(usua);
        usuario.setText(usua);
    }
    public void setusuario(){
        metodos=new Metodos();
        usuario.setText(metodos.getusuario(id));
    }

    public String getUsuario() {
        return usuario.getText();
    }
    private Conexion co;
    @FXML
    public void tabla(String sql, String sql2) {
        Connection c;
        co = new Conexion();
        data = FXCollections.observableArrayList();
        try {
            c = co.getconnection();
            table.getColumns().clear();
            String SQL = "SELECT * FROM `product`.`" + sql + "` " + sql2 + "";
            ResultSet rs = c.createStatement().executeQuery(SQL);
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));
                table.getColumns().addAll(col);
            }
            int itm=0;
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
                itm++;
            }
            items.setText("Items: "+itm);
            table.setItems(data);
            titulo.setText(sql);
            table.setOnMousePressed(event -> {});
        } catch (Exception e) {
            e.printStackTrace();

        }
        finally {
            co.desconectar();
        }
    }

    @FXML
    public void buscar() {

        if (estado == 1) {
            fecha1.setVisible(false);
            fecha2.setVisible(false);

            tabla("viveres", "WHERE `codigo` LIKE '%" + busqueda.getText() + "%' OR `producto` LIKE '%" + busqueda.getText() + "%' OR `marca` LIKE '%" + busqueda.getText() + "%'");
            table.setOnMousePressed(event -> {
                ObservableList<String> e = FXCollections.observableArrayList();
                e.addAll((ObservableList<String>) table.getItems().get(table.getSelectionModel().getFocusedIndex()));
                precios(0,e.get(0));
            });
        } else if (estado == 2) {
            fecha1.setVisible(true);
            fecha2.setVisible(true);
            table.setOnKeyPressed(event -> {
            });
            tabla("cuentas por pagar", "WHERE `codigo` LIKE '%" + busqueda.getText() + "%' OR `producto` LIKE '%" + busqueda.getText() + "%' OR `proveedor` LIKE '%" + busqueda.getText() + "%'");
        } else if (estado == 3) {
            fecha1.setVisible(false);
            fecha2.setVisible(false);
            tabla("charcuteria", "WHERE `codigo` LIKE '%" + busqueda.getText() + "%' OR `producto` LIKE '%" + busqueda.getText() + "%' OR `marca` LIKE '%" + busqueda.getText() + "%'");
            table.setOnMousePressed(event -> {
                ObservableList<String> e = FXCollections.observableArrayList();
                e.addAll((ObservableList<String>) table.getItems().get(table.getSelectionModel().getFocusedIndex()));
                precios(1,e.get(0));
            });
        } else if (estado == 4) {
            fecha1.setVisible(true);
            fecha2.setVisible(true);
            tabla("cuenta por cobrar", "WHERE `codigo` LIKE '%" + busqueda.getText() + "%' OR `producto` LIKE '%" + busqueda.getText() + "%' OR `cliente` LIKE '%" + busqueda.getText() + "%'");
            table.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    ven_abonar();
                }
            });
        } else if (estado == 5) {
            fecha1.setVisible(false);
            fecha2.setVisible(false);
            table.setOnMousePressed(event -> {
            });
            tabla("productos", "WHERE `codigo` LIKE '%" + busqueda.getText() + "%' OR `producto` LIKE '%" + busqueda.getText() + "%' OR `marca` LIKE '%" + busqueda.getText() + "%'");
        } else if (estado == 6) {
            fecha1.setVisible(true);
            fecha2.setVisible(true);
            table.setOnKeyPressed(event -> {
            });
            tabla("consumo interno", "WHERE `codigo` LIKE '%" + busqueda.getText() + "%' OR `producto` LIKE '%" + busqueda.getText() + "%");
        }  else if (estado == 8) {
            fecha1.setVisible(false);
            fecha2.setVisible(false);
            table.setOnKeyPressed(event -> {
            });
            tabla("proveedores", "WHERE `nombre` LIKE '%" + busqueda.getText() + "%' OR `rif` LIKE '%" + busqueda.getText() + "%' OR `ciudad` LIKE '%" + busqueda.getText() + "%'");
        } else if (estado == 9) {
            fecha1.setVisible(true);
            fecha2.setVisible(true);
            table.setOnKeyPressed(event -> {
            });
            tabla("ventas", "WHERE `codigo` LIKE '%" + busqueda.getText() + "%' OR `producto` LIKE '%" + busqueda.getText() + "%' OR `cliente` LIKE '%" + busqueda.getText() + "%' OR `ci` LIKE '%" + busqueda.getText() + "%'");
        } else if (estado == 10) {
            fecha1.setVisible(false);
            fecha2.setVisible(false);
            table.setOnKeyPressed(event -> {
            });
            tabla("perdidas", "WHERE `codigo` LIKE '%" + busqueda.getText() + "%' OR `producto` LIKE '%" + busqueda.getText() + "%' OR `marca` LIKE '%" + busqueda.getText() + "%'");
        }
    }

    @FXML
    public void viverest() {
        busqueda.setText("");
        estado = 1;
        fecha1.setVisible(false);
        fecha2.setVisible(false);
        tabla("viveres", "");
        table.setOnMousePressed(event -> {
            ObservableList<String> e = FXCollections.observableArrayList();
            e.addAll((ObservableList<String>) table.getItems().get(table.getSelectionModel().getFocusedIndex()));
            precios(0,e.get(0));
        });
    }
    @FXML
    public void acerca() {
        Acerca acerca = new Acerca();
        acerca.mostrarVentanaSecundaria();
    }

    @FXML
    public void fecha() {
        if (estado == 2) {
            cuentaspt();
        } else if (estado == 4) {
            cuentact();
        } else if (estado == 6) {
            consumot();
        } else if (estado == 9) {
            ventast();
        } else if (estado == 10) {
            perdidast();
        }
    }

    @FXML
    public void cuentaspt() {
        estado = 2;
        fecha1.setVisible(true);
        fecha2.setVisible(true);
        if (datepickerboo()) {
            tabla("cuentas por pagar", "WHERE `fecha` BETWEEN '" + datepicker() + "'");
        } else {
            tabla("cuentas por pagar", "WHERE `resta` NOT LIKE '0.0' AND `resta` NOT LIKE '%-%' AND `resta` NOT LIKE '0.00' ");
        }
        table.setOnMousePressed(event -> {
            try {
                ObservableList<String> e = FXCollections.observableArrayList();
                e.addAll((ObservableList<String>) table.getItems().get(table.getSelectionModel().getFocusedIndex()));
                    Stage stage=new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Abonarcp.fxml"));
                    AnchorPane ventanaDos = (AnchorPane) loader.load();
                    Stage ventana = new Stage();
                    ventana.setTitle("Abonar cuenta por pagar");
                    ventana.initOwner(stage);
                    Scene scene = new Scene(ventanaDos);
                    ventana.setScene(scene);
                    ventana.setResizable(false);
                    ventana.getIcons().add(new Image(getClass().getResource("../sample/product+.png").toExternalForm()));
                    AbonarcpEvent controller = loader.getController();
                    controller.setcuenta(e.get(0));
                    ventana.initModality(Modality.APPLICATION_MODAL);
                    ventana.show();
                    ventana.setOnHiding(event1 -> {
                        cuentaspt();
                    });
            } catch (Exception e) {
            }
        });

    }

    @FXML
    public void charcuteriat() {
        busqueda.setText("");
        estado = 3;
        fecha1.setVisible(false);
        fecha2.setVisible(false);
        tabla("charcuteria", "");
        table.setOnMousePressed(event -> {
            ObservableList<String> e = FXCollections.observableArrayList();
            e.addAll((ObservableList<String>) table.getItems().get(table.getSelectionModel().getFocusedIndex()));
            precios(1,e.get(0));
        });
    }

    @FXML
    public void cuentact() {
        estado = 4;
        fecha1.setVisible(true);
        fecha2.setVisible(true);
        if (datepickerboo()) {
            tabla("cuentas por cobrar", "WHERE `fecha` BETWEEN '" + datepicker() + "'");
        } else {
            tabla("cuentas por cobrar", "WHERE `resta` NOT LIKE '0.0' AND `resta` NOT LIKE '%-%' AND `resta` NOT LIKE '0.00' ");//WHERE `resta` NOT LIKE '0.0 Bs' AND `resta` NOT LIKE '0.00 Bs'
        }
        table.setOnMousePressed(event -> {
                ven_abonar();
        });
    }

    private void ven_abonar() {
        try {
            ObservableList<String> e = FXCollections.observableArrayList();
            e.addAll((ObservableList<String>) table.getItems().get(table.getSelectionModel().getFocusedIndex()));
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Cajero/Abonarcc.fxml"));
            AnchorPane ventanaDos = (AnchorPane) loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Abonar cuenta por cobrar");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
            ventana.setResizable(false);
            ventana.getIcons().add(new Image(getClass().getResource("../sample/product+.png").toExternalForm()));
            AbonarccEvent controller = loader.getController();
            controller.setpro(e.get(0));
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.show();
            ventana.setOnHiding(event1 -> {
                cuentact();
            });
        } catch (Exception e) {
        }
    }

    @FXML
    public void listapro() {
        estado = 5;
        fecha1.setVisible(false);
        fecha2.setVisible(false);
        tabla("productos", "");
        table.setOnMousePressed(event -> {
            ObservableList<String> e = FXCollections.observableArrayList();
            e.addAll((ObservableList<String>) table.getItems().get(table.getSelectionModel().getFocusedIndex()));
            Editarproducto editarproducto = new Editarproducto();
            String p = e.get(1);
            String t = e.get(3);
            String o = e.get(2);
            editarproducto.mostrarVentanaSecundaria(e.get(0), p, o, t);
        });
    }

    @FXML
    public void consumot() {
        estado = 6;
        fecha1.setVisible(true);
        fecha2.setVisible(true);
        table.setOnKeyPressed(event -> {
        });
        if (datepickerboo()) {
            tabla("consumo interno", "WHERE `fecha` BETWEEN '" + datepicker() + "'");
        } else {
            tabla("consumo interno", "WHERE `fecha` BETWEEN '" + getfecha.agno() + "-" + getfecha.mes() + "-" + getfecha.dia() + "" + "' AND '" + getfecha.agno() + "-" + getfecha.mes() + "-" + getfecha.dia() + "'");
        }
    }

    @FXML
    public void proveedorest() {
        estado = 8;
        fecha1.setVisible(false);
        fecha2.setVisible(false);
        table.setOnMousePressed(event -> {
        });
        tabla("proveedores", "");
    }

    @FXML
    public void ventast() {
        estado = 9;
        fecha1.setVisible(true);
        fecha2.setVisible(true);
        table.setOnKeyPressed(event -> {
        });
        if (datepickerboo()) {
            tabla("ventas", "WHERE `fecha` BETWEEN '" + datepicker() + "'");
        } else {
            tabla("ventas", "WHERE `fecha` BETWEEN '" + getfecha.agno() + "-" + getfecha.mes() + "-" + getfecha.dia() + "" + "' AND '" + getfecha.agno() + "-" + getfecha.mes() + "-" + getfecha.dia() + "'");
        }
    }

    @FXML
    public void perdidast() {
        fecha1.setVisible(true);
        fecha2.setVisible(true);
        estado = 10;
        table.setOnKeyPressed(event -> {
        });
        if (datepickerboo()) {
            tabla("produccion", "WHERE `fecha` BETWEEN '" + datepicker() + "'");
        } else {
            tabla("perdidas", "WHERE `fecha` BETWEEN '" + getfecha.agno() + "-" + getfecha.mes() + "-" + getfecha.dia() + "" + "' AND '" + getfecha.agno() + "-" + getfecha.mes() + "-" + getfecha.dia() + "'");
        }
    }

    @FXML
    public void precios() {
        precios(0,"");
    }
    public void precios(int tipopro,String codigoprod) {
        metodos=new Metodos();
        try {
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Editarprecios.fxml"));
            AnchorPane ventanaDos = (AnchorPane) loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Editar precios");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
            ventana.setResizable(false);
            ventana.getIcons().add(new Image(getClass().getResource("../sample/product+.png").toExternalForm()));
            EditarprecioEvent controller = loader.getController();
            controller.setProducto(tipopro,codigoprod);
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.setOnHiding(event1 -> {
                viverest();
            });            ventana.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    public void eliminar_producto() {
        metodos=new Metodos();
        try {
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EliminarProduct.fxml"));
            AnchorPane ventanaDos = (AnchorPane) loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Editar precios");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
            ventana.setResizable(false);
            ventana.getIcons().add(new Image(getClass().getResource("../sample/product+.png").toExternalForm()));
            EliminaProductEvent controller = loader.getController();
            controller.setUsuario(usuario.getText());
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.setOnHiding(event1 -> {
                viverest();
            });            ventana.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    public void costos() {
        metodos=new Metodos();
        try {
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditarCostos.fxml"));
            AnchorPane ventanaDos = (AnchorPane) loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Editar precios");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
            ventana.setResizable(false);
            ventana.getIcons().add(new Image(getClass().getResource("../sample/product+.png").toExternalForm()));
            EditarCostosEvent controller = loader.getController();
           // controller.setProducto(tipopro,codigoprod);
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.setOnHiding(event1 -> {
                viverest();
            });            ventana.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    private void cerrar_sesion(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Main main = new Main();
        main.mostrarVentanaSecundaria();
    }
    private boolean tabaddpro=false;
    @FXML
    private void proveedor(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("proveedor.fxml"));
        AnchorPane ventanaDos = (AnchorPane) loader.load();
        Stage ventana = new Stage();
            ventana.setTitle("Agregar provedor");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
                ventana.setResizable(false);
            ventana.getIcons().add(new Image(getClass().getResource("../sample/product+.png").toExternalForm()));
            ProveedorEvent controller = loader.getController();
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.setOnHiding(event1 ->{
                setproveedores();
            });
            ventana.show();
    }
    @FXML
    private void eliproveedor(ActionEvent event) {
        try {
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EliminarProvee.fxml"));
            AnchorPane ventanaDos = loader.load();
          //  tabs.getTabs().add(new Tab("EliminarProvee.fxml",ventanaDos));
            Stage ventana = new Stage();
            ventana.setTitle("Agregar provedor");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
            ventana.setResizable(false);
            ventana.getIcons().add(new Image(getClass().getResource("../sample/product+.png").toExternalForm()));
            EliminaProveeEvent controller = loader.getController();
            controller.setUsuario(usuario.getText());
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.setOnHiding(event1 -> {
                setproveedores();
            });            ventana.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    private void consumo(ActionEvent event) {
        try {
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Consumo.fxml"));
            AnchorPane ventanaDos = loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Agregar consumo");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
            ventana.setResizable(false);
            ventana.getIcons().add(new Image(getClass().getResource("../sample/product+.png").toExternalForm()));
            ConsumoEvent controller = loader.getController();
            ventana.setOnHiding(event1 -> {
                consumot();
            });
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void producto(ActionEvent event) {
        try {
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("producto.fxml"));
            AnchorPane ventanaDos = (AnchorPane) loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Agregar Producto");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
            ventana.setResizable(false);
            ventana.getIcons().add(new Image(getClass().getResource("../sample/product+.png").toExternalForm()));
            ProductoEvent controller = loader.getController();
            ventana.setOnHiding(event1 -> {
                listapro();
                buscarvi();
                buscachr();
            });
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    public void setcancompr(){
        itemcom.setText("Articulos: "+listacom.size()+"");
    }
    @FXML
    private void cuentacobrar(ActionEvent event) {
        try {
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Cajero/CCobrar.fxml"));
            VBox ventanaDos = (VBox) loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Agregar Producto");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
            ventana.setResizable(false);
            ventana.getIcons().add(new Image(getClass().getResource("../sample/product+.png").toExternalForm()));
            CCobrarEvent controller = loader.getController();
            ventana.setOnHiding(event1 -> {
                cuentact();
            });
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.setMaximized(true);
            ventana.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void Perdida(ActionEvent event) {
        Perdida perdida = new Perdida();
        perdida.mostrarVentanaSecundaria();
    }
    public void setContra(String clave){
        contra=clave;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        principal.setClosable(false);
        ventana.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                if (((Stage) full.getScene().getWindow()).isFullScreen()) {
                    full.setText("Pantalla normal");

                } else {
                    full.setText("Pantalla completa");
                }
            }
        });
        Textoampo textoampo = new Textoampo();
        metodos=new Metodos();
        empresa.setText(metodos.empresa());
        full.setOnAction(event -> {
            if (((Stage) full.getScene().getWindow()).isFullScreen()) {
                ((Stage) full.getScene().getWindow()).setFullScreen(false);
                full.setText("Pantalla completa");
            } else {
                ((Stage) full.getScene().getWindow()).setFullScreen(true);
                full.setText("Pantalla normal");
            }
        });
        buscarvi();
        buscachr();
        buschar.setOnKeyReleased(event -> {
            buscachr();
        });

        pago.getItems().addAll("Sin abono","Transferencia","Tarjeta","Pago movil");
        pago.getSelectionModel().select(0);
        proveedor.getSelectionModel().select(0);
        fechac1.getEditor().setText(getfecha.dia() + "/" + getfecha.mes() + "/" + getfecha.agno());
        viverest();
        setproveedores();
        busvive.setOnKeyReleased(event -> {
            buscarvi();
        });
        setcancompr();
        textoampo.setdouble(preciouvive);
        textoampo.setint(cantivive);
        textoampo.setdouble(pesounichar);
        textoampo.setdouble(abono);
        textoampo.setdouble(precioukgchar);
        abono.setText("0.00");
        preciouvive.setText("0.00");
        cantivive.setText("0");
        pesounichar.setText("0.00");
        precioukgchar.setText("0.00");
    }
    private void setproveedores(){
        ObservableList<String> itempro = FXCollections.observableArrayList();
        itempro.addAll(metodos.proveedores());
        proveedor.setItems(itempro);
        proveedor.getSelectionModel().select(0);
    }
    public void setProgramaPrincipal(Admin ProgramaPrincipal) {
        this.ProgramaPrincipal = ProgramaPrincipal;
    }
    @FXML
    private void salir(ActionEvent event) {
        System.exit(0);
    }
@FXML
    public void buscachr() {
    metodos=new Metodos();
        ObservableList<String> items2=FXCollections.observableArrayList();
        items2.addAll(metodos.charcuteriacom(buschar.getText()));
        pro_char.setItems(items2);
        pro_char.getSelectionModel().select(0);
    };
    public void buscarvi(){
        metodos=new Metodos();
        ObservableList<String> items=FXCollections.observableArrayList();
        items.addAll(metodos.viverescom(busvive.getText()));
        co_vive.setItems(items);
        co_vive.getSelectionModel().select(0);
    }
}