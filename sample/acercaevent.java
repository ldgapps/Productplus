package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

public class acercaevent implements Initializable {
    @FXML
    private Hyperlink link;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        link.setOnMousePressed(event -> {
            try{
                if(Desktop.isDesktopSupported()){
                    Desktop d=Desktop.getDesktop();
                    if(d.isSupported(Desktop.Action.BROWSE)){
                        d.browse(new URI("https://www.facebook.com/ldgapps"));
                    }
                }
            }catch (Exception e){}
        });
    }
}