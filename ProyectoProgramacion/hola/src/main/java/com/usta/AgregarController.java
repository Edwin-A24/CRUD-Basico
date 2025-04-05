package com.usta;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AgregarController {

        @FXML
    private TextField nombreTxt;

    @FXML
    private TextField animalTxt;

    @FXML
    private ComboBox<String> sexoCBX;

    @FXML
    private DatePicker nacimientoDaTePk;
    

    public void ventana(String nombre){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito!");
        alert.setHeaderText("La mascota "+nombre+" se ha agregado correctamente");
        alert.setContentText("Puedes verificar en la sección de mirar.");

        // Mostrar la ventana emergente y esperar a que el usuario la cierre
        alert.showAndWait();
    }

    public void initialize(){
       ObservableList<String> sexoOL = FXCollections.observableArrayList(
            "Macho","Hembra"
        );
        sexoCBX.setItems(sexoOL);
    }


    @FXML
    public void agregarLibro() {
        String nombre = nombreTxt.getText();
        String animal = animalTxt.getText();
        String sexo = sexoCBX.getValue();
        String nacimiento = nacimientoDaTePk.getValue().toString();

        Mascotas nuevaMascota = new Mascotas(nombre, animal, sexo, nacimiento);
        
        
        String directoryPath = "src\\main\\resources\\com\\usta\\data";
        String filePath = directoryPath + File.separator + "mascotas.txt";

        
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(nuevaMascota.toString());
            writer.newLine();
            ventana(nombre);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }
    @FXML
    private void switchToMirar() throws IOException {
        App.setRoot("mirar");
    }
    @FXML
    private void switchToEditar() throws IOException {
        App.setRoot("editar");
    }
    @FXML
    private void switchToEliminar() throws IOException {
        App.setRoot("eliminar");
    }

}