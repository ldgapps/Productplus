/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cajero;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Simple Preloader Using the ProgressBar Control
 *
 * @author Gonzalez Duerto
 */
public class Caja extends Application {
    private Stage stage;
    private AnchorPane rootPane;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("Cajero.fxml"));

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
    public void fullscreeen(Stage stage){
        stage.setFullScreen(true);
    }
    public void mostrar(String usuario){
        try {
            FXMLLoader loader=new FXMLLoader(Caja.class.getResource("Cajero.fxml"));
            VBox ventanaDos = (VBox) loader.load();
            Scene scene = new Scene(ventanaDos);
            
            stage.setTitle("Bakery Administrador");
            stage.setScene(scene);
            CajaEvent controller = loader.getController();
            controller.setProgramaPrincipal(this);
            controller.setUsuario(usuario);
            stage.getIcons().add(
                    new Image(
                            Caja.class.getResourceAsStream( "../sample/product+.png" )));

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Caja.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void mostrarVentanaSecundaria(String usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Cajero.fxml"));
            VBox ventanaDos = (VBox) loader.load();
            Stage ventana = new Stage();
            ventana.getIcons().add(new Image(getClass().getResource("logo.png").toExternalForm()));

            ventana.setTitle("Cajero Product+");
            ventana.initOwner(stage);

            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
            ventana.setMaximized(true);
            CajaEvent controller = loader.getController();
            controller.setUsuario(usuario);
            try {
                //                AdminEvent controller = loader.getController();

                //start(ventana);
            } catch (Exception ex) {
                Logger.getLogger(Caja.class.getName()).log(Level.SEVERE, null, ex);
            }
            ventana.show();
        } catch (IOException ex) {
            Logger.getLogger(Caja.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
