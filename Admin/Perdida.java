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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple Preloader Using the ProgressBar Control
 *
 * @author Gonzalez Duerto
 */
public class Perdida extends Application {
        private Stage stage;
    private AnchorPane rootPane;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("Perdida.fxml"));
        
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
            public void mostrarVentanaSecundaria() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Perdida.fxml"));
                AnchorPane ventanaDos = (AnchorPane) loader.load();
                Stage ventana = new Stage();
                ventana.setTitle("Nueva perdida");
                ventana.initOwner(stage);
                Scene scene = new Scene(ventanaDos);               
                ventana.setScene(scene);
                ventana.initModality(Modality.APPLICATION_MODAL);
                ventana.getIcons().add(new Image(getClass().getResource("../sample/product+.png").toExternalForm()));
               PerdidaEvent controller = loader.getController();
                controller.setProgramaPrincipal(this);
                try {
                    start(ventana);
                } catch (Exception ex) {
                    Logger.getLogger(Perdida.class.getName()).log(Level.SEVERE, null, ex);
                }
                ventana.show();
            } catch (IOException ex) {
                Logger.getLogger(Perdida.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
