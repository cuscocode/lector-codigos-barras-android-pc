package com.example.scanerdecodigo.socket;

import android.view.View;
import android.widget.Toast;

import java.net.Socket;

public class puente extends Thread {


    Socket socket;
    String ipserve, puerto;


    public puente(String ip, String puertop) {
        ipserve = ip;
        puerto = puertop;

    }

    static boolean creado=false;

    @Override
    public void run() {
        super.run();
        try {

            creado=false;
            try {
                socket = new Socket(ipserve, Integer.parseInt(puerto));
                creado=true;
            } catch (Exception e) {
                creado=false;
            }
           /* boolean a=false;
            while (!a){

                a=true;
            }*/
        } catch (Exception e) {
        }

    }

    public boolean valor() {
        return creado;
    }
}
