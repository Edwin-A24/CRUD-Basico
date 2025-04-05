package com.usta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class EliminarController {

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
    private TableColumn<Mascotas, String> sexCol;
    @FXML
    private TableColumn<Mascotas, String> nacimientoCol;

    private Mascotas mascotasAEliminar;

    public void initialize() {
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        animalCol.setCellValueFactory(new PropertyValueFactory<>("animal"));
        sexCol.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        nacimientoCol.setCellValueFactory(new PropertyValueFactory<>("nacimiento"));
        cargarMascota();

        mascotasOL.setAll(mascotasList);
        hakunaTable.setItems(mascotasOL);

    }

    public void setMascotasAEditar() {
        this.mascotasAEliminar = hakunaTable.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void ventanaEliminar() throws IOException {
        setMascotasAEditar();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Estás seguro de que deseas eliminar la mascota " + mascotasAEliminar.getNombre() + "?");
        alert.setContentText("Esta acción no se puede deshacer.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Aquí puedes incluir la lógica para eliminar el libro si el usuario confirma
            // Por ejemplo, puedes llamar a un método para eliminar el libro de la lista y
            // actualizar la tabla
            eliminarMascota();
        }
    }
    public void ventanaExito(String nombre) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito!");
        alert.setHeaderText(nombre);
        alert.setContentText("Puedes verificar en la sección de mirar.");

        // Mostrar la ventana emergente y esperar a que el usuario la cierre
        alert.showAndWait();
        mascotasOL.setAll(mascotasList);
        hakunaTable.setItems(mascotasOL);
    }

    public void eliminarMascota() throws IOException {
        // Archivo original y archivo temporal para escribir los cambios
       
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + ".tmp"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Dividir la línea en partes
                String[] parts = line.split("\\|");
                // Si el ISBN de la línea coincide con el ISBN del libro a eliminar, no escribas
                // esa línea en el archivo temporal
                if (parts.length == 4 && parts[3].equals(mascotasAEliminar.getNacimiento())) {
                    continue;
                }
                // Escribe la línea en el archivo temporal si no coincide con el ISBN del libro
                // a eliminar
                writer.write(line);
                writer.newLine();
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
                ventanaExito("La eliminación de la mascota " + mascotasAEliminar.getNombre() + " se ha realizado correctamente.");
                switchToMenu();
            } else {
                System.out.println("No se pudo renombrar el archivo temporal.");
            }
        } else {
            System.out.println("No se pudo eliminar el archivo original.");
        }
      
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


    @FXML
    private void switchToAgregar() throws IOException {
        App.setRoot("agregar");
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
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }

}