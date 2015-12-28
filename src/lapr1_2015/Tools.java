package lapr1_2015;

/**
 * @author Group 2
 */
public class Tools {

    /**
     * Deletes the extra spaces of a line.
     *
     * @param line Line that will be modified.
     * @return The line without spaces.
     */
    public static String removeSpaces(String line) {

        return line.replaceAll("\\s+", "");

    }

    /**
     * Prints an error message on the screen.
     *
     * @param message The error message that will be printed on the screen.
     * @return True if successfully executed or false if unsuccessfully
     * executed.
     */
    public static boolean printError(String message) {

        System.err.printf("%s%n", message);

        return true;

    }

    /**
     * Gets the number of lines of a text.
     *
     * @param text The text that will be analysed.
     * @return The number of lines of the text.
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
