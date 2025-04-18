package com.example.scanerdecodigo;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scanerdecodigo.clases.CustomAdapterRegistro;
import com.example.scanerdecodigo.clases.CustomAdapterVenta;
import com.example.scanerdecodigo.clases.consultasDB;
import com.example.scanerdecodigo.socket.clase_estado;
import com.example.scanerdecodigo.socket.conexion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class layout_venta extends Fragment {

    com.example.scanerdecodigo.socket.conexion conexion;

    View vistageneral;
    EditText txt_texto_enviar;
    ImageButton btn_enviar;


    clase_estado clase_estado = new clase_estado();

    FloatingActionButton fab;
    ListView lista_resultado;
    CustomAdapterVenta clase_adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_layout_venta, container, false);

        txt_texto_enviar = (EditText) vista.findViewById(R.id.txt_enviar_venta);
        btn_enviar = (ImageButton) vista.findViewById(R.id.btn_enviar_venta);

        fab = (FloatingActionButton) vista.findViewById(R.id.btn_camara_venta);
        lista_resultado = (ListView) vista.findViewById(R.id.lista_venta);
        clase_adapter = new CustomAdapterVenta(vista.getContext());


        recuperar_texto(vista);
        listar_datos();


        vistageneral = vista;
        // abrirScanner();


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
            //conexion = new conexion(vistageneral);
            IntentIntegrator ingrador = IntentIntegrator.forSupportFragment(layout_venta.this);
            ingrador.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);  //lee todos los tipos de codigo
            ingrador.setPrompt("para activar la linterna apriete el boton de volumen superior");
            ingrador.setCameraId(0); //(0)camara trasera
            ingrador.setBeepEnabled(true); //sonido desde pues de leer
            ingrador.setBarcodeImageEnabled(true);
            ingrador.initiateScan(); //iniciar scanner

            Toast.makeText(getContext(), "para encender la linterna tecla el boton de subir volumen", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), "error al abrir scanner", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) { //si diferente hay resultado
            if (result.getContents() == null) {

                //si se cancela
                //Toast.makeText(getContext(), "se cancelo el scaneo", Toast.LENGTH_SHORT).show();
                /*Toast.makeText(getContext(), "para salir aplste restroceso una vez mas", Toast.LENGTH_SHORT).show();
               getActivity().finishAffinity();
               getActivity().finish();*/

            } else {
                //mostramos
                enviarParametro(result.getContents());

                //abrimos nuevamente el scaner
                abrirScanner();

            }
        } else {
            //cuando se nulo. para que no tenga problemas
            super.onActivityResult(requestCode, resultCode, data);
            Toast.makeText(this.getContext(), "vacio", Toast.LENGTH_SHORT).show();
        }

    }

    private void enviarParametro(String parametro) {
        try {
            if (clase_estado.getEstado()) {
                //recibimos la datos de la conexion
                conexion = new conexion(vistageneral);
                String ip = clase_estado.getIp_servidor();
                String puerto = clase_estado.getPuerto();

                conexion.execute(ip, puerto, parametro, "venta");

                agregar_lista("bien", parametro);
            } else {
               // Toast.makeText(getContext(), "el servidor se encuentra inactivo", Toast.LENGTH_SHORT).show();
                agregar_lista("error", parametro);
            }

        } catch (Exception e) {
            Toast.makeText(getContext(), "error al enviar parametro \n" + e, Toast.LENGTH_SHORT).show();
            conexion.cancel(true);
        }
    }

    private void mensaje(View v, String mensaje) {
        Toast.makeText(v.getContext(), mensaje, Toast.LENGTH_SHORT).show();

    }


    private void agregar_lista(String status, String dato) {
        try {
            int imagen = R.drawable.error;
            if (status.equals("bien")) {
                imagen = R.drawable.bien;
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

                txt_texto_enviar.setText(clase_adapter.getTexto(position));
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