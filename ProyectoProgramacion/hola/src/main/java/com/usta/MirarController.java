package com.usta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MirarController {

        public ObservableList<Mascotas> mascotasOL = FXCollections.observableArrayList();
    public List<Mascotas> mascotasList= new ArrayList<>();

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

    public void initialize(){
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
        String filePath = directoryPath+ File.separator + "mascotas.txt";
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
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
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