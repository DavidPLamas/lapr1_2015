package lapr1_2015;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author Group 2
 *
 */
public class Lapr1_2015 {

    private static void simplexMethod(float[][] matrix, File inputFile, int nrVar) {

        fillMatrix(matrix, inputFile);

        int pivotColumn = MathTools.findPivotColumn(matrix);

        int pivotLine = MathTools.findPivotLine(matrix, pivotColumn);

        String output = appendNewMatrixOutput("", matrix, nrVar);

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

            output = appendNewMatrixOutput(output, matrix, nrVar);

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

    private static String appendNewMatrixOutput(String previousOutput, float[][] matrix, int nrVar) {

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

        newOutput += "\n" + findZValue(matrix);
        newOutput += "\n" + findVariableValues(matrix, nrVar);

        return (newOutput + "\n\n");
    }

    private static String findVariableValues(float[][] matrix, int nrVar) {
        String output = "";
        String[] indexes = {"(","("};
        float[] indexValues = new float[matrix[0].length - 1];
        int lastColumnIndex =  matrix[0].length -1;
        
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 0; j < lastColumnIndex; j++) {
                if(matrix[i][j] == 1){
                    indexValues[j] = matrix[i][lastColumnIndex];
                    break;
                }
            }
        }
        
        for (int i = 1; i <= matrix[0].length -1; i++) {
            if(i <= nrVar){
                indexes[0] += "X" + i + ", ";
            }else{
                indexes[0] += "S" + (i-nrVar) + ", ";
            }
            indexes[1] += indexValues[i - 1] + ", ";
        }
        indexes[0] = indexes[0].substring(0, indexes[0].length() -2) + ")";
        indexes[1] = indexes[1].substring(0, indexes[1].length() -2) + ")";
        
        output = indexes[0] + " = " + indexes[1];
        
        return output;
    }
    
    public static String findZValue(float[][] matrix){
        String output = "";
        
        //Get the zValue
        float zValue = matrix[0][matrix[0].length - 1];
        
        return "Z = " + zValue;
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

        simplexMethod(matriz, inputFile, nrVar);

    }

}
