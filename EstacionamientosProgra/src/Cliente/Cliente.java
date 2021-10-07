package Cliente;
import java.io.*;
import java.net.*;

public class Cliente {
    /**
     * Variables de clase del Cliente, son estáticas
     */
    public static final int PUERTO = 4182;
    public static final String HOST = "localhost";
    public static Socket cs;
    public static DataOutputStream salidaServidor;

    public static void startClient(){

        try{
            /**
             * Se conecta el cliente con el servidor
             */
            cs = new Socket(HOST, PUERTO);
            System.out.println("---- Conexion Establecida ----");

            /**
             * Lee el mensaje del servidor
             */
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(cs.getInputStream()));

            System.out.println("Recibiendo Data: ");
            String mensajeRecibido = entrada.readLine();
            /**
             * Dividimos el mensaje que recibimos del servidor
             */
            String[] mensajeSplited = mensajeRecibido.split(";");
            /**
             * imprimimos en pantalla las líneas
             */
            for(int i = 0; i < mensajeSplited.length; i++){
                System.out.println(mensajeSplited[i]);
            }

            /**
             * Se cierra la entrada y la conexión
             */
            entrada.close();
            cs.close();
            System.out.println("---- Cerrando Conexion ----");

        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     * main: Método principal para ejecutar el cliente
     * @param args
     */
    public static void main(String[] args){
        startClient();
    }


}