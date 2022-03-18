package com.example.medialab;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.example.medialab.AuxCheckInput.checkInput;
import static java.lang.Math.max;

import static com.example.medialab.AuxGetCorrectPoints.getCorrectPoints;
import static com.example.medialab.AuxSortLettersByFrequency.SortLettersByFrequency;
import static com.example.medialab.AuxShowBodyRemaining.ShowBodyRemaining;
import static com.example.medialab.AuxCreateStatistic.CreateStatistic;

public class MainController {
    static protected final DecimalFormat df = new DecimalFormat("0.00");
    static protected ArrayList<String> vocabulary = new ArrayList<>();
    static protected String chosen_word;
    static protected int guesses = 0;
    static protected int correct_guesses = 0;
    static protected boolean GameOn = false;
    static protected int points = 0;
    static protected int tries = 6;
    static protected ArrayList<String> sameLengthWords = new ArrayList<>();
    static protected Set<Integer> found_positions = new HashSet<>();
    static protected ArrayList<String> rounds = new ArrayList<>();

    @FXML private TextField AvailableWordsField;
    @FXML private TextField TotalPointsField;
    @FXML private TextField CorrectGuessesField;
    @FXML private TextField WordField;
    @FXML private TextField LetterField;
    @FXML private TextField PositionField;
    @FXML private Label SuggestedLettersField;
    @FXML private TextField TriesField;
    @FXML private Circle head;
    @FXML private Line body;
    @FXML private Line left_arm;
    @FXML private Line right_arm;
    @FXML private Line left_foot;
    @FXML private Line right_foot;

    /** ABOUT menuItem */
    public void onGameRulesClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("gameRules.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        Stage stage = new Stage();
        stage.setTitle("Rules of the Game");
        stage.setScene(scene);
        stage.show();
    }

    public void onCreatorClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("aboutCreator.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        Stage stage = new Stage();
        stage.setTitle("About Creator");
        stage.setScene(scene);
        stage.show();
    }

    /** APPLICATION menuItem (except for Start) */
    public void onCreateButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("createOption.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        Stage stage = new Stage();
        stage.setTitle("New Dictionary");
        stage.setScene(scene);
        stage.show();
    }

    public void onLoadButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("loadOption.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        Stage stage = new Stage();
        stage.setTitle("Load Dictionary");
        stage.setScene(scene);
        stage.show();
    }

    public void onExitButtonClick() {
        Alert a1 = new Alert(Alert.AlertType.INFORMATION);
        a1.setHeaderText("Let's 'hang out' some other time ;)");
        a1.showAndWait();
        System.exit(0);
    }

    /** DETAILS menuItem */
    public void onDictionaryButtonClick() throws NoLoadedDictionaryException {
        if(vocabulary.isEmpty())
            throw new NoLoadedDictionaryException("You have not loaded a dictionary!");

        int vocab_size = vocabulary.size();

        // percentage of words containing: 6 letters, 7-9 letters, 10 letters or more
        float six = 0, seven_nine = 0, ten = 0;
        for(String str : vocabulary) {
            if(str.length() == 6)
                six += 1;
            else if (str.length() > 6 && str.length() < 10)
                seven_nine += 1;
            else if (str.length() > 9)
                ten += 1;
        }

        DecimalFormat df = new DecimalFormat("0.00");
        float six_percent = six*100/vocab_size;
        float nine_percent = seven_nine*100/vocab_size;
        float ten_percent = ten*100/vocab_size;

        Alert a1 = new Alert(Alert.AlertType.INFORMATION);
        a1.setHeaderText("Words Statistics");
        a1.setContentText(
                "Percentage of words with 6 letters: " + df.format(six_percent) + "%\n"
                + "Percentage of words with 7 to 9 letters: " + df.format(nine_percent) + "%\n"
                + "Percentage of words with 10 or more letters: " + df.format(ten_percent) + "%\n");
        a1.showAndWait();
    }

    public void onRoundsButtonClick() {
        Alert a1 = new Alert(Alert.AlertType.INFORMATION);
        a1.setTitle("Last Five Rounds");
        a1.setHeaderText("Last 5 Rounds Information (from latest to earliest)");
        StringBuilder toPrint = new StringBuilder();

        Collections.reverse(rounds);
        int count = 5;
        for (String round : rounds) {
            toPrint.append(round);
            if(count-- == 1)
                break;
        }
        Collections.reverse(rounds);
        a1.setContentText(toPrint.toString());
        a1.showAndWait();
    }

    public void onSolutionButtonClick() {
        if (!GameOn) {
            Alert a1 = new Alert(Alert.AlertType.WARNING);
            a1.setHeaderText("You have not started a game!");
            a1.showAndWait();
            return;
        }

        Alert a1 = new Alert(Alert.AlertType.INFORMATION);
        a1.setTitle("SOLUTION");
        a1.setHeaderText("Solution: \t" + chosen_word);
        a1.showAndWait();

        GameOn = false;
        rounds.add(CreateStatistic(chosen_word, String.valueOf(tries), "Computer"));
    }

    /** START BUTTON */
    public void onStartButtonClick() throws NoLoadedDictionaryException {
        if(vocabulary.isEmpty())
            throw new NoLoadedDictionaryException("You have not loaded a dictionary!");

        GameOn = true;

        // randomly choose a word
        int idx = (int)(Math.random() * vocabulary.size());
        chosen_word = vocabulary.get(idx);

        // update the fields
        AvailableWordsField.setText(String.valueOf(vocabulary.size()));
        TotalPointsField.setText("0");
        CorrectGuessesField.setText("-");
        WordField.setText(" _ ".repeat(chosen_word.length()));
        TriesField.setText("6");

        // initialize game
        sameLengthWords = new ArrayList<String>();
        found_positions = new HashSet<>();
        points = 0;
        guesses = 0;
        correct_guesses = 0;
        tries = 6;
        ShowBodyRemaining(head, body, left_arm, right_arm, left_foot, right_foot, tries);

        // list of candidate words
        for (String word : vocabulary)
            if (word.length() == chosen_word.length())
                sameLengthWords.add(word);
        sameLengthWords.remove(chosen_word); // remove the hidden word

        ArrayList<ArrayList<Pair<Character, Integer>>> suggestions = SortLettersByFrequency(sameLengthWords);

        StringBuilder suggestion_to_project = new StringBuilder();
        int count = 0;
        for (ArrayList<Pair<Character, Integer>> position : suggestions) {
            suggestion_to_project.append("Position ").append(count + 1).append(": ");
            for (Pair<Character, Integer> let : position)
                suggestion_to_project.append(let.getKey()).append(" ");
            suggestion_to_project.append("\n\n");
            count += 1;
        }

        // show the suggested letters
        SuggestedLettersField.setText(suggestion_to_project.toString());
        //System.out.println(chosen_word);
    }

    /** MAKE A GUESS BUTTON */
    public void onMakeAGuessClick() throws UnfilledTextException, NoStartException, InvalidInputException {
        // ensure that the game has started
        if (!GameOn)
            throw new NoStartException("You have not started a game!");

        // ensure both fields have been filled
        if (LetterField.getText().isEmpty() || PositionField.getText().isEmpty())
            throw new UnfilledTextException("Please fill all text fields");

        // ensure that the fills are valid
        if (!checkInput(LetterField, PositionField, chosen_word))
            throw new InvalidInputException("Please fill the 'Letter' field with a single letter and" +
                    "the 'Position' Field with a single number corresponding to a valid letter position!");

        char input_letter = Character.toUpperCase(LetterField.getText().charAt(0));
        int input_position = Integer.parseInt(PositionField.getText())-1;

        boolean found = chosen_word.charAt(input_position) == input_letter;
        guesses += 1;

        if(found_positions.contains(input_position)) {
            Alert a1 = new Alert(Alert.AlertType.INFORMATION);
            a1.setHeaderText("You've already found the letter for this position!");
            a1.showAndWait();
            return;
        }

        if (found) { /** CASE 1: WE INPUT A CORRECT GUESS  */
            found_positions.add(input_position);
            correct_guesses += 1;

            input_position = (input_position + 1)*3 - 2;
            StringBuilder wordField_update = new StringBuilder(WordField.getText());
            wordField_update.setCharAt(input_position-1, ' ');
            wordField_update.setCharAt(input_position, input_letter);
            wordField_update.setCharAt(input_position+1, ' ');
            WordField.setText(String.valueOf(wordField_update));
            input_position = (input_position + 2)/3 - 1;

            if (!sameLengthWords.isEmpty()) {
                // add the appropriate points
                ArrayList<ArrayList<Pair<Character, Integer>>> suggestions = SortLettersByFrequency(sameLengthWords);
                ArrayList<Pair<Character, Integer>> position_suggestions = suggestions.get(input_position);

                ArrayList<Character> possible_chars = new ArrayList<Character>();
                for (Pair<Character, Integer> i : position_suggestions)
                    possible_chars.add(i.getKey());

                if (!possible_chars.contains(input_letter))
                    points += 30;
                else {
                    float prob = 0.0F, nom = 0.0F, denom = 0.0F;
                    for ( Pair<Character, Integer> i : position_suggestions ) {
                        denom += i.getValue();
                        if (i.getKey() == input_letter)
                            nom = i.getValue();
                    }
                    prob = nom / denom;
                    points += getCorrectPoints(prob);
                }

                // update the set of candidate words
                ArrayList<String> sameLengthWords_update = new ArrayList<>();
                for (String word :  sameLengthWords)
                    if (word.charAt(input_position) == input_letter)
                        sameLengthWords_update.add(word);
                sameLengthWords = sameLengthWords_update;
                StringBuilder suggestion_to_project = new StringBuilder();

                if(!sameLengthWords.isEmpty()) {
                    suggestions = SortLettersByFrequency(sameLengthWords);
                    int count = -1;
                    for (ArrayList<Pair<Character, Integer>> position : suggestions) {
                        count += 1;
                        if (found_positions.contains(count))
                            continue;
                        suggestion_to_project.append("Position ").append(count + 1).append(": ");
                        for (Pair<Character, Integer> let : position)
                            suggestion_to_project.append(let.getKey()).append(" ");
                        suggestion_to_project.append("\n\n");
                    }
                }
                SuggestedLettersField.setText(suggestion_to_project.toString());
            }
            else {
                points += 30;
                SuggestedLettersField.setText("");
            }

            TotalPointsField.setText(String.valueOf(points));
            CorrectGuessesField.setText(String.valueOf( df.format((float)correct_guesses/ (float)guesses * 100)));

            if(found_positions.size() == chosen_word.length()) { // the word is found
                Alert a1 = new Alert(Alert.AlertType.INFORMATION);
                a1.setTitle("YOU WON!");
                a1.setHeaderText("Congratulations! You found the hidden word!");
                a1.showAndWait();

                GameOn = false;
                rounds.add(CreateStatistic(chosen_word, String.valueOf(tries), "Player"));
            }
        }
        else { /** CASE 2: WE INPUT A WRONG GUESS  */

            if (!sameLengthWords.isEmpty()) { // update the set of candidate words
                ArrayList<String> sameLengthWords_update = new ArrayList<>();
                for (String word :  sameLengthWords)
                    if (word.charAt(input_position) != input_letter)
                        sameLengthWords_update.add(word);
                sameLengthWords = sameLengthWords_update;
                StringBuilder suggestion_to_project = new StringBuilder();

                if(!sameLengthWords.isEmpty()) {
                    ArrayList<ArrayList<Pair<Character, Integer>>> suggestions = SortLettersByFrequency(sameLengthWords);
                    int count = -1;
                    for (ArrayList<Pair<Character, Integer>> position : suggestions) {
                        count += 1;
                        if (found_positions.contains(count))
                            continue;
                        suggestion_to_project.append("Position ").append(count + 1).append(": ");
                        for (Pair<Character, Integer> let : position)
                            suggestion_to_project.append(let.getKey()).append(" ");
                        suggestion_to_project.append("\n\n");
                    }
                }
                SuggestedLettersField.setText(suggestion_to_project.toString());
            }
            tries -= 1;
            points = max(0, points-15);
            TotalPointsField.setText(String.valueOf(points));
            CorrectGuessesField.setText(String.valueOf( df.format((float)correct_guesses/ (float)guesses * 100)));
            TriesField.setText(String.valueOf(tries));

            ShowBodyRemaining(head, body, left_arm, right_arm, left_foot, right_foot, tries);

            if(tries == 0) {

                // reveal the word
                StringBuilder wordField_update = new StringBuilder(WordField.getText());
                for (int i = 0; i < chosen_word.length(); i++) {
                    int to_put = 3*(i+1)-2;
                    wordField_update.setCharAt(to_put-1, ' ');
                    wordField_update.setCharAt(to_put, chosen_word.charAt(i));
                    wordField_update.setCharAt(to_put+1, ' ');
                }
                WordField.setText(String.valueOf(wordField_update));

                Alert a1 = new Alert(Alert.AlertType.INFORMATION);
                a1.setHeaderText("GAME OVER!");
                a1.showAndWait();

                GameOn = false;
                rounds.add(CreateStatistic(chosen_word, String.valueOf(tries), "Computer"));
            }
        }
    }
}