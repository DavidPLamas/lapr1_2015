package lapr1_2015;

/**
 *
 * @author Grupo 2
 */
public class MathTools {

    
    public static final String VARIABLE_PATTERN = "[+-]?\\d{0,3}X[1-2]";
    
    public static float getVariableCoeficient(String variavel){
        
        String coeficient;
        
        int posX = variavel.indexOf("X");
        
        if(posX > 0){
            
            coeficient = variavel.substring(0, posX);
            
        }else{
            
            coeficient = "1";
            
        }
        
        if(coeficient.equals("-") || coeficient.equals("+")){
            
            coeficient += "1";
        }

        return Float.parseFloat(coeficient);
        
    }
    
    public static float calculateSimetric(float num){
        return (num * -1);
    }
    
    public static boolean validatesObjectiveFunction(String equacao){
        
        String padrao = "^Z=(" + VARIABLE_PATTERN + ")([+-]\\d{0,3}X[1-2])?$";
        
        return equacao.matches(padrao);
    }
    
    public static boolean validatesRestriction(String equacao){
        
        String padrao = "(" + VARIABLE_PATTERN + ")([+-]\\d{0,3}X[1-2])?(<=\\d{1,3}){1}";
        
        return equacao.matches(padrao);
    }
    
    public static int getXIndex(String variable){
        int XPos = variable.indexOf("X");
        if (XPos == -1) {
            return -1;
        }

        return (Integer.parseInt(variable.substring(XPos + 1)));
            
    }
    
    public static float[] multiplyLineByScalar (float [][] matrix, int lineIndex, float scalar){
        
        float newLine [] = new float [matrix[0].length];
        
        for (int i = 0; i < matrix[0].length; i++){
            
            newLine[i] = matrix [lineIndex][i] * scalar;
            
        }
        
        return newLine;
       
    }
    
    public static float[] addTwoLinesWithScalar(float[][] matrix, int lineIndex1, int lineIndex2, float scalar) {

        float[] lineToSum = multiplyLineByScalar(matrix, lineIndex2, scalar);

        float[] newLine = new float[matrix[0].length];

        for (int i = 0; i < matrix[0].length; i++) {

            newLine[i] = lineToSum[i] + matrix[lineIndex1][i];

        }

        return newLine;

    }
     
    public static int findPivotColumn(float[][] matrix) {
        float minor = Float.MAX_VALUE;
        int column = 0;
        
        for (int i = 0; i < matrix[0].length; i++) {
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
                minor = matrix[i][lastColumn]/matrix[i][column];
                line = i;
            }
        } 
        return line;
    }
    
}



