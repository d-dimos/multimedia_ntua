package com.example.medialab;

import javafx.scene.control.Alert;

public class UnbalancedException extends Throwable {
    public UnbalancedException(String e) {
        super(e);
        Alert a1 = new Alert(Alert.AlertType.ERROR);
        a1.setHeaderText("Less than 20% of the words contain more than 9 letters");
        a1.showAndWait();
    }
}
