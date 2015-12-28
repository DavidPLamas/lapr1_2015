package lapr1_2015;

/**
 * @author Group 2
 */
public class MathTools {

    public static final String VARIABLE_PATTERN = "[+-]?\\d{0,}X[1-2]";

    /**
     * Get the coefficient of a certain variable.
     *
     * @param variable Variable from where the coefficient will be taken.
     * @return The coefficient.
     */
    public static float getVariableCoefficient(String variable) {

        String coefficient;

        int posX = variable.indexOf("X");

        if (posX == -1) {

            throw new Error("Couldn't find the X in " + variable);

        } else if (posX > 0) {

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
     * Calculate the symmetric of a number.
     *
     * @param num The number which symmetric will be calculated.
     * @return Number's symmetric.
     */
    public static float calculateSymmetric(float num) {

        return (num * -1);

    }

    /**
     * Validate the objective function.
     *
     * @param equation The function that will be verified.
     * @return True if the function is valid or false if the function is not
     * valid.
     */
    public static boolean validatesObjectiveFunction(String equation) {

        equation = Tools.removeSpaces(equation);

        String pattern = "^Z=(" + VARIABLE_PATTERN + ")([+-]\\d{0,3}X[1-2])?$";

        return equation.matches(pattern);

    }

    /**
     * Validate a restriction.
     *
     * @param equation The restriction that will be verified.
     * @return True if the restricion is valid or false if the restriction is
     * not valid.
     */
    public static boolean validatesRestriction(String equation) {

        equation = Tools.removeSpaces(equation);

        String pattern = "(" + VARIABLE_PATTERN + ")([+-]\\d{0,3}X[1-2])?(<=\\d{1,3}){1}";

        return equation.matches(pattern);

    }

    /**
     * Get the index of a variable.
     *
     * @param variable The variable that will be used.
     * @return The index of the variable.
     */
    public static int getXIndex(String variable) {

        int XPos = variable.indexOf("X");

        if (XPos == -1) {

            return -1;
        }

        return (Integer.parseInt(variable.substring(XPos + 1)));

    }

    /**
     * Multiply a line by a scalar.
     *
     * @param matrix The matrix main matrix.
     * @param lineIndex The line's index.
     * @param scalar The scalar that will be multiplied with the line.
     * @return The line multiplied.
     */
    public static float[] multiplyLineByScalar(float[][] matrix, int lineIndex, float scalar) {

        float newLine[] = new float[matrix[0].length];

        for (int i = 0; i < matrix[0].length; i++) {

            newLine[i] = matrix[lineIndex][i] * scalar;

        }

        return newLine;

    }

    /**
     * Add two lines, where one of them is multiplied by a scalar.
     *
     * @param matrix The main matrix.
     * @param lineIndex1 The index of the line that will be altered.
     * @param lineIndex2 The index of the line that will alter the other line.
     * @param scalar The scalar that will be multiplied with the line that will
     * be used to alter the other line.
     * @return The line that was modified.
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
     * Find the column that contains the pivot.
     *
     * @param matrix The main matrix.
     * @return The column that contains the pivot.
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

        //Return -1 if the minor is not negative. 
        //This means the method should be over.
        if (minor >= 0) {

            return -1;

        } else {

            return column;

        }

    }

    /**
     * Find the line that contains the pivot.
     *
     * @param matrix The main matrix.
     * @param column The column that contains the pivot.
     * @return The line that contains the pivot.
     */
    public static int findPivotLine(float[][] matrix, int column) {

        //Check if the column parameter is valid.
        if (column < 0) {

            return -1;

        }

        float minor = Float.MAX_VALUE;

        int line = -1;

        int lastColumn = matrix[0].length - 1;

        for (int i = 1; i < matrix.length; i++) {

            //Skip this element in the pivot column is zero (it's mathematically impossible).
            if (matrix[i][column] == 0) {

                continue;

            }

            //Check if this line has a minor value.
            if ((matrix[i][lastColumn] / matrix[i][column]) > 0 && (matrix[i][lastColumn] / matrix[i][column]) < minor) {

                minor = matrix[i][lastColumn] / matrix[i][column];

                line = i;

            }

        }

        return line;

    }

}
