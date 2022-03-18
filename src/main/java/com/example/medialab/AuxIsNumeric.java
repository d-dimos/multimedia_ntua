package com.example.medialab;

public class AuxIsNumeric {
    public static boolean isNumeric(String strNum) {
        if (strNum == null)
            return false;
        try { double d = Double.parseDouble(strNum); }
        catch (NumberFormatException nfe) { return false; }
        return true;
    }
}
