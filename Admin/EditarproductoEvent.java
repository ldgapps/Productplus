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
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditarproductoEvent implements Initializable {
    private Editarproducto ProgramaPrincipal;
    @FXML
    private Label codigo,error,tipo;
    @FXML
    private JFXTextField marcan, producton;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        error.setText("");
}

    public void setProgramaPrincipal(Editarproducto ProgramaPrincipal) {
        this.ProgramaPrincipal = ProgramaPrincipal;
    }
    public void setpro(String codigoset,String productoset,String marcaset,String tiposet){
        codigo.setText(codigoset);
        producton.setText(productoset);
        marcan.setText(marcaset);
        tipo.setText(tiposet);
    }
    @FXML
    public void edita(){
        Metodos metodos=new Metodos();
        try {
            metodos.editaproducto(codigo.getText(),producton.getText(),marcan.getText(),tipo.getText());
            error.setText("Produto editado");
            Timer t=new Timer();
            TimerTask tt=new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> error.setText(""));
                }
            };
            t.schedule(tt, 2000 );
        } catch (SQLException e) {
            error.setText("Error al editar");
            Timer t=new Timer();
            TimerTask tt=new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> error.setText(""));
                }
            };
            t.schedule(tt, 2000 );
            Logger.getLogger(ConsumoEvent.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }
    @FXML
    private void salir(ActionEvent event) {
       (((Node) event.getSource())).getScene().getWindow().hide();
    }
}

