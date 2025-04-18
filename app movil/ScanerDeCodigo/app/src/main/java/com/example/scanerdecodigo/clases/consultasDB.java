package com.example.scanerdecodigo.clases;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Toast;

public class consultasDB {

    View vista;
    BaseDeDatos admin;


    public consultasDB(View v) {
        vista = v;
    }

    public consultasDB() {
    }


    public String[] recuperar_info(int id) {
        String datos[] = new String[2];
        datos[0] = "";
        datos[1] = "";
        try {
            //buscar
            admin = new BaseDeDatos(vista.getContext(), "db_servidor", null, 1);
            SQLiteDatabase base_de_datos = admin.getWritableDatabase(); //habilitar lectura

            //consultar
            Cursor fila = base_de_datos.rawQuery("select ipservidor,puerto from tservidor where codigo=" + id, null);
            //recuperar datos
            if (fila.moveToFirst()) { //si contiene valores
                datos[0] = fila.getString(0);
                datos[1] = fila.getString(1);
            }//de lo contrario vacio


            base_de_datos.close();

        } catch (Exception e) {
        }

        return datos;
    }


    public String[] listar(int id) {
        String datos[] = new String[2];
        try {
            //buscar
            admin = new BaseDeDatos(vista.getContext(), "db_servidor", null, 1);
            SQLiteDatabase base_de_datos = admin.getWritableDatabase(); //habilitar lectura

            //consultar
            Cursor fila = base_de_datos.rawQuery("select ipservidor,puerto from tservidor where codigo=" + id, null);
            //recuperar datos
            if (fila.moveToFirst()) { //si contiene valores
                datos[0] = fila.getString(0);
                datos[1] = fila.getString(1);
            } else {
                mensaje("no se encontraron datos");
                datos[0] = "";
                datos[1] = "";
            }


            base_de_datos.close();

        } catch (Exception e) {
            mensaje("error al listar");
        }

        return datos;
    }

    public void insertar(int id, String ip, int puerto) {
        try {
            admin = new BaseDeDatos(vista.getContext(), "db_servidor", null, 1);
            SQLiteDatabase base_de_datos = admin.getWritableDatabase();//habilitar lectura

            //insertar en la base de datos
            ContentValues regitro = new ContentValues();
            regitro.put("codigo", id);
            regitro.put("ipservidor", ip);
            regitro.put("puerto", puerto);
            base_de_datos.insert("tservidor", null, regitro);
            base_de_datos.close();

            mensaje("se inserto correctamente");
        } catch (Exception e) {
            mensaje("error al insertar");
        }


    }


    public void modificar(int id, String ip, int puerto) {
        try {
            admin = new BaseDeDatos(vista.getContext(), "db_servidor", null, 1);
            SQLiteDatabase base_de_datos = admin.getWritableDatabase();//habilitar lectura

            //modificar en la base de datos
            ContentValues regitro = new ContentValues();

            regitro.put("codigo", id);
            regitro.put("ipservidor", ip);
            regitro.put("puerto", puerto);


            int cantidad = base_de_datos.update("tservidor", regitro, "codigo=" + id, null);
            base_de_datos.close();

            if (cantidad == 1) {
                mensaje("se modifico correctamente");
            } else {
                mensaje("no se pudo modificar");
            }
        } catch (Exception e) {
            mensaje("error al modificar");
        }
    }

    public void eliminar(int id) {
        try {
            admin = new BaseDeDatos(vista.getContext(), "db_servidor", null, 1);
            SQLiteDatabase base_de_datos = admin.getWritableDatabase();//habilitar lectura
            int cantidad = base_de_datos.delete("tservidor", "codigo=" + id, null);
            base_de_datos.close();

            if (cantidad == 1) {
                mensaje("se elimino correctamente");
            } else {
                mensaje("no se pudo eliminar");
            }

        } catch (Exception e) {
            mensaje("error al eliminar");
        }
    }


    private void mensaje(String mensaje) {
        Toast.makeText(vista.getContext(), mensaje, Toast.LENGTH_SHORT).show();
    }


    public void metodo_boton(int id, String ip, int puerto) {//
        try {
            admin = new BaseDeDatos(vista.getContext(), "db_servidor", null, 1);
            SQLiteDatabase base_de_datos = admin.getWritableDatabase(); //habilitar lectura

            //saber si hay datos en la tabla
            int cantidad = 0;
            //consultar
            Cursor fila = base_de_datos.rawQuery("select count(*) from tservidor", null);
            fila.moveToFirst();
            cantidad = fila.getInt(0);
            base_de_datos.close();


            if (cantidad > 0) {

                modificar(id, ip, puerto);
            } else {
                insertar(id, ip, puerto);
            }

        } catch (Exception e) {
            mensaje("error metodo");
        }
    }

}
