package lapr1_2015;

/**
 *
 * @author Group 2
 */
public class Tools {

    public static String removeSpaces(String line) {

        return line.replaceAll("\\s+", "");
        
    }

    public static boolean printError(String message) {

        System.err.printf("%s%n", message);
        
        return true;

    }
}
