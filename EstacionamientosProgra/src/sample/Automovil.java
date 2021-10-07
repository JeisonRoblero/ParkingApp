package sample; /**
 * Automovil
 *Clase que ereda todos los atributos del la clase Vehiculo sin ninguna variacion en sus metodos.
 * @author Merleen Guardado
 * @version 1-09-2021
 */

import java.util.Date;

public class Automovil extends Vehiculo{

    public Automovil(String placa, int noEspacio, Date fechaIngreso) {
        super(placa, noEspacio, fechaIngreso);
    }

    /**
     * calcularMonto: Calcula el monto que el cliente debe pagar segun el tiepo que estubo en el estacionamiento.
     * @param tiempoTarifa: este es la variante donde el usuario asigna el valor del periodo y el periodo mismo.
     * @param precio:
     * @return {descripcionDesalida}
     */

    @Override
    public double calcularMonto(int tiempoTarifa, double precio) {
        Date fecha = new Date();
        long tiempoTranscurridoMili = fecha.getTime() - getFechaIngreso().getTime();
        long tiempoTranscurrido = tiempoTranscurridoMili / 1000;
        double periodos = tiempoTranscurrido / tiempoTarifa;
        double redondeado = Math.floor(periodos);
        return redondeado * precio;
    }

}

