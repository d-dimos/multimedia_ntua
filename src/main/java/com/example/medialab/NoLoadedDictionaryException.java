package com.example.medialab;

import javafx.scene.control.Alert;

public class NoLoadedDictionaryException extends Exception {
    public NoLoadedDictionaryException(String e) {
        super(e);
        Alert a1 = new Alert(Alert.AlertType.ERROR);
        a1.setHeaderText("You have not loaded a dictionary!");
        a1.showAndWait();
    }
}
