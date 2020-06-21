/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import com.jfoenix.controls.JFXTextField;
import extras.Metodos;
import javafx.application.Platform;
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

/**
 *
 * @author Gonzalez Duerto
 */
public class EmpresaEvent implements Initializable {
    Admin bakery=new Admin();
    AdminEvent adminEvent;
    private Metodos metodos;
    @FXML
    private Label error;
    @FXML
    private JFXTextField nombre;
    @FXML
    public void cambiar(){

        metodos=new Metodos();
        try {
            metodos.setempresa(nombre.getText());

            error.setText("Nombre cambiado");
            Timer t=new Timer();
            TimerTask tt=new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> error.setText(""));
                }
            };
            t.schedule(tt, 2000 );
            adminEvent.recibe(nombre.getText());
            nombre.setText("");
        } catch (SQLException e) {
            error.setText("Error");
            Timer t=new Timer();
            TimerTask tt=new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> error.setText(""));
                }
            };
            t.schedule(tt, 2000 );
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        error.setText("");
    }
    @FXML
    private void salir(ActionEvent event) {
       (((Node) event.getSource())).getScene().getWindow().hide();
    }
    @FXML
    public void parametros(AdminEvent stage,Label texto,String string){
        adminEvent=stage;
        texto.setText(string);
    }
}

