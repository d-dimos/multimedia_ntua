package com.example.medialab;

import javafx.scene.control.Alert;

public class UndersizeException extends Exception {
    public UndersizeException(String e) {
        super(e);
        Alert a1 = new Alert(Alert.AlertType.ERROR);
        a1.setHeaderText("This book description contains less than 20 words.");
        a1.showAndWait();
    }
}
