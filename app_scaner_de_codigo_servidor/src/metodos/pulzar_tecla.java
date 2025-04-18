/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodos;


/**
 *
 * @author ANONYMOUS
 */
public class pulzar_tecla {

    public static void main(String[] args) {
        for (int i = 0; i < 1000000000; i++) {
            String texto=java.awt.event.KeyEvent.getKeyText(i);
           // System.out.println(texto);
            if (!texto.contains("Desconocido keyCode: ")) {
                System.out.println(" "+i+ " : "+texto);
            }
        }
    }
   
}
