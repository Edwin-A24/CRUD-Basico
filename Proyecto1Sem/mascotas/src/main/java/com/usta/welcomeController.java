package com.usta;

import java.io.IOException;
import javafx.fxml.FXML;

public class welcomeController {

    @FXML
    private void switchTopreview() throws IOException {
        App.setRoot("preview");
    }
}
