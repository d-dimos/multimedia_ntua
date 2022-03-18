package com.example.medialab;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoadOptionController extends MainController{
    @FXML private TextField TypeDictionaryIdField;
    public void DoneButtonClick() throws IOException, DictNotExistException, UnfilledTextException  {
        String DictionaryId = TypeDictionaryIdField.getText();

        // make sure that the field is not empty
        if(DictionaryId.isEmpty())
            throw new UnfilledTextException("Please fill all text fields");

        // make sure that given DICTIONARY-ID exists
        File dictID = new File("./medialab/hangman_" + DictionaryId + ".txt");
        if (!dictID.exists())
            throw new DictNotExistException("Given Dictionary ID does not exist!");
        else {
            Alert a1 = new Alert(Alert.AlertType.INFORMATION);
            a1.setHeaderText("Input Dictionary was Loaded!");
            a1.showAndWait();
        }

        // input the words from the dictionary
        BufferedReader br = new BufferedReader(new FileReader(dictID));

        // save the vocabulary to handle its words afterwords
        String temp_st;
        vocabulary = new ArrayList<>();
        while ((temp_st = br.readLine()) != null)
            vocabulary.add(temp_st);

        GameOn = false;
    }
}
