package com.example.scanerdecodigo;

import android.app.AlertDialog;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scanerdecodigo.clases.BaseDeDatos;
import com.example.scanerdecodigo.clases.CustomAdapterVenta;
import com.example.scanerdecodigo.clases.consultasDB;
import com.example.scanerdecodigo.clases.obteneterModeloDelEquipo;
import com.example.scanerdecodigo.interfacez.dialogo_de_carga;
import com.example.scanerdecodigo.socket.clase_estado;
import com.example.scanerdecodigo.socket.puente;

import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.concurrent.ConcurrentHashMap;

public class MainActivity extends AppCompatActivity {

    TextView tv_estado,tv_modelo;
    String datos[] = new String[2];
    consultasDB consultaBasededato;
    Button btn_guardar,btn_aceptar_error;
    EditText txt_ip;
    EditText txt_puerto;
    ImageButton btn_scanner,btnActualizar,btn_ayuda_error_dialogo;
   static Handler hilo = new Handler();

    private AlertDialog dialogo_configuracion,dialogo_error,dialogo_ayuda;
    final dialogo_de_carga modalCarga = new dialogo_de_carga(MainActivity.this);

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //ventana de bienvenda
        setTheme(R.style.Theme_ScanerDeCodigo_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //cabecera sin titulo
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.layout_configuracion, R.id.layout_venta, R.id.layout_registro, R.id.layout_hacerca)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        //para que los iconos de la barra lateral conserven su color
        navigationView.setItemIconTintList(null);
        //leer los datos

        actualizartexto_inicio();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private void actualizartexto_inicio() {
        try {
            datos = recuperar_datos(1);

            if (!datos[0].equals("") && !datos[1].equals("")) {

                comprobar_conexion_modal(datos[0], datos[1]);
                cambiar_datosBanner();

                if (!clase_estado.getEstado()){
                    abrirDialogo_error_coneccion();
                }
            } else {
                //Toast.makeText(this, "ingrese la configuracion del servidor por favor", Toast.LENGTH_SHORT).show();
                abrirDialogoConfiguracion();
            }

        } catch (Exception e) {

        }
    }


    private void mensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }


    BaseDeDatos admin;

    public String[] recuperar_datos(int id) {

        consultaBasededato = new consultasDB();
        String datos[] = new String[2];
        try {
            //buscar
            admin = new BaseDeDatos(this, "db_servidor", null, 1);
            SQLiteDatabase base_de_datos = admin.getWritableDatabase(); //habilitar lectura

            //consultar
            Cursor fila = base_de_datos.rawQuery("select ipservidor,puerto from tservidor where codigo=" + id, null);
            //recuperar datos
            if (fila.moveToFirst()) { //si contiene valores
                datos[0] = fila.getString(0);
                datos[1] = fila.getString(1);
            } else {
                //mensaje("no se encontraron datos");
                datos[0] = "";
                datos[1] = "";

            }


            base_de_datos.close();

        } catch (Exception e) {
            mensaje("error al listar");
        }

        return datos;
    }





    private void guardarConfiguracion() {
        try {
            if (txt_ip.getText().length() > 0 && txt_puerto.getText().length() > 0) {

                Boolean a = insertarDatos_db(1, txt_ip.getText().toString(), Integer.parseInt(txt_puerto.getText().toString()));
                if (a) {
                    mensaje("se guardo con exito");
                    clase_estado.setIp_servidor(txt_ip.getText().toString());
                    clase_estado.setPuerto(txt_puerto.getText().toString());

                    //cerrar dialogo carga
                    dialogo_configuracion.dismiss();
                    comprobar_conexion_modal(txt_ip.getText().toString(), txt_puerto.getText().toString());
                    cambiar_datosBanner();
                }

            } else {
                mensaje("complete los campos");
            }
        } catch (Exception e) {
            mensaje("error al guardar configuracion");
        }

    }

    private void validaciones() {

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


    private void abrirScanner() {
        try {
            IntentIntegrator ingrador = new IntentIntegrator(MainActivity.this);
            ingrador.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);  //lee todos los tipos de codigo
            ingrador.setPrompt("para activar la linterna apriete el boton de volumen superior");
            ingrador.setCameraId(0); //(0)camara trasera
            ingrador.setBeepEnabled(true); //sonido desde pues de leer
            ingrador.setBarcodeImageEnabled(true);
            ingrador.initiateScan(); //iniciar scanner
            //Toast.makeText(getContext(), "para encender la linterna tecla el boton de subir volumen", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            mensaje("ocurrio un error");
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) { //si diferente hay resultado
            if (result.getContents() == null) {
                //si se cancela

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
                    mensaje("indice de separacion no encontrado. \n introdusca la configuracion manualmente");
                }

                //  1234|012

            }
        } else {
            //cuando se nulo. para que no tenga problemas
            super.onActivityResult(requestCode, resultCode, data);
            //mensaje("vacio");
        }

    }

    private void abrirDialogoConfiguracion() {
        validaciones();

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_configuracion, null);
        builder.setView(view);
        dialogo_configuracion = builder.create();
        dialogo_configuracion.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //

        btn_guardar = (Button) view.findViewById(R.id.btn_actualizar_dialogo);
        txt_ip = (EditText) view.findViewById(R.id.txt_ipservidor_dialogo);
        txt_puerto = (EditText) view.findViewById(R.id.txt_puertoconexion_dialogo);
        btn_scanner = (ImageButton) view.findViewById(R.id.img_buton_dialogo);
        ImageButton btn_ayuda=(ImageButton)view.findViewById(R.id.buton_dialogo_ayuda_config) ;

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //metodo guardar
                guardarConfiguracion();
                //salir
                //dialogo_configuracion.dismiss();
            }
        });

        btn_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirScanner();
            }
        });

        btn_ayuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo_configuracion.dismiss();
                abrirDialogo_ayuda_instalacion();
            }
        });

        //mostrar
        dialogo_configuracion.show();
    }

    private void abrirDialogo_error_coneccion() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_conexion_error, null);
        builder.setView(view);
        dialogo_error = builder.create();
        dialogo_error.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //

        btn_aceptar_error = (Button) view.findViewById(R.id.btn_aceptar_dialogo_error);
        btnActualizar = (ImageButton) view.findViewById(R.id.buton_actualizar_dialogo_error);
        btn_ayuda_error_dialogo = (ImageButton) view.findViewById(R.id.buton_actualizar_dialogo_ayuda);

        btn_aceptar_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //salir
                dialogo_error.dismiss();
            }
        });

        btn_ayuda_error_dialogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //salir
                dialogo_error.dismiss();
                abrirDialogo_ayuda_instalacion();
            }
        });



        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //recreate();
                overridePendingTransition(0,0);
                finish();
                overridePendingTransition(0,0);
                startActivity(getIntent());
                overridePendingTransition(0,0);
            }
        });

        //mostrar
        dialogo_error.show();
    }

    private void abrirDialogo_ayuda_instalacion() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogo_instruciones, null);
        builder.setView(view);
        dialogo_ayuda = builder.create();
        dialogo_ayuda.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //

        Button btn_aceptar = (Button) view.findViewById(R.id.btn_aceptar_dialogo_instrucciones);
        TextView tv_descarga=(TextView)view.findViewById(R.id.tv_descarga_dialog);
        TextView tv_video=(TextView)view.findViewById(R.id.tv_video_dialog);

        tv_descarga.setMovementMethod(LinkMovementMethod.getInstance());
        tv_video.setMovementMethod(LinkMovementMethod.getInstance());

        tv_descarga.setMovementMethod(LinkMovementMethod.getInstance());

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //salir
                dialogo_ayuda.dismiss();
            }
        });


        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //recreate();
                overridePendingTransition(0,0);
                finish();
                overridePendingTransition(0,0);
                startActivity(getIntent());
                overridePendingTransition(0,0);
            }
        });


        //mostrar
        dialogo_ayuda.show();
    }

    public boolean insertarDatos_db(int id, String ip, int puerto) {//
        try {
            admin = new BaseDeDatos(this, "db_servidor", null, 1);
            SQLiteDatabase base_de_datos = admin.getWritableDatabase();//habilitar lectura

            //insertar en la base de datos
            ContentValues regitro = new ContentValues();
            regitro.put("codigo", id);
            regitro.put("ipservidor", ip);
            regitro.put("puerto", puerto);
            base_de_datos.insert("tservidor", null, regitro);
            base_de_datos.close();

            return true;
        } catch (Exception e) {
            mensaje("error al insertar");
            return false;
        }
    }


    void comprobar_conexion_modal(String ipServidor, String puerto) {
        //actualizamos el estado del servidor
        try {
            modalCarga.mostrarAlertaCarga();
            puente estadocon = new puente(ipServidor, puerto);
            estadocon.start();
            //para recibir correctamente el resultado nesesita un tienpo
            estadocon.sleep(1000);
            hilo.postDelayed(new Runnable() {
                @Override
                public void run() {
                    modalCarga.terminar_dialogo();
                }
            }, 2000);

            Boolean valor = estadocon.valor();
            if (valor) {
                clase_estado.setEstado(true);
                clase_estado.setConextividad("activo");
            } else {
                //tv_estado.setText("servidor: inactivo");
                clase_estado.setEstado(false);
                clase_estado.setConextividad("inactivo");
            }


            //paramos el hilo
            if (estadocon.isAlive()) {
                estadocon.destroy();
            }

        } catch (Exception e) {
        }
    }


    public void cambiar_datosBanner() {
        try {
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            View header = navigationView.getHeaderView(0);
            tv_estado = (TextView) header.findViewById(R.id.tvestado);
            tv_modelo = (TextView) header.findViewById(R.id.tvmodelo);


            if (clase_estado.getEstado()) {
                tv_estado.setText("servidor: conectado");
                mensaje("servidor conectado");
            } else {
                tv_estado.setText("servidor: desconectado");
               // mensaje("servidor no conectado. \n  vuelva a abrir la app si desea \n comprobar nuevaente la conexion");
            }

            datos = recuperar_datos(1);
            if (!datos[0].equals("") && !datos[1].equals("")){
                clase_estado.setIp_servidor(datos[0]);
                clase_estado.setPuerto(datos[1]);
            }


            //modelo del celular
            try {
                clase_estado.setModelo_celular(obteneterModeloDelEquipo.obtenerNombreDelDispositivo());
                tv_modelo.setText("Dispositivo: "+clase_estado.getModelo_celular());
            }catch (Exception e){
            }
        } catch (Exception e) {
            mensaje(e.toString());
        }
    }


}




