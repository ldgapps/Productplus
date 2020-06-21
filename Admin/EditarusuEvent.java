/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import extras.Metodos;
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
public class EditarusuEvent implements Initializable {
    private AdminEvent adminEvent=new AdminEvent();
    @FXML
    private Label nombre;
    @FXML
    private JFXPasswordField contra;
    @FXML
    private JFXTextField usuario;
    private String vusuario,nusuario;
    @FXML
    private void agregar(ActionEvent event) {
    }
    public String getUsuario(){
        System.out.println(nusuario+"aaaa");
        return nusuario;
    }
    public void setUsuario(String usua){
        vusuario=usua;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    public void eliminar(ActionEvent event){
        Metodos metodos=new Metodos();
        if(metodos.editanombre(vusuario,contra.getText(),usuario.getText())){
            nombre.setText("Usuario cambiado");
//            adminEvent.recibeusu(usuario.getText());
            salir(event);
        }
        else {
            nombre.setText("Contraseña incorrecta");
        }
    }

    @FXML
    private void salir(ActionEvent event) {
       (((Node) event.getSource())).getScene().getWindow().hide();
    }
    @FXML
    public void parametros(AdminEvent stage,Label texto,String string){
        adminEvent=stage;
        texto.setText(string);
    }
}

