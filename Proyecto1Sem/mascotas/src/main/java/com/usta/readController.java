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

public class readController {

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
}