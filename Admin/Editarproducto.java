/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import com.jfoenix.controls.JFXComboBox;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
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
public class Editarproducto extends Application {
    public JFXComboBox producto;
    public Spinner nprecio;
    public JFXComboBox tipo;
    private Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("Editarproducto.fxml"));
        
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
    public void mostrar(){
        try {
            FXMLLoader loader=new FXMLLoader(Editarproducto.class.getResource("Editarproducto.fxml"));
            AnchorPane ventanaDos = (AnchorPane) loader.load();
            Scene scene = new Scene(ventanaDos);
            stage.setScene(scene);
            EditarprecioEvent controller = loader.getController();
            //controller.setProgramaPrincipal(this);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Editarproducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
            public void mostrarVentanaSecundaria(String codigo,String producto,String marca,String tipo) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Editarproducto.fxml"));
                AnchorPane ventanaDos = (AnchorPane) loader.load();
                Stage ventana = new Stage();
                ventana.setTitle("Editar producto");
                ventana.initOwner(stage);
                Scene scene = new Scene(ventanaDos);
                ventana.setScene(scene);
                ventana.setResizable(false);
                ventana.getIcons().add(new Image(getClass().getResource("../sample/product+.png").toExternalForm()));
                EditarproductoEvent controller = loader.getController();
                controller.setProgramaPrincipal(this);
                controller.setpro(codigo,producto,marca,tipo);
                try {
                    //                AdminEvent controller = loader.getController();
                    //start(ventana);
                } catch (Exception ex) {
                    Logger.getLogger(Editarproducto.class.getName()).log(Level.SEVERE, null, ex);
                }
                ventana.show();
            } catch (IOException ex) {
                Logger.getLogger(Editarproducto.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
