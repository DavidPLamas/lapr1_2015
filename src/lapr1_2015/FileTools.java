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
     * Get the input file's data.
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
     * Get the number of lines of the input file.
     *
     * @param file The input file.
     * @return The number of lines of the input file.
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
     * Read the objective function from the input file and put it on a vector.
     *
     * @param line The line that contains the objective function.
     * @param nrColumns The number of columns the matrix.
     * @return A vector that contains the objective function's information.
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
     * Read a restricion from the input file and put it on a vector.
     *
     * @param line The line that contains the restriction.
     * @param matrixLine The index of the line that will be filled with the
     * restriction's information.
     * @param nrColumns The number of columns of the matrix.
     * @return A vector that contains the restriction's information.
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
     * @return ++matrixLine if the line is valid or matrixLine is the line is
     * not valid.
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
     * Verify in the input file is valid.
     *
     * @param file The input file.
     * @return True if the file is valid or false if the file is not valid.
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
     * Write information to the output file.
     *
     * @param fileName The name of the output file.
     * @param data The information that will be written on the output file.
     * @return True if sucessfully executed or false if unsucessfully executed.
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
