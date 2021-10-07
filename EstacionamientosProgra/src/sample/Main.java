package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    /**
     * Método que lanza o ejecuta el diseño gráfico del programa
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Estacionamientos Progra");
        primaryStage.setScene(new Scene(root, 466, 429));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}



