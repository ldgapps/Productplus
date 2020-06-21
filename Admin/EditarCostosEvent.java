/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import extras.Metodos;
import extras.Textoampo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class EditarCostosEvent implements Initializable {
    @FXML
    private JFXComboBox producto, tipo;
    @FXML
    private JFXTextField nprecio,busqueda;
    @FXML
    private Label vprecio, error;
    Metodos metodos=new Metodos();

    private void setProducto(){
        ObservableList<String> item2=FXCollections.observableArrayList();
        item2.addAll(metodos.productos(tipo.getSelectionModel().getSelectedItem().toString(),busqueda.getText()));
        producto.setItems(item2);
        producto.getSelectionModel().select(0);
    }
    private void setprecio(){
        try {
            String pr = producto.getSelectionModel().getSelectedItem().toString();
            vprecio.setText(metodos.get_prop_producto(tipo.getSelectionModel().getSelectedItem().toString(), pr.substring(0, pr.indexOf(":")), "costo"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setProducto(int tipodeprod,String codigo){
        tipo.getSelectionModel().select(tipodeprod);
        busqueda.setText(codigo);
        setProducto();
        setprecio();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            // TODO
        Textoampo textoampo=new Textoampo();
        textoampo.setdouble(nprecio);
        ObservableList<String> items=FXCollections.observableArrayList();
        items.addAll("Viveres","Charcuteria");
        tipo.setItems(items);


        tipo.setOnMousePressed(event -> {
            setProducto();
        });
        tipo.getSelectionModel().select(0);
        setProducto();
        setprecio();
        producto.setOnHiding(event -> {
            setprecio();
        });
        error.setText("");
        busqueda.setOnKeyReleased(event -> {
            try{
            setProducto();
            setprecio();}catch (Exception e){
                e.printStackTrace();
            }
        });
}

    @FXML
    public void cambio(){
        String pr=producto.getSelectionModel().getSelectedItem().toString();
        try {
            metodos.editarcosto(tipo.getSelectionModel().getSelectedItem().toString(),pr.substring(0,pr.indexOf(":")),nprecio.getText());
            error.setText("Costo cambiado");



        } catch (SQLException e) {
            error.setText("Error");
            e.printStackTrace();
        }
        finally {
            vprecio.setText(metodos.get_prop_producto(tipo.getSelectionModel().getSelectedItem().toString(), pr.substring(0, pr.indexOf(":")), "costo"));
            Timer t=new Timer();
            TimerTask tt=new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> error.setText(""));
                    t.cancel();
                }
            };
            t.schedule(tt, 800 );
        }
    }
    @FXML
    private void salir(ActionEvent event) {
       (((Node) event.getSource())).getScene().getWindow().hide();
    }

}

