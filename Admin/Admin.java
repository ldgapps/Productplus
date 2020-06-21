/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import static javafx.application.Application.launch;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Main;
import sample.MainPreloader;

/**
 * Simple Preloader Using the ProgressBar Control
 *
 * @author Gonzalez Duerto
 */
public class Admin extends Application {
        private Stage stage;
    private AnchorPane rootPane;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        LauncherImpl.launchApplication(Admin.class, MainPreloader.class ,args);
    }
    public void mostrar(String usuario){

        try {

            FXMLLoader loader=new FXMLLoader(Admin.class.getResource("admin.fxml"));
            VBox ventanaDos = loader.load();
            Scene scene = new Scene(ventanaDos);
            stage.setTitle("Product+");
            stage.setScene(scene);
            AdminEvent controller = loader.getController();
            controller.setProgramaPrincipal(this);
            controller.setUsuario(usuario);
            stage.getIcons().add(
                    new Image(
                            Admin.class.getResourceAsStream( "../sample/product+.png" )));

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
            public void mostrarVentanaSecundaria(String usuario) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("admin.fxml"));
                VBox ventanaDos = loader.load();
                Stage ventana = new Stage();
                ventana.getIcons().add(new Image(getClass().getResource("logo.png").toExternalForm()));

                ventana.setTitle("Product+ Administrador");
                ventana.initOwner(stage);

                Scene scene = new Scene(ventanaDos);               
                ventana.setScene(scene);
                ventana.setMaximized(true);
                AdminEvent controller = loader.getController();
                      controller.setUsuario(usuario);
                try {
                    //                AdminEvent controller = loader.getController();

                    //start(ventana);
                } catch (Exception ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
                ventana.show();
            } catch (IOException ex) {
                Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
