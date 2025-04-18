package com.example.scanerdecodigo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scanerdecodigo.clases.consultasDB;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class layout_configuracion extends Fragment {

    EditText txt_ip, txt_puerto;
    Button btn_actualizar;
    ImageButton btn_scanner_config;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_layout_configuracion, container, false);

        txt_ip = (EditText) vista.findViewById(R.id.txt_ipservidor);
        txt_puerto = (EditText) vista.findViewById(R.id.txt_puertoconexxion);
        btn_actualizar = (Button) vista.findViewById(R.id.btn_actualizarp);
        btn_scanner_config = (ImageButton) vista.findViewById(R.id.img_buton);




        validaciones(vista);
        mostrar(vista);

        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if (txt_ip.getText().length() > 0 && txt_puerto.getText().length() > 0) {

                        consultasDB basededatos = new consultasDB(v);
                        basededatos.metodo_boton(1, txt_ip.getText().toString(), Integer.parseInt(txt_puerto.getText().toString()));
                        mensaje("vuelva a entrar a la aplicacion para actualizar la conexion");

                    } else {
                        mensaje("complete los campos");
                    }

                } catch (Exception e) {
                    mensaje("ocurrio un error");
                }

            }
        });


        btn_scanner_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirScanner();
            }
        });




        return vista;


    }


    private void mensaje(String mensaje) {
        Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
    }

    private void abrirScanner() {
        try {

            //  IntentIntegrator  ingrador = new IntentIntegrator(MainActivity.this);
            //  IntentIntegrator ingrador = new IntentIntegrator(getActivity());
            IntentIntegrator ingrador = IntentIntegrator.forSupportFragment(layout_configuracion.this);
            ingrador.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);  //lee todos los tipos de codigo
            ingrador.setPrompt("para activar la linterna apriete el boton de volumen superior");
            ingrador.setCameraId(0); //(0)camara trasera
            ingrador.setBeepEnabled(true); //sonido desde pues de leer
            ingrador.setBarcodeImageEnabled(true);
            ingrador.initiateScan(); //iniciar scanner

            //Toast.makeText(getContext(), "para encender la linterna tecla el boton de subir volumen", Toast.LENGTH_SHORT).show();

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
                //Toast.makeText(getContext(), "se cancelo el scaneo", Toast.LENGTH_SHORT).show();
            } else {

                String resultado = result.getContents();
                String ip_servidor = "";
                String puerto = "";
                boolean encontrado = false;
                int indice = -1;

                //encontrar posicion partida
                for (int i = 1; i <= resultado.length(); i++) {
                    if (resultado.substring(i - 1, i).equals("|")) {
                        encontrado = true;
                        indice = i;
                    }
                }

                //comenzar a separar
                if (encontrado) {
                    ip_servidor = resultado.substring(0, indice - 1);
                    puerto = resultado.substring(indice, resultado.length());
                    txt_ip.setText(ip_servidor);
                    txt_puerto.setText(puerto);
                } else {
                  Toast.makeText(this.getContext(), "indice de separacion no encontrado. \n introdusca la configuracion manualmente", Toast.LENGTH_SHORT).show();
                }

                //  1234|012

            }
        } else {
            //cuando se nulo. para que no tenga problemas
            super.onActivityResult(requestCode, resultCode, data);
           // Toast.makeText(this.getContext(), "vacio", Toast.LENGTH_SHORT).show();
        }

    }

    private void mostrar(View v) {
        try {

            String datos[] = new String[2];
            txt_ip.setText("");
            txt_puerto.setText("");
            consultasDB basededatos = new consultasDB(v);
            datos = basededatos.listar(1);

            txt_ip.setText(datos[0]);
            txt_puerto.setText(datos[1]);
        } catch (Exception e) {
           // mensaje("ocurrio un error al mostrar valores");
        }
    }


    private void validaciones(View v) {

        try {
            txt_ip.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() > 15) {
                        txt_ip.setText("");
                        mensaje("no puedes ingresar mas caracteres");
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });


            txt_puerto.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() > 4) {
                        txt_puerto.setText("");
                        mensaje("no es aceptable un puerto con mas de 4 caracteres");
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } catch (Exception e) {
            mensaje("ocurrio un error ");
        }
    }
}