package lapr1_2015;

/**
 *
 * @author Group 2
 */
public class MathTools {

    /**
     *
     */
    public static final String VARIABLE_PATTERN = "[+-]?\\d{0,}X[1-2]";

    /**
     * Gets the coefficient of a certain variable.
     * @param variable - variable from where the coefficient will be taken.
     * @return the coefficient.
     */
    public static float getVariableCoefficient(String variable) {

        String coefficient;

        int posX = variable.indexOf("X");
        
        if(posX == -1){
            
            throw new Error("Couldn't find the X in "+variable);
            
        }else if (posX > 0) {

            coefficient = variable.substring(0, posX);

        } else {

            coefficient = "1";

        }

        if (coefficient.equals("-") || coefficient.equals("+")) {

            coefficient += "1";
        }

        return Float.parseFloat(coefficient);

    }

    /**
     * Calculates the symmetric of a number.
     * @param num - the number which symmetric will be calculated. 
     * @return {@code num}'s symmetric.
     */
    public static float calculateSymmetric(float num) {

        return (num * -1);

    }

    /**
     * Validates the objective function.
     * @param equation - the function that will be verified.
     * @return {@code true} if the {@code equation} is valid or {@code false} if the {@code equation} is not valid.
     */
    public static boolean validatesObjectiveFunction(String equation) {
        
        equation = Tools.removeSpaces(equation);

        String pattern = "^Z=(" + VARIABLE_PATTERN + ")([+-]\\d{0,3}X[1-2])?$";

        return equation.matches(pattern);
    }

    /**
     * Validates a restriction.
     * @param equation - the restriction that will be verified.
     * @return {@code true} if the {@code equation} is valid or {@code false} if the {@code equation} is not valid.
     */
    public static boolean validatesRestriction(String equation) {
        
        equation = Tools.removeSpaces(equation);

        String pattern = "(" + VARIABLE_PATTERN + ")([+-]\\d{0,3}X[1-2])?(<=\\d{1,3}){1}";

        return equation.matches(pattern);
    }

    /**
     * Gets the index of a variable.
     * @param variable - the variable which index will be gotten.
     * @return the index of the variable.
     */
    public static int getXIndex(String variable) {

        int XPos = variable.indexOf("X");

        if (XPos == -1) {

            return -1;
        }

        return (Integer.parseInt(variable.substring(XPos + 1)));

    }

    /**
     * Multiplies a line by a scalar.
     * @param matrix - the matrix that contains the line that will be multiplied.
     * @param lineIndex - the line's index.
     * @param scalar - the scalar that will be multiplied with the line.
     * @return the line multiplied.
     */
    public static float[] multiplyLineByScalar(float[][] matrix, int lineIndex, float scalar) {

        float newLine[] = new float[matrix[0].length];

        for (int i = 0; i < matrix[0].length; i++) {

            newLine[i] = matrix[lineIndex][i] * scalar;

        }

        return newLine;

    }

    /**
     * Adds two lines, where one of them is multiplied by a scalar.
     * @param matrix - the matrix that contains the line that will be multiplied.
     * @param lineIndex1 - the index of the line that will be altered.
     * @param lineIndex2 - the index of the line that will alter the other line.
     * @param scalar - the scalar that will be multiplied with the line that will be used to alter the other line.
     * @return the line that was modified.
     */
    public static float[] addTwoLinesWithScalar(float[][] matrix, int lineIndex1, int lineIndex2, float scalar) {

        float[] lineToSum = multiplyLineByScalar(matrix, lineIndex2, scalar);

        float[] newLine = new float[matrix[0].length];

        for (int i = 0; i < matrix[0].length; i++) {

            newLine[i] = lineToSum[i] + matrix[lineIndex1][i];

        }

        return newLine;

    }

    /**
     * Finds the column that contains the pivot.
     * @param matrix - the matrix where the column that contains the pivot will be found.
     * @return the {@code column} that contains the pivot.
     */
    public static int findPivotColumn(float[][] matrix) {

        float minor = Float.MAX_VALUE;

        int column = 0;

        for (int i = 0; i < matrix[0].length; i++) {

            if (matrix[0][i] < minor) {

                minor = matrix[0][i];

                column = i;
            }
        }

        //Returns -1 if the minor is not negative. 
        //This means the method should be over.
        if (minor >= 0) {

            return -1;

        } else {

            return column;

        }
    }

    /**
     * Finds the line that contains the pivot.
     * @param matrix - the matrix where the line that contains the pivot will be found.
     * @param column - the column that contains the pivot.
     * @return the {@code line} that contains the pivot.
     */
    public static int findPivotLine(float[][] matrix, int column) {
        //Checks if the column parameter is valid.
        if (column < 0) {

            return -1;

        }

        float minor = Float.MAX_VALUE;

        int line = -1;

        int lastColumn = matrix[0].length - 1;

        for (int i = 1; i < matrix.length; i++) {
            //Skips this line if either one of the columns is negative.
            if (matrix[i][lastColumn] <= 0) {

                continue;

            }

            //Checks if this line has a minor value.
            if ((matrix[i][lastColumn] / matrix[i][column]) > 0 && (matrix[i][lastColumn] / matrix[i][column]) < minor) {

                minor = matrix[i][lastColumn] / matrix[i][column];

                line = i;
            }
        }

        return line;
    }

}
