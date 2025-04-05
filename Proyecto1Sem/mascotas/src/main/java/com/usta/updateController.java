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

public class updateController {

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

    
    @FXML
    private ComboBox<String> animalCBX;
    @FXML
    private TextField otherTxt;
    @FXML
    private ComboBox<String> typeCBX;
    @FXML
    private TextField nameTxt;
    @FXML
    private DatePicker ageDtP;
    @FXML
    private ComboBox<String> sexCBX;
    @FXML
    private ComboBox<String> colorCBX;
    @FXML
    private ComboBox<String> hairCBX;

    private Pet petsToEdit;

    public void window(String name) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText(" " + name + " has been added successfully.");
        alert.setContentText("You can check in the view section.");

        alert.showAndWait();
        petOL.setAll(petList);
        petsTable.setItems(petOL);
    }

    public void initialize() {

        ObservableList<String> typeOL = FXCollections.observableArrayList(
            "Puppy","Adult","Senior");
        typeCBX.setItems(typeOL);

        ObservableList<String> colorOL = FXCollections.observableArrayList(
            "White","Black","Gray","Yellow","Stained");
        colorCBX.setItems(colorOL);

        ObservableList<String> hairOL = FXCollections.observableArrayList(
            "Hard", "Curly", "Short", "Long","Straight", "Hairless");
        hairCBX.setItems(hairOL);

        ObservableList<String> animalOL = FXCollections.observableArrayList(
            "Dog","Cat","Other Species");
        animalCBX.setItems(animalOL);

        ObservableList<String> sexOL = FXCollections.observableArrayList(
            "Female","Male");
        sexCBX.setItems(sexOL);


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


    public void setPetsToEdit() {
        this.petsToEdit = petsTable.getSelectionModel().getSelectedItem();
        uploadDataPets();
    }

    private void uploadDataPets() {
        if (petsToEdit != null) {
            animalCBX.setValue(petsToEdit.getAnimal());
            otherTxt.setText(petsToEdit.getOther());
            typeCBX.setValue(petsToEdit.getType());
            nameTxt.setText(petsToEdit.getName());
            LocalDate localDate = LocalDate.parse(petsToEdit.getAge().toString());
            ageDtP.setValue(localDate);
            sexCBX.setValue(petsToEdit.getSex());
            colorCBX.setValue(petsToEdit.getColor());
            hairCBX.setValue(petsToEdit.getHair());

        }
    }

    @FXML
    public void editPets() throws IOException {
        if (petsToEdit != null) {
            petsToEdit.setAnimal(animalCBX.getValue());
            petsToEdit.setOther(otherTxt.getText());
            petsToEdit.setType(typeCBX.getValue());
            petsToEdit.setName(nameTxt.getText());
            LocalDate localDate = LocalDate.parse(petsToEdit.getAge().toString());
            ageDtP.setValue(localDate);
            petsToEdit.setSex(sexCBX.getValue());
            petsToEdit.setColor(colorCBX.getValue());
            petsToEdit.setHair(hairCBX.getValue());


            updateFile();

        }
        switchTopreview();
    }

    private void updateFile() {

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + ".tmp"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 8 && parts[7].equals(petsToEdit.getHair())) {
                    writer.write(petsToEdit.getAnimal() + "|" + petsToEdit.getOther() + "|" +
                            petsToEdit.getType() + "|" + petsToEdit.getName() + "|" +
                            petsToEdit.getAge() + "|" + petsToEdit.getSex() + "|" +
                            petsToEdit.getColor() + "|" + petsToEdit.getHair());
                    writer.newLine();
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        
        File file = new File(filePath);
        File tempFile = new File(filePath + ".tmp");
        if (file.delete()) {
            if (tempFile.renameTo(file)) {
                window("The mascot edition " + petsToEdit.getName() + " has been saved successfully.");
            } else {
                System.out.println("Could not rename temporary file.");
            }
        } else {
            System.out.println("Could not delete the original file.");
        }
        if (tempFile.renameTo(file)) {
            window("The pet edit " + petsToEdit.getName() + "  has been saved successfully.");
        } else {
            System.out.println("Could not replace the original file with the temporary one.");
        }
    }

    @FXML
    private void switchTopreview() throws IOException {
        App.setRoot("preview");
    }


}
