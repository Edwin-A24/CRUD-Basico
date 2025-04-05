package com.usta;

import java.io.IOException;
import javafx.fxml.FXML;

public class previewController {

    @FXML
    private void switchTocreate() throws IOException {
        App.setRoot("create");
    }
    @FXML
    private void switchToread() throws IOException {
        App.setRoot("read");
    }
    @FXML
    private void switchToupdate() throws IOException {
        App.setRoot("update");
    }
    @FXML
    private void switchTodelete() throws IOException {
        App.setRoot("delete");
    }

}