package lapr1_2015;

import java.io.File;
import java.util.Scanner;

/**
 * @author Group 2
 */
public class Lapr1_2015 {

    /**
     * The line separator identifier.
     */
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * Stores equations parallel to the objective function. To be used in the graph.
     */
    private static String intermediateEquations = "";

    /**
     * The name of the file that will contain the validation errors.
     */
    public static final String ERROR_LOG = "errors.txt";

    /**
     * Apply the simplex method to a problem and write the output to the
     * outputFileName file.
     *
     * @param matrix The matrix that will be used to receive and manipulate the
     * necessary data to solve the problem.
     * @param outputFileName The file that will contain the solution of the
     * problem.
     * @param nrVar The number of variables in the problem.
     * @param problem The problem.
     * @param variables The variable names for the output header.
     * @param outputHeader Information that will be prepended to the information
     * gathered on this method
     * @return The final matrix.
     */
    public static float[][] applySimplexMethod(float[][] matrix, String outputFileName, int nrVar,
            String problem, String[] variables, String outputHeader) {

        int pivotColumn = MathTools.findPivotColumn(matrix);

        int pivotLine = MathTools.findPivotLine(matrix, pivotColumn);

        String outputFileData = problem
                + LINE_SEPARATOR
                + LINE_SEPARATOR
                + outputHeader
                + getFormattedMatrix(matrix, nrVar, variables);

        while (pivotColumn >= 0 && pivotLine >= 0) {

            intermediateEquations += problem.split(LINE_SEPARATOR)[0].replace("Z", String.format("%.2f", getZValue(matrix)))
                    + LINE_SEPARATOR;

            float pivot = matrix[pivotLine][pivotColumn];

            float scalar;

            //To make sure the pivot value will be changed to 0, we need to make sure if
            //it wasn't negative first.
            if (matrix[pivotLine][pivotColumn] < 0) {

                scalar = MathTools.calculateSymmetric(1 / pivot);

            } else {

                scalar = 1 / pivot;

            }

            matrix[pivotLine] = MathTools.multiplyLineByScalar(matrix, pivotLine, scalar);

            for (int i = 0; i < matrix.length; i++) {

                if (i == pivotLine) {

                    continue;

                }

                scalar = MathTools.calculateSymmetric(matrix[i][pivotColumn]);

                matrix[i] = MathTools.addTwoLinesWithScalar(matrix, i, pivotLine, scalar);

            }

            outputFileData += LINE_SEPARATOR + getFormattedMatrix(matrix, nrVar, variables);

            pivotColumn = MathTools.findPivotColumn(matrix);

            pivotLine = MathTools.findPivotLine(matrix, pivotColumn);

        }

        outputFileData += LINE_SEPARATOR + "• = Pivot"
                + LINE_SEPARATOR + formatZValue(matrix)
                + LINE_SEPARATOR + findVariableValues(matrix, nrVar, variables);

        FileTools.saveToFile(outputFileName, outputFileData);

        return matrix;

    }

    /**
     * Create a format for the main matrix to be written on the main file.
     *
     * @param matrix The matrix.
     * @param nrVar The number of variables in the problem.
     * @param variables The variable names for the header.
     * @return The formatted matrix.
     */
    public static String getFormattedMatrix(float[][] matrix, int nrVar, String[] variables) {

        String output = "";

        int nrColumns = matrix[0].length;

        String lineFormat = getOutputLineFormat(nrColumns);

        String[] lineArgs = new String[nrColumns];

        output += getOutputMatrixHeader(nrColumns, nrVar, lineFormat, variables);

        int pivotColumn = MathTools.findPivotColumn(matrix);

        int pivotLine = MathTools.findPivotLine(matrix, pivotColumn);

        for (int i = 0; i < matrix.length; i++) {

            if (i == matrix.length - 1) {

                for (int j = 0; j < (nrColumns * 9); j++) {

                    output += "-";

                }

                output += "%n";

            }

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
     * Create a header for the main matrix to be written
     *
     * @param nrColumns The number of columns in the matrix.
     * @param nrVar The number of variables of the problem.
     * @param lineFormat The format that will be used to write a line.
     * @param variables The variable names.
     * @return The header of the main matrix.
     */
    public static String getOutputMatrixHeader(int nrColumns, int nrVar, String lineFormat, String[] variables) {

        String header = "";

        header += String.format(lineFormat, (Object[]) variables);

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
     * @param variableNames The variable names for this problem.
     * @return The values of the variables conveniently formatted.
     */
    public static String findVariableValues(float[][] matrix, int nrVar, String[] variableNames) {

        String output;

        String[] indexes = {"(", "("};

        //Check if the problem envolves minimization or maximization.
        if (variableNames[0].equals("Y1")) {

            for (int i = matrix[0].length - nrVar - 1; i < matrix[0].length - 1; i++) {

                indexes[0] += variableNames[i] + ", ";

                indexes[1] += String.format("%.2f", matrix[matrix.length - 1][i]) + ", ";

            }

        } else {

            float[] indexValues = new float[matrix[0].length - 1];

            int lastColumnIndex = matrix[0].length - 1;

            for (int i = 0; i < matrix.length - 1; i++) {

                for (int j = 0; j < lastColumnIndex; j++) {

                    if (matrix[i][j] == 1) {

                        indexValues[j] = matrix[i][lastColumnIndex];

                        break;
                    }

                }

            }

            for (int i = 1; i <= matrix[0].length - 1; i++) {

                indexes[0] += variableNames[i - 1] + ", ";

                indexes[1] += String.format("%.2f", indexValues[i - 1]) + ", ";

            }

        }

        indexes[0] = indexes[0].substring(0, indexes[0].length() - 2) + ")";

        indexes[1] = indexes[1].substring(0, indexes[1].length() - 2) + ")";

        output = indexes[0] + " = " + indexes[1];

        return output;

    }

    /**
     * Get the Z Value and format it.
     *
     * @param matrix The matrix that will be analyzed.
     * @return The Z value.
     */
    public static String formatZValue(float[][] matrix) {

        return "Z = " + String.format("%.2f", getZValue(matrix));

    }

    /**
     * Find the current value of the Z
     * 
     * @param matrix The matrix
     * @return 
     */
    private static float getZValue(float[][] matrix) {

        float zValue = matrix[matrix.length - 1][matrix[0].length - 1];

        return zValue;

    }

    /**
     * Resolve a maximization problem.
     *
     * @param matrix The matrix that will be used to solve the problem.
     * @param outputFileName The file that will contain the solution of the
     * problem.
     * @param nrVar The number of variables in the problem.
     * @param problem The information existent in the input file.
     * @return The matrix after the simplex method
     */
    public static float[][] maximizeFunction(float[][] matrix, String outputFileName, int nrVar, String problem) {

        float[][] fullMatrix = FileTools.addBasicVariables(matrix);

        fullMatrix[fullMatrix.length - 1] = MathTools.multiplyLineByScalar(fullMatrix, fullMatrix.length - 1, -1);

        String[] variables = new String[fullMatrix[0].length];

        for (int i = 1; i <= variables.length; i++) {

            if (i <= nrVar) {

                variables[i - 1] = "X" + i;

            } else if (i == variables.length) {

                variables[i - 1] = "SOL";

            } else {

                variables[i - 1] = "S" + (i - nrVar);

            }

        }

        float[][] finalMatrix = applySimplexMethod(fullMatrix, outputFileName, nrVar, problem, variables, "");

        System.out.println(formatZValue(finalMatrix) + LINE_SEPARATOR + findVariableValues(finalMatrix, nrVar, variables));

        if (nrVar == 2) {

            String graphDetails[] = askForGraphDetails();

            if (!graphDetails[0].equals("")) {

                //Set the last Z value.
                String equations = problem.replace("Z", String.format("%.2f", getZValue(finalMatrix)));

                float x = getNonBasicVariableValue(finalMatrix, "x1", nrVar, variables);

                float y = getNonBasicVariableValue(finalMatrix, "x2", nrVar, variables);

                //Append the intermediate equations.
                equations = intermediateEquations + equations;

                Graph.makeGraph(graphDetails[0], graphDetails[1], graphDetails[2], equations, x, y);

            }

        }

        return finalMatrix;

    }

    /**
     * Resolve a minimization problem.
     *
     * @param matrix The matrix that will be used to solve the problem.
     * @param outputFileName The file that will contain the solution of the
     * problem.
     * @param nrVar The number of variables in the problem.
     * @param problem The information existent in the input file.
     * @return The matrix after the simplex method.
     */
    public static float[][] minimizeFunction(float[][] matrix, String outputFileName, int nrVar, String problem) {

        //Prepare data to write to the file (it's possible to see the matrix before it's transposed).
        String[] firstVariables = new String[nrVar + 1];

        for (int i = 0; i < firstVariables.length - 1; i++) {

            firstVariables[i] = "X" + (i + 1);

        }

        firstVariables[firstVariables.length - 1] = "SOL";

        String outputFileData = getFormattedMatrix(matrix, nrVar, firstVariables) + LINE_SEPARATOR;

        float[][] transposedMatrix = MathTools.transposeMatrix(matrix);

        float[][] fullMatrix = FileTools.addBasicVariables(transposedMatrix);

        fullMatrix[fullMatrix.length - 1] = MathTools.multiplyLineByScalar(fullMatrix, fullMatrix.length - 1, -1);

        String[] variables = new String[fullMatrix[0].length];

        int index = 1;

        for (int i = 0; i < variables.length; i++) {

            if (i == variables.length - 1) {

                variables[i] = "SOL";

            } else if (i < matrix.length - 1) {

                variables[i] = "Y" + index;

                index++;

            } else {

                if (i == matrix.length - 1) {

                    index = 1;

                }

                variables[i] = "X" + index;

                index++;

            }

        }

        float[][] finalMatrix = applySimplexMethod(fullMatrix, outputFileName, nrVar, problem, variables, outputFileData);

        System.out.println(formatZValue(finalMatrix) + LINE_SEPARATOR + findVariableValues(finalMatrix, nrVar, variables));

        if (nrVar == 2) {

            String graphDetails[] = askForGraphDetails();

            if (!graphDetails[0].equals("")) {

                //Set the last Z value.
                String equations = problem.replace("Z", String.format("%.2f", getZValue(finalMatrix)));

                float x = getNonBasicVariableValue(finalMatrix, "x1", nrVar, variables);

                float y = getNonBasicVariableValue(finalMatrix, "x2", nrVar, variables);

                //Append the intermediate equations.
                equations = intermediateEquations + equations;

                Graph.makeGraph(graphDetails[0], graphDetails[1], graphDetails[2], equations, x, y);

            }

        }

        return finalMatrix;

    }

    /**
     * Get the value of a non basic variable (X1, X2, X3, etc).
     * 
     * @param matrix The matrix that will be used to solve the problem.
     * @param variable The variable designation (X1, X2, etc).
     * @param nrVariables The number of variables in the problem.
     * @param variables The variable names.
     * @return The value of a variable or 0 if not found
     */
    public static float getNonBasicVariableValue(float[][] matrix, String variable, int nrVariables, String[] variables) {

        int index = Tools.getPositionOf(variables, variable);

        if(index < 0){
            return 0;
        }
        //Check for minimization problem.
        if (variables[0].equals("Y1")) {

            return matrix[matrix.length - 1][index];

        } else {

            for (int i = 0; i < matrix.length; i++) {

                if (matrix[i][index] == 1) {

                    return matrix[i][matrix[i].length - 1];

                }

            }

        }

        return 0;

    }

    /**
     * Ask the user if he wants to save the information in a graph.
     * If true, he will be prompted for a graph name and extension.
     *
     * @return All the information gathered by the user
     *          The position 0 contains the Graph name
     *          The position 1 contains the Graph name + extension (can be used for filenames)
     *          The position 2 contains the terminal for gnuplot
     */
    public static String[] askForGraphDetails() {

        System.out.printf("%nWould you like to save this information in a graphic? (Y/N) ");

        String[] response = {"", "", ""};

        Scanner in = new Scanner(System.in);

        String answer = in.nextLine();

        if (answer.trim().equalsIgnoreCase("y")) {

            do {

                System.out.printf("Insert the graph name: ");

                answer = in.nextLine();

            } while (answer.trim().equals(""));

            int option;

            do {
                System.out.printf("Save graph as...?%n"
                        + "1 -> Image (.png)%n"
                        + "2 -> Ascii (.txt)%n"
                        + "3 -> PostScript (.eps)%n"
                        + "Insert your option here: ");

                option = in.nextInt();

            } while (option < 1 || option > 3);

            switch (option) {

                case 1:

                    response[0] = answer;

                    response[1] = Tools.encodeString(answer) + ".png";

                    response[2] = "png";

                    break;

                case 2:

                    response[0] = answer;

                    response[1] = Tools.encodeString(answer) + ".txt";

                    response[2] = "dumb";

                    break;

                case 3:

                    response[0] = answer;

                    response[1] = Tools.encodeString(answer) + ".eps";

                    response[2] = "postscript";

                    break;

                default:

                    //Do nothing.    
                    break;

            }

        }

        return response;

    }

    /**
     * Execute the program, reading the information from the first argument and
     * writing information to the second argument.
     *
     * @param args the command line arguments.
     *
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

        //Verify if the input file is valid.    
        if (!FileTools.isValid(inputFileData, ERROR_LOG)) {

            Tools.printError(String.format("The file %s is not valid.", inputFileName));

            return;

        }

        int nrVar = FileTools.getNumberOfVariables(inputFileData);

        float[][] matrix = FileTools.fillMatrixWithNonBasicVariables(inputFileData, nrVar);

        String secondLine = inputFileData.split(LINE_SEPARATOR)[1];

        float[][] finalMatrix;
        
        if (secondLine.contains("<=")) {

            finalMatrix = maximizeFunction(matrix, outputFileName, nrVar, inputFileData);

        } else {
            
            finalMatrix = minimizeFunction(matrix, outputFileName, nrVar, inputFileData);
            
        }

    }

}
