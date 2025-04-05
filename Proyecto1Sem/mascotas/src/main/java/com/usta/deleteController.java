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

public class deleteController {

    private String filePath = "src/main/resources/com/usta/data/pets.txt";
    public ObservableList<Pet> petOL = FXCollections.observableArrayList();
    public List<Pet> petList = new ArrayList<>();

    @FXML
    private TableView<Pet> petsTable;
    @FXML
    private TableColumn<Pet, String> animalCol;
    @FXML
    private TableColumn<Pet, String> otherCol;
    @FXML
    private TableColumn<Pet, String> typeCol;
    @FXML
    private TableColumn<Pet, String> nameCol;
    @FXML
    private TableColumn<Pet, String> ageCol;
    @FXML
    private TableColumn<Pet, String> sexCol;
    @FXML
    private TableColumn<Pet, String> colorCol;
    @FXML
    private TableColumn<Pet, String> hairCol;

    private Pet petsToDelete;


    public void initialize(){
        animalCol.setCellValueFactory(new PropertyValueFactory<>("animal"));
        otherCol.setCellValueFactory(new PropertyValueFactory<>("other"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        sexCol.setCellValueFactory(new PropertyValueFactory<>("sex"));
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
        hairCol.setCellValueFactory(new PropertyValueFactory<>("hair"));
        loadPet();

        petOL.setAll(petList);
        petsTable.setItems(petOL);
        
    }

    public void setPetsToEdit() {
        this.petsToDelete = petsTable.getSelectionModel().getSelectedItem();
    }

        @FXML
    public void windowDelete() throws IOException {
        setPetsToEdit();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to remove the pet " + petsToDelete.getName() + "?");
        alert.setContentText("This action can not be undone.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Aquí puedes incluir la lógica para eliminar el libro si el usuario confirma
            // Por ejemplo, puedes llamar a un método para eliminar el libro de la lista y
            // actualizar la tabla
            removePet();
        }
    }
    public void windowSuccess(String name) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText(name);
        alert.setContentText("You can check in the view section.");

        // Mostrar la ventana emergente y esperar a que el usuario la cierre
        alert.showAndWait();
        petOL.setAll(petList);
        petsTable.setItems(petOL);
    }

    public void removePet() throws IOException {
        // Archivo original y archivo temporal para escribir los cambios
       
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + ".tmp"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Dividir la línea en partes
                String[] parts = line.split("\\|");
                // Si el ISBN de la línea coincide con el ISBN del libro a eliminar, no escribas
                // esa línea en el archivo temporal
                if (parts.length == 8 && parts[7].equals(petsToDelete.getHair())) {
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
                windowSuccess("Removal of the pet " + petsToDelete.getName() + " it has been realized correctly.");
                switchTodelete();
            } else {
                System.out.println("Could not rename temporary file.");
            }
        } else {
            System.out.println("Could not delete the original file.");
        }
    }

    private void loadPet() {
        String directoryPath = "src\\main\\resources\\com\\usta\\data";
        String filePath = directoryPath + File.separator + "pets.txt";
        File file = new File(filePath);

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length == 8) {
                        Pet pet = new Pet(parts[0], parts[1], parts[2], parts[3],
                        parts[4], parts[5], parts[6], parts[7]);
                        petList.add(pet);
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
    private void switchTopreview() throws IOException {
        App.setRoot("preview");
    }
    @FXML
    private void switchTodelete() throws IOException {
        App.setRoot("delete");
    }

}