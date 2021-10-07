package sample;

public enum TipoVehiculos {
    /**TipoVehiculo
     *
     * es una clase que da un valor por de fould a las variantes de vehiculos.
     *
     * @author Merleen Guardado y Jeison Roblero
     * @version 31-08-2021
     */

    AUTOMOVIL(0,0.0, 0),
    MOTOCICLETA(0,0.0, 0),
    CAMION(0,0.0, 0);

    /**
     * Entero que recibe la cantidad de especios para cada vehiuculo.
     */
    private int espacios;
    /**
     * Entero que recibe valor del tiempo o periodo.
     */
    private int tiempoTarifa;
    /**
     * Entero que asigna valor de la precio dependiendo el tamano.
     */
    private double precio;

    /**
     * TipoVehiculos: dependiendo el tipo de vehiculo seleccionado asigna el valor de precio y espacio.
     */
    private TipoVehiculos(int espacios, double precio, int tiempoTarifa) {
        this.espacios = espacios;
        this.precio = precio;
        this.tiempoTarifa = tiempoTarifa;
    }
    /**
     * getEspacios: Muestra el valor asignado de espacios en el estacionamiento.
     *
     */
    public int getEspacios() {
        return espacios;
    }
    /**
     * setEspacios: asigna el valor a la variable espacios dependiendo lo que el usuario ingrese.
     *
     */

    public void setEspacios(int espacios) {
        this.espacios = espacios;
    }
    /**
     * getPrecio: Muestra el valor asignado de precios en el estacionamiento.
     *
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * setEspacios: asigna el valor a la variable precio dependiendo lo que el usuario ingrese.
     *
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    /**
     * getTiempoTarifa: Muestra el valor asignado del periodo por tarifa.
     *
     */
    public int getTiempoTarifa() {
        return tiempoTarifa;
    }

    /**
     * setTiempoTarifa: asigna el valor a la variable del periodo de ka tarifa.
     *
     */
    public void setTiempoTarifa(int tiempoTarifa) {
        this.tiempoTarifa = tiempoTarifa;
    }
}