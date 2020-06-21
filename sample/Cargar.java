package sample;

import Conexion.Conexion;
import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Cargar extends Preloader {

    ProgressBar bar;
    Stage stage;
    @Override
    public void init() throws Exception {
        createPreloaderScene();
        start(stage);
    }
    private Scene createPreloaderScene() {
        bar = new ProgressBar();
        BorderPane p = new BorderPane();
        p.setCenter(bar);

        return new Scene(p, 300, 150);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setScene(createPreloaderScene());
        stage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification scn) {
        if (scn.getType() == StateChangeNotification.Type.BEFORE_START) {
            stage.hide();


        }
    }

    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        System.out.println("aaa");
        bar.setProgress(pn.getProgress());
    }
}
