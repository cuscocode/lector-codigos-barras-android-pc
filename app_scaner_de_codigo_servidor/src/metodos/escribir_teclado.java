/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodos;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class escribir_teclado {

    public void escribir_mediante_scrip(String texto) {
        try {
            texto=encore.desencriptar_multiple(texto);  //desencriptamos
            String codigo = "";
            codigo = codigo + "set teclado=Wscript.CreateObject(\"Wscript.shell\") \n";
            codigo = codigo + "teclado.sendkeys \"" + texto + "\"";

            File archivo = new File("key.vbs");
            FileWriter fw = new FileWriter(archivo);
            System.out.println(archivo);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(codigo);
            bw.close();
            System.out.println("ubicacion:" + archivo.getAbsolutePath());
            Desktop.getDesktop().open(archivo);
            //funcionesAnimaciones.mostrarAlertaBien("error", "se limpiaron las colas de impreciones ");

        } catch (Exception e) {
            //funcionesAnimaciones.mostrarAlertaError("error", "error al limpiar para cola de impreciones");
            System.out.println("error al crear y ejecutar script");
            
        }
    }

    public static void main(String[] args) {
        // new escribir_teclado().escribir_mediante_scrip("hola");
    }
}
