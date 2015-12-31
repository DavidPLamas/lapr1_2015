package lapr1_2015;

/**
 * @author Group 2
 */
public class Tools {

    /**
     * Delete all whitespaces from a string.
     *
     * @param line Line that will be modified.
     * @return The line without whitespaces.
     */
    public static String removeSpaces(String line) {

        return line.replaceAll("\\s+", "");

    }

    /**
     * Print an error message on the screen.
     *
     * @param message The error message that will be printed on the screen.
     * @return True if successfull, false otherwise.
     */
    public static boolean printError(String message) {

        System.err.printf("%s%n", message);

        return true;

    }

    /**
     * Get the number of lines of specific text.
     * The number of lines is based on the system's line separator.
     * 
     * @param text The text that will be analysed.
     * @return The number of lines of that text.
     */
    public static int getNumberOfLines(String text) {

        text = text.trim();

        String lineSeparator = System.getProperty("line.separator");

        if (text.isEmpty() || text.equals("")) {

            return 0;
            
        }

        if (!text.contains(lineSeparator)) {

            return 1;
            
        }

        return text.split(lineSeparator).length;
        
    }

}
