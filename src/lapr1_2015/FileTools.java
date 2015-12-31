package lapr1_2015;

import java.io.File;
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
     * Find the number of lines of a specific file, ignoring all empty lines.
     *
     * @param file The file.
     * @return The number of lines
     */
    public static int getNumberOfLines(File file) {

        int nrLines = 0;

        try {

            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {

                String line = scan.nextLine();

                if (!line.trim().equals("")) {

                    nrLines++;
                }
            }

        } catch (Exception e) {

        }

        return nrLines;

    }

    /**
     * Transform the line which contains the objective function into an array of floats
     * that represents the first line of the matrix
     *
     * @param line The line that contains the objective function.
     * @param nrColumns The number of columns we're from the main matrix (Number of variables + number of restrictions + 1).
     * @return An array that represents the objective function.
     */
    public static float[] getObjectiveFunction(String line, int nrColumns) {

        int column;

        float[] newLine = new float[nrColumns];

        Matcher m = Pattern.compile("(" + MathTools.VARIABLE_PATTERN + ")").matcher(line);

        while (m.find()) {

            String variable = m.group(1);

            float coeficient = MathTools.getVariableCoefficient(variable);

            column = MathTools.getXIndex(variable);

            newLine[column - 1] = MathTools.calculateSymmetric(coeficient);
            
        }

        newLine[nrColumns - 1] = 0;

        return newLine;

    }

    /**
     * Transform the line which contains a restriction into an array of floats
     * that represents one restriction
     *
     * @param line The line that contains the restriction.
     * @param matrixLine The index of the line that where we will be using the output of this method.
     *                   We need this to correctly fill the S1, S2, etc...
     * @param nrColumns The number of columns of the main matrix.
     * @return An array that represents the restriction.
     */
    public static float[] getRestriction(String line, int matrixLine, int nrColumns) {

        String parts[] = line.split("<=");

        int column;

        float[] newLine = new float[nrColumns];

        Matcher m = Pattern.compile("(" + MathTools.VARIABLE_PATTERN + ")").matcher(parts[0]);

        //Fill X1, X2, etc.
        while (m.find()) {

            String variable = m.group(1);

            float coeficient = MathTools.getVariableCoefficient(variable);

            column = MathTools.getXIndex(variable);

            newLine[column - 1] = coeficient;

        }

        //Fill S1, S2, etc.
        newLine[matrixLine + 1] = 1;

        //Fill solution.
        newLine[nrColumns - 1] = Float.parseFloat(parts[1]);

        return newLine;

    }

    /**
     * Inspects a line, and based on the type of line (objective function or
     * restriction), adds information to the main matrix.
     *
     * @param line The line that will be read.
     * @param matrix The main matrix.
     * @param matrixLine The index of the line that will be filled.
     * @return The next line that will be filled
     */
    public static int readLine(String line, float[][] matrix, int matrixLine) {

        line = Tools.removeSpaces(line);

        if (line.equals("")) {

            return matrixLine;

        }

        if (matrixLine == 0) {

            matrix[matrixLine] = getObjectiveFunction(line, matrix[matrixLine].length);

        } else {

            matrix[matrixLine] = getRestriction(line, matrixLine, matrix[matrixLine].length);
        }

        return ++matrixLine;

    }

    /**
     * Check if a file is valid. 
     * To be valid, the first line must be the objective function and the other lines should be restrictions. 
     * This means, the first line must have, for example, Z = 2X1 and the other lines must have, 
     * for example, X1 &lt;= 3. 
     * All variables must be identified by X and a number after it. That number should not be superior than 2.
     * 
     *
     * @param file The file.
     * @return Whether the file is valid or not.
     */
    public static boolean isValid(File file) {

        int nrLine = 0;

        try {

            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {

                String line = scan.nextLine();

                if (line.equals("")) {

                    continue;
                }

                if (nrLine == 0) {

                    if (!MathTools.validatesObjectiveFunction(line)) {

                        return false;
                    }

                } else if (!MathTools.validatesRestriction(line)) {

                    return false;

                }

                nrLine++;
                
            }
            
        } catch (Exception e) {

            return false;

        }

        return (nrLine > 0);

    }

    /**
     * Write information to a file. If the file already exists, it will be replaced with the new
     * informtation
     *
     * @param fileName The name of the file.
     * @param data The information that will be written to the file.
     * @return Whether it was successfull or not
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

}
