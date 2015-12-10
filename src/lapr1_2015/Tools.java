package lapr1_2015;

/**
 *
 * @author User
 */
public class Tools {
    public static int retirarCoeficienteDeVariavel(String variavel){
        variavel = variavel.trim();
        String coeficiente = "";
        int posSinal;
        if((posSinal = variavel.indexOf("-")) != -1){
            coeficiente = variavel.substring(posSinal + 1, variavel.indexOf("X"));
            coeficiente = "-" + coeficiente;
        }
        
        //@todo acabar este metodo
        return -1;
    }
    
    public static String retirarEspacos(String linha){
        //@todo acabar este metodo
        return linha;
    }
}
