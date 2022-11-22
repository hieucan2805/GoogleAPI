package utils;

import org.apache.log4j.Logger;

public class Log {
    public Log() {
    }

    // Initialize Log4j logs
    private static final Logger Log = Logger.getLogger(Log.class.getName());

    // Need to create these methods, so that they can be called

    public static void info(String message) {
        System.out.println(message);
        Log.info(message);
    }

    public static void step(String message) {
        System.out.println("STEP: " + message);
        Log.info(message);
    }

    public static void warn(String message) {
        Log.warn(message);
    }
}