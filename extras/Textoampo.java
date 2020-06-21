package extras;

import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Textoampo {
    public void setdouble(JFXTextField textField){
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9](\\.[0-9]*)?")) {
                textField.setText(newValue.replaceAll("[^\\d.]", ""));
                StringBuilder aus = new StringBuilder(newValue);
                boolean firstPointFound = false;
                for (int i = 0; i < aus.length(); i++){
                    if(aus.charAt(i) == '.') {
                        if(!firstPointFound)
                            firstPointFound = true;
                        else
                            aus.deleteCharAt(i);
                    }
                }
                newValue = aus.toString();
            }
        });
    }
    public void setint(JFXTextField textField){
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
}
