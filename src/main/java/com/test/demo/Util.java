package com.test.demo;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {

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
}
