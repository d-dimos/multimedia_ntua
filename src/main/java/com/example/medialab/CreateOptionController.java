package com.example.medialab;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.json.JSONObject;

public class CreateOptionController extends MainController{
    @FXML private TextField DictionaryIdField;
    @FXML private TextField OpenLibraryIdField;

    public void DoneButtonClick(MouseEvent mouseEvent) throws NoDescriptionException, UndersizeException, UnbalancedException, UnfilledTextException {
        String dictionaryId = DictionaryIdField.getText();
        String OpenLibraryId = OpenLibraryIdField.getText();

        // make sure input is not empty
        if (dictionaryId.isEmpty() || OpenLibraryId.isEmpty())
            throw new UnfilledTextException("Please fill all text fields");

        try { // send a GET request
            String url = "https://openlibrary.org/books/" + OpenLibraryId + ".json";
            InputStream response = new URL(url).openStream();
            Scanner scanner = new Scanner(response);
            String responseBody = scanner.useDelimiter("\\A").next();

            if (!responseBody.contains("description")) // check if description exists
                throw new NoDescriptionException("This book does not contain a description.");

            JSONObject jsonObject = new JSONObject(responseBody);
            String value = jsonObject.get("description").toString();

            if (value.contains("value")) // check if field value exists
                value = jsonObject.getJSONObject("description").getString("value");

            // keep only the letters and turn string into list of words
            value = value.replaceAll("[^a-zA-Z]", " ");
            String[] list_of_words = value.split("\\W+");

            // discard words with length less than 6 letters
            ArrayList<String> words = new ArrayList<String>();
            for (String list_of_word : list_of_words)
                if (list_of_word.length() >= 6) {
                    String uppercase = list_of_word.toUpperCase();
                    words.add(uppercase);
                }

            // insert every word into a set - ensure uniqueness
            Set<String> wordsSet = new HashSet<String>(words);

            // ensure that the final words are at least 20
            if(wordsSet.size() < 20)
                throw new UndersizeException("This book description contains less than 20 words.");

            // ensure that 20% of the words contain at least 9 letters
            int limit = 2*wordsSet.size() / 10;
            int counter = 0;
            for(String word : wordsSet)
                if (word.length() >= 9)
                    counter += 1;
            if(counter < limit)
                throw new UnbalancedException("Less than 20% of the words contain more than 9 letters");

            // save our files
            FileWriter myWriter = new FileWriter("./medialab/hangman_" + dictionaryId + ".txt");
            for(String word : wordsSet)
                myWriter.write(word + '\n');
            myWriter.close();
            Alert a1 = new Alert(Alert.AlertType.INFORMATION);
            a1.setHeaderText("Dictionary " + dictionaryId + " was created successfully!");
            a1.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            Alert a1 = new Alert(Alert.AlertType.ERROR);
            a1.setHeaderText("This OPEN LIBRARY ID is not valid.");
            a1.showAndWait();
        }

    }
}
