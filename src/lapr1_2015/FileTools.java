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

            String lineSeparator = System.getProperty("line.separator");

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

            //newLine[column - 1] += MathTools.calculateSymmetric(coeficient);
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

            float coeficient = MathTools.getVariableCoefficient(variable);

            column = MathTools.getXIndex(variable);

            if (column <= nrVariables) {

                newLine[column - 1] += coeficient;

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

            matrix[matrix.length -1] = getObjectiveFunction(line, nrVariables);

        } else {

            matrix[matrixLine -1] = getRestriction(line, matrixLine -1, nrVariables);
            
        }

        return ++matrixLine;

    }

    /**
     * Check if a file is valid based on the file's data. To be valid, the first line must be the
     * objective function and the other lines should be restrictions. This
     * means, the first line must have, for example, Z = 2X1 and the other lines
     * must have, for example, X1 &lt;= 3. All variables must be identified by X
     * and a number after it. That number should not be superior than 2.
     * Also, there shouldn't be more than 2 white spaces between each sequence of characters
     *
     * @param fileData The information inside the file. Break lines are identified
     * using on the line.separator system's property.
     * @return Whether the file is valid or not.
     */
    public static boolean isValid(String fileData) {

        int nrLine = 0;

        String search = "   ";

        FileWriter logErrors = Log.openFile(Lapr1_2015.LOG_ERRORS, false);
        
        String[] lines = fileData.split(System.getProperty("line.separator"));
        
        int validLines = 0;
        
        for(int i = 0; i < lines.length; i++){
            String line = lines[i];
            
            if(line.trim().equals("")){
                return false;
            }
            
            if (line.contains(search)) {
                Log.insertLog("The line " + (i+1) +" contains 3 or more consecutive white spaces.", logErrors);
                validLines--;
            }

            if (i == 0) {

                if (!MathTools.validateObjectiveFunction(line)) {
                    Log.insertLog("The objective function is malformed.", logErrors);
                    validLines--;
                }

            } else if (!MathTools.validateRestriction(line)) {
                Log.insertLog("The restriction at line " + (i+1) + " is malformed.", logErrors);
                validLines--;
            }
            
            validLines++;
        }
        
        Log.closeFile(logErrors);
        
        return ( (lines.length > 0) && (lines.length == validLines ));

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

        String firstLine = problem.split(System.getProperty("line.separator"))[0];

        int nrVariables = 0;

        String variables = "";

        Matcher m = Pattern.compile("(" + MathTools.VARIABLE_PATTERN + ")").matcher(firstLine);

        while (m.find()) {

            String variable = m.group(1);

            //Get the variable name (Ex: 3X1 -> X1).
            String variableName = variable.substring(variable.indexOf("X"));

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
     * basic variables
     * 
     * @param matrix The matrix
     * @return The old matrix with more columns that represent the basic variables
     */
    public static float [][] addBasicVariables(float [][] matrix){
        int nrVariables = matrix[0].length - 1;
        int currentBasicVariable = nrVariables;
        float[][] newMatrix = new float[matrix.length][nrVariables + matrix.length];
        
        //Dont add the basic variables on the last line because it's the objective function
        for(int i=0; i < newMatrix.length; i++){
            
            //Fill the new matrix with values existant in the matrix except the last column
            for (int j = 0; j < matrix[i].length - 1; j++) {
                newMatrix[i][j] = matrix[i][j];
            }
            //Fill the solution (the last column)
            newMatrix[i][newMatrix[i].length - 1] = matrix[i][matrix[0].length -1];
            
            //Now add the basic variable to the new matrix
            if(i < newMatrix.length -1){
               newMatrix[i][currentBasicVariable] = 1;
               currentBasicVariable++;
            }
        }
        
        return newMatrix;
    }
    
    /**
     * Fill the matrix with the problem's non basic variables (X1, X2, etc). This assumes that the
     * file was already validated.
     *
     * @param problem The problem that it's going to be resolved.
     * @param nrVariables The number of variables for this problem.
     * @return The matrix with the data from the problem
     * @see #isValid(java.lang.String)  
     */
    public static float[][] fillMatrixWithNonBasicVariables(String problem, int nrVariables) {
        String lineSeparator = System.getProperty("line.separator");
        int nrLines = problem.split(lineSeparator).length;
        
        float[][] matrix = new float[nrLines][nrVariables + 1];
        
        int matrixLine = 0;
        String[] lines = problem.split(lineSeparator);
        
        for(int i=0; i < lines.length; i++){
            matrixLine = FileTools.fillLine(lines[i], matrix, matrixLine, nrVariables);
        }
        
        return matrix;
        

    }

}
