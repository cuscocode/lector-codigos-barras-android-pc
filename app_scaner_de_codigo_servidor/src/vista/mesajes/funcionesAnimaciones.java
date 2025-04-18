/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.mesajes;

/**
 *
 * @author ANONYMOUS
 */
public class funcionesAnimaciones {

    public funcionesAnimaciones() {

    }

    public static void mostrarAlertaBien(String titulo, String mensaje) {
        try {
            new AlertaBien(titulo, mensaje).setVisible(true);
        } catch (Exception e) {
        }
    }

    public static void mostrarAlertaError(String titulo, String mensaje) {
        try {
            new AlertaError(titulo, mensaje).setVisible(true);
        } catch (Exception e) {
        }
    }

    public static void mostrarAlerta(String titulo, String mensaje) {
        try {
            new Alerta(titulo, mensaje).setVisible(true);
        } catch (Exception e) {
        }
    }

    public static void mostrarMensajeDialogo(String titulo, String mensaje) {
        try {
            new mensaje_dialogo(null, true, titulo, mensaje).setVisible(true);
        } catch (Exception e) {
        }
    }

    public static void mostrarMensajeDialogo_error(String titulo, String mensaje) {
        try {
            new mensaje_dialogo_error(null, true, titulo, mensaje).setVisible(true);
        } catch (Exception e) {
        }
    }

}
