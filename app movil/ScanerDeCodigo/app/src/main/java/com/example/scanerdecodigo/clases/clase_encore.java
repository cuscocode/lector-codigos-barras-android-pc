package com.example.scanerdecodigo.clases;




public class clase_encore {

    private static String encriptar(String texto) {
        char array[] = texto.toCharArray();
        char arrayax[] = texto.toCharArray();

        int a = array.length - 1;
        for (int k = 0; k < array.length; k++) {
            arrayax[a] = (char) (array[k] + (char) k);  //remplaza 5 caracteres despues del codigo ascii
            a--;
        }

        String encriptado = String.valueOf(arrayax);
        return String.valueOf(encriptado);
    }

    public static String encriptar_multiple(String texto) {
        String aux = texto;
        int indice=4;//desencriptar.length();
        for (int i = 0; i < indice; i++) {
            aux = encriptar(aux);
            System.out.println(aux);
        }
        return aux;
    }

    private static String desencriptar(String texto) {
        String desencriptado = "";
        char arrayD[] = texto.toCharArray(); //separa palabras y los mete en arreglo
        int retoceso=texto.length()-1;
        for (int i = 0; i < arrayD.length; i++) {
            arrayD[i] = (char) (arrayD[i] - (char) retoceso);  //remplaza 5 caracteres despues del codigo ascii
            retoceso--;
            desencriptado=String.valueOf(arrayD[i])+desencriptado; //invertimos
        }



        // String desencriptado = String.valueOf(arrayD);
        return desencriptado;
    }

    public static String desencriptar_multiple(String desencriptar) {
        String text = desencriptar;
        int indice=4;//desencriptar.length();
        for (int i = 0; i < indice; i++) {
            text = desencriptar(text);
        }
        return text;
    }


}
