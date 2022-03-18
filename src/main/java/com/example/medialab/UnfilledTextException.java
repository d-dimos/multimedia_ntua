package com.example.medialab;

import javafx.scene.control.Alert;

public class UnfilledTextException extends Exception {
    public UnfilledTextException(String e) {
        super(e);
        Alert a1 = new Alert(Alert.AlertType.ERROR);
        a1.setHeaderText("Please fill all text fields");
        a1.showAndWait();
    }
}
