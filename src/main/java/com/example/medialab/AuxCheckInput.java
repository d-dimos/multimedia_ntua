package com.example.medialab;

import javafx.scene.control.TextField;

import static com.example.medialab.AuxIsNumeric.isNumeric;

public class AuxCheckInput {
    /**
     * Checks whether the input given by the user is valid.
     * For the input number to be valid: it must be an
     * integer greater than zero but less than the length
     * of the hidden word. For the input letter to be valid
     * it must be a single letter. The input is considered
     * valid for letters (both uppercase and lowercase) of
     * any language.
     *
     * @param LetterField   the textField corresponding to the letter input form on the UI
     * @param PositionField the textField corresponding to the position input form on the UI
     * @param chosen_word   the (randomly chosen and) hidden word
     * @return              true if the input letter and position are valid, else false
     */
    static boolean checkInput(TextField LetterField, TextField PositionField, String chosen_word) {
        String letter = LetterField.getText();
        String position = PositionField.getText();

        if (letter.length() != 1)
            return false;
        if (!Character.isLetter(letter.charAt(0)))
            return false;
        if (!isNumeric(position))
            return false;
        try {
            Integer.parseInt(position);
        } catch(NumberFormatException e) {
            return false;
        }
        return Integer.parseInt(position) > 0 && Integer.parseInt(position) < chosen_word.length() + 1;
    }
}
