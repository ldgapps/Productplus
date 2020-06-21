/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import Conexion.Conexion;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import extras.Metodos;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Gonzalez Duerto
 */
public class ProveedorEvent implements Initializable {
    Admin bakery=new Admin();
    @FXML
    private AnchorPane panel;
    @FXML
    private JFXTextField nproveedor,rifproveedor,tlfproveedor,ciuproveedor,emproveedor,bancoproveedor,ncuentaproveedor,producprovee;
    @FXML
    private JFXComboBox tipoc;
    @FXML
    private Label error;
    private Timer t=new Timer();
    @FXML
    private void agregar(ActionEvent event) {
        Metodos metodos=new Metodos();
        try {
            if(nproveedor.getText().equals("")){
                error.setText("Agregue un nombre");
                Timer t = new Timer();
                TimerTask tt = new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> error.setText(""));
                    }
                };
                t.schedule(tt, 2000);            }
            else {
                String nombre = nproveedor.getText().trim();
                String rif = rifproveedor.getText().trim();
                String tlf = tlfproveedor.getText().trim();
                String ciu = ciuproveedor.getText().trim();
                String banco = bancoproveedor.getText().trim();
                String tipocu = tipoc.getSelectionModel().selectedItemProperty().getValue().toString();
                String ncuenta = ncuentaproveedor.getText().trim();
                String produc = producprovee.getText().trim();
                String email = emproveedor.getText().trim();

                metodos.add_proveedor(nombre, rif, tlf
                        , ciu, email, banco, tipocu, ncuenta, produc);

                nproveedor.setText("");
                rifproveedor.setText("");
                tlfproveedor.setText("");
                ciuproveedor.setText("");
                bancoproveedor.setText("");
                ncuentaproveedor.setText("");
                producprovee.setText("");
                emproveedor.setText("");
                error.setText("Agregado");
/*                Timer t = new Timer();
                TimerTask tt = new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> error.setText(""));
                    }
                };
                t.schedule(tt, 2000);*/
            }
        } catch (SQLException e) {
            error.setText("Error al agregar");
            e.printStackTrace();
        }
        finally {

            TimerTask tt=new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> error.setText(""));
                }
            };
            t.schedule(tt, 2000 );
        }
    }
    public void cancelartimer(){
        t.cancel();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<String> items=FXCollections.observableArrayList();
        items.addAll("Sin cuenta","Ahorro","Corriente");
        tipoc.setItems(items);
        tipoc.getSelectionModel().select(0);
        error.setText("");


    }
    private String usuario;
    public void setUsuario(String usuario){
        this.usuario=usuario;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EliminarProvee.fxml"));
        try {
            AnchorPane ventanaDos = loader.load();
            panel.getChildren().add(ventanaDos);
            EliminaProveeEvent controller = loader.getController();
            controller.setUsuario(usuario);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void salir(ActionEvent event) {
        cancelartimer();
        (((Node) event.getSource())).getScene().getWindow().hide();
    }
}

