/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retoautomata;

import java.io.FileWriter;
import java.io.PrintWriter;

/**
 *Esta clase se encarga de crear el archivo Programa.java y copiar en el, el programa del automata que le indiquemos
 * en pantalla
 * @author Felipe
 */
public class Archivo {
    
    public void programa(String matriz[][]) {

        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("src/run/Programa.java");
            pw = new PrintWriter(fichero); 
            pw.println("package run;");
            pw.println("import javax.swing.JOptionPane;");
            pw.println("public class Programa {");
            pw.println("public static void main(String[] args) {");
            pw.println("int i = 0, j = 0;");
            pw.println("boolean aor=false;");
            pw.println("String cadena = JOptionPane.showInputDialog(\"Ingrese la hilera siendo cuidadoso con los simbolos del lenguaje\");");
            pw.println("String estado = \""+matriz[0][0]+"\";");
            pw.println("while(j < cadena.length()){");
            pw.println("String simbolo = cadena.charAt(j)+\"\";");
            pw.println("  switch(estado){");
            for (int i = 0; i < matriz.length; i++) {
                pw.println("      case(\""+matriz[i][0]+"\"):");
                String t = matriz[i][0];
                pw.println("        switch(simbolo){");
                while(i<matriz.length&&t.equals(matriz[i][0])){
                    pw.println("          case \""+matriz[i][1]+"\":");
                    pw.println("          estado "+"= \""+matriz[i][2]+"\""+";");
                    for (int j = 0; j < matriz.length; j++) {
                        if (matriz[i][2].equals(matriz[j][0])) {
                            if (matriz[j][3].equals("1")) {
                                pw.println("          aor = true;");
                            }else{
                                pw.println("          aor = false;");
                            }
                        j = j + 1;     
                        }
                    }
                    pw.println("          break;");
                    i = i + 1;
                }
                i = i - 1;             
                pw.println("        }");
                pw.println("        break;");
            }
            pw.println("  }");
            pw.println(" if (estado.equals(\"ERROR\")) {\n" +
"       aor = false; \n" +
"    }");
            pw.println("j = j + 1;");
            pw.println("}");
            pw.println("System.out.println(\"El estado final es: \"+estado+\" la cadena es: \"+aor);");
            pw.println("}");
            pw.println("}");
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }
    
    
}
