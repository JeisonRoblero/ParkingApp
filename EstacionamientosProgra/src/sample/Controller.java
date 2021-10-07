package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Cliente.Cliente;

import javax.rmi.CORBA.Util;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Controller {

    public static Double montoActual = new Double(0);
    public static ArrayList<Automovil> listaAutomovil = new ArrayList<>();
    public static ArrayList<Camion> listaCamion = new ArrayList<>();
    public static ArrayList<Motocicleta> listaMotocicleta = new ArrayList<>();

    static List<Integer> listaEspaciosA = new ArrayList<>();
    static List<Integer> listaEspaciosM = new ArrayList<>();
    static List<Integer> listaEspaciosC = new ArrayList<>();
    static String strDateFormat = "dd-MM-yyyy hh-mm-ss";
    static SimpleDateFormat formatter = new SimpleDateFormat(strDateFormat);
    Vehiculo vehiculoEgreso;
    String tipoVehiculoEgreso;
    Date fechaEgreso;
    public static double totalMonto;
    long tiempoTranscurrido;

    public static int autoE;
    public static int motoE;
    public static int camionE;

    @FXML
    Pane ingresoPane;
    @FXML
    Pane egresoPane;
    @FXML
    Pane configuracionPane;
    @FXML
    Pane homePane;
    @FXML
    TextField autoEspacios;
    @FXML
    TextField autoPrecio;
    @FXML
    TextField motoEspacios;
    @FXML
    TextField motoPrecio;
    @FXML
    TextField camionEspacios;
    @FXML
    TextField camionPrecio;
    @FXML
    TextField tiempoTarifa;
    @FXML
    Label labelErrorConfiguracion;

    @FXML
    Label homeLabel;

    @FXML
    ComboBox comboTipoVehiculo;
    @FXML
    TextField textFieldPlaca;
    @FXML
    Label labelErrorIngresoVehiculo;

    @FXML
    TextField textFieldPlacaEgreso;
    @FXML
    Label egresoLabel;
    @FXML
    Label labelErrorEgresoVehiculo;
    @FXML
    Button btnEgresarVehiculo;
    @FXML
    TextField textFieldNit;
    @FXML
    Button btnPagar;

    /**
     * Método para inicializar los hilos
     */
    public void initialize() {
       Thread thread = new Thread("Server Thread") {
           public void run(){
               Servidor.start(listaAutomovil, listaEspaciosA, listaMotocicleta, listaEspaciosM, listaCamion, listaEspaciosC);
           }
       };
       thread.start();
       comboTipoVehiculo.getItems().addAll("AUTOMOVIL", "MOTOCICLETA", "CAMION");

   }

    /**
     * salir: Método vinculado al botón salir de la interfaz, cierra y se sale del programa
     * @param actionEvent
     */
    public void salir(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

    /**
     * mostrarConfiguraciones: Primero limpia el panel de la interfaz, después hace visible el panel de configuraciones
     */
    public void mostrarConfiguraciones(){
        limpiarPane();
        configuracionPane.setVisible(true);
    }

    /**
     * mostrarIngreso: Limpia en la interfaz el panel anterior y muestra el panel de ingreso
     */
    public void mostrarIngreso(){
        limpiarPane();
        ingresoPane.setVisible(true);
    }

    /**
     * mostrarEgreso: Limpia en la interfaz el panel anterior y muestra el panel de egreso
     */
    public void mostrarEgreso(){
       limpiarPane();
        vehiculoEgreso = null;
        egresoPane.setVisible(true);
    }

    /**
     * mostrarInicio: Limpia en la interfaz el panel anterior y muestra el panel de inicio
     */
    public void mostrarInicio(){
        limpiarPane();
        inicioPane("Bienvenidos...");
    }

    /**
     * inicioPane: Limpia en la interfaz el panel anterior y muestra el inicio
     */
    public void inicioPane(String mensaje){
        limpiarPane();
        homeLabel.setText(mensaje);
        homePane.setVisible(true);
    }

    /**
     * limpiarPane: Limpia el panel estableciendo valores vacíos en cada campo y volviendolos invisibles
     */
    public void limpiarPane() {
        homeLabel.setText("");
        configuracionPane.setVisible(false);
        ingresoPane.setVisible(false);
        egresoPane.setVisible(false);
        homePane.setVisible(false);
    }

    /**
     * buscarVehiculo: Busca entre los vehículos ingresados el solicitado
     * por medio de su placa
     */
    public void buscarVehiculo() {
        String placa = textFieldPlacaEgreso.getText();
        labelErrorEgresoVehiculo.setText("...");
        labelErrorEgresoVehiculo.setVisible(false);
        btnEgresarVehiculo.setDisable(true);
        textFieldNit.setDisable(true);
        btnPagar.setVisible(false);
        btnEgresarVehiculo.setVisible(true);
        textFieldNit.setText("CF");
        egresoLabel.setText("...");
        tipoVehiculoEgreso = "";
        if (buscarAutomovil(placa) != null) {
            vehiculoEgreso = buscarAutomovil(placa);
            tipoVehiculoEgreso = "Automovil";
        } else if (buscarMotocicleta(placa) != null) {
            tipoVehiculoEgreso = "Motocicleta";
            vehiculoEgreso = buscarMotocicleta(placa);
        } else if (buscarCamion(placa) != null) {
            tipoVehiculoEgreso = "Camion";
            vehiculoEgreso = buscarCamion(placa);
        } else {
            labelErrorEgresoVehiculo.setText("No se encontro ningun vehiculo con la placa " + placa);
            labelErrorEgresoVehiculo.setVisible(true);
            return;
        }
        String mensaje = tipoVehiculoEgreso + " encontrado... \n"
                + "------------------------------ \n"
                + "Placa: " + vehiculoEgreso.getPlaca() + "\n"
                + "No. Espacio: " + Utils.obtenerMascara(vehiculoEgreso.getNoEspacio()) + "\n"
                + "Ingreso: " + formatter.format(vehiculoEgreso.getFechaIngreso()) + "\n";
        egresoLabel.setText(mensaje);
        btnEgresarVehiculo.setDisable(false);
    }

    /**
     * egresarVehiculo: Permite sacar un carro del parqueo, dependiendo del tipo de vehículo
     * calcula el tiempo transcurrido, redondea la cantidad y calcula el total a pagar
     */
    public void egresarVehiculo() {
        fechaEgreso = new Date();
        long tiempoTranscurridoMili = fechaEgreso.getTime() - vehiculoEgreso.getFechaIngreso().getTime();
        tiempoTranscurrido = tiempoTranscurridoMili / 1000;
        double periodos = 0;
        double redondeado = 0;
        totalMonto = 0;
       if (vehiculoEgreso != null) {
           switch (tipoVehiculoEgreso) {
               case "Automovil":
                   periodos = tiempoTranscurrido / TipoVehiculos.AUTOMOVIL.getTiempoTarifa();
                   redondeado = Math.floor(periodos);
                   totalMonto = vehiculoEgreso.calcularMonto(TipoVehiculos.AUTOMOVIL.getTiempoTarifa(), TipoVehiculos.AUTOMOVIL.getPrecio());

                   break;
               case "Motocicleta":
                   periodos = tiempoTranscurrido / TipoVehiculos.MOTOCICLETA.getTiempoTarifa();
                   redondeado = Math.floor(periodos);
                   totalMonto = vehiculoEgreso.calcularMonto(TipoVehiculos.MOTOCICLETA.getTiempoTarifa(),
                           TipoVehiculos.MOTOCICLETA.getPrecio());
                   break;
               case "Camion":
                   periodos = tiempoTranscurrido / TipoVehiculos.CAMION.getTiempoTarifa();
                   redondeado = Math.floor(periodos);
                   totalMonto = vehiculoEgreso.calcularMonto(TipoVehiculos.CAMION.getTiempoTarifa(),
                           TipoVehiculos.CAMION.getPrecio());

                   break;
           }
           String mensajePago = "Placa: " + vehiculoEgreso.getPlaca() + " | "
                   + "No. Espacio: " + Utils.obtenerMascara(vehiculoEgreso.getNoEspacio()) + "\n"
                   + "Ingreso: " + formatter.format(vehiculoEgreso.getFechaIngreso()) + "\n"
                   + "Egreso: " + formatter.format(fechaEgreso) + "\n"
                   + "Tiempo Total: " + tiempoTranscurrido + " segundos\n"
                   + "Periodos: " + redondeado + " \n"
                   + "Total a Pagar Q." + totalMonto;
           egresoLabel.setText(mensajePago);
           btnPagar.setVisible(true);
           btnEgresarVehiculo.setVisible(false);
           textFieldNit.setDisable(false);
           textFieldNit.requestFocus();
       } else {
           System.out.println("Ocurrio un error con el vehiculo encontrado");
       }
    }

    /**
     * pagarFactura: Calcula los espacios libres disponibles, quita el vehiculo de la lista de vehículos
     * del parqueo, posteriormente imprime un pdf detallando los datos del egreso.
     */
    public void pagarFactura(){
        String nit = textFieldNit.getText();
        labelErrorEgresoVehiculo.setText("...");
        labelErrorEgresoVehiculo.setVisible(false);
        if(Utils.validarNit(nit) || nit.equals("CF")) {
            switch (tipoVehiculoEgreso) {
                case "Automovil":
                    listaEspaciosA.add(vehiculoEgreso.getNoEspacio());
                    listaAutomovil.remove(vehiculoEgreso);
                    Utils.generarPdfEgreso(vehiculoEgreso.getPlaca(), Utils.obtenerMascara(vehiculoEgreso.getNoEspacio()), formatter.format(fechaEgreso),
                            totalMonto, TipoVehiculos.AUTOMOVIL.toString(), tiempoTranscurrido, nit);
                    break;
                case "Motocicleta":
                    listaEspaciosM.add(vehiculoEgreso.getNoEspacio());
                    listaMotocicleta.remove(vehiculoEgreso);
                    Utils.generarPdfEgreso(vehiculoEgreso.getPlaca(), Utils.obtenerMascara(vehiculoEgreso.getNoEspacio()), formatter.format(fechaEgreso),
                            totalMonto, TipoVehiculos.MOTOCICLETA.toString(), tiempoTranscurrido, nit);
                    break;
                case "Camion":
                    listaEspaciosC.add(vehiculoEgreso.getNoEspacio());
                    listaCamion.remove(vehiculoEgreso);
                    Utils.generarPdfEgreso(vehiculoEgreso.getPlaca(), Utils.obtenerMascara(vehiculoEgreso.getNoEspacio()), formatter.format(fechaEgreso),
                            totalMonto, TipoVehiculos.CAMION.toString(), tiempoTranscurrido, nit);
                    break;
            }
            montoActual = montoActual + totalMonto;
            Servidor.montoActual = montoActual;
            String mensaje = "Su "+tipoVehiculoEgreso+" con placas " + vehiculoEgreso.getPlaca() + " fue egresado exitosamente.\n" +
                    "El monto actual recaudado es de Q."+montoActual;
            inicioPane(mensaje);
            // resetear formulario
            labelErrorEgresoVehiculo.setText("...");
            labelErrorEgresoVehiculo.setVisible(false);
            btnEgresarVehiculo.setDisable(true);
            textFieldNit.setDisable(true);
            btnPagar.setVisible(false);
            btnEgresarVehiculo.setVisible(true);
            textFieldNit.setText("CF");
            egresoLabel.setText("...");
            tipoVehiculoEgreso = "";
        }else{
            labelErrorEgresoVehiculo.setText("El nit  " + nit + " no es valido");
            labelErrorEgresoVehiculo.setVisible(true);
        }
    }

    /**
     * ingresarVehiculo: Método para ingresar un vehículo al parqueo, se valida que la placa
     * esté ingresada correctamente
     */
    public void ingresarVehiculo() {
        String tipoVehiculo = "";
        int cantidadEspaciosDisponibles;
        int indexRandom;
        int espacioTomar;
        labelErrorIngresoVehiculo.setVisible(false);
        labelErrorIngresoVehiculo.setText("...");
        try {
            tipoVehiculo = comboTipoVehiculo.getValue().toString();
        }catch (Exception e) {
            labelErrorIngresoVehiculo.setVisible(true);
            labelErrorIngresoVehiculo.setText("Seleccione un tipo de vehiculo");
        }
        String placa = textFieldPlaca.getText();
        if(!Utils.validarPlaca(placa)){
            labelErrorIngresoVehiculo.setVisible(true);
            labelErrorIngresoVehiculo.setText("Placa " + placa + " Invalida");
        } else if (tipoVehiculo.equals("")) {
            labelErrorIngresoVehiculo.setVisible(true);
            labelErrorIngresoVehiculo.setText("Seleccione un tipo de vehiculo");
        } else {
            switch (tipoVehiculo) {
                /**
                 * Se compureba en cada tipo de vehículo si aún hay espacios disponibles para ingresar
                 * un nuevo vehículo
                 */
                case "AUTOMOVIL":
                    if (listaEspaciosA.size() > 0) {
                        cantidadEspaciosDisponibles = listaEspaciosA.size();
                        ThreadLocalRandom tlr = ThreadLocalRandom.current();
                        indexRandom = tlr.nextInt(0, cantidadEspaciosDisponibles);
                        espacioTomar = listaEspaciosA.get(indexRandom);
                        Date fecha = new Date();
                        Automovil auto = new Automovil(placa, espacioTomar, fecha);
                        Utils.generarPdfIngreso(placa, Utils.obtenerMascara(espacioTomar), formatter.format(fecha), TipoVehiculos.AUTOMOVIL.getPrecio(), "Automovil");
                        listaEspaciosA.remove(indexRandom);
                        listaAutomovil.add(auto);
                        String mensaje = "Su automovil con placas "+ placa + " fue ingresado correctamente en el espacio " + Utils.obtenerMascara(espacioTomar) +
                                " a la fecha "+ formatter.format(fecha);
                        inicioPane(mensaje);
                        textFieldPlaca.setText("");
                    } else {
                        labelErrorIngresoVehiculo.setVisible(true);
                        labelErrorIngresoVehiculo.setText("No hay espacios en el parqueo de Automoviles");
                    }
                    break;
                case "MOTOCICLETA":
                    if (listaEspaciosM.size() > 0) {
                        cantidadEspaciosDisponibles = listaEspaciosM.size();
                        ThreadLocalRandom tlr = ThreadLocalRandom.current();
                        indexRandom = tlr.nextInt(0, cantidadEspaciosDisponibles);
                        espacioTomar = listaEspaciosM.get(indexRandom);
                        Date fecha = new Date();
                        Motocicleta moto = new Motocicleta(placa, espacioTomar, fecha);
                        Utils.generarPdfIngreso(placa, Utils.obtenerMascara(espacioTomar), formatter.format(fecha), TipoVehiculos.MOTOCICLETA.getPrecio(), "Motocicleta");
                        listaEspaciosM.remove(indexRandom);
                        listaMotocicleta.add(moto);
                        inicioPane("Su motocicleta con placas "+ placa + " fue ingresado correctamente en el espacio " + Utils.obtenerMascara(espacioTomar) +
                                " a la fecha "+ formatter.format(fecha));
                        textFieldPlaca.setText("");
                    } else {
                        labelErrorIngresoVehiculo.setVisible(true);
                        labelErrorIngresoVehiculo.setText("No hay espacios en el parqueo de Motocicletas");
                    }
                    break;
                case "CAMION":
                    if (listaEspaciosC.size() > 0) {
                        cantidadEspaciosDisponibles = listaEspaciosC.size();
                        ThreadLocalRandom tlr = ThreadLocalRandom.current();
                        indexRandom = tlr.nextInt(0, cantidadEspaciosDisponibles);
                        espacioTomar = listaEspaciosC.get(indexRandom);
                        Date fecha = new Date();
                        Camion camion = new Camion(placa, espacioTomar, fecha);
                        Utils.generarPdfIngreso(placa, Utils.obtenerMascara(espacioTomar), formatter.format(fecha), TipoVehiculos.CAMION.getPrecio(), "Motocicleta");
                        listaEspaciosC.remove(indexRandom);
                        listaCamion.add(camion);
                        inicioPane("Su camion con placas "+ placa + " fue ingresado correctamente en el espacio " + Utils.obtenerMascara(espacioTomar) +
                                " a la fecha "+ formatter.format(fecha));
                        textFieldPlaca.setText("");
                    } else {
                        labelErrorIngresoVehiculo.setVisible(true);
                        labelErrorIngresoVehiculo.setText("No hay espacios en el parqueo de Camiones");
                    }
                    break;
            }
        }
    }

    /**
     * guardarConfiguracion: Guarda en variables los datos como lo son:
     * los espacios disponibles para cada vehículo, el precio para cada tipo de vehículo
     * y el periodo de tiempo en que se cobrará
     */
    public void guardarConfiguracion() {
        labelErrorConfiguracion.setVisible(false);
        labelErrorConfiguracion.setText("...");
       try {
           autoE = Integer.parseInt(autoEspacios.getText());
           double autoP = Double.parseDouble(autoPrecio.getText());
           motoE = Integer.parseInt(motoEspacios.getText());
           double motoP = Double.parseDouble(motoPrecio.getText());
           camionE = Integer.parseInt(camionEspacios.getText());
           double camionP = Double.parseDouble(camionPrecio.getText());
           int tiempoT = Integer.parseInt(tiempoTarifa.getText());
           Utils.actualizarConfiguraciones(autoE, autoP, motoE, motoP, camionE, camionP, tiempoT);
           generarEespacios(autoE, listaEspaciosA);
           generarEespacios(motoE, listaEspaciosM);
           generarEespacios(camionE, listaEspaciosC);
           String mensaje = "Se ha actualizado la configuracion \n " +
                   "Automovil\n\t- Espacios: " + TipoVehiculos.AUTOMOVIL.getEspacios()
                   + "\n\t- Precio: Q." + TipoVehiculos.AUTOMOVIL.getPrecio() + "\n\t- Tiempo Tarifa: " + TipoVehiculos.AUTOMOVIL.getTiempoTarifa() + "seg.\n"
                   + "Motocicleta\n\t- Espacios: " + TipoVehiculos.MOTOCICLETA.getEspacios()
                   + "\n\t- Precio: Q." + TipoVehiculos.MOTOCICLETA.getPrecio() + "\n\t- Tiempo Tarifa: " + TipoVehiculos.MOTOCICLETA.getTiempoTarifa() + "seg.\n"
                   + "Camion\n\t- Espacios: " + TipoVehiculos.CAMION.getEspacios()
                   + "\n\t- Precio: Q." + TipoVehiculos.CAMION.getPrecio() + "\n\t -Tiempo Tarifa: " + TipoVehiculos.CAMION.getTiempoTarifa() + "seg.\n";
           inicioPane(mensaje);
       }catch (Exception e) {
           labelErrorConfiguracion.setVisible(true);
           labelErrorConfiguracion.setText("Verificar valores ingresados.");
       }
    }

    /**
     * buscarAutomovil: busca el automóvil requerido por medio de su placa.
     * Realiza una comparación entre el valor ingresado por el usuario y
     * la lista de vehículos que se encuentran dentro del parqueo.
     * Retorna los datos del automóvil encontrado.
     * @param placa
     * @return
     */
    public static Automovil buscarAutomovil(String placa){
        Automovil automovil = null;
        for (Automovil auto : listaAutomovil){
            if(auto.getPlaca().equals(placa)){
                automovil = auto;
                break;
            }
        }
        return automovil;
    }

    /**
     * buscarMotocicleta: busca la motocicleta requerida por medio de su placa.
     * Realiza una comparación entre el valor ingresado por el usuario y
     * la lista de motos que se encuentran dentro del parqueo.
     * Retorna los datos de la motocicleta encontrada.
     * @param placa
     * @return
     */
    public static Motocicleta buscarMotocicleta(String placa){
        Motocicleta motocicleta = null;
        for (Motocicleta moto : listaMotocicleta){
            if(moto.getPlaca().equals(placa)){
                motocicleta = moto;
                break;
            }
        }
        return motocicleta;
    }

    /**
     * buscarCamion: busca el camión requerido por medio de su placa.
     * Realiza una comparación entre el valor ingresado por el usuario y
     * la lista de camiones que se encuentran dentro del parqueo.
     * Retorna los datos del camión encontrado.
     * @param placa
     * @return
     */
    public static Camion buscarCamion(String placa){
        Camion camionB = null;
        for (Camion camion : listaCamion){
            if(camion.getPlaca().equals(placa)){
                camionB = camion;
                break;
            }
        }
        return camionB;
    }

    /**
     * generarEespacios: Permite generar los espacios correspondientes y los agrega a una lista.
     * @param cantidadEspacios
     * @param lista
     */
    public static void generarEespacios(int cantidadEspacios, List<Integer> lista){
        lista.clear();
        for (int i = 1; i <= cantidadEspacios; i++){
            lista.add(i);
        }
    }
}
