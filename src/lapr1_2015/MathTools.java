package lapr1_2015;

/**
 *
 * @author Grupo 2
 */
public class MathTools {

    public static final String padraoVariavel = "[+-]?\\d{0,3}X[1-2]";
    
    public static float retirarCoeficienteDeVariavel(String variavel){
        String coeficiente = "";
        int posX = variavel.indexOf("X");
        if(posX > 0){
            coeficiente = variavel.substring(0, posX);
        }else{
            coeficiente = "1";
        }
        
        if(coeficiente.equals("-") || coeficiente.equals("+")){
            coeficiente += "1";
        }
        
        return Float.parseFloat(coeficiente);
    }
    
    /* @todo Retirar no futuro
    public static boolean validaParteComVariaveis(String equacao){
        //Para ser válido, tem de começar por uma variavel padrao (ou seja, pode nao ter sinal atrás)
        //mas o resto das variaveis terão de ter obrigatoriamente sinal
        String padrao = "";
        return equacao.matches(padrao);
    }*/
    
    public static float calcularSimetrico(float num){
        return (num * -1);
    }
    
    public static boolean validaFuncaoObjetiva(String equacao){
        String padrao = "^Z=(" + padraoVariavel + ")([+-]\\d{0,3}X[1-2])?$";
        return equacao.matches(padrao);
    }
    
    public static boolean validaRestricao(String equacao){
        String padrao = "(" + padraoVariavel + ")([+-]\\d{0,3}X[1-2])?(<=\\d{1,3}){1}";
        return equacao.matches(padrao);
    }
    
    public static int retirarIndiceDeX(String variavel){
        int posDoX = variavel.indexOf("X");
        if(posDoX == -1){
            return -1;
        }
        
        return (Integer.parseInt(variavel.substring(posDoX)));
    }
}
