package lapr1_2015;

import java.io.File;
import java.io.FileWriter;
import java.util.Formatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Group 2
 */
public class FileTools {

    /**
     * Get the file's data and ignore all empty lines.
     *
     * @param file The input file.
     * @return The input file's data.
     */
    public static String getFileData(File file) {

        String fileData = "";

        try {

            Scanner scan = new Scanner(file);

            String lineSeparator = Lapr1_2015.LINE_SEPARATOR;

            while (scan.hasNext()) {

                String line = scan.nextLine().trim();

                if (!line.equals("")) {

                    fileData += lineSeparator + line.trim();

                }

            }

            //Remove the very first lineSeparator.
            fileData = fileData.replaceFirst(lineSeparator, "");

            return fileData;

        } catch (Exception e) {

            return fileData;

        }

    }

    /**
     * Transform the line which contains the objective function into an array of
     * floats that represents the first line of the matrix.
     *
     * @param line The line that contains the objective function.
     * @param nrVariables The number of variables for the current problem
     * @return An array that represents the objective function.
     */
    public static float[] getObjectiveFunction(String line, int nrVariables) {

        int column;

        float[] newLine = new float[nrVariables + 1];

        Matcher m = Pattern.compile("(" + MathTools.VARIABLE_PATTERN + ")").matcher(line);

        while (m.find()) {

            String variable = m.group(1);

            float coeficient = MathTools.getVariableCoefficient(variable);

            column = MathTools.getXIndex(variable);

            newLine[column - 1] += coeficient;

        }

        newLine[nrVariables] = 0;

        return newLine;

    }

    /**
     * Transform the line which contains a restriction into an array of floats
     * that represents one restriction.
     *
     * @param line The line that contains the restriction.
     * @param matrixLine The index of the line that where we will be using the
     * output of this method. We need this to correctly fill S1, S2, etc...
     * @param nrVariables The number of variables for the current problem.
     * @return An array that represents the restriction.
     */
    public static float[] getRestriction(String line, int matrixLine, int nrVariables) {

        String parts[] = line.split("<=|>=");

        int column;

        float[] newLine = new float[nrVariables + 1];

        Matcher m = Pattern.compile("(" + MathTools.VARIABLE_PATTERN + ")").matcher(parts[0]);

        //Fill X1, X2, etc.
        while (m.find()) {

            String variable = m.group(1);

            float coefficient = MathTools.getVariableCoefficient(variable);

            column = MathTools.getXIndex(variable);

            if (column <= nrVariables) {

                newLine[column - 1] += coefficient;

            }

        }

        //Fill solution.
        newLine[nrVariables] = MathTools.parseToFloat(parts[1]);

        return newLine;

    }

    /**
     * Inspects a line, and based on the type of line (objective function or
     * restriction), adds information to the main matrix.
     *
     * @param line The line that will be read.
     * @param matrix The main matrix.
     * @param matrixLine The index of the line that will be filled.
     * @param nrVariables The number of variables for this problem
     * @return The next line index that will be filled.
     */
    public static int fillLine(String line, float[][] matrix, int matrixLine, int nrVariables) {

        line = Tools.removeSpaces(line);

        if (line.equals("")) {

            return matrixLine;

        }

        if (matrixLine == 0) {

            matrix[matrix.length - 1] = getObjectiveFunction(line, nrVariables);

        } else {

            matrix[matrixLine - 1] = getRestriction(line, matrixLine - 1, nrVariables);

        }

        return ++matrixLine;

    }

    /**
     * Check if a file is valid based on the file's data. To be valid, the first
     * line must be the objective function and the other lines should be
     * restrictions. This means the first line must have, for example, Z = 2X1
     * and the other lines must have, for example, X1 &lt;= 3. All variables
     * must be identified by X and a number after it. That number should not be
     * superior than 2. Also, there shouldn't be more than 2 white spaces
     * between each sequence of characters
     *
     * @param fileData The information inside the file. Break lines are
     * identified using on the line.separator system's property.
     * @param errorLog The file name where all errors will be recorded.
     * @return Whether the file is valid or not.
     */
    public static boolean isValid(String fileData, String errorLog) {

        String search = "   ";

        String signal = null;

        FileWriter logErrors = Log.openFile(errorLog, true);

        String[] lines = fileData.split(Lapr1_2015.LINE_SEPARATOR);

        int validLines = 0;

        int nrLines = Tools.getNumberOfLines(fileData);

        //Verify if the input file has more than one line.
        if (nrLines <= 1) {

            Log.insertLog("The file should have more than one line.", logErrors);

            validLines--;

        }

        for (int i = 0; i < lines.length; i++) {

            String line = lines[i];

            if (line.trim().equals("")) {

                return false;

            }

            if (line.contains(search)) {

                Log.insertLog("The line " + (i + 1) + " contains 3 or more consecutive white spaces.", logErrors);

                validLines--;

            }

            if (i == 0) {

                //It should be a valid function.
                if (!validateObjectiveFunction(line)) {

                    Log.insertLog("The objective function is malformed.", logErrors);

                    validLines--;

                }

            } else //It should be a valid restriction.
            {
                if (!validateRestriction(line)) {

                    Log.insertLog("The restriction at line " + (i + 1) + " is malformed.", logErrors);

                    validLines--;

                } else {

                    if (signal == null) {

                        signal = getRestrictionSignal(line);

                    }

                    String currentSignal = getRestrictionSignal(line);

                    if (!signal.equals(currentSignal)) {

                        Log.insertLog("Invalid signal found. Expected " + signal + " but "
                                + "found " + currentSignal + " in line " + (i + 1), logErrors);

                        validLines--;

                    }

                }
            }

            validLines++;

        }

        Log.closeFile(logErrors);

        return ((lines.length > 0) && (lines.length == validLines));

    }

    /**
     * Validate the objective function. To be valid, the objective function must
     * contain Z as the first character followed by an = operator and one or
     * more variables. All the variables must be in crescent order. For example
     * X1 + X2 + X3. If they are not ordered, it will be considered invalid.
     *
     * @param equation The equation that will be verified.
     * @return Whether the objective function is valid or not.
     */
    public static boolean validateObjectiveFunction(String equation) {

        equation = Tools.removeSpaces(equation);

        String pattern = "^Z=(" + MathTools.VARIABLE_PATTERN + ")([+-]" + MathTools.REAL_NUMBER_PATTERN + "[Xx]\\d{1,2}){0,}$";

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
     * Validate a restriction. To be valid, the restriction must start with one
     * or more variables followed by &lt;= operator and a number next to it.
     *
     * @param equation The equation that will be verified.
     * @return Whether this is a valid restriction or not.
     */
    public static boolean validateRestriction(String equation) {

        equation = Tools.removeSpaces(equation);

        String pattern = "^(" + MathTools.VARIABLE_PATTERN + ")([+-]" + MathTools.REAL_NUMBER_PATTERN
                + "[Xx]\\d{1,2}){0,}((<=|>=)[+-]?" + MathTools.REAL_NUMBER_PATTERN + ")$";

        return equation.matches(pattern);

    }

    /**
     * Write information to a file. If the file already exists, it will be
     * replaced with the new information.
     *
     * @param fileName The name of the file.
     * @param data The information that will be written to the file.
     * @return Whether it was successful or not.
     */
    public static boolean saveToFile(String fileName, String data) {

        try {

            Formatter outputFile = new Formatter(new File(fileName));

            outputFile.format(data);

            outputFile.close();

            return true;

        } catch (Exception e) {

            return false;

        }

    }

    /**
     * Get the number of variables of the problem based on the first line.
     *
     * @param problem The problem we're analyzing. Should be a formatted text
     * with break lines.
     * @return The number of variables of the problem.
     */
    public static int getNumberOfVariables(String problem) {

        String firstLine = problem.split(Lapr1_2015.LINE_SEPARATOR)[0];

        int nrVariables = 0;

        String variables = "";

        Matcher m = Pattern.compile("(" + MathTools.VARIABLE_PATTERN + ")").matcher(firstLine);

        while (m.find()) {

            String variable = m.group(1);

            //Get the variable name (Ex: 3X1 -> X1).
            String variableName = getVariableName(variable);

            //Check if that variable hasn't been found yet.
            if (!variables.contains(variableName + ";")) {

                variables += variableName + ";";

                nrVariables++;

            }

        }

        return nrVariables;

    }

    /**
     * Create a new matrix based on the received matrix and add columns for the
     * basic variables.
     *
     * @param matrix The matrix.
     * @return The old matrix with more columns that represent the basic
     * variables.
     */
    public static float[][] addBasicVariables(float[][] matrix) {

        int nrVariables = matrix[0].length - 1;

        int currentBasicVariable = nrVariables;

        float[][] newMatrix = new float[matrix.length][nrVariables + matrix.length];

        //Don't add the basic variables on the last line because it's the objective function.
        for (int i = 0; i < newMatrix.length; i++) {

            //Fill the new matrix with values existant in the matrix except the last column.
            for (int j = 0; j < matrix[i].length - 1; j++) {

                newMatrix[i][j] = matrix[i][j];

            }

            //Fill the solution (the last column).
            newMatrix[i][newMatrix[i].length - 1] = matrix[i][matrix[0].length - 1];

            //Now add the basic variable to the new matrix.
            if (i < newMatrix.length - 1) {

                newMatrix[i][currentBasicVariable] = 1;

                currentBasicVariable++;

            }
        }

        return newMatrix;
    }

    /**
     * Fill the matrix with the problem's non basic variables (X1, X2, etc).
     * This assumes that the file was already validated.
     *
     * @param problem The problem that it's going to be resolved.
     * @param nrVariables The number of variables for this problem.
     * @return The matrix with the data from the problem.
     * @see #isValid(java.lang.String)
     */
    public static float[][] fillMatrixWithNonBasicVariables(String problem, int nrVariables) {

        String lineSeparator = Lapr1_2015.LINE_SEPARATOR;

        int nrLines = problem.split(lineSeparator).length;

        float[][] matrix = new float[nrLines][nrVariables + 1];

        int matrixLine = 0;

        String[] lines = problem.split(lineSeparator);

        for (int i = 0; i < lines.length; i++) {

            matrixLine = FileTools.fillLine(lines[i], matrix, matrixLine, nrVariables);

        }

        return matrix;

    }

    public static String getRestrictionSignal(String line) {

        String signal = null;

        Matcher m = Pattern.compile("(<=|>=)").matcher(line);

        if (m.find()) {

            signal = m.group(1);

        }

        return signal;

    }

    public static String getVariableName(String variable) {

        if (variable.contains("X")) {

            return variable.substring(variable.indexOf("X"));

        } else {

            return variable.substring(variable.indexOf("x"));

        }

    }

}
