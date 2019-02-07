
package retoautomata;

import ListClass.Grafo;
import java.util.List;
import java.util.Stack;

/**
 * Clase encargada del reconocimiento de la expresion regular
 * @author Andrea Ortiz
 * @author Felipe Triana
 */
public class expresionRegular {

    private Thompson thom;
    private String expresion;
    static Grafo resultante;

    public expresionRegular(Thompson thom, String expresion) {
        this.thom = thom;                                                        /** Constructor de la clase expresión regular */ 
        expresion = "("+expresion+")"+'&';                           /** Se le agregan los paramétros y el símbolo de fin de secuencia */
        this.expresion = expresion;                                                  
    }

    

    public Stack generarGrafos() {                                    /** Método que genera el grafo a partir de la expresión y haciendo uso a su vez */
        boolean abrioP = false;                                          /** del método reconocerExpresioncita*/
        boolean segundoDigito = false;
        String operacion = "";
        Grafo rg = new Grafo();
        Grafo sg = new Grafo();
        Stack pila = new Stack();
        Object oR = null;
        for (int i = 0; i < expresion.length(); i++) {     /** Método que realiza el reconocimiento de la expresión teniendo en cuenta todos los casos
                                                            **también hace un llamado al método reconocer expresioncita que realiza el reconocimento para cada caso/*                                                              */
            char x = expresion.charAt(i);
            if (x == ')') {
                Stack s = new Stack();                                   
                s.push('&');
                char y = ' ';
                while (y != '(') {    
                    if(y !=' ' && y != '?'){
                    s.push(y);                        
                    }else if(y == '?'){
                    s.push(oR);
                    }
                    try{
                    oR = pila.pop();
                    y = (char) oR;
                    }catch(Exception e){
                    y = '?';                        
                    }                         
                }            
                Grafo g= new Grafo();
                g= reconocerExpresioncita(s);
                pila.push(g);
                
            } else {
                if(x == '&'){
                return pila;
                }
                pila.push(x);
            }
        }
        return pila;        
    }

    public Grafo reconocerExpresioncita(Stack expresion) {       
        String operacion = "";                                               /** Método para reconocer la expresión que recibe como parámetro una pila
                                                   ** que contiene la expresión y viene del método generar grafos */
         boolean segundo = false;  
        Stack pilita = new Stack();
        Stack pilita2 = new Stack();               
        Grafo fg = new Grafo();
        Grafo sg = new Grafo();
        Grafo result = new Grafo();
        Grafo thomsom = new Grafo();
        int nExpresion = expresion.size();
        Object rO = null;                
        for (int i = 0; i < nExpresion; i++) {
            char r;
            try {
                rO = expresion.pop();
                r = (char) rO;                                       /** Desapila cada caracter de la pila recibida como parámetro y procede analizarlo*/
                
            } catch (Exception e) {                
                r = ' ';
            }
            switch (r) {                                    /** Realiza un Switch con el caracter recibido como parámetro para analizar cual es y que hacer con el*/
                case '+':                              /** Caso cuando lee un caracter + */
                    if(segundo == true){       
                        fg = (Grafo) pilita2.pop();     /** Solo entra cuando segundo==true, es decir, que haya leído una operación antes*/
                        result = thom.building5(fg);    /** Realiza la construcción #5 para el caso en que r+, lo guarda en el grafo fg y lo apila en pilita2  */
                        pilita2.push(result);
                    }
                    else{
                        fg = (Grafo) pilita.pop();    /** Solo entra cuando segundo==false, es decir, que no haya leído una operación antes*/
                        result = thom.building5(fg);    /** Realiza la construcción #5 para el caso en que r+*/
                        pilita.push(result);
                    } 
                    break;
                case '|':                                     /** Caso cuando lee un caracter | */
                    if (operacion != null && !"".equals(operacion)) {     /** Controla que no se haya leído ninguna operación antes, pero si es asi entra a un nuevo switch para revisar que operación es */
                        switch (operacion) {            /** Realiza un Switch para la operación que haya guardada en el String, es decir, que ya haya leído*/
                            case "concatenacion":             /** Caso en el que la operación es una concatenación*/
                                if (pilita.size() == 1 && pilita2.size() == 1) {  /** Compara si las pilas donde se guardan los simbolos son del mismo tamaño para realizar la operacion */
                                    fg = (Grafo) pilita.pop();                  /** Lo desapilado de ambas pilas puede ser un grafo o tan solo un caracter, por grafo se refiere a una construccion ya realizada*/
                                    sg = (Grafo) pilita2.pop();                                    
                                    result = thom.building2(fg,sg);     /** Realiza la construcción para el caso fg.sg, ambos grafos son lo desapilado de pilita y plita2*/
                                } else { 
                                    pilita.push('&');
                                    pilita2.push('&');
                                    pilita = invertirPila(pilita);    /** Se invierte la pila para que la expresión quede en el orden correcto*/
                                    pilita2 = invertirPila(pilita2);
                                    Grafo gAux1 = reconocerExpresioncita(pilita); /** Realiza el método reconocerExpresioncita a la expresión que haya en ambas pilas*/
                                    Grafo gAux2 = reconocerExpresioncita(pilita2);  /** Ambos son llamados recursivos*/                                          
                                    result =   thom.building2(gAux1,gAux2);           /** Realiza la construcción de gAux1.gAux2, ambos obtenidos de las llamadas recursivas*/
                                }
                                pilita.push(result);      /** Apila el grafo resultante en pilita*/
                                operacion = "suma";     /** Le lleva al String operación aquella que realiza el caracter reconocido*/
                                break;
                            case "suma":                       /** Caso en el que la operación es una suma*/
                                if (pilita.size() == 1 && pilita2.size() == 1) {   /** Compara si las pilas donde se guardan los simbolos son del mismo tamaño para realizar la operacion */
                                    fg = (Grafo) pilita.pop();                   /** Lo desapilado de ambas pilas puede ser un grafo o tan solo un caracter, por grafo se refiere a una construccion ya realizada*/
                                    sg = (Grafo) pilita2.pop();
                                    result = thom.building3(fg,sg);       /** Realiza la construcción para el caso fg|sg, ambos grafos son lo desapilado de pilita y plita2*/
                                } else {
                                    pilita.push('&');
                                    pilita2.push('&');
                                    pilita = invertirPila(pilita);               /** Se invierte la pila para que la expresión quede en el orden correcto*/
                                    pilita2 = invertirPila(pilita2);
                                    Grafo gAux1 = reconocerExpresioncita(pilita);  /** Realiza el método reconocerExpresioncita a la expresión que haya en ambas pilas*/
                                    Grafo gAux2 = reconocerExpresioncita(pilita2);  /** Ambos son llamados recursivos*/ 
                                    result =   thom.building3(gAux1,gAux2);        /** Realiza la construcción de gAux1|gAux2, ambos obtenidos de las llamadas recursivas*/
                                }
                                pilita.push(result);                     /** Apila el grafo resultante en pilita*/
                                operacion = "suma";                         /** Le lleva al String operación aquella que realiza el caracter reconocido*/   
                                break;
                            default:
                                break;
                        }
                    }else{
                        operacion = "suma";         /** Le lleva al String operación aquella que realiza el caracter reconocido*/
                        segundo = true;         /** Hace segundo= true, lo que quiere decir que ya leyo una operación*/
                    }                    
                    break;               
                case '*':                               /** Caso en el que caracter leido es una estrella de Kleene*/
                    if(segundo == true){                 /** Solo entra cuando segundo==true, es decir, que haya leído una operación antes*/
                        fg = (Grafo) pilita2.pop();      
                        result =  thom.building4(fg);  /** Realiza la operación de * con lo que desapilo de pilita*/
                        pilita2.push(result);
                    }
                    else{                                  /** Solo entra cuando segundo==false, es decir, que no haya leído una operación antes*/
                        fg = (Grafo) pilita.pop();          
                        result =  thom.building4(fg);    /** Realiza la operación de * con lo que desapilo de pilita*/
                        pilita.push(result);
                    }                    
                    break;
                case '.':                            /** Caso en el que caracter leido es una estrella de Kleene*/
                    if (operacion != null && !"".equals(operacion)) {   /** Controla que no se haya leído ninguna operación antes, pero si es asi entra a un nuevo switch para revisar que operación es */
                        switch (operacion) {              /** Realiza un Switch para la operación que haya guardada en el String, es decir, que ya haya leído*/
                            case "concatenacion":           /** Caso en el que la operación es una concatenación*/
                                if (pilita.size() == 1 && pilita2.size() == 1) {   /** Compara si las pilas donde se guardan los simbolos son del mismo tamaño para realizar la operacion */
                                    fg = (Grafo) pilita.pop();
                                    sg = (Grafo) pilita2.pop();              /** Lo desapilado de ambas pilas puede ser un grafo o tan solo un caracter, por grafo se refiere a una construccion ya realizada*/
                                    result = thom.building2(fg,sg);            /** Realiza la construcción para el caso fg.sg, ambos grafos son lo desapilado de pilita y plita2*/         
                                } else {
                                    pilita.push('&');
                                    pilita2.push('&');
                                    pilita = invertirPila(pilita);                 /** Se invierte la pila para que la expresión quede en el orden correcto*/
                                    pilita2 = invertirPila(pilita2);
                                    Grafo gAux1 = reconocerExpresioncita(pilita);    /** Realiza el método reconocerExpresioncita a la expresión que haya en ambas pilas*/
                                    Grafo gAux2 = reconocerExpresioncita(pilita2);  /** Ambos son llamados recursivos*/  
                                    result = thom.building2(gAux1,gAux2);                /** Realiza la construcción de gAux1.gAux2, ambos obtenidos de las llamadas recursivas*/
                                }
                                operacion = "concatenacion";                         /** Le lleva al String operación aquella que realiza el caracter reconocido*/   
                                pilita.push(result);                                
                                break;
                            case "suma":               
                                pilita2.push(r);                                   /** Caso en el que la operación es una suma*/
                                break;    
                            default:
                                break;
                        }
                    }
                    else{
                        operacion = "concatenacion";                          /** Le lleva al String operación aquella que realiza el caracter reconocido*/   
                        segundo = true;
                    }                                        
                    break;
                case '&':                                                  /** Caso en el que simbolo leido es fin de secuencia*/
                      if (operacion != null && !"".equals(operacion)) {                  /** Controla que no se haya leído ninguna operación antes, pero si es asi entra a un nuevo switch para revisar que operación es */
                        switch (operacion) {                              /** Realiza un Switch para la operación que haya guardada en el String, es decir, que ya haya leído*/
                            case "concatenacion":                       /** Caso en el que la operación es una concatenación*/
                                if (pilita.size() == 1 && pilita2.size() == 1) {  /** Compara si las pilas donde se guardan los simbolos son del mismo tamaño para realizar la operacion */
                                    fg = (Grafo) pilita.pop();           /** Lo desapilado de ambas pilas puede ser un grafo o tan solo un caracter, por grafo se refiere a una construccion ya realizada*/
                                    sg = (Grafo) pilita2.pop();  /** Realiza la construcción para el caso fg.sg, ambos grafos son lo desapilado de pilita y plita2*/         
                                    result = thom.building2(fg,sg);
                                } else {
                                    pilita.push('&');                       /** Se invierte la pila para que la expresión quede en el orden correcto*/
                                    pilita2.push('&');
                                    pilita = invertirPila(pilita);
                                    pilita2 = invertirPila(pilita2);
                                    Grafo gAux1 = reconocerExpresioncita(pilita);      /** Realiza el método reconocerExpresioncita a la expresión que haya en ambas pilas*/
                                    Grafo gAux2 = reconocerExpresioncita(pilita2); /** Ambos son llamados recursivos*/  
                                    result = thom.building2(gAux1,gAux2);              /** Realiza la construcción de gAux1.gAux2, ambos obtenidos de las llamadas recursivas*/
                                }
                                pilita.push(result);                                
                                break;
                            case "suma":
                                if (pilita.size() == 1 && pilita2.size() == 1) {   /** Compara si las pilas donde se guardan los simbolos son del mismo tamaño para realizar la operacion */
                                    fg = (Grafo) pilita.pop();               /** Lo desapilado de ambas pilas puede ser un grafo o tan solo un caracter, por grafo se refiere a una construccion ya realizada*/
                                    sg = (Grafo) pilita2.pop();
                                    result = thom.building3(fg,sg);         /** Realiza la construcción para el caso fg|sg, ambos grafos son lo desapilado de pilita y plita2*/         
                                } else {
                                    pilita.push('&');
                                    pilita2.push('&');
                                    pilita = invertirPila(pilita);        /** Se invierte la pila para que la expresión quede en el orden correcto*/
                                    pilita2 = invertirPila(pilita2);
                                    Grafo gAux1 = reconocerExpresioncita(pilita);  /** Realiza el método reconocerExpresioncita a la expresión que haya en ambas pilas*/
                                    Grafo gAux2 = reconocerExpresioncita(pilita2);  /** Ambos son llamados recursivos*/  
                                    result =  thom.building3(gAux1,gAux2);            /** Realiza la construcción de gAux1|gAux2, ambos obtenidos de las llamadas recursivas*/
                                }
                                pilita.push(result);                                
                                break;
                            default:
                                break;
                        }
                    }                   
                    break; 
                case ' ':
                    if (segundo == false) {       
                        pilita.push(rO);                       
                    } else {                        
                        pilita2.push(rO);
                    }
                    break;
                default:
                    String s = ""+r;
                    if (segundo == false) {                      /** Solo entra cuando segundo==false, es decir, que no haya leído una operación antes*/
                        Grafo aux = thom.building1(s);          /** Realiza la construccion de r* para s*/
                        pilita.push(aux);                        
                    } else {                                    /** Solo entra cuando segundo==true, es decir, que haya leído una operación antes*/
                        Grafo aux = thom.building1(s);             /** Realiza la construccion de r* para s*/
                        pilita2.push(aux);
                    }
                    break;
            }
        }        
        resultante= (Grafo)pilita.pop();
        return resultante;                
    }
       
    
    public Stack invertirPila(Stack pila){
        Stack pilaUx = new Stack();
        int n = pila.size();                         /** Invierte la pila para que quede en el orden correcto */
        for (int i =0; i < n ;i++) {
            pilaUx.push(pila.pop());
        }
        return pilaUx;
    }

    public String[] simEntrada(String expresion){   /** Método que analiza los simbolos de entrada, sin repeticiones y sin incluir caracteres especiales*/
        int contador=0,contador2=0,contador3=0;
        int c =0;
        String simbolo;
        for (int i = 0; i < expresion.length(); i++) {  /** For que recorre la expresión caracer por caracter y mira si es un caracter especial o un char normal*/
            if (expresion.charAt(i)!='+' && expresion.charAt(i)!='.'&& expresion.charAt(i)!= '*' && expresion.charAt(i)!=')' && expresion.charAt(i)!='|' && expresion.charAt(i)!='(') {
               contador++; /** Aumenta contador cada vez que encuentra un simbolo diferente a los de la condición para saber cuantos son*/
            }           
        }
        String[] simEntrep = new String[contador];        
        for (int i = 0; i < expresion.length(); i++) {  /** Crea un nuevo vector y lo recorre en este for donde meterá esos simbolos distintos en sus respectivas posiciones*/
        if (expresion.charAt(i)!='+' && expresion.charAt(i)!='.'&& expresion.charAt(i)!= '*' && expresion.charAt(i)!=')' && expresion.charAt(i)!='|' && expresion.charAt(i)!='(') {
               
               simbolo = Character.toString(expresion.charAt(i));
               simEntrep[c] = simbolo;
               c++;
             
            }    
        }
        
        for (int i = 0; i < simEntrep.length; i++) {        /** Elimina los espacios en blanco que hayan quedado en el vector*/    
            for (int j = i+1; j < simEntrep.length; j++) {  
                if (simEntrep[i].equals(simEntrep[j])) {
                    simEntrep[j] = "";   
                }
    
            }
        }
        for (int i = 0; i < simEntrep.length; i++) {
            if (!simEntrep[i].equals("")) {                  
                contador2++;
            }
        }
        
        String[] sinRep= new String[contador2];
       
        
        for (int i = 0; i < simEntrep.length; i++) {
           
                if (!simEntrep[i].equals("")) {
                sinRep[contador3]= simEntrep[i];
                contador3++;
            }
          
        }
        return sinRep;
    }
    
    
    
}
