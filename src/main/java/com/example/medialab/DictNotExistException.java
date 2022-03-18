package com.example.medialab;

import javafx.scene.control.Alert;

public class DictNotExistException extends Exception {
    public DictNotExistException(String e) {
        super(e);
        Alert a1 = new Alert(Alert.AlertType.ERROR);
        a1.setHeaderText("Given Dictionary ID does not exist!");
        a1.showAndWait();
    }
}
