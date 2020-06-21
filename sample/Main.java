package sample;

import Conexion.Conexion;

import com.jfoenix.controls.JFXSpinner;
import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    Cargar carga=new Cargar();

    private Stage stage;

    @Override
    public void init() throws Exception {
        Thread.sleep(2000);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Conexion conexion=new Conexion();
        if(conexion.conecta()){
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            primaryStage.getIcons().add(new Image(getClass().getResource("product+.png").toExternalForm()));
            primaryStage.setTitle("Iniciar sesión");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.show();
        }
        else{
            Parent root = FXMLLoader.load(getClass().getResource("ErrorConexion.fxml"));
            primaryStage.setTitle("Error");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.show();
        }
    }

    public void mostrarVentanaSecundaria() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
            AnchorPane ventanaDos = (AnchorPane) loader.load();
            Stage ventana = new Stage();
            ventana.setTitle("Iniciar sesión");
            ventana.initOwner(stage);
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);
            ventana.centerOnScreen();

            FXMLDocumentController controller = loader.getController();
            controller.setProgramaPrincipal(this);
            try {
                start(ventana);

            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            ventana.show();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
        LauncherImpl.launchApplication(Main.class, MainPreloader.class ,args);
    }
}
