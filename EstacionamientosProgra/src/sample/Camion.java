package sample;

import java.util.Date;

public class Camion extends Vehiculo{

    /**
     * Camion: constructor el cual recibe parametros que hereda de la clase padre por medio de la
     * palabra reservada 'super'
     * @param placa
     * @param noEspacio
     * @param fechaIngreso
     */
    public Camion(String placa, int noEspacio, Date fechaIngreso) {
        super(placa, noEspacio, fechaIngreso);
    }

    /**
     * calcularMonto: calcula el monto a pagar en este caso del vehiculo camion, recibe como parametro
     * el tiempo de tarifa y el precio.
     * @param tiempoTarifa
     * @param precio
     * @return {descripcionDesalida}
     */
    @Override
    public double calcularMonto(int tiempoTarifa, double precio) {
        Date fecha = new Date();
        long tiempoTranscurridoMili = fecha.getTime() - getFechaIngreso().getTime();
        long tiempoTranscurrido = tiempoTranscurridoMili / 1000;
        double periodos = tiempoTranscurrido / tiempoTarifa;
        double redondeado = Math.floor(periodos);
        double montoTotal = (redondeado * precio);
        double recargo = 0;
        if(periodos > 1) {
            recargo = (periodos - 1) * 5;
        }
        montoTotal += recargo;
        return montoTotal;
    }

}
