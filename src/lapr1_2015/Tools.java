package lapr1_2015;

/**
 *
 * @author Group 2
 */
public class Tools {
    
    /**
     * Deletes the extra spaces of a line.
     * @param line - line where the spaces will be removed.
     * @return The {@code line} without spaces.
     */
    public static String removeSpaces(String line) {

        return line.replaceAll("\\s+", "");
        
    }

    /**
     * Prints an error message on the screen.
     * @param message - The error message that will be printed on the screen.
     * @return {@code true} if successfully executed or {@code false} if unsuccessfully executed.
     */
    public static boolean printError(String message) {
        
        System.err.printf("%s%n", message);
        
        return true;

    }
    
    public static int getNumberOfLines(String text){
        String lineSeparator = System.getProperty("line.separator");
        return text.split(lineSeparator).length;
    }
}
