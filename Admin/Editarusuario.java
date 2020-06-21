/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
public class Editarusuario extends Application {
        private Stage stage;
    private AnchorPane rootPane;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("EliminarUsuario.fxml"));
        
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
        stage.getIcons().add(new Image(getClass().getResource("../sample/product+.png").toExternalForm()));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }   
//    public void mostrar(){
//        try {
//            FXMLLoader loader=new FXMLLoader(Bakery.class.getResource("admin.fxml"));
//            VBox ventanaDos = (VBox) loader.load();
//            Scene scene = new Scene(ventanaDos);
//            stage.setTitle("Ventana Principal");
//            stage.setScene(scene);
//            AdminEvent controller = loader.getController();
//            controller.setProgramaPrincipal(this);
//            stage.show();
//        } catch (IOException ex) {
//            Logger.getLogger(Bakery.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
            public void mostrarVentanaSecundaria(String usuario) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Editarusuario.fxml"));
                AnchorPane ventanaDos = (AnchorPane) loader.load();
                Stage ventana = new Stage();
                ventana.setTitle("Editar usuario");
                ventana.initOwner(stage);
                Scene scene = new Scene(ventanaDos);               
                ventana.setScene(scene);
                ventana.setResizable(false);
                ventana.getIcons().add(new Image(getClass().getResource("../sample/product+.png").toExternalForm()));
                EditarusuEvent controller = loader.getController();
                controller.setUsuario(usuario);
               // controller.setProgramaPrincipal(this);
                try {
                    //                AdminEvent controller = loader.getController();
                    //start(ventana);
                } catch (Exception ex) {
                    Logger.getLogger(Editarusuario.class.getName()).log(Level.SEVERE, null, ex);
                }
                ventana.show();
            } catch (IOException ex) {
                Logger.getLogger(Editarusuario.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
