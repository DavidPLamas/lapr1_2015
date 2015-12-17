package lapr1_2015;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Group 2
 */
public class FileTools {

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

    public static float[] getObjectiveFunction(String line, int nrColumns) {
        int column;
        float[] newLine = new float[nrColumns];

        Matcher m = Pattern.compile("(" + MathTools.VARIABLE_PATTERN + ")").matcher(line);

        while (m.find()) {

            String variable = m.group(1);

            float coeficient = MathTools.getVariableCoeficient(variable);

            column = MathTools.getXIndex(variable);

            newLine[column - 1] = MathTools.calculateSimetric(coeficient);
        }

        newLine[nrColumns - 1] = 0;

        return newLine;
    }

    public static float[] getRestriction(String line, int matrixLine, int nrColumns) {

        String parts[] = line.split("<=");

        int col;
        float[] newLine = new float[nrColumns];

        Matcher m = Pattern.compile("(" + MathTools.VARIABLE_PATTERN + ")").matcher(parts[0]);

        //Fills X1, X2, etc.
        while (m.find()) {

            String variable = m.group(1);

            float coeficient = MathTools.getVariableCoeficient(variable);

            col = MathTools.getXIndex(variable);

            newLine[col - 1] = coeficient;
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

                    if (!MathTools.validatesObjectiveFunction(Tools.removeSpaces(line))) {
                        //@todo log errors?

                        return false;
                    }

                } else if (!MathTools.validatesRestriction(Tools.removeSpaces(line))) {
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
}
