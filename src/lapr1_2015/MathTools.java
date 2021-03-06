package lapr1_2015;

/**
 * @author Group 2
 */
public class MathTools {

    /**
     * The regex pattern for a REAL number. This means it can be either an
     * integer, decimal or fractional number.
     */
    public static final String REAL_NUMBER_PATTERN = "(([0-9]+\\.[0-9]{2})|([0-9]+/[1-9]\\d*)|([0-9]*))";

    /**
     * The regex pattern for any variable. A variable contains (or not) a signal
     * (+ or -), followed by a number. Following that, there should be an X
     * followed by a number from 1 to 99, which indicates the variable index.
     *
     * @see #REAL_NUMBER_PATTERN
     */
    public static final String VARIABLE_PATTERN = "[+-]?" + REAL_NUMBER_PATTERN + "[Xx]\\d{1,2}";

    /**
     * Get the coefficient value of a certain variable. If there is no X in the
     * variable, an error will be thrown.
     *
     * @param variable Variable from where the coefficient will be taken.
     * @return The coefficient.
     * @throws Error An error
     */
    public static float getVariableCoefficient(String variable) {

        String coefficient;

        int posX = variable.indexOf("X");

        if (posX == -1) {

            posX = variable.indexOf("x");

        }

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

        return parseToFloat(coefficient);

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
     * Get the index of a variable.
     *
     * @param variable The variable that will be used.
     * @return The index of the variable. If there is no X in the variable, will
     * return -1.
     */
    public static int getXIndex(String variable) {

        int XPos = variable.indexOf("X");

        if (XPos == -1) {
            
            XPos = variable.indexOf("x");
            
        }

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

            if (matrix[lineIndex][i] == 0) {
                
                continue;
                
            }
            
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

        for (int i = 0; i < matrix[0].length - 1; i++) {

            if (matrix[matrix.length - 1][i] < minor) {

                minor = matrix[matrix.length - 1][i];

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
     * line.
     */
    public static int findPivotLine(float[][] matrix, int column) {

        //Check if the column parameter is valid.
        if (column < 0) {

            return -1;

        }

        float minor = Float.MAX_VALUE;

        int line = -1;

        int lastColumn = matrix[0].length - 1;

        for (int i = 0; i < matrix.length - 1; i++) {

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
     * Calculate the transposed matrix of a matrix received as a parameter.
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

    /**
     * Parse any number to float. This differs from Float.parseFloat because it
     * accepts numbers formatted as fractions (Ex: 5/2).
     *
     * @param number The number to be parsed to float
     * @return The number formated to float
     */
    public static float parseToFloat(String number) {
        
        number = number.replaceAll(",", ".");
        
        if (number.contains("/")) {
            
            String[] parts = number.split("/");
            
            return (parseToFloat(parts[0]) / parseToFloat(parts[1]));
            
        } else {
            
            return Float.parseFloat(number);
            
        }
        
    }

}
