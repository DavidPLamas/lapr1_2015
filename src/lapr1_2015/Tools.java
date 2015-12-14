package lapr1_2015;

/**
 *
 * @author Grupo 2
 */
public class Tools {
    
    
    public static String retirarEspacos(String linha){
        return linha.replaceAll("\\s+","");
    }
    
    public static void printError(String message){
        System.err.printf("%s%n",message);
    }
}
