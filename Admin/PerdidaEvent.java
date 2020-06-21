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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gonzalez Duerto
 */
public class PerdidaEvent implements Initializable {
    private Perdida ProgramaPrincipal;
    @FXML
    private Label error;
        @FXML
    private JFXComboBox producto,tipo;
    @FXML
    private JFXTextField causa,busqueda,canti;

    @FXML
    private void agregar(ActionEvent event) {
        Metodos metodos=new Metodos();
        try {
            String pro=producto.getSelectionModel().getSelectedItem().toString();
            metodos.add_perdida(pro.substring(0,pro.indexOf(": ")),pro.substring(pro.indexOf(": ")+2),canti.getText(),metodos.get_prop_producto(tipo.getSelectionModel().getSelectedItem().toString(),pro.substring(0,pro.indexOf(": ")),"costo"),causa.getText());
            metodos.perdida(tipo.getSelectionModel().getSelectedItem().toString(),pro.substring(0,pro.indexOf(": ")),canti.getText());
            error.setText("Perdida agregada");
        } catch (SQLException ex) {
            error.setText("Error al agregar");

            Logger.getLogger(PerdidaEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            Timer t=new Timer();
            TimerTask tt=new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> error.setText(""));
                }
            };
            t.schedule(tt, 2000 );
        }
    }
    @FXML
    private void setpro(){
        Metodos metodos=new Metodos();
        ObservableList<String> item=FXCollections.observableArrayList();
        item.addAll(metodos.productos(tipo.getSelectionModel().getSelectedItem().toString(),busqueda.getText()));
        producto.setItems(item);
        producto.getSelectionModel().select(0);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Metodos metodos=new Metodos();
        ObservableList<String> items=FXCollections.observableArrayList();
        items.addAll("Viveres","Charcuteria");
        tipo.setItems(items);
        tipo.getSelectionModel().select(0);
        Textoampo textoampo=new Textoampo();
        textoampo.setdouble(canti);
        ObservableList<String> item=FXCollections.observableArrayList();
        item.addAll(metodos.productos(tipo.getEditor().getText(),""));
        producto.setItems(item);
        error.setText("");
        setpro();
        busqueda.setOnKeyReleased(event -> {
            try{
                setpro();
                }catch (Exception e){
                e.printStackTrace();
            }
        });
        canti.setText("0");
    }    
    public void setProgramaPrincipal(Perdida ProgramaPrincipal) {
        this.ProgramaPrincipal = ProgramaPrincipal;
    }
    @FXML
    private void salir(ActionEvent event) {
       (((Node) event.getSource())).getScene().getWindow().hide();
    }
}

