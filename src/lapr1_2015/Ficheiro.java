package lapr1_2015;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Grupo 2
 */
public class Ficheiro {
    public static int getNumberOfLines(File ficheiro){
        int nrLinhas = 0;
        try {
            Scanner scan = new Scanner(ficheiro);
            while(scan.hasNextLine()){
                String linha = scan.nextLine();
                if(!linha.trim().equals("")){
                    nrLinhas++;
                }
            }
        } catch (Exception e) {
        }
        
        return nrLinhas;
    }
    
    public static boolean interpretarFuncaoObjetivo(String linha, float [][] matriz, int linhaDaMatriz){
        int col;
        Matcher m = Pattern.compile("(" + MathTools.VARIABLE_PATTERN +")").matcher(linha);
        
        while(m.find()){
            String variavel = m.group(1);
            float coeficiente = MathTools.getVariableCoeficient(variavel);
            col = MathTools.getXIndex(variavel);
            matriz[linhaDaMatriz][col -1] = MathTools.calculateSimetric(coeficiente);
        }
        
        matriz[linhaDaMatriz][matriz[linhaDaMatriz].length - 1] = 0;
        
        return true;
    }
    
    public static boolean interpretarRestricao(String linha, float [][] matriz, int linhaDaMatriz){
        String parts[] = linha.split("<=");
        int col;
        Matcher m = Pattern.compile("(" + MathTools.VARIABLE_PATTERN +")").matcher(parts[0]);
        
        //Preenche os x1, x2, etc
        while(m.find()){
            String variavel = m.group(1);
            float coeficiente = MathTools.getVariableCoeficient(variavel);
            col = MathTools.getXIndex(variavel);
            matriz[linhaDaMatriz][col -1] = coeficiente;
        }
        
        //Preencher o s1, s2, etc
        matriz[linhaDaMatriz][linhaDaMatriz+1] = 1;
        
        //Preencher a solucao
        matriz[linhaDaMatriz][matriz[linhaDaMatriz].length -1] = Float.parseFloat(parts[1]);
        
        
        return true;
    }
 
    public static int readLine(String line, float[][] matrix, int matrixLine){
        line = Tools.retirarEspacos(line);
        if(line.equals("")){
            return matrixLine;
        }
        
        int nrCols = matrix[matrixLine].length;
        
        int indice = 0;
        
        float linhaTemp[] = new float[nrCols];
        
        if(matrixLine == 0){
            interpretarFuncaoObjetivo(line, matrix, matrixLine);
        }else{
            interpretarRestricao(line, matrix, matrixLine);
        }
        
        
        return ++matrixLine;
        
    }
    
    public static boolean isValid(File ficheiro){
        int nrLinha = 0;
        try {
            Scanner scan = new Scanner(ficheiro);
            
            while(scan.hasNextLine()){
                String linha = scan.nextLine();
                if(linha.equals("")){
                    continue;
                }
                if(nrLinha == 0){
                    if(!MathTools.validatesObjectiveFunction(Tools.retirarEspacos(linha))){
                        //@todo log erros?
                        return false;
                    }
                }else{
                    if(!MathTools.validatesRestriction(Tools.retirarEspacos(linha))){
                        //@todo log erros?
                        return false;
                    }
                }
                
                nrLinha++;
            }
        } catch (Exception e) {
            return false;
        }
        
        return (nrLinha > 0);
    }
}
