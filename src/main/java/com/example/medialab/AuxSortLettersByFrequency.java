package com.example.medialab;

import javafx.util.Pair;
import java.util.*;

public class AuxSortLettersByFrequency {
    static ArrayList<ArrayList<Pair<Character, Integer>>> SortLettersByFrequency(ArrayList<String> words) {

        int words_num = words.get(0).length();
        ArrayList<ArrayList<Pair<Character, Integer>>> final_2D_list = new ArrayList<>();

        for (int i = 0; i < words_num; i++) {
            Map<Character, Integer> freq = new HashMap<>();

            for (String word : words) { // initialize with zeros
                Character letter = word.charAt(i);
                freq.put(letter, 0);
            }

            for (String word : words) { // count the frequency of each letter
                Character letter = word.charAt(i);
                int count = freq.get(letter);
                freq.put(letter, count+1);
            }

            // convert the dictionary to list of pairs
            ArrayList< Pair<Character, Integer> > pairs = new ArrayList<>();
            for (Character key : freq.keySet())
                pairs.add(new Pair<>(key, freq.get(key)));

            // sort the pairs according to their freqs
            Collections.sort(pairs, Comparator.comparing(p -> -p.getValue()));
            final_2D_list.add(pairs);
        }
        return final_2D_list;
    }
}