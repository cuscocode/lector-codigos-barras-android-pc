package com.example.scanerdecodigo.interfacez;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import com.example.scanerdecodigo.R;

public class dialogo_de_carga {
    Activity activity;
    private AlertDialog dialogo;

    public dialogo_de_carga(Activity activity) {
        this.activity = activity;
    }


    public void mostrarAlertaCarga(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialogo_carga, null));
        builder.setCancelable(false);

        dialogo=builder.create();
        dialogo.show();

    }

    public void terminar_dialogo(){
        dialogo.dismiss();
    }
}
