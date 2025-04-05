package com.usta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class EditarController {

        private String filePath = "src/main/resources/com/usta/data/mascotas.txt";

    public ObservableList<Mascotas> mascotasOL = FXCollections.observableArrayList();
    public List<Mascotas> mascotasList = new ArrayList<>();

    @FXML
    private TableView<Mascotas> hakunaTable;
    @FXML
    private TableColumn<Mascotas, String> nombreCol;
    @FXML
    private TableColumn<Mascotas, String> animalCol;
    @FXML
    private TableColumn<Mascotas, String> sexoCol;
    @FXML
    private TableColumn<Mascotas, String> nacimientoCol;

    
    @FXML
    private TextField nombreTxt;

    @FXML
    private TextField animalTxt;

    @FXML
    private ComboBox<String> sexoCBX;

    @FXML
    private DatePicker nacimientoDaTePk;

    private Mascotas mascotasAEditar;

    public void ventana(String nombre) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito!");
        alert.setHeaderText("La mascota " + nombre + " se ha agregado correctamente");
        alert.setContentText("Puedes verificar en la sección de mirar.");

        // Mostrar la ventana emergente y esperar a que el usuario la cierre
        alert.showAndWait();
        mascotasOL.setAll(mascotasList);
        hakunaTable.setItems(mascotasOL);
    }

    public void initialize() {
        ObservableList<String> sexoOL = FXCollections.observableArrayList(
                "Macho","Hembra");
        sexoCBX.setItems(sexoOL);

        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        animalCol.setCellValueFactory(new PropertyValueFactory<>("animal"));
        sexoCol.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        nacimientoCol.setCellValueFactory(new PropertyValueFactory<>("nacimiento"));
        cargarMascota();

        mascotasOL.setAll(mascotasList);
        hakunaTable.setItems(mascotasOL);
    }

    private void cargarMascota() {
        String directoryPath = "src\\main\\resources\\com\\usta\\data";
        String filePath = directoryPath + File.separator + "mascotas.txt";
        File file = new File(filePath);

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length == 4) {
                        Mascotas mascotas = new Mascotas(parts[0], parts[1], parts[2], parts[3]);
                        mascotasList.add(mascotas);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not found: " + filePath);
        }
    }


    public void setMascotasAEditar() {
        this.mascotasAEditar = hakunaTable.getSelectionModel().getSelectedItem();
        cargarDatosMascotas();
    }

    private void cargarDatosMascotas() {
        if (mascotasAEditar != null) {
            nombreTxt.setText(mascotasAEditar.getNombre());
            animalTxt.setText(mascotasAEditar.getAnimal());
            sexoCBX.setValue(mascotasAEditar.getSexo());
            LocalDate localDate = LocalDate.parse(mascotasAEditar.getNacimiento().toString());
            nacimientoDaTePk.setValue(localDate);

        }
    }

    @FXML
    public void editarMascotas() throws IOException {
        if (mascotasAEditar != null) {
            // Actualizar los datos del libro con los valores de los campos
            mascotasAEditar.setNombre(nombreTxt.getText());
            mascotasAEditar.setAnimal(animalTxt.getText());
            mascotasAEditar.setSexo(sexoCBX.getValue());
            LocalDate localDate = LocalDate.parse(mascotasAEditar.getNacimiento().toString());
            nacimientoDaTePk.setValue(localDate);

            // Guardar los cambios en el archivo
            actualizarArchivo();

        }
        switchToMenu();
    }

    private void actualizarArchivo() {

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + ".tmp"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4 && parts[3].equals(mascotasAEditar.getNacimiento())) {
                    writer.write(mascotasAEditar.getNombre() + "|" + mascotasAEditar.getAnimal() + "|" +
                            mascotasAEditar.getSexo() + "|" + mascotasAEditar.getNacimiento());
                    writer.newLine();
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        
        // Reemplazar el archivo original con el temporal
        File file = new File(filePath);
        File tempFile = new File(filePath + ".tmp");
        if (file.delete()) {
            // Intentar renombrar el archivo temporal
            if (tempFile.renameTo(file)) {
                // Mostrar ventana de éxito
                ventana("La edición de la mascota " + mascotasAEditar.getNombre() + " se ha guardado correctamente.");
            } else {
                System.out.println("No se pudo renombrar el archivo temporal.");
            }
        } else {
            System.out.println("No se pudo eliminar el archivo original.");
        }
        if (tempFile.renameTo(file)) {
            // Mostrar ventana de éxito
            ventana("La edición de la mascota " + mascotasAEditar.getNombre() + " se ha guardado correctamente.");
        } else {
            System.out.println("No se pudo reemplazar el archivo original con el temporal.");
        }
    }


    @FXML
    private void switchToAgregar() throws IOException {
        App.setRoot("agregar");
    }
    @FXML
    private void switchToMirar() throws IOException {
        App.setRoot("mirar");
    }
    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }
    @FXML
    private void switchToEliminar() throws IOException {
        App.setRoot("eliminar");
    }

}