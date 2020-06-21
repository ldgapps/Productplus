package sample;

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

public class Acerca extends Application {
    private Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent rooti = FXMLLoader.load(getClass().getResource("acercade.fxml"));
        primaryStage.getIcons().add(new Image(getClass().getResource("../sample/product+.png").toExternalForm()));
        primaryStage.setTitle("Iniciar sesión");
        primaryStage.setScene(new Scene(rooti));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void mostrarVentanaSecundaria() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("acercade.fxml"));
            AnchorPane ventanaDos = (AnchorPane) loader.load();
            Stage ventana = new Stage();
            ventana.initOwner(stage);
            ventana.setTitle("Acerca de bakery");
            Scene scene = new Scene(ventanaDos);
            ventana.setScene(scene);

            //FXMLDocumentController controller = loader.getController();
            //controller.setProgramaPrincipal(this);
            try {
                //                AdminEvent controller = loader.getController();
                start(ventana);
            } catch (Exception ex) {
                Logger.getLogger(Acerca.class.getName()).log(Level.SEVERE, null, ex);
            }
            ventana.show();
        } catch (IOException ex) {
            Logger.getLogger(Acerca.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
