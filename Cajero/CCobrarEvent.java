/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cajero;

import Admin.listcompra;
import Conexion.Conexion;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import extras.Fecha;
import extras.Metodos;
import extras.Textoampo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sample.Main;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Gonzalez Duerto
 */
public class CCobrarEvent implements Initializable {
    private CCobrar ProgramaPrincipal;
    private Conexion co;
    private float precc;
    @FXML
    private Label label;
    @FXML
    private JFXTextField buscavive;
    @FXML
    private JFXTextField cantichar;
    @FXML
    private JFXDatePicker fecha;
    @FXML
    private Label tpreciol,error;
    @FXML
    private JFXTextField buscacharcu,cliente,ci,ref,abono,cantivive;
    @FXML
    private JFXComboBox charcuteria,pago,viveres;
    @FXML
    private Label resto,tpreciocc;
    @FXML
    private TableView<listcompra> ventatabla;
    private Metodos metodos;
    @FXML
    private TableColumn<listcompra, String> tipop,codigol,productol,cantidadl,precioul,preciotl;
    @FXML
    private TableColumn<listcompra,JFXButton> elimina;
    ObservableList<listcompra> lista=FXCollections.observableArrayList();
    @FXML
    public void elimina(KeyEvent event){
        //compratabla.getItems().get(compratabla.getSelectionModel().getSelectedIndex());
    }
    @FXML
    private void vender() {
        try {
            if(ventatabla.getItems().size()<1||cliente.getText().equals("")){

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
                String fecha2=fecha.getEditor().getText();
                String dia=fecha2.substring(0,2);
                String mes=fecha2.substring(3,5);
                String agno=fecha2.substring(6);
                System.out.println(cliente.getText()+" "+ci.getText());
                metodos.add_cuenta_cobrar(codigo_s,tipo_s,producto_s,cliente.getText(),precio_s,preciot_s,ci.getText(),abono.getText(),precc+"",pago.getSelectionModel().getSelectedItem().toString(),resto.getText(),cantidad_s,agno+"-"+mes+"-"+dia,ref.getText());
                for(int i=0;i<ventatabla.getItems().size();i++){
                    if(ventatabla.getItems().get(i).getTipo().equals("Charcuteria")){
                        metodos.del_charcuteria(ventatabla.getItems().get(i).getCodigo(),ventatabla.getItems().get(i).getPreciot());
                    }
                    else if(ventatabla.getItems().get(i).getTipo().equals("Viveres")){
                        metodos.del_viveres(ventatabla.getItems().get(i).getCodigo(),ventatabla.getItems().get(i).getCantidad(),ventatabla.getItems().get(i).getPreciou());
                    }
                }
                precc=0;
                eliminart();abonar();}
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
    public void abonar(){
        try{
            float ab=Float.parseFloat(abono.getText());
            float restos=precc-ab;
            resto.setText(restos+"");
        }catch(Exception e){}
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
                System.out.println(i);
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
        }
        addtabla();
        precc+=precit;
        tpreciocc.setText(""+precc);
    }
    private void setprecio_eliminar(){
        precc=0;
        for(int i=0;i<ventatabla.getItems().size();i++){
            precc+=Float.parseFloat(ventatabla.getItems().get(i).preciotProperty().getValue());
        }
        tpreciocc.setText(precc + "");
        abonar();
    }
    private void addtabla(){
        tipop.setCellValueFactory(cellData->cellData.getValue().tipoProperty());
        codigol.setCellValueFactory(cellData->cellData.getValue().codigoProperty());
        productol.setCellValueFactory(cellData->cellData.getValue().productoProperty());
        cantidadl.setCellValueFactory(cellData->cellData.getValue().cantidadProperty());
        preciotl.setCellValueFactory(cellData->cellData.getValue().preciotProperty());
        precioul.setCellValueFactory(cellData->cellData.getValue().preciouProperty());
        elimina.setCellValueFactory(cellData -> cellData.getValue().eliminaProperty());
        ventatabla.setItems(lista);
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
        }
        precc+=precit;
        tpreciocc.setText(""+precc);
        addtabla();
    }


    @FXML
    public void eliminart(){
        tpreciocc.setText("0,00");
        precc=0;
        ventatabla.getItems().clear();
        abonar();
    }
    @FXML
    public void buscarvi(){
        co=new Conexion();
        metodos=new Metodos();
        ObservableList<String> items=FXCollections.observableArrayList();
        items.addAll(metodos.viveres(buscavive.getText()));
        viveres.setItems(items);
        viveres.getSelectionModel().select(0);
        co.desconectar();
    }
    @FXML
    public void buscachr() {
        co=new Conexion();
        metodos=new Metodos();
        ObservableList<String> items2=FXCollections.observableArrayList();
        items2.addAll(metodos.charcuteria(buscacharcu.getText()));
        charcuteria.setItems(items2);
        charcuteria.getSelectionModel().select(0);
        co.desconectar();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Textoampo textoampo=new Textoampo();
        textoampo.setint(cantivive);
        textoampo.setdouble(cantichar);
        textoampo.setdouble(abono);
        abono.setText("0.00");
        metodos=new Metodos();
        ObservableList<String> items=FXCollections.observableArrayList();
        items.addAll(metodos.viveres(buscavive.getText()));
        viveres.setItems(items);
        viveres.getSelectionModel().select(0);
        ObservableList<String> items2=FXCollections.observableArrayList();
        items2.addAll(metodos.charcuteria(buscacharcu.getText()));
        charcuteria.setItems(items2);
        charcuteria.getSelectionModel().select(0);
        ObservableList<String> items4=FXCollections.observableArrayList();
        ObservableList<String> tpagos=FXCollections.observableArrayList();
        tpagos.addAll("Sin abono","Efectivo","Transferencia","Pago movil","Tarjeta");
        pago.setItems(tpagos);
        pago.getSelectionModel().select(0);
        ventatabla.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try{
                    ventatabla.getSelectionModel().select(ventatabla.getSelectionModel().getFocusedIndex());
                    float elp=Float.parseFloat(ventatabla.getSelectionModel().getSelectedItem().getPreciot());
                    ventatabla.getItems().remove(ventatabla.getSelectionModel().getSelectedIndex());
                    precc-=elp;
                    tpreciocc.setText(precc+"");

                }catch(Exception e){
                    tpreciocc.setText("0,00");
                }
                abonar();
            }
        });
        Fecha getfecha=new Fecha();
        fecha.getEditor().setText(getfecha.dia() + "/" + getfecha.mes() + "/" + getfecha.agno());
        abono.setOnKeyReleased(event -> abonar());
        buscacharcu.setOnKeyReleased(event -> {
            buscachr();
        });
        buscavive.setOnKeyReleased(event -> {
            buscarvi();
        });
    }
    @FXML
    private void cerrar_sesion(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Main main=new Main();
        main.mostrarVentanaSecundaria();

    }
    public void setProgramaPrincipal(CCobrar ProgramaPrincipal) {
        this.ProgramaPrincipal = ProgramaPrincipal;
    }
    @FXML
    private void salir(ActionEvent event) {
        System.exit(0);
    }
}

