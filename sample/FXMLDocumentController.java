/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import Admin.Admin;
import Cajero.Caja;
import Conexion.Conexion;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;


import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


import extras.Metodos;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


/**
 *
 * @author Gonzalez Duerto
 */
public class FXMLDocumentController implements Initializable {
    private Main ProgramaPrincipal;
    private Admin admin=new Admin();
    private Caja cajero=new Caja();
    Conexion conexion;
    @FXML
    private Label label;
    @FXML
    private JFXTextField usuario;

        @FXML
    private JFXPasswordField contra;
    @FXML
    private void iniciar(ActionEvent event) {
        conexion=new Conexion();
        String usu=usuario.getText();
        String contras=contra.getText();
        String conusu=conexion.iniciar(usu, contras);
       if(conusu.equals("admin")){
           admin.mostrarVentanaSecundaria(usu);
           cerrar(event);
      }
       else if(conusu.equals("cajero")){
           cajero.mostrarVentanaSecundaria(usu);
           cerrar(event);
       }
       else{
           label.setText("Contraseña Incorrecta");
           Timer t=new Timer();
           TimerTask tt=new TimerTask() {
               @Override
               public void run() {
                   Platform.runLater(() -> label.setText(""));
               }
           };
           t.schedule(tt, 2000 );
       }
       conexion.desconectar();
    }
    @FXML
    private void iniciart(KeyEvent event) {
        conexion=new Conexion();
        String usu=usuario.getText();
        String contras=contra.getText();
        String conusu=conexion.iniciar(usu, contras);

        if(conusu.equals("admin")){
            admin.mostrarVentanaSecundaria(usu);
            cerrar(event);
        }
        else if(conusu.equals("cajero")){
            cajero.mostrarVentanaSecundaria(usu);
            cerrar(event);
        }
        else{
            label.setText("Contraseña Incorrecta");
            Timer t=new Timer();
            TimerTask tt=new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> label.setText(""));
                }
            };
            t.schedule(tt, 2000 );
        }
        conexion.desconectar();
    }
    public void setProgramaPrincipal(Main ProgramaPrincipal) {
        this.ProgramaPrincipal = ProgramaPrincipal;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        contra.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {

                iniciart(event);
            }
        });
        usuario.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {

                iniciart(event);
            }
        });
    }
    public void cerrar(KeyEvent event){
        (((Node) event.getSource())).getScene().getWindow().hide();
    }
    public void cerrar(ActionEvent event){
        (((Node) event.getSource())).getScene().getWindow().hide();
    }

}
