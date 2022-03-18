package com.example.medialab;

import javafx.scene.control.Alert;

public class InvalidInputException extends Exception {
    public InvalidInputException(String e) {
        super(e);
        Alert a1 = new Alert(Alert.AlertType.ERROR);
        a1.setHeaderText("Please fill the 'Letter' field with a single letter and " +
                "the 'Position' Field with a single number corresponding to a valid letter position!");
        a1.showAndWait();
    }
}
