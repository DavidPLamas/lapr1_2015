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
        preencherMatrizDoFicheiro(matrix, inputFile);
        
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
    
    static void preencherMatrizDoFicheiro(float[][] matriz, File ficheiro){
        int linhaDaMatriz = 0;
        try{
            Scanner scan = new Scanner(ficheiro);
            while(scan.hasNextLine()){
                String linha = scan.nextLine();
                linhaDaMatriz = Ficheiro.analisarLinha(linha, matriz, linhaDaMatriz);
            }
        } catch (Exception e) {
            Tools.writeError("Ocorreu um erro ao ler o ficheiro");
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
            System.err.println("O programa precisa de dois argumentos!");
            return;
        }
        
        String nomeFicheiroInput = args[0];
        String nomeFicheiroOutput = args[1];
        
        File ficheiroInput = new File(nomeFicheiroInput);
        
        //Verifica se o ficheiro input existe
        if(!ficheiroInput.exists() || ficheiroInput.isDirectory()){
            System.err.printf("O ficheiro %s não existe", ficheiroInput);
            return;
        }
        
        if(!Ficheiro.isValid(ficheiroInput)){
            System.err.println("O ficheiro nao é válido!!");
            return;
        }
        int nrLinhas = Ficheiro.contaNrLinhas(ficheiroInput);
        
        if(nrLinhas <= 0){
            System.err.printf("O ficheiro %s deve ter mais de 0 linhas com conteúdo", ficheiroInput);
            return;
        }
        
        int nrVariaveis = 2;
        float[][] matriz = new float[nrLinhas][nrLinhas + nrVariaveis];
        
        methodSimplex(matriz, ficheiroInput);
    }
    
}
