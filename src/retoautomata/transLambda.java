/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retoautomata;

import ListClass.Grafo;
import ListClass.Node;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Stack;

/**
 * Clase encargada de la construccion de los cierres lambda y las transiciones lambda
 * @author Felipe Triana
 */
public class transLambda {
 /**
 * Se encarga de la construccion de los conjuntos de cierre lambda que corresponden a cada nodo de la construccion de thompson.
 * @param b Recibe un objeto tipo Grafo 
 * @return Retornara un vector que contendra los cierres lambda, cada posicion del vector + 1, corresponde a ese cierre lambda.
 */
    public String[] cierreLambda(Grafo b) {
        int num;
        String c = "";
        Node p;
        boolean liga1 = false, liga2 = false, termina = false;
        num = b.getUltimo().getLink1().getNum() + 1;
        Stack triana = new Stack();
        Stack andrea = new Stack();
        Stack raga = new Stack();
        Stack pilsen = new Stack();
        String[] cierres = new String[num];
        p = b.getPrimer();
        for (int i = 1; i < num; i++) {
            do {
                for (int j = 0; j < c.length(); j++) {
                    String cad = "";
                    while (c.charAt(j) != ',') {
                        cad = cad + c.charAt(j);
                        j = j + 1;
                    }
                    if (p.getNum() == Integer.parseInt(cad) && !andrea.isEmpty() && liga1 == false && liga2 == false) {
                        p = (Node) andrea.pop();
                        liga1 = (boolean) triana.pop();
                        liga2 = (boolean) raga.pop();
                    }
                    cad = "";
                }
                if (p.getData() == "^" && liga1 == false && liga2 == false) {
                    c = c + p.getNum() + ",";
                    andrea.push(p);
                    if (p.getLink1() == null) {
                        liga1 = true;
                        liga2 = true;
                        triana.push(liga1);
                        raga.push(liga2);
                        p = p.getLink2();
                        liga1 = false;
                        liga2 = false;
                    } else {
                        p = p.getLink1();
                        liga1 = true;
                        liga2 = false;
                        triana.push(liga1);
                        raga.push(liga2);
                        liga1 = false;
                        liga2 = false;
                    }
                } else if (p.getData() != "^") {
                    if (andrea.empty()) {
                        c = c + p.getNum() + ",";
                        termina = true;
                    } else {
                        c = c + p.getNum() + ",";
                        p = (Node) andrea.pop();
                        liga1 = (boolean) triana.pop();
                        liga2 = (boolean) raga.pop();
                    }
                } else if (p.getData() == "^" && liga1 == true && liga2 == false) {
                    if (!andrea.isEmpty()) {            
                        if (andrea.peek() == p.getLink2()) {
                            liga2 = true;
                        }
                    }
                    if (p.getLink2() == null) {  
                        liga2 = true;
                    } else if (liga2 != true) {                     
                        andrea.push(p);
                        p = p.getLink2();
                        liga2 = true;
                        triana.push(liga1);
                        raga.push(liga2);
                        liga1 = false;
                        liga2 = false;
                    }
                } else if (p.getData() == "^" && liga1 == true && liga2 == true && !andrea.empty()) {
                    p = (Node) andrea.pop();
                    liga1 = (boolean) triana.pop();
                    liga2 = (boolean) raga.pop();
                } else if (andrea.empty() && liga1 == true && liga2 == true) {
                    termina = true;
                }
            } while (termina == false);
            cierres[i] = c;
            c = "";
            termina = false;
            liga1 = false;
            liga2 = false;
            if (p.getData() == null) {
                i = num + 1;
            } else if (p.getS() == "s") {
                pilsen.push(p);
                p = p.getLink2();
            } else if (p.getLink1() == null && p.getLink2() != null) {
                p = (Node) pilsen.pop();
                p = p.getLink1();
            } else {
                p = p.getLink1();
            }
        }
        return cierres;
    }

        public void ordenaCierres(String cierres[]){
            String s;
            ArrayList<Integer> numeros = new ArrayList<Integer>();
            for (int i = 1; i < cierres.length; i++) {
                s = cierres[i];
                String cad = "";
                for (int j = 0; j < s.length(); j++) {
                        while(s.charAt(j)!=','){
                            cad = cad + s.charAt(j);
                            j = j + 1;
                        }
                 numeros.add(Integer.parseInt(cad));
                 cad = "";
                }
                Collections.sort(numeros);  //Ordena la lista de numeros(las transiciones lambda)
                for (int j = 0; j < numeros.size(); j++) {
                 cad = cad + numeros.get(j) + ",";
                }
                cierres[i] = cad;
                numeros.clear();
            }
        }
    
        public String purificar(String s){
             ArrayList<Integer> numeros = new ArrayList<Integer>();
             String cad = "";
                for (int j = 0; j < s.length(); j++) {
                        while(s.charAt(j)!=','){
                            cad = cad + s.charAt(j);
                            j = j + 1;
                        }
                 numeros.add(Integer.parseInt(cad));
                 cad = "";
                }
                int[] vector = new int[numeros.size()];
                for (int i = 0; i < vector.length; i++) {
                vector[i] = numeros.get(i);
            }
                  //Ordena la lista de numeros del string a purificar :v
		for(int i=0;i<vector.length;i++){
			for(int j=0;j<vector.length-1;j++){ 
				if(i!=j){
					if(vector[i]==vector[j]){
						
						vector[j]=0;
					}
				}
			}
		}
                numeros.clear();
                for (int i = 0; i < vector.length; i++) {
                    if (vector[i]!=0) {
                        numeros.add(vector[i]);
                    }
            }
                Collections.sort(numeros);
                for (int j = 0; j < numeros.size(); j++) {
                 cad = cad + numeros.get(j) + ",";
                }
                return cad;        //Devuelve la cadena purificada >:v
            
        }
        
        public Node buscaNodo(Grafo b, String a){ /** Método que busca si en el grafo b existe un nodo llevado como parametro*/
        Node p;
        int n;
        Stack pilan = new Stack();
        p = b.getPrimer();
        n = Integer.parseInt(a);
        while(!b.endOfList(p)){                       /**Recorre con un apuntador tipo nodo la lista hasta que no sea el final*/
            if (b.getUltimo().getLink1().getNum()==n) {   /** Si este apuntador que es el nodo final en el campo num es igual al que estamos buscando retornamos*/
            return b.getUltimo().getLink1();
            }
            if (p.getNum()== n) {          /** Si este apuntador que en el campo num es igual al que estamos buscando retornamos*/
               return p;
            } else {
            if(p.getS()=="s") {     /** Controla en caso de que en ese nodo apuntador  inicie una construcción de suma*/
                pilan.push(p);
                p = p.getLink2();          
            }else{
                if (p.getLink1()==null&&p.getLink2()!=null) {   /** Controla el caso de que en ese nodo apuntador inicie una construccion de estrella de kleene*/
                p = (Node) pilan.pop();
                p = p.getLink1(); 
                }else{
                p = p.getLink1();
                }
            }
            }
        }
        System.out.println("El nodo no existe");   /** El método retorna null si el nodo no existe*/
        return null; 
    }         
  
 public ArrayList<String> AFD(Grafo a, String[] cierres, String[] sim){
     Node p;
     ArrayList<String> trans = new ArrayList<>();
     ArrayList<String> listCierres = new ArrayList<>();
     transLambda bus = new transLambda();
     String i = cierres[1], s = "";
     int c = 0, p1,p2;
     listCierres.add(i);
     do {
         for (int j = 0; j < sim.length; j++) {
             for (int k = 0; k < i.length(); k++) {
                 String cad = "";
                    while (i.charAt(k) != ',') {
                        cad = cad + i.charAt(k);
                        k = k + 1;
                    }
                    p = bus.buscaNodo(a, cad);
                    if (p.getData()!=null) {
                       if (!p.getData().equals("^")) {
                    if (p.getData().equals(sim[j])) {
                        s = s + cierres[p.getLink1().getNum()];
                 }
                 } 
                 }
                 
                   
                
                    cad = "";
             }
             if (!s.equals("")) {
                 s = bus.purificar(s); 
                 trans.add(i+"-"+sim[j]+"-"+s);
             }else{
                 trans.add(i+"-"+sim[j]+"-"+"ERROR");
             }
             
             if (!s.equals("")) {
                   for (int f = 0; f < listCierres.size(); f++) {
                 if (s.equals(listCierres.get(f))) {
                     f = listCierres.size();
                 }else{
                     if (f == listCierres.size()-1) {
                         listCierres.add(s);
                     }
                 }        
             }
             }
             s = "";
         }
         c = c + 1;
         if (c < listCierres.size()) {
         i = listCierres.get(c);
         }
     } while (c < listCierres.size());

     
     return trans;
 }       
 
 public String[][] AFDtabla(ArrayList trans, String[] sim, String cierres[]){
     String t,est,simbolo,transicion,AoR;
     String[][] automata = new String[trans.size()][4];
     for (int i = 0; i < trans.size(); i++) {
         int c = 0;
         t = (String) trans.get(i);
         while(t.charAt(c)!='-'){
         c = c + 1;
         }
         est = t.substring(0,c);
         simbolo = (""+t.charAt(c+1));
         transicion = t.substring(c+3,t.length());
         automata[i][0]=est;
         automata[i][1]=simbolo;
         automata[i][2]=transicion;
         if (est.contains(cierres[cierres.length-1])) {
         automata[i][3]="1";    
         }else{
             automata[i][3]="0";
         }
     }
     for (int x=0; x < automata.length; x++) {
            for (int y=0; y < automata[x].length; y++) {
            System.out.print("["+automata[x][y]+"]"+"\t");
            }
            System.out.println("\n");
    }
     
     switch(""){
         case (""):
             break;
     }
     
     return automata;
 }
        
        
    }
    

