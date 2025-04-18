package com.example.scanerdecodigo.clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scanerdecodigo.R;

import java.util.List;

public class CustomAdapterVenta extends BaseAdapter {

    Context context;
    private List<item_lista_venta>lista;


    public CustomAdapterVenta(Context context) {
        this.context = context;
        this.lista = item_lista_venta.lista;
    }



    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imagen;
        TextView tv_contenido;

        item_lista_venta clase=lista.get(position);

        if (convertView==null)
            convertView= LayoutInflater.from(context).inflate(R.layout.disen_lista,null);
            imagen=(ImageView) convertView.findViewById(R.id.img_stado);
            tv_contenido=(TextView)convertView.findViewById(R.id.tv_contenido);

            //mostrar texto
            imagen.setImageResource(clase.imagen);
            tv_contenido.setText(clase.texto);

        return convertView;
    }













//metodos



    public void agregar(int imagen, String dato) {
       try {
           lista.add(new item_lista_venta(imagen,dato));
       }catch (Exception e){
       }
    }

    public int getImagen(int posicion) {
        item_lista_venta  clase=lista.get(posicion);
        return clase.imagen;
    }

    public String getTexto(int posicion) {
        item_lista_venta  clase=lista.get(posicion);
        return clase.texto;
    }

}
