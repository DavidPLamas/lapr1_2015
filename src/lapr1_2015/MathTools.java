package lapr1_2015;

import java.sql.DatabaseMetaData;

/**
 *
 * @author Grupo 2
 */
public class MathTools {

    public static final String padraoVariavel = "[+-]?\\d{0,3}X[1-2]";

    public static float retirarCoeficienteDeVariavel(String variavel) {
        String coeficiente = "";
        int posX = variavel.indexOf("X");
        if (posX > 0) {
            coeficiente = variavel.substring(0, posX);
        } else {
            coeficiente = "1";
        }

        if (coeficiente.equals("-") || coeficiente.equals("+")) {
            coeficiente += "1";
        }

        return Float.parseFloat(coeficiente);
    }

    public static float calcularSimetrico(float num) {
        return (num * -1);
    }

    public static boolean validaFuncaoObjetiva(String equacao) {
        String padrao = "^Z=(" + padraoVariavel + ")([+-]\\d{0,3}X[1-2])?$";
        return equacao.matches(padrao);
    }

    public static boolean validaRestricao(String equacao) {
        String padrao = "(" + padraoVariavel + ")([+-]\\d{0,3}X[1-2])?(<=\\d{1,3}){1}";
        return equacao.matches(padrao);
    }

    public static int retirarIndiceDeX(String variavel) {
        int posDoX = variavel.indexOf("X");
        if (posDoX == -1) {
            return -1;
        }

        return (Integer.parseInt(variavel.substring(posDoX + 1)));
    }

    public static int findPivotColumn(float[][] matrix, int nrVar) {
        float minor = Float.MAX_VALUE;
        int column = 0;
        
        for (int i = 0; i < nrVar; i++) {
            if (matrix[0][i] < minor) {
                minor = matrix[0][i];
                column = i;
            }
        }
        
        //Return -1 if the minor is not negative. 
        //This means the method should be over
        if(minor >= 0){
            return -1;
        }else{
            return column;
        }
    }

    public static int findPivotLine(float[][] matrix, int column) {
        //Check if the column parameter is valid
        if(column < 0){
            return -1;
        }
        
        float minor = Float.MAX_VALUE;
        int line = -1;
        int lastColumn = matrix[0].length -1; 
        
        for (int i = 0; i < matrix.length; i++) {
            //Skip this line if either one of the columns is negative
            if(matrix[i][column] < 0 || matrix[i][lastColumn] < 0){
                continue;
            }
            
            //Check if this line has a minor value
            if((matrix[i][lastColumn]/matrix[i][column]) < minor){
                minor = matrix[i][column];
                line = i;
            }
        } 
        return line;
    }
}
