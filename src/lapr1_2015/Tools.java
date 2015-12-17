package lapr1_2015;

/**
 *
 * @author Group 2
 */
public class Tools {
    
    
    public static String removeSpaces(String line){
        
        return line.replaceAll("\\s+","");
    }
    
    public static void printError(String message){
        
        System.err.printf("%s%n",message);
        
    }
}
