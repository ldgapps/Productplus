/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import com.jfoenix.controls.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import extras.Metodos;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 *
 * @author Gonzalez Duerto
 */
public class AgregausuEvent implements Initializable {
    Admin bakery=new Admin();
    @FXML
    private Label error;
    @FXML
    private JFXPasswordField contra;
    @FXML
    private JFXTextField usuario;
    @FXML
    private JFXComboBox tipo;
    private Metodos metodos;
    @FXML
    private void agregar(ActionEvent event) {
        try {
            if(usuario.getText().equals("")||contra.getText().equals("")){
                error.setText("Complete todos los campos");
                Timer t=new Timer();
                TimerTask tt=new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> error.setText(""));
                    }
                };
                t.schedule(tt, 2000 );
            }else{
                metodos=new Metodos();
                metodos.add_usuario(usuario.getText(), contra.getText(), tipo.getSelectionModel().selectedItemProperty().getValue().toString());
                error.setText("Usuario agregado");
                java.util.Timer t=new Timer();
                TimerTask tt=new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> error.setText(""));
                    }
                };
                t.schedule(tt, 2000 );
            }
        } catch (SQLException ex) {
            error.setText("Error al agregar");
            Timer t=new Timer();
            TimerTask tt=new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> error.setText(""));
                }
            };
            t.schedule(tt, 2000 );
            Logger.getLogger(AgregausuEvent.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<String> items=FXCollections.observableArrayList();
        items.addAll("admin","cajero");
        tipo.setItems(items);
    }



    @FXML
    private void salir(ActionEvent event) {
       (((Node) event.getSource())).getScene().getWindow().hide();
    }
}

