/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import java.applet.AudioClip;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import metodos.encore;
import vista.mesajes.funcionesAnimaciones;

/**
 *
 * @author ANONYMOUS
 */
public class controlador_vista {

    private DefaultTableModel modelo;
    private AudioClip musica;
    //formularios a usar
    frm_principal formulario = new frm_principal();

    public controlador_vista() {
        formulario.setVisible(true);
    }

    public void mostrar_otro(String resultado, String tipo) {
        try {

            if (tipo.equals("ip_servidor")) {
                formulario.lb_ip_servidor.setText(resultado);
                formulario.generar_codigo();
            }
            if (tipo.equals("estado_servidor")) {
                formulario.lb_servir_estatus.setText(resultado);
            }
            if (tipo.equals("estado_cliente")) {
                formulario.lb_cliente_estado.setText(resultado);
            }
            if (tipo.equals("nombre_host")) {
                formulario.lb_host.setText(resultado);
            }

        } catch (Exception e) {
            // JOptionPane.showMessageDialog(null, "error al mostrar");
        }
    }

    private void sonido() {
        try {
            musica = java.applet.Applet.newAudioClip(getClass().getResource("/vistas/notificacion.wav"));
            musica.play();
        } catch (Exception e) {
        }
    }

    public void mostrar_resultado_tabla(String parametro, String cliente,String modelo_cel) {
        try {

            parametro=encore.desencriptar_multiple(parametro);
            
            modelo = (DefaultTableModel) formulario.tabla.getModel();
            String datos[] = {parametro, cliente,modelo_cel};
            modelo.addRow(datos);
            formulario.tabla.setModel(modelo);

            funcionesAnimaciones.mostrarAlertaBien(modelo_cel, "codigo recibido: "+parametro);
            sonido();
        } catch (Exception e) {
            System.out.println("error en la tabla");
        }
    }

   
}
