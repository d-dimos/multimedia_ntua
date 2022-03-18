package com.example.medialab;

import javafx.scene.control.Alert;

public class NoDescriptionException extends Exception{
    public NoDescriptionException(String e) {
        super(e);
        Alert a1 = new Alert(Alert.AlertType.ERROR);
        a1.setHeaderText("This book does not contain a description.");
        a1.showAndWait();
    }
}


