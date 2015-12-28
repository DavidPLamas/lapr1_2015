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
     * Gets the input file's data.
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

            //Removes the very first lineSeparator.
            fileData = fileData.replaceFirst(lineSeparator, "");

            return fileData;

        } catch (Exception e) {

            return fileData;

        }

    }

    /**
     * Gets the number of lines of the input file.
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
     * 
     * @param line The line that contains the objective function.
     * @param nrColumns The number of columns the 
     * @return 
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

    public static float[] getRestriction(String line, int matrixLine, int nrColumns) {

        String parts[] = line.split("<=");

        int column;
        
        float[] newLine = new float[nrColumns];

        Matcher m = Pattern.compile("(" + MathTools.VARIABLE_PATTERN + ")").matcher(parts[0]);

        //Fills X1, X2, etc.
        while (m.find()) {

            String variable = m.group(1);

            float coeficient = MathTools.getVariableCoefficient(variable);

            column = MathTools.getXIndex(variable);

            newLine[column - 1] = coeficient;
        }

        //Fills S1, S2, etc.
        newLine[matrixLine + 1] = 1;

        //Fills solution.
        newLine[nrColumns - 1] = Float.parseFloat(parts[1]);

        return newLine;
    }

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
                        //@todo log errors?

                        return false;
                    }

                } else if (!MathTools.validatesRestriction(line)) {
                    //@todo log errors?

                    return false;
                }

                nrLine++;
            }
        } catch (Exception e) {

            return false;

        }

        return (nrLine > 0);
    }

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
