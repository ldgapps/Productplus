/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import com.jfoenix.controls.JFXComboBox;
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
public class EliminausuEvent implements Initializable {
    AdminEvent bakery=new AdminEvent();
    @FXML
    private Label error,nombre;
    @FXML
    private JFXPasswordField contra;
    @FXML
    private JFXComboBox usuario;
    @FXML
    private void agregar(ActionEvent event) {
    }
    public void setUsuario(String usua){
        Metodos metodos=new Metodos();
        ObservableList<String> itm=FXCollections.observableArrayList();
        error.setText(usua);
        itm.addAll(metodos.usuario(usua));
        usuario.setItems(itm);
        usuario.getSelectionModel().select(0);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    public void eliminar(){
        Metodos metodos=new Metodos();
        if(metodos.Elimnausuario(usuario.getSelectionModel().getSelectedItem().toString(),error.getText(),contra.getText())){
            nombre.setText("Eliminado");
        }
        else {
            nombre.setText("Contraseña incorrecta");
        }
    }

    @FXML
    private void salir(ActionEvent event) {
       (((Node) event.getSource())).getScene().getWindow().hide();
    }
}

