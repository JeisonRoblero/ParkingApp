package sample;

import java.util.Date;

public class Motocicleta extends Vehiculo{

    /**
     * Motocicleta: constructor utilizando las variables de su clase padre, llamandolas por medio de
     * la palabra reservada 'super'
     * @param placa
     * @param noEspacio
     * @param fechaIngreso
     */
    public Motocicleta(String placa, int noEspacio, Date fechaIngreso) {
        super(placa, noEspacio, fechaIngreso);
    }

    /**
     * calcularMonto: calcula el monto a pagar pasandole como parametro un entero para el tiempo de tarifa
     * y un double o decimal para el precio. Este metodo se sobreescribe de su clase padre.
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
        montoTotal = montoTotal - (montoTotal*0.1);
        return montoTotal;
    }
}
