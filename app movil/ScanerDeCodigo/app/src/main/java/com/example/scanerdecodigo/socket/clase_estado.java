package com.example.scanerdecodigo.socket;

public class clase_estado {
    private static boolean estado = false;
    private static String conextividad = "";
    private static String ip_servidor = "127.0.0.1", puerto = "8000";
    private static String modelo_celular = "desconocido";

    public static boolean isEstado() {
        return estado;
    }

    public static void setEstado(boolean estado) {
        clase_estado.estado = estado;
    }

    public static boolean getEstado() {
        return estado;
    }

    public static String getConextividad() {
        return conextividad;
    }

    public static void setConextividad(String conextividad) {
        clase_estado.conextividad = conextividad;
    }

    public static String getIp_servidor() {
        return ip_servidor;
    }

    public static void setIp_servidor(String ip_servidorp) {
        ip_servidor = ip_servidorp;
    }

    public static String getPuerto() {
        return puerto;
    }

    public static void setPuerto(String puertop) {
        puerto = puertop;
    }


    public static String getModelo_celular() {
        return modelo_celular;
    }

    public static void setModelo_celular(String modelo_celular) {
        clase_estado.modelo_celular = modelo_celular;
    }
}
