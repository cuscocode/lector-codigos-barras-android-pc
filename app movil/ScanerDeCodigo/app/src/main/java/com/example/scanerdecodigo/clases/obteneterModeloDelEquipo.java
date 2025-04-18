package com.example.scanerdecodigo.clases;

import android.os.Build;

public class obteneterModeloDelEquipo {

    public static String obtenerNombreDelDispositivo(){
        try {
            String fabricante= Build.MANUFACTURER;
            String modelo=Build.MODEL;
            if (modelo.startsWith(fabricante)){
                return primera_letra_en_mayuscula(modelo);
            }else{
                return primera_letra_en_mayuscula(fabricante)+" "+modelo;
            }
        }catch (Exception e){
            return "Desconocido";
        }
    }

    private static String primera_letra_en_mayuscula(String cadena){
        try{
            if (cadena==null || cadena.length()==0){
                return "";
            }
            char primera_letra=cadena.charAt(0);
            if (Character.isUpperCase(primera_letra)){
                return cadena;
            }else{
                return Character.toUpperCase(primera_letra)+cadena.substring(1);
            }
        }catch (Exception e){
            return "";
        }

    }



    public String optener_ip(){
        try {
            String ip="";



            return ip;

        }catch (Exception e){
            return "";
        }
    }
}
