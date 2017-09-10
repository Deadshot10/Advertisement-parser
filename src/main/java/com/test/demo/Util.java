package com.test.demo;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {

    private static final int MAX_DESCRIPTION_LENGTH = 150;

    public static Long tryParse(String str) {
        Long retVal;
        try {
            retVal = Long.parseLong(str);
        } catch (NumberFormatException nfe) {
            retVal = -1L;
        }
        return retVal;
    }

    public static void log(Object c, String message){
        Logger.getLogger(c.getClass().toString()).log(Level.SEVERE, message);
    }

    public static void log(Object c, String... args){
        StringBuilder result = new StringBuilder("");
        for (String arg:args) {
            result.append(arg);
            result.append(" ");
        }
        log(c, result.toString());
    }

    public static String shortString(String s) {
        if (s.length() > MAX_DESCRIPTION_LENGTH)
            return s.substring(0, MAX_DESCRIPTION_LENGTH);
        else
            return s;
    }
}
