/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cajero;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import extras.Metodos;
import extras.Textoampo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AbonarccEvent implements Initializable {
    private Abonarcc ProgramaPrincipal;
    @FXML
    private Label resto, nresto,nombre,cedula,fecha,total,abonado;
    @FXML
    private JFXComboBox pago;
    @FXML
    private JFXTextField abonar, ref;

    private String cuenta;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        Textoampo textoampo=new Textoampo();
        ObservableList<String> strings=FXCollections.observableArrayList();
        strings.addAll("Efectivo","Transferencia","Pago movil","Tarjeta");
        pago.setItems(strings);
        textoampo.setdouble(abonar);
        nresto.setText("0.00");
        abonar.setOnKeyReleased(event -> {
            setresto();
        });

}
private void setresto(){
    try {
        float tresto = Float.parseFloat(resto.getText());
        float abo = Float.parseFloat(abonar.getText());
        float sresto = tresto - abo;
        nresto.setText(sresto + "");
    }catch (Exception e){
        nresto.setText("0.00");
    }
}
@FXML
private void abonar(){
    Metodos metodos=new Metodos();
    try {
        metodos.abonacc(cuenta,abonar.getText(),ref.getText(),pago.getSelectionModel().getSelectedItem().toString(),nresto.getText());
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public void setProgramaPrincipal(Abonarcc ProgramaPrincipal) {
        this.ProgramaPrincipal = ProgramaPrincipal;
    }

    public void setpro(String cuenta){
          this.cuenta=cuenta;
          Metodos metodos=new Metodos();
          ArrayList<String> a=metodos.cuentacobrar(cuenta);
          total.setText(a.get(0));
          abonado.setText(a.get(1));
          resto.setText(a.get(2));
          nombre.setText(a.get(3));
          fecha.setText(a.get(4));
          cedula.setText(a.get(5));
        abonar.setText(resto.getText());
    }
    @FXML
    private void salir(ActionEvent event) {
       (((Node) event.getSource())).getScene().getWindow().hide();
    }
}

