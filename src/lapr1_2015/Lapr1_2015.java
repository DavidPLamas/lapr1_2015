package lapr1_2015;

import java.io.File;
import java.util.Scanner;

/**
 * @author Group 2
 */
public class Lapr1_2015 {

    static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static final String LOG_ERRORS = "log_erros.txt";

    /**
     * Apply the simplex method to a problem. This assumes the matrix is already
     * filled with the data from the file.
     *
     * @param matrix The matrix that will be used to receive and manipulate the
     * necessary data to solve the problem.
     * @param outputFileName The file that will contain the solution of the
     * problem.
     * @param nrVar The number of variables in the problem.
     * @param inputFileData The information existent in the input file.
     * @see #fillMatrix(float[][], java.io.File).
     */
    public static void applySimplexMethod(float[][] matrix, String outputFileName, int nrVar, String inputFileData) {

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

    /**
     * Fill the matrix with the problem's information. This assumes that the
     * file was already validated.
     *
     * @param matrix The matrix.
     * @param file The input file.
     * @param nrVariables The number of variables for this problem
     * @see FileTools#isValid(java.io.File).
     */
    public static void fillMatrix(float[][] matrix, File file, int nrVariables) {

        int matrixLine = 0;

        try {

            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {

                String line = scan.nextLine();

                matrixLine = FileTools.readLine(line, matrix, matrixLine, nrVariables);

            }

        } catch (Exception e) {

            Tools.printError(e.getMessage());

            Tools.printError("An error ocurred while reading the file.");

        }

    }

    /**
     * Create a format for the main matrix to be written on the main file.
     *
     * @param matrix The matrix.
     * @param nrVar The number of variables in the problem.
     * @return The formatted matrix.
     */
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

    /**
     * Create the format type to use with String.format. This just helps to make
     * sure all the lines have the same output format.
     *
     * @param nrColumns The number of columns in the matrix.
     * @return The format of a line.
     */
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

    /**
     * Create a header for the main matrix to be written in the output file.
     *
     * @param nrColumns The number of columns in the matrix.
     * @param nrVar The number of variables of the problem.
     * @param lineFormat The format that will be used to write a line.
     * @param lineArgs lineArgs.
     * @return The header of the main matrix.
     */
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

    /**
     * Find the values of the variables on the final matrix and format that
     * information.
     *
     * @param matrix The matrix that will be analyzed.
     * @param nrVar The number of variables of the problem.
     * @return The values of the variables conveniently formatted.
     */
    public static String findVariableValues(float[][] matrix, int nrVar) {

        String output;

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

    /**
     * Get the Z Value.
     *
     * @param matrix The matrix that will be analyzed.
     * @return The Z value.
     */
    public static String findZValue(float[][] matrix) {

        float zValue = matrix[0][matrix[0].length - 1];

        return "Z = " + String.format("%.2f", zValue);

    }

    /**
     * Resolve a maximization problem.
     *
     * @param matrix The matrix that will be used to solve the problem.
     * @param outputFileName The file that will contain the solution of the
     * problem.
     * @param nrVar The number of variables in the problem.
     * @param inputFileData The information existent in the input file.
     */
    public static void maximizeFunction(float[][] matrix, String outputFileName, int nrVar, String inputFileData) {

        applySimplexMethod(matrix, outputFileName, nrVar, inputFileData);

    }

    /**
     * Resolve a minimization problem.
     *
     * @param matrix The matrix that will be used to solve the problem.
     * @param outputFileName The file that will contain the solution of the
     * problem.
     * @param nrVar The number of variables in the problem.
     * @param inputFileData The information existent in the input file.
     */
    public static void minimizeFunction(float[][] matrix, String outputFileName, int nrVar, String inputFileData) {

        float[][] newMatrix = MathTools.transposeMatrix(matrix);

        applySimplexMethod(newMatrix, outputFileName, nrVar, inputFileData);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Verify if the program received two arguments.
        if (args.length < 2) {

            Tools.printError("The program needs two arguments to work.");

            return;

        }

        String inputFileName = args[0];

        String outputFileName = args[1];

        File inputFile = new File(inputFileName);

        //Verify if the input file exists.
        if (!inputFile.exists() || inputFile.isDirectory()) {

            Tools.printError(String.format("The file %s doesn't exist.", inputFileName));

            return;

        }

        String inputFileData = FileTools.getFileData(inputFile);

        int nrLines = Tools.getNumberOfLines(inputFileData);

        //Verify if the input file is not empty.
        if (nrLines <= 0) {

            Tools.printError(String.format("The file %s shouldn't be empty.", inputFileName));

            return;

        }

        //Verify if the input file is valid.    
        if (!FileTools.isValid(inputFile)) {

            Tools.printError(String.format("The file %s is not valid.", inputFileName));

            return;

        }

        //@todo David - Continuar aqui. Criar função para determinar número de variáveis.
        int nrVar = FileTools.getNumberOfVariables(inputFileData);

        float[][] matrix = new float[nrLines][nrLines + nrVar];

        fillMatrix(matrix, inputFile, nrVar);

        String secondLine = inputFileData.split(LINE_SEPARATOR)[1];

        if (secondLine.contains("<=") || secondLine.contains("≤")) {

            maximizeFunction(matrix, outputFileName, nrVar, inputFileData);

        }

        if (secondLine.contains(">=") || secondLine.contains("≥")) {

            minimizeFunction(matrix, outputFileName, nrVar, inputFileData);

        }

    }

}
