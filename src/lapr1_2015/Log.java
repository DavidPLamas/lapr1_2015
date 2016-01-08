package lapr1_2015;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Formatter;

/**
 * @author Group 2
 */
public class Log {

    /**
     * Allocate the error log in memory.
     *
     * @param fileName The name of the error log file.
     * @return The allocated file or null if unsuccessful.
     */
    public static Formatter openFile(String fileName) {

        Formatter logErrors = null;

        try {

            logErrors = new Formatter(new File(fileName));

        } catch (Exception e) {

            Tools.printError("It wasn't possible to create the error log!");

        }

        return logErrors;

    }

    /**
     * Write messages to the error log.
     *
     * @param message Message to be written to the error log.
     * @param output The place where the message will be written.
     */
    public static void insertLog(String message, Formatter output) {

        if (output != null) {

            output.format("%s -> %s%n%n", calculateDate(), message);

        }

    }

    /**
     * Calculate the current date and time and returns them formatted correctly.
     *
     * @return The formatted time and date.
     */
    private static String calculateDate() {

        Calendar now = Calendar.getInstance();

        DateFormat formattedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        return formattedDate.format(now.getTime());

    }

    /**
     * Close the error log.
     *
     * @param file The error log file.
     */
    public static void closeFile(Formatter file) {

        if (file != null) {

            file.close();

        }

    }

}
