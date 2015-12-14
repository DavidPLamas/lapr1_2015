package lapr1_2015;

/**
 *
 * @author Grupo 2
 */
public class MathTools {

    
    public static final String PADRAO_VARIAVEL = "[+-]?\\d{0,3}X[1-2]";
    
    public static float getVariableCoeficient(String variavel){
        
        String coeficiente;
        
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
    
    public static float calculateSimetric(float num){
        return (num * -1);
    }
    
    public static boolean validatesObjectiveFunction(String equacao){
        
        String padrao = "^Z=(" + PADRAO_VARIAVEL + ")([+-]\\d{0,3}X[1-2])?$";
        
        return equacao.matches(padrao);
    }
    
    public static boolean validatesRestriction(String equacao){
        
        String padrao = "(" + PADRAO_VARIAVEL + ")([+-]\\d{0,3}X[1-2])?(<=\\d{1,3}){1}";
        
        return equacao.matches(padrao);
    }
    
    public static int getXIndex(String variable){
        
        int XPos = variable.indexOf("X");
        
        if (XPos == -1){
            
            return -1;
            
        }
        
        return (Integer.parseInt(variable.substring(XPos +1)));
        
    }
    
    public static float[] multiplyLineByScalar (int [][] matrix, int lineIndex, int scalar){
        
        float newLine [] = new float [matrix[0].length];
        
        for (int i = 0; i < matrix[0].length; i++){
            
            newLine[i] = matrix [lineIndex][i] * scalar;
            
        }
        
        return newLine;
       
    }
    
}



