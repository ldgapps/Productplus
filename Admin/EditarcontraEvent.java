/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import com.jfoenix.controls.JFXPasswordField;

import extras.Metodos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author Gonzalez Duerto
 */
public class EditarcontraEvent implements Initializable {
    private Editarcontra ProgramaPrincipal;

    @FXML
    private Label error, nombre;
    @FXML
    private JFXPasswordField contra, ncontra;
    @FXML
    private void agregar(ActionEvent event) {
    }
    public void setUsuario(String usua){
        ObservableList<String> itm=FXCollections.observableArrayList();
        error.setText(usua);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    public void eliminar(){
        Metodos metodos=new Metodos();
        if(metodos.editacontra(error.getText(),contra.getText(),ncontra.getText())){
            nombre.setText("Contraseña cambiada");
        }
        else {
            nombre.setText("Contraseña incorrecta");
        }
    }
    public void setProgramaPrincipal(Editarcontra ProgramaPrincipal) {
        this.ProgramaPrincipal = ProgramaPrincipal;
    }

    @FXML
    private void salir(ActionEvent event) {
       (((Node) event.getSource())).getScene().getWindow().hide();
    }
}

