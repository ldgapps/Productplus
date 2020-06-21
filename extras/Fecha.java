/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extras;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Gonzalez Duerto
 */
public class Fecha {
    private Calendar c2 = new GregorianCalendar () ;
    
    public String dia(){
        String dia = Integer.toString (c2.get (Calendar.DATE));
        if(dia.length()==1){            
            dia="0"+dia;
            return dia;
        }
        return dia;
    }
    public String mes(){
            String mes = Integer .toString (c2.get (Calendar .MONTH )+1) ;        
            if(mes.length()==1){ 
                mes="0"+mes;
                return mes;
            }
            return mes;
    }
    public String agno(){
        String annio = Integer.toString ( c2.get( Calendar . YEAR)) ;  
        return annio;
    }

}
