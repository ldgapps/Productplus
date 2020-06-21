/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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
public class EliminaProductEvent implements Initializable {
    AdminEvent bakery=new AdminEvent();
    @FXML
    private Label error,nombre;
    @FXML
    private JFXPasswordField contra;
    @FXML
    private JFXComboBox usuario,usuario1;
    private Metodos metodos;
    @FXML
    private JFXTextField busqueda;
    @FXML
    private void agregar(ActionEvent event) {
    }
    public void setUsuario(String usua){
        metodos=new Metodos();
        usuario1.getItems().addAll("Viveres","Charcuteria");
        usuario1.getSelectionModel().select(0);

        error.setText(usua);
        setproducto();}
        public void setproducto(){
        metodos=new Metodos();
    ObservableList<String> itm=FXCollections.observableArrayList();
        itm.addAll(metodos.productos(usuario1.getSelectionModel().getSelectedItem().toString(),busqueda.getText()));
        usuario.setItems(itm);
        usuario.getSelectionModel().select(0);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        usuario1.setOnAction(event -> {
            setproducto();
        });
        busqueda.setOnKeyReleased(event -> {
            setproducto();
        });
    }
    public void eliminar(){
        Metodos metodos=new Metodos();
        if(metodos.Elimnaproduct(usuario.getSelectionModel().getSelectedItem().toString().substring(0,usuario.getSelectionModel().getSelectedItem().toString().indexOf(":")),error.getText(),contra.getText(),usuario1.getSelectionModel().getSelectedItem().toString())){
            nombre.setText("Eliminado");
            setUsuario(error.getText());
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

