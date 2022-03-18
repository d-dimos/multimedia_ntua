package com.example.medialab;

import javafx.scene.control.Alert;

public class NoStartException extends Exception {
    public NoStartException(String e) {
        super(e);
        Alert a1 = new Alert(Alert.AlertType.ERROR);
        a1.setHeaderText("You have not started a game!");
        a1.showAndWait();
    }
}
