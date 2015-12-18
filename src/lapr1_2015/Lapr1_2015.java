package lapr1_2015;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author Group 2
 *
 */
public class Lapr1_2015 {

    static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static void simplexMethod(float[][] matrix, File inputFile, String outputFileName, int nrVar, String inputFileData) {

        fillMatrix(matrix, inputFile);

        int pivotColumn = MathTools.findPivotColumn(matrix);

        int pivotLine = MathTools.findPivotLine(matrix, pivotColumn);

        String outputFileData = inputFileData
                + LINE_SEPARATOR
                + LINE_SEPARATOR
                + getFormattedMatrix(matrix, nrVar);

        while (pivotColumn >= 0 && pivotLine >= 0) {

            float pivot = matrix[pivotLine][pivotColumn];

            matrix[pivotLine] = MathTools.multiplyLineByScalar(matrix, pivotLine, 1 / pivot);

            for (int i = 0; i < matrix.length; i++) {

                if (i == pivotLine) {

                    continue;
                }

                float scalar = MathTools.calculateSymmetric(matrix[i][pivotColumn]);

                matrix[i] = MathTools.addTwoLinesWithScalar(matrix, i, pivotLine, scalar);
            }

            outputFileData += LINE_SEPARATOR + getFormattedMatrix(matrix, nrVar);

            pivotColumn = MathTools.findPivotColumn(matrix);

            pivotLine = MathTools.findPivotLine(matrix, pivotColumn);
        }

        outputFileData += LINE_SEPARATOR + "• = Pivot"
                + LINE_SEPARATOR + findZValue(matrix)
                + LINE_SEPARATOR + findVariableValues(matrix, nrVar);

        System.out.println(findZValue(matrix) + LINE_SEPARATOR + findVariableValues(matrix, nrVar));
        FileTools.saveToFile(outputFileName, outputFileData);

    }

    public static void fillMatrix(float[][] matrix, File file) {

        int matrixLine = 0;

        try {

            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {

                String line = scan.nextLine();

                matrixLine = FileTools.readLine(line, matrix, matrixLine);
            }

        } catch (Exception e) {

            Tools.printError("An error ocurred while reading the file.");
        }
    }

    public static String getFormattedMatrix(float[][] matrix, int nrVar) {

        String output = "";

        int nrColumns = matrix[0].length;
        String lineFormat = getOutputLineFormat(nrColumns);
        String[] lineArgs = new String[nrColumns];

        output += getOutputMatrixHeader(nrColumns, nrVar, lineFormat, lineArgs);

        int pivotColumn = MathTools.findPivotColumn(matrix);
        int pivotLine = MathTools.findPivotLine(matrix, pivotColumn);

        for (int i = 0; i < matrix.length; i++) {
            float[] line = matrix[i];
            for (int j = 0; j < line.length; j++) {
                if (pivotLine == i && pivotColumn == j) {
                    lineArgs[j] = String.format("%s%.2f", "•", line[j]);
                } else {
                    lineArgs[j] = String.format("%.2f", line[j]);
                }

            }
            output += String.format(lineFormat, (Object[]) lineArgs);
        }

        return output + "%n";
    }

    public static String getOutputLineFormat(int nrColumns) {
        String format = "";

        for (int i = 0; i < nrColumns; i++) {
            if (i != 0) {
                format += "|";
            }
            format += "%8s";
        }
        return format + "%n";

    }

    public static String getOutputMatrixHeader(int nrColumns, int nrVar, String lineFormat, String[] lineArgs) {
        String header = "";
        for (int i = 1; i <= nrColumns; i++) {
            if (i <= nrVar) {
                lineArgs[i - 1] = "X" + i;
            } else if (i == nrColumns) {
                lineArgs[i - 1] = "SOL";
            } else {
                lineArgs[i - 1] = "S" + (i - nrVar);
            }
        }

        header += String.format(lineFormat, (Object[]) lineArgs);

        for (int i = 0; i < (nrColumns * 9); i++) {
            header += "-";
        }

        header += "%n";

        return header;

    }

    public static String findVariableValues(float[][] matrix, int nrVar) {
        String output = "";
        String[] indexes = {"(", "("};
        float[] indexValues = new float[matrix[0].length - 1];
        int lastColumnIndex = matrix[0].length - 1;

        for (int i = 1; i < matrix.length; i++) {
            for (int j = 0; j < lastColumnIndex; j++) {
                if (matrix[i][j] == 1) {
                    indexValues[j] = matrix[i][lastColumnIndex];
                    break;
                }
            }
        }

        for (int i = 1; i <= matrix[0].length - 1; i++) {
            if (i <= nrVar) {
                indexes[0] += "X" + i + ", ";
            } else {
                indexes[0] += "S" + (i - nrVar) + ", ";
            }
            indexes[1] += String.format("%.2f", indexValues[i - 1]) + ", ";
        }
        indexes[0] = indexes[0].substring(0, indexes[0].length() - 2) + ")";
        indexes[1] = indexes[1].substring(0, indexes[1].length() - 2) + ")";

        output = indexes[0] + " = " + indexes[1];

        return output;
    }

    public static String findZValue(float[][] matrix) {
        String output = "";

        //Get the zValue
        float zValue = matrix[0][matrix[0].length - 1];

        return "Z = " + String.format("%.2f", zValue);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Verifies if the program received two arguments.
        if (args.length < 2) {

            Tools.printError("The program needs two arguments to work.");

            return;

        }

        String inputFileName = args[0];

        String outputFileName = args[1];

        File inputFile = new File(inputFileName);

        //Verifies if the input file exists.
        if (!inputFile.exists() || inputFile.isDirectory()) {

            Tools.printError(String.format("The file %s doesn't exist.", inputFileName));

            return;
        }

        String inputFileData = FileTools.getFileData(inputFile);

        int nrLines = Tools.getNumberOfLines(inputFileData);

        if (nrLines <= 0) {

            Tools.printError(String.format("The file %s shouldn't be empty.", inputFileName));

            return;
        }

        if (!FileTools.isValid(inputFile)) {

            Tools.printError(String.format("The file %s is not valid.", inputFileName));

            return;
        }

        int nrVar = 2;

        float[][] matriz = new float[nrLines][nrLines + nrVar];

        simplexMethod(matriz, inputFile, outputFileName, nrVar, inputFileData);

    }

}
