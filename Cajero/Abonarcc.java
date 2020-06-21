/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cajero;

import Admin.EditarprecioEvent;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple Preloader Using the ProgressBar Control
 *
 * @author Gonzalez Duerto
 */
public class Abonarcc extends Application {
    public JFXComboBox producto;
    public Spinner nprecio;
    public JFXComboBox tipo;
    private Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("Abonarcc.fxml"));
        
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }   
    public void mostrar(String cuenta){
        try {
            FXMLLoader loader=new FXMLLoader(Abonarcc.class.getResource("Abonarcc.fxml"));
            AnchorPane ventanaDos = (AnchorPane) loader.load();
            Scene scene = new Scene(ventanaDos);
            stage.setScene(scene);
            AbonarccEvent controller = loader.getController();
            controller.setProgramaPrincipal(this);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Abonarcc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
            public void mostrarVentanaSecundaria(String cuenta) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Abonarcc.fxml"));
                AnchorPane ventanaDos = (AnchorPane) loader.load();
                Stage ventana = new Stage();
                ventana.setTitle("Abonar");
                ventana.initOwner(stage);
                ventana.setResizable(false);
                Scene scene = new Scene(ventanaDos);               
                ventana.setScene(scene);
                AbonarccEvent controller = loader.getController();
                controller.setProgramaPrincipal(this);
                controller.setpro(cuenta);

                try {
                    //                AdminEvent controller = loader.getController();
                   //start(ventana);
                } catch (Exception ex) {
                    Logger.getLogger(Abonarcc.class.getName()).log(Level.SEVERE, null, ex);
                }
                ventana.show();
            } catch (IOException ex) {
                Logger.getLogger(Abonarcc.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
