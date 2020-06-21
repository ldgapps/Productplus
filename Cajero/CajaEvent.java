/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cajero;

import Admin.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import Conexion.Conexion;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import extras.Metodos;
import extras.Textoampo;
import javafx.application.Platform;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Acerca;
import sample.Main;

/**
 *
 * @author Gonzalez Duerto
 */
public class CajaEvent implements Initializable {
    private Caja ProgramaPrincipal;
    private float precc;
    private String usuarion,id;

    @FXML
    private JFXTextField cantiga, cantichar,buscalocal,buscacharcu,cliente,ci, buscagalle,ref,buscavive,cantivive;
    @FXML
    private Spinner cantilo;
    @FXML
    private Label tprecio, error,usuario,empresa;
    @FXML
    private JFXComboBox local, charcuteria,pago, galletas,viveres;
    @FXML
    private TableView<listcompra> ventatabla;

    @FXML
    private TableColumn<listcompra, String> tipop, codigol, productol, cantidadl, precioul, preciotl;
    @FXML
    private TableColumn<listcompra,JFXButton> elimina;
    ObservableList<listcompra> lista=FXCollections.observableArrayList();
    private Metodos metodos;
    @FXML
    public void elimina(KeyEvent event){
         ventatabla.getItems().get(ventatabla.getSelectionModel().getSelectedIndex());
    }
    @FXML
    public void acerca() {
        Acerca acerca = new Acerca();
        acerca.mostrarVentanaSecundaria();
    }
    @FXML
    private void vender() {
        Metodos metodos=new Metodos();
        try {
            if(ventatabla.getItems().size()<1||cliente.getText().equals("")){
                error.setText("Campos vacios");
            }else{
            String tipo_s="";
            String precio_s="";
            String preciot_s="";
            String codigo_s="";
            String producto_s="";
            String cantidad_s="";
            for(int i=0;i<ventatabla.getItems().size();i++){

                codigo_s+=ventatabla.getItems().get(i).getCodigo()+'\n';
                producto_s+=ventatabla.getItems().get(i).getProducto()+'\n';
                cantidad_s+=ventatabla.getItems().get(i).getCantidad()+'\n';
                precio_s+=ventatabla.getItems().get(i).getPreciou()+'\n';
                preciot_s+=ventatabla.getItems().get(i).getPreciot()+'\n';
                tipo_s+=ventatabla.getItems().get(i).getTipo()+'\n';
            }
            metodos.add_venta(tipo_s,codigo_s,producto_s,cantidad_s,cliente.getText(),ci.getText(),precio_s,preciot_s,precc+"",pago.getSelectionModel().getSelectedItem().toString(),ref.getText());
            for(int i=0;i<ventatabla.getItems().size();i++){
                if(ventatabla.getItems().get(i).getTipo().equals("Charcuteria")){

                    metodos.del_charcuteria(ventatabla.getItems().get(i).getCodigo(),ventatabla.getItems().get(i).getPreciot());
                }
                else if(ventatabla.getItems().get(i).getTipo().equals("Viveres")){
                    metodos.del_viveres(ventatabla.getItems().get(i).getCodigo(),ventatabla.getItems().get(i).getCantidad(),ventatabla.getItems().get(i).getPreciou());
                }
                error.setText("Venta realizada");
            }
            precc=0;
            eliminart();}
            error.setText("Venta realizada");
            Timer t=new Timer();
            TimerTask tt=new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> error.setText(""));
                }
            };
            t.schedule(tt, 4000 );
        } catch (SQLException e) {
            error.setText("Error al realizar venta");
            Timer t=new Timer();
            TimerTask tt=new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> error.setText(""));
                }
            };
            t.schedule(tt, 4000 );
            e.printStackTrace();
        }
    }
    @FXML
    public void agregaviveres(){
        JFXButton eli=new JFXButton("Borrar");
        eli.setOnMouseReleased(event -> {
            setprecio_eliminar();
        });
        String pro=viveres.getSelectionModel().getSelectedItem().toString();
        float can=Float.parseFloat(cantivive.getText());
        float preci=Float.parseFloat(pro.substring(pro.indexOf("$:")+2));
        float precit=can*preci;
        boolean encontrado=false;
        int nencontrado=0;
        for(int i=0;i<ventatabla.getItems().size();i++){
            if(ventatabla.getItems().get(i).codigoProperty().getValue().equals(pro.substring(0, pro.indexOf(":")))){
                encontrado=true;
                nencontrado=i;
            }
        }
        if(encontrado){
            int cant=Integer.parseInt(cantivive.getText());
            float anterior=Float.parseFloat(ventatabla.getItems().get(nencontrado).cantidadProperty().getValue());
            float cantt=precit;
            float anteriort=Float.parseFloat(ventatabla.getItems().get(nencontrado).preciotProperty().getValue());
            ventatabla.getItems().get(nencontrado).cantidadProperty().setValue(cant+anterior+"");
            ventatabla.getItems().get(nencontrado).preciotProperty().setValue(cantt+anteriort+"");
        }else {
            lista.add(new listcompra("Viveres", pro.substring(0, pro.indexOf(":")), pro.substring(pro.indexOf(": ") + 2, pro.indexOf(" dispo:")), can + "", preci + "", precit + "", eli, ventatabla));
        }addlista();
        precc+=precit;
        tprecio.setText(""+precc);
        cantivive.setText("0");
    }
    @FXML
    public void cobrar(){
        CCobrar c=new CCobrar();
        c.mostrarVentanaSecundaria();
    }
    @FXML
    public void cuentascobrar(){
        Cuentascc c=new Cuentascc();
        c.mostrarVentanaSecundaria();
    }
    @FXML
    public void agregacharcuteria(){
        JFXButton eli=new JFXButton("Borrar");
        eli.setOnMouseReleased(event -> {
            setprecio_eliminar();
        });
        String pro=charcuteria.getSelectionModel().getSelectedItem().toString();
        float can=1;
        float preci=Float.parseFloat(pro.substring(pro.indexOf("$:")+2));
        float precit=Float.parseFloat(cantichar.getText());
        boolean encontrado=false;
        int nencontrado=0;
        for(int i=0;i<ventatabla.getItems().size();i++){
            if(ventatabla.getItems().get(i).codigoProperty().getValue().equals(pro.substring(0, pro.indexOf(":")))){
                encontrado=true;
                nencontrado=i;
            }
        }
        if(encontrado){
            float cant=Float.parseFloat(cantichar.getText());
            float anterior=Float.parseFloat(ventatabla.getItems().get(nencontrado).cantidadProperty().getValue());
            float cantt=precit;
            float anteriort=Float.parseFloat(ventatabla.getItems().get(nencontrado).preciotProperty().getValue());
         //   ventatabla.getItems().get(nencontrado).cantidadProperty().setValue(cant+anterior+"");
            ventatabla.getItems().get(nencontrado).preciotProperty().setValue(cantt+anteriort+"");
        }else {
            lista.add(new listcompra("Charcuteria", pro.substring(0, pro.indexOf(":")), pro.substring(pro.indexOf(": ") + 2, pro.indexOf(" dispo:")), can + "", preci + "", precit + "", eli, ventatabla));
        }addlista();
        precc+=precit;
        tprecio.setText(""+precc);
        cantichar.setText("0.00");
    }
    private void addlista(){
        tipop.setCellValueFactory(cellData->cellData.getValue().tipoProperty());
        codigol.setCellValueFactory(cellData->cellData.getValue().codigoProperty());
        productol.setCellValueFactory(cellData->cellData.getValue().productoProperty());
        cantidadl.setCellValueFactory(cellData->cellData.getValue().cantidadProperty());
        preciotl.setCellValueFactory(cellData->cellData.getValue().preciotProperty());
        precioul.setCellValueFactory(cellData->cellData.getValue().preciouProperty());
        elimina.setCellValueFactory(cellData -> cellData.getValue().eliminaProperty());
        ventatabla.setItems(lista);
    }
    private void setprecio_eliminar(){
        precc=0;
        for(int i=0;i<ventatabla.getItems().size();i++){
            precc+=Float.parseFloat(ventatabla.getItems().get(i).preciotProperty().getValue());
        }
        tprecio.setText(precc + "");
        //abonar();
    }
    @FXML
    public void eliminart(){
        tprecio.setText("0,00");
        precc=0;
        ventatabla.getItems().clear();
    }
    @FXML
    public void buscarvi(){
        Metodos metodos=new Metodos();
        ObservableList<String> items=FXCollections.observableArrayList();
        items.addAll(metodos.viveres(buscavive.getText()));
        viveres.setItems(items);
        viveres.getSelectionModel().select(0);
    }
    @FXML
    public void buscachr() {
        Metodos metodos=new Metodos();
        ObservableList<String> items2=FXCollections.observableArrayList();
        items2.addAll(metodos.charcuteria(buscacharcu.getText()));
        charcuteria.setItems(items2);
        charcuteria.getSelectionModel().select(0);
    }
    private boolean fullscreen=false;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Metodos metodos=new Metodos();
        ObservableList<String> items=FXCollections.observableArrayList();
        items.addAll(metodos.viveres(buscavive.getText()));
        viveres.setItems(items);
        viveres.getSelectionModel().select(0);
        ObservableList<String> items2=FXCollections.observableArrayList();
        items2.addAll(metodos.charcuteria(buscacharcu.getText()));
        charcuteria.setItems(items2);
        charcuteria.getSelectionModel().select(0);
        Textoampo textoampo=new Textoampo();
        textoampo.setdouble(cantichar);
        ObservableList<String> tpagos=FXCollections.observableArrayList();
        tpagos.addAll("Efectivo","Transferencia","Pago movil","Tarjeta");
        empresa.setText(metodos.empresa());
        pago.setItems(tpagos);
        ventatabla.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try{
                    ventatabla.getSelectionModel().select(ventatabla.getSelectionModel().getFocusedIndex());
                    float elp=Float.parseFloat(ventatabla.getSelectionModel().getSelectedItem().getPreciot());
                    ventatabla.getItems().remove(ventatabla.getSelectionModel().getSelectedIndex());
                    precc-=elp;
                    tprecio.setText(precc+"");
                }catch(Exception e){
                    tprecio.setText("0,00");
                }}
        });
        full.setOnAction(event -> {
            if(fullscreen){
                ((Stage)full.getScene().getWindow()).setFullScreen(false);
                full.setText("Pantalla completa");

                fullscreen=false;
            }else{
                ((Stage)full.getScene().getWindow()).setFullScreen(true);
                full.setText("Pantalla normal");
                fullscreen=true;
            }
        });

    }
    @FXML
    private JFXButton full;
    @FXML
    private void cerrar_sesion(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Main main=new Main();
        main.mostrarVentanaSecundaria();
    }
    public void cambiacontra(){
        Editarcontra agregar=new Editarcontra();
        agregar.mostrarVentanaSecundaria(usuarion);
    }
    public void cambiausuario(){
        metodos=new Metodos();
        try {
            Stage stage=new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Admin/Editarusuario.fxml"));
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
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.show();
            ventana.setOnHiding(event -> {
                setusuario();
            });
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
    public void setProgramaPrincipal(Caja ProgramaPrincipal) {
        this.ProgramaPrincipal = ProgramaPrincipal;
    }
    @FXML
    private void salir(ActionEvent event) {
        System.exit(0);
    }
    @FXML
    private void expandir(ActionEvent event) {
    }
}

