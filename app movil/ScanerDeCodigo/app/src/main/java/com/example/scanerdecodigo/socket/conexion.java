package com.example.scanerdecodigo.socket;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.example.scanerdecodigo.clases.clase_encore;
import com.example.scanerdecodigo.clases.consultasDB;
import com.example.scanerdecodigo.clases.obteneterModeloDelEquipo;

import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class conexion extends AsyncTask<String, Void, Void> {


    //variables globales
    private Socket socket;
    private PrintWriter printWriter;
    View vista;
    static String resultado = "";
    private String ipservidor, puerto;


    DataOutputStream flujo_de_salida = null;

    public conexion(View v) {
        vista = v;
    }


    @Override
    protected Void doInBackground(String... strings) {

        try {
            //limpiar
            socket = null;
            flujo_de_salida = null;

            //(String ipservidor,String puerto, String mensaje)
            String ip = strings[0].toString();
            String puerto = strings[1].toString();
            //String parametro1 = strings[2].toString();
            String parametro1 = clase_encore.encriptar_multiple(strings[2].toString());
            String parametro2 = strings[3].toString();
            String modelo= obteneterModeloDelEquipo.obtenerNombreDelDispositivo();
            try {
                socket = new Socket(ip, Integer.parseInt(puerto));
                resultado = "socket conectado";
            } catch (Exception e) {
                resultado = "no se puedo crear el socket";
                //limpiar
                socket = null;
                flujo_de_salida = null;
                this.cancel(true);
            }

            try {
                flujo_de_salida = new DataOutputStream(socket.getOutputStream());
                flujo_de_salida.writeUTF(parametro1);
                flujo_de_salida.writeUTF(parametro2);
                flujo_de_salida.writeUTF(modelo);
                flujo_de_salida.close();
                resultado = "parametro enviado";

                //limpiar
                socket = null;
                flujo_de_salida = null;

            } catch (Exception e) {
                resultado = "error al enviar parametro";
            }

        } catch (Exception e) {
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(vista.getContext(), resultado, Toast.LENGTH_SHORT).show();
        //terminamos el proceso

    }
}

