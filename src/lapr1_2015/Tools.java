package lapr1_2015;

/**
 * @author Group 2
 */
public class Tools {

    /**
     * Delete all white spaces from a string.
     *
     * @param line The line that will be modified.
     * @return The line without white spaces.
     */
    public static String removeSpaces(String line) {

        return line.replaceAll("\\s+", "");

    }

    /**
     * Print an error message on the screen.
     *
     * @param message The error message that will be printed on the screen.
     * @return True if successful, false otherwise.
     */
    public static boolean printError(String message) {

        System.err.printf("%s%n", message);

        return true;

    }

    /**
     * Get the number of lines of a specific text. The number of lines is based
     * on the system's line separator.
     *
     * @param text The text that will be analyzed.
     * @return The number of lines of that text.
     */
    public static int getNumberOfLines(String text) {

        text = text.trim();

        String lineSeparator = Lapr1_2015.LINE_SEPARATOR;

        if (text.isEmpty() || text.equals("")) {

            return 0;

        }

        if (!text.contains(lineSeparator)) {

            return 1;

        }

        return text.split(lineSeparator).length;

    }
    
    /**
     * Get a random integer number between 2 integer numbers
     * 
     * @param min The minimum number
     * @param max The maximum number
     * @return A random number between the min and max numbers received
     */
    public static int getRandomNumber(int min, int max){
        
         return (min + (int)(Math.random()*max)); 
         
    }
    
    /**
     * Find the position of the first ocurrence of something in an array
     * 
     * @param array The array 
     * @param search The word or words being searched in the array
     * @return The position of the ocurrence or -1 if not found
     */
    public static int getPositionOf(String[] array, String search){
        
        for (int i = 0; i < array.length; i++) {
            
            if(array[i].equalsIgnoreCase(search)){
                
                return i;
                
            }
            
        }
        
        return -1;
        
    }
    
    /**
     * Encode a string to a filename friendly string.
     * 
     * @param text The text to be encoded
     * @return An encoded string
     */
    public static String encodeString(String text){
        
        return text.replaceAll("\\s", "_").replaceAll("\\.", "");
        
    }

}
