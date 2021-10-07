package sample;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Servidor{
    /**
     * Variables de clase del Servidor, son estáticas
     */
    public static final int PUERTO=4182;
    public static ServerSocket ss;
    public static Socket cs;
    public static DataOutputStream salidaCliente;
    public static Double montoActual;

    /**
     * startServer: Método por medio del cual se crea y envía los datos al cliente
     * Se envían por parámetro las listas que se utilizarán para enviar al cliente
     * lo solicitado.
     * @param listaAutomovil
     * @param listaEspaciosA
     * @param listaMotocicleta
     * @param listaEspaciosM
     * @param listaCamion
     * @param listaEspaciosC
     * @throws Exception
     */
    public static void startServer(ArrayList<Automovil> listaAutomovil, List<Integer> listaEspaciosA,
                                   ArrayList<Motocicleta> listaMotocicleta, List<Integer> listaEspaciosM,
                                   ArrayList<Camion> listaCamion, List<Integer> listaEspaciosC) throws Exception{
        /**
         * Crear el socket del servidor y lo dejamos escuchando
         */
        ss = new ServerSocket(PUERTO);
        cs = new Socket();
        for(int i = 0; i < 10; i++) {
            System.out.println("---- Esperando Nueva Solicitud ----");
            cs = ss.accept();
            System.out.println("---- Solicitud Recibida ----");

            /**
             * envío de datos al cliente
             */
            salidaCliente = new DataOutputStream(cs.getOutputStream());

            /**
             * La variabe mensaje almacenará lo que se le enviará al cliente
             */
            String mensaje = "**--** Vehiculos **--** ;" +
                    "Espacios Libres: " + listaEspaciosA.size() + ";" +
                    "Espacios Ocupados: " + listaAutomovil.size() + ";" +
                    "--------------------------------------- ;" +
                    "**--** Motocicletas **--** ;" +
                    "Espacios Libres: " + listaEspaciosM.size() + ";" +
                    "Espacios Ocupados: " + listaMotocicleta.size() + ";"+
                    "--------------------------------------- ;" +
                    "**--** Camiones **--** ;" +
                    "Espacios Libres: " + listaEspaciosC.size() + ";" +
                    "Espacios Ocupados: " + listaCamion.size() + ";" +
                    "--------------------------------------- ;" +
                    "- " +
                    "Recaudado hasta el Momento: Q." + montoActual + ";"
                    + "---------------------------------------\n";

            salidaCliente.writeUTF(mensaje);
            System.out.println("---- Mensaje Enviado ----");
        }
        //se cierra la comunicacion
        System.out.println("---- Cerrando Servidor ----");
        ss.close();
    }

    /**
     * start: Método para iniciar el servidor, se envían los parámetros necesarios para que se
     * le puedan pasar al cliente.
     * @param listaAutomovil
     * @param listaEspaciosA
     * @param listaMotocicleta
     * @param listaEspaciosM
     * @param listaCamion
     * @param listaEspaciosC
     */
    public static void start(ArrayList<Automovil> listaAutomovil, List<Integer> listaEspaciosA,
                             ArrayList<Motocicleta> listaMotocicleta, List<Integer> listaEspaciosM,
                             ArrayList<Camion> listaCamion, List<Integer> listaEspaciosC) {
        /**
         * Usamos try - catch para el manejo de excepciones
         */
        try{
            /**
             * Trasladamos los parámetros al método startServer
             */
            startServer( listaAutomovil, listaEspaciosA, listaMotocicleta, listaEspaciosM, listaCamion, listaEspaciosC);
        }catch(Exception e){
            System.out.println("Error:"+e.getMessage());
        }
    }


}