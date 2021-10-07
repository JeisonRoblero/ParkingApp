package sample;

import java.util.Date;

public abstract class Vehiculo {
    /**
     * Variables privadas de la clase
     */
    private String placa;
    private Date fechaIngreso;
    private int noEspacio;

    /**
     * Vehiculo: Este constructor establece el valor predeterminado para cada variable, se envian por parametro
     * un String para la placa, un entero o int para la cantidad de espacios y un parametro
     * de tipo date para ingresar la fecha de ingreso.
     * @param placa
     * @param noEspacio
     * @param fechaIngreso
     */
    public Vehiculo(String placa, int noEspacio, Date fechaIngreso) {
        this.placa = placa;
        this.noEspacio = noEspacio;
        this.fechaIngreso = fechaIngreso;
    }

    /**
     * getPlaca: Metodo para obtener la placa del vehiculo, retorna el valor de placa
     * @return {descripcionDesalida}
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * setPlaca: Metodo set que establece el valor que tiene la placa, se envia por parametro un String
     * @param placa
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * getNoEspacio: Metodo get de tipo entero que nos devuelve el numero de espacios
     * @return {descripcionDesalida}
     */
    public int getNoEspacio() {return noEspacio;}

    /**
     * setNoEspacio: Metodo set que nos permite establecer la cantidad de espacios por medio de un parametro entero
     * @param noEspacio
     */
    public void setNoEspacio(int noEspacio) {this.noEspacio = noEspacio;}

    /**
     * getFechaIngreso: Metodo get que nos devuelve la fecha en que ingresó el vehiculo
     * @return {descripcionDesalida}
     */
    public Date getFechaIngreso() {return fechaIngreso;}

    /**
     * setFechaIngreso: Metodo set que nos permite establecer la fecha de ingreso del vehiculo por medio de un
     * parametro de tipo date
     * @param fechaIngreso
     */
    public void setFechaIngreso(Date fechaIngreso) {this.fechaIngreso = fechaIngreso;}

    /**
     * calcularMonto: Metodo abstracto double en donde pasamos por parametro el tiempo de tarifa
     * y el precio
     * @param tiempoTarifa
     * @param precio
     * @return {descripcionDesalida}
     */
    public abstract double calcularMonto(int tiempoTarifa, double precio);
}
