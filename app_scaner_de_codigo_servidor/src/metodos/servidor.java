package metodos;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;
import vistas.controlador_vista;
import static vistas.frm_principal.CONFIG;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ANONYMOUS
 */
public class servidor {

    private ServerSocket socketServidor = null;
    private Socket socketCliente = null;
    private DataInputStream flujoDeEntrada;
    private int puerto;

    escribir_teclado teclado = new escribir_teclado();
    public static final String CONFIG = "config.properties";

    controlador_vista controller = new controlador_vista();

    private void recuperar_puerto_configurador() {
        puerto = 8000;
        try (InputStream lecturaE = new FileInputStream(CONFIG)) {
            Properties propiedadesE = new Properties();
            propiedadesE.load(lecturaE);

            //leer propiedades de la configuracion
            puerto = Integer.parseInt(propiedadesE.getProperty("puerto"));
        } catch (IOException e) {
            puerto = 8000;
        }
    }

    //iniciamos el servidor.
    public void iniciarServidor() {
        recuperar_puerto_configurador();
        try {
            InetAddress ip = InetAddress.getLocalHost();
            System.out.println("ip servidor: " + ip.getHostAddress());
            controller.mostrar_otro(ip.getHostAddress(), "ip_servidor");
            controller.mostrar_otro(ip.getHostName(), "nombre_host");
        } catch (UnknownHostException e) {
            System.err.println("no puedo saber la direccion ip local " + e);
        }

        // abrimos un sockets derservidor TCP con el puerto configurado 
        try {
            socketServidor = new ServerSocket(puerto); //creando servidor
            System.out.println("puerto: " + socketServidor.getLocalPort());

        } catch (IOException e) {
            System.err.println("error al abrir el socket del servidor  " + e);
        }

        String parametro = "", tipo = "",modelo="";

        while (true) {
            try {
                //aceptamos la conexion del cliente
                controller.mostrar_otro("activo", "estado_servidor");
                socketCliente = socketServidor.accept();
                System.out.println("cliente aceptado: " + socketCliente.getLocalSocketAddress());
                 String ip_cliente = socketCliente.getLocalAddress().toString();
                controller.mostrar_otro("conectado", "estado_cliente");
                // System.out.println("cliente aceptado: " + socketCliente.getLocalAddress());
                flujoDeEntrada = new DataInputStream(socketCliente.getInputStream());  //flujo de entrada de datos 

                //recivimos los parametros enviados por el cliente 
                parametro = flujoDeEntrada.readUTF();
                tipo = flujoDeEntrada.readUTF();
                modelo = flujoDeEntrada.readUTF();

                //cerramos la los Streams  los flujos de coneccion
                flujoDeEntrada.close();
                socketCliente.close();

                System.out.println(parametro);
                teclado.escribir_mediante_scrip(parametro);
                controller.mostrar_resultado_tabla(parametro, ip_cliente,modelo);
            } catch (IOException e) {

                System.err.println("se ha producido la exepcion \n " + e);
            }
        }

    }

}
