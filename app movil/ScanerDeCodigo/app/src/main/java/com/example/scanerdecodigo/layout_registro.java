package com.example.scanerdecodigo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.scanerdecodigo.R;
import com.example.scanerdecodigo.clases.CustomAdapterRegistro;
import com.example.scanerdecodigo.clases.consultasDB;
import com.example.scanerdecodigo.socket.clase_estado;
import com.example.scanerdecodigo.socket.conexion;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class layout_registro extends Fragment {


    EditText txt_texto_enviar;
    ImageButton btn_enviar;

    conexion conexion;
    clase_estado clase_estado=new clase_estado();

    View vistag;
    FloatingActionButton fab;
    ListView lista_resultado;
    CustomAdapterRegistro clase_adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_layout_registro, container, false);

        txt_texto_enviar=(EditText)vista.findViewById(R.id.txt_enviar);
        btn_enviar=(ImageButton)vista.findViewById(R.id.btn_enviar);

        fab = (FloatingActionButton) vista.findViewById(R.id.btn_camara);
        lista_resultado = (ListView) vista.findViewById(R.id.lista);
        clase_adapter = new CustomAdapterRegistro(vista.getContext());


        recuperar_texto(vista);
        listar_datos();

        vistag = vista;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirScanner();
            }
        });

        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarParametro(txt_texto_enviar.getText().toString());
                txt_texto_enviar.setText("");
            }
        });

        return vista;
    }




    private void abrirScanner() {
        try {

            IntentIntegrator ingrador = IntentIntegrator.forSupportFragment(layout_registro.this);
            ingrador.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);  //lee todos los tipos de codigo
            ingrador.setPrompt("para activar la linterna apriete el boton de volumen superior");
            ingrador.setCameraId(0); //(0)camara trasera
            ingrador.setBeepEnabled(true); //sonido desde pues de leer
            ingrador.setBarcodeImageEnabled(true);
            ingrador.initiateScan(); //iniciar scanner

            Toast.makeText(getContext(), "para encender la linterna tecla el boton de subir volumen", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(getContext(), "ocurrio in error", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) { //si diferente hay resultado
            if (result.getContents() == null) {
                //si se cancela
               // Toast.makeText(getContext(), "se cancelo el scaneo", Toast.LENGTH_SHORT).show();
            } else {

                //enviamos el parametro atravez del socket
                enviarParametro(result.getContents());

            }
        } else {
            //cuando se nulo. para que no tenga problemas
            super.onActivityResult(requestCode, resultCode, data);
            Toast.makeText(this.getContext(), "vacio", Toast.LENGTH_SHORT).show();
        }

    }


    private void mensaje(View v, String mensaje) {
        Toast.makeText(v.getContext(), mensaje, Toast.LENGTH_SHORT).show();
    }


    private void enviarParametro(String parametro) {

        try {

            if (clase_estado.getEstado()) {
                //recibimos la datos de la conexion
                //mensaje(vistag,clase_estado.getIp_servidor());

                conexion = new conexion(vistag);
                String ip=clase_estado.getIp_servidor();
                String puerto=clase_estado.getPuerto();

                conexion.execute(ip, puerto, parametro, "registro");

                agregar_lista("bien",parametro);


            } else {
               // Toast.makeText(getContext(), "el servidor se encuentra inactivo", Toast.LENGTH_SHORT).show();
                agregar_lista("error",parametro);
            }



        } catch (Exception e) {
            Toast.makeText(getContext(), "ocurrio un error \n" + e, Toast.LENGTH_SHORT).show();
            conexion.cancel(true);
        }
    }


    private void agregar_lista(String status,String dato) {
        try {
            int imagen=R.drawable.error;
            if (status.equals("bien")){
                imagen=R.drawable.bien;
            }
            clase_adapter.agregar(imagen, dato);
            listar_datos();

        } catch (Exception e) {

        }
    }

    private void recuperar_texto(View v) {
        lista_resultado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //tv_resultado.setText(lista_resultado.getItemAtPosition(position).toString());

              txt_texto_enviar.setText( clase_adapter.getTexto(position));
            }
        });
    }


    private void listar_datos() {
        try {
            lista_resultado.setAdapter(clase_adapter);
        } catch (Exception e) {
        }
    }


}



