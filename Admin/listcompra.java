package Admin;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class listcompra {
    SimpleStringProperty tipo;
    SimpleStringProperty codigo;
    SimpleStringProperty producto;
    SimpleStringProperty cantidad;
    SimpleStringProperty preciou;
    SimpleStringProperty preciot;

    public JFXButton getElimina() {
        return elimina.get();
    }

    public SimpleObjectProperty<JFXButton> eliminaProperty() {
        return elimina;
    }

    public void setElimina(JFXButton elimina) {
        this.elimina.set(elimina);
    }

    SimpleObjectProperty<JFXButton> elimina;

    public String getCodigo() {
        return codigo.get();
    }

    public String getTipo() {
        return tipo.get();
    }

    public String getPreciot() {
        return preciot.get();
    }

    public SimpleStringProperty preciotProperty() {
        return preciot;
    }

    public void setPreciot(String preciot) {
        this.preciot.set(preciot);
    }

    public String getPreciou() {
        return preciou.get();
    }

    public SimpleStringProperty preciouProperty() {
        return preciou;
    }

    public void setPreciou(String preciou) {
        this.preciou.set(preciou);
    }

    public String getCantidad() {
        return cantidad.get();
    }

    public SimpleStringProperty cantidadProperty() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad.set(cantidad);
    }

    public String getProducto() {
        return producto.get();
    }

    public SimpleStringProperty productoProperty() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto.set(producto);
    }

    public SimpleStringProperty tipoProperty() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo.set(tipo);
    }

    public SimpleStringProperty codigoProperty() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo.set(codigo);
    }


    public listcompra(String tipo, String codigo, String producto, String cantidad, String preciou, String preciot, JFXButton elimina){
        this.tipo=new SimpleStringProperty(tipo);
        this.codigo= new SimpleStringProperty(codigo);
        this.producto= new SimpleStringProperty(producto);
        this.cantidad= new SimpleStringProperty(cantidad);
        this.preciou= new SimpleStringProperty(preciou);
        this.preciot= new SimpleStringProperty(preciot);
        this.elimina=new SimpleObjectProperty<JFXButton>(elimina);
        elimina.setOnAction(event ->{
            AdminEvent admin=new AdminEvent();
            System.out.println(codigo);

        });
    }
    public listcompra(String tipo, String codigo, String producto, String cantidad, String preciou, String preciot, JFXButton elimina, TableView<listcompra> lista){
        this.tipo=new SimpleStringProperty(tipo);
        this.codigo= new SimpleStringProperty(codigo);
        this.producto= new SimpleStringProperty(producto);
        this.cantidad= new SimpleStringProperty(cantidad);
        this.preciou= new SimpleStringProperty(preciou);
        this.preciot= new SimpleStringProperty(preciot);
        this.elimina=new SimpleObjectProperty<JFXButton>(elimina);
        elimina.setOnAction(event ->{

            lista.getItems().remove(this);
        });
    }
}
