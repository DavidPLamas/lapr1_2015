package lapr1_2015;

import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Group 2
 */
public class Log {

    /**
     * Allocate the error log in memory.
     *
     * @param fileName The name of the file.
     * @param append If true and the file exists the file will not be replaced
     * by a new one
     * @return The allocated file or null if unsuccessful.
     */
    public static FileWriter openFile(String fileName, boolean append) {

        FileWriter logErrors = null;

        try {

            logErrors = new FileWriter(fileName, append);

        } catch (Exception e) {

            Tools.printError("It wasn't possible to create the error log!");

        }

        return logErrors;

    }

    /**
     * Write messages to a specific file.
     *
     * @param message Message to be written to the error log.
     * @param file The place where the message will be written.
     * @return Whether the message was or not written to the file 
     */
    public static boolean insertLog(String message, FileWriter file) {

        if (file != null) {

            try {
                file.write(String.format("%s -> %s%n", getCurrentDate(), message));
            } catch (Exception e) {
                return false;
            }
            
            return true;
        }
        
        return false;

    }

    /**
     * Calculate the current date and time and returns them formatted correctly.
     *
     * @return The formatted time and date.
     */
    private static String getCurrentDate() {

        Calendar now = Calendar.getInstance();

        DateFormat formattedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        return formattedDate.format(now.getTime());

    }

    /**
     * Close the file.
     *
     * @param file The error log file.
     * @return Whether the file was closed or not
     */
    public static boolean closeFile(FileWriter file) {

        if (file != null) {

            try {
                file.close();
            } catch (Exception e) {
                return false;
            }
            return true;

        }
        
        return false;

    }

}
