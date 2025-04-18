package com.example.scanerdecodigo.clases;


import java.util.ArrayList;
import java.util.List;

public class item_lista_registro {

    public int imagen;
    public String texto;

    public static List<item_lista_registro> lista=new ArrayList<>();



    public item_lista_registro(int imagen, String texto) {
        this.imagen = imagen;
        this.texto = texto;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}


