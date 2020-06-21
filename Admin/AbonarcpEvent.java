/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import extras.Metodos;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;

import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class AbonarcpEvent implements Initializable {
    private Abonarcp ProgramaPrincipal;
    @FXML
    private Label factura,proveedor,total,resto,fecha,nresto,error;
    @FXML
    private JFXTextField abonarn,ref;
    @FXML
    private JFXComboBox pago;
    @FXML
    private JFXTextArea abono;
    private String cuenta;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        nresto.setText("0.00");

        setResto();
        abonarn.setOnKeyReleased(event -> {
            setResto();
        });
        ObservableList<String> tpagos=FXCollections.observableArrayList();
        tpagos.addAll("Efectivo","Transferencia","Pago movil","Tarjeta");
        pago.setItems(tpagos);
        pago.getSelectionModel().select(0);
        error.setText("");

}
private void setResto(){
    try {
        float tresto = Float.parseFloat(resto.getText());
        float abo = Float.parseFloat(abonarn.getText());
        float sresto = tresto - abo;
        nresto.setText(sresto + "");
    }catch (Exception e){
        System.out.println("error");
        nresto.setText("0.00");
    }
}
    @FXML
    private void prueba(KeyEvent event) {
        if(event.getCharacter().equals("1")||event.getCharacter().equals("2")||event.getCharacter().equals("3")||event.getCharacter().equals("4")||event.getCharacter().equals("5")||event.getCharacter().equals("6")||event.getCharacter().equals("7")||event.getCharacter().equals("8")||event.getCharacter().equals("9")||event.getCharacter().equals("0")) {
            abonarn.appendText(event.getCharacter());
        }
    }
    @FXML
    private void abonar(){
        Metodos metodos=new Metodos();
        try {
            metodos.abonacp(cuenta,abonarn.getText(),ref.getText(),pago.getSelectionModel().getSelectedItem().toString(),nresto.getText());
            error.setText("Abonado");
        } catch (Exception e) {
            error.setText("Error al abonar");
            e.printStackTrace();
        }
        finally {
            Timer t=new Timer();
            TimerTask tt=new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> error.setText(""));
                }
            };
            t.schedule(tt, 2000 );
        }
    }
    public void setProgramaPrincipal(Abonarcp ProgramaPrincipal) {
        this.ProgramaPrincipal = ProgramaPrincipal;
    }
    public void setcuenta(String cuenta){
        Metodos metodos=new Metodos();
        this.cuenta=cuenta;
        ArrayList<String> a=metodos.cuentapagar(cuenta);
        total.setText(a.get(0));
        abono.setText(a.get(1));
        resto.setText(a.get(2));
        proveedor.setText(a.get(3));
        fecha.setText(a.get(4));
        factura.setText(a.get(5));
        abonarn.setText(resto.getText());
    }
    @FXML
    private void salir(ActionEvent event) {
       (((Node) event.getSource())).getScene().getWindow().hide();
    }

}

