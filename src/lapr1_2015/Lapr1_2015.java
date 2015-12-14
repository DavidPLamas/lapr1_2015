package lapr1_2015;
import java.io.File;
import java.util.Scanner;
/**
 *
 * @author Grupo 2
 * 
 */
public class Lapr1_2015 {

    static void methodSimplex(float [][] matrix, File inputFile){
        fillMatrix(matrix, inputFile);
        
        int pivotColumn = MathTools.findPivotColumn(matrix);
        int pivotLine = MathTools.findPivotLine(matrix, pivotColumn);
        String output = appendNewMatrixOutput("",matrix);
        while(pivotColumn >= 0 && pivotLine >= 0){
            float pivot = matrix[pivotLine][pivotColumn];
            
            matrix[pivotLine] = MathTools.multiplyLineByScalar(matrix, pivotLine, 1/pivot);
            
            for(int i = 0; i < matrix.length; i++){
                if(i == pivotLine){
                    continue;
                }
                float scalar = MathTools.calculateSimetric(matrix[i][pivotColumn]);
                matrix[i] = MathTools.addTwoLinesWithScalar(matrix,i,pivotLine, scalar);
            }
            
            output = appendNewMatrixOutput(output,matrix);
            
            pivotColumn = MathTools.findPivotColumn(matrix);
            pivotLine = MathTools.findPivotLine(matrix, pivotColumn);
        }
        
        System.out.println(output);
    }
    
    static void fillMatrix(float[][] matrix, File file){
        int matrixLine = 0;
        try{
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine()){
                String line = scan.nextLine();
                matrixLine = Ficheiro.readLine(line, matrix, matrixLine);
            }
        } catch (Exception e) {
            Tools.printError("Ocorreu um erro ao ler o ficheiro");
        }
    }
    
    static String appendNewMatrixOutput(String previousOutput, float [][] matrix){
        String newOutput = previousOutput;
        
        for (float[] line : matrix) {
            for (int j = 0; j < line.length; j++) {
                newOutput += String.format("%7.2f", line[j]);
                if(j != (line.length -1)){
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
        //Verificar se o programa recebeu dois argumentos
        if(args.length < 2){
            Tools.printError("O programa precisa de dois argumentos!");
            return;
        }
        
        String inputFileName = args[0];
        String outputFileName = args[1];
        
        File inputFile = new File(inputFileName);
        
        //Verifica se o ficheiro input existe
        if(!inputFile.exists() || inputFile.isDirectory()){
            Tools.printError("O ficheiro %s não existe");
            return;
        }
        
        if(!Ficheiro.isValid(inputFile)){
            Tools.printError("O ficheiro nao é válido!!");
            return;
        }
        int nrLines = Ficheiro.getNumberOfLines(inputFile);
        
        if(nrLines <= 0){
            Tools.printError("O ficheiro %s deve ter mais de 0 linhas com conteúdo");
            return;
        }
        
        int nrVar = 2;
        float[][] matriz = new float[nrLines][nrLines + nrVar];
        
        methodSimplex(matriz, inputFile);
    }
    
}
