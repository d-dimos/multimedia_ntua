package com.example.medialab;

public class AuxGetCorrectPoints {
    static int getCorrectPoints(float prob) {
        if (prob >= 0.6) return 5;
        if (prob < 0.6 && prob >= 0.4) return 10;
        if (prob < 0.4 && prob >= 0.25) return 15;
        return 30;
    }
}
