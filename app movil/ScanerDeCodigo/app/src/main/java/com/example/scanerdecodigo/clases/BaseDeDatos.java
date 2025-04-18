package com.example.scanerdecodigo.clases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BaseDeDatos extends SQLiteOpenHelper {
    public BaseDeDatos(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db_servidor) {

        //creando la tabla dnd se alamacenaran los datos
        db_servidor.execSQL("create table tservidor(codigo int primary key,ipservidor text,puerto int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
