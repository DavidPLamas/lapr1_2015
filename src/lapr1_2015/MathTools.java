package lapr1_2015;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Group 2
 */
public class MathTools {

    /**
     * The regex pattern for a number of type REAL. This means it can be either
     * an integer, decimal or fractional number
     */
    public static final String REAL_NUMBER_PATTERN = "((([0-9]+)\\.([0-9]+))|([0-9]+/[1-9]\\d*)|([0-9]*))";

    /**
     * @todo review syntax The regex pattern for any variable. A variable is
     * made of a signal (+ or -) or none, followed by any number. After that
     * should be an X followed by a number from 1 to 99, which indicates the
     * variable index
     * @see #REAL_NUMBER_PATTERN
     */
    public static final String VARIABLE_PATTERN = "[+-]?" + REAL_NUMBER_PATTERN + "X\\d{1,2}";

    /**
     * Get the coefficient value of a certain variable. If there is no X in the
     * variable, an error will be thrown.
     *
     * @param variable Variable from where the coefficient will be taken.
     * @return The coefficient.
     * @throws Error
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

        //If coeficient is a fraction, we need to manualy divide the numbers
        if (coefficient.contains("/")) {
            String[] parts = coefficient.split("/");

            return (Float.parseFloat(parts[0]) / Float.parseFloat(parts[1]));
        }

        return Float.parseFloat(coefficient);

    }

    /**
     * Calculate the symmetric of a number.
     *
     * @param num The number.
     * @return The symmetric of the number.
     */
    public static float calculateSymmetric(float num) {

        return (num * -1);

    }

    /**
     * Validate the objective function. To be valid, the objective function must
     * contain Z as the first character followed by an = operator and one or
     * more variables. All the variables must be in crescent order. For example
     * X1 +X2 + X3. If they are not ordered, it will be considered invalid.
     *
     * @param equation The equation that will be verified.
     * @return Whether the objective function is valid or not.
     */
    public static boolean validateObjectiveFunction(String equation) {

        equation = Tools.removeSpaces(equation);

        String pattern = "^Z=(" + VARIABLE_PATTERN + ")([+-]" + REAL_NUMBER_PATTERN + "X\\d{1,2}){0,}$";

        if (!equation.matches(pattern)) {
            return false;
        }

        Matcher m = Pattern.compile("(" + MathTools.VARIABLE_PATTERN + ")").matcher(equation);

        int lastIndex = 0;
        while (m.find()) {

            String variable = m.group(1);

            int currentIndex = MathTools.getXIndex(variable);

            if (currentIndex != (lastIndex + 1)) {
                return false;
            } else {
                lastIndex = currentIndex;
            }
        }

        return true;

    }

    /**
     * Validate a restriction. To be valid, the restriction start with one or
     * more variables followed by &lt;= operator and a number next to it.
     *
     * @param equation The equation that will be verified.
     * @return Whether this is a valid restriction or not.
     */
    public static boolean validateRestriction(String equation) {

        equation = Tools.removeSpaces(equation);

        //String pattern = "(" + VARIABLE_PATTERN + ")([+-]"+REAL_NUMBER_PATTERN+"X\\d{1,2})?(<="+REAL_NUMBER_PATTERN+"){1}";
        String pattern = "^(" + VARIABLE_PATTERN + ")([+-]" + REAL_NUMBER_PATTERN + "X\\d{1,2}){0,}((<=|>=)[+-]?" + REAL_NUMBER_PATTERN + "){1}$";

        return equation.matches(pattern);

    }

    /**
     * Get the index of a variable.
     *
     * @param variable The variable that will be used.
     * @return The index of the variable. If there is no X in the variable, will
     * return -1.
     */
    public static int getXIndex(String variable) {

        int XPos = variable.indexOf("X");

        if (XPos == -1) {

            return -1;
        }

        return (Integer.parseInt(variable.substring(XPos + 1)));

    }

    /**
     * Multiply a line of a matrix by a scalar.
     *
     * @param matrix The matrix.
     * @param lineIndex The matrix line's index.
     * @param scalar The scalar that will be multiplied to the line.
     * @return The line multiplied by that scalar.
     */
    public static float[] multiplyLineByScalar(float[][] matrix, int lineIndex, float scalar) {

        float newLine[] = new float[matrix[0].length];

        for (int i = 0; i < matrix[0].length; i++) {

            newLine[i] = matrix[lineIndex][i] * scalar;

        }

        return newLine;

    }

    /**
     * Add one line to another multiplied by a scalar.
     *
     * @param matrix The matrix.
     * @param lineIndex1 The first line index.
     * @param lineIndex2 The line index that will be multiplied.
     * @param scalar The scalar that will be multiplied to the lineIndex2.
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
     * @param matrix The matrix.
     * @return The column that contains the pivot or -1 if there is not a pivot.
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
        //This means there is no pivot column.
        if (minor >= 0) {

            return -1;

        } else {

            return column;

        }

    }

    /**
     * Find the line that contains the pivot based on a specific column.
     *
     * @param matrix The matrix.
     * @param column The column that contains the pivot.
     * @return The line that contains the pivot or -1 if there is not a pivot
     * line
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
            //Skip this element if it equals 0 (it's mathematically impossible to divide by 0).
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

    /**
     * Calculates the transposed matrix of a matrix received as a parameter.
     * 
     * @param matrix The matrix.
     * @return return The transposed matrix.
     */
    public static float[][] transposeMatrix(float[][] matrix) {
        float[][] transposeMatrix = new float[matrix[0].length][matrix.length];
        for (int line = 0; line < transposeMatrix.length; line++) {
            for (int column = 0; column < transposeMatrix[line].length; column++) {
                if (column > line || column < line) {
                    transposeMatrix[line][column] = matrix[column][line];
                }
                if (column == line) {
                    transposeMatrix[line][column] = matrix[line][column];
                }
            }
        }
        return transposeMatrix;
    }

}
