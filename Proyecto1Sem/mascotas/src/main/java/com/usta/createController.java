package com.usta;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class createController {

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


    public void window(String name){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText("The pet "+ name +" has been successfully added.");
        alert.setContentText("You can check it in the view section.");

        alert.showAndWait();
    }


    public void initialize(){

        List<String> typeList = Arrays.asList("Puppy","Adult","Senior");
        ObservableList<String> oLType = FXCollections.observableArrayList(typeList);
        typeCBX.setItems(oLType);
        
        List<String> colorList = Arrays.asList("White","Black","Gray","Yellow","Stained");
        ObservableList<String> oLColor = FXCollections.observableArrayList(colorList);
        colorCBX.setItems(oLColor);

        List<String> hairList = Arrays.asList("Hard", "Curly", "Short", "Long","Straight", "Hairless");
        ObservableList<String> oLHair = FXCollections.observableArrayList(hairList);
        hairCBX.setItems(oLHair);

        List<String> animalList = Arrays.asList("Dog","Cat","Other Species");
        ObservableList<String> oLAnimal = FXCollections.observableArrayList(animalList);
        animalCBX.setItems(oLAnimal);

        List<String> sexList = Arrays.asList("Female","Male");
        ObservableList<String> oLSex = FXCollections.observableArrayList(sexList);
        sexCBX.setItems(oLSex);

    }

        public void addpet() {
        String animal = animalCBX.getValue();
        String other = otherTxt.getText();
        String type = typeCBX.getValue();
        String name = nameTxt.getText();
        String age = ageDtP.getValue().toString();
        String sex = sexCBX.getValue();
        String color = colorCBX.getValue();
        String hair = hairCBX.getValue();
    
        

        Pet newPet = new Pet(animal, other, type, name, age, sex, color, hair);
        
        
        String directoryPath = "src\\main\\resources\\com\\usta\\data";
        String filePath = directoryPath + File.separator + "pets.txt";

        
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(newPet.toString());
            writer.newLine();
            window(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void switchTopreview() throws IOException {
        App.setRoot("preview");
    }
}