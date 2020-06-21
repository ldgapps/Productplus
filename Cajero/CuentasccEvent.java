/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cajero;

import Conexion.Conexion;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 *
 * @author Gonzalez Duerto
 */
public class CuentasccEvent implements Initializable {
    private Cuentascc ProgramaPrincipal;
    private Conexion co;
    @FXML
    private TableView table;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tabla();
    }
    private ObservableList<ObservableList> data;
    public void setProgramaPrincipal(Cuentascc ProgramaPrincipal) {
        this.ProgramaPrincipal = ProgramaPrincipal;
    }
    @FXML
    private void salir(ActionEvent event) {
        System.exit(0);
    }
    @FXML
    public void tabla(){
        Connection c ;
        co=new Conexion();
        data = FXCollections.observableArrayList();
        try{
            c = co.getconnection();
            table.getColumns().clear();
            String SQL = "SELECT * FROM `product`.`cuentas por cobrar` WHERE `resta` NOT LIKE '0.0' AND `resta` NOT LIKE '0.0'";
            //ResultSet
            ResultSet rs = c.createStatement().executeQuery(SQL);

            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));

                table.getColumns().addAll(col);

            }

            while(rs.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    row.add(rs.getString(i));
                }

                data.add(row);

            }
            table.setItems(data);
            table.setOnMouseClicked(event -> {
                    Abonarcc abonarcc=new Abonarcc();
                    String item=table.getItems().get(table.getSelectionModel().getSelectedIndex()).toString();
                    abonarcc.mostrarVentanaSecundaria(item.substring(1,item.indexOf(",")));

            });

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        finally {
            co.desconectar();
        }
    }
    @FXML
    public void abrir(){
        table.getSelectionModel().getSelectedItem().toString();
    }
}

