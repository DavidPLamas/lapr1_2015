package lapr1_2015;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author Group 2
 *
 */
public class Lapr1_2015 {

    private static void simplexMethod(float[][] matrix, File inputFile) {

        fillMatrix(matrix, inputFile);

        int pivotColumn = MathTools.findPivotColumn(matrix);

        int pivotLine = MathTools.findPivotLine(matrix, pivotColumn);

        String output = appendNewMatrixOutput("", matrix);

        while (pivotColumn >= 0 && pivotLine >= 0) {

            float pivot = matrix[pivotLine][pivotColumn];

            matrix[pivotLine] = MathTools.multiplyLineByScalar(matrix, pivotLine, 1 / pivot);

            for (int i = 0; i < matrix.length; i++) {

                if (i == pivotLine) {

                    continue;
                }

                float scalar = MathTools.calculateSimetric(matrix[i][pivotColumn]);

                matrix[i] = MathTools.addTwoLinesWithScalar(matrix, i, pivotLine, scalar);
            }

            output = appendNewMatrixOutput(output, matrix);

            pivotColumn = MathTools.findPivotColumn(matrix);

            pivotLine = MathTools.findPivotLine(matrix, pivotColumn);
        }

        System.out.println(output);

    }

    private static void fillMatrix(float[][] matrix, File file) {

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

    private static String appendNewMatrixOutput(String previousOutput, float[][] matrix) {

        String newOutput = previousOutput;

        for (float[] line : matrix) {

            for (int j = 0; j < line.length; j++) {

                newOutput += String.format("%7.2f", line[j]);

                if (j != (line.length - 1)) {

                    newOutput += " | ";
                }
            }

            newOutput += "\n";

        }

        return newOutput + "\n";
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

            Tools.printError("The file %s doesn't exist.");

            return;
        }

        if (!FileTools.isValid(inputFile)) {

            Tools.printError("The file is not valid.");

            return;
        }

        int nrLines = FileTools.getNumberOfLines(inputFile);

        if (nrLines <= 0) {

            Tools.printError("The file %s shouldn't be empty.");

            return;
        }

        int nrVar = 2;

        float[][] matriz = new float[nrLines][nrLines + nrVar];

        simplexMethod(matriz, inputFile);

    }

}
