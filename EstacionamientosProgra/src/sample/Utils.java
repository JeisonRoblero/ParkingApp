package sample;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Utils: es donde se encuentran los mtodos para poder validar informacion y recibirla de la manera que se solicita.
 * @author Merleen Guardado y Jeison Roblero
 * @version 31-08-2021
 */

public class Utils {

    public static void actualizarConfiguraciones(int autoE, double autoP, int motoE, double motoP,
                                                 int camionE, double camionP, int tiempoTarifa){
        TipoVehiculos.AUTOMOVIL.setEspacios(autoE);
        TipoVehiculos.AUTOMOVIL.setPrecio(autoP);
        TipoVehiculos.MOTOCICLETA.setEspacios(motoE);
        TipoVehiculos.MOTOCICLETA.setPrecio(motoP);
        TipoVehiculos.CAMION.setEspacios(camionE);
        TipoVehiculos.CAMION.setPrecio(camionP);
        TipoVehiculos.CAMION.setTiempoTarifa(tiempoTarifa);
        TipoVehiculos.AUTOMOVIL.setTiempoTarifa(tiempoTarifa);
        TipoVehiculos.MOTOCICLETA.setTiempoTarifa(tiempoTarifa);
    }

    public static boolean validarPlaca(String placa) {
        String regexPlaca = "(M|P|C)+[0-9]{3}+[BCDFGHJKLMNOPQRSTVWXYZ]{3}";
        return placa.matches(regexPlaca);
    }

    public static boolean validarNit(String nit) {
        String regexNit = "[0-9]{8}";
        return nit.matches(regexNit);
    }

    /**
     * generarPdfIngreso: Genera un pdf con toda la informacion del ingreso del vehiculo, como lo es
     * la placa, el espacio de parqueo, la fecha y hora de ingreso, la tarifa y el tipo de vehiculo
     * @param placa
     * @param espacioParqueo
     * @param fechaHora
     * @param tarifa
     * @param tipoVehiculo
     */
    public static void generarPdfIngreso(String placa, String espacioParqueo, String fechaHora, double tarifa, String tipoVehiculo) {
        Document documento = new Document();
        try {
            String fileName = "PDF/Ticket-" + placa + "_" + espacioParqueo + "_" + tipoVehiculo + "_" + fechaHora + ".pdf";
            FileOutputStream ficheroPdf = new FileOutputStream(fileName);

            // Se asocia el documento al OutputStream y se indica que el espaciado entre
            // lineas sera de 20. Esta llamada debe hacerse antes de abrir el documento
            PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);

            documento.open();
            documento.add(new Paragraph("Ticket - Gestor de Parqueos",
                    FontFactory.getFont("arial",   // fuente
                            20,                        // tamaño
                            Font.ITALIC,                   // estilo
                            BaseColor.RED)));             // color
            PdfPTable tabla = new PdfPTable(2);
            tabla.addCell("Fecha");
            tabla.addCell(fechaHora);
            tabla.addCell("Placa");
            tabla.addCell(placa);
            tabla.addCell("No. Estacionamiento");
            tabla.addCell(espacioParqueo);
            tabla.addCell("Tipo de Vehiculo");
            tabla.addCell(tipoVehiculo);
            tabla.addCell("Tarifa por Segundo");
            tabla.addCell("Q. " + tarifa);
            documento.add(tabla);
            documento.add(new Paragraph("Nota: Por favor no pierda este ticket."));
            documento.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * generarPdfEgreso: Genera un pdf el cual contiene toda la información de salida como lo es el
     * numero de placa, el espacio del parqueo, la fecha y hora, el monto a pagar, el tipo de vehiculo,
     * el tiempo total y el numero de nit de quien fue registrado.
     * @param placa
     * @param espacioParqueo
     * @param fechaHora
     * @param monto
     * @param tipoVehiculo
     * @param totalTiempo
     * @param nit
     */
    public static void generarPdfEgreso(String placa, String espacioParqueo, String fechaHora, double monto, String tipoVehiculo, long totalTiempo, String nit) {
        Document documento = new Document();
        try {
            String fileName = "PDF/Factura-" + placa + "_" + espacioParqueo + "_" + tipoVehiculo + "_" + fechaHora + ".pdf";
            FileOutputStream ficheroPdf = new FileOutputStream(fileName);

            // Se asocia el documento al OutputStream y se indica que el espaciado entre
            // lineas sera de 20. Esta llamada debe hacerse antes de abrir el documento
            PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);

            documento.open();
            documento.add(new Paragraph("Factura - Gestor de Parqueos",
                    FontFactory.getFont("arial",   // fuente
                            20,                        // tamaño
                            Font.ITALIC,                   // estilo
                            BaseColor.BLUE)));             // color
            PdfPTable tabla = new PdfPTable(2);
            tabla.addCell("Fecha");
            tabla.addCell(fechaHora);
            tabla.addCell("NIT");
            tabla.addCell(nit);
            tabla.addCell("Placa");
            tabla.addCell(placa);
            tabla.addCell("No. Estacionamiento");
            tabla.addCell(espacioParqueo);
            tabla.addCell("Tipo de Vehiculo");
            tabla.addCell(tipoVehiculo);
            tabla.addCell("Tiempo");
            tabla.addCell(String.valueOf(totalTiempo));
            tabla.addCell("Total Pagado");
            tabla.addCell("Q. " + monto);
            documento.add(tabla);
            documento.add(new Paragraph("Gracias por visitarnos."));
            documento.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * obtenerMascara: le da el formato ### al numero, es decir, si ingresa '1' devolverá 0001, si ingresa '10' devolverá 0010
     * y así sucesivamente.
     * @param numero
     * @return {descripcionDesalida}
     */
    public static String obtenerMascara(int numero){
        if(numero < 10) {
            return "000" + numero;
        } else if (numero < 100) {
            return "00" + numero;
        } else if (numero < 1000) {
            return "0" + numero;
        } else {
            return "" + numero;
        }
    }
}
